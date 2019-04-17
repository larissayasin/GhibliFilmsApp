package br.com.larissag.ghibiliapp.data.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import br.com.larissag.ghibiliapp.data.Film

@Database(entities = [Film::class], version = 1)
abstract class FilmDatabase : RoomDatabase() {

    abstract fun filmDAO(): FilmDAO
}