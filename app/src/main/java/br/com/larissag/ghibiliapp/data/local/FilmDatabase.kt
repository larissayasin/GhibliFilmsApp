package br.com.larissag.ghibiliapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.larissag.ghibiliapp.data.Film

@Database(entities = [Film::class], version = 1, exportSchema = false)
abstract class FilmDatabase : RoomDatabase() {

    abstract fun filmDAO(): FilmDAO
}