package utils

import models.Cinema
import models.Movie

object Utilities {

    // NOTE: JvmStatic annotation means that the methods are static i.e. we can call them over the class
    //      name; we don't have to create an object of Utilities to use them.

    @JvmStatic
    fun formatListString(moviesToFormat: List<Movie>): String =
        moviesToFormat
            .joinToString(separator = "\n") { movie -> "$movie" }

    @JvmStatic
    fun formatCinemaListString(cinemasToFormat: List<Cinema>): String =
        cinemasToFormat
            .joinToString(separator = "\n") { cinema -> "$cinema" }

    @JvmStatic
    fun formatSetString(itemsToFormat: Set<Cinema>): String =
        itemsToFormat
            .joinToString(separator = "\n") { item -> "\t$item" }
}
