package br.com.larissag.ghibiliapp.data.local

import android.arch.persistence.room.*
import br.com.larissag.ghibiliapp.data.Film
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface FilmDAO {
    @Insert(
        onConflict = OnConflictStrategy.IGNORE
    )
    fun saveAll(entities: List<Film>)

    @Query("SELECT * FROM film WHERE id = :id")
    fun findById(id: String): Single<Film>

    @Query("SELECT * FROM film")
    fun findAll(): Flowable<List<Film>>

    @Query("UPDATE film set poster_url = :posterUrl WHERE id = :id")
    fun updatePoster(id: String, posterUrl: String)

}