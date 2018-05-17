package Gmail;

import java.time.LocalDate;

public interface Email extends Comparable<Email> {

    LocalDate getDate();

    String getTopic();

    String getEmailAddress();

    String getBody();
}
