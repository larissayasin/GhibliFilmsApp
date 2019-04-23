package br.com.larissag.ghibiliapp.data.repository

import OmdbKey.OMDB_KEY
import android.util.Log
import br.com.larissag.ghibiliapp.data.Film
import br.com.larissag.ghibiliapp.data.OmdbFilm
import br.com.larissag.ghibiliapp.data.local.FilmDAO
import br.com.larissag.ghibiliapp.data.remote.FilmApi
import br.com.larissag.ghibiliapp.data.remote.OmdbApi
import io.reactivex.Observable

interface FilmRepository {
    fun getFilms(): Observable<List<Film>>
    fun getFilmPoster(title : String) : Observable<OmdbFilm>
}

class FilmRepositoryImpl(private val dao: FilmDAO, private val api: FilmApi, private val omdbApi: OmdbApi) : FilmRepository {

    override fun getFilmPoster(title: String): Observable<OmdbFilm> {
       return omdbApi.getFilmPoster(OMDB_KEY, title)
    }

    override fun getFilms(): Observable<List<Film>> {
        val observableFromApi = getFilmsFromApi()
        val observableFromDb = getFilmsFromDb()
        return Observable.merge(observableFromDb, observableFromApi)
    }

    private fun getFilmsFromApi(): Observable<List<Film>> {
        return api.getFilms()
            .doOnNext {
                Log.e("REPOSITORY API * ", it.size.toString())
                dao.saveAll(it)

            }
    }

    private fun getFilmsFromDb(): Observable<List<Film>> {
        return dao.findAll()
            .toObservable()
            .doOnNext {
                //Print log it.size :)
                Log.e("REPOSITORY DB *** ", it.size.toString())
            }
    }

}