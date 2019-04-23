package br.com.larissag.ghibiliapp.data.remote

import br.com.larissag.ghibiliapp.data.OmdbFilm
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbApi {

    @GET("/")
    fun getFilmPoster(@Query("apikey") key: String, @Query("t") title: String): Observable<OmdbFilm>
}