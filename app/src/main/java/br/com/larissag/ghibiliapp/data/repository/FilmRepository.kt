package br.com.larissag.ghibiliapp.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import br.com.larissag.ghibiliapp.Keys.OMDB_KEY
import br.com.larissag.ghibiliapp.data.Film
import br.com.larissag.ghibiliapp.data.OmdbFilm
import br.com.larissag.ghibiliapp.data.local.FilmDAO
import br.com.larissag.ghibiliapp.data.remote.FilmApi
import br.com.larissag.ghibiliapp.data.remote.OmdbApi
import br.com.larissag.ghibiliapp.viewmodel.FilmEvent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

interface FilmRepository {
    fun getFilms()
    fun getFilmPoster(title: String): Observable<OmdbFilm>
    fun updateFilm(film: Film, posterUrl: String)
    var event: MutableLiveData<FilmEvent>?
}

class FilmRepositoryImpl(
    private val dao: FilmDAO,
    private val api: FilmApi,
    private val omdbApi: OmdbApi
) :
    FilmRepository {
    override var event: MutableLiveData<FilmEvent>? = MutableLiveData<FilmEvent>()

    override fun updateFilm(film: Film, posterUrl: String) {
        GlobalScope.launch {
            dao.updatePoster(film.id, posterUrl)
        }
    }

    override fun getFilmPoster(title: String): Observable<OmdbFilm> {
        return omdbApi.getFilmPoster(OMDB_KEY, title)
    }

    override fun getFilms() {
        val d = getFilmsFromDb().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ listDb ->
                if (listDb.isEmpty()) {
                    getFilmsFromApi().subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            event?.value = FilmEvent(films = it, isSuccess = true)
                        }, { error ->
                            event?.value = FilmEvent(error = error)

                        })
                } else {
                    event?.value = FilmEvent(films = listDb, isSuccess = true)
                }

            }, { error ->
                event?.value = FilmEvent(error = error)
            })

    }

    private fun getFilmsFromApi(): Observable<List<Film>> {
        return api.getFilms()
            .doOnNext { list ->
                Log.e("REPOSITORY API * ", list.size.toString())
                dao.saveAll(list)
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