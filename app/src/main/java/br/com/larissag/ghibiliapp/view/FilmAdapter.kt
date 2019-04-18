package br.com.larissag.ghibiliapp.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.larissag.ghibiliapp.R
import br.com.larissag.ghibiliapp.data.Film
import kotlinx.android.synthetic.main.view_film_item.view.*

class FilmAdapter (private val films : List<Film>): RecyclerView.Adapter<FilmAdapter.FilmViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.view_film_item, parent, false)
        return FilmViewHolder(v)
    }

    override fun getItemCount()  = films.size


    override fun onBindViewHolder(holder: FilmViewHolder, pos: Int) {
        holder.bind(films[pos])
    }

    class FilmViewHolder(private val iv : View) : RecyclerView.ViewHolder(iv) {

        fun bind(film : Film){
            iv.tv_film_item_title.text = film.title.trim()
            iv.tv_fil_item_director.text = film.director
            iv.tv_film_item_year.text = "(${film.release_date})"
        }
    }
}