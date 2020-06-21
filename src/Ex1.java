import java.io.IOException;

/**
 * this the main class of the project
 * you can run the tile puzzle game by inserting the selected input text file
 * and run
 * @autor Ko tal 311148902
 */
public class Ex1 {
    public static void main(String[] args) throws IOException {
        String input = "input5.txt";
        TilePuzzleGame myGame = new TilePuzzleGame(input);
        myGame.solveGame();

        System.out.println("finish");
    }

}
