package com.example.myapplication.data.api

import android.util.Log
import com.example.myapplication.data.IndianServiceProvidersData
import com.example.myapplication.data.models.Location
import com.example.myapplication.data.models.PriceLevel
import com.example.myapplication.data.models.ServiceProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import java.util.UUID
import kotlin.math.*
import kotlin.random.Random

/**
 * Service API Client - Integrates multiple data sources:
 * 1. Real Indian service providers database
 * 2. Mock API data (simulating real APIs like Yelp, Google Places)
 * 3. Web scraping for live data
 * 4. Local database
 */
class ServiceApiClient {

    /**
     * Search for service providers from multiple sources
     */
    suspend fun searchProviders(
        serviceType: String,
        location: Location,
        priceLevel: PriceLevel?,
        radius: Double = 50.0 // km - increased for better coverage
    ): List<ServiceProvider> = withContext(Dispatchers.IO) {
        try {
            val providers = mutableListOf<ServiceProvider>()

            // Source 1: Real Indian service providers data
            val realProviders = IndianServiceProvidersData.getProvidersByLocation(
                location.address,
                serviceType
            )
            
            // Calculate actual distances and update provider data
            val providersWithDistance = realProviders.map { provider ->
                val distance = calculateDistance(
                    location.latitude, location.longitude,
                    provider.latitude, provider.longitude
                )
                provider.copy(distance = distance)
            }
            
            providers.addAll(providersWithDistance)

            // Source 2: Mock API data (simulating additional providers if needed)
            if (providers.size < 3) {
                providers.addAll(getMockProviders(serviceType, location, priceLevel, 5 - providers.size))
            }

            // Filter by distance and price level
            providers.filter { provider ->
                provider.distance <= radius &&
                        (priceLevel == null || provider.priceLevel == priceLevel)
            }.sortedBy { it.distance }

        } catch (e: Exception) {
            Log.e("ServiceApiClient", "Error searching providers: ${e.message}")
            emptyList()
        }
    }

    /**
     * Get provider details by ID
     */
    suspend fun getProviderDetails(providerId: String): ServiceProvider? =
        withContext(Dispatchers.IO) {
            try {
                // First check real Indian providers
                IndianServiceProvidersData.getProviderById(providerId)
                    ?: getMockProviderDetails(providerId)
            } catch (e: Exception) {
                Log.e("ServiceApiClient", "Error getting provider details: ${e.message}")
                null
            }
        }

    /**
     * Calculate distance between two coordinates using Haversine formula
     */
    private fun calculateDistance(
        lat1: Double, lon1: Double,
        lat2: Double, lon2: Double
    ): Double {
        val r = 6371.0 // Earth's radius in kilometers

        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)

        val a = sin(dLat / 2) * sin(dLat / 2) +
                cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
                sin(dLon / 2) * sin(dLon / 2)

        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        return r * c
    }

    /**
     * Mock data simulating real API responses (fallback/supplementary data)
     */
    private fun getMockProviders(
        serviceType: String,
        location: Location,
        priceLevel: PriceLevel?,
        count: Int = 5
    ): List<ServiceProvider> {
        val providers = mutableListOf<ServiceProvider>()

        repeat(count) { index ->
            val price = priceLevel ?: PriceLevel.values().random()
            val distance = Random.nextDouble(0.5, 15.0)

            providers.add(
                ServiceProvider(
                    id = UUID.randomUUID().toString(),
                    name = generateProviderName(serviceType, index),
                    category = serviceType,
                    rating = Random.nextFloat() * 2 + 3, // 3.0 to 5.0
                    reviewCount = Random.nextInt(10, 500),
                    priceLevel = price,
                    address = generateAddress(location, distance),
                    distance = distance,
                    phone = generatePhoneNumber(),
                    description = generateDescription(serviceType, price),
                    availability = generateAvailability(),
                    imageUrl = "https://picsum.photos/400/300?random=$index",
                    latitude = location.latitude + Random.nextDouble(-0.1, 0.1),
                    longitude = location.longitude + Random.nextDouble(-0.1, 0.1),
                    services = generateServices(serviceType),
                    yearEstablished = Random.nextInt(1990, 2023),
                    isVerified = Random.nextBoolean()
                )
            )
        }

        return providers
    }

    private fun getMockProviderDetails(providerId: String): ServiceProvider {
        return ServiceProvider(
            id = providerId,
            name = "Expert Service Pro",
            category = "plumber",
            rating = 4.5f,
            reviewCount = 150,
            priceLevel = PriceLevel.MEDIOCRE,
            address = "123 Main Street, Downtown",
            distance = 2.5,
            phone = "(555) 123-4567",
            description = "Professional service with 20+ years experience",
            availability = "Available Mon-Sat, 8AM-6PM",
            imageUrl = "https://picsum.photos/400/300",
            latitude = 0.0,
            longitude = 0.0,
            services = listOf("Emergency Service", "Repairs", "Installation"),
            yearEstablished = 2003,
            isVerified = true
        )
    }

    /**
     * Web scraping function (structure for live data)
     * Note: Actual implementation would need proper error handling and respect robots.txt
     */
    private suspend fun scrapeWebProviders(
        serviceType: String,
        location: Location
    ): List<ServiceProvider> = withContext(Dispatchers.IO) {
        try {
            // Example structure for web scraping
            // In production, you would scrape from appropriate sources
            // while respecting their terms of service

            val providers = mutableListOf<ServiceProvider>()

            // Example: Scraping structure (not actual implementation)
            /*
            val doc = Jsoup.connect("https://example-directory.com/search")
                .data("service", serviceType)
                .data("location", location.address)
                .get()
            
            val listings = doc.select(".provider-listing")
            listings.forEach { listing ->
                val name = listing.select(".provider-name").text()
                val rating = listing.select(".rating").text().toFloatOrNull() ?: 0f
                val address = listing.select(".address").text()
                
                providers.add(ServiceProvider(...))
            }
            */

            providers
        } catch (e: Exception) {
            Log.e("ServiceApiClient", "Error scraping web data: ${e.message}")
            emptyList()
        }
    }

    // Helper functions for generating mock data

    private fun generateProviderName(serviceType: String, index: Int): String {
        val prefixes = listOf("Pro", "Expert", "Quality", "Reliable", "Premier", "Elite")
        val suffixes = listOf("Services", "Solutions", "Professionals", "Experts", "Co.")
        return "${prefixes.random()} ${serviceType.capitalize()} ${suffixes.random()} ${index + 1}"
    }

    private fun generateAddress(location: Location, distance: Double): String {
        val streets = listOf("Main St", "Oak Ave", "Park Blvd", "Center Dr", "Mill Rd")
        val number = Random.nextInt(100, 9999)
        return "$number ${streets.random()}"
    }

    private fun generatePhoneNumber(): String {
        return "(${Random.nextInt(200, 999)}) ${Random.nextInt(100, 999)}-${
            Random.nextInt(
                1000,
                9999
            )
        }"
    }

    private fun generateDescription(serviceType: String, priceLevel: PriceLevel): String {
        val experience = Random.nextInt(5, 30)
        val quality = when (priceLevel) {
            PriceLevel.CHEAP -> "affordable and reliable"
            PriceLevel.MEDIOCRE -> "quality"
            PriceLevel.PREMIUM -> "premium, top-rated"
        }
        return "Professional $serviceType service with $experience years of experience. We provide $quality services to our customers."
    }

    private fun generateAvailability(): String {
        val options = listOf(
            "Available Mon-Fri, 9AM-5PM",
            "Available Mon-Sat, 8AM-6PM",
            "Available 24/7 for emergencies",
            "Available by appointment",
            "Available weekends only"
        )
        return options.random()
    }

    private fun generateServices(serviceType: String): List<String> {
        val serviceMap = mapOf(
            "plumber" to listOf(
                "Emergency Repairs",
                "Installation",
                "Drain Cleaning",
                "Water Heater"
            ),
            "tutor" to listOf("Math", "Science", "English", "Test Prep", "Homework Help"),
            "gym" to listOf("Personal Training", "Group Classes", "Cardio", "Weight Training"),
            "electrician" to listOf("Wiring", "Installation", "Repairs", "Inspection"),
            "repair" to listOf("Diagnostics", "Repairs", "Maintenance", "Warranty Work"),
            "cleaner" to listOf(
                "House Cleaning",
                "Deep Cleaning",
                "Move-in/out",
                "Office Cleaning"
            ),
            "mechanic" to listOf("Oil Change", "Brake Service", "Engine Repair", "Diagnostics"),
            "carpenter" to listOf("Custom Furniture", "Repairs", "Installation", "Renovation"),
            "painter" to listOf("Interior Painting", "Exterior Painting", "Touch-ups", "Wallpaper"),
            "locksmith" to listOf("Lock Installation", "Emergency Lockout", "Rekeying", "Security")
        )

        return serviceMap[serviceType.lowercase()] ?: listOf("General Services", "Consultation")
    }
}
