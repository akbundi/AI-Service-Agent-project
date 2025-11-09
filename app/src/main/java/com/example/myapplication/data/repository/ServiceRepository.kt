package com.example.myapplication.data.repository

import android.content.Context
import com.example.myapplication.data.api.RunAnywhereSDK
import com.example.myapplication.data.api.ServiceApiClient
import com.example.myapplication.data.models.ChatMessage
import com.example.myapplication.data.models.Location
import com.example.myapplication.data.models.PriceLevel
import com.example.myapplication.data.models.ServiceProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.UUID

class ServiceRepository(context: Context) {

    private val apiClient = ServiceApiClient()
    private val aiSDK = RunAnywhereSDK(context)

    /**
     * Search for service providers using AI-powered query processing
     */
    suspend fun searchProvidersWithAI(
        query: String,
        location: Location?,
        conversationHistory: List<ChatMessage>
    ): SearchResult {
        // Step 1: Process query with RunAnywhere AI SDK
        val aiResponse = aiSDK.processQuery(query, location, conversationHistory)

        // Step 2: If we have enough information, search for providers
        val providers = if (aiResponse.serviceType != null && location != null) {
            apiClient.searchProviders(
                serviceType = aiResponse.serviceType,
                location = location,
                priceLevel = aiResponse.priceLevel
            )
        } else {
            emptyList()
        }

        // Step 3: Use AI to rank and recommend providers
        val recommendations = if (providers.isNotEmpty()) {
            val preferences = mutableMapOf<String, Any>()
            aiResponse.priceLevel?.let { preferences["priceLevel"] = it }
            aiSDK.generateRecommendations(providers, preferences, query)
        } else {
            providers
        }

        // Step 4: Generate intelligent response
        val responseText = aiSDK.generateChatResponse(query, recommendations, location)

        return SearchResult(
            providers = recommendations,
            aiResponse = responseText,
            suggestions = aiResponse.suggestedQuestions,
            needsMoreInfo = aiResponse.serviceType == null || location == null
        )
    }

    /**
     * Get detailed information about a specific provider
     */
    suspend fun getProviderDetails(providerId: String): ServiceProvider? {
        return apiClient.getProviderDetails(providerId)
    }

    /**
     * Search providers by category and location
     */
    suspend fun searchByCategory(
        category: String,
        location: Location,
        priceLevel: PriceLevel? = null
    ): List<ServiceProvider> {
        return apiClient.searchProviders(category, location, priceLevel)
    }

    /**
     * Get popular categories
     */
    fun getPopularCategories(): List<ServiceCategory> {
        return listOf(
            ServiceCategory("plumber", "🔧 Plumbing", "Pipes, leaks, and water systems"),
            ServiceCategory("tutor", "📚 Coaching/Tutoring", "IIT-JEE, NEET, school tuition"),
            ServiceCategory("gym", "💪 Fitness & Gyms", "Gyms, yoga, and personal training"),
            ServiceCategory("electrician", "⚡ Electrical", "Wiring and electrical work"),
            ServiceCategory("repair", "🔨 Repairs", "AC, appliances, and maintenance"),
            ServiceCategory("cleaner", "🧹 Cleaning", "House and office cleaning"),
            ServiceCategory("mechanic", "🚗 Auto Repair", "Car and vehicle service"),
            ServiceCategory("carpenter", "🪚 Carpentry", "Woodwork and furniture"),
            ServiceCategory("painter", "🎨 Painting", "Interior and exterior painting"),
            ServiceCategory("locksmith", "🔐 Locksmith", "Lock and security services")
        )
    }
}

data class SearchResult(
    val providers: List<ServiceProvider>,
    val aiResponse: String,
    val suggestions: List<String>,
    val needsMoreInfo: Boolean
)

data class ServiceCategory(
    val id: String,
    val name: String,
    val description: String
)
