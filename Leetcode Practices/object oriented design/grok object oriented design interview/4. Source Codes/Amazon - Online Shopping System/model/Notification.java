public class Notification {
    private int notificationId;
    private Date createdOn;
    private String content;

    public boolean sendNotification(Account account);
}

public class SMSNotification extends Notification {
    private String phone;
}

public class EmailNotification extends Notification {
    private String email;
}
