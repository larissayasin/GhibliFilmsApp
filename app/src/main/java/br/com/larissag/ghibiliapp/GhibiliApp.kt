package br.com.larissag.ghibiliapp

import android.app.Application
import br.com.larissag.ghibiliapp.di.ghibiliApp
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class GhibiliApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@GhibiliApp)
            modules(ghibiliApp)
        }
    }
}