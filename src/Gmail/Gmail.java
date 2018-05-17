package Gmail;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.SortedSet;

public interface Gmail {

    DateTimeFormatter FORMAT_DATE = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    void send(String topic, String destEmail, String body, LocalDate date);

    void receive(String topic, String senderEmail, String body, LocalDate date);

    String sent();

    String received();

    String topic(String topic);

    String email(String email);

    String topics();

    boolean duplicateSentMessage(String topic, String email, String body, LocalDate realDate);

    boolean duplicateReceivedMessage(String topic, String email, String body, LocalDate realDate);
}
