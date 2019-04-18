package br.com.larissag.ghibiliapp.data.repository

import android.util.Log
import br.com.larissag.ghibiliapp.data.Film
import br.com.larissag.ghibiliapp.data.local.FilmDAO
import br.com.larissag.ghibiliapp.data.remote.FilmApi
import io.reactivex.Observable

interface FilmRepository {
    fun getFilms(): Observable<List<Film>>
}

class FilmRepositoryImpl(private val dao: FilmDAO, private val api: FilmApi) : FilmRepository {

    override fun getFilms(): Observable<List<Film>> {
        val observableFromApi = getFilmsFromApi()
        val observableFromDb = getFilmsFromDb()
        return Observable.concatArray(observableFromDb, observableFromApi)
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