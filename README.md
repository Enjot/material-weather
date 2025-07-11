# Material Design Weather Android App
![Group 3](https://github.com/Enjot/material-weather/assets/60782298/34df86b0-8764-476f-b4be-b1ef827aa1a2)
  
My main goal was to make a pleasant-looking app, that has some small, unique functionality I didn't find in other weather apps in the Play Store:
- getting info about postcodes in search results for the same-named places (other apps can put the region, but quite often it's not enough in my area to recognize which place I should choose from the list, some places can be named the same while being in the same region)

# Features:  
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
✅ horizontal responsive widget  

# Tests
Instrumental tests  
[Settings data module (WorkManager)](https://github.com/Enjot/material-weather/tree/master/settings/data/src/androidTest/java/com/enjot/materialweather)  
  
Unit tests  
[Weather data module](https://github.com/Enjot/material-weather/tree/master/weather/data/src/test/java/com/enjot/materialweather/weather/data)  
[Weather domain module](https://github.com/Enjot/material-weather/tree/master/weather/domain/src/test/java/com/enjot/materialweather/weather/domain)  
  
UI tests  
[Weather presentation module](https://github.com/Enjot/material-weather/tree/master/weather/presentation/src/androidTest/java/com/enjot/materialweather)  

# Architecture
- Multi-module
- Extra layer modules inside feature modules
- Gradle convention plugins
- Use cases and repositories
- Coroutines for async programming
- Kotlin flows for reactive programming
- Unidirectional data flow
- Dependency injection with inversion of control

# Tech Stack:
- Kotlin, Jetpack Compose including Canvas and Glance, Material 3, Ktor, Room, Koin, WorkManager, Preferences DataStore, JUnit5, Mockk
### Helpers libraries:
- Firebase Crashlytics, KSP, Location Services, Accompanist, Kotlin Serialization, Turbine, Timber, LeakCanary

# Instructions to build this repo
### Add those API keys to your `local.properties` file:  
api_key_openweathermap=[OpenWeatherMap API](https://openweathermap.org/api/one-call-3)  
api_key_geoapify=[Geoapify API](https://www.geoapify.com/geocoding-api/)  

add `google-services.json` to `/app` dir (create new Firebase project)
