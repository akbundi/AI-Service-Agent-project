# PowerShell script to fix corrupted Gradle cache
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Fixing Corrupted Gradle Cache" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Step 1: Stop Gradle daemons
Write-Host "Step 1: Stopping all Gradle daemons..." -ForegroundColor Yellow
& .\gradlew.bat --stop
Start-Sleep -Seconds 2

# Step 2: Delete corrupted cache
Write-Host ""
Write-Host "Step 2: Deleting corrupted cache files..." -ForegroundColor Yellow
$gradleHome = "$env:USERPROFILE\.gradle"
$cachePaths = @(
    "$gradleHome\caches\journal-1",
    "$gradleHome\caches\transforms-3",
    "$gradleHome\caches\build-cache-1"
)

foreach ($path in $cachePaths) {
    if (Test-Path $path) {
        Write-Host "  Removing: $path" -ForegroundColor Gray
        Remove-Item -Path $path -Recurse -Force -ErrorAction SilentlyContinue
    }
}

# Step 3: Clean local project
Write-Host ""
Write-Host "Step 3: Cleaning local project caches..." -ForegroundColor Yellow
$localPaths = @(
    ".gradle",
    "app\build",
    "build"
)

foreach ($path in $localPaths) {
    if (Test-Path $path) {
        Write-Host "  Removing: $path" -ForegroundColor Gray
        Remove-Item -Path $path -Recurse -Force -ErrorAction SilentlyContinue
    }
}

# Step 4: Rebuild
Write-Host ""
Write-Host "Step 4: Rebuilding project with fresh dependencies..." -ForegroundColor Yellow
& .\gradlew.bat clean build --refresh-dependencies

Write-Host ""
Write-Host "========================================" -ForegroundColor Green
Write-Host "Cache cleanup complete!" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Green
Write-Host ""

# Completion message
Write-Host "If the issue persists, you can manually delete:" -ForegroundColor White
Write-Host "  $gradleHome\caches" -ForegroundColor Cyan
Write-Host ""
