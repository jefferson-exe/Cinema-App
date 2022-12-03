package models

data class Cinema (var cinemaId: Int = 0, var cinemaAddress : String, var cinemaName : String){

    override fun toString(): String {
        return "ID: $cinemaId, Cinema Address $cinemaAddress, Cinema Name: $cinemaName"

    }

}