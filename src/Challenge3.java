import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Challenge3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numberOfSamples = scanner.nextInt();
        for (int i = 0; i < numberOfSamples; i++) {
            int width = scanner.nextInt();
            int height = scanner.nextInt();
            int folds = scanner.nextInt();
            int punches = scanner.nextInt();
            Paper paper = new Paper(height, width);

            List<String> foldMoves = new ArrayList<>();
            for (int f = 0; f < folds; f++) {
                foldMoves.add(scanner.next());
            }

            // Make initial punches
            for (int p = 0; p < punches; p++) {
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                paper.addPunch(new Punch(x, y));
            }

            // Unfold the paper
            for (String foldMove : foldMoves) {
                switch (foldMove) {
                    case "R":
                        paper = unfoldRight(paper);
                        break;
                    case "L":
                        paper = unfoldLeft(paper);
                        break;
                    case "T":
                        paper = unfoldTop(paper);
                        break;
                    case "B":
                        paper = unfoldBottom(paper);
                        break;
                }
            }
            
            printPaper(i + 1, paper);
        }
        scanner.close();
    }

    private static void printPaper(int caseNumber, Paper paper) {
        System.out.printf("Case #%d:\n", caseNumber);
        paper.punches.sort(Comparator.comparing(Punch::getX).thenComparing(Punch::getY));
        int i = 1;
        for (Punch punch : paper.punches) {
            System.out.printf("%d %d\n", punch.x, punch.y);
        }
    }

    private static Paper unfoldTop(Paper paper) {
        Paper unfolded = new Paper(paper.height * 2, paper.width);
        for (Punch punch : paper.punches) {
            unfolded.addPunch(new Punch(punch.x, paper.height - 1 - punch.y));
            unfolded.addPunch(new Punch(punch.x, punch.y + paper.height));
        }
        return unfolded;
    }

    private static Paper unfoldBottom(Paper paper) {
        Paper unfolded = new Paper(paper.height * 2, paper.width);
        for (Punch punch : paper.punches) {
            unfolded.addPunch(new Punch(punch.x, punch.y));
            unfolded.addPunch(new Punch(punch.x, unfolded.height - punch.y - 1));
        }
        return unfolded;
    }

    private static Paper unfoldRight(Paper paper) {
        Paper unfolded = new Paper(paper.height, paper.width * 2);
        unfolded.punches.addAll(paper.punches);
        for (Punch punch : paper.punches) {
            unfolded.addPunch(new Punch(unfolded.width - 1 - punch.x, punch.y));
        }
        return unfolded;
    }

    private static Paper unfoldLeft(Paper paper) {
        Paper unfolded = new Paper(paper.height, paper.width * 2);
        for (Punch punch : paper.punches) {
            unfolded.addPunch(new Punch(punch.x + paper.width, punch.y));
            unfolded.addPunch(new Punch(paper.width - punch.x - 1, punch.y));
        }
        return unfolded;
    }

    private static class Punch {
        final int x;
        final int y;

        Punch(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

    private static class Paper {
        final int height;
        final int width;
        List<Punch> punches = new ArrayList<>();

        Paper(int height, int width) {
            this.height = height;
            this.width = width;
        }

        void addPunch(Punch punch) {
            this.punches.add(punch);
        }
    }
}
