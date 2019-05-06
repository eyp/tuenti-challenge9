import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import sun.jvm.hotspot.runtime.Bytes;

public class Challenge7 {
    private static final char[] CRACK_ALPHABET = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    private static final String PRINT_SECTION_SEPARATOR = "---";
    private final String originalMessage;
    private final String modifiedMessage;
    private String preamble;
    private String body;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long numberOfSamples = scanner.nextInt();
        for (int i = 0; i < numberOfSamples; i++) {
            String originalMessage = readMessage(scanner);
            String modifiedMessage = readMessage(scanner);
            Challenge7 challenge7 = new Challenge7(originalMessage, modifiedMessage);
            System.out.printf("Case #%d: \n", (i + 1));
            System.out.printf("Original message: %s\n", originalMessage);
            System.out.printf("Modified message: %s\n", modifiedMessage);
            System.out.printf("Original message hash: %s\n",
                              Arrays.toString(challenge7.notSoComplexHash(originalMessage)));
            System.out.printf("Modified message hash: %s\n",
                              Arrays.toString(challenge7.notSoComplexHash(modifiedMessage)));
            challenge7.crackMessage();
        }
        scanner.close();
    }

    public Challenge7(String originalMessage, String modifiedMessage) {
        this.originalMessage = originalMessage;
        this.modifiedMessage = modifiedMessage;
        int printSectionIndex = modifiedMessage.indexOf(PRINT_SECTION_SEPARATOR);
        this.preamble = modifiedMessage.substring(0, printSectionIndex)
                + PRINT_SECTION_SEPARATOR;
        this.body = modifiedMessage.substring(printSectionIndex + PRINT_SECTION_SEPARATOR.length());
    }

    private static String readMessage(Scanner scanner) {
        int numberOfLines = scanner.nextInt();
        scanner.nextLine();
        StringBuilder message = new StringBuilder();
        for (int i = 0; i < numberOfLines; i++) {
            message.append(scanner.nextLine());
        }
        return message.toString();
    }

    public byte[] notSoComplexHash(String inputText) {
        byte[] hash = new byte[16];
        Arrays.fill(hash, (byte) 0x00);
        byte[] textBytes = inputText.getBytes(StandardCharsets.ISO_8859_1);
        for (int i = 0; i < textBytes.length; i++) {
            hash[i % 16] = (byte) (hash[i % 16] + textBytes[i]);
//            System.out.printf("i = %d, i %% 16 = %d, bytes = %d, hash = %d\n", i, (i%16), textBytes[i], hash[i%16]);
        }
        return hash;
    }

    public byte notSoComplexHashForIndex(String inputText, int index) {
        byte hash = 0;
        byte[] textBytes = inputText.getBytes(StandardCharsets.ISO_8859_1);
        for (int i = (index % 16); i < textBytes.length; i += 16) {
            hash = (byte) (hash + textBytes[i]);
//            System.out.printf("i = %d, i %% 16 = %d, bytes = %d, hash = %d\n", i, (i%16), textBytes[i], hash[i%16]);
        }
        return hash;
    }

    public byte[] notSoComplexHashUptoIndex(String inputText, int index) {
        if (index > 15) {
            index = 15;
        }
        byte[] hash = new byte[16];
        Arrays.fill(hash, (byte) 0x00);
        for (int i = 0; i < index + 1; i++) {
            hash[i] = notSoComplexHashForIndex(inputText, i);
        }
        return hash;
    }

    public String crackMessage() {
        byte[] hashOriginalMessage = notSoComplexHash(originalMessage);
        byte[] hashModifiedMessage = notSoComplexHash(modifiedMessage);
        System.out.printf("Original message hash: %s\n", Arrays.toString(hashOriginalMessage));
        System.out.printf("Modified message hash: %s\n", Arrays.toString(hashModifiedMessage));

        Map<Integer, List<Byte>> crack = new HashMap<>();
        for (int i = 0; i < 16; i++) {
            crack.put(i, getCrackForByte(hashOriginalMessage[i], hashModifiedMessage[i]));
            System.out.printf("Crack [%d] = %s\n", i, crack.get(i));
        }
        String crackString = buildCrackString(crack);
        System.out.printf("Crack = %s\n", crackString);
        //System.out.printf("Cracked message: %s\n", crackedMessage);
        //System.out.printf("Cracked message hash: %s\n",
        //                  Arrays.toString(notSoComplexHash(crackedMessage)));

        return crack.toString();
    }

    public String buildCrackString(Map<Integer, List<Byte>> crack) {
        StringBuilder crackString = new StringBuilder();
        int j = 0;
        for (int i = 0; i < 16; i++) {
            List<Byte> crackBytes = crack.get(i);
            if (j >= crackBytes.size()) {
                continue;
            }
            crackString.append(crack.get(j));
        }
        System.out.printf("Crack: %s\n", crackString.toString());
        return crackString.toString();
    }

    public String crackMessage2() {
        byte[] hashOriginalMessage = notSoComplexHash(originalMessage);
        System.out.printf("Original message hash: %s\n", Arrays.toString(hashOriginalMessage));
        System.out.printf("Modified message hash: %s\n", Arrays.toString(notSoComplexHash(this.modifiedMessage)));

        String crack = "0000000000000000";
        long k = 0;
        String crackedMessage = buildCrackedMessage(crack);
        //while (!hasSameHash(crackedMessage, hashOriginalMessage) && k < 20000) {
        while (!hasSameHash(crackedMessage, hashOriginalMessage)) {
            crack = incrementCrack(crack);
            //System.out.printf("Crack [%d]: %s\n", k, crack);
            crackedMessage = buildCrackedMessage(crack);
            //System.out.printf("Cracked message: %s\n", crackedMessage);
            k++;
        }
        System.out.printf("K = %d\n", k);
        System.out.printf("Cracked message: %s\n", crackedMessage);
        System.out.printf("Cracked message hash: %s\n",
                          Arrays.toString(notSoComplexHash(crackedMessage)));

        return crack;
    }

/*
    public void incrementCrack(int position) {
        LinkedList<Byte> values = this.crack.get(position);
        Byte value = values.getLast();
        value++;
        if (value > 'z') {
            value = '0';
            values.add((byte) '0');
            values.
        }
    }
*/

    public List<Byte> getCrackForByte(byte originalByte, byte modifiedByte) {
        List<Byte> crack = new ArrayList<>();
        byte difference = (byte) (originalByte - modifiedByte);
        if (difference != 0) {
            crack.add(difference);
            if (difference < 48 || difference > 122) {
                crack.add((byte) 48);
                crack.add((byte) 84);
                crack.add((byte) 'z');
            }
        }
        System.out.printf("Crack: %s\n", crack.toString());
        return crack;
    }

    public void printHashBytes(String message) {
        Map<Integer, List<Byte>> hashSequences = new HashMap<>();
        byte[] textBytes = message.getBytes(StandardCharsets.ISO_8859_1);
        for (int i = 0; i < textBytes.length; i++) {
            if (hashSequences.get(i % 16) == null) {
                hashSequences.put(i % 16, new ArrayList<>());
            }
            hashSequences.get(i % 16).add(textBytes[i]);
        }
        for (int i = 0; i < 16; i++) {
            System.out.printf("Hash sequence %d: %s\n", i, hashSequences.get(i));
        }
    }

    public String incrementCrack(String crack) {
        return new String(incrementCrack(crack.getBytes()));
    }

    public byte[] incrementCrack(byte[] crack) {
        if (crack.length == 0) {
            return new byte[]{'0'};
        }

        if (crack.length == 1) {
            if (crack[0] != 'z') {
                crack[0]++;
                return crack;
            }
        }

        // Length > 1

        int i = 0;
        for (byte b : crack) {
            if (b == 'z') {
                i++;
                continue;
            }
        }

        if (i == crack.length) {
            // Add character to sequence
            byte[] newCrack = new byte[crack.length + 1];
            Arrays.fill(newCrack, (byte) '0');
            System.out.printf("crack: %s\n", new String(newCrack));
            String crackedMessage = buildCrackedMessage(new String(newCrack));
            System.out.printf("Cracked message hash: %s\n",
                              Arrays.toString(notSoComplexHash(crackedMessage)));
            return newCrack;
        }

        for (int j = crack.length - 1; j >= 0; j--) {
            crack[j]++;
            if (crack[j] > 'z') {
                crack[j] = '0';
            } else {
                break;
            }
        }
        return crack;
    }

    public boolean hasSameHash(String message, byte[] hash) {
        for (int i = 0; i < 16; i++) {
            if (notSoComplexHashForIndex(message, i) != hash[i]) {
                return false;
            }
        }
        return true;
    }

    private String buildCrackedMessage(String crack) {
        return this.preamble
                + crack
                + this.body;
    }
}
