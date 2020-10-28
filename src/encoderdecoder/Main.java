package encoderdecoder;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        String sendFilepath = "data/send.txt";
        String encodedFilepath = "data/encoded.txt";
        String receivedFilepath = "data/received.txt";
        String decodedFilepath = "data/decoded.txt";

        Scanner scanner = new Scanner(System.in);
        System.out.print("Write a mode: ");
        String modeInput = scanner.next("^encode$|^send$|^decode$");

        switch (modeInput) {

            case "encode":

                MessageToEncode messageToEncode = new MessageToEncode(sendFilepath);

                System.out.printf("%n%s:%n", sendFilepath);
                System.out.printf("text view: %s%n", new String(messageToEncode.getCharArray()));
                System.out.printf("hex view: %s%n",
                        Message.collapse(messageToEncode.getHexStringArray(), " "));
                System.out.printf("bin view: %s%n",
                        Message.collapse(messageToEncode.getBinaryStringArray(), " "));

                System.out.printf("%n%s:%n", encodedFilepath);
                System.out.printf("expand: %s%n",
                        Message.collapse(messageToEncode.getExpandedSplitBinaryStringArray(), " "));
                System.out.printf("parity: %s%n",
                        Message.collapse(messageToEncode.getParitiedBinaryStringArray(), " "));
                System.out.printf("hex view: %s%n",
                        Message.collapse(messageToEncode.getParitiedHexStringArray(), " "));
                Message.writeToBinaryFile(messageToEncode.getIntArray(), encodedFilepath);

                break;

            case "send":

                MessageToSend messageToSend = new MessageToSend(encodedFilepath);

                System.out.printf("%n%s:%n", encodedFilepath);
                System.out.printf("hex view: %s%n",
                        Message.collapse(messageToSend.getHexStringArray(), " "));
                System.out.printf("bin view: %s%n",
                        Message.collapse(messageToSend.getBinaryStringArray(), " "));

                System.out.printf("%n%s:%n", receivedFilepath);
                System.out.printf("bin view: %s%n",
                        Message.collapse(messageToSend.getErroredBinaryStringArray(), " "));
                System.out.printf("hex view: %s%n",
                        Message.collapse(messageToSend.getErroredHexStringArray(), " "));
                Message.writeToBinaryFile(messageToSend.getErroredIntArray(), receivedFilepath);

                break;

            case "decode":

                MessageToDecode messageToDecode = new MessageToDecode(receivedFilepath);

                System.out.printf("%n%s:%n", receivedFilepath);
                System.out.printf("hex view: %s%n",
                        Message.collapse(messageToDecode.getErroredHexStringArray(), " "));
                System.out.printf("bin view: %s%n",
                        Message.collapse(messageToDecode.getErroredBinaryStringArray(), " "));

                System.out.printf("%n%s:%n", decodedFilepath);
                System.out.printf("correct: %s%n",
                        Message.collapse(messageToDecode.getCorrectedBinaryStringArray(), " "));
                System.out.printf("decode: %s%n",
                        Message.collapse(messageToDecode.getBinaryStringArray(), " "));
                System.out.printf("hex view: %s%n",
                        Message.collapse(messageToDecode.getHexStringArray(), " "));
                System.out.printf("text view: %s%n", new String(messageToDecode.getCharArray()));
                Message.writeToTextFile(messageToDecode.getCharArray(), decodedFilepath);

                break;

            default:
                System.out.println("Invalid mode entered.");
        }
    }
}
