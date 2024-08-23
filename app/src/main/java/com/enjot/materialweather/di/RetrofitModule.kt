package com.enjot.materialweather.di

//@Module
//@InstallIn(SingletonComponent::class)
//object RetrofitModule {
//
//
//    @Provides
//    @Singleton
//    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().build()
//
//    @Provides
//    @Named("openweathermap")
//    @Singleton
//    fun provideWeatherApi(client: OkHttpClient): OpenWeatherMapApi {
//        val contentType = "application/json".toMediaType()
//        val json = Json { ignoreUnknownKeys = true }
//        return Retrofit.Builder()
//            .baseUrl(OpenWeatherMapApi.BASE_URL)
//            .addConverterFactory(json.asConverterFactory(contentType))
//            .client(client)
//            .build()
//            .create()
//    }
//
//    @Provides
//    @Named("geoapify")
//    @Singleton
//    fun provideGeoapifyApi(client: OkHttpClient): GeoapifyApi {
//        val contentType = "application/json".toMediaType()
//        val json = Json { ignoreUnknownKeys = true }
//        return Retrofit.Builder()
//            .baseUrl(GeoapifyApi.BASE_URL)
//            .addConverterFactory(json.asConverterFactory(contentType))
//            .client(client)
//            .build()
//            .create()
//    }
//}