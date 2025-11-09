# RunAnywhere SDK Integration Guide

This guide covers the RunAnywhere SDK integration, customization, and best practices for
intelligent, private, and high-performance AI experiences in the Service Finder app.

## 📖 Overview

The RunAnywhere SDK powers the AI capabilities of this app, providing:

- **On-Device Processing**: All AI computations run locally for privacy
- **Natural Language Understanding**: Intent recognition and entity extraction
- **Intelligent Recommendations**: ML-powered provider ranking
- **Contextual Conversations**: Multi-turn dialogue management
- **Privacy-Preserving**: No data sent to external servers

## 🏗️ Architecture

### SDK Components

```
RunAnywhereSDK
├── Query Processing
│   ├── Intent Extraction
│   ├── Entity Recognition
│   └── Parameter Parsing
├── Recommendation Engine
│   ├── Scoring Algorithm
│   ├── Personalization
│   └── Ranking
├── Conversation Management
│   ├── Context Tracking
│   ├── Response Generation
│   └── Suggestion System
└── Privacy Layer
    ├── Local Processing
    ├── Data Encryption
    └── Anonymization
```

## 🚀 Getting Started

### 1. SDK Initialization

The SDK is initialized in `ServiceViewModel`:

```kotlin
class ServiceRepository(context: Context) {
    private val aiSDK = RunAnywhereSDK(context)
    // ... rest of implementation
}
```

### 2. API Key Configuration

Update your API key in `RunAnywhereSDK.kt`:

```kotlin
class RunAnywhereSDK(private val context: Context) {
    private val apiKey = "your_runanywhere_api_key_here"
    
    // Initialize SDK with your key
    init {
        // SDK initialization code
    }
}
```

**Production Best Practice:**

```kotlin
private val apiKey: String by lazy {
    // Load from encrypted storage
    context.getSharedPreferences("secure_prefs", Context.MODE_PRIVATE)
        .getString("runanywhere_key", "") ?: ""
}
```

## 🎯 Core Features

### 1. Query Processing

The SDK processes natural language queries to extract intent and parameters:

```kotlin
suspend fun processQuery(
    query: String,
    location: Location?,
    conversationHistory: List<ChatMessage>
): AIResponse
```

**Supported Intents:**

- `search` - Finding service providers
- `booking` - Scheduling appointments
- `compare` - Comparing providers
- `help` - User assistance

**Custom Intent Example:**

```kotlin
private fun extractIntent(query: String): String {
    val lowerQuery = query.lowercase()
    return when {
        lowerQuery.contains("review") || lowerQuery.contains("rating") -> "review_search"
        lowerQuery.contains("emergency") || lowerQuery.contains("urgent") -> "emergency_search"
        // Add your custom intents
        else -> "search"
    }
}
```

### 2. Entity Extraction

The SDK automatically extracts:

- **Service Types**: plumber, tutor, gym, etc.
- **Price Levels**: cheap, mediocre, premium
- **Location**: addresses, neighborhoods
- **Time**: dates, availability

**Adding New Service Types:**

```kotlin
companion object {
    private val serviceKeywords = mapOf(
        "plumber" to listOf("plumber", "plumbing", "pipe", "leak"),
        // Add your service type
        "photographer" to listOf("photographer", "photography", "photos", "photoshoot"),
        "lawyer" to listOf("lawyer", "attorney", "legal", "law firm")
    )
}
```

### 3. Recommendation Engine

AI-powered ranking based on multiple factors:

```kotlin
suspend fun generateRecommendations(
    providers: List<ServiceProvider>,
    userPreferences: Map<String, Any>,
    query: String
): List<ServiceProvider>
```

**Scoring Algorithm:**

```kotlin
providers.sortedByDescending { provider ->
    var score = 0.0
    
    // Rating weight (0-100 points)
    score += provider.rating * 20
    
    // Price match (0-30 points)
    if (priceMatch) score += 30
    
    // Distance weight (0-50 points)
    score += (10 - distance) * 5
    
    // Verification bonus (15 points)
    if (provider.isVerified) score += 15
    
    // Review count (0-10 points)
    score += (reviewCount / 10.0).coerceAtMost(10.0)
    
    score
}
```

**Customizing the Algorithm:**

```kotlin
// Add time-based scoring
val hourOfDay = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
if (hourOfDay in provider.openHours) {
    score += 20 // Boost for currently open businesses
}

// Add user preference learning
val userHistory = getUserInteractionHistory()
if (provider.category in userHistory.favoriteCategories) {
    score += 25 // Boost based on user preferences
}

// Add seasonal factors
val currentMonth = Calendar.getInstance().get(Calendar.MONTH)
if (isSeasonal(provider.category, currentMonth)) {
    score += 10
}
```

### 4. Response Generation

Generate natural, conversational responses:

```kotlin
suspend fun generateChatResponse(
    userMessage: String,
    providers: List<ServiceProvider>,
    location: Location?
): String
```

**Response Templates:**

```kotlin
// Customize responses for better UX
when {
    providers.isEmpty() -> 
        "I couldn't find any ${serviceType}s in ${location.address}. " +
        "Would you like me to expand the search radius?"
    
    providers.size == 1 -> 
        "I found ${provider.name}! They have ${provider.rating}⭐ " +
        "and are ${provider.distance}km away. Shall I show you more details?"
    
    providers.size < 5 ->
        "I found ${providers.size} great options. " +
        "The top-rated one is ${providers.first().name} with ${providers.first().rating} stars."
    
    else ->
        "Great news! I found ${providers.size} ${serviceType}s nearby. " +
        "I've ranked them by rating, distance, and your preferences. " +
        "Would you like to see the top 5?"
}
```

## 🎨 Customization Examples

### 1. Adding Context Awareness

Track conversation context for better responses:

```kotlin
class ConversationContext {
    var lastServiceType: String? = null
    var lastPriceLevel: PriceLevel? = null
    var lastProviders: List<ServiceProvider> = emptyList()
    var userPreferences: MutableMap<String, Any> = mutableMapOf()
    
    fun updateFromQuery(query: String, response: AIResponse) {
        lastServiceType = response.serviceType
        lastPriceLevel = response.priceLevel
    }
    
    fun getContextualSuggestions(): List<String> {
        return buildList {
            lastServiceType?.let {
                add("Show me more $it options")
                add("Compare the top 3 $it providers")
            }
            if (lastProviders.isNotEmpty()) {
                add("Book the top rated one")
                add("Get directions to the closest")
            }
        }
    }
}
```

### 2. Implementing User Preferences

Learn from user interactions:

```kotlin
class UserPreferenceLearner {
    private val interactions = mutableListOf<UserInteraction>()
    
    fun recordInteraction(provider: ServiceProvider, action: String) {
        interactions.add(UserInteraction(
            providerId = provider.id,
            category = provider.category,
            priceLevel = provider.priceLevel,
            rating = provider.rating,
            action = action,
            timestamp = System.currentTimeMillis()
        ))
        
        // Update preferences
        analyzePreferences()
    }
    
    private fun analyzePreferences(): Map<String, Any> {
        return mapOf(
            "preferredPriceLevel" to getMostCommonPriceLevel(),
            "minimumRating" to getAverageClickedRating(),
            "preferredCategories" to getTopCategories()
        )
    }
}
```

### 3. Multi-Language Support

Add internationalization to AI responses:

```kotlin
class LocalizedResponseGenerator(private val locale: Locale) {
    
    fun generateResponse(
        key: String,
        params: Map<String, Any>
    ): String {
        val template = when (locale.language) {
            "es" -> spanishTemplates[key]
            "fr" -> frenchTemplates[key]
            else -> englishTemplates[key]
        } ?: englishTemplates[key]!!
        
        return template.format(params)
    }
    
    private val englishTemplates = mapOf(
        "found_providers" to "I found {count} {service} providers near you",
        "no_results" to "No {service} providers found in {location}"
    )
    
    private val spanishTemplates = mapOf(
        "found_providers" to "Encontré {count} proveedores de {service} cerca de ti",
        "no_results" to "No se encontraron proveedores de {service} en {location}"
    )
}
```

### 4. Advanced Filtering

Implement complex filtering logic:

```kotlin
class AdvancedFilterEngine {
    
    fun applyFilters(
        providers: List<ServiceProvider>,
        filters: FilterCriteria
    ): List<ServiceProvider> {
        return providers
            .filter { filters.minRating?.let { min -> it.rating >= min } ?: true }
            .filter { filters.maxDistance?.let { max -> it.distance <= max } ?: true }
            .filter { filters.priceRange?.contains(it.priceLevel) ?: true }
            .filter { filters.verified?.let { req -> it.isVerified == req } ?: true }
            .filter { filters.minReviews?.let { min -> it.reviewCount >= min } ?: true }
            .filter { filters.availability?.let { req -> checkAvailability(it, req) } ?: true }
    }
}

data class FilterCriteria(
    val minRating: Float? = null,
    val maxDistance: Double? = null,
    val priceRange: Set<PriceLevel>? = null,
    val verified: Boolean? = null,
    val minReviews: Int? = null,
    val availability: TimeRange? = null
)
```

## 🔒 Privacy & Security

### 1. Local Data Processing

All AI processing happens on-device:

```kotlin
class PrivacyPreservingSDK(context: Context) {
    
    // All data stays local
    private val localModel = loadLocalModel(context)
    
    suspend fun processSecurely(input: String): Result {
        // No network calls for AI processing
        return withContext(Dispatchers.Default) {
            localModel.process(input)
        }
    }
    
    // Optionally encrypt sensitive data
    private fun encryptIfNeeded(data: String): String {
        return if (sensitiveDataDetected(data)) {
            encrypt(data)
        } else {
            data
        }
    }
}
```

### 2. Data Anonymization

Remove PII before any processing:

```kotlin
class DataAnonymizer {
    
    fun anonymize(query: String): String {
        var clean = query
        
        // Remove phone numbers
        clean = clean.replace(Regex("\\d{3}[-.]?\\d{3}[-.]?\\d{4}"), "[PHONE]")
        
        // Remove emails
        clean = clean.replace(Regex("\\S+@\\S+\\.\\S+"), "[EMAIL]")
        
        // Remove addresses
        clean = removeSpecificAddresses(clean)
        
        return clean
    }
}
```

## 📊 Performance Optimization

### 1. Caching Strategies

```kotlin
class IntelligentCache {
    private val memoryCache = LruCache<String, AIResponse>(50)
    private val diskCache = DiskLruCache.open(...)
    
    suspend fun getOrCompute(
        key: String,
        compute: suspend () -> AIResponse
    ): AIResponse {
        // Check memory cache
        memoryCache.get(key)?.let { return it }
        
        // Check disk cache
        diskCache.get(key)?.let { 
            return it.also { memoryCache.put(key, it) }
        }
        
        // Compute and cache
        return compute().also {
            memoryCache.put(key, it)
            diskCache.put(key, it)
        }
    }
}
```

### 2. Batch Processing

Process multiple queries efficiently:

```kotlin
suspend fun processBatch(
    queries: List<String>
): List<AIResponse> = coroutineScope {
    queries.map { query ->
        async {
            processQuery(query, null, emptyList())
        }
    }.awaitAll()
}
```

### 3. Model Optimization

```kotlin
class OptimizedModel {
    // Use quantized models for faster inference
    private val tfliteModel = loadQuantizedModel()
    
    // Batch predictions for efficiency
    fun predictBatch(inputs: List<FloatArray>): List<FloatArray> {
        val batchSize = inputs.size
        val inputTensor = Array(batchSize) { inputs[it] }
        return tfliteModel.runBatch(inputTensor)
    }
}
```

## 🧪 Testing AI Features

### Unit Tests

```kotlin
class RunAnywhereSDKTest {
    
    @Test
    fun `test intent extraction for search query`() = runTest {
        val sdk = RunAnywhereSDK(context)
        val response = sdk.processQuery(
            query = "find a plumber near me",
            location = mockLocation,
            conversationHistory = emptyList()
        )
        
        assertEquals("search", response.intent)
        assertEquals("plumber", response.serviceType)
    }
    
    @Test
    fun `test price level extraction`() = runTest {
        val sdk = RunAnywhereSDK(context)
        val response = sdk.processQuery(
            query = "show me cheap gyms",
            location = mockLocation,
            conversationHistory = emptyList()
        )
        
        assertEquals(PriceLevel.CHEAP, response.priceLevel)
    }
}
```

### Integration Tests

```kotlin
class AIIntegrationTest {
    
    @Test
    fun `test end-to-end search flow`() = runTest {
        val repository = ServiceRepository(context)
        
        val result = repository.searchProvidersWithAI(
            query = "I need an affordable plumber",
            location = testLocation,
            conversationHistory = emptyList()
        )
        
        assertTrue(result.providers.isNotEmpty())
        assertTrue(result.providers.all { it.priceLevel == PriceLevel.CHEAP })
        assertFalse(result.needsMoreInfo)
    }
}
```

## 📈 Analytics & Monitoring

### Track AI Performance

```kotlin
class AIAnalytics {
    
    fun logQueryProcessing(
        query: String,
        response: AIResponse,
        processingTime: Long
    ) {
        val metrics = mapOf(
            "query_length" to query.length,
            "intent" to response.intent,
            "confidence" to response.confidence,
            "processing_time_ms" to processingTime,
            "has_service_type" to (response.serviceType != null),
            "has_price_level" to (response.priceLevel != null)
        )
        
        // Log to analytics service (locally, privacy-preserving)
        analyticsLogger.log("ai_query_processed", metrics)
    }
    
    fun logRecommendationQuality(
        providers: List<ServiceProvider>,
        userSelectedIndex: Int
    ) {
        val metrics = mapOf(
            "total_results" to providers.size,
            "selected_index" to userSelectedIndex,
            "selected_rating" to providers[userSelectedIndex].rating,
            "top_rated_selected" to (userSelectedIndex == 0)
        )
        
        analyticsLogger.log("recommendation_interaction", metrics)
    }
}
```

## 🚀 Advanced Features

### 1. Voice Input Support

```kotlin
class VoiceEnabledSDK(private val baseSDK: RunAnywhereSDK) {
    
    suspend fun processVoiceQuery(
        audioData: ByteArray
    ): AIResponse {
        // Convert speech to text (on-device)
        val text = speechToText(audioData)
        
        // Process with AI
        return baseSDK.processQuery(text, null, emptyList())
    }
    
    private suspend fun speechToText(audio: ByteArray): String {
        // Use Android Speech Recognition API
        // or on-device ML model
        return ""
    }
}
```

### 2. Image-Based Search

```kotlin
class VisualSearchSDK(private val context: Context) {
    
    suspend fun searchByImage(
        imageUri: Uri
    ): List<ServiceProvider> {
        // Extract features from image
        val features = extractImageFeatures(imageUri)
        
        // Match to service categories
        val category = classifyServiceCategory(features)
        
        // Search providers
        return searchByCategory(category)
    }
}
```

### 3. Predictive Suggestions

```kotlin
class PredictiveEngine {
    
    fun getPredictiveSuggestions(
        partialQuery: String,
        context: ConversationContext
    ): List<String> {
        return buildList {
            // Complete based on popular queries
            add(completeFromPopular(partialQuery))
            
            // Suggest based on history
            add(completeFromHistory(partialQuery))
            
            // Context-aware completion
            add(completeWithContext(partialQuery, context))
        }
    }
}
```

## 📚 Best Practices

1. **Always validate inputs** - Sanitize user queries
2. **Handle edge cases** - Empty results, malformed queries
3. **Optimize for performance** - Cache aggressively
4. **Respect privacy** - Keep data local
5. **Monitor quality** - Track AI accuracy
6. **Test thoroughly** - Unit and integration tests
7. **Update models** - Keep AI models current
8. **Document behavior** - Clear AI decision explanations
9. **Fallback gracefully** - Have non-AI alternatives
10. **Iterate based on feedback** - Continuous improvement

## 🎓 Resources

- RunAnywhere SDK Documentation: [docs.runanywhere.com](https://docs.runanywhere.com)
- TensorFlow Lite: [tensorflow.org/lite](https://www.tensorflow.org/lite)
- ML Kit: [developers.google.com/ml-kit](https://developers.google.com/ml-kit)
- On-Device AI Best Practices: [developer.android.com/ml](https://developer.android.com/ml)

## 🤝 Support

For SDK-specific questions:

- Email: support@runanywhere.com
- Documentation: https://docs.runanywhere.com
- Community Forum: https://community.runanywhere.com

---

**Remember**: The RunAnywhere SDK enables powerful AI while keeping user data private and secure
through on-device processing.
