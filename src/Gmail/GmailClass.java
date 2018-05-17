package Gmail;

import Comparators.ComparatorByTopic;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class GmailClass implements Gmail {

    private SortedMap<LocalDate,SortedSet<Email>> sortedByDateInbox;
    private SortedMap<LocalDate,SortedSet<Email>> sortedByDateOutbox;
    private Map<String,List<Email>> sortedByTopicBox;
    private Map<String,List<Email>> sortedByEmailBox;

    public GmailClass() {
        sortedByDateInbox = new TreeMap<>();
        sortedByDateOutbox = new TreeMap<>();
        sortedByTopicBox = new TreeMap<>();
        sortedByEmailBox = new TreeMap<>();
    }

    private void addEmail(Email email, SortedMap<LocalDate,SortedSet<Email>> box){
        List<Email> insertionOrderEmails;
        SortedSet<Email> naturallySortedEmails;
        LocalDate date = email.getDate();
        String topic = email.getTopic();
        String SEmail = email.getEmailAddress();

        naturallySortedEmails = box.get(date);
        if (naturallySortedEmails == null)
            naturallySortedEmails = new TreeSet<>(new ComparatorByTopic());
        naturallySortedEmails.add(email);
        box.put(date, naturallySortedEmails);

        insertionOrderEmails = sortedByTopicBox.get(topic);
        if (insertionOrderEmails == null)
            insertionOrderEmails = new ArrayList<>();
        insertionOrderEmails.add(email);
        sortedByTopicBox.put(topic, insertionOrderEmails);

        insertionOrderEmails = sortedByEmailBox.get(SEmail);
        if (insertionOrderEmails == null)
            insertionOrderEmails = new ArrayList<>();
        insertionOrderEmails.add(email);
        sortedByEmailBox.put(SEmail, insertionOrderEmails);
    }

    private String listBox(String key, Map<String, List<Email>> box) {
        String msg = "data | assunto | email | texto\n";
        List<Email> emails = box.get(key);
        if(emails != null)
            for (Email email : emails)
                msg += email.getDate().format(FORMAT_DATE) + " | " + email.getTopic() + " | " + email.getEmailAddress() + " | " + email.getBody() + "\n";
        return msg;
    }

    private String listBox(Map<LocalDate,SortedSet<Email>> box) {
        String msg = "data | assunto | email\n";
        Set<LocalDate> keySet = box.keySet();
        for (LocalDate date: keySet) {
            SortedSet<Email> emails = box.get(date);
            if(emails != null)
                for (Email email : emails)
                    msg += date.format(FORMAT_DATE) + " | " + email.getTopic() + " | " + email.getEmailAddress() + "\n";
        }
        return msg;
    }

    @Override
    public void send(String topic, String destEmail, String body, LocalDate date) {
        Email email = new EmailClass(topic,destEmail,body,date);
        addEmail(email, sortedByDateOutbox);
    }

    @Override
    public void receive(String topic, String senderEmail, String body, LocalDate date) {
        Email email = new EmailClass(topic,senderEmail,body,date);
        addEmail(email, sortedByDateInbox);
    }

    @Override
    public String sent() {
        return listBox(sortedByDateOutbox);
    }

    @Override
    public String received() {
        return listBox(sortedByDateInbox);
    }

    @Override
    public String topic(String topic) {
        return listBox(topic, sortedByTopicBox);
    }

    @Override
    public String email(String email) {
        return listBox(email, sortedByEmailBox);
    }

    @Override
    public String topics() {
        String msg = "";
        Set<String> keySet = sortedByTopicBox.keySet();

        for (String topic : keySet)
            msg += topic + "\n";

        return msg;
    }

    @Override
    public boolean duplicateSentMessage(String topic, String email, String body, LocalDate realDate) {
        return duplicateMessage(topic, email, body, realDate, sortedByDateOutbox);
    }

    @Override
    public boolean duplicateReceivedMessage(String topic, String email, String body, LocalDate realDate) {
        return duplicateMessage(topic, email, body, realDate, sortedByDateInbox);
    }

    private boolean duplicateMessage(String topic, String emailAddress, String body, LocalDate realDate, Map<LocalDate,SortedSet<Email>> box){
        Email email = new EmailClass(topic,emailAddress,body,realDate);
        SortedSet<Email> emails = box.get(realDate);
        if(emails != null)
            for (Email Email : emails) {
                if (email.compareTo(Email) == 0)
                    return true;
        }
        return false;
    }
}