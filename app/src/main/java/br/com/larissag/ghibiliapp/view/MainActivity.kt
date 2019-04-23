package br.com.larissag.ghibiliapp.view

import androidx.lifecycle.Observer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.widget.LinearLayout
import android.widget.Toast
import br.com.larissag.ghibiliapp.R
import br.com.larissag.ghibiliapp.viewmodel.FilmViewModel
import kotlinx.android.synthetic.main.fragment_films.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
