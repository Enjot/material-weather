# Material Design Weather Android App
  ![Group 2](https://github.com/Enjot/material-weather/assets/60782298/46402733-0809-4fee-8f36-369128de2dbe)
  
My main goal was to make a pleasant-looking app, that has some small unique functionality I didn't find in other weather apps in the Play Store:
- getting info about postcodes in search results for the same-named places (other apps can put the region, but quite often it's not enough in my area to recognize which place I should choose from the list, some places can be named the same being in the same region)

### Features:  
✅ current, hourly and daily weather data  
✅ air pollution data  
✅ searching for places  
✅ offline-first functionality  
✅ getting data from the device location  
✅ handling location permissions  
✅ handling no location, no internet and error server responses  
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
- kotlin coroutines for async programming
- kotlin flows for reactive programming
- unidirectional data flow
- dependency injection with inversion of control

### To do:  
➡️ support for imperial units  
➡️ widgets  
➡️ tests  

### Tech Stack:
- Kotlin, Jetpack Compose including Canvas, Material 3, Dagger-Hilt, Room, Retrofit, WorkManager, Preferences DataStore
### Helpers libraries:
- KSP, Gson, ViewModel, Location Services, Coil, Accompanist, OkHttp, Kotlin Serialization and Converter for Retrofit
