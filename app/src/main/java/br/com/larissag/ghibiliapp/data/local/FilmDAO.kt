package br.com.larissag.ghibiliapp.data.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import br.com.larissag.ghibiliapp.data.Film
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface FilmDAO{
    @Insert(
        onConflict = OnConflictStrategy.IGNORE
    )
    fun saveAll(entities: List<Film>)

    @Query("SELECT * FROM film WHERE id = :id")
    fun findById(id: String): Single<Film>

    @Query("SELECT * FROM film")
    fun findAll(): Flowable<List<Film>>



}