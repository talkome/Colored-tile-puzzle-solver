import java.util.*;

/**
 * this class represent the A* search algorithm
 * @autor Ko tal
 */
public class AStar extends Algorithms{
    private PriorityQueue<State> priorityQueue;
    private Hashtable<String,State> closedList,openList;

    public AStar(State first,boolean isWithTime,boolean isWithOpenList) {
        this.isWithTime = isWithTime;
        this.isWithOpenList = isWithOpenList;
        this.first = first;
        this.path = "";
        this.cost = 0;
        this.statesExpCounter = 1;
        this.iterCounter = 0;
        this.priorityQueue = new PriorityQueue<State>();
        this.closedList = new Hashtable<String, State>();
        this.openList = new Hashtable<String, State>();
        if (isWithTime)
            this.startTime = System.currentTimeMillis();
        solving();
    }

    public void solving() {
        boolean found = false;
        priorityQueue.add(first);
        ArrayList<State> sonsList;
        openList.put(first.getKey(), first);
        while (!priorityQueue.isEmpty() || found){
            if (isWithOpenList) System.out.println(openList);
            iterCounter++;
            State curr = priorityQueue.poll();

            if (curr.isGoalState()){
                found = true;
                cost = curr.getCost();
                path = curr.getPath();
                if (isWithTime){
                    finishTime = System.currentTimeMillis();
                    timeToEnd = finishTime - startTime;
                }
                return;

            } else {
                openList.remove(curr.getKey());
                closedList.put(curr.getKey(), curr); // put in the close list
                sonsList = curr.getAllOperations(); // expand the current state
                for (State son : sonsList) {
                    statesExpCounter++;
                    if (!closedList.contains(son.getKey()) && !openList.contains(son.getKey())) {
                        openList.put(son.getKey(), son);
                        priorityQueue.add(son);

                    } else if (!openList.contains(son.getKey())) {
                        State t = openList.get(son.getKey());
                        if (son.getF() < t.getF()) {
                            openList.remove(t.getKey());
                            priorityQueue.remove(t);
                            openList.put(son.getKey(), son);
                            priorityQueue.add(son);
                        }
                    }
                }
            }
        }
    }
}
