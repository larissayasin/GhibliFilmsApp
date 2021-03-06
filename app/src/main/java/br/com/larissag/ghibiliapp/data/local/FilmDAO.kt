package br.com.larissag.ghibiliapp.data.local

import androidx.room.*
import br.com.larissag.ghibiliapp.data.Film
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface FilmDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAll(entities: List<Film>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveFilm(film: Film)

    @Query("SELECT * FROM film WHERE id = :id")
    fun findById(id: String): Film

    @Query("SELECT * FROM film")
    fun findAll(): Flowable<List<Film>>

    @Query("SELECT * FROM film")
    fun listAll(): List<Film>

    @Query("SELECT * FROM film")
    fun findA(): List<Film>

    @Query("UPDATE film set poster_url = :posterUrl WHERE id = :id")
    fun updatePoster(id: String, posterUrl: String)

}