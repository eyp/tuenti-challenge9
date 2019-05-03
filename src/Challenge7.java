import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Challenge7 {
    private static final char[] CRACK_ALPHABET = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    private static final String PRINT_SECTION_SEPARATOR = "---";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long numberOfSamples = scanner.nextInt();
        Challenge7 challenge7 = new Challenge7();
        for (int i = 0; i < numberOfSamples; i++) {
            String originalMessage = readMessage(scanner);
            String modifiedMessage = readMessage(scanner);
            System.out.printf("Case #%d: \n", (i + 1));
            System.out.printf("Original message: %s\n", originalMessage);
            System.out.printf("Modified message: %s\n", modifiedMessage);
            System.out.printf("Original message hash: %s\n",
                              Arrays.toString(challenge7.notSoComplexHash(originalMessage)));
            System.out.printf("Modified message hash: %s\n",
                              Arrays.toString(challenge7.notSoComplexHash(modifiedMessage)));
            challenge7.crackMessage(originalMessage, modifiedMessage);
        }
        scanner.close();
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

    public String crackMessage(String originalMessage, String modifiedMessage) {
        byte[] hashOriginalMessage = notSoComplexHash(originalMessage);
        System.out.printf("Original message hash: %s\n", Arrays.toString(hashOriginalMessage));
        System.out.printf("Modified message hash: %s\n", Arrays.toString(notSoComplexHash(modifiedMessage)));

        String crack = "";
        byte[] hashCrackedMessage = notSoComplexHash(buildCrackedMessage(modifiedMessage, crack));
        long k = 0;
        while (!areEquals(hashOriginalMessage, hashCrackedMessage) && k < 150) {
            crack = nextCrack(crack);
            System.out.printf("Crack [%d]: %s\n", k, crack);
            String crackedMessage = buildCrackedMessage(modifiedMessage, crack);
//            System.out.printf("Cracked message: %s\n", crackedMessage);
            hashCrackedMessage = notSoComplexHash(crackedMessage);
            System.out.printf("Cracked message hash: %s\n", Arrays.toString(hashCrackedMessage));
            k++;
        }
        System.out.printf("K = %d\n", k);
        return crack;
    }

    public String crackMessage3(String originalMessage, String modifiedMessage) {
        byte[] hashOriginalMessage = notSoComplexHash(originalMessage);
        System.out.printf("Original message hash: %s\n", Arrays.toString(hashOriginalMessage));
        System.out.printf("Original message hash value: %d\n", sumByteArray(hashOriginalMessage));
        System.out.printf("Original message hash value as int: %d\n", sumByteArrayAsInt(hashOriginalMessage));

        String crack = "";
        byte[] hashCrackedMessage = notSoComplexHash(buildCrackedMessage(modifiedMessage, crack));
        int k = 0;
        int printSectionIndex = modifiedMessage.indexOf(PRINT_SECTION_SEPARATOR);
        int i = 0;
        String goodCrack = "03W000000S0e0000Xzzwue08BzQz0Z0DzzzzzzRzzzzzez_zz";
        while (!areEquals(hashOriginalMessage, hashCrackedMessage) && k < 100) {
            crack += goodCrack.charAt(i++);
            System.out.printf("Crack [%d]: %s\n", k, crack);
            String crackedMessage = buildCrackedMessage(modifiedMessage, crack);
            System.out.printf("Cracked message: %s\n", crackedMessage);
            hashCrackedMessage = notSoComplexHash(crackedMessage);
            System.out.printf("Cracked message hash: %s\n", Arrays.toString(hashCrackedMessage));
            System.out.printf("Cracked message hash value: %d\n", sumByteArray(hashCrackedMessage));
            System.out.printf("Cracked message hash value as int: %d\n", sumByteArrayAsInt(hashCrackedMessage));
            k++;
        }
        System.out.printf("K = %d\n", k);
        return crack;
    }

    public String crackMessage2(String originalMessage, String modifiedMessage) {
        byte[] hashOriginalMessage = notSoComplexHash(originalMessage);

        String crack = "0";
        byte[] hashCrackedMessage = notSoComplexHash(buildCrackedMessage(modifiedMessage, crack));
        int k = 0;
        int printSectionIndex = modifiedMessage.indexOf(PRINT_SECTION_SEPARATOR);
        while (!areEquals(hashOriginalMessage, hashCrackedMessage) && k < 100) {
            int indexAffected = (printSectionIndex + 2 + crack.length()) % 16;
//            System.out.printf("Hash index affected: %d\n", indexAffected);
            if (hashCrackedMessage[indexAffected] != hashOriginalMessage[indexAffected]) {
                crack = nextCrack(crack);
                System.out.printf("Crack [%d]: %s\n", k, crack);
                String crackedMessage = buildCrackedMessage(modifiedMessage, crack);
                System.out.printf("Cracked message: %s\n", crackedMessage);
                hashCrackedMessage = notSoComplexHash(crackedMessage);
                System.out.printf("Cracked message hash: %s\n", Arrays.toString(hashCrackedMessage));
            }
            k++;
        }
        System.out.printf("K = %d\n", k);
        return crack;
    }

    public byte[] bytesToReachOriginal(byte original, byte modified, byte start) {
        byte total = modified;
        byte currentChar = start;
        List<Byte> characters = new ArrayList<>();
        while (total != original) {
            if (currentChar > 122) {
                break;
            }
            total = (byte) (total + currentChar);
            characters.add(currentChar);
            currentChar++;
        }
        byte[] bytes = new byte[characters.size()];
        for (int i = 0; i < characters.size(); i++) {
            bytes[i] = characters.get(i);
        }
        return bytes;
    }

    public String nextCrack(String crack) {
        return new String(incrementCrack(crack.getBytes()));
    }

    public byte[] incrementCrack(byte[] crack) {
        if (crack.length == 0) {
            return new byte[]{'0'};
        }

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
            return newCrack;
        }

        for (int j = crack.length - 1; j >= 0; j--) {
            if (crack[j] < 122) {
                crack[j] = (byte) (crack[j] + 1);
                if (j < crack.length - 1) {
                    crack[j + 1] = (byte) '0';
                }
                break;
            }
        }
        return crack;
    }

    public String nextCrack2(String crack) {
        StringBuilder crackStringBuilder = new StringBuilder(crack);
        if (crack.length() == 0) {
            crackStringBuilder.append((char) 48);
        } else if (crack.charAt(crack.length() - 1) == 'z') {
            crackStringBuilder.setCharAt(crack.length() - 1, (char) 48);
            crackStringBuilder.append((char) 48);
        } else {
            char currentChar = crackStringBuilder.charAt(crack.length() - 1);
            crackStringBuilder.setCharAt(crack.length() - 1, ++currentChar);
        }
        return crackStringBuilder.toString();
    }

    private boolean areEquals(byte[] hash, byte[] anotherHash) {
        return Arrays.equals(hash, anotherHash);
    }

    private byte sumByteArray(byte[] hash) {
        byte total = 0;
        for (byte b : hash) {
            total += b;
        }
        return total;
    }

    private int sumByteArrayAsInt(byte[] hash) {
        int total = 0;
        for (byte b : hash) {
            total += (int) b;
        }
        return total;
    }

    private String buildCrackedMessage(String modifiedMessage, String crack) {
        int printSectionIndex = modifiedMessage.indexOf(PRINT_SECTION_SEPARATOR);
//        System.out.printf("Hash index affected: %d\n", (printSectionIndex + 2 + crack.length()) % 16);
        String crackedMessage = modifiedMessage.substring(0, printSectionIndex)
                + PRINT_SECTION_SEPARATOR
                + crack
                + modifiedMessage.substring(printSectionIndex + PRINT_SECTION_SEPARATOR.length());
//        System.out.printf("Cracked message: %s\n", crackedMessage);
        return crackedMessage;
    }
}
