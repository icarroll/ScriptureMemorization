package cs246.scripturememorization;

import java.util.Date;

public class Scripture {
    public String volume;
    public String book;
    public String chapter;
    public String verse;
    public String text;
    public boolean memorized;
    Date dateMemorized;
    Date lastReviewed;
    int percentCorrect;

    public Scripture() {
        volume = "Book of Mormon";
        book = "1 Nephi";
        chapter = "1";
        verse = "1";
        text = "I Nephi, having been born of goodly parents";

    }
}
