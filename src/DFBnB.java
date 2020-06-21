import java.util.*;

/**
 * this class represent DFBnB search algorithm
 * with loop-avoidance
 * @autor Ko tal
 */
public class DFBnB extends Algorithms{
    private Stack<State> stack;
    private Hashtable<String,State> openList;

    public DFBnB(State first,boolean isWithTime,boolean isWithOpenList) {
        this.isWithTime = isWithTime;
        this.isWithOpenList = isWithOpenList;
        this.first = first;
        this.path = "";
        this.cost = 0;
        this.statesExpCounter = 1;
        this.iterCounter = 0;
        this.stack = new Stack<State>(); // create stack
        this.openList = new Hashtable<String, State>(); // create hashtable for saving our path and check if state is exist in the stack
        if (isWithTime) this.startTime = System.currentTimeMillis();
        solving();
    }

    public void solving(){
        ArrayList<State> sonsList = new ArrayList<>();
        stack.push(first);
        openList.put(first.getKey(), first);
        int threshold = getBound();
        while (!stack.isEmpty()){
            iterCounter++;
            if (isWithOpenList) System.out.println(openList);
            State curr = stack.pop();
            if (curr.isMarkedOut()) { // if it marked out
                openList.remove(curr.getKey()); // remove from hashtable
            } else {
                curr.setMarkedOut(true); // for saving the current path
                stack.push(curr);
                sonsList = curr.getAllOperations(); // get all operations
                Collections.sort(sonsList); // we sort the arraylist according to their f values
                iterCounter += sonsList.size();
                for (int i = 0; i < sonsList.size(); i++) {
                    State son = sonsList.get(i);
                    statesExpCounter++;
                    if (son.getF() >= threshold) { // case 1, we can cut
                        sonsList.subList(i,sonsList.size()).clear(); // remove this son and all the other son that son.f >= threshold
                    } else if (openList.containsKey(sonsList.get(i).getKey()) && son.isMarkedOut()){ // case 2, this state is exist in our path
                            sonsList.remove(sonsList.get(i)); // this state is not necessary
                            i--;
                    } else if (openList.containsKey(sonsList.get(i).getKey()) && !son.isMarkedOut()){ //case 3, the state is exist in our stack
                        if (openList.get(sonsList.get(i).getKey()).getF() <= sonsList.get(i).getF()){
                                sonsList.remove(i);
                                i--;
                        } else {
                            openList.remove(sonsList.get(i).getKey());
                            stack.remove(sonsList.get(i));
                        }
                    } else if (sonsList.get(i).isGoalState()){ // case 4, if we reach here f_son < threshold
                        threshold = son.getF();
                        this.cost = son.getCost();
                        this.path = son.getPath();
                        if (isWithTime) {
                            this.finishTime = System.currentTimeMillis();
                            this.timeToEnd = finishTime - startTime;
                        }
                    }
                }
                Collections.reverse(sonsList); // insert sonList reverse order to the open list ans stack
                for (State son: sonsList) {
                    openList.put(son.getKey(),son);
                    stack.add(son);
                }
            }
        }
    }

    private int getBound() {
        int bound = first.getN()*first.getM();
        if (bound <= 12){
            bound = (int) Math.pow(2,bound);
        }
        return bound;
    }
}
