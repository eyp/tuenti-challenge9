import java.util.AbstractMap;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Challenge2 {
    private static final String GALACTICA = "Galactica";
    private static final String NEW_EARTH = "New Earth";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numberOfSamples = scanner.nextInt();
        for (int i = 0; i < numberOfSamples; i++) {
            int planets = scanner.nextInt();
            scanner.nextLine();
            Map<String, List<String>> paths = new HashMap<>();
            for (int j = 0; j < planets; j++) {
                Map.Entry<String, List<String>> path = readPath(scanner.nextLine());
                paths.put(path.getKey(), path.getValue());
            }
            System.out.printf("Case #%d: %d\n", (i+1), calculateNumberOfPaths(paths, paths.get(GALACTICA)));
        }
        scanner.close();
    }

    private static Map.Entry<String, List<String>> readPath(String line) {
        String[] tokens = line.split(":");
        return new AbstractMap.SimpleImmutableEntry<>(tokens[0], Arrays.asList(tokens[1].split(",")));
    }

    private static int calculateNumberOfPaths(Map<String, List<String>> paths, List<String> nodes) {
        int total = 0;
        for (String node : nodes) {
            if (node.equals(NEW_EARTH)) {
                total++;
            } else {
                total += calculateNumberOfPaths(paths, paths.get(node));
            }
        }
        return total;
    }
}
