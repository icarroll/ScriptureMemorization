package cs246.scripturememorization;

public class Verses {

    private String verseTitle;
    private String verseId;

    public Verses(String verseTitle, String verseId) {
        this.verseTitle = verseTitle;
        this.verseId = verseId;
    }

    public String getVerseTitle() { return this.verseTitle; }
    public String getVerseId() {
        return this.verseId;
    }
}