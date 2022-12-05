package controllers

import models.Cinema
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertFalse
import kotlin.test.assertEquals

class CinemaAPITest {
    private var learnKotlin: Cinema? = null
    private var summerHoliday: Cinema? = null
    private var codeApp: Cinema? = null
    private var testApp: Cinema? = null
    private var swim: Cinema? = null
    private var populatedNotes: CinemaAPI? = CinemaAPI()
    private var emptyNotes: CinemaAPI? = CinemaAPI()

    @BeforeEach
    fun setup(){
        learnKotlin = Cinema(0, "Pizza", "Car", 0, 4, "Shower", false)
        summerHoliday = Cinema(1, "Cake", "Work", 0, 4, "Work", false)
        codeApp = Cinema(2, "Chocolate", "Pineapple", 0, 4, "Earth", false)
        testApp = Cinema(3, "Cookie", "Mall", 0, 4, "Pie", false)
        swim = Cinema(4, "Mango", "Battery", 0, 4, "Seagull", false)

        //adding 5 Note to the notes api
        populatedNotes!!.add(learnKotlin!!)
        populatedNotes!!.add(summerHoliday!!)
        populatedNotes!!.add(codeApp!!)
        populatedNotes!!.add(testApp!!)
        populatedNotes!!.add(swim!!)
    }

    @AfterEach
    fun tearDown(){
        learnKotlin = null
        summerHoliday = null
        codeApp = null
        testApp = null
        swim = null
        populatedNotes = null
        emptyNotes = null
    }

    @Nested
    inner class AddCinema {
        @Test
        fun `adding a Cinema to a populated list adds to ArrayList`(){
            val newCinema = Cinema(0, "Fruit", "Blueberry", 0, 4, "Salt", false)
            assertEquals(5, populatedNotes!!.numberOfCinemas())
            assertTrue(populatedNotes!!.add(newCinema))
            assertEquals(6, populatedNotes!!.numberOfCinemas())
            assertEquals(newCinema, populatedNotes!!.findCinemas(populatedNotes!!.numberOfCinemas() - 1))
        }

        @Test
        fun `adding a Cinema to an empty list adds to ArrayList`(){
            val newCinema = Cinema(0, "Pork", "Cranberry", 0, 4, "Hawk", false)
            assertEquals(0, emptyNotes!!.numberOfCinemas())
            assertTrue(emptyNotes!!.add(newCinema))
            assertEquals(1, emptyNotes!!.numberOfCinemas())
            assertEquals(newCinema, emptyNotes!!.findCinemas(emptyNotes!!.numberOfCinemas() - 1))
        }
    }

    @Nested
    inner class ListCinemas {


        @Test
        fun `listAllNotes returns No Notes Stored message when ArrayList is empty`() {
            assertEquals(0, emptyNotes!!.numberOfCinemas())
            assertTrue(emptyNotes!!.listAllCinemas().lowercase().contains("no notes"))
        }

        @Test
        fun `listAllNotes returns Notes when ArrayList has notes stored`() {
            assertEquals(5, populatedNotes!!.numberOfCinemas())
            val notesString = populatedNotes!!.listAllCinemas().lowercase()
            assertFalse(notesString.contains("learning kotlin"))
            assertFalse(notesString.contains("code app"))
            assertFalse(notesString.contains("test app"))
            assertFalse(notesString.contains("swim"))
            assertFalse(notesString.contains("summer holiday"))
        }

        @Test
        fun `listArchivedNotes returns no archived notes when ArrayList is empty`() {
            Assertions.assertEquals(0, emptyNotes!!.numberOfArchivedCinemas())
            assertTrue(
                emptyNotes!!.listArchivedCinemas().lowercase().contains("no archived notes")
            )
        }
    }
}