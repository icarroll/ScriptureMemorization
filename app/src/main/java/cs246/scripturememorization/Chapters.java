package cs246.scripturememorization;

public class Chapters {

    private String chapterTitle;
    private String chapterId;

    public Chapters(String chapterTitle, String chapterId) {
        this.chapterTitle = chapterTitle;
        this.chapterId = chapterId;
    }

    public String getChapterTitle() { return this.chapterTitle; }
    public String getChapterId() {
        return this.chapterId;
    }
}
