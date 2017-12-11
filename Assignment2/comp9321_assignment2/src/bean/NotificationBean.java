package bean;

import java.sql.Timestamp;

public class NotificationBean {
    
    private int notificationId;
    private int subjectiveUserId;
    private int objectiveUserId;
    private String notificationType;
    private Timestamp notificationTime;
    
    public int getNotificationId() {
        return notificationId;
    }
    public int getSubjectiveUserId() {
        return subjectiveUserId;
    }
    public int getObjectiveUserId() {
        return objectiveUserId;
    }
    public String getNotificationType() {
        return notificationType;
    }
    public Timestamp getNotificationTime() {
        return notificationTime;
    }
    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }
    public void setSubjectiveUserId(int subjectiveUserId) {
        this.subjectiveUserId = subjectiveUserId;
    }
    public void setObjectiveUserId(int objectiveUserId) {
        this.objectiveUserId = objectiveUserId;
    }
    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }
    public void setNotificationTime(Timestamp notificationTime) {
        this.notificationTime = notificationTime;
    }
    

}
