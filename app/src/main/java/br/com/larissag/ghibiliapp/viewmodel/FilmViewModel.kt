package br.com.larissag.ghibiliapp.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import br.com.larissag.ghibiliapp.data.Film
import br.com.larissag.ghibiliapp.data.repository.FilmRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class FilmViewModel(private val repository: FilmRepository) : ViewModel() {

    val event = MutableLiveData<FilmEvent>()
    @SuppressLint("CheckResult")
    fun getFilms() {
        event.value = FilmEvent(isLoading = true)
        val d = repository.getFilms()

         repository.event?.observeForever {
             event.value = it
             Log.d("REPO EVENT" , "$it")
        }

    }

    fun getPoster(title: String) {
        event.value = FilmEvent(isLoading = true)
        val d = repository.getFilmPoster(title)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                event.value = FilmEvent(posterUrl = it.posterUrl, isSuccess = true)

            }, { error ->
                event.value = FilmEvent(error = error)
            })
    }

    fun updateFilmPoster(film: Film, posterUrl: String) {
        repository.updateFilm(film, posterUrl)
    }
}

data class FilmEvent(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: Throwable? = null,
    val posterUrl: String? = null,
    val films: List<Film>? = null
)