package br.com.larissag.ghibiliapp.view

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.larissag.ghibiliapp.R
import br.com.larissag.ghibiliapp.data.Film
import br.com.larissag.ghibiliapp.viewmodel.FilmViewModel
import com.bumptech.glide.Glide
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_details.view.*
import kotlinx.android.synthetic.main.fragment_films.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class DetailsFragment : androidx.fragment.app.Fragment() {

    private val vm: FilmViewModel by sharedViewModel()
    private lateinit var film: Film
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_details, container, false)
        film = Gson().fromJson(
            arguments?.getString(
                "film"
            ), Film::class.java
        )

        vm.getPoster(film.title)

        vm.event.observe(this, Observer { event ->
            if (event != null) {
                when {
                    event.isLoading -> Toast.makeText(this.context, "LOADING", Toast.LENGTH_SHORT).show()
                    event.isSuccess -> {
                        film.poster_url = event.posterUrl ?: ""
                        Glide.with(this).load(film.poster_url).into(v.iv_details_poster)
                    }
                    event.error != null -> Toast.makeText(
                        this.context,
                        getString(R.string.error_msg),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
        return v
    }
}
