# Quick Start Guide

Get the AI Service Finder app running in **5 minutes**!

## 🚀 Prerequisites

- ✅ Android Studio Hedgehog (2023.1.1) or later
- ✅ JDK 11 or higher
- ✅ Android device/emulator running Android 7.0+ (API 24+)

## 📦 Installation Steps

### Step 1: Clone & Open Project

```bash
# Navigate to your workspace
cd D:/mystudio

# Open Android Studio
# File → Open → Select the mystudio folder
```

### Step 2: Sync Dependencies

Android Studio will automatically detect the Gradle project and prompt you to sync. Click **"Sync
Now"**.

If sync doesn't start automatically:

- Click the "Sync Project with Gradle Files" button (🐘 icon in toolbar)
- Or: File → Sync Project with Gradle Files

**Wait for sync to complete** (~2-3 minutes on first run)

### Step 3: Run the App

1. **Connect a Device** or **Start Emulator**
    - Physical device: Enable USB debugging in Developer Options
    - Emulator: Tools → Device Manager → Create or start a device

2. **Click Run** (▶️ green play button) or press `Shift + F10`

3. **Select Device** from the dialog and click OK

The app will build and install automatically!

## 🎉 First Launch

When the app launches for the first time:

1. **Location Screen** appears first
    - Click "Use Current Location" (grants permission if needed)
    - Or click "Enter Address Manually" and type a city

2. **Chat Interface** loads
    - You'll see a welcome message from the AI assistant
    - Try typing: "Find cheap plumbers near me"

3. **Switch to Dashboard**
    - Tap the "Dashboard" icon at the bottom
    - Browse categories and apply filters

## 🧪 Try These Queries

In the AI Chat, try these example queries:

### Basic Searches

```
Find plumbers near me
Show me gyms in my area
I need a tutor
```

### With Price Preferences

```
Show me cheap electricians
Find premium gyms
Affordable car repair shops
```

### Specific Requests

```
I need an emergency plumber
Show me 5-star rated tutors
Find certified mechanics nearby
```

## 🛠️ Configuration (Optional)

### RunAnywhere SDK API Key

By default, the app uses a demo implementation. To enable real RunAnywhere SDK features:

1. Get your API key from [RunAnywhere Dashboard](https://runanywhere.com)

2. Open `app/src/main/java/com/example/myapplication/data/api/RunAnywhereSDK.kt`

3. Replace the API key:

```kotlin
private val apiKey = "your_actual_api_key_here"
```

### External API Integration

To connect real data sources (Yelp, Google Places):

1. Follow the detailed instructions in `API_INTEGRATION_GUIDE.md`

2. Add your API keys to `local.properties`:

```properties
YELP_API_KEY=your_yelp_key
GOOGLE_API_KEY=your_google_key
```

3. Uncomment the real API calls in `ServiceApiClient.kt`

## 📱 App Features

### 🤖 AI Chat Tab

- **Natural language queries** - Talk naturally, AI understands
- **Inline provider cards** - See results directly in chat
- **Smart suggestions** - Quick action buttons
- **Conversation memory** - Context-aware responses

### 📊 Dashboard Tab

- **Category browsing** - Popular service types
- **Price filters** - Budget, Moderate, Premium
- **Grid view** - Visual provider cards
- **Detailed info** - Ratings, distance, verification

### 📍 Provider Details

- **Full profiles** - Complete information
- **Photos** - Provider images
- **Services list** - What they offer
- **Contact options** - Call or book directly

## 🐛 Troubleshooting

### Build Issues

**Problem**: Gradle sync fails

```bash
# Solution: Clean and rebuild
./gradlew clean
./gradlew build --refresh-dependencies
```

**Problem**: Kotlin version mismatch

- Ensure you're using Android Studio Hedgehog or later
- Update Kotlin plugin: Tools → Kotlin → Configure Kotlin Plugin Updates

### Runtime Issues

**Problem**: App crashes on launch

- Check minimum SDK is 24 (Android 7.0)
- Clear app data: Settings → Apps → My Application → Clear Data

**Problem**: Location not working

- Ensure location permissions granted
- Enable GPS on device/emulator
- Enter address manually as fallback

**Problem**: No providers showing

- Check internet connection (for images)
- Mock data should still work offline
- Try different search queries

### Emulator Issues

**Problem**: Emulator is slow

- Enable Hardware Acceleration (HAXM/Hyper-V)
- Use x86 system image (not ARM)
- Allocate more RAM: AVD Manager → Edit → Advanced Settings

**Problem**: Location not available in emulator

- Use Extended Controls (… button in emulator)
- Navigate to Location tab
- Set custom GPS coordinates

## 📚 Next Steps

### Learn the Codebase

1. **Read** `README.md` for comprehensive overview
2. **Study** `RUNANYWHERE_SDK_GUIDE.md` for AI customization
3. **Explore** `API_INTEGRATION_GUIDE.md` for real data sources

### Customize the App

1. **Add categories**: Edit `ServiceRepository.kt`
2. **Modify AI behavior**: Update `RunAnywhereSDK.kt`
3. **Change UI theme**: Edit files in `ui/theme/`
4. **Add features**: Follow MVVM pattern

### Connect Real APIs

1. Get API keys (Yelp, Google Places)
2. Implement API clients (see guides)
3. Test with real data
4. Deploy to production

## 🎨 UI Customization

### Change Colors

Edit `app/src/main/java/com/example/myapplication/ui/theme/Color.kt`:

```kotlin
val Purple80 = Color(0xFFD0BCFF)  // Change primary color
val PurpleGrey80 = Color(0xFFCCC2DC)  // Change secondary color
```

### Modify Categories

Edit `ServiceRepository.kt`:

```kotlin
ServiceCategory(
    id = "your_service",
    name = "🎯 Your Service Name",
    description = "Your description"
)
```

### Update Welcome Message

Edit `ServiceViewModel.kt` in the `init` block:

```kotlin
addBotMessage("Your custom welcome message here")
```

## 🧑‍💻 Development Tips

### Hot Reload

- Jetpack Compose supports Live Edit
- Make UI changes and see them instantly
- Look for ⚡ icon in code editor

### Debugging

- Add breakpoints by clicking line numbers
- Use "Debug" (🐞) instead of Run
- Inspect StateFlow values in debugger

### Logs

- View Logcat: View → Tool Windows → Logcat
- Filter by tag: "ServiceApiClient", "RunAnywhereSDK"
- Search for errors: Use "Error" filter

### Code Quality

```bash
# Run lint checks
./gradlew lint

# Run tests
./gradlew test

# Generate APK
./gradlew assembleDebug
```

## 📦 Build Variants

### Debug Build (Default)

- Includes logging
- Faster build times
- For development

### Release Build

```bash
# Build release APK
./gradlew assembleRelease

# Find APK at:
# app/build/outputs/apk/release/app-release-unsigned.apk
```

For signing and publishing, see Android documentation.

## 🆘 Getting Help

### Resources

- **README.md** - Full documentation
- **API_INTEGRATION_GUIDE.md** - API setup
- **RUNANYWHERE_SDK_GUIDE.md** - AI customization
- **Code comments** - Inline documentation

### Common Questions

**Q: Can I use this in production?**
A: Yes, but obtain proper API keys and licenses first.

**Q: Does it work offline?**
A: AI processing works offline. Provider search needs internet for real APIs.

**Q: How do I add more service categories?**
A: Edit the `getPopularCategories()` method in `ServiceRepository.kt`.

**Q: Can I customize the AI responses?**
A: Absolutely! Edit response templates in `RunAnywhereSDK.kt`.

**Q: How do I change the app name?**
A: Edit `app/src/main/res/values/strings.xml`.

## 🎯 Success Checklist

- [ ] App builds without errors
- [ ] App runs on device/emulator
- [ ] Location screen appears
- [ ] Can set location (GPS or manual)
- [ ] Chat interface loads with welcome message
- [ ] Can send messages and get AI responses
- [ ] Provider cards appear in chat
- [ ] Dashboard tab shows categories
- [ ] Can filter by price level
- [ ] Provider details screen works
- [ ] Images load (requires internet)

## 🎉 You're All Set!

Congratulations! Your AI Service Finder app is up and running.

**Next Steps:**

1. Explore all features
2. Try different queries
3. Check out customization guides
4. Add your own enhancements
5. Connect real APIs when ready

**Happy Coding! 🚀**

---

Need more help? Check the other documentation files or review the inline code comments.
