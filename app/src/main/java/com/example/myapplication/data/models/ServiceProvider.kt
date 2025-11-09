package com.example.myapplication.data.models

data class ServiceProvider(
    val id: String,
    val name: String,
    val category: String,
    val rating: Float,
    val reviewCount: Int,
    val priceLevel: PriceLevel,
    val address: String,
    val distance: Double, // in km
    val phone: String?,
    val description: String,
    val availability: String,
    val imageUrl: String?,
    val latitude: Double,
    val longitude: Double,
    val services: List<String>,
    val yearEstablished: Int?,
    val isVerified: Boolean = false
)

enum class PriceLevel {
    CHEAP,
    MEDIOCRE,
    PREMIUM
}

data class ServiceCategory(
    val id: String,
    val name: String,
    val icon: String,
    val description: String
)

data class Location(
    val latitude: Double,
    val longitude: Double,
    val address: String
)

data class ChatMessage(
    val id: String,
    val text: String,
    val isUser: Boolean,
    val timestamp: Long,
    val providers: List<ServiceProvider>? = null,
    val suggestions: List<String>? = null
)

data class BookingRequest(
    val providerId: String,
    val userId: String,
    val serviceType: String,
    val preferredDate: Long,
    val notes: String?
)
