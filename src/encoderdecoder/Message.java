package encoderdecoder;

import java.io.CharArrayWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;

abstract class Message {

    /* Instance Fields */
    char[] charArray;
    String[] hexStringArray;
    String[] binaryStringArray;
    String[] paritiedHexStringArray;
    int[] intArray;

    /* Getters */
    public char[] getCharArray() {
        return charArray;
    }

    public String[] getHexStringArray() {
        return hexStringArray;
    }

    public String[] getBinaryStringArray() {
        return binaryStringArray;
    }

    public String[] getParitiedHexStringArray() {
        return paritiedHexStringArray;
    }

    public int[] getIntArray() {
        return intArray;
    }

    /* Static Methods */
    protected static char[] readTextFileToCharArray(String filepath) {

        int charInt;
        char c;
        CharArrayWriter charArrayWriter = new CharArrayWriter();

        try (FileReader fileReader = new FileReader(filepath)) {
            while ((charInt = fileReader.read()) != -1) {
                c = (char) charInt;
                charArrayWriter.append(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return charArrayWriter.toCharArray();
    }

    protected static String collapse(String[] stringArray, String infix) {

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < stringArray.length; i++) {
            String unit = i < stringArray.length - 1 ? stringArray[i] + infix : stringArray[i];
            stringBuilder.append(unit);
        }

        return stringBuilder.toString();
    }

    protected static String collapse(int[] intArray, String infix) {

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < intArray.length; i++) {
            String unit = i < intArray.length - 1 ? intArray[i] + infix : intArray[i] + "";
            stringBuilder.append(unit);
        }

        return stringBuilder.toString();
    }

    protected static String[] toHexStringArray(char[] charArray) {

        String[] hexStringArray = new String[charArray.length];

        for (int i = 0; i < charArray.length; i++) {
            hexStringArray[i] = Message.toHexString(charArray[i]).toUpperCase();
        }

        return hexStringArray;
    }

    protected static String[] toHexStringArray(int[] intArray) {

        String[] hexStringArray = new String[intArray.length];

        for (int i = 0; i < intArray.length; i++) {
            hexStringArray[i] = Message.toHexString(intArray[i]);
        }

        return hexStringArray;
    }

    protected static String toHexString(char c) {
        return String.format("%02x", (int) c).toUpperCase();
    }

    protected static String toHexString(int i) {
        return String.format("%02x", i).toUpperCase();
    }

    protected static String toHexString(String s) {

        int i = Integer.parseInt(s, 2);

        return String.format("%02x", i).toUpperCase();
    }

    protected static String[] toBinaryStringArray(char[] charArray) {

        String[] binaryStringArray = new String[charArray.length];

        for (int i = 0; i < charArray.length; i++) {
            binaryStringArray[i] = Message.toBinaryString(charArray[i]);
        }

        return binaryStringArray;
    }

    protected static String[] toBinaryStringArray(int[] intArray) {

        String[] binaryStringArray = new String[intArray.length];

        for (int i = 0; i < intArray.length; i++) {
            binaryStringArray[i] = Message.toBinaryString(intArray[i]);
        }

        return binaryStringArray;
    }

    protected static String toBinaryString(char c) {

        String binaryString = Integer.toBinaryString(c);

        return String.format("%8s", binaryString).replace(' ', '0');
    }

    protected static String toBinaryString(int i) {

        String binaryString = Integer.toBinaryString(i);

        return String.format("%8s", binaryString).replace(' ', '0');
    }

    protected static int[] toIntArray(String[] stringArray) {

        int[] intArray = new int[stringArray.length];

        for (int i = 0; i < stringArray.length; i++) {
            intArray[i] = Integer.parseInt(stringArray[i], 2);
        }

        return intArray;
    }

    protected static char[] toCharArray(String[] stringArray) {

        char[] charArray = new char[stringArray.length];

        for (int i = 0; i < stringArray.length; i++) {
            charArray[i] = (char) Integer.parseInt(stringArray[i], 2);
        }

        return charArray;
    }

    protected static int[] readBinaryFileToIntArray(String filepath) {

        byte[] byteArray = new byte[0];

        try (FileInputStream fileInputStream = new FileInputStream(filepath)) {
            byteArray = fileInputStream.readAllBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }

        int[] intArray = new int[byteArray.length];

        for (int i = 0; i < byteArray.length; i++) {
            intArray[i] = byteArray[i] & 0xff;
        }

        return intArray;
    }

    protected static void writeToBinaryFile(int[] intArray, String filepath) {

        try (FileOutputStream fileOutputStream = new FileOutputStream(filepath)) {
            for (int i : intArray) {
                fileOutputStream.write(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected static void writeToTextFile(char[] charArray, String filepath) {

        try (FileWriter fileWriter = new FileWriter(filepath)) {
            fileWriter.write(charArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
