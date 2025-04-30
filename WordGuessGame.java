import java.util.ArrayList;
import java.util.Scanner;

// Person is the superclass used to demonstrate inheritance; though not instantiated directly,
// it provides shared fields for Player (useful for "writing another class" + "subclassing").
class Person {
    protected String name;

    public Person(String name) {
        this.name = name;
    }
}

// Player is a subclass of Person, instantiated and used in the game
class Player extends Person {
    private int attemptsLeft;

    public Player(String name, int maxAttempts) {
        super(name);
        this.attemptsLeft = maxAttempts;
    }

    public int getAttemptsLeft() {
        return attemptsLeft;
    }

    public void loseAttempt() {
        attemptsLeft--; // method call is useful as it updates state
    }
}

public class WordGuessGame {

    // WORDS array (1-D array) used to select random words
    static String[] WORDS = { "programming", "computer", "java", "cybersecurity" };

    /**
     * Recursive search method.
     * Useful: actually checks if target letter is in the array at runtime.
     */
    public static boolean containsRecursively(char[] arr, char target, int index) {
        if (index >= arr.length) {
            return false;
        }
        if (arr[index] == target) {
            return true;
        }
        return containsRecursively(arr, target, index + 1);
    }

    /**
     * Builds masked word using String methods (charAt, contains)
     * Useful: provides visible feedback each turn.
     */
    public static String maskWord(String word, ArrayList<Character> guesses) {
        StringBuilder masked = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {           // loop is active and useful
            char c = word.charAt(i);                        // String.charAt used 
            if (guesses.contains(c)) {                      // String.contains & ArrayList.contains
                masked.append(c);
            } else {
                masked.append('_');
            }
        }
        return masked.toString();
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Welcome to WordGuessGame!");   // print statement

        // Two variables declared
        String playerName;
        int maxAttempts = 6;

        System.out.print("Enter your name: ");
        playerName = in.nextLine();

        // Instantiate Player (class + subclass + use)
        Player player = new Player(playerName, maxAttempts);

        // Choose a random word (uses Math.random and array) ⇒ variables useful
        int idx = (int)(Math.random() * WORDS.length);
        String wordToGuess = WORDS[idx];
        char[] wordChars = wordToGuess.toCharArray();   // fill and use array

        // ArrayList to track guesses (filling & using)
        ArrayList<Character> guessed = new ArrayList<>();

        // Main game loop (while loop useful) loop
        while (player.getAttemptsLeft() > 0) {
            String display = maskWord(wordToGuess, guessed);
            System.out.println("Word: " + display);

            // Check win condition using if
            if (!display.contains("_")) {
                System.out.println("Congratulations, " + playerName + "! You win!");
                break;
            }

            System.out.print("Guess a letter: ");
            String input = in.nextLine().toLowerCase();   // String.toLowerCase

            // switch for input length (control structure)
            switch (input.length()) {
                case 0:
                    System.out.println("No input; try again.");
                    continue;
                case 1:
                    char guess = input.charAt(0);
                    // Use recursive method to check guess
                    if (containsRecursively(wordChars, guess, 0)) {
                        System.out.println("Good guess!");
                    } else {
                        System.out.println("Wrong! Attempts left before: " + player.getAttemptsLeft());
                        player.loseAttempt();                   // method call
                        System.out.println("Attempts left now: " + player.getAttemptsLeft());
                    }
                    // Track guesses to avoid duplicates
                    if (!guessed.contains(guess)) {
                        guessed.add(guess);
                    }
                    break;
                default:
                    System.out.println("Please enter only one letter.");
            }
        }

        // Game over condition
        if (player.getAttemptsLeft() == 0) {
            System.out.println("Game over! The word was: " + wordToGuess);
        }

        in.close();
    }
}

