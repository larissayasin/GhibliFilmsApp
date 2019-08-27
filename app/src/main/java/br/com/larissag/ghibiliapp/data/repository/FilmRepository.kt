package br.com.larissag.ghibiliapp.data.repository

import Keys.OMDB_KEY
import android.util.Log
import br.com.larissag.ghibiliapp.Keys.OMDB_KEY
import br.com.larissag.ghibiliapp.data.Film
import br.com.larissag.ghibiliapp.data.OmdbFilm
import br.com.larissag.ghibiliapp.data.local.FilmDAO
import br.com.larissag.ghibiliapp.data.remote.FilmApi
import br.com.larissag.ghibiliapp.data.remote.OmdbApi
import io.reactivex.Observable
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

interface FilmRepository {
    fun getFilms(): Observable<List<Film>>
    fun getFilmPoster(title: String): Observable<OmdbFilm>
    fun updateFilm(film: Film, posterUrl: String)
}

class FilmRepositoryImpl(private val dao: FilmDAO, private val api: FilmApi, private val omdbApi: OmdbApi) :
    FilmRepository {

    override fun updateFilm(film: Film, posterUrl: String) {
        GlobalScope.launch {
            //dao.findA()
            dao.updatePoster(film.id, posterUrl)
        }
    }

    override fun getFilmPoster(title: String): Observable<OmdbFilm> {
        return omdbApi.getFilmPoster(OMDB_KEY, title)
    }

    override fun getFilms(): Observable<List<Film>> {
        val observableFromDb = getFilmsFromDb()
        val observableFromApi = getFilmsFromApi()
        return Observable.merge(observableFromDb, observableFromApi)
    }

    private fun getFilmsFromApi(): Observable<List<Film>> {
        return api.getFilms()
            .doOnNext { list ->
                Log.e("REPOSITORY API * ", list.size.toString())
                list.forEach {
                    dao.saveFilm(it)
                }
            }
    }

    private fun getFilmsFromDb(): Observable<List<Film>> {
        return dao.findAll()
            .toObservable()
            .doOnNext {
                Log.e("REPOSITORY DB *** ", it.size.toString())
            }
    }


}