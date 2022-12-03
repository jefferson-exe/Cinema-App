package models

import utils.Utilities

data class Movie(var movieId: Int = 0,
                var movieTitle: String,
                var movieGenre: String,
                var movieAgeRating: Int = 0,
                var movieStars: Int = 0,
                var movieStatus: String,
                var movieNumber: Int,
                var isMovieArchived: Boolean = false,
                var items : MutableSet<Cinema> = mutableSetOf())
{
    private var lastItemId = 0
    private fun getItemId() = lastItemId++

    fun addCinema(item: Cinema) : Boolean {
        item.cinemaId = getItemId()
        return items.add(item)
    }

    fun numberOfCinema() = items.size

    fun findOne(id: Int): Cinema?{
        return items.find{ item -> item.cinemaId == id }
    }

    fun delete(id: Int): Boolean {
        return items.removeIf { item -> item.cinemaId == id}
    }

    fun update(id: Int, newCinema : Cinema): Boolean {
        val foundItem = findOne(id)

        //if the object exists, use the details passed in the newItem parameter to
        //update the found object in the Set
        if (foundItem != null){
            foundItem.cinemaAddress = newCinema.cinemaAddress
            //foundItem.isItemComplete = newCinema.isItemComplete
            return true
        }

        //if the object was not found, return false, indicating that the update was not successful
        return false
    }

    // fun checkMovieCompletionStatus(): Boolean {
        // if (items.isNotEmpty()) {
            // for (item in items) {
                // if (!item.isItemComplete) {
                    // return false
               //}
            // }
        // }
        // return true //a note with empty items can be archived, or all items are complete
    // }

    fun listItems() =
         if (items.isEmpty())  "\tNO ITEMS ADDED"
         else  Utilities.formatSetString(items)

    override fun toString(): String {
        val archived = if (isMovieArchived) 'Y' else 'N'
        return "ID : $movieId, Movie: $movieTitle, Genre: $movieGenre, Age Rating: $movieAgeRating, Movie Stars: $movieStars, Movie Status: $movieStatus, Movie Number: $movieNumber, Archived: $archived \n${listItems()}"
    }

}