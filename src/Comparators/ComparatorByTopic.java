package Comparators;

import Gmail.Email;

import java.util.Comparator;

public class ComparatorByTopic  implements Comparator<Email> {

    @Override
    public int compare(Email email1, Email email2) {
        return email1.compareTo(email2);
    }
}
