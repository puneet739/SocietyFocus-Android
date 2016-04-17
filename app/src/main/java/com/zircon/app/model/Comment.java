package com.zircon.app.model;

/**
 * Created by jikoobaruah on 15/02/16.
 */
public class Comment {

    public interface Status{
        int FROM_SERVER = 0;
        int SENDING_TO_SERVER = 1;
        int SENTSERVER = 2;
    }

    public String commentid;
    public User user;
    public String comment;
    public String complaintid;
    public String creationdate;
    public int status = Status.FROM_SERVER;


}
