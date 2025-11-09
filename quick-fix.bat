@echo off
echo Fixing Gradle cache corruption...
gradlew.bat --stop
rmdir /s /q "%USERPROFILE%\.gradle\caches\journal-1"
rmdir /s /q .gradle
echo Done! Now run: gradlew.bat build
pause
