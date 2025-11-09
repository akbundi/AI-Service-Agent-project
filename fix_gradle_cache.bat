@echo off
echo ========================================
echo Fixing Corrupted Gradle Cache
echo ========================================
echo.

echo Step 1: Stopping all Gradle daemons...
call gradlew.bat --stop
timeout /t 2 /nobreak >nul

echo.
echo Step 2: Cleaning project build directories...
call gradlew.bat clean
timeout /t 2 /nobreak >nul

echo.
echo Step 3: Deleting corrupted Gradle caches...
rmdir /s /q "%USERPROFILE%\.gradle\caches\journal-1" 2>nul
rmdir /s /q "%USERPROFILE%\.gradle\caches\transforms-3" 2>nul
rmdir /s /q "%USERPROFILE%\.gradle\caches\build-cache-1" 2>nul

echo.
echo Step 4: Cleaning local project caches...
rmdir /s /q .gradle 2>nul
rmdir /s /q app\build 2>nul
rmdir /s /q build 2>nul

echo.
echo Step 5: Rebuilding project...
call gradlew.bat clean build --refresh-dependencies

echo.
echo ========================================
echo Cache cleanup complete!
echo ========================================
echo.
echo If the issue persists, you can manually delete the entire Gradle cache:
echo %USERPROFILE%\.gradle\caches
echo.
pause
