package cs246.scripturememorization;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Scripture implements Parcelable{
    public String volume;
    public String book;
    public String chapter;
    public String verse;
    public String text;
    public boolean memorized;
    Date dateMemorized;
    Date lastReviewed;
    int percentCorrect;

    public Scripture(Parcel in) {
        volume = in.readString();
        book = in.readString();
        chapter = in.readString();
        verse = in.readString();
        text = in.readString();
        if (in.readInt() == 1) {
            memorized = true;
        }
        else {
            memorized = false;
        }
        dateMemorized = new Date(in.readString());
        lastReviewed = new Date(in.readString());
        percentCorrect = in.readInt();
    }

    public Scripture() {
        volume = "Book of Mormon";
        book = "1 Nephi";
        chapter = "1";
        verse = "1";
        text = "I Nephi, having been born of goodly parents";
        memorized = false;
        dateMemorized = null;
        lastReviewed = null;
        percentCorrect = 0;
    }

    public Scripture(String volume, String book, String chapter, String verse, String text) {
        this.volume = volume;
        this.book = book;
        this.chapter = chapter;
        this.verse = verse;
        this.text = text;
        memorized = false;
        dateMemorized = null;
        lastReviewed = null;
        percentCorrect = 0;
    }

    @Override
    public String toString() {
        return(String.format("%s %s:%s passed:%b", book, chapter, verse, memorized));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        @Override
        public Scripture createFromParcel(Parcel source) {
            return new Scripture(source);
        }

        public Scripture[] newArray(int size) {
            return new Scripture[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(volume);
        dest.writeString(book);
        dest.writeString(chapter);
        dest.writeString(verse);
        dest.writeString(text);
        if (memorized) {
            dest.writeInt(1);
        }
        else {
            dest.writeInt(0);
        }
        dest.writeString(dateMemorized.toString());
        dest.writeString(lastReviewed.toString());
        dest.writeInt(percentCorrect);
    }
}
