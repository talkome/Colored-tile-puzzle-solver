import java.util.*;

/**
 * this class represent BFS search algorithm
 * @autor Ko tal
 */
public class BFS extends Algorithms{
    private Queue<State> queue;
    private Hashtable<String,State> closedList,openList;

    public BFS(State first,boolean isWithTime,boolean isWithOpenList){
        this.isWithTime = isWithTime;
        this.isWithOpenList = isWithOpenList;
        this.first = first;
        this.path = "";
        this.cost = 0;
        this.statesExpCounter = 1;
        this.queue = new LinkedList<State>();
        this.closedList = new Hashtable<String,State>();
        this.openList = new Hashtable<String,State>();
        if (isWithTime)
            this.startTime = System.currentTimeMillis();
        solving();
    }

    public void solving(){
        boolean found = false;
        openList.put(first.getKey(), first); // insert first state in the open list
        queue.add(first);// insert first state to the queue
        ArrayList<State> sonsList;
        while (!queue.isEmpty() || found){
            iterCounter++;
            if (isWithOpenList) System.out.println("openlist: " + openList);
            State curr = queue.poll();
            openList.remove(curr.getKey()); // remove current state from the open list
            closedList.put(curr.getKey(),curr); // add this current state to the close list
            sonsList = curr.getAllOperations(); // expand the current state
            for (State son : sonsList) {
                statesExpCounter++;
                if (!closedList.containsKey(son.getKey()) && !openList.containsKey(son.getKey())){
                    if (son.isGoalState()){
                        this.cost = son.getCost();
                        this.path = son.getPath();
                        found = true;
                        if (isWithTime){
                            this.finishTime = System.currentTimeMillis();
                            this.timeToEnd = finishTime - startTime;
                        }
                        return;

                    } else {
                        queue.add(son);
                        openList.put(son.getKey(),son);
                    }
                }
            }
        }
    }
}
