package models

import utils.Utilities

data class Movie(var movieId: Int = 0,
                var movieTitle: String,
                var movieGenre: String,
                var movieAgeRating: Int = 0,
                var movieStars: Int = 0,
                var movieStatus: String,
                var isMovieArchived: Boolean = false,
                var movies : MutableSet<Movie> = mutableSetOf())
{
    private var lastItemId = 0
    private fun getItemId() = lastItemId++

    fun addMovie(movie: Movie) : Boolean {
        movie.movieId = getItemId()
        return movies.add(movie)
    }

    fun numberOfMovies() = movies.size

    fun findOne(id: Int): Movie?{
        return movies.find{ movie -> movie.movieId == id }
    }

    fun delete(id: Int): Boolean {
        return movies.removeIf { movie -> movie.movieId == id}
    }

    override fun toString(): String {
        val archived = if (isMovieArchived) 'Y' else 'N'
        return "ID : $movieId, Movie: $movieTitle, Genre: $movieGenre, Age Rating: $movieAgeRating, Movie Stars: $movieStars, Movie Status: $movieStatus, Archived: $archived"
    }

}