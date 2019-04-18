package br.com.larissag.ghibiliapp.view


import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import br.com.larissag.ghibiliapp.R
import br.com.larissag.ghibiliapp.viewmodel.FilmViewModel
import kotlinx.android.synthetic.main.fragment_films.*
import org.koin.android.viewmodel.ext.android.viewModel


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class FilmsFragment : Fragment() {

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
                        val viewManager = LinearLayoutManager(this.context)
                        val viewAdapter = FilmAdapter(event.films ?: emptyList())
                        rv_films.apply {
                            setHasFixedSize(true)
                            layoutManager = viewManager
                            adapter = viewAdapter
                        }
                    }
                    event.error != null -> Toast.makeText(this.context, "deu ruim", Toast.LENGTH_SHORT).show()
                }
            }
        })
        return vw
    }


}
