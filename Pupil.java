import java.util.*;

public class Pupil {
    public static void main(String[] arguments) {
        try (Scanner inputScanner = new Scanner(System.in)) {
            ArrayList<String> namesList = new ArrayList<>();
            ArrayList<Integer> scoresList = new ArrayList<>();
            int highestScore;

            System.out.println("Welcome to the Score Analyzer!\n");
            System.out.println("Please enter the examination title:");
            String testTitle = inputScanner.nextLine();
            System.out.println("Please enter the Maximum Score of the examination:");
            highestScore = inputScanner.nextInt();
            System.out.println("Please enter the count of pupils:");

            int totalPupils = 0;
            while (true) {
                try {
                    totalPupils = inputScanner.nextInt();
                    break;
                } catch (InputMismatchException exception) {
                    System.out.println("Invalid Input. Please enter a valid count of pupils:");
                    inputScanner.next();
                }
            }

            for (int i = 0; i < totalPupils; i++) {
                System.out.print("Enter the name/register number of pupil " + (i + 1) + ": ");
                String pupilName = inputScanner.next();
                namesList.add(pupilName);

                boolean validScore = false;
                while (!validScore) {
                    try {
                        int score;
                        while (true) {
                            System.out.print("Enter the score for pupil " + pupilName + ": ");
                            score = inputScanner.nextInt();
                            if (score <= highestScore) {
                                break;
                            } else {
                                System.out.println("Invalid input. The score exceeds the maximum mark of " + highestScore);
                            }
                        }
                        scoresList.add(score);
                        validScore = true;
                    } catch (InputMismatchException exception) {
                        System.out.println("Invalid input. Please enter a valid score.");
                        inputScanner.next(); 
                    }
                }
            }

            double averageScore = computeAverage(scoresList);

            int topScore = getTopScore(scoresList);
            int bottomScore = getLowestScore(scoresList);

            ArrayList<String> topScorers = getMatchingScorers(namesList, scoresList, topScore);

            ArrayList<String> bottomScorers = getMatchingScorers(namesList, scoresList, bottomScore);

            System.out.println("\n" + testTitle + " Examination Summary:\n");
            System.out.println("Average Score: " + averageScore);
            System.out.println("\nTop Scorer(s):");
            for (String scorer : topScorers) {
                System.out.println(scorer + " with a score of " + topScore);
            }
            System.out.println("\nLowest Scorer(s):");
            for (String scorer : bottomScorers) {
                System.out.println(scorer + " with a score of " + bottomScore);
            }
        }
    }

    public static double computeAverage(ArrayList<Integer> scoresList) {
        int totalScore = 0;
        for (int score : scoresList) {
            totalScore += score;
        }
        return (double) totalScore / scoresList.size();
    }

    public static int getTopScore(ArrayList<Integer> scoresList) {
        int topScore = 0;
        for (int score : scoresList) {
            if (score > topScore) {
                topScore = score;
            }
        }
        return topScore;
    }

    public static int getLowestScore(ArrayList<Integer> scoresList) {
        int bottomScore = Integer.MAX_VALUE;
        for (int score : scoresList) {
            if (score < bottomScore) {
                bottomScore = score;
            }
        }
        return bottomScore;
    }

    public static ArrayList<String> getMatchingScorers(ArrayList<String> namesList, ArrayList<Integer> scoresList,
            int targetScore) {
        ArrayList<String> matchingScorers = new ArrayList<>();
        for (int i = 0; i < scoresList.size(); i++) {
            if (scoresList.get(i) == targetScore) {
                matchingScorers.add(namesList.get(i));
            }
        }
        return matchingScorers;
    }
}
