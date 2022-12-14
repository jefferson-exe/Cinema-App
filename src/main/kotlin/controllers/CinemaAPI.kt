package controllers

import models.Cinema
import utils.ScannerInput
import utils.Utilities
import java.io.BufferedReader
import java.io.File
import java.util.ArrayList

class CinemaAPI {

    private var cinemas = ArrayList<Cinema>()

    // ----------------------------------------------
    //  For Managing the id internally in the program
    // ----------------------------------------------
    private var lastId = 0
    private fun getId() = lastId++

    // ----------------------------------------------
    //  CRUD METHODS FOR MOVIE ArrayList
    // ----------------------------------------------
    fun add(cinema: Cinema): Boolean {
        cinema.cinemaId = getId()
        return cinemas.add(cinema)
    }

    //fun deleteCinema(id: Int) = cinemas.removeIf { cinema -> cinema.cinemaId == id }
    fun deleteCinema(indexToDelete: Int): Cinema? {
        return if (isValidListIndex(indexToDelete, cinemas)) {
            cinemas.removeAt(indexToDelete)
        } else null
    }

    fun update(id: Int, cinema: Cinema?): Boolean {
        // find the movie object by the index number
        val foundCinema = findCinemas(id)

        // if the movie exists, use the note details passed as parameters to update the found note in the ArrayList.
        if ((foundCinema != null) && (cinema != null)) {
            foundCinema.cinemaId = cinema.cinemaId
            foundCinema.cinemaName = cinema.cinemaName
            foundCinema.cinemaAddress = cinema.cinemaAddress
            foundCinema.cinemaPhone = cinema.cinemaPhone
            foundCinema.numberOfScreens = cinema.numberOfScreens
            foundCinema.cinemaEmail = cinema.cinemaEmail
            return true
        }

        // if the movie was not found, return false, indicating that the update was not successful
        return false
    }

    fun listAllCinemas() =
        if (cinemas.isEmpty()) "No notes stored"
        else Utilities.formatCinemaListString(cinemas)

    fun listCurrentCinema() =
        if (numberOfActiveCinemas() == 0) "No active notes stored"
        else Utilities.formatCinemaListString(cinemas.filter { movies -> !movies.isCinemaArchived })

    fun listArchivedCinemas() =
        if (numberOfArchivedCinemas() == 0) "No archived notes stored"
        else Utilities.formatCinemaListString(cinemas.filter { cinemas -> cinemas.isCinemaArchived })

    // ----------------------------------------------
    //  COUNTING METHODS FOR MOVIE ArrayList
    // ----------------------------------------------
    fun numberOfActiveCinemas() = cinemas.size
    fun numberOfArchivedCinemas(): Int = cinemas.count { cinema: Cinema -> cinema.isCinemaArchived }
    fun numberOfCinemas(): Int = cinemas.count { cinema: Cinema -> !cinema.isCinemaArchived }

    fun findCinemas(index: Int): Cinema? {
        return if (isValidListIndex(index, cinemas)) {
            cinemas[index]
        } else null
    }

    // utility method to determine if an index is valid in a list.
    fun isValidListIndex(index: Int, list: List<Any>): Boolean {
        return (index >= 0 && index < list.size)
    }

    fun archiveCinema(id: Int): Boolean {
        val foundMovie = findCinemas(id)
        if ((foundMovie != null) && (!foundMovie.isCinemaArchived)) {
            foundMovie.isCinemaArchived = true
            return true
        }
        return false
    }
// fun findCinemas(cinemaId : Int) =  cinemas.find{ cinema -> cinema.cinemaId == cinemaId }
}
