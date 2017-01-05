package com.zircon.app.model;

/**
 * Created by Cbc-03 on 10/25/16.
 */
public class NoticeBoard {

    private int id;

    private String image_url2;

    private String title;

    private String image_url1;

    private int status;

    private String modifieddate;

    private String description;

    private String isPinned;

    private int score;

    private String creationdate;

    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage_url2() {
        return image_url2;
    }

    public void setImage_url2(String image_url2) {
        this.image_url2 = image_url2;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage_url1() {
        return image_url1;
    }

    public void setImage_url1(String image_url1) {
        this.image_url1 = image_url1;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getModifieddate() {
        return modifieddate;
    }

    public void setModifieddate(String modifieddate) {
        this.modifieddate = modifieddate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsPinned() {
        return isPinned;
    }

    public void setIsPinned(String isPinned) {
        this.isPinned = isPinned;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(String creationdate) {
        this.creationdate = creationdate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}