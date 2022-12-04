package models

data class Cinema (var cinemaId: Int = 0,
                   var cinemaName : String,
                   var cinemaAddress : String,
                   var cinemaPhone: Int,
                   var numberOfScreens: Int,
                   var cinemaEmail: String,
                   var isCinemaArchived: Boolean = false,
                   var cinemas : MutableSet<Cinema> = mutableSetOf()){

    private var lastItemId = 0
    private fun getItemId() = lastItemId++

    fun addCinema(item: Cinema) : Boolean {
        item.cinemaId = getItemId()
        return cinemas.add(item)
    }

    fun numberOfCinemas() = cinemas.size
    override fun toString(): String {
        return "ID: $cinemaId, Cinema Name $cinemaAddress, Cinema Address: $cinemaName, Cinema Phone $cinemaPhone, Cinema Address: $numberOfScreens, Cinema Email: $cinemaEmail"

    }

}