import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Challenge6 {
    private List<Character> sortedAlphabet = new ArrayList<>();
    private Map<Character, Character> alphabetRelationships = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long numberOfSamples = scanner.nextInt();
        Challenge6 challenge6 = new Challenge6();
        for (long i = 0; i < numberOfSamples; i++) {
            int numberOfStrings = scanner.nextInt();
            scanner.nextLine();
            List<String> strings = new ArrayList<>();
            Set<Character> unorderedAlphabet = new HashSet<>();
            for (int j = 0; j < numberOfStrings; j++) {
                String string = scanner.nextLine();
                strings.add(string);
                for (int k = 0; k < string.length(); k++) {
                    unorderedAlphabet.add(string.charAt(k));
                }
            }
            challenge6.buildSortedAlphabet(strings);
            if (challenge6.sortedAlphabet.size() != unorderedAlphabet.size()) {
                System.out.printf("Case #%d: AMBIGUOUS\n", (i + 1));
//                System.out.printf("Case #%d: AMBIGUOUS - %s\n", (i + 1),
//                                  challenge6.sortedAlphabet.toString()
//                                                            .replace("[", "")
//                                                            .replace("]", "")
//                                                            .replace(",", ""));
            } else {
                System.out.printf("Case #%d: %s\n", (i + 1),
                                  challenge6.sortedAlphabet.toString()
                                                           .replace("[", "")
                                                           .replace("]", "")
                                                           .replace(",", ""));
            }
//            System.out.printf("Number of strings: %d\n", numberOfStrings);
//            System.out.printf("Unordered alphabet: %s\n", unorderedAlphabet);
            challenge6.sortedAlphabet.clear();
            challenge6.alphabetRelationships.clear();
        }
        scanner.close();
    }

    public void buildSortedAlphabet(List<String> strings) {
        for (int i = 0; i < strings.size(); i++) {
            if ((i + 1) == strings.size()) {
                break;
            }
            compareStrings(strings.get(i), strings.get(i + 1));
        }

        Character firstCharacter = getFirstCharacterOfSortedAlphabet(strings.get(0).charAt(0));
        this.sortedAlphabet.add(firstCharacter);
        this.sortedAlphabet.addAll(buildSortedAlphabetFromFirstCharacter(firstCharacter));
    }

    private Character getFirstCharacterOfSortedAlphabet(Character startingCharacter) {
        if (this.alphabetRelationships.get(startingCharacter) == null) {
            return startingCharacter;
        }
        return getFirstCharacterOfSortedAlphabet(this.alphabetRelationships.get(startingCharacter));
    }

    private List<Character> buildSortedAlphabetFromFirstCharacter(Character value) {
        List<Character> sortedAlphabet = new ArrayList<>();
        for (Character key : this.alphabetRelationships.keySet()) {
            if (this.alphabetRelationships.get(key).equals(value)) {
                sortedAlphabet.add(key);
                sortedAlphabet.addAll(buildSortedAlphabetFromFirstCharacter(key));
                return sortedAlphabet;
            }
        }
        return sortedAlphabet;
    }

    private List<Character> buildSortedAlphabetFromLastCharacter(Character node) {
        List<Character> sortedAlphabet = new ArrayList<>();
        Character character = this.alphabetRelationships.get(node);
        if (character == null) {
            return sortedAlphabet;
        }
        sortedAlphabet.add(character);
        sortedAlphabet.addAll(buildSortedAlphabetFromLastCharacter(character));
        return sortedAlphabet;
    }

    public void compareStrings(String smaller, String bigger) {
        for (int i = 0; i < smaller.length(); i++) {
            if (i >= bigger.length()) {
                break;
            }

            char smallerChar = smaller.charAt(i);
            char biggerChar = bigger.charAt(i);
            if (smallerChar != biggerChar) {
                if (this.alphabetRelationships.get(biggerChar) != null) {
                    List<Character> sortedAlphabetFromBiggerChar = buildSortedAlphabetFromLastCharacter(biggerChar);
                    if (!sortedAlphabetFromBiggerChar.contains(smallerChar)) {
                        this.alphabetRelationships.put(biggerChar, smallerChar);
                    }
                } else {
                    this.alphabetRelationships.put(biggerChar, smallerChar);
                }
                break;
            }
        }
    }
}
