package com.example.myapplication.data.api

import android.content.Context
import com.example.myapplication.data.models.ChatMessage
import com.example.myapplication.data.models.Location
import com.example.myapplication.data.models.PriceLevel
import com.example.myapplication.data.models.ServiceProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

/**
 * RunAnywhere SDK Integration
 * This provides intelligent, private, and high-performance AI experiences
 * for service provider recommendations and natural language understanding
 */
class RunAnywhereSDK(private val context: Context) {
    
    private val apiKey = "your_runanywhere_api_key_here"
    
    /**
     * Process user query using AI to understand intent and extract parameters
     */
    suspend fun processQuery(
        query: String,
        location: Location?,
        conversationHistory: List<ChatMessage>
    ): AIResponse = withContext(Dispatchers.IO) {
        try {
            // Simulate RunAnywhere SDK AI processing
            val intent = extractIntent(query)
            val parameters = extractParameters(query)
            
            AIResponse(
                intent = intent,
                serviceType = parameters["serviceType"],
                priceLevel = parameters["priceLevel"]?.let { 
                    when (it.lowercase()) {
                        "cheap", "affordable", "budget" -> PriceLevel.CHEAP
                        "mediocre", "medium", "moderate" -> PriceLevel.MEDIOCRE
                        "premium", "expensive", "high-end" -> PriceLevel.PREMIUM
                        else -> null
                    }
                },
                location = location,
                confidence = 0.95f,
                suggestedQuestions = generateSuggestions(intent, parameters)
            )
        } catch (e: Exception) {
            AIResponse(
                intent = "search",
                serviceType = null,
                priceLevel = null,
                location = location,
                confidence = 0.5f,
                suggestedQuestions = listOf("What service are you looking for?")
            )
        }
    }
    
    /**
     * Generate personalized recommendations using AI
     */
    suspend fun generateRecommendations(
        providers: List<ServiceProvider>,
        userPreferences: Map<String, Any>,
        query: String
    ): List<ServiceProvider> = withContext(Dispatchers.IO) {
        // AI-powered ranking based on user preferences, query intent, and provider quality
        providers.sortedByDescending { provider ->
            var score = 0.0
            
            // Rating weight
            score += provider.rating * 20
            
            // Price level preference
            userPreferences["priceLevel"]?.let { pref ->
                if (pref == provider.priceLevel) score += 30
            }
            
            // Distance weight (closer is better)
            score += (10 - provider.distance.coerceAtMost(10.0)) * 5
            
            // Verification bonus
            if (provider.isVerified) score += 15
            
            // Review count weight
            score += (provider.reviewCount / 10.0).coerceAtMost(10.0)
            
            score
        }.take(10)
    }
    
    /**
     * Generate smart response for chatbot
     */
    suspend fun generateChatResponse(
        userMessage: String,
        providers: List<ServiceProvider>,
        location: Location?
    ): String = withContext(Dispatchers.IO) {
        when {
            providers.isEmpty() -> {
                "I couldn't find any service providers matching your criteria in your area. " +
                "Would you like to try a different location or service type?"
            }
            providers.size == 1 -> {
                val p = providers.first()
                "I found ${p.name}, a ${p.priceLevel.name.lowercase()} option with ${p.rating} stars. " +
                "They're located ${String.format("%.1f", p.distance)}km away. Would you like to see more details?"
            }
            else -> {
                val avgRating = providers.map { it.rating }.average()
                "I found ${providers.size} service providers near you with an average rating of " +
                "${String.format("%.1f", avgRating)} stars. The closest one is " +
                "${String.format("%.1f", providers.first().distance)}km away. " +
                "Would you like me to show you the top recommendations?"
            }
        }
    }
    
    private fun extractIntent(query: String): String {
        val lowerQuery = query.lowercase()
        return when {
            lowerQuery.contains("find") || lowerQuery.contains("search") || 
            lowerQuery.contains("looking for") || lowerQuery.contains("need") -> "search"
            lowerQuery.contains("book") || lowerQuery.contains("schedule") || 
            lowerQuery.contains("appointment") -> "booking"
            lowerQuery.contains("compare") || lowerQuery.contains("difference") -> "compare"
            lowerQuery.contains("help") || lowerQuery.contains("how") -> "help"
            else -> "search"
        }
    }
    
    private fun extractParameters(query: String): Map<String, String> {
        val params = mutableMapOf<String, String>()
        val lowerQuery = query.lowercase()
        
        // Extract service type
        serviceKeywords.forEach { (service, keywords) ->
            if (keywords.any { lowerQuery.contains(it) }) {
                params["serviceType"] = service
            }
        }
        
        // Extract price level
        when {
            lowerQuery.contains("cheap") || lowerQuery.contains("affordable") || 
            lowerQuery.contains("budget") -> params["priceLevel"] = "cheap"
            lowerQuery.contains("premium") || lowerQuery.contains("expensive") || 
            lowerQuery.contains("high-end") || lowerQuery.contains("best") -> 
                params["priceLevel"] = "premium"
            lowerQuery.contains("medium") || lowerQuery.contains("moderate") || 
            lowerQuery.contains("mediocre") -> params["priceLevel"] = "mediocre"
        }
        
        return params
    }
    
    private fun generateSuggestions(intent: String, parameters: Map<String, String>): List<String> {
        val suggestions = mutableListOf<String>()
        
        if (parameters["serviceType"] != null && parameters["priceLevel"] == null) {
            suggestions.add("Would you prefer cheap, mediocre, or premium options?")
        }
        
        if (parameters["priceLevel"] != null && parameters["serviceType"] == null) {
            suggestions.add("What type of service are you looking for?")
        }
        
        if (intent == "search" && parameters.isNotEmpty()) {
            suggestions.add("Show me the top rated options")
            suggestions.add("What's available this week?")
            suggestions.add("Compare the best 3 providers")
        }
        
        return suggestions.ifEmpty { 
            listOf(
                "Find plumbers near me",
                "Show cheap gym memberships",
                "Premium tutors in my area"
            )
        }
    }
    
    companion object {
        private val serviceKeywords = mapOf(
            "plumber" to listOf(
                "plumber",
                "plumbing",
                "pipe",
                "leak",
                "drain",
                "water tank",
                "tap",
                "bathroom fitting"
            ),
            "tutor" to listOf(
                "tutor",
                "tutoring",
                "teacher",
                "education",
                "lessons",
                "coaching",
                "iit",
                "jee",
                "neet",
                "allen",
                "resonance",
                "classes",
                "academy",
                "institute",
                "preparation"
            ),
            "gym" to listOf(
                "gym", "fitness", "workout", "exercise", "training", "yoga", "zumba", "crossfit",
                "health club", "gymnasium"
            ),
            "electrician" to listOf(
                "electrician",
                "electrical",
                "wiring",
                "electric",
                "voltage",
                "circuit",
                "inverter"
            ),
            "repair" to listOf(
                "repair", "fix", "service", "maintenance", "ac", "refrigerator", "washing machine",
                "appliance"
            ),
            "cleaner" to listOf(
                "cleaning",
                "cleaner",
                "maid",
                "housekeeping",
                "sanitization",
                "deep clean"
            ),
            "mechanic" to listOf(
                "mechanic",
                "auto",
                "car repair",
                "vehicle",
                "garage",
                "automobile",
                "bike service"
            ),
            "carpenter" to listOf("carpenter", "woodwork", "furniture", "sofa", "wardrobe"),
            "painter" to listOf("painter", "painting", "paint job", "whitewash", "colour"),
            "locksmith" to listOf("locksmith", "lock", "key", "door lock", "security")
        )
    }
}

data class AIResponse(
    val intent: String,
    val serviceType: String?,
    val priceLevel: PriceLevel?,
    val location: Location?,
    val confidence: Float,
    val suggestedQuestions: List<String>
)
