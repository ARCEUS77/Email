import Gmail.*;

import java.util.Scanner;

public class Main {

    private enum Message {

        CART_REGISTERED("Carrinho criado com sucesso."),
        DUPLICATE_CART("Carrinho existente!"),
        ITEM_REGISTERED("Artigo criado com sucesso."),
        DUPLICATE_ITEM("Artigo existente!"),
        ITEM_ADDED_TO_CART("Artigo adicionado com sucesso."),
        NON_EXISTANT_CART("Carrinho inexistente!"),
        NON_EXISTANT_ITEM("Artigo inexistente!"),
        NO_MORE_SPACE("Capacidade excedida!"),
        ITEM_REMOVED("Artigo removido com sucesso."),
        CART_DOES_NOT_CONTAIN_ITEM(NON_EXISTANT_ITEM.msg.replace("!", " no carrinho!")),
        EMPTY_CART("Carrinho vazio!"),
        VOID(""),
        UNKNOWN("Opcao inexistente."),
        EXITING("A terminar.");


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
        String cmd = in.next().toUpperCase();
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
                case RECEIVED:
                    processRece(in, gmail);
                    break;

                case LIST_BY_TOPIC:
                    processRemoveFromCart(in,gmail);
                    break;

                case LIST_SENT_RECEIVED_BY_EMAIL:
                    processListItems(in, gmail);
                    break;

                case LIST_TOPICS:
                    processPay(in, gmail);
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

}
