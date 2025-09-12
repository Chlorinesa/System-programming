//Написать программу, запускающую экземпляр процесса, получающую информацию о этом процессе, завершающую процесс,
// на 5 имя процесса должно вводится с экрана, а завершение процесса по запросу с экрана "Завершить д/н"
import java.util.Scanner;

public class Main {
    private static final ProcessManager processManager = new ProcessManager();
    private static final Scanner in = new Scanner(System.in);
    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            System.out.println("1 - Создать процесс");
            System.out.println("2 - Вывести всю информацию о процессах");
            System.out.println("3 - Завершить процесс");
            System.out.println("4 - Вывести писок PID запущенных процессов");
            System.out.println("5 - Выход");
            System.out.print("Выберите опцию (1-5): ");
            String input = in.nextLine().trim();
            switch (input) {
                case "1": createProcess(); break;
                case "2": showProcessesInfo(); break;
                case "3": terminateProcess(); break;
                case "4": showProcessesPID(); break;
                case "5": running=false; break;
                default: System.out.println("Некорректный ввод, введите 1-5");
            }
        }
    }
    private static void createProcess() {
        System.out.print("Введите имя процесса: ");
        String name = in.nextLine().trim();
        processManager.createProcess(name);
    }
    private static void showProcessesInfo() {
        processManager.showProcessesInfo();
    }
    private static void terminateProcess() {
        processManager.showProcessesInfo();
        System.out.print("Введите имя процесса для завершения: ");
        String name = in.nextLine().trim();
        System.out.print("Завершить процесс? (да/нет): ");
        String confirm = in.nextLine().trim().toLowerCase();
        if (confirm.equals("да")) {
            processManager.terminateProcess(name);
        }
        else {
            System.out.println("Отмена завершения");
        }
    }
    private static void showProcessesPID() {
        processManager.showProcessesPID();
    }
}
