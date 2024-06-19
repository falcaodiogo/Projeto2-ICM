# Health Tracker (Second Practical Assignemt ICM)

Health Tracker is a health application aimed at improving user well-being through both a mobile and WearOS platform integration. The mobile app provides extensive health monitoring capabilities, while the WearOS app offers real-time health monitoring during physical activities.
This application was develop 

## Application Concept
**HealthTracker** is a health companion app for Android and WearOS that utilizes the HealthConnect API to measure health metrics. The mobile app includes:
- **SignInScreen**: Google sign-in for authentication.
- **HomeScreen**: Displays step count, calories, kilometers, and exercise points of interest (POIs) in Aveiro.
- **PlannedExercises**: Lists daily exercises; syncs with WearOS for real-time activity tracking.
- **NotificationsScreen**: Manages app notifications.
- **SleepScreen**: Shows sleep data and graphs.
- **Settings**: Allows users to adjust step goals.

## Implemented Solution

### Architecture Overview (Technical Design)

- **Wear OS App**: Collects and displays real-time health data.
- **Health Services**: Retrieves health metrics like heart rate and calories burned.
- **Mobile App Components**: ViewModel, Firebase Google Sign-In, Google Maps API, Health Connect API, Notification Manager, SQLite Database.

### Implemented Interactions
- Users track sleep routines, monitor POIs for exercise, and log completed exercises.

## Project Structure
```bash
.
├── phone_app
│   ├── build.gradle.kts
│   ├── gradle.properties
│   ├── gradlew
│   ├── gradlew.bat
│   ├── local.defaults.properties
│   ├── local.properties
│   ├── secrets.properties
│   └── settings.gradle.kts
├── README.md
├── Report
│   └── Report_Android_Project.pdf
└── wearOS_app
    ├── build.gradle.kts
    ├── gradle.properties
    ├── gradlew
    ├── gradlew.bat
    ├── local.properties
    └── settings.gradle.kts
```

## Resources

**Project Resources**:  
- **Code Repository**: [GitHub](https://github.com/falcaodiogo/Projeto2-ICM)
- **Ready-to-Deploy APK**: [APK](https://github.com/falcaodiogo/Projeto2-ICM/tree/main/APK/Phone%20App)
- **Report**: [PDF](https://github.com/falcaodiogo/Projeto2-ICM/tree/main/Report)

## How to run

To execute the application, you must follow the following steps:

### Mobile App
1. Clone the repository to your local machine.
2. Open the phone_app project in Android Studio.
3. Run the application on an mobile emulator or physical device.

### Google Authentication with Firebase

To allow the google authentication please contact one of the authors to permit it.

### WearOS App
1. Open the wearOS_app project in Android Studio.
2. Run the application on an wearOS emulator or physical device.

## Authors

| Name | GitHub |
| :---: | :---: |
| Diogo Falcão | [falcaodiogo](https://github.com/falcaodiogo)
| José Gameiro | [zegameiro](https://github.com/zegameiro)
