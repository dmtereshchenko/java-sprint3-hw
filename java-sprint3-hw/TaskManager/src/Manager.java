import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;
public class Manager {
    HashMap<Integer, Task> simpleTasks = new HashMap<>();
    HashMap<Integer, EpicTask> epicTasks = new HashMap<>();
    HashMap<Integer, SubTask> subTasks = new HashMap<>();
    private static int s = 100000;
    private int taskId = 0;
    private int epicTaskId = 0;
    private int subTaskId = s;


    public int createTask(int subCommand, String taskName, String taskDescription) {
        Scanner scanner = new Scanner(System.in);
        int id = -1;
        if (subCommand == 1) {
            Task task = new Task(taskName, taskDescription);
            simpleTasks.put(taskId++, task);
            id = (taskId - 1);
        } else if (subCommand == 2) {
            EpicTask epicTask = new EpicTask(taskName, taskDescription);
            epicTasks.put(epicTaskId++, epicTask);
            id = (epicTaskId - 1);
        } else if (subCommand == 3) {
            System.out.println("Введите epicTaskId");
            int parentId = scanner.nextInt();
            if (epicTasks.containsKey(parentId)) {
            SubTask subTask = new SubTask(taskName, taskDescription, parentId);
            EpicTask parentEpicTask = epicTasks.get(parentId);
            parentEpicTask.subTasksIds.add(subTaskId);
            epicTasks.put(parentId, parentEpicTask);
            subTasks.put(subTaskId++, subTask);
            id = (subTaskId - 1);
            }
    } return id;
        }
    public ArrayList<String> getTaskList(int subCommand) {
        ArrayList<String> taskList = new ArrayList<>();
        if (subCommand == 1) {
            for (int i = 0; i < simpleTasks.size(); i++) {
                Task task = simpleTasks.get(i);
                String name = task.taskName;
                taskList.add(name);
            }
        } else if (subCommand == 2) {
            for (int i = 0; i < epicTasks.size(); i++) {
                EpicTask epicTask = epicTasks.get(i);
                String name = epicTask.taskName;
                taskList.add(name);
            }
        } else if (subCommand == 3) {
            for (int i = s; i < (s + subTasks.size()); i++) {
                SubTask subTask = subTasks.get(i);
                String name = subTask.taskName;
                taskList.add(name);
                }
        }
        return taskList;
    }

    public void clearAllTasks(int subCommand) {
        switch (subCommand) {
            case 1:
                simpleTasks.clear();
                taskId = 0;
                break;
            case 2:
                epicTasks.clear();
                epicTaskId = 0;
                subTasks.clear();
                subTaskId = s;
                break;
            case 3:
                subTasks.clear();
                subTaskId = s;
                for (int i = 0; i < epicTasks.size(); i++) {
                    EpicTask epicTask = epicTasks.get(i);
                    epicTask.subTasksIds.clear();
                }
                break;
        }
    }

    public Task getTaskById(int id) {
        return simpleTasks.get(id);
    }

    public EpicTask getEpicTaskById(int id) {
        return epicTasks.get(id);
    }

    public SubTask getSubTaskById(int id) {
        return subTasks.get(id);
    }


    public boolean renewTaskStatus(int subCommand, int id, int status) {
        boolean success = false;
        switch (subCommand) {
            case 1:
                if (simpleTasks.containsKey(id)) {
                Task task = simpleTasks.get(id);
                if (status == 1) {
                    task.isNew = false;
                    task.isInProgress = true;
                    task.isDone = false;
                    simpleTasks.put(id, task);
                    success = true;
                } else if (status == 2) {
                    task.isNew = false;
                    task.isInProgress = false;
                    task.isDone = true;
                    simpleTasks.put(id, task);
                    success = true;
                    break;
                } else {
                    success = false;
                    break;
                }
            }
            case 3:
                if (subTasks.containsKey(id)) {
                    SubTask subTask = subTasks.get(id);
                    if (status == 1) {
                        subTask.isNew = false;
                        subTask.isInProgress = true;
                        subTask.isDone = false;
                        subTasks.put(id, subTask);
                        EpicTask epicTask = epicTasks.get(subTask.parentId);
                        epicTask.isDone = false;
                        epicTask.isNew = false;
                        epicTask.isInProgress = true;
                        epicTasks.put(subTask.parentId, epicTask);
                        success = true;
                    } else if (status == 2) {
                        subTask.isNew = false;
                        subTask.isInProgress = false;
                        subTask.isDone = true;
                        subTasks.put(id, subTask);
                        boolean epicStatus = checkEpic(subTask);
                        if (epicStatus) {
                            EpicTask epicTask = epicTasks.get(subTask.parentId);
                            epicTask.isDone = true;
                            epicTask.isNew = false;
                            epicTask.isInProgress = false;
                            epicTasks.put(subTask.parentId, epicTask);
                        }
                        success = true;
                    } else {
                        success = false;
                    }
                }
        }
        return success;
    }
    public boolean changeTaskName(int subCommand, int id, String newName) {
        boolean success = false;
        if (subCommand == 1) {
            if (simpleTasks.containsKey(id)) {
                Task task = simpleTasks.get(id);
                task.taskName = newName;
                simpleTasks.put(id, task);
                success = true;
            }
        } else if (subCommand == 2) {
            if (epicTasks.containsKey(id)) {
                EpicTask epicTask = epicTasks.get(id);
                epicTask.taskName = newName;
                epicTasks.put(id, epicTask);
                success = true;
            }
        } else if (subCommand == 3) {
            if (subTasks.containsKey(id)) {
                SubTask subTask = subTasks.get(id);
                subTask.taskName = newName;
                subTasks.put(id, subTask);
                success = true;
            }
        }
        return success;
    }

    public boolean changeTaskDescription(int subCommand, int id, String newDescription) {
        boolean success = false;
        if (subCommand == 1) {
            if (simpleTasks.containsKey(id)) {
                Task task = simpleTasks.get(id);
                task.taskDescription = newDescription;
                simpleTasks.put(id, task);
                success = true;
            }
        } else if (subCommand == 2) {
            if (epicTasks.containsKey(id)) {
                EpicTask epicTask = epicTasks.get(id);
                epicTask.taskDescription = newDescription;
                epicTasks.put(id, epicTask);
                success = true;
            }
        } else if (subCommand == 3) {
            if (subTasks.containsKey(id)) {
                SubTask subTask = subTasks.get(id);
                subTask.taskDescription = newDescription;
                subTasks.put(id, subTask);
                success = true;
            }
        }
        return success;
    }

public boolean deleteById(int subCommand, int id) {
        boolean success = false;
        if (subCommand == 1) {
            if (simpleTasks.containsKey(id)) {
                simpleTasks.remove(id);
                success = true;
            }
        } else if (subCommand == 2) {
            if (epicTasks.containsKey(id)) {
                epicTasks.remove(id);
                for (int i = 0; i < subTasks.size(); i++) {
                    SubTask subTask = subTasks.get(i);
                    if (subTask.parentId == id) {
                        subTasks.remove(i);
                    }
                }
                success = true;
            }
        } else if (subCommand == 3) {
            if (subTasks.containsKey(id)) {
                subTasks.remove(id);
                for (int i = 0; i < epicTasks.size(); i++) {
                    EpicTask epicTask = epicTasks.get(i);
                    if (epicTask.subTasksIds.contains(id)) {
                        epicTasks.remove(id);
                    }
                }
                success = true;
            }
        }
       return success;
}
public ArrayList<String> returnSubTasksByEpic(int id) {
        ArrayList<String> subTasksList = new ArrayList<>();
        for (int i = 0; i < subTasks.size(); i++) {
            SubTask subTask = subTasks.get(i);
            if (subTask.parentId == id) {
                subTasksList.add(subTask.taskName);
            }
        }
        return subTasksList;
}

public boolean checkEpic(SubTask subTask) {
        EpicTask epicTask = epicTasks.get(subTask.parentId);
        ArrayList<Integer> subTasksByEpic = epicTask.subTasksIds;
        for (int id : subTasksByEpic) {
            SubTask currentSubTask = subTasks.get(id);
             if (!currentSubTask.isDone) {
                 return false;
             }
        }
        return true;
}
}