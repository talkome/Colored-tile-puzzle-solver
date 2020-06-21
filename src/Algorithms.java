import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * this class represent the Base Algorithms class
 * of the searching algorithms in this project
 * @autor Ko tal
 */
public class Algorithms {
    protected boolean isWithTime, isWithOpenList;
    protected State first;
    protected String path;
    protected int cost, iterCounter, statesExpCounter;
    protected long startTime,finishTime,timeToEnd;

    /**
     * this method print the result of the algorithm
     * @throws FileNotFoundException
     */
    public void printResult() throws FileNotFoundException {
        String output = "output5.txt";
        PrintWriter pw = new PrintWriter(output);
        if (path.length() > 0){
            pw.write(path.substring(1, path.length())+"\n");
        } else {
            pw.write("no path\n");
        }
        pw.write("Num: " + this.statesExpCounter+"\n");
        if (path.length() > 0) {
            pw.write("Cost: " + this.cost + "\n");
        }
        if (isWithTime)
            pw.write("Time: " + (this.timeToEnd/1000.0) + " seconds\n");
        pw.close();
    }
}
