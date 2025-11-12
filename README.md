# Service Finder AI - Mobile Application

## Overview

An intelligent mobile application that helps users find and book local service providers across
India, featuring 70+ cities across 20+ states with special focus on Rajasthan's coaching hubs like
Kota.

The app integrates the **RunAnywhere SDK** for private, intelligent, and high-performance AI
experiences, providing natural language search and smart recommendations.

## Features

### 🌍 Coverage

- **70+ Indian Cities** across **20+ States**
- **Rajasthan Complete Coverage**: Kota, Jaipur, Ajmer, Bundi, Udaipur, Jodhpur, and more
- **Major Metropolitan Areas**: Delhi NCR, Mumbai, Bangalore, Chennai, Hyderabad, Pune, etc.

### 🎯 Services

- **IIT-JEE & NEET Coaching Institutes** (50+ verified providers)
- **Gyms & Fitness Centers**
- **Plumbing Services**
- **Tutors** (Home Tuition)
- **Repair Services**
- **Electricians**
- **And more...**

### 🤖 AI-Powered Features

- **Natural Language Search**: "Find cheap IIT coaching in Kota"
- **Smart Recommendations**: Based on location and user context
- **Intent Recognition**: Automatically detects service type, price range, and location from queries
- **On-Device Processing**: Privacy-first AI with RunAnywhere SDK

### 💰 Price Tiers

- **Budget-Friendly**: Affordable options for students
- **Standard**: Quality service at reasonable prices
- **Premium**: Top-tier services with excellent facilities

### 📱 Hybrid Interface

- **Chatbot Tab**: Conversational AI interface for natural queries
- **Dashboard Tab**: Visual browse experience with categories and filters

### 📍 Location Features

- **GPS Location**: Auto-detect nearest city
- **Manual City Selection**: Browse 70+ cities
- **Distance Calculation**: Real-time distance using Haversine formula
- **Nearby Search**: Find services within specified radius

## Architecture

### Tech Stack

- **Platform**: Android (Kotlin + Jetpack Compose)
- **UI Framework**: Material Design 3
- **Architecture**: MVVM (Model-View-ViewModel)
- **State Management**: Kotlin Flows & StateFlow
- **AI SDK**: RunAnywhere SDK (private on-device processing)

### Key Components

#### Data Layer

- `ServiceProvider` - Service provider model with ratings, location, and specializations
- `City` - City data with real GPS coordinates
- `IndianCitiesData` - Repository with 70+ cities
- `ServiceProvidersData` - Repository with 50+ verified providers

#### AI Layer

- `RunAnywhereSDK` - Intelligent query processing
- `QueryIntent` - Extracted intent from natural language
- `UserContext` - User preferences and context
- Natural language understanding for service types and price ranges

#### Service Layer

- `ServiceFinderService` - Core search and filter logic
- Location-based search with distance calculation
- Smart ranking based on multiple factors

#### Presentation Layer

- `ServiceFinderViewModel` - UI state management
- `MainScreen` - Hybrid chat + dashboard interface
- `UIComponents` - Reusable UI elements

### Location Services

- **Haversine Formula**: Accurate distance calculation between coordinates
- **GPS Integration**: Android location services
- **City Proximity**: Find nearest city from GPS coordinates

## Sample Providers

### Kota - Coaching Capital

- **Resonance Kota** - 4.7★, 2145 reviews, Premium
- **Vibrant Academy** - 4.6★, 1876 reviews, Premium
- **Motion IIT-JEE** - 4.5★, 1543 reviews, Standard
- **Fitness First Kota** - 4.3★, 342 reviews, Budget-Friendly

### Jaipur

- **Allen Career Institute** - 4.8★, 1250 reviews, Premium (Since 1988)
- **Gold's Gym Jaipur** - 4.6★, 567 reviews, Premium

### Major Cities

- **Delhi**: Cult.fit, Sri Chaitanya, Delhi Plumbing Experts
- **Mumbai**: FIITJEE, Talwalkars Gym, Mumbai Plumbing Services
- **Bangalore**: BYJU'S, Fitness One, Bangalore Plumbing Co.
- **Chennai**: BASE Educational Services, O2 Fitness
- **Hyderabad**: Narayana IIT Academy, The Gym
- **Pune**: Triumph IIT Academy, Snap Fitness

## How It Works

### 1. Location Selection

Users can:

- Select city from searchable list of 70+ cities
- Use GPS to auto-detect location
- Browse by state or popular cities

### 2. Search Options

#### Natural Language Search

```
"Find cheap IIT coaching in Kota"
"Best gyms near me"
"Affordable plumber in Jaipur"
"Premium coaching institutes"
```

#### Category Browse

- Select service type (Coaching, Gym, Plumber, etc.)
- Filter by price range (Budget, Standard, Premium)
- Sort by rating, distance, or relevance

### 3. AI Processing

The RunAnywhere SDK processes queries to:

- Extract service type
- Identify price preferences
- Detect location mentions
- Calculate confidence scores

### 4. Results Display

- **Provider Cards**: Name, rating, reviews, specializations
- **Distance Display**: Calculated from user location
- **Contact Options**: Call or visit website
- **Price Badges**: Visual price tier indicators

## Privacy & Performance

### RunAnywhere SDK Benefits

- **On-Device AI**: All processing happens locally
- **Private**: No data sent to cloud for AI inference
- **Fast**: Optimized for mobile performance
- **Offline Capable**: Core AI features work without internet

## Setup & Installation

### Prerequisites

- Android Studio Arctic Fox or later
- Kotlin 1.9+
- Android SDK 24+
- Target SDK 35

### Build Instructions

1. Clone the repository
2. Open in Android Studio
3. Sync Gradle dependencies
4. Run on emulator or physical device

### Dependencies

```gradle
// Core Android
implementation("androidx.core:core-ktx:1.17.0")
implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.9.4")

// Compose
implementation(platform("androidx.compose:compose-bom:2024.09.00"))
implementation("androidx.compose.ui:ui")
implementation("androidx.compose.material3:material3")
implementation("androidx.activity:activity-compose:1.11.0")

// Navigation
implementation("androidx.navigation:navigation-compose:2.7.7")

// Location Services
implementation("com.google.android.gms:play-services-location:21.2.0")

// Networking (for future API integration)
implementation("com.squareup.retrofit2:retrofit:2.9.0")
implementation("com.squareup.retrofit2:converter-gson:2.9.0")
```

## User Experience

### Welcome Message

```
🙏 Namaste! I'm your AI assistant for finding local service providers across India.

I can help you find:
• IIT-JEE & NEET coaching institutes
• Gyms & fitness centers
• Plumbers & repair services
• And many more services!

Just select your city or share your location to get started.
```

### Smart Suggestions

- Context-aware recommendations based on location
- Student-friendly options in coaching hubs like Kota
- Popular services in each city

## Future Enhancements

### Planned Features

- **Live Web Scraping**: Real-time data from multiple sources
- **API Integration**: Connect with booking platforms
- **User Reviews**: Community-driven ratings
- **Booking System**: Direct booking through app
- **Payment Integration**: Secure payment processing
- **Push Notifications**: Service availability alerts
- **Favorites**: Save preferred providers
- **Comparison View**: Side-by-side provider comparison

### API Integrations (Future)

- Google Places API
- JustDial API
- Sulekha API
- Urban Company API
- Custom web scraping for real-time data

## Contributing

Contributions are welcome! Please follow these guidelines:

1. Fork the repository
2. Create a feature branch
3. Add comprehensive tests
4. Submit pull request with detailed description

## License

This project is licensed under the MIT License.

## Contact & Support

For questions, issues, or feature requests, please open an issue on GitHub.

---

**Built with ❤️ for Indian communities**

*Helping students find coaching in Kota, families find plumbers in Jaipur, and everyone discover
quality services across India*
