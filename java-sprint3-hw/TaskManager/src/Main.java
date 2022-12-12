import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Manager manager = new Manager();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {


        while (true) {
            printMenu();
            int command = scanner.nextInt();
            if (command == 1) {
                printSmallMenu();
                int subCommand = scanner.nextInt();
                if (subCommand > 0 && subCommand < 4) {
                    System.out.println("Введите наименование задачи");
                    String taskName = scanner.next();
                    System.out.println("Введите описание задачи");
                    String taskDescription = scanner.next();
                    int taskId = manager.createTask(subCommand, taskName, taskDescription);
                    if (taskId < 0) {
                        System.out.println("Не существует такого epicTaskId");
                    } else {
                        System.out.println("Задача под номером " + taskId + " успешно добавлена");
                    }
                } else {
                    System.out.println("Такого типа задач не существует");
                }
            } else if (command == 2) {
                printSmallMenu();
                int subCommand = scanner.nextInt();
                if (subCommand > 0 && subCommand < 4) {
                    ArrayList<String> taskList = manager.getTaskList(subCommand);
                    if (taskList.isEmpty()) {
                        System.out.println("Список задач пуст");
                    } else {
                        System.out.println(taskList);
                    }
                } else {
                    System.out.println("Такого типа задач не существует");
                }
            } else if (command == 3) {
                printSmallMenu();
                int subCommand = scanner.nextInt();
                if (subCommand > 0 && subCommand < 4) {
                    manager.clearAllTasks(subCommand);
                    System.out.println("Список задач выбранного типа пуст");
                } else {
                    System.out.println("Такого типа задач не существует");
                }
            } else if (command == 4) {
                printSmallMenu();
                int subCommand = scanner.nextInt();
                if (subCommand > 0 && subCommand < 4) {
                    System.out.println("Введите id задачи");
                    int id = scanner.nextInt();
                    System.out.println("Задача " + getTaskById(subCommand, id) + " получена");
                } else {
                    System.out.println("Такого типа задач не существует");
                }
            } else if (command == 5) {
                    boolean success = renewTask();
                    if (success) {
                        System.out.println("Задача успешно обновлена");
                    } else {
                        System.out.println("Введенные данные не верны");
                    }
            } else if (command == 6) {
                printSmallMenu();
                int subCommand = scanner.nextInt();
                if (subCommand > 0 && subCommand < 4) {
                    System.out.println("Введите id задачи");
                    int id = scanner.nextInt();
                    boolean result = manager.deleteById(subCommand, id);
                    if (result) {
                        System.out.println("Задача удалена успешно");
                    } else {
                        System.out.println("Такой задачи не существует");
                    }
                } else {
                    System.out.println("Такого типа задач не существует");
                }

            } else if (command == 7) {
                System.out.println("Введите id Эпика");
                int id = scanner.nextInt();
                ArrayList<String> subTasksByEpic = manager.returnSubTasksByEpic(id);
                if (subTasksByEpic.isEmpty()) {
                    System.out.println("Не существует такого Эпика");
                } else {
                    System.out.println(subTasksByEpic);
                }
            } else if (command == 0) {
                System.out.println("Выход из программы");
                break;
            } else {
                System.out.println("Такой команды не существует");
            }
        }
    }


    private static void printMenu() {
        System.out.println("1 - Создание задачи");
        System.out.println("2 - Получение списка всех задач");
        System.out.println("3 - Удаление всех задач");
        System.out.println("4 - Получение задачи");
        System.out.println("5 - Обновить задачу");
        System.out.println("6 - Удалить задачу");
        System.out.println("7 - Список подзадач Эпика");
    }

    private static void printSmallMenu() {
        System.out.println("1. Задача");
        System.out.println("2. Эпик");
        System.out.println("3. Подзадача");
    }

    public static String getTaskById(int subCommand, int id) {
        String taskName = null;
        if (subCommand == 1) {
            Task task = manager.getTaskById(id);
            taskName = task.taskName;
        } else if (subCommand == 2) {
            EpicTask epicTask = manager.getEpicTaskById(id);
            taskName = epicTask.taskName;
        } else if (subCommand == 3) {
            SubTask subTask = manager.getSubTaskById(id);
            taskName = subTask.taskName;
        }
        return taskName;
    }

    public static boolean renewTask() {
        boolean success = false;
        printSmallMenu();
        int subCommand = scanner.nextInt();
        if (subCommand > 0 && subCommand < 4) {
            System.out.println("Введите id задачи");
            int taskId = scanner.nextInt();
            System.out.println("Что вы хотите изменить ?");
            System.out.println("1 - Наименование задачи");
            System.out.println("2 - Описание задачи");
            System.out.println("3 - Статус задачи");
            int change = scanner.nextInt();
            if (change == 1) {
                System.out.println("Введите новое наименование");
                String newName = scanner.next();
                success = manager.changeTaskName(subCommand, taskId, newName);
            } else if (change == 2) {
                System.out.println("Введите новое описание");
                String newDescription = scanner.next();
                success = manager.changeTaskDescription(subCommand, taskId, newDescription);
            } else if (change == 3) {
                if (subCommand == 2) {
                    System.out.println("Для изменения статуса Эпика, воспользуйтесь изменением статуса его подзадач");
                } else {
                    System.out.println("Введите новый статус задачи:");
                    System.out.println("1 - в процессе");
                    System.out.println("2 - завершена");
                    int status = scanner.nextInt();
                    success = manager.renewTaskStatus(subCommand, taskId, status);
                }
            }
        } else {
            System.out.println("Такого типа задач не существует");
        }
        return success;
    }
}

