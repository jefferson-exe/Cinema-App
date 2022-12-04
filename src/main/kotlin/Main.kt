import controllers.CinemaAPI
import controllers.MovieAPI
import models.Cinema
import models.Movie
import utils.ScannerInput.readNextChar
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
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
            5 -> archiveMovie()
            6 -> addCinema()
            7 -> listCinemas()
            //7 -> askUserToChooseActiveMovie()
            9 -> searchMovies()
            0 -> exitApp()
            else -> println("Invalid menu choice: $option")
        }
    } while (true)
}

fun mainMenu() = readNextInt(
        """ 
         > -----------------------------------------------------  
         > |                  CINEMA APP                       |
         > -----------------------------------------------------  
         > | Movie MENU                                        |
         > |   1) Add a movie                                  |
         > |   2) List movies                                  |
         > |   3) Update a movie                               |
         > |   4) Delete a movie                               |
         > |   5) Archive a movie                              |
         > -----------------------------------------------------  
         > | Cinema MENU                                       | 
         > |   6) Add cinema to a movie                        |
         > |   7) List cinemas                                 |
         > |   8) .............................                |
         > |   9) .............................                | 
         > -----------------------------------------------------    
         > |   0) Exit                                         |
         > -----------------------------------------------------  
         > ==>> """.trimMargin(">")
    )

//------------------------------------
//MOVIE MENU
//------------------------------------
fun addMovie() {
    val movieTitle = readNextLine("Enter a title for the movie\n: ")
    val movieGenre = readNextLine("Enter movie genre\n: ")
    val movieAgeRating = readNextInt("Enter movie age\n: ")
    val movieStars = readNextInt("Enter number of stars\n: ")
    val movieStatus = readNextLine("Enter movie status\n: ")
    val movieNumber = readNextInt("Enter movie number\n: ")
    val isAdded = movieAPI.add(Movie(movieTitle = movieTitle, movieGenre = movieGenre, movieAgeRating = movieAgeRating, movieStars = movieStars, movieStatus = movieStatus, movieNumber = movieNumber))

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
                  > |   3) View ARCHIVED Movies     |
                  > --------------------------------
         > ==>> """.trimMargin(">")
        )

        when (option) {
            1 -> listAllMovies()
            2 -> listActiveMovies()
            3 -> listArchivedMovies()
            else -> println("Invalid option entered: $option")
        }
    } else {
        println("Option Invalid - No notes stored")
    }
}

fun listAllMovies() = println(movieAPI.listAllMovies())
fun listActiveMovies() = println(movieAPI.listActiveMovies())
fun listArchivedMovies() = println(movieAPI.listArchivedMovies())

fun updateMovie() {
    listMovies()
    if (movieAPI.numberOfMovies() > 0) {
        // only ask the user to choose the note if notes exist
        val id = readNextInt("Enter the id of the note to update: ")
        if (movieAPI.findMovies(id) != null) {
            val movieTitle = readNextLine("Enter a title for the note: ")
            val movieGenre = readNextLine("Enter a priority (1-low, 2, 3, 4, 5-high): ")
            val movieAgeRating = readNextInt("Enter a category for the note: ")
            val movieStars = readNextInt("Enter star rating for Movie")
            val movieStatus = readNextLine("Enter star rating for Movie")
            val movieNumber = readNextInt("Enter star rating for Movie")

            // pass the index of the note and the new note details to NoteAPI for updating and check for success.
            if (movieAPI.update(id, Movie(0, movieTitle, movieGenre, movieAgeRating, movieStars, movieStatus, movieNumber, false))){
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

fun archiveMovie() {
    listActiveMovies()
    if (movieAPI.numberOfActiveMovies() > 0) {
        // only ask the user to choose the note to archive if active notes exist
        val id = readNextInt("Enter the id of the note to archive: ")
        // pass the index of the note to NoteAPI for archiving and check for success.
        if (movieAPI.archiveMovie(id)) {
            println("Archive Successful!")
        } else {
            println("Archive NOT Successful")
        }
    }
}

//------------------------------------
//CINEMA MENU
//------------------------------------
fun addCinema() {
    val cinemaName = readNextLine("Enter name for cinema\n: ")
    val cinemaAddress = readNextLine("Enter cinema address\n: ")
    val cinemaPhone = readNextInt("Enter cinema phone number\n: ")
    val numberOfScreens = readNextInt("Enter number of screens for cinema\n: ")
    val cinemaEmail = readNextLine("Enter cinema email\n: ")
    val isAdded = cinemaAPI.add(Cinema(cinemaName = cinemaName,
        cinemaAddress = cinemaAddress,
        cinemaPhone = cinemaPhone,
        numberOfScreens = numberOfScreens,
        cinemaEmail = cinemaEmail))

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
                  > --------------------------------
                  > |   1) View ALL Cinemas          |
                  > |   2) View Current Cinema       |
                  > |   3) View ARCHIVED Cinemas     |
                  > --------------------------------
         > ==>> """.trimMargin(">")
        )

        when (option) {
            1 -> listAllCinemas()
            2 -> listCurrentCinema()
            3 -> listArchivedCinemas()
            else -> println("Invalid option entered: $option")
        }
    } else {
        println("Option Invalid - No notes stored")
    }
}

fun listAllCinemas() = println(cinemaAPI.listAllCinemas())
fun listCurrentCinema() = println(cinemaAPI.listCurrentCinema())
fun listArchivedCinemas() = println(cinemaAPI.listArchivedCinemas())

//------------------------------------
//NOTE REPORTS MENU
//------------------------------------
fun searchMovies() {
    val searchTitle = readNextLine("Enter the description to search by: ")
    val searchResults = movieAPI.searchMoviesByTitle(searchTitle)
    if (searchResults.isEmpty()) {
        println("No notes found")
    } else {
        println(searchResults)
    }
}


//------------------------------------
// Exit App
//------------------------------------
fun exitApp() {
    println("Exiting...bye")
    exitProcess(0)
}

//------------------------------------
//HELPER FUNCTIONS
//------------------------------------

private fun askUserToChooseActiveMovie(): Movie? {
    listActiveMovies()
    if (movieAPI.numberOfActiveMovies() > 0) {
        val movie = movieAPI.findMovies(readNextInt("\nEnter the id of the movie: "))
        if (movie != null) {
            if (movie.isMovieArchived) {
                println("Movie is NOT Active, it is Archived")
            } else {
                return movie //chosen movie is active
            }
        } else {
            println("Movie id is not valid")
        }
    }
    return null //selected note is not active
}
