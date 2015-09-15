package bt;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Hallvard on 14.09.2015.
 */
public abstract class Composite<E> extends Task<E> {

    public boolean deterministic = true; //TODO

    protected int childIndex;

    public Composite(ArrayList<Task<E>> tasks) {
        this.children = tasks;
    }

    @Override
    public void childRunning(Task<E> focal, Task<E> nonFocal) {
        this.runningTask = focal;
        parent.childRunning(focal, this);
    }

    @Override
    public void run() {
        if(runningTask != null)
            runningTask.run();
        else {
            if(childIndex < children.size()) {
                if(!deterministic) {
                    final int lastChild = children.size() - 1;
                    if(childIndex < lastChild) Collections.swap(children, childIndex, Math.random() >= 0.5 ? childIndex : lastChild);
                }
                runningTask = children.get(childIndex);
                runningTask.setParent(this);
                runningTask.start();
                run();
            } else
                end();
        }
    }

    @Override
    public void start() {
        this.childIndex = 0;
        runningTask = null;
    }

    @Override
    public void reset() {
        super.reset();
        this.childIndex = 0;
    }


    //TODO
}
