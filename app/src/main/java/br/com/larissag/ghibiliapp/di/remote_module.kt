package br.com.larissag.ghibiliapp.di

import br.com.larissag.ghibiliapp.BuildConfig
import br.com.larissag.ghibiliapp.data.remote.FilmApi
import br.com.larissag.ghibiliapp.data.remote.OmdbApi
import br.com.larissag.ghibiliapp.di.DatasourceProperties.OMDB_POSTER_URL
import br.com.larissag.ghibiliapp.di.DatasourceProperties.SERVER_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val remoteModule = module {
    single { createOkHttpClient() }

    single { createWebService<FilmApi>(get(), SERVER_URL) }
    single { createWebService<OmdbApi>(get(), OMDB_POSTER_URL) }
}

object DatasourceProperties {
    const val SERVER_URL = "https://ghibliapi.herokuapp.com/"
    const val OMDB_POSTER_URL = "http://www.omdbapi.com"
}

fun createOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    if (BuildConfig.DEBUG) {
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    } else {
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
    }
    return OkHttpClient.Builder()
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor).build()
}

inline fun <reified T> createWebService(okHttpClient: OkHttpClient, url: String): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
    return retrofit.create(T::class.java)
}