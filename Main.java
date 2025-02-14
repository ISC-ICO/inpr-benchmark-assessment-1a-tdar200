import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Step 1: Get the code length
        int codeLength = getCodeLength(scanner);

        // Step 2: Ask if the code should contain repeated digits
        boolean allowRepetition = askRepetitionPreference(scanner);

        // Step 3: Generate the secret code
        String secretCode = generateSecretCode(codeLength, allowRepetition);

        // Step 4: Ask for max attempts (optional)
        int maxAttempts = getMaxAttempts(scanner);

        // Step 5: Start guessing loop
        playGame(scanner, secretCode, maxAttempts);

        scanner.close();
    }

    // method to get code length from the user
    private static int getCodeLength(Scanner scanner) {
        int length;
        while (true) {
            System.out.println("Enter the length of code you want to crack between 2-6:");
            length = scanner.nextInt();
            if (length >= 2 && length <= 6) {
                break;
            }
            System.out.println("Invalid input! Please enter a number between 2 and 6.");
        }
        return length;
    }

    // method to ask whether the code should allow repeated digits
    private static boolean askRepetitionPreference(Scanner scanner) {
        while (true) {
            System.out.println("Should the code contain repeated digits? (yes/no):");
            String input = scanner.next().toLowerCase();
            if (input.equals("yes")) {
                return true;
            }
            if (input.equals("no")) {
                return false;
            }
            System.out.println("Invalid input! Please enter 'yes' or 'no'.");
        }
    }

    // method to generate the secret code based on user preferences
    private static String generateSecretCode(int length, boolean allowRepetition) {
        Random rand = new Random();
        char[] code = new char[length];
        Set<Integer> usedNumbers = new HashSet<>();

        int index = 0;
        while (index < length) {
            int digit = rand.nextInt(10);
            if (allowRepetition || !usedNumbers.contains(digit)) {
                code[index] = (char) ('0' + digit); // Convert int to char
                usedNumbers.add(digit);
                index++;
                System.out.println(usedNumbers);
                System.out.println(Arrays.toString(code));
            }
        }


        return new String(code); // Convert char array to String
    }


    // function to get the maximum number of attempts from the user
    private static int getMaxAttempts(Scanner scanner) {
        System.out.println("Enter the maximum number of attempts:");
        int attempts = scanner.nextInt();
        return attempts;
    }

    // The Main game
    private static void playGame(Scanner scanner, String secretCode, int maxAttempts) {
        int attempts = 0;
        boolean isCracked = false;

        System.out.println("Code length is " + secretCode.length() + ". Enter your guess:");

        while (attempts < maxAttempts) {
            String guess = scanner.next();

            if (guess.length() != secretCode.length()) {
                System.out.println("Your guess must be exactly " + secretCode.length() + " digits long.");
                continue;
            }

            attempts++;
            int correctChars = countCorrectCharacters(secretCode, guess);
            int correctPositions = countCorrectPositions(secretCode, guess);

            System.out.println("Your guess: " + guess);
            System.out.println("Correct characters found: " + correctChars +
                    " | Correct characters in correct position: " + correctPositions);

            if (correctPositions == secretCode.length()) {
                System.out.println("Congratulations! You cracked the code in " + attempts + " attempts!");
                isCracked = true;
                break;
            }

            if (attempts < maxAttempts) {
                System.out.println("Try again:");
            }
        }

        if (!isCracked) {
            System.out.println("Game over! The secret code was: " + secretCode);
        }
    }

    // Method to check correct characters
    private static int countCorrectCharacters(String secretCode, String guess) {
        Set<Character> secretSet = new HashSet<>();
        for (char c : secretCode.toCharArray()) {
            secretSet.add(c);
        }

        int count = 0;
        for (char c : guess.toCharArray()) {
            if (secretSet.contains(c)) {
                count++;
            }
        }
        return count;
    }

    // method to count correct characters in the correct position
    private static int countCorrectPositions(String secretCode, String guess) {
        int count = 0;
        for (int i = 0; i < secretCode.length(); i++) {
            if (secretCode.charAt(i) == guess.charAt(i)) {
                count++;
            }
        }
        return count;
    }
}
