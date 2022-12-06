package controllers

import models.Movie
import utils.ScannerInput
import utils.Utilities.formatListString
import java.io.BufferedReader
import java.io.File
import java.util.ArrayList

class MovieAPI() {

    private var movies = ArrayList<Movie>()

    // ----------------------------------------------
    //  For Managing the id internally in the program
    // ----------------------------------------------
    private var lastId = 0
    private fun getId() = lastId++

    // ----------------------------------------------
    //  CRUD METHODS FOR MOVIE ArrayList
    // ----------------------------------------------
    fun add(movie: Movie): Boolean {
        movie.movieId = getId()
        return movies.add(movie)
    }

    fun delete(id: Int) = movies.removeIf { movie -> movie.movieId == id }

    fun update(id: Int, movie: Movie?): Boolean {
        // find the movie object by the index number
        val foundMovie = findMovies(id)

        // if the movie exists, use the note details passed as parameters to update the found note in the ArrayList.
        if ((foundMovie != null) && (movie != null)) {
            foundMovie.movieTitle = movie.movieTitle
            foundMovie.movieGenre = movie.movieGenre
            foundMovie.movieAgeRating = movie.movieAgeRating
            foundMovie.movieStars = movie.movieStars
            foundMovie.movieStatus = movie.movieStatus

            return true
        }

        // if the movie was not found, return false, indicating that the update was not successful
        return false
    }

    fun archiveMovie(id: Int): Boolean {
        val foundMovie = findMovies(id)
        if ((foundMovie != null) && (!foundMovie.isMovieArchived)) {
            foundMovie.isMovieArchived = true
            return true
        }
        return false
    }

    // ----------------------------------------------
    //  LISTING METHODS FOR MOVIE ArrayList
    // ----------------------------------------------
    fun listAllMovies() =
        if (movies.isEmpty()) "No notes stored"
        else formatListString(movies)

    fun listActiveMovies() =
        if (numberOfActiveMovies() == 0) "No active notes stored"
        else formatListString(movies.filter { movies -> !movies.isMovieArchived })

    fun listArchivedMovies() =
        if (numberOfArchivedMovies() == 0) "No archived notes stored"
        else formatListString(movies.filter { movies -> movies.isMovieArchived })

    // ----------------------------------------------
    //  COUNTING METHODS FOR MOVIE ArrayList
    // ----------------------------------------------
    fun numberOfMovies() = movies.size
    fun numberOfArchivedMovies(): Int = movies.count { movie: Movie -> movie.isMovieArchived }
    fun numberOfActiveMovies(): Int = movies.count { movie: Movie -> !movie.isMovieArchived }

    // ----------------------------------------------
    //  SEARCHING METHODS
    // ---------------------------------------------
    fun findMovies(noteId: Int) = movies.find { movie -> movie.movieId == noteId }

    fun searchMoviesByTitle(searchString: String) =
        formatListString(
            movies.filter { movie -> movie.movieTitle.contains(searchString, ignoreCase = true) }
        )

    fun writeReceiptToFile(){
        val fileName = "cinemas.txt"
        val myFile = File(fileName)

        val cinemaTitle = ScannerInput.readNextLine("Enter title of cinema: ")
        val saleDateTime = ScannerInput.readNextLine("Enter date/time of sale: ")
        val transactionNumber = ScannerInput.readNextInt("Enter transaction number: ")
        val cinemaMovieTitle = ScannerInput.readNextLine("Enter cinema movie title: ")
        val costOfMovie = ScannerInput.readNextDouble("Enter cost of movie: ")
        val tax = ScannerInput.readNextDouble("Enter tax: ")

        myFile.writeText(cinemaTitle)
        File("cinemas.txt").appendText("\n" + saleDateTime)
        File("cinemas.txt").appendText("\n" + transactionNumber.toString())
        File("cinemas.txt").appendText("\n" + cinemaMovieTitle)
        File("cinemas.txt").appendText("\n" + costOfMovie)
        File("cinemas.txt").appendText("\n" + tax.toString())

        if (myFile != null) {
            println("Receipt is saved")
        } else println("Receipt not saved")

    }

    fun readReceiptFile(){
        val bufferedReader: BufferedReader = File("cinemas.txt").bufferedReader()
        val fileInput = bufferedReader.use { it.readLines()
        }
        println(fileInput)
    }
}
