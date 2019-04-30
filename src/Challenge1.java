import java.util.Scanner;

public class Challenge1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numberOfSamples = Integer.valueOf(scanner.nextLine());
        for (int i = 0; i < numberOfSamples; i++) {
            int withOnion = scanner.nextInt();
            int withoutOnion = scanner.nextInt();
            System.out.printf("Case #%d: %d\n", (i + 1), calculateOmelets(withOnion, withoutOnion));
        }
    }

    private static int calculateOmelets(int withOnion, int withoutOnion) {
        return calculateOmelets(withOnion) + calculateOmelets(withoutOnion);
    }

    private static int calculateOmelets(int people) {
        return (people % 2 + people / 2);
    }
}

