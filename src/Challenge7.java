import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;

public class Challenge7 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long numberOfSamples = scanner.nextInt();
        Challenge7 challenge7 = new Challenge7();
        for (long i = 0; i < numberOfSamples; i++) {
        }
        scanner.close();
    }

    public byte[] notSoComplexHash(String inputText) {
        byte[] hash = new byte[16];
        Arrays.fill(hash, (byte) 0x00);
        byte[] textBytes = inputText.getBytes(StandardCharsets.ISO_8859_1);
        for (int i = 0; i < textBytes.length; i++) {
            hash[i % 16] = (byte) (hash[i % 16] + textBytes[i]);
        }
        return hash;
    }
}