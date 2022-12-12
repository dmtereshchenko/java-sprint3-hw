public class Task {
    String taskName;
    String taskDescription;
    boolean isNew;
    boolean isInProgress;
    boolean isDone;
    Task (String newTaskName, String newTaskDescription) {
        this.taskName = newTaskName;
        this.taskDescription = newTaskDescription;
        this.isNew = true;
        this.isInProgress = false;
        this.isDone = false;
    }

    public Task() {
        this.isNew = true;
        this.isInProgress = false;
        this.isDone = false;
    }
}
