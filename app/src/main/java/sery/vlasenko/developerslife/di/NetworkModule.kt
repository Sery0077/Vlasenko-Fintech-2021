package sery.vlasenko.developerslife.di

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import sery.vlasenko.developerslife.BuildConfig
import sery.vlasenko.developerslife.data.repository.DevLifeService
import java.util.concurrent.TimeUnit

@Module
class NetworkModule {

    @Provides
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.DEV_LIFE_API)
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideOkHttpClient())
            .build()

    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val httpUrl = chain.request().url().newBuilder()
                    .addQueryParameter("json", "true")
                    .build()

                chain.proceed(chain.request().newBuilder().url(httpUrl).build())
            }
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()

    @Provides
    fun provideDevLifeService(): DevLifeService =
        provideRetrofit().create(DevLifeService::class.java)
}