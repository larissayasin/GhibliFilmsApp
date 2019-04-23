package br.com.larissag.ghibiliapp.data

import com.google.gson.annotations.SerializedName

data class OmdbFilm(
    @SerializedName("Poster") val posterUrl: String
)