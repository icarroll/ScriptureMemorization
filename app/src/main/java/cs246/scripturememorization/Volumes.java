package cs246.scripturememorization;

public class Volumes {

    private String volumeTitle;
    private String volumeId;

    public Volumes(String volumeTitle, String volumeId) {
        this.volumeTitle = volumeTitle;
        this.volumeId = volumeId;
    }

    public String getVolumeTitle() { return this.volumeTitle; }
    public String getVolumeId() {
        return this.volumeId;
    }
}
