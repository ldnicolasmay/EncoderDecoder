package encoderdecoder;

import java.util.Arrays;
import java.util.Random;

class MessageToSend extends Message {

    /* Instance Fields */
    String[] erroredBinaryStringArray;
    String[] erroredHexStringArray;
    int[] erroredIntArray;

    /* Constructors */
    public MessageToSend(String filepath) {
        this.intArray = readBinaryFileToIntArray(filepath);
        this.hexStringArray = toHexStringArray(intArray);
        this.binaryStringArray = toBinaryStringArray(intArray);
        this.erroredBinaryStringArray = Arrays.stream(binaryStringArray)
                .map(MessageToSend::errorBinaryString)
                .toArray(String[]::new);
        this.erroredHexStringArray = Arrays.stream(erroredBinaryStringArray)
                .map(Message::toHexString)
                .toArray(String[]::new);
        this.erroredIntArray = toIntArray(erroredBinaryStringArray);
    }

    /* Getters */
    public String[] getErroredBinaryStringArray() {
        return erroredBinaryStringArray;
    }

    public String[] getErroredHexStringArray() {
        return erroredHexStringArray;
    }

    public int[] getErroredIntArray() {
        return erroredIntArray;
    }

    /* Instance Methods */
    protected static String errorBinaryString(String s) {

        Random random = new Random();

        int binaryStringLength = s.length();
        int indexToRandomize = random.nextInt(binaryStringLength);

        char swappedChar = s.charAt(indexToRandomize) == '0' ? '1' : '0';

        return s.substring(0, indexToRandomize) + swappedChar + s.substring(indexToRandomize + 1, binaryStringLength);
    }
}
