package encoderdecoder;

import java.util.Arrays;

class MessageToEncode extends Message {

    /* Instance Fields */
    String[] splitBinaryStringArray;
    String[] expandedSplitBinaryStringArray;
    String[] paritiedBinaryStringArray;

    /* Constructors */
    public MessageToEncode(String filepath) {
        this.charArray = Message.readTextFileToCharArray(filepath);
        this.hexStringArray = Message.toHexStringArray(charArray);
        this.binaryStringArray = Message.toBinaryStringArray(charArray);
        this.splitBinaryStringArray = Arrays.stream(binaryStringArray)
                .flatMap(s -> Arrays.stream(splitBinaryString(s)))
                .toArray(String[]::new);
        this.expandedSplitBinaryStringArray = Arrays.stream(splitBinaryStringArray)
                .map(MessageToEncode::expandSplitBinaryString)
                .toArray(String[]::new);
        this.paritiedBinaryStringArray = Arrays.stream(splitBinaryStringArray)
                .map(MessageToEncode::addParityBits)
                .toArray(String[]::new);
        this.paritiedHexStringArray = Arrays.stream(paritiedBinaryStringArray)
                .map(Message::toHexString)
                .toArray(String[]::new);
        this.intArray = Message.toIntArray(paritiedBinaryStringArray);
    }

    /* Getters */
    public String[] getExpandedSplitBinaryStringArray() {
        return expandedSplitBinaryStringArray;
    }

    public String[] getParitiedBinaryStringArray() {
        return paritiedBinaryStringArray;
    }

    /* Instance Methods */
    protected static String[] splitBinaryString(String s) {

        String[] splitStringArray = new String[2];
        splitStringArray[0] = s.substring(0, 4);
        splitStringArray[1] = s.substring(4, 8);

        return splitStringArray;
    }

    protected static String expandSplitBinaryString(String s) {
        return ".." + s.substring(0, 1) + "." + s.substring(1, 4) + ".";
    }

    protected static String addParityBits(String s) {

        int p3 = Integer.parseInt(s.substring(0, 1), 2);
        int p5 = Integer.parseInt(s.substring(1, 2), 2);
        int p6 = Integer.parseInt(s.substring(2, 3), 2);
        int p7 = Integer.parseInt(s.substring(3, 4), 2);

        int p1 = p3 ^ p5 ^ p7;
        int p2 = p3 ^ p6 ^ p7;
        int p4 = p5 ^ p6 ^ p7;

        return "" + p1 + p2 + p3 + p4 + p5 + p6 + p7 + "0";
    }
}
