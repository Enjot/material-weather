Hello,
this is a Material Design Weather Android App, written using Jetpack Compose UI Toolkit to consolidate the knowledge I learned from various sources.
The app runs on Android 12 and later. It's still in the early stage of development.

My goal was to make something, that isn't in the Play Store yet, a weather app that combines those things:
- material you design
- simple, straightforward and intuitive UI

And the most unique thing:
- **getting zip codes for searched places (!), so there is an easy way to decide which place is which if they are named the same (other apps provide additional data like region, but for my area, quite often it's not enough)**

Things I did already:
- support for dynamic system themes and dark mode
- core functionality: searching for places, showing retrieved data
- getting current location coordinates and weather data for them
- offline-first functionality - app saves data to the database and loads it while user runs it, app always shows database data (data from the network is saved to the database)
- pull to refresh functionality
- fancy search banner animations
- self-written indicators of weather parameters in Canvas Compose
- navigation to daily weather and settings
- separated data, domain and UI layers (dependency injection, inversion of control and mappers)

Things I will do in the future:
- support for polish language (currently english only)
- adding cities to favorites
- widgets and periodic updates in the background
- handling location permissions (currently, user has to grant them in the settings app manually)
- fill settings screen
- fill the screen for the selected day from the daily list
- more animations (when loading search results for example)
- dialogs for different parameters to explain what they mean
- weather images stored locally (currently they are always loaded from the network)
- banners for sun and moon data
- various tests (unit, integration and end-to-end)

Things that would be nice, but not **currently** planned:
- support for various screen sizes
- support for accesibility
- deploying to the Play Store
- support for multi-languages
- Wear OS version

![Group 21](https://github.com/Enjot/materialweather/assets/60782298/37cac4a4-5f7e-4c6d-b50c-b235b64f7dd2)
