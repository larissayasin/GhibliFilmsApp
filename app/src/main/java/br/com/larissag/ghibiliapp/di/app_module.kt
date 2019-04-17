package br.com.larissag.ghibiliapp.di

import br.com.larissag.ghibiliapp.data.repository.FilmRepository
import br.com.larissag.ghibiliapp.data.repository.FilmRepositoryImpl
import br.com.larissag.ghibiliapp.viewmodel.FilmViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ghibiliModule = module {
    single<FilmRepository> { FilmRepositoryImpl(get(), get()) }

    viewModel { FilmViewModel(get()) }
}

val ghibiliApp = listOf(ghibiliModule, remoteModule, localModule)