import java.util.ArrayList;

/**
 * this class represent the State
 * @autor Ko tal
 */
public class State implements Comparable<State> {

    enum Directions { UP,DOWN,LEFT,RIGHT };

    private int f,h,cost,iteration;
    private String path, key;
    private boolean markedOut;
    private int N, M, iOfBlankTile, jOfBlankTile;
    private char currDirection;
    private Tile[][] puzzle;

    public State(Tile[][] puzzle) {
        this.markedOut = false;
        this.path = "";
        this.cost = 0;
        this.currDirection = 0;
        this.iteration = 0;
        this.puzzle = puzzle;
        this.N = puzzle.length;
        this.M = puzzle[0].length;
        findBlankTile(puzzle);
        setH();
        setF();
        setKey();
    }

    /**
     * this function finding the pair (i,j) of the blank tile in our game board
     * @param puzzle
     */
    public void findBlankTile(Tile[][] puzzle){
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (puzzle[i][j] == null){
                    this.iOfBlankTile = i;
                    this.jOfBlankTile = j;
                    return;
                }
            }
        }
    }

    /**
     * this function run on the tile puzzle and check each block if his index-location equal to
     * his value
     * @return
     */
    public boolean isGoalState(){
        int count = 0;
        int numOfTiles = N * M -1;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                Tile currTile = puzzle[i][j];
                if (count < numOfTiles){
                    if (currTile == null||currTile.getValue() != i * M + j +1)
                        return false;
                }
                count++;
            }
        }
        return true;
    }

    /**
     * this function create a son for the current state
     * and update his info
     * @return
     */
    public State createState(){
        Tile[][] newPuzzle = new Tile[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (puzzle[i][j] != null){
                    newPuzzle[i][j] = new Tile(puzzle[i][j].getValue(),puzzle[i][j].getColor());
                }
            }
        }
        State newState = new State(newPuzzle);
        newState.path = this.path;
        newState.cost = this.cost;
        return newState;
    }

    /**
     * this function return an sonlist that contain all the sons of the current state
     * in each one we move the blank tile to the available directions
     * @return
     */
    public ArrayList<State> getAllOperations(){
        ArrayList<State> sonsList = new ArrayList<>();
        for (Directions dir : Directions.values()) {
            State son = getSon(dir);
            if (son != null){
                sonsList.add(son);
            }
        }
        return sonsList;
    }

    /**
     * this function move the blank tile to the available direction for each son
     * @param direction
     * @return
     */
    public State getSon(Directions direction) {
        switch(direction)
        {
            case UP:
                if (iOfBlankTile +1 < N && iOfBlankTile > 0) {
                    if (puzzle[iOfBlankTile + 1][jOfBlankTile].getColor() != Tile.Colors.BLACK) {
                        State up = createState();
                        Tile temp = up.puzzle[up.iOfBlankTile][up.jOfBlankTile];
                        up.puzzle[up.iOfBlankTile][up.jOfBlankTile] = up.puzzle[up.iOfBlankTile + 1][up.jOfBlankTile];
                        up.puzzle[up.iOfBlankTile + 1][up.jOfBlankTile] = temp;
                        up.iOfBlankTile++;
                        up.currDirection = 'U';
                        up.path += "-" + up.puzzle[up.iOfBlankTile - 1][up.jOfBlankTile].getValue() + up.currDirection;

                        if (up.puzzle[up.iOfBlankTile - 1][up.jOfBlankTile].getColor() == Tile.Colors.RED) {
                            up.cost += 30;
                        } else if (up.puzzle[up.iOfBlankTile - 1][up.jOfBlankTile].getColor() == Tile.Colors.GREEN) {
                            up.cost += 1;
                        } else {
                            throw new RuntimeException("Error: wrong input");
                        }
                        up.iteration++;
                        up.setH();
                        up.setF();
                        up.setKey();
                        return up;
                    }
                }
                break;

            case DOWN:
                if (iOfBlankTile < N && iOfBlankTile > 0) {
                    if (puzzle[iOfBlankTile - 1][jOfBlankTile].getColor() != Tile.Colors.BLACK) {
                        State down = createState();
                        Tile temp = puzzle[down.iOfBlankTile][down.jOfBlankTile];
                        down.puzzle[down.iOfBlankTile][down.jOfBlankTile] = down.puzzle[down.iOfBlankTile - 1][down.jOfBlankTile];
                        down.puzzle[down.iOfBlankTile - 1][down.jOfBlankTile] = temp;
                        down.iOfBlankTile--;
                        down.currDirection = 'D';
                        down.path += "-" + down.puzzle[down.iOfBlankTile + 1][down.jOfBlankTile].getValue() + down.currDirection;

                        if (down.puzzle[down.iOfBlankTile + 1][down.jOfBlankTile].getColor() == Tile.Colors.RED) {
                            down.cost += 30;
                        } else if (down.puzzle[down.iOfBlankTile + 1][down.jOfBlankTile].getColor() == Tile.Colors.GREEN) {
                            down.cost += 1;
                        } else {
                            throw new RuntimeException("Error: wrong input");
                        }
                        down.iteration++;
                        down.setH();
                        down.setF();
                        down.setKey();
                        return down;
                    }
                }
                break;

            case LEFT:
                if (jOfBlankTile > 0 && jOfBlankTile + 1 < M) {
                   if (puzzle[iOfBlankTile][jOfBlankTile + 1].getColor() != Tile.Colors.BLACK) {
                        State left = createState();
                        Tile temp = puzzle[left.iOfBlankTile][left.jOfBlankTile];
                        left.puzzle[left.iOfBlankTile][left.jOfBlankTile] = left.puzzle[left.iOfBlankTile][left.jOfBlankTile + 1];
                        left.puzzle[iOfBlankTile][jOfBlankTile + 1] = temp;
                        left.jOfBlankTile++;
                        left.currDirection = 'L';
                        left.path += "-" + left.puzzle[left.iOfBlankTile][left.jOfBlankTile - 1].getValue() + left.currDirection;
                        if (left.puzzle[left.iOfBlankTile][left.jOfBlankTile - 1].getColor() == Tile.Colors.RED) {
                            left.cost += 30;
                        } else if (left.puzzle[left.iOfBlankTile][left.jOfBlankTile - 1].getColor() == Tile.Colors.GREEN) {
                            left.cost += 1;
                        } else {
                            throw new RuntimeException("Error: wrong input");
                        }
                        left.iteration++;
                        left.setH();
                        left.setF();
                        left.setKey();
                        return left;
                    }
                }
                break;

            case RIGHT:
                if (jOfBlankTile > 0 && jOfBlankTile < M) {
                    if (puzzle[iOfBlankTile][jOfBlankTile - 1].getColor() != Tile.Colors.BLACK) {
                        State right = createState();
                        Tile temp = right.puzzle[right.iOfBlankTile][right.jOfBlankTile];
                        right.puzzle[right.iOfBlankTile][right.jOfBlankTile] = right.puzzle[right.iOfBlankTile][right.jOfBlankTile - 1];
                        right.puzzle[iOfBlankTile][jOfBlankTile -1] = temp;
                        right.jOfBlankTile--;
                        right.currDirection = 'R';
                        right.path += "-" + right.puzzle[right.iOfBlankTile][right.jOfBlankTile + 1].getValue() + right.currDirection;
                        if (right.puzzle[right.iOfBlankTile][right.jOfBlankTile + 1].getColor() == Tile.Colors.RED) {
                            right.cost += 30;
                        } else if (right.puzzle[right.iOfBlankTile][right.jOfBlankTile + 1].getColor() == Tile.Colors.GREEN) {
                            right.cost += 1;
                        } else {
                            throw new RuntimeException("Error: wrong input");
                        }
                        right.iteration++;
                        right.setH();
                        right.setF();
                        right.setKey();
                        return right;
                    }
                }
                break;

            default:
                throw new RuntimeException("Error: wrong input");
        }
        return null;
    }

    /**
     * this heuristic function
     * we calculate the distance between the block's value
     * and there origin location on the puzzle
     * using Manhattan Distance
     * @return
     */
    public void setH() {
        int currValue,sum = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                Tile currTile = puzzle[i][j];
                if (currTile != null && currTile.getValue() != i * M + j + 1) {
                    currValue = currTile.getValue();
                    if (currTile.getColor() == Tile.Colors.BLACK)
                        continue;
                    int expectedI = (currValue-1) / M;
                    int expectedJ = (currValue-1) % M;
                    int distI = i - expectedI;
                    int distJ = j - expectedJ;
                    sum += Math.abs(distI) + Math.abs(distJ);
                    if (currTile.getColor() == Tile.Colors.RED)
                        sum *= 30;
                }
            }
        }
        this.h = sum;
    }

    public int getH() {
        return this.h;
    }

    /**
     * this function calculate the value of the current state to be his cost + this heuristic function
     * @return
     */
    public void setF() {
        this.f = this.getCost() + this.getH();
    }

    public int getF() {
        return this.f;
    }

    /**
     * this function calculate for the current state his unique key
     */
    public void setKey() {
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (this.puzzle[i][j] != null){
                    key.append(this.puzzle[i][j].toString());
                } else {
                    key.append("(BLANKTILE)");
                }
            }
        }
        this.key = key.toString();
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return "State(" +
                "out = " + markedOut +
                ", f = " + f +
                ", h = " + h +
                ", g = " + cost +
                ", iter = " + iteration +
                ", path = '" + path + '\'' +
                ", key = '" + key + '\'' +
                ", blankTile = (" + iOfBlankTile + "," + jOfBlankTile + ")" +
                ", currDir = " + currDirection +
                ')';
    }

    @Override
    public int compareTo(State state) {
        if (this.getF() != state.getF()) return this.getF() - state.getF();
        else if(this.getIteration() != state.getIteration()) return this.getIteration() - state.getIteration();
        else return this.getCurrDirection() - state.getCurrDirection();
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public boolean isMarkedOut() {
        return markedOut;
    }

    public void setMarkedOut(boolean markedOut) {
        this.markedOut = markedOut;
    }

    public String getPath() {
        return path;
    }

    public int getIteration() {
        return iteration;
    }

    public char getCurrDirection() {
        return currDirection;
    }

    public int getN() {
        return N;
    }

    public int getM() {
        return M;
    }
}
