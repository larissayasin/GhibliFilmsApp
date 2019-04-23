package br.com.larissag.ghibiliapp.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
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
        repository.getFilms()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                event.value = FilmEvent(films = it, isSuccess = true)

            }, { error ->
                event.value = FilmEvent(error = error)
            })
    }

    fun getPoster(title : String){
        event.value = FilmEvent(isLoading = true)
        repository.getFilmPoster(title)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                event.value = FilmEvent(posterUrl = it.posterUrl, isSuccess = true)

            }, { error ->
                event.value = FilmEvent(error = error)
            })
    }
}

data class FilmEvent(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: Throwable? = null,
    val posterUrl : String? = null,
    val films: List<Film>? = null
)