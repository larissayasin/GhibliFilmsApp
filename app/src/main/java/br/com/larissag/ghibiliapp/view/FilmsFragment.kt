package br.com.larissag.ghibiliapp.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import br.com.larissag.ghibiliapp.R
import br.com.larissag.ghibiliapp.viewmodel.FilmViewModel
import kotlinx.android.synthetic.main.fragment_films.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel


class FilmsFragment : androidx.fragment.app.Fragment() {

    private val vm: FilmViewModel by viewModel()
    private lateinit var vw: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        vw = inflater.inflate(R.layout.fragment_films, container, false)
        vm.getFilms()

        vm.event.observe(this, Observer { event ->
            if (event != null) {
                when {
                    event.isLoading -> Toast.makeText(this.context, "LOADING", Toast.LENGTH_SHORT).show()
                    event.isSuccess -> {
                        val viewManager = androidx.recyclerview.widget.LinearLayoutManager(this.context)
                        val viewAdapter = FilmAdapter(event.films ?: emptyList(), this.activity!!)
                        rv_films.apply {
                            setHasFixedSize(true)
                            layoutManager = viewManager
                            adapter = viewAdapter
                        }
                    }
                    event.error != null ->    Toast.makeText(this.context, getString(R.string.error_msg), Toast.LENGTH_SHORT).show()
                }
            }
        })
        return vw
    }
}
