@echo off
echo ========================================
echo Quick Fix for Corrupted Gradle Cache
echo ========================================
echo.

echo Stopping Gradle daemons...
gradlew.bat --stop

echo.
echo Deleting corrupted cache file...
del /f /q "%USERPROFILE%\.gradle\caches\journal-1\file-access.bin" 2>nul
rmdir /s /q "%USERPROFILE%\.gradle\caches\journal-1" 2>nul

echo.
echo Cleaning project...
rmdir /s /q .gradle 2>nul
rmdir /s /q app\build 2>nul
rmdir /s /q build 2>nul

echo.
echo Done! Now try building your project again.
echo.
pause
