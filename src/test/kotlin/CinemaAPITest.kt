package controllers

import models.Cinema
import org.junit.jupiter.api.*
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
class CinemaAPITest {
    private var learnKotlin: Cinema? = null
    private var summerHoliday: Cinema? = null
    private var codeApp: Cinema? = null
    private var testApp: Cinema? = null
    private var swim: Cinema? = null
    private var populatedNotes: CinemaAPI? = CinemaAPI()
    private var emptyNotes: CinemaAPI? = CinemaAPI()

    @BeforeEach
    fun setup() {
        learnKotlin = Cinema(0, "Pizza", "Car", 0, 4, "Shower", false)
        summerHoliday = Cinema(1, "Cake", "Work", 0, 4, "Work", false)
        codeApp = Cinema(2, "Chocolate", "Pineapple", 0, 4, "Earth", false)
        testApp = Cinema(3, "Cookie", "Mall", 0, 4, "Pie", false)
        swim = Cinema(4, "Mango", "Battery", 0, 4, "Seagull", false)

        // adding 5 Note to the notes api
        populatedNotes!!.add(learnKotlin!!)
        populatedNotes!!.add(summerHoliday!!)
        populatedNotes!!.add(codeApp!!)
        populatedNotes!!.add(testApp!!)
        populatedNotes!!.add(swim!!)
    }

    @AfterEach
    fun tearDown() {
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
        fun `adding a Cinema to a populated list adds to ArrayList`() {
            val newCinema = Cinema(0, "Fruit", "Blueberry", 0, 4, "Salt", false)
            assertEquals(5, populatedNotes!!.numberOfCinemas())
            assertTrue(populatedNotes!!.add(newCinema))
            assertEquals(6, populatedNotes!!.numberOfCinemas())
            assertEquals(newCinema, populatedNotes!!.findCinemas(populatedNotes!!.numberOfCinemas() - 1))
        }

        @Test
        fun `adding a Cinema to an empty list adds to ArrayList`() {
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
            assertEquals(0, emptyNotes!!.numberOfArchivedCinemas())
            assertTrue(
            emptyNotes!!.listArchivedCinemas().lowercase().contains("no archived notes"))
         }

        @Test
        fun `listArchivedNotes returns archived notes when ArrayList has archived notes stored`() {
            Assertions.assertEquals(0, populatedNotes!!.numberOfArchivedCinemas())
            val archivedNotesString = populatedNotes!!.listArchivedCinemas().lowercase()
            assertFalse(archivedNotesString.contains("learning kotlin"))
            assertFalse(archivedNotesString.contains("code app"))
            assertFalse(archivedNotesString.contains("summer holiday"))
            assertFalse(archivedNotesString.contains("test app"))
            assertFalse(archivedNotesString.contains("swim"))
        }

        @Test
        fun `listActiveNotes returns active notes when ArrayList has active notes stored`() {
            Assertions.assertEquals(5, populatedNotes!!.numberOfActiveCinemas())
            val activeNotesString = populatedNotes!!.listCurrentCinema().lowercase()
            assertFalse(activeNotesString.contains("learning kotlin"))
            assertFalse(activeNotesString.contains("code app"))
            assertFalse(activeNotesString.contains("summer holiday"))
            assertFalse(activeNotesString.contains("test app"))
            assertFalse(activeNotesString.contains("swim"))
        }
    }

    @Nested
    inner class DeleteNotes {

        @Test
        fun `deleting a Note that does not exist, returns null`() {
            Assertions.assertNull(emptyNotes!!.deleteCinema(0))
            Assertions.assertNull(populatedNotes!!.deleteCinema(-1))
            Assertions.assertNull(populatedNotes!!.deleteCinema(5))
        }

        @Test
        fun `deleting a note that exists delete and returns deleted object`() {
            Assertions.assertEquals(5, populatedNotes!!.numberOfCinemas())
            assertEquals(swim, populatedNotes!!.deleteCinema(4))
            Assertions.assertEquals(4, populatedNotes!!.numberOfCinemas())
            assertEquals(learnKotlin, populatedNotes!!.deleteCinema(0))
            Assertions.assertEquals(3, populatedNotes!!.numberOfCinemas())
        }
    }

    @Nested
    inner class UpdateNotes {
        @Test
        fun `updating a note that does not exist returns false`() {
            assertFalse(populatedNotes!!.update(6, Cinema(0, "Fruit", "Blueberry", 0, 4, "Salt", false)))
            assertFalse(populatedNotes!!.update(-1, Cinema(0, "Fruit", "Blueberry", 0, 4, "Salt", false)))
            assertFalse(emptyNotes!!.update(0, Cinema(0, "Fruit", "Blueberry", 0, 4, "Salt", false)))
        }

        @Test
        fun `updating a note that exists returns true and updates`() {
            //check note 5 exists and check the contents
            assertEquals(swim, populatedNotes!!.findCinemas(4))
            Assertions.assertEquals("Mango", populatedNotes!!.findCinemas(4)!!.cinemaName)
            Assertions.assertEquals(0, populatedNotes!!.findCinemas(4)!!.cinemaPhone)
            Assertions.assertEquals("Seagull", populatedNotes!!.findCinemas(4)!!.cinemaEmail)

            //update note 5 with new information and ensure contents updated successfully
            assertTrue(populatedNotes!!.update(4, Cinema(0, "Fruit", "Blueberry", 0, 4, "Salt", false)))
            Assertions.assertEquals("Fruit", populatedNotes!!.findCinemas(4)!!.cinemaName)
            Assertions.assertEquals(0, populatedNotes!!.findCinemas(4)!!.cinemaPhone)
            Assertions.assertEquals("Salt", populatedNotes!!.findCinemas(4)!!.cinemaEmail)
        }
    }
}
