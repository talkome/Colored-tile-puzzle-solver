import java.io.*;
import java.util.ArrayList;

/**
 * this class represent the main TilePuzzle game
 * setting up the puzzle from the input text file and start the game
 * @autor Ko tal
 */
public class TilePuzzleGame {
    boolean isWithTime,isWithOpenList;
    String algorithmName;
    String runTime;
    String withOpenList;
    String puzzleSize;
    String[] black,red;
    State startState;
    int N,M;

    public TilePuzzleGame(String file) throws IOException {
        FileReader fr = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fr);
        this.algorithmName = bufferedReader.readLine(); //row 1: print chosen algorithm
        this.runTime = bufferedReader.readLine(); // row 2: with time / no time
        if (runTime.toLowerCase().contains("with time"))
            isWithTime = true;

        this.withOpenList = bufferedReader.readLine(); // row 3: with open / no open
        if (withOpenList.toLowerCase().contains("with open"))
            isWithOpenList = true;

        this.puzzleSize = bufferedReader.readLine(); // chose size of the game board
        String[] indexSize = puzzleSize.toLowerCase().split("x");
        this.N = Integer.parseInt(indexSize[0]);
        this.M = Integer.parseInt(indexSize[1]);
        if (N == 0 || M == 0){
            throw new RuntimeException("boards size cannot be 0");
        }
        Tile[][] gPuzzle = new Tile[N][M];

        this.black = bufferedReader.readLine().substring(6).replaceAll("\\s","").split(","); // number of black tile in the game puzzle
        ArrayList<Integer> blackList = new ArrayList<Integer>();
        for (int i = 0; i < black.length; i++) {
            if(!black[i].isEmpty())
                blackList.add(Integer.parseInt(black[i]));
        }
        this.red = bufferedReader.readLine().substring(4).replaceAll("\\s","").split(","); // number of red tile in the game puzzle
        ArrayList<Integer> redList = new ArrayList<Integer>();
        for (int i = 0; i < red.length; i++) {
            if(!red[i].isEmpty())
                redList.add(Integer.parseInt(red[i]));
        }

        for (int i = 0; i < N; i++) {
            String[] values = bufferedReader.readLine().split(",");
            for (int j = 0; j < M; j++) {
                if (values[j].equals("_")){
                    gPuzzle[i][j] = null;
                } else {
                    if (redList.contains(Integer.parseInt(values[j]))){
                        gPuzzle[i][j] = new Tile(Integer.parseInt(values[j]), Tile.Colors.RED);
                    } else if (blackList.contains(Integer.parseInt(values[j]))){
                        gPuzzle[i][j] = new Tile(Integer.parseInt(values[j]), Tile.Colors.BLACK);
                    } else{
                        gPuzzle[i][j] = new Tile(Integer.parseInt(values[j]), Tile.Colors.GREEN);
                    }
                }
            }
        }
        this.startState = new State(gPuzzle);
    }

    public void solveGame() throws FileNotFoundException {
        switch (algorithmName) {

            case "BFS":
                BFS bfs = new BFS(startState,isWithTime,isWithOpenList);
                bfs.printResult();
                break;

            case "DFID":
                DFID dfid = new DFID(startState,isWithTime,isWithOpenList);
                dfid.printResult();
                break;

            case "A*":
                AStar aStar = new AStar(startState,isWithTime,isWithOpenList);
                aStar.printResult();
                break;

            case "IDA*":
                IDAStar idaStar = new IDAStar(startState,isWithTime,isWithOpenList);
                idaStar.printResult();
                break;

            case "DFBnB":
                DFBnB dfBnB = new DFBnB(startState,isWithTime,isWithOpenList);
                dfBnB.printResult();
                break;
        }
    }
}
