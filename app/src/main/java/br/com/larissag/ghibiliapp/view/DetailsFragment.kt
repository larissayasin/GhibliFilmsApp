package br.com.larissag.ghibiliapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import br.com.larissag.ghibiliapp.R
import br.com.larissag.ghibiliapp.data.Film
import br.com.larissag.ghibiliapp.databinding.FragmentDetailsBinding
import br.com.larissag.ghibiliapp.viewmodel.FilmViewModel
import com.bumptech.glide.Glide
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_details.view.*
import org.koin.android.viewmodel.ext.android.sharedViewModel


class DetailsFragment : androidx.fragment.app.Fragment() {

    private val vm: FilmViewModel by sharedViewModel()
    private lateinit var film: Film
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding : FragmentDetailsBinding = DataBindingUtil.inflate(inflater ,R.layout.fragment_details,container , false)
        val v : View  = binding.root

        film = Gson().fromJson(
            arguments?.getString(
                "film"
            ), Film::class.java
        )

        binding.film = film

        vm.getPoster(film.title)

        vm.event.observe(this, Observer { event ->
            if (event != null) {
                when {
                  //  event.isLoading -> Toast.makeText(this.context, "LOADING", Toast.LENGTH_SHORT).show()
                    event.isSuccess -> {
                        Glide.with(this).load(event.posterUrl ?: "").into(v.iv_details_poster)
                        vm.updateFilmPoster(film, event.posterUrl ?: "")
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
//            // Handle the back button event
//        }
    }
}
