package sample.vlayout.bean;

import java.io.Serializable;

public class VideoBean implements Serializable {

    private String title;
    private String videoUrl;
    private String audioUrl;
    private String thumb;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public VideoBean() {
    }

    public VideoBean(String title, String thumb, String videoUrl,String audioUrl) {
        this.title = title;
        this.videoUrl = videoUrl;
        this.audioUrl = audioUrl;
        this.thumb = thumb;

    }
}