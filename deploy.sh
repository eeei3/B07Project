#!/bin/bash

# EcoTracker Deployment Script
# This script builds and prepares the app for deployment

set -e  # Exit on any error

echo "🌱 EcoTracker Deployment Script"
echo "================================"

# Check if Java is available
if ! command -v java &> /dev/null; then
    echo "❌ Java not found. Please install Java 11 or higher."
    echo "   On macOS: brew install openjdk@17"
    exit 1
fi

# Set Java path if needed
export PATH="/opt/homebrew/opt/openjdk@17/bin:$PATH"

echo "✅ Java version:"
java -version

# Clean previous builds
echo "🧹 Cleaning previous builds..."
./gradlew clean

# Build debug version
echo "🔨 Building debug version..."
./gradlew assembleDebug

# Build release version
echo "🔨 Building release version..."
./gradlew assembleRelease

# Copy APK to root directory
echo "📱 Copying APK files..."
cp app/build/outputs/apk/release/app-release.apk ./app-release.apk
cp app/build/outputs/apk/debug/app-debug.apk ./app-debug.apk

# Get APK info
echo "📊 APK Information:"
echo "Release APK: $(ls -lh app-release.apk | awk '{print $5}')"
echo "Debug APK: $(ls -lh app-debug.apk | awk '{print $5}')"

echo ""
echo "✅ Build completed successfully!"
echo ""
echo "📁 Generated files:"
echo "   - app-release.apk (Production ready)"
echo "   - app-debug.apk (Development/testing)"
echo "   - index.html (Download page)"
echo ""
echo "🚀 Next steps:"
echo "   1. Test the release APK on a device"
echo "   2. Upload to Google Play Console or distribute directly"
echo "   3. Check DEPLOYMENT.md for detailed instructions"
echo ""
echo "🌱 Your EcoTracker app is ready to help users reduce their carbon footprint!"
