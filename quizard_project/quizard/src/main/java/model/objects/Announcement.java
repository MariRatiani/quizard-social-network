package model.objects;

import java.sql.Time;
import java.sql.Timestamp;

public class Announcement {
    private int announcementId;
    private final int userId;
    private String title;
    private String description;
    private Timestamp time_posted;


    public Announcement(int announcementId, int userId, String title, String description, Timestamp time) {
        this.announcementId = announcementId;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.time_posted = time;
    }
    public int getUserId(){
        return this.userId;
    }

    public int getAnnouncementId() {
        return announcementId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Timestamp getTime_posted() {
        return time_posted;
    }
}
