import Exceptions.DuplicateMessageException;
import Exceptions.NoMessagesException;
import Gmail.*;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {

    private enum Message {

        MESSAGE_REGISTERED("Mensagem registada."),
        DUPLICATE_MESSAGE("Mensagem duplicada."),
        NO_MESSAGES_WITH_EMAIL("Nao existem mensagens trocadas com esse email."),
        NO_MESSAGES_WITH_TOPIC(NO_MESSAGES_WITH_EMAIL.msg.replace("email", "assunto")),
        EXITING("A terminar."),
        UNKNOWN("UNKNOWN");

        private final String msg;

        Message(String msg) {
            this.msg = msg;
        }
    }

    private enum Command {
        SEND("ENVIAR"),
        RECEIVE("RECEBER"),
        SENT("ENVIADAS"),
        RECEIVED("RECEBIDAS"),
        LIST_BY_TOPIC("ASSUNTO"),
        LIST_SENT_RECEIVED_BY_EMAIL("EMAIL"),
        LIST_TOPICS("ASSUNTOS"),
        EXIT("SAIR"),
        UNKNOWN("");

        private final String cmd;

        Command(String cmd) {
            this.cmd = cmd;
        }
    }


    public static void main(String[] args) {
        Gmail gmail = new GmailClass();
        Scanner in = new Scanner(System.in);
        executeCommand(in, gmail);
    }


    private static Command getCommand(Scanner in) {
        String cmd = in.nextLine().toUpperCase();
        for (Command Cmd : Command.values())
            if (cmd.equals(Cmd.cmd))
                return Cmd;
        return Command.UNKNOWN;
    }

    private static void executeCommand(Scanner in, Gmail gmail) {
        Command cmd = Command.UNKNOWN;

        while (!cmd.equals(Command.EXIT)) {
            cmd = getCommand(in);

            switch (cmd) {
                case SEND:
                    processSend(in,gmail);
                    break;

                case RECEIVE:
                    processReceive(in,gmail);
                    break;

                case SENT:
                    processSent(gmail);
                    break;

                case RECEIVED:
                    processReceived(gmail);
                    break;

                case LIST_BY_TOPIC:
                    processListByTopic(in,gmail);
                    break;

                case LIST_SENT_RECEIVED_BY_EMAIL:
                    processListByEmail(in, gmail);
                    break;

                case LIST_TOPICS:
                    processListTopics(gmail);
                    break;


                case EXIT:
                    System.out.println(Message.EXITING.msg);
                    in.close();
                    break;

                case UNKNOWN:
                    System.out.println(Message.UNKNOWN.msg);
            }
            System.out.print("\n");
        }
    }






    private static void processListTopics(Gmail gmail) {
        System.out.print(gmail.topics());
    }







    private static void processListByEmail(Scanner in, Gmail gmail) {
        try{
            listByEmail(in,gmail);
        } catch(NoMessagesException e) {
            System.out.println(Message.NO_MESSAGES_WITH_EMAIL.msg);
        }
    }

    private static void listByEmail(Scanner in, Gmail gmail) throws NoMessagesException {
        String email = in.nextLine();
        String msg = gmail.email(email);
        if(msg.equals("data | assunto | email | texto\n"))
            throw new NoMessagesException();
        System.out.print(msg);
    }







    private static void processListByTopic(Scanner in, Gmail gmail) {
        try{
            listByTopic(in,gmail);
        } catch(NoMessagesException e) {
            System.out.println(Message.NO_MESSAGES_WITH_TOPIC.msg);
        }
    }

    private static void listByTopic(Scanner in, Gmail gmail) {
        String topic = in.nextLine();
        String msg = gmail.topic(topic);
        if(msg.equals("data | assunto | email | texto\n"))
            throw new NoMessagesException();
        System.out.print(msg);
    }




    private static void processReceived(Gmail gmail) {
        System.out.print(gmail.received());
    }





    private static void processSent(Gmail gmail) {
        System.out.print(gmail.sent());
    }





    private static void processReceive(Scanner in, Gmail gmail) {
        try{
            receive(in,gmail);
            System.out.println(Message.MESSAGE_REGISTERED.msg);
        } catch(DuplicateMessageException e) {
            System.out.println(Message.DUPLICATE_MESSAGE.msg);
        }
    }

    private static void receive(Scanner in, Gmail gmail) {
        String topic = in.nextLine();
        String email = in.nextLine();
        String body = in.nextLine();

        in.useDelimiter("-");
        int year = in.nextInt();
        in.skip("-");
        int month = in.nextInt();
        in.skip("-");
        in.reset();
        int day = in.nextInt();
        in.nextLine();

        LocalDate realDate = LocalDate.of(year, month, day);


        if(gmail.duplicateReceivedMessage(topic,email,body,realDate))
            throw new DuplicateMessageException();
        gmail.receive(topic,email,body,realDate);
    }





    private static void processSend(Scanner in, Gmail gmail) {
        try{
            send(in,gmail);
            System.out.println(Message.MESSAGE_REGISTERED.msg);
        } catch(DuplicateMessageException e) {
            System.out.println(Message.DUPLICATE_MESSAGE.msg);
        }
    }

    private static void send(Scanner in, Gmail gmail) {
        String topic = in.nextLine();
        String email = in.nextLine();
        String body = in.nextLine();

        in.useDelimiter("-");
        int year = in.nextInt();
        in.skip("-");
        int month = in.nextInt();
        in.skip("-");
        in.reset();
        int day = in.nextInt();
        in.nextLine();

        LocalDate realDate = LocalDate.of(year, month, day);

        if(gmail.duplicateSentMessage(topic,email,body,realDate))
            throw new DuplicateMessageException();
      gmail.send(topic,email,body,realDate);
    }

}
