package br.com.larissag.ghibiliapp.viewmodel

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
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
                event.value = FilmEvent(films = it)

            }, { error ->
                event.value = FilmEvent(error = error)
            })
    }
}

data class FilmEvent(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: Throwable? = null,
    val films: List<Film>? = null
)