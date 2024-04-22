# Material Design Weather Android App
![Group 3](https://github.com/Enjot/material-weather/assets/60782298/34df86b0-8764-476f-b4be-b1ef827aa1a2)
  
My main goal was to make a pleasant-looking app, that has some small unique functionality I didn't find in other weather apps in the Play Store:
- getting info about postcodes in search results for the same-named places (other apps can put the region, but quite often it's not enough in my area to recognize which place I should choose from the list, some places can be named the same while being in the same region)

### Features:  
✅ current, hourly and daily weather data  
✅ air pollution data  
✅ sun and moon data  
✅ searching for places  
✅ offline-first functionality  
✅ getting data from the device location  
✅ handling location permissions  
✅ handling lacking location, lacking internet and server error responses  
✅ pull to refresh  
✅ managing saved places  
✅ settings screen  
✅ screen for a detailed daily forecast  
✅ periodic background updates  
✅ support for the Polish language  
✅ light/dark mode and dynamic themes support  
✅ edge-to-edge design  

### Architecture
- single module
- 3 layers (data, domain and UI)
- Use cases
- kotlin coroutines for async programming
- kotlin flows for reactive programming
- unidirectional data flow
- dependency injection with inversion of control

### Tech Stack:
- Kotlin, Jetpack Compose including Canvas, Material 3, Dagger-Hilt, Room, Retrofit, WorkManager, Preferences DataStore, JUnit5, Mockk
### Helpers libraries:
- KSP, Gson, Location Services, Accompanist, OkHttp, Kotlin Serialization and Converter for Retrofit, Turbine
