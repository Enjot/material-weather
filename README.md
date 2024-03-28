# Material Design Weather Android App
![Group 2](https://github.com/Enjot/material-weather/assets/60782298/7739e742-05d1-4c12-a473-71311059db58)
  
My main goal was to make a pleasant-looking app, that has some small unique functionality I didn't find in other weather apps in the Play Store:
- getting info about postcodes in search results for the same-named places (other apps can put the region, but quite often it's not enough in my area to recognize which place I should choose from the list, some places can be named the same being in the same region)

### Already in the app:  
✅ light/dark mode  
✅ dynamic themes  
✅ current, hourly, and daily weather data  
✅ air pollution data  
✅ weather conditions data  
✅ searching for places  
✅ offline-first functionality  
✅ getting data from the device location  
✅ handling location permissions  
✅ pull to refresh  
✅ search bar edge-to-edge animations  
### Functional to do:  
➡️ support for the Polish language  
➡️ managing favorite places  
➡️ widgets  
➡️ detailed daily weather data on another screen  
➡️ settings screen  
➡️ periodic background updates  
➡️ dialogs with additional info about some weather data  
➡️ moon and sun data  
➡️ support for imperial units  
➡️ informing about poor/no internet connectivity
### Technical to do:
➡️ weather images stored locally (currently they are always loaded from the network)  
➡️ rework of pull to refresh  
➡️ rework of loading weather and search results animations (including shimmer effect)  
➡️ unit, integration and end-to-end tests
### Architecture:
- separated data, domain, and UI layers (dependency injection, IoC, mappers)
- MVI design
- single module application
### Tech Stack:
- Kotlin, Jetpack Compose including Canvas, Material 3, Dagger-Hilt, Room, Retrofit
### Helpers:
- KSP, Gson, ViewModel, Location Services, Coil, Accompanist, OkHttp, Kotlin Serialization and Converter for Retrofit  
