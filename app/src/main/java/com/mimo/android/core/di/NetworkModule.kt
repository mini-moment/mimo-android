package com.mimo.android.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

  @Singleton
  @Provides
  fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    //BuildConfig로 넣어주기
    val apiKey = ""
    return Retrofit.Builder()
      .addConverterFactory(ScalarsConverterFactory.create())
      .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
      .baseUrl(apiKey)
      .client(okHttpClient)
      .build()
  }



  @Singleton
  @Provides
  fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient =
    OkHttpClient.Builder()
      .run {
        connectTimeout(120, TimeUnit.SECONDS)
        readTimeout(120, TimeUnit.SECONDS)
        writeTimeout(120, TimeUnit.SECONDS)
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        addInterceptor(interceptor)
        build()
      }
}