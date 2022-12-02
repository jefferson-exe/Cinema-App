package controllers

import models.Movie
import utils.Utilities.formatListString
import java.util.ArrayList

class NoteAPI() {

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
            //foundMovie.movieCategory = movie.movieCategory
            return true
        }

        // if the movie was not found, return false, indicating that the update was not successful
        return false
    }

    fun archiveMovie(id: Int): Boolean {
        val foundMovie = findMovies(id)
        if (( foundMovie != null) && (!foundMovie.isMovieArchived)
            && ( foundMovie.checkMovieCompletionStatus())) {
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
    fun findMovies(noteId : Int) =  movies.find{ movie -> movie.movieId == noteId }

    fun searchMoviesByTitle(searchString: String) =
       formatListString(
            movies.filter { movie -> movie.movieTitle.contains(searchString, ignoreCase = true) }
        )

    fun searchItemByContents(searchString: String): String {
        return if (numberOfMovies() == 0) "No notes stored"
        else {
            var listOfMovies = ""
            for (movie in movies) {
                for (item in movie.items) {
                    if (item.itemContents.contains(searchString, ignoreCase = true)) {
                        listOfMovies += "${movie.movieId}: ${movie.movieTitle} \n\t${item}\n"
                    }
                }
            }
            if (listOfMovies == "") "No items found for: $searchString"
            else listOfMovies
        }
    }

     // ----------------------------------------------
      // LISTING METHODS FOR ITEMS
     // ----------------------------------------------
     fun listToDoItems(): String =
          if (numberOfMovies() == 0) "No notes stored"
          else {
              var listOfToDoItems = ""
              for (movie in movies) {
                  for (item in movie.items) {
                      if (!item.isItemComplete) {
                          listOfToDoItems += movie.movieTitle + ": " + item.itemContents + "\n"
                      }
                  }
              }
              listOfToDoItems
          }

     // ----------------------------------------------
      // COUNTING METHODS FOR ITEMS
     // ----------------------------------------------
     fun numberOfToDoItems(): Int {
         var numberOfToDoItems = 0
         for (movie in movies) {
             for (item in movie.items) {
                 if (!item.isItemComplete) {
                     numberOfToDoItems++
                 }
             }
         }
         return numberOfToDoItems
    }

}
