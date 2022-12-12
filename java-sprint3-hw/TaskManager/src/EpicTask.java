import java.util.ArrayList;

public class EpicTask extends Task {
   ArrayList<Integer> subTasksIds = new ArrayList<>();
    EpicTask (String newTaskName, String newTaskDescription) {
        this.taskName = newTaskName;
        this.taskDescription = newTaskDescription;
        this.isNew = true;
        this.isInProgress = false;
        this.isDone = false;

    }
}
