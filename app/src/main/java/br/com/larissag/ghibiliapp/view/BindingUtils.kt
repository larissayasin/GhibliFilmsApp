package br.com.larissag.ghibiliapp.view

object BindingUtils {

    @JvmStatic
    fun setUpDirector(director: String) = "Directed by $director"

    @JvmStatic
    fun setUpRT(rt: String) = " $rt%"

    @JvmStatic
    fun showTomato(rt : String) = rt.toInt() > 59
}