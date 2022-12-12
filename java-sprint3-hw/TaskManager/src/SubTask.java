public class SubTask extends Task {
Integer parentId;

    SubTask (String newTaskName, String newTaskDescription, int newParentId) {
        this.taskName = newTaskName;
        this.taskDescription = newTaskDescription;
        this.isNew = true;
        this.isInProgress = false;
        this.isDone = false;
        this.parentId = newParentId;
    }
}
