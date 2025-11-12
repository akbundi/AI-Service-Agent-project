# Java 17 Setup Guide

## ⚠️ Issue

The build failed because Android Gradle Plugin 8.13+ requires **Java 17** or higher.
You are currently using **Java 15** located at: `C:\Program Files\Java\jdk-15.0.2`

## ✅ Solution: Install Java 17

### Option 1: Download from Oracle (Recommended)

1. **Download JDK 17**:
    - Visit: https://www.oracle.com/java/technologies/downloads/#java17
    - Choose: **Windows x64 Installer** (jdk-17_windows-x64_bin.exe)

2. **Install JDK 17**:
    - Run the installer
    - Default location: `C:\Program Files\Java\jdk-17`
    - Complete the installation

3. **Set JAVA_HOME Environment Variable**:
   ```
   - Open "Environment Variables" (Search in Windows Start)
   - Under "System Variables", click "New"
   - Variable name: JAVA_HOME
   - Variable value: C:\Program Files\Java\jdk-17
   - Click OK
   ```

4. **Update PATH**:
   ```
   - In "System Variables", find and edit "Path"
   - Add new entry: %JAVA_HOME%\bin
   - Move it to the top of the list
   - Click OK
   ```

5. **Verify Installation**:
   ```powershell
   java -version
   # Should show: java version "17.0.x"
   ```

### Option 2: Use Android Studio's Embedded JDK

Android Studio comes with JDK 17. You can configure Gradle to use it:

1. **Find Android Studio's JDK**:
    - Usually located at: `C:\Program Files\Android\Android Studio\jbr`

2. **Update gradle.properties**:
   Add this line (adjust path if needed):
   ```properties
   org.gradle.java.home=C:\\Program Files\\Android\\Android Studio\\jbr
   ```

3. **Or in Android Studio**:
    - File → Settings → Build, Execution, Deployment → Build Tools → Gradle
    - Gradle JDK: Select "Embedded JDK (version 17)"

### Option 3: Install via Chocolatey (Package Manager)

If you have Chocolatey installed:

```powershell
choco install openjdk17
```

### Option 4: Download OpenJDK (Free Alternative)

1. Visit: https://adoptium.net/
2. Choose: **Temurin 17 (LTS)**
3. Download Windows x64 installer
4. Install and set JAVA_HOME as above

## 🔄 After Installing Java 17

1. **Restart PowerShell/Terminal**
2. **Verify Java version**:
   ```powershell
   java -version
   # Should show version 17.x.x
   ```

3. **Clean and Build**:
   ```powershell
   cd D:\MyApplication
   .\gradlew.bat --stop
   .\gradlew.bat clean build
   ```

4. **Or in Android Studio**:
    - File → Invalidate Caches → Invalidate and Restart
    - File → Sync Project with Gradle Files
    - Build → Rebuild Project
    - Run ▶️

## 🎯 Quick Fix (If You Don't Want to Install Java 17)

You can downgrade the Android Gradle Plugin to work with Java 15:

Edit `gradle/libs.versions.toml`:

```toml
[versions]
agp = "8.1.4"  # Change from 8.13.1 to 8.1.4
```

Then run:

```powershell
.\gradlew.bat --stop
.\gradlew.bat clean build
```

**Note**: This is not recommended as you'll miss out on latest features and bug fixes.

## ✅ Recommended Solution

**Install Java 17** (Option 1 or 2 above) - This is the best long-term solution and is required for
modern Android development.

## 📞 Troubleshooting

### After Installing, Still Shows Java 15?

1. **Check JAVA_HOME**:
   ```powershell
   echo $env:JAVA_HOME
   # Should show: C:\Program Files\Java\jdk-17
   ```

2. **Check PATH**:
   ```powershell
   echo $env:PATH
   # Should include: C:\Program Files\Java\jdk-17\bin
   ```

3. **Restart Everything**:
    - Close all PowerShell/Command Prompt windows
    - Restart Android Studio
    - Try again

### Still Having Issues?

Edit `gradle.properties` and explicitly set Java path:

```properties
org.gradle.java.home=C:\\Program Files\\Java\\jdk-17
```

Or point to Android Studio's JDK:

```properties
org.gradle.java.home=C:\\Program Files\\Android\\Android Studio\\jbr
```

---

**After fixing Java version, the app should build successfully!** 🚀
