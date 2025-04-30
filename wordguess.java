import java.util.ArrayList;
import java.util.Scanner;

// Person is a base class that stores the player's name.
class Person {
    protected String name; // Name of the person

    public Person(String name) {
        this.name = name; // Sets the name
    }
}

// Player is a subclass of Person that adds attempts tracking.
class Player extends Person {
    private int attemptsLeft; // Number of attempts the player has left

    public Player(String name, int maxAttempts) {
        super(name); // Calls the Person constructor
        this.attemptsLeft = maxAttempts; // Sets the initial attempts
    }

    public int getAttemptsLeft() {
        return attemptsLeft; // Returns the remaining attempts
    }

    public void loseAttempt() {
        attemptsLeft--; // Reduces the attempts by 1
    }
}

public class WordGuessGame {

    // List of words for the game
    static String[] WORDS = { "programming", "computer", "java", "cybersecurity" };

    /**
     * Checks if a character is in the array using recursion.
     */
    public static boolean containsRecursively(char[] arr, char target, int index) {
        if (index >= arr.length) { // If we reach the end of the array
            return false;
        }
        if (arr[index] == target) { // If the character matches
            return true;
        }
        return containsRecursively(arr, target, index + 1); // Check the next character
    }

    /**
     * Creates a masked version of the word, showing guessed letters.
     */
    public static String maskWord(String word, ArrayList<Character> guesses) {
        StringBuilder masked = new StringBuilder(); // To build the masked word
        for (int i = 0; i < word.length(); i++) { // Go through each letter in the word
            char c = word.charAt(i); // Get the current letter
            if (guesses.contains(c)) { // If the letter was guessed
                masked.append(c); // Show the letter
            } else {
                masked.append('_'); // Hide the letter
            }
        }
        return masked.toString(); // Return the masked word
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in); // For user input
        System.out.println("Welcome to WordGuessGame!"); // Welcome message

        // Variables for the player's name and max attempts
        String playerName;
        int maxAttempts = 6;

        System.out.print("Enter your name: ");
        playerName = in.nextLine(); // Get the player's name

        // Create a Player object
        Player player = new Player(playerName, maxAttempts);

        // Pick a random word from the list
        int idx = (int)(Math.random() * WORDS.length); // Random index
        String wordToGuess = WORDS[idx]; // The word to guess
        char[] wordChars = wordToGuess.toCharArray(); // Convert the word to a char array

        // List to keep track of guessed letters
        ArrayList<Character> guessed = new ArrayList<>();

        // Main game loop
        while (player.getAttemptsLeft() > 0) {
            String display = maskWord(wordToGuess, guessed); // Get the masked word
            System.out.println("Word: " + display); // Show the masked word

            // Check if the player has guessed the whole word
            if (!display.contains("_")) { // No underscores means the word is complete
                System.out.println("Congratulations, " + playerName + "! You win!");
                break; // Exit the loop
            }

            System.out.print("Guess a letter: ");
            String input = in.nextLine().toLowerCase(); // Get the player's guess

            // Handle the input based on its length
            switch (input.length()) {
                case 0: // No input
                    System.out.println("No input; try again.");
                    continue; // Skip to the next loop iteration
                case 1: // Single character input
                    char guess = input.charAt(0); // Get the guessed letter
                    // Check if the letter is already guessed
                    if (guessed.contains(guess)) {
                        System.out.println("That letter is already guessed."); // Notify the player
                        continue; // Allow another chance
                    }
                    // Check if the letter is in the word
                    if (containsRecursively(wordChars, guess, 0)) {
                        System.out.println("Good guess!");
                    } else {
                        System.out.println("Wrong! Attempts left before: " + player.getAttemptsLeft());
                        player.loseAttempt(); // Reduce attempts
                        System.out.println("Attempts left now: " + player.getAttemptsLeft());
                    }
                    // Add the guess to the list
                    guessed.add(guess);
                    break;
                default: // Input is longer than one character
                    System.out.println("Please enter only one letter.");
            }
        }

        // If the player runs out of attempts
        if (player.getAttemptsLeft() == 0) {
            System.out.println("Game over! The word was: " + wordToGuess);
        }

        in.close(); // Close the scanner
    }
}
