import java.util.Random;
import java.util.Scanner;

public class Task3NumberGuess {
    public static void main(String[] args) {
        int number, guess, numberOfGuesses;
        int RANGE = 100;
        Scanner input = new Scanner(System.in);
        Random rand = new Random();
        boolean correctGuess = false;
        numberOfGuesses = 0;
        number = rand.nextInt(RANGE);

        while(!correctGuess) {
            System.out.println("Guess the number between 0 and " + RANGE + " :");
            guess = input.nextInt();
            numberOfGuesses++;
            if(guess < number) {
                System.out.println("Try higher!\n");
            }
            else if(guess > number) {
                System.out.println("Try lower!\n");
            }
            else {
                System.out.println("Correct Guess. The number was " + number + " and it took you " + numberOfGuesses + " guesses to figure it out.");
                correctGuess = true;
            }
        }
        input.close();
        System.exit(0);
    }
}