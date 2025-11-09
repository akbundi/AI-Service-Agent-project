# AI Service Finder - RunAnywhere SDK Integration

A sophisticated mobile application that integrates the **RunAnywhere SDK** to provide intelligent,
private, and high-performance AI experiences for finding and booking local service providers.

---

## ⚠️ **TROUBLESHOOTING: Gradle Cache Corruption**

If you encounter this error:

```
Caused by: org.gradle.cache.internal.btree.CorruptedCacheException: 
Corrupted IndexBlock found in cache 'file-access.bin'
```

**Quick Fix (Windows):**

```cmd
quick-fix.bat
```

Or run manually:

```cmd
gradlew.bat --stop
rmdir /s /q "%USERPROFILE%\.gradle\caches\journal-1"
```

**See [`GRADLE_CACHE_FIX.md`](GRADLE_CACHE_FIX.md) for detailed instructions and automated scripts.
**

## 🌟 Overview

This hybrid mobile app combines an AI-powered chatbot interface with a traditional dashboard to help
users discover plumbers, tutors, gyms, repair professionals, and more. The app leverages the
RunAnywhere SDK for on-device AI processing, ensuring privacy and speed.

## ✨ Key Features

### 🤖 AI-Powered Chatbot

- **Natural Language Understanding**: Process user queries using RunAnywhere SDK's AI capabilities
- **Intelligent Intent Recognition**: Automatically extracts service type, location, and price
  preferences
- **Contextual Conversations**: Maintains conversation history for better recommendations
- **Smart Suggestions**: Provides relevant follow-up questions and options

### 📊 Interactive Dashboard

- **Category Browse**: Quick access to popular service categories
- **Price Level Filters**: Budget (💰), Moderate (💰💰), Premium (💰💰💰) options
- **Grid View**: Beautiful card-based layout with images and ratings
- **Real-time Results**: Dynamic filtering and search capabilities

### 🎯 Intelligent Recommendations

- **AI-Powered Ranking**: Uses RunAnywhere SDK to rank providers based on:
    - User preferences and query intent
    - Rating and review count
    - Distance from user location
    - Price level matching
    - Verification status

### 🔒 Privacy-First Architecture

- **Local Processing**: AI runs on-device via RunAnywhere SDK
- **No Data Sharing**: Your location and preferences stay private
- **Secure**: End-to-end encryption for sensitive data

### 📍 Location Services

- **GPS Integration**: Automatic location detection
- **Manual Entry**: Option to enter address manually
- **Privacy Controls**: Transparent about location usage

### 🔍 Multi-Source Data Integration

- **Mock API Data**: Simulates integration with Yelp, Google Places, etc.
- **Web Scraping Support**: Structure for live data extraction (JSoup)
- **Extensible Architecture**: Easy to add new data sources

### 📱 Service Provider Details

- **Comprehensive Profiles**: Ratings, reviews, services, contact info
- **Verified Badges**: Trust indicators for quality providers
- **Direct Booking**: In-app booking with custom notes
- **One-Click Actions**: Call or message providers directly

## 🏗️ Architecture

### Technology Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose (Material Design 3)
- **Architecture**: MVVM (Model-View-ViewModel)
- **AI Engine**: RunAnywhere SDK
- **Navigation**: Jetpack Navigation Compose
- **Async Processing**: Kotlin Coroutines & Flow
- **Image Loading**: Coil
- **Networking**: Retrofit + OkHttp
- **Web Scraping**: JSoup
- **Location Services**: Google Play Services

### Project Structure

```
app/src/main/java/com/example/myapplication/
├── data/
│   ├── models/              # Data classes (ServiceProvider, Location, etc.)
│   ├── api/                 # API clients and SDK integration
│   │   ├── RunAnywhereSDK.kt   # AI SDK wrapper
│   │   └── ServiceApiClient.kt # Data source integration
│   └── repository/          # Repository layer
│       └── ServiceRepository.kt
├── ui/
│   ├── screens/             # Composable screens
│   │   ├── ChatScreen.kt         # AI chatbot interface
│   │   ├── DashboardScreen.kt    # Browse & filter interface
│   │   ├── LocationScreen.kt     # Location setup
│   │   └── ProviderDetailsScreen.kt
│   ├── viewmodel/           # ViewModels
│   │   └── ServiceViewModel.kt
│   └── theme/              # UI theming
└── MainActivity.kt         # App entry point
```

## 🚀 Getting Started

### Prerequisites

- Android Studio Hedgehog or later
- JDK 11 or higher
- Android SDK 24+ (Android 7.0 Nougat or higher)
- Gradle 8.13+

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd mystudio
   ```

2. **Open in Android Studio**
    - Open Android Studio
    - Select "Open an Existing Project"
    - Navigate to the project directory

3. **Sync Gradle**
    - Android Studio will automatically sync Gradle
    - Wait for dependencies to download

4. **Run the app**
    - Connect an Android device or start an emulator
    - Click the "Run" button or press Shift + F10

### Configuration

#### RunAnywhere SDK Setup

1. Obtain your RunAnywhere SDK API key from [RunAnywhere Dashboard](https://runanywhere.com)
2. Open `app/src/main/java/com/example/myapplication/data/api/RunAnywhereSDK.kt`
3. Replace the placeholder API key:
   ```kotlin
   private val apiKey = "your_runanywhere_api_key_here"
   ```

## 💡 How It Works

### AI Query Processing Flow

1. **User Input**: User types natural language query (e.g., "Find cheap plumbers near me")

2. **RunAnywhere SDK Processing**:
    - Extracts intent (search, booking, comparison)
    - Identifies service type (plumber, tutor, gym, etc.)
    - Determines price preference (cheap, mediocre, premium)
    - Analyzes conversation context

3. **Data Retrieval**:
    - Searches multiple data sources
    - Applies filters based on AI insights
    - Retrieves relevant service providers

4. **AI Ranking**:
    - Scores providers using intelligent algorithm
    - Considers rating, distance, price match, verification
    - Returns top recommendations

5. **Smart Response**:
    - Generates natural language response
    - Presents provider cards inline
    - Suggests follow-up actions

## 📖 Usage Examples

### Natural Language Queries

The AI chatbot understands various query formats:

**Finding Services:**

- "I need a plumber"
- "Find gyms near me"
- "Looking for affordable tutors"
- "Show me premium electricians"

**With Price Preferences:**

- "Cheap car repair shops"
- "Budget-friendly cleaning services"
- "High-end personal trainers"

**Comparisons:**

- "Compare the top 3 plumbers"
- "What's the difference between these gyms?"

**Booking:**

- "Book an appointment with [provider name]"
- "Schedule a service for next week"

## 🎨 UI Components

### Chat Interface

- Message bubbles with timestamps
- Inline provider cards
- Suggestion chips for quick actions
- Auto-scrolling conversation view

### Dashboard

- Horizontal scrolling category cards
- Price level filter chips
- Grid layout for provider cards
- Loading and error states

### Provider Details

- Hero image
- Rating statistics
- Service list
- Contact information
- Booking dialog

## 🔧 Customization

### Adding New Service Categories

Edit `ServiceRepository.kt`:

```kotlin
ServiceCategory(
    id = "your_service",
    name = "🎯 Your Service",
    description = "Service description"
)
```

### Modifying AI Behavior

Edit `RunAnywhereSDK.kt`:

- Update `serviceKeywords` for new service types
- Modify `extractIntent()` for new intents
- Adjust scoring in `generateRecommendations()`

### Adding Data Sources

Edit `ServiceApiClient.kt`:

- Implement new API client methods
- Add web scraping logic (follow robots.txt)
- Extend mock data generators

## 🔐 Privacy & Security

- **On-Device AI**: RunAnywhere SDK processes data locally
- **No Tracking**: No third-party analytics or tracking
- **Secure Storage**: Encrypted local data storage
- **Permissions**: Only requests necessary permissions
- **Transparency**: Clear privacy policy and data usage

## 🧪 Testing

### Manual Testing

1. Test location setup (GPS and manual)
2. Try various natural language queries
3. Filter by price levels
4. Browse categories
5. View provider details
6. Test booking flow

### Key Test Scenarios

- Empty state handling
- No results state
- Network error handling
- Permission denied states
- Long provider lists
- Edge cases in search queries

## 📱 Supported Platforms

- **Android**: 7.0 (API 24) and above
- **Devices**: Phones and tablets
- **Orientations**: Portrait and landscape

## 🤝 Contributing

Contributions are welcome! Areas for improvement:

- Real API integrations (Yelp, Google Places)
- Enhanced AI models
- More service categories
- User authentication
- Booking confirmation system
- Review and rating system
- Push notifications
- Offline mode

## 📄 License

This project is created for demonstration purposes. Please ensure you have appropriate licenses for:

- RunAnywhere SDK
- Any third-party APIs you integrate
- Image assets and icons

## 🙏 Acknowledgments

- **RunAnywhere SDK**: For providing powerful on-device AI capabilities
- **Material Design 3**: For beautiful, modern UI components
- **Jetpack Compose**: For declarative UI framework
- **Community**: For various open-source libraries

## 📞 Support

For issues, questions, or contributions:

- Create an issue on GitHub
- Check existing documentation
- Review the code comments

## 🚀 Future Enhancements

- [ ] Real-time availability checking
- [ ] In-app messaging with providers
- [ ] Payment integration
- [ ] User profiles and history
- [ ] Favorites and bookmarks
- [ ] Multi-language support
- [ ] Voice input support
- [ ] Maps integration
- [ ] Social sharing
- [ ] Review system

---

**Built with ❤️ using RunAnywhere SDK for intelligent, private, and high-performance AI experiences
**
