package cs246.scripturememorization;

import org.junit.Test;

import java.io.File;
import java.util.Date;

import static org.junit.Assert.*;


public class StoreDataUnitTest {

    @Test
    public void writeDataTest() {
        String filename = "numbers.txt";
        File file = new File(filename);
        assertTrue(file.canRead());
    }

    @Test
    public void readStoredData(){
        //calls "readData" method to get stored information
        // - converts JSON to Java object

        // testScripture = readData();

        Scripture testScripture = new Scripture();

        assertEquals("Book of Mormon",testScripture.volume);
        assertEquals("1 Nephi", testScripture.book);
        assertEquals("1", testScripture.chapter);
        assertEquals("1", testScripture.verse);
        assertEquals("I Nephi, having been born of goodly parents", testScripture.text);

        //assertEquals(new Date(),);


        //
        /*
        * volume
        * book
        * chapter
        * verse
        * text
        * memorized
        * firstMemorized
        * lastReviewed
        * percentCorrect
        * */

        /*
        {
        volume: "Book of Mormon",
        book: "1 Nephi",
        chapter: "1",
        verse: "1",
        text: "I Nephi, having been born of goodly parents",
        memorized: false,
        firstMemorized: "",
        lastReviewed: "2018-06-12 20:00:00",
        percentCorrect: 30
        }


         */


    }

}
