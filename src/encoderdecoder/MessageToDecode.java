package encoderdecoder;

import java.util.Arrays;

class MessageToDecode extends Message {

    /* Instance Fields */
    String[] erroredBinaryStringArray;
    String[] erroredHexStringArray;
    int[] erroredIntArray;
    String[] correctedBinaryStringArray;

    /* Constructors */
    public MessageToDecode(String filepath) {
        this.erroredIntArray = readBinaryFileToIntArray(filepath);
        this.erroredHexStringArray = toHexStringArray(erroredIntArray);
        this.erroredBinaryStringArray = toBinaryStringArray(erroredIntArray);
        this.correctedBinaryStringArray = Arrays.stream(erroredBinaryStringArray)
                .map(MessageToDecode::correctErroredBinaryString)
                .toArray(String[]::new);
        this.binaryStringArray = decodeCorrectedBinaryStringArray(correctedBinaryStringArray);
        this.hexStringArray = Arrays.stream(binaryStringArray)
                .map(Message::toHexString)
                .toArray(String[]::new);
        this.charArray = toCharArray(binaryStringArray);
    }

    /* Getters */
    public int[] getErroredIntArray() {
        return erroredIntArray;
    }

    public String[] getErroredHexStringArray() {
        return erroredHexStringArray;
    }

    public String[] getErroredBinaryStringArray() {
        return erroredBinaryStringArray;
    }

    public String[] getCorrectedBinaryStringArray() {
        return correctedBinaryStringArray;
    }

    /* Instance Methods */
    private static String correctErroredBinaryString(String s) {

        // Data bits
        String correctedBinaryString;

        int p3 = Integer.parseInt(s.substring(2, 3), 2);
        int p5 = Integer.parseInt(s.substring(4, 5), 2);
        int p6 = Integer.parseInt(s.substring(5, 6), 2);
        int p7 = Integer.parseInt(s.substring(6, 7), 2);

        // Parity bits
        int p1 = Integer.parseInt(s.substring(0, 1), 2);
        int p2 = Integer.parseInt(s.substring(1, 2), 2);
        int p4 = Integer.parseInt(s.substring(3, 4), 2);
        int p8 = Integer.parseInt(s.substring(7, 8), 2);

        int p1Calc = p3 ^ p5 ^ p7;
        int p2Calc = p3 ^ p6 ^ p7;
        int p4Calc = p5 ^ p6 ^ p7;

        int errorIndex = -1;
        if (p1 != p1Calc) { errorIndex += 1; }
        if (p2 != p2Calc) { errorIndex += 2; }
        if (p4 != p4Calc) { errorIndex += 4; }

        if (errorIndex == -1 || p8 == 1) {
            correctedBinaryString = "" + p1 + p2 + p3 + p4 + p5 + p6 + p7 + "0";
        } else {
            char correctedBit = s.charAt(errorIndex) == '0' ? '1' : '0';
            correctedBinaryString = s.substring(0, errorIndex) + correctedBit + s.substring(errorIndex + 1, 8);
        }

        return correctedBinaryString;
    }

    private static String[] decodeCorrectedBinaryStringArray(String[] ss) {

        int ssLength = ss.length;
        String[] newSs = new String[ssLength / 2];

        for (int i = 0; i < ssLength; i += 2) {

            newSs[i / 2] = "" +
                    ss[i].charAt(2) + ss[i].charAt(4) + ss[i].charAt(5) + ss[i].charAt(6) +
                    ss[i + 1].charAt(2) + ss[i + 1].charAt(4) + ss[i + 1].charAt(5) + ss[i + 1].charAt(6);
        }

        return newSs;
    }
}
