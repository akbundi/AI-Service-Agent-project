package com.example.myapplication.ui.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.models.ChatMessage
import com.example.myapplication.data.models.Location
import com.example.myapplication.data.models.PriceLevel
import com.example.myapplication.data.models.ServiceProvider
import com.example.myapplication.data.repository.ServiceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class ServiceViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ServiceRepository(application)

    // UI State
    private val _uiState = MutableStateFlow<UIState>(UIState.Initial)
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()

    // Chat messages
    private val _chatMessages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages.asStateFlow()

    // Service providers
    private val _providers = MutableStateFlow<List<ServiceProvider>>(emptyList())
    val providers: StateFlow<List<ServiceProvider>> = _providers.asStateFlow()

    // Current location
    private val _currentLocation = MutableStateFlow<Location?>(null)
    val currentLocation: StateFlow<Location?> = _currentLocation.asStateFlow()

    // Selected provider
    private val _selectedProvider = MutableStateFlow<ServiceProvider?>(null)
    val selectedProvider: StateFlow<ServiceProvider?> = _selectedProvider.asStateFlow()

    // Filter states
    private val _selectedPriceLevel = MutableStateFlow<PriceLevel?>(null)
    val selectedPriceLevel: StateFlow<PriceLevel?> = _selectedPriceLevel.asStateFlow()

    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> = _selectedCategory.asStateFlow()

    init {
        // Add welcome message
        addBotMessage(
            "👋 Namaste! I'm your AI assistant for finding local service providers across India. " +
                    "I can help you find services in cities like Jaipur, Kota, Delhi, Mumbai, Bangalore, and many more. " +
                    "First, please set your location to get started.",
            suggestions = listOf("Set my location", "Browse Indian cities")
        )
    }

    /**
     * Set user's location
     */
    fun setLocation(location: Location) {
        _currentLocation.value = location
        addBotMessage(
            "Great! I've set your location to ${location.address}. " +
                    "What kind of service are you looking for?",
            suggestions = listOf(
                "Find a plumber near me",
                "Show me coaching institutes",
                "I need a gym membership",
                "Find electricians nearby"
            )
        )
    }

    /**
     * Process user's chat message
     */
    fun sendMessage(message: String) {
        if (message.isBlank()) return

        // Add user message
        val userMessage = ChatMessage(
            id = UUID.randomUUID().toString(),
            text = message,
            isUser = true,
            timestamp = System.currentTimeMillis()
        )
        _chatMessages.value = _chatMessages.value + userMessage

        // Process with AI
        viewModelScope.launch {
            _uiState.value = UIState.Loading

            try {
                val result = repository.searchProvidersWithAI(
                    query = message,
                    location = _currentLocation.value,
                    conversationHistory = _chatMessages.value
                )

                // Update providers
                _providers.value = result.providers

                // Add bot response
                val botMessage = ChatMessage(
                    id = UUID.randomUUID().toString(),
                    text = result.aiResponse,
                    isUser = false,
                    timestamp = System.currentTimeMillis(),
                    providers = if (result.providers.isNotEmpty()) result.providers.take(3) else null,
                    suggestions = result.suggestions
                )
                _chatMessages.value = _chatMessages.value + botMessage

                _uiState.value = if (result.providers.isNotEmpty()) {
                    UIState.Success(result.providers)
                } else if (result.needsMoreInfo) {
                    UIState.NeedsMoreInfo
                } else {
                    UIState.NoResults
                }

            } catch (e: Exception) {
                _uiState.value = UIState.Error(e.message ?: "An error occurred")
                addBotMessage(
                    "Sorry, I encountered an error. Please try again.",
                    suggestions = listOf("Search again", "Show popular categories")
                )
            }
        }
    }

    /**
     * Search by category from dashboard
     */
    fun searchByCategory(category: String, priceLevel: PriceLevel? = null) {
        _selectedCategory.value = category
        _selectedPriceLevel.value = priceLevel

        val location = _currentLocation.value
        if (location == null) {
            _uiState.value = UIState.Error("Please set your location first")
            return
        }

        viewModelScope.launch {
            _uiState.value = UIState.Loading

            try {
                val providers = repository.searchByCategory(category, location, priceLevel)
                _providers.value = providers
                _uiState.value = if (providers.isNotEmpty()) {
                    UIState.Success(providers)
                } else {
                    UIState.NoResults
                }
            } catch (e: Exception) {
                _uiState.value = UIState.Error(e.message ?: "An error occurred")
            }
        }
    }

    /**
     * Get provider details
     */
    fun loadProviderDetails(providerId: String) {
        viewModelScope.launch {
            _uiState.value = UIState.Loading

            try {
                val provider = repository.getProviderDetails(providerId)
                _selectedProvider.value = provider
                _uiState.value = UIState.Success(listOfNotNull(provider))
            } catch (e: Exception) {
                _uiState.value = UIState.Error(e.message ?: "An error occurred")
            }
        }
    }

    /**
     * Set price level filter
     */
    fun setPriceLevel(priceLevel: PriceLevel?) {
        _selectedPriceLevel.value = priceLevel
        _selectedCategory.value?.let { category ->
            searchByCategory(category, priceLevel)
        }
    }

    /**
     * Clear filters
     */
    fun clearFilters() {
        _selectedPriceLevel.value = null
        _selectedCategory.value = null
    }

    /**
     * Get popular categories
     */
    fun getPopularCategories() = repository.getPopularCategories()

    private fun addBotMessage(text: String, suggestions: List<String> = emptyList()) {
        val botMessage = ChatMessage(
            id = UUID.randomUUID().toString(),
            text = text,
            isUser = false,
            timestamp = System.currentTimeMillis(),
            suggestions = suggestions
        )
        _chatMessages.value = _chatMessages.value + botMessage
    }
}

sealed class UIState {
    object Initial : UIState()
    object Loading : UIState()
    object NeedsMoreInfo : UIState()
    object NoResults : UIState()
    data class Success(val providers: List<ServiceProvider>) : UIState()
    data class Error(val message: String) : UIState()
}
