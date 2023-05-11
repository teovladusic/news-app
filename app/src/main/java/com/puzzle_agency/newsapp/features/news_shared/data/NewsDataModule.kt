package com.puzzle_agency.newsapp.features.news_shared.data

import com.google.gson.GsonBuilder
import com.puzzle_agency.newsapp.BuildConfig
import com.puzzle_agency.newsapp.features.news_shared.data.api.NewsApi
import com.puzzle_agency.newsapp.features.news_shared.data.api.NewsApiInterceptor
import com.puzzle_agency.newsapp.features.news_shared.data.repository.NewsRepositoryImpl
import com.puzzle_agency.newsapp.features.news_shared.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NewsDataModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)

        val clientBuilder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            clientBuilder.addInterceptor(httpLoggingInterceptor)
        }
        clientBuilder.readTimeout(60, TimeUnit.SECONDS)
        clientBuilder.writeTimeout(60, TimeUnit.SECONDS)

        val newsApiInterceptor = NewsApiInterceptor()
        clientBuilder.addInterceptor(newsApiInterceptor)

        return clientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().create()))
        .build()

    @Provides
    @Singleton
    fun provideNewsApi(retrofit: Retrofit): NewsApi = retrofit.create(NewsApi::class.java)

    @Provides
    @Singleton
    fun provideNewsRepository(newsApi: NewsApi): NewsRepository = NewsRepositoryImpl(newsApi)
}