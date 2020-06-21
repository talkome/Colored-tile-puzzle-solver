import java.util.ArrayList;
import java.util.Hashtable;

/**
 * this class represent DFID search algorithm
 * with loop-avoidance
 * recursive implementation
 * @autor Ko tal
 */
public class DFID extends Algorithms{
    private Hashtable<String,State> hTable;
    private boolean result;

    public DFID(State first,boolean isWithTime,boolean isWithOpenList) {
        this.isWithTime = isWithTime;
        this.isWithOpenList = isWithOpenList;
        this.path = "";
        this.cost = 0;
        this.statesExpCounter = 1;
        this.iterCounter = 0;
        this.hTable = new Hashtable<String, State>(); // check if the vertex is exist in our path
        this.first = first;
        this.result = false;
        if (isWithTime)
            this.startTime = System.currentTimeMillis();
        solving();
    }

    public void solving(){
        boolean found = false;
        for (int depth = 1; depth < Integer.MAX_VALUE; depth++) { // l = 1
            result = LimitedDFID(first,depth,hTable);
            if (result){
                found = true;
                if (isWithTime) {
                    finishTime = System.currentTimeMillis();
                    timeToEnd = finishTime - startTime;
                }
                return;
            }
        }
    }

    private boolean LimitedDFID(State state, int limit, Hashtable<String,State> HT) {
        if (state.isGoalState()) {
            this.path = state.getPath();
            this.cost = state.getCost();
            return true;
        }
        if (limit == 0)
            return false;

        HT.put(state.getKey(),state);
        ArrayList<State> sonsList = state.getAllOperations();
        for (State son : sonsList) {
            statesExpCounter++;
            if (!HT.containsKey(son.getKey())) { // if this state already exist in our path
                boolean isCutOff = LimitedDFID(son, limit - 1, HT);
                if (isCutOff) return true;
            }
        }
        HT.remove(state.getKey());
        return false;
    }
}
