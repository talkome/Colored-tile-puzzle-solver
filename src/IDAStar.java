import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Stack;

/**
 * this class represent the IDA* search algorithm
 * with loop-avoidance
 * @autor Ko tal
 */
public class IDAStar extends Algorithms{
    private Stack<State> stack;
    private Hashtable<String, State> openList;

    public IDAStar(State first,boolean isWithTime,boolean isWithOpenList){
        this.isWithTime = isWithTime;
        this.isWithOpenList = isWithOpenList;
        this.first = first;
        this.path = "";
        this.cost = 0;
        this.statesExpCounter = 1;
        this.iterCounter = 0;
        this.stack = new Stack<State>();
        this.openList = new Hashtable<String, State>();
        if (isWithTime)
            this.startTime = System.currentTimeMillis();
        solving();
    }

    public void solving(){
        int threshold = first.getH(); // initial the threshold to the heuristic function
        ArrayList<State> sonsList;
        while (threshold < Integer.MAX_VALUE) {
            int f_min = Integer.MAX_VALUE;
            stack.add(first);
            openList.put(first.getKey(), first);

            while (!stack.isEmpty()) {
                if (isWithOpenList) System.out.println(openList);
                State curr = stack.pop();

                if (curr.isMarkedOut()){
                    openList.remove(curr.getKey(),curr);
                } else {
                    curr.setMarkedOut(true); // marked it as out
                    openList.put(curr.getKey(),curr);
                    sonsList = curr.getAllOperations(); // expand the current state
                    for (State son : sonsList) {
                        statesExpCounter++;
                        if (son.getF() > threshold){ //this state should not insert stack
                            f_min = Math.min(f_min,son.getF()); // update f_min
                            continue; // next operation
                        }
                        if (openList.containsKey(son.getKey()) && son.isMarkedOut()){ //this state is on the current path, we would get loop
                            continue; // next operation
                        }
                        if (openList.containsKey(son.getKey()) && !son.isMarkedOut()){ //we get a state that exist in our stack
                            State t = openList.get(son.getKey());
                            if (son.getF() < t.getF()){
                                openList.remove(t.getKey(),t);
                                stack.remove(t);
                            } else {
                                continue;
                            }
                        }
                        if (son.isGoalState()){
                            cost = son.getCost();
                            path = son.getPath();
                            if (isWithTime){
                                finishTime = System.currentTimeMillis();
                                timeToEnd = finishTime - startTime;
                            }
                            return;
                        }
                        stack.push(son);
                        openList.put(son.getKey(),son);
                    }
                }
            }
            threshold = f_min;
        }
    }
}
