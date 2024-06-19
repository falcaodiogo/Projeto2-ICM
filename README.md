### Technical Project Report - Android Module
**Health Tracker**

**Subject:** Introdução à Computação Móvel  
**Date:** Aveiro, 19/06/2024  
**Students:**  
108712: Diogo Falcão  
108840: José Gameiro  

---

#### Project Abstract:
This report details the development of a comprehensive health application aimed at improving user well-being through both a mobile and WearOS platform integration. The mobile app provides extensive health monitoring capabilities, while the WearOS app offers real-time health monitoring during physical activities.

#### Report Contents:
1. **Application Concept**
2. **Implemented Solution**
   - Architecture Overview (Technical Design)
   - Implemented Interactions
   - Project Limitations
   - New Features & Changes After Project Presentation
3. **Conclusions and Supporting Resources**
   - Lessons Learned
   - Work Distribution Within the Team
   - Project Resources
   - Reference Materials

---

#### 1. Application Concept
**HealthTracker** is a health companion app for Android and WearOS that utilizes the HealthConnect API to measure health metrics. The app includes:
- **SignInScreen**: Google sign-in for authentication.
- **HomeScreen**: Displays step count, calories, kilometers, and exercise points of interest (POIs) in Aveiro.
- **PlannedExercises**: Lists daily exercises; syncs with WearOS for real-time activity tracking.
- **NotificationsScreen**: Manages app notifications.
- **SleepScreen**: Shows sleep data and graphs.
- **Settings**: Allows users to adjust step goals.

#### 2. Implemented Solution
**Architecture Overview (Technical Design)**
- **Wear OS App**: Collects and displays real-time health data.
- **Health Services**: Retrieves health metrics like heart rate and calories burned.
- **Mobile App Components**: ViewModel, Firebase Google Sign-In, Google Maps API, Health Connect API, Notification Manager, SQLite Database.

**Implemented Interactions**
- Users track sleep routines, monitor POIs for exercise, and log completed exercises.

#### Project Limitations
- Lack of notifications on the watch and basic unit tests.

#### New Features & Changes After Project Presentation
- (No specific changes mentioned)

#### 3. Conclusions and Supporting Resources
**Lessons Learned**: Challenges in integrating real-time health data and managing Kotlin complexities in UI data handling.

**Work Distribution Within the Team**: Equal contribution (50% each) by José Gameiro and Diogo Falcão.

**Project Resources**:  
- **Code Repository**: [GitHub](https://github.com/falcaodiogo/Projeto2-ICM)
- **Ready-to-Deploy APK**: [APK](https://github.com/falcaodiogo/Projeto2-ICM/tree/main/APK/Phone%20App)
- **Reference Materials**: Listed URLs for Android Jetpack, Health Services, Motion, and local notifications.
