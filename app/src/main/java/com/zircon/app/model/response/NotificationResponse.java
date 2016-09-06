package com.zircon.app.model.response;

/**
 * Created by sagar_000 on 8/23/2016.
 */
public class NotificationResponse {

    private boolean isnotify;

    public boolean isnotify() {
        return isnotify;
    }

    public void setIsnotify(boolean isnotify) {
        this.isnotify = isnotify;
    }

    private String Icon;

    public String getIcon() {
        return Icon;
    }

    public void setIcon(String icon) {
        Icon = icon;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    private String Message;

    private String Title;

    private String main_picture;


    public String getMain_picture() {
        return main_picture;
    }

    public void setMain_picture(String main_picture) {
        this.main_picture = main_picture;
    }


}
