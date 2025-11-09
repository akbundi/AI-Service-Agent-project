# API Integration Guide

This guide explains how to integrate real APIs and data sources into the AI Service Finder app.

## Overview

The app currently uses mock data but is architected to easily integrate with:

- **Yelp Fusion API** - Business search and details
- **Google Places API** - Location-based business search
- **Custom APIs** - Your own service provider database
- **Web Scraping** - Direct data extraction from websites

## 🔌 Yelp Fusion API Integration

### Setup

1. **Get API Key**
    - Register at [Yelp Developers](https://www.yelp.com/developers)
    - Create an app to get your API key

2. **Add to Gradle Dependencies**
   ```kotlin
   // Already included in app/build.gradle.kts:
   implementation(libs.retrofit)
   implementation(libs.retrofit.gson)
   ```

3. **Create API Interface**

Create `app/src/main/java/com/example/myapplication/data/api/YelpApi.kt`:

```kotlin
package com.example.myapplication.data.api

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface YelpApi {
    @GET("v3/businesses/search")
    suspend fun searchBusinesses(
        @Header("Authorization") authorization: String,
        @Query("term") term: String,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("radius") radius: Int = 10000,
        @Query("limit") limit: Int = 20,
        @Query("price") price: String? = null
    ): YelpSearchResponse
    
    @GET("v3/businesses/{id}")
    suspend fun getBusinessDetails(
        @Header("Authorization") authorization: String,
        @Query("id") businessId: String
    ): YelpBusinessResponse
}

data class YelpSearchResponse(
    val businesses: List<YelpBusiness>,
    val total: Int
)

data class YelpBusiness(
    val id: String,
    val name: String,
    val rating: Float,
    val review_count: Int,
    val price: String?,
    val location: YelpLocation,
    val distance: Double,
    val phone: String?,
    val categories: List<YelpCategory>,
    val image_url: String?
)

data class YelpLocation(
    val address1: String?,
    val city: String?,
    val state: String?,
    val zip_code: String?
)

data class YelpCategory(
    val alias: String,
    val title: String
)

data class YelpBusinessResponse(
    val id: String,
    val name: String,
    val rating: Float,
    val review_count: Int,
    val price: String?,
    val location: YelpLocation,
    val phone: String?,
    val photos: List<String>,
    val hours: List<YelpHours>?,
    val categories: List<YelpCategory>
)

data class YelpHours(
    val open: List<YelpOpenHours>,
    val is_open_now: Boolean
)

data class YelpOpenHours(
    val day: Int,
    val start: String,
    val end: String
)
```

4. **Create Retrofit Client**

Add to `ServiceApiClient.kt`:

```kotlin
private val yelpApi: YelpApi by lazy {
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()
    
    Retrofit.Builder()
        .baseUrl("https://api.yelp.com/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(YelpApi::class.java)
}

private suspend fun searchYelpProviders(
    serviceType: String,
    location: Location,
    priceLevel: PriceLevel?
): List<ServiceProvider> = withContext(Dispatchers.IO) {
    try {
        val apiKey = "YOUR_YELP_API_KEY"
        val priceParam = when(priceLevel) {
            PriceLevel.CHEAP -> "1"
            PriceLevel.MEDIOCRE -> "2"
            PriceLevel.PREMIUM -> "3,4"
            null -> null
        }
        
        val response = yelpApi.searchBusinesses(
            authorization = "Bearer $apiKey",
            term = serviceType,
            latitude = location.latitude,
            longitude = location.longitude,
            price = priceParam
        )
        
        response.businesses.map { business ->
            ServiceProvider(
                id = business.id,
                name = business.name,
                category = serviceType,
                rating = business.rating,
                reviewCount = business.review_count,
                priceLevel = parsePriceLevel(business.price),
                address = formatAddress(business.location),
                distance = business.distance / 1000, // Convert to km
                phone = business.phone,
                description = business.categories.joinToString { it.title },
                availability = "Check website for hours",
                imageUrl = business.image_url,
                latitude = location.latitude, // Yelp doesn't provide exact coords
                longitude = location.longitude,
                services = business.categories.map { it.title },
                yearEstablished = null,
                isVerified = false
            )
        }
    } catch (e: Exception) {
        Log.e("YelpAPI", "Error: ${e.message}")
        emptyList()
    }
}

private fun parsePriceLevel(price: String?): PriceLevel {
    return when(price) {
        "$" -> PriceLevel.CHEAP
        "$$" -> PriceLevel.MEDIOCRE
        "$$$", "$$$$" -> PriceLevel.PREMIUM
        else -> PriceLevel.MEDIOCRE
    }
}

private fun formatAddress(location: YelpLocation): String {
    return listOfNotNull(
        location.address1,
        location.city,
        location.state
    ).joinToString(", ")
}
```

## 🗺️ Google Places API Integration

### Setup

1. **Enable Google Places API**
    - Go to [Google Cloud Console](https://console.cloud.google.com/)
    - Enable Places API
    - Get API key

2. **Add Dependency**
   ```kotlin
   implementation("com.google.android.libraries.places:places:3.3.0")
   ```

3. **Create Places Client**

```kotlin
class GooglePlacesClient(private val context: Context) {
    private val placesClient: PlacesClient by lazy {
        Places.initialize(context, "YOUR_GOOGLE_API_KEY")
        Places.createClient(context)
    }
    
    suspend fun searchNearbyPlaces(
        serviceType: String,
        location: Location,
        radius: Int = 5000
    ): List<ServiceProvider> = withContext(Dispatchers.IO) {
        // Implementation using Places SDK
        emptyList()
    }
}
```

## 🕷️ Web Scraping Integration

### JSoup Implementation

The app includes JSoup for web scraping. Here's how to implement it safely:

```kotlin
private suspend fun scrapeYellowPages(
    serviceType: String,
    location: Location
): List<ServiceProvider> = withContext(Dispatchers.IO) {
    try {
        // IMPORTANT: Check robots.txt and terms of service first!
        val url = "https://www.yellowpages.com/search?search_terms=$serviceType&geo_location_terms=${location.address}"
        
        val doc = Jsoup.connect(url)
            .userAgent("Mozilla/5.0")
            .timeout(10000)
            .get()
        
        val providers = mutableListOf<ServiceProvider>()
        
        doc.select(".result").forEach { element ->
            try {
                val name = element.select(".business-name").text()
                val address = element.select(".street-address").text()
                val phone = element.select(".phone").text()
                val rating = element.select(".rating").attr("data-rating").toFloatOrNull() ?: 0f
                
                providers.add(
                    ServiceProvider(
                        id = UUID.randomUUID().toString(),
                        name = name,
                        category = serviceType,
                        rating = rating,
                        reviewCount = 0,
                        priceLevel = PriceLevel.MEDIOCRE,
                        address = address,
                        distance = 0.0,
                        phone = phone,
                        description = "",
                        availability = "",
                        imageUrl = null,
                        latitude = location.latitude,
                        longitude = location.longitude,
                        services = listOf(serviceType),
                        yearEstablished = null,
                        isVerified = false
                    )
                )
            } catch (e: Exception) {
                Log.e("Scraper", "Error parsing element: ${e.message}")
            }
        }
        
        providers
    } catch (e: Exception) {
        Log.e("Scraper", "Error scraping: ${e.message}")
        emptyList()
    }
}
```

**Important Notes:**

- Always respect robots.txt
- Follow website terms of service
- Implement rate limiting
- Handle errors gracefully
- Consider legal implications

## 🔄 Updating ServiceApiClient

Modify the `searchProviders` method to use real APIs:

```kotlin
suspend fun searchProviders(
    serviceType: String,
    location: Location,
    priceLevel: PriceLevel?,
    radius: Double = 10.0
): List<ServiceProvider> = withContext(Dispatchers.IO) {
    try {
        val providers = mutableListOf<ServiceProvider>()
        
        // Source 1: Yelp API
        providers.addAll(searchYelpProviders(serviceType, location, priceLevel))
        
        // Source 2: Google Places
        providers.addAll(searchGooglePlaces(serviceType, location, priceLevel))
        
        // Source 3: Web scraping (use cautiously)
        if (providers.isEmpty()) {
            providers.addAll(scrapeWebProviders(serviceType, location))
        }
        
        // Source 4: Mock data (fallback)
        if (providers.isEmpty()) {
            providers.addAll(getMockProviders(serviceType, location, priceLevel))
        }
        
        // Remove duplicates based on name and address similarity
        providers.distinctBy { "${it.name}-${it.address}" }
            .filter { it.distance <= radius }
            .sortedBy { it.distance }
            
    } catch (e: Exception) {
        Log.e("ServiceApiClient", "Error: ${e.message}")
        getMockProviders(serviceType, location, priceLevel)
    }
}
```

## 🔐 API Key Management

### Secure Storage

Create `local.properties` (gitignored):

```properties
YELP_API_KEY=your_yelp_key
GOOGLE_API_KEY=your_google_key
RUNANYWHERE_API_KEY=your_runanywhere_key
```

Access in `build.gradle.kts`:

```kotlin
android {
    defaultConfig {
        buildConfigField("String", "YELP_API_KEY", 
            "\"${project.findProperty("YELP_API_KEY") ?: ""}\"")
        buildConfigField("String", "GOOGLE_API_KEY",
            "\"${project.findProperty("GOOGLE_API_KEY") ?: ""}\"")
    }
}
```

Use in code:

```kotlin
val yelpApiKey = BuildConfig.YELP_API_KEY
```

## 📊 Response Caching

Implement caching to reduce API calls:

```kotlin
class CachedServiceRepository(context: Context) {
    private val cache = mutableMapOf<String, Pair<Long, List<ServiceProvider>>>()
    private val cacheTimeout = 5 * 60 * 1000 // 5 minutes
    
    suspend fun searchProviders(
        serviceType: String,
        location: Location,
        priceLevel: PriceLevel?
    ): List<ServiceProvider> {
        val cacheKey = "$serviceType-${location.address}-$priceLevel"
        val cached = cache[cacheKey]
        
        if (cached != null && System.currentTimeMillis() - cached.first < cacheTimeout) {
            return cached.second
        }
        
        val results = apiClient.searchProviders(serviceType, location, priceLevel)
        cache[cacheKey] = System.currentTimeMillis() to results
        
        return results
    }
}
```

## 🧪 Testing API Integration

### Test with Mock Server

Use MockWebServer for testing:

```kotlin
class ApiTests {
    private lateinit var mockWebServer: MockWebServer
    
    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
    }
    
    @Test
    fun testYelpApiIntegration() {
        val response = MockResponse()
            .setResponseCode(200)
            .setBody(yelpMockResponse)
        
        mockWebServer.enqueue(response)
        
        // Test API call
    }
}
```

## 📈 Rate Limiting

Implement rate limiting to respect API limits:

```kotlin
class RateLimiter(
    private val maxRequests: Int,
    private val timeWindowMs: Long
) {
    private val requests = mutableListOf<Long>()
    
    suspend fun execute(block: suspend () -> Unit) {
        synchronized(requests) {
            val now = System.currentTimeMillis()
            requests.removeAll { it < now - timeWindowMs }
            
            if (requests.size >= maxRequests) {
                val oldestRequest = requests.first()
                val waitTime = oldestRequest + timeWindowMs - now
                delay(waitTime)
            }
            
            requests.add(now)
        }
        
        block()
    }
}

// Usage
val yelpLimiter = RateLimiter(maxRequests = 50, timeWindowMs = 60000)

suspend fun searchYelp(...) {
    yelpLimiter.execute {
        yelpApi.searchBusinesses(...)
    }
}
```

## 🔍 Error Handling

Implement robust error handling:

```kotlin
sealed class ApiResult<T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error<T>(val message: String, val code: Int?) : ApiResult<T>()
    data class NetworkError<T>(val exception: Exception) : ApiResult<T>()
}

suspend fun <T> safeApiCall(
    apiCall: suspend () -> T
): ApiResult<T> {
    return try {
        ApiResult.Success(apiCall())
    } catch (e: HttpException) {
        ApiResult.Error(e.message(), e.code())
    } catch (e: IOException) {
        ApiResult.NetworkError(e)
    } catch (e: Exception) {
        ApiResult.Error(e.message ?: "Unknown error", null)
    }
}
```

## 📝 API Documentation Links

- **Yelp Fusion API**: https://www.yelp.com/developers/documentation/v3
- **Google Places API**: https://developers.google.com/maps/documentation/places/web-service
- **OpenStreetMap Nominatim**: https://nominatim.org/release-docs/develop/api/Search/
- **JSoup Documentation**: https://jsoup.org/cookbook/

## 🎯 Best Practices

1. **Always use API keys securely** - Never commit them to version control
2. **Implement proper error handling** - APIs can fail
3. **Cache responses** - Reduce API calls and costs
4. **Respect rate limits** - Implement backoff strategies
5. **Handle pagination** - Don't miss results
6. **Monitor API usage** - Track costs and limits
7. **Test thoroughly** - Use mock servers for unit tests
8. **Document API quirks** - Each API has unique behaviors
9. **Handle deprecated endpoints** - Plan for API changes
10. **Implement fallbacks** - Always have a backup data source

## 🚀 Production Checklist

- [ ] Obtain production API keys
- [ ] Set up API key rotation
- [ ] Implement monitoring and alerting
- [ ] Add analytics for API performance
- [ ] Configure CDN for image caching
- [ ] Set up error reporting (Crashlytics, Sentry)
- [ ] Implement retry logic with exponential backoff
- [ ] Add request/response logging
- [ ] Test with production-like load
- [ ] Document API costs and limits
- [ ] Set up billing alerts
- [ ] Create API documentation for your team
