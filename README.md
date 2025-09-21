# 🌱 EcoTracker - Carbon Footprint Tracking App

EcoTracker is an Android application that helps users monitor their environmental impact by tracking transportation, food, and shopping emissions. Make informed decisions to reduce your carbon footprint and live more sustainably.

## ✨ Features

- **📱 User Authentication**: Secure login/register with Firebase
- **🚗 Transportation Tracking**: Log car, bus, train, and flight emissions
- **🍽️ Food Emissions**: Track food-related carbon footprint
- **🛒 Shopping Activities**: Monitor purchase-related emissions
- **📅 Calendar Integration**: Track activities by date
- **📊 Data Visualization**: Charts and graphs for emissions data
- **🎯 Habits Management**: Set and track environmental goals
- **☁️ Real-time Sync**: Firebase integration for data backup

## 🚀 Quick Start

### Option 1: Download APK (Recommended)
1. Download the latest release: [**Download APK**](app-release.apk)
2. Enable "Install from unknown sources" on your Android device
3. Install and start tracking your carbon footprint!

### Option 2: Build from Source
```bash
# Clone the repository
git clone <your-repo-url>
cd B07Project

# Build the project
./gradlew assembleDebug

# Install on device
./gradlew installDebug
```

## 📱 System Requirements

- **Android Version**: 7.0 (API level 24) or higher
- **Storage**: 50MB free space
- **Internet**: Required for Firebase sync and authentication

## 🛠️ Technical Stack

- **Language**: Java
- **Platform**: Android (Native)
- **Database**: Firebase Realtime Database
- **Authentication**: Firebase Auth
- **Charts**: MPAndroidChart
- **UI**: Material Design Components

## 📋 App Structure

```
app/src/main/java/com/example/b07project/
├── EntryActivity.java          # Main entry point with login/register
├── EcoTrackerHomeActivity.java # Home screen with calendar
├── LogActivitiesActivity.java  # Activity logging interface
├── DetailPageActivity.java     # Activity details and charts
├── HabitsMenu.java            # Habits management
├── EcoGauge.java              # Eco gauge visualization
├── SurveyActivity.java        # Environmental survey
├── ResultsActivity.java       # Survey results
└── Firebase integration files
```

## 🔧 Development Setup

### Prerequisites
- Android Studio Arctic Fox or later
- Java 11 or higher
- Android SDK 34
- Firebase project setup

### Setup Steps
1. Clone the repository
2. Open in Android Studio
3. Configure Firebase:
   - Create Firebase project
   - Add Android app to project
   - Download `google-services.json`
   - Place in `app/` directory
4. Sync project with Gradle files
5. Build and run

## 📊 Key Activities

### Transportation Tracking
- Car trips (distance, fuel type, vehicle efficiency)
- Public transit (bus, train, subway)
- Flights (domestic/international)
- Walking and cycling

### Food Emissions
- Meal tracking
- Food type categorization
- Local vs imported food tracking

### Shopping Activities
- Purchase tracking
- Product category emissions
- Shopping frequency analysis

## 🌍 Environmental Impact

EcoTracker helps users:
- **Understand** their daily carbon footprint
- **Identify** high-impact activities
- **Set goals** for emission reduction
- **Track progress** over time
- **Make informed decisions** about lifestyle choices

## 📈 Future Enhancements

- [ ] Social features and leaderboards
- [ ] Carbon offset integration
- [ ] Location-based recommendations
- [ ] Barcode scanning for products
- [ ] Integration with smart home devices
- [ ] Web dashboard for data analysis

## 🤝 Contributing

We welcome contributions! Please see our [Contributing Guidelines](CONTRIBUTING.md) for details.

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 📞 Support

For support, feature requests, or bug reports:
- Create an issue in this repository
- Contact the development team

## 🎯 Deployment

Ready to go live? Check out our comprehensive [Deployment Guide](DEPLOYMENT.md) for:
- Google Play Store submission
- Direct APK distribution
- Firebase App Distribution
- Production configuration

---

**Start your journey towards a more sustainable lifestyle today!** 🌱📱
