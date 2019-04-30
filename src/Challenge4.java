import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Challenge4 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numberOfSamples = scanner.nextInt();
        for (int i = 0; i < numberOfSamples; i++) {
            int numOfEntries = scanner.nextInt();
            List<Integer> entries = new ArrayList<>();
            for (int j = 0; j < numOfEntries; j++) {
                int number = scanner.nextInt();
                entries.add(number);
            }
            entries.sort(Comparator.reverseOrder());
            System.out.println(entries);
            int x = 1;
            for (Integer entry : entries) {
                int f = Collections.frequency(entries, entry);
                System.out.printf("%d appears %d times, ", entry, f);
                if (x == 1 && f < entry) {
                    x = entry - f;
                }
            }
            System.out.printf("X = %d\n", x);
            List<Integer> fullEntries = new ArrayList<>();
            for (int j = 0; j < x + 1; j++) {
                fullEntries.addAll(entries);
            }
            fullEntries.sort(Comparator.reverseOrder());
            System.out.println(fullEntries);
        }
        scanner.close();
    }
}
