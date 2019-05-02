import java.util.Objects;
import java.util.Scanner;

public class Challenge5 {

    private char[][] keyboard = {
            {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'},
            {'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P'},
            {'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L', ';'},
            {'Z', 'X', 'C', 'V', 'B', 'N', 'M', ',', '.', '-'},
    };

    private static final int ROW_LENGTH = 10;
    private static final int KEYBOARD_ROWS = 4;
    private static final Coordinates COORDS_B = new Coordinates(4, 3);
    private static final Coordinates COORDS_G = new Coordinates(4, 2);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long numberOfSamples = scanner.nextInt();
        Challenge5 challenge5 = new Challenge5();
        for (long i = 0; i < numberOfSamples; i++) {
            char key = scanner.next().charAt(0);
            scanner.nextLine();
            String encryptedText = scanner.nextLine();
            System.out.printf("Case #%d: %s\n", (i + 1), challenge5.decryptText(key, encryptedText.toCharArray()));
        }
        scanner.close();
    }

    public String decryptText(char key, char[] text) {
        Coordinates difference = difference(getCoordinates(text[text.length - 1]), getCoordinates(key));
        StringBuilder decrypted = new StringBuilder();
        for (char character : text) {
            if (character == ' ') {
                decrypted.append(character);
            } else {
                decrypted.append(decryptChar(character, difference));
            }
        }
        return decrypted.toString();
    }

    public char decryptChar(char character, Coordinates difference) {
        Coordinates encryptedCharCoordinates = getCoordinates(character);
        Coordinates decryptedCharCoordinates = new Coordinates(encryptedCharCoordinates.x - difference.x,
                                                               encryptedCharCoordinates.y - difference.y);
        if (decryptedCharCoordinates.getX() < 0) {
            decryptedCharCoordinates.setX(ROW_LENGTH + decryptedCharCoordinates.getX());
        } else if (decryptedCharCoordinates.getX() >= ROW_LENGTH) {
            decryptedCharCoordinates.setX(decryptedCharCoordinates.getX() - ROW_LENGTH);
        }
        if (decryptedCharCoordinates.getY() < 0) {
            decryptedCharCoordinates.setY(KEYBOARD_ROWS + decryptedCharCoordinates.getY());
        } else if (decryptedCharCoordinates.getY() >= KEYBOARD_ROWS) {
            decryptedCharCoordinates.setY(decryptedCharCoordinates.getY() - KEYBOARD_ROWS);
        }
        return getChar(decryptedCharCoordinates);
    }

    public Coordinates difference(Coordinates a, Coordinates b) {
        int x = a.getX() - b.getX();
        int y = a.getY() - b.getY();
        return new Coordinates(x, y);
    }

    public Coordinates getCoordinates(char character) {
        if ('B' == character) {
            return COORDS_B;
        }
        if ('G' == character) {
            return COORDS_G;
        }

        for (int i = 0; i < KEYBOARD_ROWS; i++) {
            for (int j = 0; j < ROW_LENGTH; j++) {
                if (keyboard[i][j] == character) {
                    return new Coordinates(j, i);
                }
            }
        }
        return null;
    }

    public char getChar(Coordinates coordinates) {
        return keyboard[coordinates.getY()][coordinates.getX()];
    }

    public static class Coordinates {
        private int x;
        private int y;

        public Coordinates(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Coordinates coordinates = (Coordinates) o;
            return x == coordinates.x &&
                    y == coordinates.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "Coordinates{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }
}
