package br.com.larissag.ghibiliapp.di

import android.arch.persistence.room.Room
import br.com.larissag.ghibiliapp.data.local.FilmDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val localModule = module {

    single {
        Room.databaseBuilder(androidApplication(), FilmDatabase::class.java, "film-db")
            .build()
    }

    single { get<FilmDatabase>().filmDAO() }
}