# Gradle Cache Corruption Fix

## Problem

You're experiencing this error:

```
Caused by: org.gradle.cache.internal.btree.CorruptedCacheException: 
Corrupted IndexBlock 230406 found in cache 'C:\Users\HPWORLD\.gradle\caches\journal-1\file-access.bin'
```

This occurs when Gradle's cache files get corrupted, usually due to:

- Interrupted builds
- System crashes
- Disk errors
- Multiple Gradle instances running simultaneously

## Solutions

### ✅ **Option 1: Automated Fix (Recommended)**

Run one of the provided scripts:

#### Using PowerShell (Recommended):

```powershell
.\Fix-GradleCache.ps1
```

#### Using Command Prompt:

```cmd
fix_gradle_cache_simple.bat
```

### ✅ **Option 2: Manual Fix**

Follow these steps in PowerShell or Command Prompt:

1. **Stop all Gradle daemons:**
   ```powershell
   .\gradlew.bat --stop
   ```

2. **Delete the corrupted cache:**
   ```powershell
   Remove-Item -Path "$env:USERPROFILE\.gradle\caches\journal-1" -Recurse -Force
   ```

   Or in Command Prompt:
   ```cmd
   rmdir /s /q "%USERPROFILE%\.gradle\caches\journal-1"
   ```

3. **Clean your project:**
   ```powershell
   .\gradlew.bat clean
   ```

4. **Delete local build directories:**
   ```powershell
   Remove-Item -Path ".gradle", "app\build", "build" -Recurse -Force -ErrorAction SilentlyContinue
   ```

5. **Rebuild with fresh dependencies:**
   ```powershell
   .\gradlew.bat build --refresh-dependencies
   ```

### ✅ **Option 3: Nuclear Option (If above fails)**

Delete the entire Gradle cache:

```powershell
# Stop Gradle
.\gradlew.bat --stop

# Delete entire cache
Remove-Item -Path "$env:USERPROFILE\.gradle\caches" -Recurse -Force

# Clean project
.\gradlew.bat clean

# Rebuild
.\gradlew.bat build --refresh-dependencies
```

## Prevention Tips

1. **Always stop Gradle daemon before shutting down:**
   ```powershell
   .\gradlew.bat --stop
   ```

2. **Don't force-close Android Studio or terminals during builds**

3. **Ensure sufficient disk space**

4. **Run periodic cache cleanup:**
   ```powershell
   .\gradlew.bat cleanBuildCache
   ```

5. **Update Gradle regularly** (currently using 8.13.0)

## Verification

After applying the fix, verify it works:

```powershell
.\gradlew.bat --version
.\gradlew.bat tasks
```

If these commands run without errors, your cache is fixed!

## Still Having Issues?

If the problem persists:

1. Check disk space: `Get-PSDrive C`
2. Run disk check: `chkdsk C: /F`
3. Update Android Studio and Gradle
4. Check antivirus isn't blocking Gradle
5. Try running as Administrator

## Quick Reference

### File Locations

- **Corrupted file:** `C:\Users\HPWORLD\.gradle\caches\journal-1\file-access.bin`
- **Gradle home:** `%USERPROFILE%\.gradle`
- **Project cache:** `D:\mystudio\.gradle`

### Scripts in This Project

- `Fix-GradleCache.ps1` - Full automated fix (PowerShell)
- `fix_gradle_cache.bat` - Full automated fix (Batch)
- `fix_gradle_cache_simple.bat` - Quick fix (Batch)

## Additional Commands

### Check Gradle status:

```powershell
.\gradlew.bat --status
```

### Force dependency refresh:

```powershell
.\gradlew.bat build --refresh-dependencies
```

### Clean everything:

```powershell
.\gradlew.bat clean cleanBuildCache
```

### Offline build (uses cache):

```powershell
.\gradlew.bat build --offline
```

---

**Need more help?** Check
the [Gradle documentation](https://docs.gradle.org/current/userguide/troubleshooting.html)
