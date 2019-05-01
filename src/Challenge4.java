import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Challenge4 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long numberOfSamples = scanner.nextInt();
        for (long i = 0; i < numberOfSamples; i++) {
            int numOfEntries = scanner.nextInt();
            Map<Integer, BigInteger> frequencies = new HashMap<>();
            int max = 0;
            for (long j = 0; j < numOfEntries; j++) {
                int number = scanner.nextInt();
                if (number >  max) {
                    max = number;
                }
                BigInteger frequency = frequencies.get(number);
                if (frequency == null) {
                    frequencies.put(number, BigInteger.ONE);
                } else {
                    frequencies.put(number, frequency.add(BigInteger.ONE));
                }
            }
            Map<Integer, BigInteger> originalFrequencies = new HashMap<>(frequencies);
            multiplyAndCheckIsComplete(new ArrayList<>(originalFrequencies.keySet()), originalFrequencies, frequencies);
            BigInteger people = BigInteger.ZERO;
            BigInteger candies = BigInteger.ZERO;
            for (Map.Entry<Integer, BigInteger> entry : frequencies.entrySet()) {
                people = people.add(entry.getValue().divide(BigInteger.valueOf(entry.getKey())));
                candies = candies.add(entry.getValue());
            }
            System.out.printf("Case #%d: %s\n", (i + 1), greatestCommonDenominatorAsString(candies, people));
        }
        scanner.close();
    }

    private static boolean isComplete(Map<Integer, BigInteger> frequencies) {
        for (Map.Entry<Integer, BigInteger> entry : frequencies.entrySet()) {
            if (!entry.getValue().mod(BigInteger.valueOf(entry.getKey())).equals(BigInteger.ZERO)) {
                return false;
            }
        }
        return true;
    }

    private static void multiply(BigInteger x, Map<Integer, BigInteger> originalFrequencies, Map<Integer, BigInteger> frequencies) {
        for (Map.Entry<Integer, BigInteger> entry : frequencies.entrySet()) {
            frequencies.put(entry.getKey(), originalFrequencies.get(entry.getKey()).multiply(x));
        }
    }

    private static void multiplyAndCheckIsComplete(List<Integer> sequence, Map<Integer, BigInteger> originalFrequencies, Map<Integer, BigInteger> frequencies) {
        BigInteger x = BigInteger.ONE;
            for (Integer integer : sequence) {
                x = x.multiply(BigInteger.valueOf(integer));
                multiply(x, originalFrequencies, frequencies);
                if (isComplete(frequencies)) {
                    break;
                }
        }
    }


    private static BigInteger greatestCommonDenominator(BigInteger a, BigInteger b) {
        return b.equals(BigInteger.ZERO) ? a : greatestCommonDenominator(b, a.mod(b));
    }

    private static String greatestCommonDenominatorAsString(BigInteger a, BigInteger b) {
        BigInteger greatestCommonDenominator = greatestCommonDenominator(a, b);
        return (a.divide(greatestCommonDenominator)) + "/" + (b.divide(greatestCommonDenominator));
    }
}
