# Quick Start Guide - Service Finder AI

## 🚀 Get Up and Running in 5 Minutes

### Prerequisites

Before you begin, make sure you have:

- ✅ **Android Studio** (Arctic Fox or later)
- ✅ **JDK 11** or higher
- ✅ **Android SDK** (API 24+)
- ✅ An **Android device** or **emulator**

### Step 1: Clone or Open the Project

```bash
# If cloning from repository
git clone <repository-url>
cd MyApplication

# Or simply open the existing project folder in Android Studio
# File → Open → Navigate to D:/MyApplication
```

### Step 2: Sync Gradle

1. Android Studio will automatically prompt to sync Gradle
2. Click **"Sync Now"** when the banner appears
3. Wait for dependencies to download (~2-3 minutes)

### Step 3: Run the App

**Option A: Using Android Emulator**

```
1. Click "Device Manager" in Android Studio
2. Create a new virtual device (if needed)
   - Choose Pixel 5 or any modern device
   - Select Android API 33 or higher
3. Click Run (▶️) or press Shift+F10
4. Select your emulator from the list
5. Wait for the app to build and deploy
```

**Option B: Using Physical Device**

```
1. Enable Developer Options on your Android phone:
   - Go to Settings → About Phone
   - Tap "Build Number" 7 times
2. Enable USB Debugging:
   - Settings → Developer Options → USB Debugging
3. Connect phone via USB
4. Click Run (▶️) in Android Studio
5. Select your device from the list
```

### Step 4: Explore the App

Once the app launches, you'll see:

1. **Welcome Screen**:
    - "🙏 Namaste!" greeting
    - Location selection prompt

2. **Try These Actions**:
    - Tap **"Select City"** → Choose "Kota"
    - Type: **"Find cheap IIT coaching"**
    - See AI-powered results!

3. **Switch to Dashboard Tab**:
    - Browse categories
    - Select price ranges
    - Explore 70+ cities

## 📱 Sample Queries to Try

### Coaching Searches

```
"Find IIT coaching in Kota"
"Cheap NEET coaching"
"Best coaching institutes"
"Allen in Jaipur"
```

### Service Searches

```
"Gyms near me"
"Affordable plumber"
"Premium fitness center"
"Plumber in Delhi"
```

### With Price Preferences

```
"Find cheap gyms in Mumbai"
"Premium coaching in Bangalore"
"Budget-friendly tutors"
```

## 🏙️ Cities to Explore

### Rajasthan (Complete Coverage)

- **Kota** - Coaching capital with Resonance, Vibrant, Motion
- **Jaipur** - Allen Career Institute, Gold's Gym
- **Ajmer** - Career Point coaching
- **Bundi** - Local coaching centers

### Major Metros

- **Delhi** - Cult.fit, Sri Chaitanya
- **Mumbai** - FIITJEE, Talwalkars
- **Bangalore** - BYJU'S, Fitness One
- **Chennai** - BASE Educational, O2 Fitness

## 🎯 Key Features to Test

### 1. Location Selection

```
1. Click "Select City" button
2. Search for "Kota"
3. Click on Kota
4. See location updated in top bar
```

### 2. Natural Language Search

```
1. Go to Chat tab
2. Type: "Find cheap IIT coaching in Kota"
3. Press Send
4. See AI extract intent and show results
```

### 3. Category Browsing

```
1. Switch to Dashboard tab
2. Click "Coaching" category
3. See all coaching institutes
4. Try other categories
```

### 4. Price Filtering

```
1. In Dashboard tab
2. Click "Budget-Friendly" price card
3. See affordable options
4. Try "Premium" for top-tier services
```

### 5. Provider Details

```
1. Search for any service
2. View provider cards with:
   - Ratings & reviews
   - Specializations
   - Contact options
   - Distance (if location set)
```

## 🔧 Project Structure Quick Reference

### Main Files

```
app/src/main/java/com/example/myapplication/
│
├── MainActivity.kt                    # App entry point
│
├── data/
│   ├── models/ServiceProvider.kt      # Data models
│   └── repository/
│       ├── IndianCitiesData.kt       # 70+ cities
│       └── ServiceProvidersData.kt   # 50+ providers
│
├── ai/
│   └── RunAnywhereSDK.kt             # AI processing
│
├── service/
│   └── ServiceFinderService.kt       # Search logic
│
├── viewmodel/
│   └── ServiceFinderViewModel.kt     # UI state
│
└── ui/
    ├── screens/MainScreen.kt         # Main interface
    └── components/UIComponents.kt    # Reusable UI
```

### Key Configuration Files

```
app/build.gradle.kts              # Dependencies
app/src/main/AndroidManifest.xml  # Permissions
app/src/main/res/values/strings.xml  # App name
```

## 🎨 Customization Quick Tips

### Add a New City

Edit `IndianCitiesData.kt`:

```kotlin
City("bangalore", "Bangalore", "Karnataka", 12.9716, 77.5946, true)
```

### Add a New Provider

Edit `ServiceProvidersData.kt`:

```kotlin
ServiceProvider(
    id = "unique_id",
    name = "Provider Name",
    type = ServiceType.COACHING,
    rating = 4.5,
    // ... other fields
)
```

### Change App Name

Edit `app/src/main/res/values/strings.xml`:

```xml
<string name="app_name">Your App Name</string>
```

### Modify Welcome Message

Edit `ServiceFinderViewModel.kt` in the `initializeApp()` function

## 🐛 Troubleshooting

### Build Errors

```bash
# Clean and rebuild
./gradlew clean build

# Or in Android Studio:
Build → Clean Project
Build → Rebuild Project
```

### Gradle Sync Issues

```
1. File → Invalidate Caches → Invalidate and Restart
2. Delete .gradle folder in project root
3. Sync again
```

### Emulator Won't Start

```
1. Tools → Device Manager
2. Delete existing emulator
3. Create new one with latest system image
4. Restart Android Studio
```

### App Crashes on Launch

```
1. Check logcat for errors
2. Verify minimum SDK is 24+
3. Ensure all dependencies synced
4. Try on different device/emulator
```

## 📊 Performance Expectations

### Build Times

- **Initial build**: ~3-5 minutes
- **Incremental builds**: ~30-60 seconds
- **Compose hot reload**: ~2-5 seconds

### Runtime Performance

- **App startup**: <1 second
- **AI query processing**: <300ms
- **Search results**: Instant (in-memory)
- **City selector**: <100ms

## 🎓 Learning Resources

### Understanding the Code

**Start with these files in order:**

1. `MainActivity.kt` - Entry point
2. `MainScreen.kt` - UI structure
3. `ServiceFinderViewModel.kt` - Business logic
4. `ServiceProvidersData.kt` - Data layer
5. `RunAnywhereSDK.kt` - AI integration

### Key Concepts Used

- **Jetpack Compose**: Modern UI toolkit
- **MVVM Pattern**: Separation of concerns
- **Kotlin Flows**: Reactive data streams
- **Material Design 3**: Design system
- **State Management**: UI state handling

## 🚀 Next Steps

### Extend the App

1. **Add More Cities**: Update `IndianCitiesData.kt`
2. **Add More Providers**: Update `ServiceProvidersData.kt`
3. **New Service Types**: Add to `ServiceType` enum
4. **API Integration**: Implement in `ServiceFinderService`
5. **GPS Location**: Add in location banner handler

### Deploy to Device

```
1. Build → Generate Signed Bundle / APK
2. Choose APK
3. Create or use existing keystore
4. Select release variant
5. Transfer APK to device
6. Install and test
```

## ✅ Verification Checklist

After setup, verify these work:

- ✅ App launches without errors
- ✅ Welcome message appears
- ✅ City selector opens
- ✅ Can select a city
- ✅ Can type in search box
- ✅ Search returns results
- ✅ Tabs switch properly
- ✅ Provider cards display correctly
- ✅ No crashes or ANRs

## 💡 Pro Tips

1. **Use Hot Reload**:
    - Make UI changes
    - Press Ctrl+Shift+R (Windows) or Cmd+Shift+R (Mac)
    - See changes instantly!

2. **Logcat Filtering**:
   ```
   Tag: ServiceFinderViewModel
   Level: Debug
   ```

3. **Database Inspection**:
    - Set breakpoints in Data repositories
    - Inspect provider/city lists

4. **UI Preview**:
    - Open any `@Composable` function
    - Click "Split" or "Design" view
    - See UI without running app

## 📞 Need Help?

- Check `README.md` for detailed documentation
- Review `PROJECT_SUMMARY.md` for architecture details
- See `FEATURES.md` for complete feature list
- Open an issue on GitHub

## 🎉 You're Ready!

The app is now running with:

- ✅ 70+ Indian cities
- ✅ 50+ service providers
- ✅ AI-powered search
- ✅ Beautiful Material Design 3 UI
- ✅ Hybrid chat + dashboard interface

**Start exploring and customizing!** 🚀

---

*From zero to running app in under 5 minutes!*
