package br.com.larissag.ghibiliapp.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "film")
data class Film(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val director: String,
    val producer: String,
    val release_date: String,
    val rt_score: String
)

