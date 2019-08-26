package br.com.larissag.ghibiliapp

import android.app.Application
import br.com.larissag.ghibiliapp.di.ghibiliApp
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import com.flurry.android.FlurryAgent
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log


class GhibiliApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@GhibiliApp)
            modules(ghibiliApp)
        }

        FlurryAgent.Builder()
            .withLogEnabled(true)
            .withCaptureUncaughtExceptions(true)
            .withContinueSessionMillis(10000)
            .withLogLevel(Log.VERBOSE)
            .build(this, Keys.FLURRY_KEY)
    }
}