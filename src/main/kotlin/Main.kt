import controllers.CinemaAPI
import controllers.MovieAPI
import models.Cinema
import models.Movie
import utils.ScannerInput.readNextDouble
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import java.io.BufferedReader
import java.io.File
import kotlin.system.exitProcess

private val movieAPI = MovieAPI()
private val cinemaAPI = CinemaAPI()

fun main() = runMenu()

fun runMenu() {
    do {
        when (val option = mainMenu()) {
            1 -> addMovie()
            2 -> listMovies()
            3 -> updateMovie()
            4 -> deleteMovie()
            5 -> watchLaterMovies()
            6 -> searchMovies()
            7 -> askUserToChooseActiveMovie()
            8 -> addCinema()
            9 -> listCinemas()
            10 -> updateCinema()
            11 -> deleteCinema()
            12 -> markFavouriteCinema()
            13 -> writeReceiptToFile()
            14 -> readReceiptFile()
            100 -> cinemaMenu()
            200 -> movieMenu()
            300 -> readAndWriteMenu()
            0 -> exitApp()
            else -> println("Invalid menu choice: $option")
        }
    } while (true)
}

fun mainMenu() = readNextInt(

    """ 
         > ------------------------------------------------------  
         > | Welcome to the Cinema App! please choose an option |
         > ------------------------------------------------------  
         > | Read and write MENU                                |
         > |   100) Cinema Menu                                 | 
         > |   200) Movie Menu                                  |
         > |   300) Read and Write receipt Menu                 |
         > ------------------------------------------------------    
         > |   0) Exit                                          |
         > ------------------------------------------------------  
         > ==>> """.trimMargin(">"))

fun cinemaMenu(){
    val option = readNextInt("""
    > -----------------------------------------------------
    > |                  CINEMA APP                       |
    > -----------------------------------------------------
    > | Cinema MENU                                       |
    > |   8) Add a cinema                                 |
    > |   9) List cinemas                                 |
    > |   10) Update a cinema                             |
    > |   11) Delete a cinema                             |
    > |   12) Add favourite cinema                        |
    > |   13) Mark favourite cinema                       |
    > |   0) Exit                                         |
    > -----------------------------------------------------
    > ==>> """.trimMargin(">"))

    when (option) {
        8 -> addCinema()
        9 -> listCinemas()
        10 -> updateCinema()
        11 -> deleteCinema()
        12 -> markFavouriteCinema()
        else -> println("Invalid option entered: $option")
    }
}

fun movieMenu(){
    val option = readNextInt("""
    > -----------------------------------------------------  
    > |                  CINEMA APP                       |
    > -----------------------------------------------------  
    > | Movie MENU                                        |
    > |   1) Add a movie                                  |
    > |   2) List movies                                  |
    > |   3) Update a movie                               |
    > |   4) Delete a movie                               |
    > |   5) Add movie to watch later                     |
    > |   6) Search movies                                |
    > |   7) Ask user to choose active movie              |
    > |   0) Exit                                         |
    > -----------------------------------------------------  
    > ==>> """.trimMargin(">"))

    when (option) {
        1 -> addMovie()
        2 -> listMovies()
        3 -> updateMovie()
        4 -> deleteMovie()
        5 -> watchLaterMovies()
        6 -> searchMovies()
        7 -> askUserToChooseActiveMovie()
        else -> println("Invalid option entered: $option")
    }
}

fun readAndWriteMenu(){
    val option = readNextInt("""
    > -----------------------------------------------------
    > | Read and write MENU                               |
    > -----------------------------------------------------
    > |   13) Write receipt to file                       | 
    > |   14) Read receipt from file                      | 
    > -----------------------------------------------------    
    > |   0) Exit                                         |
    > ----------------------------------------------------- 
    > ==>> """.trimMargin(">"))

    when (option) {
        13 -> writeReceiptToFile()
        14 -> readReceiptFile()
        else -> println("Invalid option entered: $option")
    }
}

// ------------------------------------
// MOVIE MENU
// ------------------------------------
fun addMovie() {
    val movieTitle = readNextLine("Enter a title for the movie\n: ")
    val movieGenre = readNextLine("Enter movie genre\n: ")
    val movieAgeRating = readNextInt("Enter movie age\n: ")
    val movieStars = readNextInt("Enter number of stars\n: ")
    val movieStatus = readNextLine("Enter movie status\n: ")
    val isAdded = movieAPI.add(Movie(movieTitle = movieTitle, movieGenre = movieGenre, movieAgeRating = movieAgeRating, movieStars = movieStars, movieStatus = movieStatus))

    if (isAdded) {
        println("Added Successfully")
    } else {
        println("Add Failed")
    }
}

fun listMovies() {
    if (movieAPI.numberOfMovies() > 0) {
        val option = readNextInt(
            """
                  > --------------------------------
                  > |   1) View ALL Movies          |
                  > |   2) View ACTIVE Movies       |
                  > |   3) View favourite Movies     |
                  > --------------------------------
         > ==>> """.trimMargin(">")
        )

        when (option) {
            1 -> listAllMovies()
            2 -> listActiveMovies()
            3 -> listFavouriteMovies()
            else -> println("Invalid option entered: $option")
        }
    } else {
        println("Option Invalid - No movies stored")
    }
}

fun listAllMovies() = println(movieAPI.listAllMovies())
fun listActiveMovies() = println(movieAPI.listActiveMovies())
fun listFavouriteMovies() = println(movieAPI.listArchivedMovies())

fun updateMovie() {
    listMovies()
    if (movieAPI.numberOfMovies() > 0) {
        // only ask the user to choose the note if notes exist
        val id = readNextInt("Enter the id of the note to update: ")
        if (movieAPI.findMovies(id) != null) {
            val movieTitle = readNextLine("Enter a new title for movie\n: ")
            val movieGenre = readNextLine("Enter a new genre for movie\n: ")
            val movieAgeRating = readNextInt("Enter new age rating for movie: ")
            val movieStars = readNextInt("Enter new star rating for movie")
            val movieStatus = readNextLine("Enter new status for movie")

            // pass the index of the note and the new note details to NoteAPI for updating and check for success.
            if (movieAPI.update(id, Movie(0, movieTitle, movieGenre, movieAgeRating, movieStars, movieStatus, false))) {
                println("Update Successful")
            } else {
                println("Update Failed")
            }
        } else {
            println("There are no notes for this index number")
        }
    }
}

fun deleteMovie() {
    listMovies()
    if (movieAPI.numberOfMovies() > 0) {
        // only ask the user to choose the note to delete if notes exist
        val id = readNextInt("Enter the id of the note to delete: ")
        // pass the index of the note to NoteAPI for deleting and check for success.
        val noteToDelete = movieAPI.delete(id)
        if (noteToDelete) {
            println("Delete Successful!")
        } else {
            println("Delete NOT Successful")
        }
    }
}

fun watchLaterMovies() {
    listActiveMovies()
    if (movieAPI.numberOfActiveMovies() > 0) {
        // only ask the user to choose the movie to archive if active notes exist
        val id = readNextInt("Enter the id of the movie to watch for later: ")
        // pass the index of the note to MovieAPI for archiving and check for success.
        if (movieAPI.watchLaterMovies(id)) {
            println("Movie watch for later Successful!")
        } else {
            println("Mark watch for later not Successful")
        }
    }
}

// ------------------------------------
// CINEMA MENU
// ------------------------------------
fun addCinema() {
    val cinemaName = readNextLine("Enter name for cinema\n: ")
    val cinemaAddress = readNextLine("Enter cinema address\n: ")
    val cinemaPhone = readNextInt("Enter cinema phone number\n: ")
    val numberOfScreens = readNextInt("Enter number of screens for cinema\n: ")
    val cinemaEmail = readNextLine("Enter cinema email\n: ")
    val isAdded = cinemaAPI.add(
        Cinema(
            cinemaName = cinemaName,
            cinemaAddress = cinemaAddress,
            cinemaPhone = cinemaPhone,
            numberOfScreens = numberOfScreens,
            cinemaEmail = cinemaEmail
        )
    )

    if (isAdded) {
        println("Added Successfully")
    } else {
        println("Add Failed")
    }
}

fun listCinemas() {
    if (cinemaAPI.numberOfCinemas() > 0) {
        val option = readNextInt(
            """
                  > ----------------------------------
                  > |   1) View ALL Cinemas          |
                  > |   2) View Current Cinema       |
                  > |   3) View ARCHIVED Cinemas     |
                  > ----------------------------------
         > ==>> """.trimMargin(">")
        )

        when (option) {
            1 -> listAllCinemas()
            2 -> listCurrentCinema()
            3 -> listFavouriteCinemas()
            else -> println("Invalid option entered: $option")
        }
    } else {
        println("Option Invalid - No notes stored")
    }
}

fun listAllCinemas() = println(cinemaAPI.listAllCinemas())
fun listCurrentCinema() = println(cinemaAPI.listCurrentCinema())
fun listFavouriteCinemas() = println(cinemaAPI.listArchivedCinemas())

fun updateCinema() {
    listMovies()
    if (cinemaAPI.numberOfCinemas() > 0) {
        // only ask the user to choose the cinema if cinemas exist
        val id = readNextInt("Enter the id of the cinema to update: ")
        if (cinemaAPI.findCinemas(id) != null) {
            val cinemaName = readNextLine("Enter new name for cinema\n: ")
            val cinemaAddress = readNextLine("Enter new cinema address\n: ")
            val cinemaPhone = readNextInt("Enter cinema new phone number\n: ")
            val numberOfScreens = readNextInt("Enter new number of screens for cinema\n: ")
            val cinemaEmail = readNextLine("Enter new cinema email\n: ")

            // pass the index of the cinema and the new cinema details to CinemaAPI for updating and check for success.
            if (cinemaAPI.update(id, Cinema(0, cinemaName, cinemaAddress, cinemaPhone, numberOfScreens, cinemaEmail, false))) {
                println("Update Successful")
            } else {
                println("Update Failed")
            }
        } else {
            println("There is no cinema with this index number")
        }
    }
}

fun deleteCinema() {
    listAllCinemas()
    if (cinemaAPI.numberOfCinemas() > 0) {
        // only ask the user to choose the cinema to delete if cinema exist
        val indexToDelete = readNextInt("Enter the index of the note to delete: ")
        // pass the index of the cinema to CinemaAPI for deleting and check for success.
        val cinemaToDelete = cinemaAPI.deleteCinema(indexToDelete)
        if (cinemaToDelete != null) {
            println("Delete Successful! Deleted cinema: ${cinemaToDelete.cinemaName}")
        } else {
            println("Delete NOT Successful")
        }
    }
}

fun markFavouriteCinema() {
    listCurrentCinema()
    if (cinemaAPI.numberOfCinemas() > 0) {
        // only ask the user to choose the cinema to mark favourite if current cinema exist
        val id = readNextInt("Enter the id of the cinema to mark favourite: ")
        // pass the index of the cinema to CinemaAPI for mark favourite and check for success.
        if (cinemaAPI.archiveCinema(id)) {
            println("Mark as favourite Successful!")
        } else {
            println("Mark as favourite NOT Successful")
        }
    }
}

fun writeReceiptToFile(){
    println("------------ Receipt ------------")
    movieAPI.writeReceiptToFile()
}

fun readReceiptFile(){
    movieAPI.readReceiptFile()
}

fun searchMovies() {
    val searchTitle = readNextLine("Enter the description to search by: ")
    val searchResults = movieAPI.searchMoviesByTitle(searchTitle)
    if (searchResults.isEmpty()) {
        println("No movies found")
    } else {
        println(searchResults)
    }
}

// ------------------------------------
// Exit App
// ------------------------------------
fun exitApp() {
    println("Exiting...bye")
    exitProcess(0)
}

private fun askUserToChooseActiveMovie(): Movie? {
    listActiveMovies()
    if (movieAPI.numberOfActiveMovies() > 0) {
        val movie = movieAPI.findMovies(readNextInt("\nEnter the id of the movie: "))
        if (movie != null) {
            if (movie.watchLaterMovie) {
                println("Movie is NOT Active, it is Archived")
            } else {
                return movie // chosen movie is active
            }
        } else {
            println("Movie id is not valid")
        }
    }
    return null // selected cinema is not active
}
