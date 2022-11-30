import controllers.NoteAPI
import models.Item
import models.Movie
import utils.ScannerInput.readNextChar
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import kotlin.system.exitProcess

private val noteAPI = NoteAPI()

fun main() = runMenu()

fun runMenu() {
    do {
        when (val option = mainMenu()) {
            1 -> addMovie()
            2 -> listMovies()
            3 -> updateMovie()
            4 -> deleteMovie()
            5 -> archiveMovie()
            6 -> addItemToMovie()
            7 -> updateItemContentsInMovie()
            //8 -> deleteAnMovie()
            8 -> markMovieStatus()
            9 -> searchMovies()
            //15 -> searchItems()
            //16 -> listToDoItems()
            0 -> exitApp()
            else -> println("Invalid menu choice: $option")
        }
    } while (true)
}

fun mainMenu() = readNextInt(
        """ 
         > -----------------------------------------------------  
         > |                  CINEMA APP                        |
         > -----------------------------------------------------  
         > | Movie MENU                                         |
         > |   1) Add a movie                                   |
         > |   2) List movies                                   |
         > |   3) Update a movie                                |
         > |   4) Delete a movie                                |
         > |   5) Archive a movie                               |
         > -----------------------------------------------------  
         > | ITEM MENU                                          | 
         > |   6) Add item to a movie                           |
         > |   7) Update item contents on a movie               |
         > |   8) Delete item from a movie                      |
         > |   9) Mark item as complete/todo                    | 
         > -----------------------------------------------------  
         > | REPORT MENU FOR NOTES                             | 
         > |   10) Search for all notes (by note title)        |
         > |   11) .....                                       |
         > |   12) .....                                       |
         > |   13) .....                                       |
         > |   14) .....                                       |
         > -----------------------------------------------------  
         > | REPORT MENU FOR ITEMS                             |                                
         > |   15) Search for all items (by item description)  |
         > |   16) List TODO Items                             |
         > |   17) .....                                       |
         > |   18) .....                                       |
         > |   19) .....                                       |
         > -----------------------------------------------------  
         > |   0) Exit                                         |
         > -----------------------------------------------------  
         > ==>> """.trimMargin(">")
    )

//------------------------------------
//NOTE MENU
//------------------------------------
fun addMovie() {
    val movieTitle = readNextLine("Enter a title for the movie: ")
    val movieGenre = readNextLine("Enter movie genre")
    val movieAgeRating = readNextInt("Enter movie age")
    val movieStars = readNextInt("Enter number of stars")
    val movieStatus = readNextLine("Enter movie status")
    val movieNumber = readNextInt("Enter a priority (1-low, 2, 3, 4, 5-high): ")
    //val noteCategory = readNextLine("Enter a category for the movie: ")
    val isAdded = noteAPI.add(Movie(movieTitle = movieTitle, movieGenre = movieGenre, movieAgeRating = movieAgeRating, movieStars = movieStars, movieStatus = movieStatus, movieNumber = movieNumber))

    if (isAdded) {
        println("Added Successfully")
    } else {
        println("Add Failed")
    }
}

fun listMovies() {
    if (noteAPI.numberOfMovies() > 0) {
        val option = readNextInt(
            """
                  > --------------------------------
                  > |   1) View ALL notes          |
                  > |   2) View ACTIVE notes       |
                  > |   3) View ARCHIVED notes     |
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

fun listAllMovies() = println(noteAPI.listAllMovies())
fun listActiveMovies() = println(noteAPI.listActiveMovies())
fun listArchivedMovies() = println(noteAPI.listArchivedMovies())

fun updateMovie() {
    listMovies()
    if (noteAPI.numberOfMovies() > 0) {
        // only ask the user to choose the note if notes exist
        val id = readNextInt("Enter the id of the note to update: ")
        if (noteAPI.findMovies(id) != null) {
            val movieTitle = readNextLine("Enter a title for the note: ")
            val movieGenre = readNextLine("Enter a priority (1-low, 2, 3, 4, 5-high): ")
            val movieAgeRating = readNextInt("Enter a category for the note: ")
            val movieStars = readNextInt("Enter star rating for Movie")
            val movieStatus = readNextLine("Enter star rating for Movie")
            val movieNumber = readNextInt("Enter star rating for Movie")

            // pass the index of the note and the new note details to NoteAPI for updating and check for success.
            if (noteAPI.update(id, Movie(0, movieTitle, movieGenre, movieAgeRating, movieStars, movieStatus, movieNumber, false))){
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
    if (noteAPI.numberOfMovies() > 0) {
        // only ask the user to choose the note to delete if notes exist
        val id = readNextInt("Enter the id of the note to delete: ")
        // pass the index of the note to NoteAPI for deleting and check for success.
        val noteToDelete = noteAPI.delete(id)
        if (noteToDelete) {
            println("Delete Successful!")
        } else {
            println("Delete NOT Successful")
        }
    }
}

fun archiveMovie() {
    listActiveMovies()
    if (noteAPI.numberOfActiveMovies() > 0) {
        // only ask the user to choose the note to archive if active notes exist
        val id = readNextInt("Enter the id of the note to archive: ")
        // pass the index of the note to NoteAPI for archiving and check for success.
        if (noteAPI.archiveMovie(id)) {
            println("Archive Successful!")
        } else {
            println("Archive NOT Successful")
        }
    }
}

//-------------------------------------------
//ITEM MENU (only available for active notes)
//-------------------------------------------
private fun addItemToMovie() {
    val note: Movie? = askUserToChooseActiveMovie()
    if (note != null) {
        if (note.addItem(Item(itemContents = readNextLine("\t Item Contents: "))))
            println("Add Successful!")
        else println("Add NOT Successful")
    }
}

fun updateItemContentsInMovie() {
    val note: Movie? = askUserToChooseActiveMovie()
    if (note != null) {
        val item: Item? = askUserToChooseItem(note)
        if (item != null) {
            val newContents = readNextLine("Enter new contents: ")
            if (note.update(item.itemId, Item(itemContents = newContents))) {
                println("Item contents updated")
            } else {
                println("Item contents NOT updated")
            }
        } else {
            println("Invalid Item Id")
        }
    }
}

fun deleteAnItem() {
    val note: Movie? = askUserToChooseActiveMovie()
    if (note != null) {
        val item: Item? = askUserToChooseItem(note)
        if (item != null) {
            val isDeleted = note.delete(item.itemId)
            if (isDeleted) {
                println("Delete Successful!")
            } else {
                println("Delete NOT Successful")
            }
        }
    }
}

fun markMovieStatus() {
    val note: Movie? = askUserToChooseActiveMovie()
    if (note != null) {
        val item: Item? = askUserToChooseItem(note)
       if (item != null) {
                var changeStatus = 'X'
                if (item.isItemComplete) {
                    changeStatus = readNextChar("The item is currently complete...do you want to mark it as TODO?")
                    if ((changeStatus == 'Y') ||  (changeStatus == 'y'))
                        item.isItemComplete = false
                }
                else {
                    changeStatus = readNextChar("The item is currently TODO...do you want to mark it as Complete?")
                    if ((changeStatus == 'Y') ||  (changeStatus == 'y'))
                        item.isItemComplete = true
                }
       }
    }
}

//------------------------------------
//NOTE REPORTS MENU
//------------------------------------
fun searchMovies() {
    val searchTitle = readNextLine("Enter the description to search by: ")
    val searchResults = noteAPI.searchMoviesByTitle(searchTitle)
    if (searchResults.isEmpty()) {
        println("No notes found")
    } else {
        println(searchResults)
    }
}

//------------------------------------
//ITEM REPORTS MENU
//------------------------------------
fun searchItems() {
    val searchContents = readNextLine("Enter the item contents to search by: ")
    val searchResults = noteAPI.searchItemByContents(searchContents)
    if (searchResults.isEmpty()) {
        println("No items found")
    } else {
        println(searchResults)
    }
}

// fun markMovieStatus(){
   // if (noteAPI.markMovieStatus() > 0) {
       // println("Total TODO items: ${noteAPI.numberOfToDoItems()}")
   // }
    // println(noteAPI.listTodoItems())
// }


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
    if (noteAPI.numberOfActiveMovies() > 0) {
        val note = noteAPI.findMovies(readNextInt("\nEnter the id of the note: "))
        if (note != null) {
            if (note.isMovieArchived) {
                println("Note is NOT Active, it is Archived")
            } else {
                return note //chosen note is active
            }
        } else {
            println("Note id is not valid")
        }
    }
    return null //selected note is not active
}

private fun askUserToChooseItem(note: Movie): Item? {
    if (note.numberOfItems() > 0) {
        print(note.listItems())
        return note.findOne(readNextInt("\nEnter the id of the item: "))
    }
    else{
        println("No items for chosen note")
        return null
    }
}
