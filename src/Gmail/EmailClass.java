package Gmail;

import java.time.LocalDate;

public final class EmailClass implements Email {

    private LocalDate date;
    private String topic;
    private String emailAddress;
    private String body;

    EmailClass(String topic, String email, String body,LocalDate date) {
        this.date = date;
        this.topic = topic;
        this.emailAddress = email;
        this.body = body;
    }

    @Override
    public LocalDate getDate() {
        return date;
    }

    @Override
    public String getTopic() {
        return topic;
    }

    @Override
    public String getEmailAddress() {
        return emailAddress;
    }

    @Override
    public String getBody() {
        return body;
    }

    @Override
    public int compareTo(Email email) {
       int result = topic.compareTo(email.getTopic());
        if(result == 0)
            return this.emailAddress.compareTo(email.getEmailAddress());
        return result;
    }
}
