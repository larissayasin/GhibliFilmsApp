package br.com.larissag.ghibiliapp.view

import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.com.larissag.ghibiliapp.R
import br.com.larissag.ghibiliapp.viewmodel.FilmViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val vm: FilmViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        vm.getFilms()

        vm.event.observe(this, Observer { event ->
            if (event != null) {
                if (event.isLoading)
                    Toast.makeText(this, "LOADING", Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(this, "Carregou", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
