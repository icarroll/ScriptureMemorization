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
        percentCorrect = in.readInt();
        String temp = in.readString();
        if (temp.equals("null")) {
            dateMemorized = null;
        }
        else {
            dateMemorized = new Date(temp);
        }
        temp = in.readString();
        if (temp.equals("null")) {
            lastReviewed = null;
        }
        else {
            lastReviewed = new Date(temp);
        }
    }

    public Scripture() {
        volume = "Book of Mormon";
        book = "1 Nephi";
        chapter = "1";
        verse = "1";
        text = "I, Nephi, having been born of goodly parents, therefore I was taught somewhat in all the learning of my father; and having seen many afflictions in the course of my days, nevertheless, having been highly favored of the Lord in all my days; yea, having had a great knowledge of the goodness and the mysteries of God, therefore I make a record of my proceedings in my days.";
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
        dest.writeInt(percentCorrect);
        if (dateMemorized != null) {
            dest.writeString(dateMemorized.toString());
        }
        else {
            dest.writeString("null");
        }

        if (lastReviewed != null) {
            dest.writeString(lastReviewed.toString());
        }
        else {
            dest.writeString("null");
        }
    }
}
