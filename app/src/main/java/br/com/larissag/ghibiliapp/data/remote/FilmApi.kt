package br.com.larissag.ghibiliapp.data.remote

import br.com.larissag.ghibiliapp.data.Film
import io.reactivex.Observable
import retrofit2.http.GET

interface FilmApi {

    @GET("films")
    fun getFilms(): Observable<List<Film>>
}