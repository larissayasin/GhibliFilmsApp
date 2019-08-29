package br.com.larissag.ghibiliapp.view

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import br.com.larissag.ghibiliapp.R
import br.com.larissag.ghibiliapp.data.Film
import com.bumptech.glide.Glide
import com.google.gson.Gson
import kotlinx.android.synthetic.main.view_film_item.view.*

class FilmAdapter (private val films : List<Film>, private val activity : Activity): androidx.recyclerview.widget.RecyclerView.Adapter<FilmAdapter.FilmViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.view_film_item, parent, false)
        return FilmViewHolder(v, activity)
    }

    override fun getItemCount()  = films.size


    override fun onBindViewHolder(holder: FilmViewHolder, pos: Int) {
        holder.bind(films[pos])
    }

    class FilmViewHolder(private val iv: View, private val activity: Activity) : androidx.recyclerview.widget.RecyclerView.ViewHolder(iv) {
        fun bind(film : Film){
            iv.tv_film_item_title.text = film.title.trim()
            iv.tv_fil_item_director.text = film.director
            iv.tv_film_item_year.text = "(${film.release_date})"
            Glide.with(iv).load(film.poster_url).placeholder(R.drawable.avatar_placeholder).into(iv.iv_film_item_poster)
            val nav = Navigation.findNavController(activity, R.id.main_frag_nav)
            iv.setOnClickListener {
                val bundle = bundleOf("film" to Gson().toJson(film))
                nav.navigate(R.id.action_filmsFragment_to_detailsFragment, bundle)
            }
        }
    }
}