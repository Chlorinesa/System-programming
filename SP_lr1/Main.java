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
            System.out.println("2 - Информация о процессах");
            System.out.println("3 - Завершить процесс");
            System.out.println("4 - Завершить все процессы и выйти");
            System.out.print("Выберите опцию (1-4): ");
            String input = in.nextLine().trim();
            switch (input) {
                case "1": createProcess(); break;
                case "2": showProcessesInfo(); break;
                case "3": terminateProcess(); break;
                case "4": terminateAllProcesses(); running = false; break;
                default: System.out.println("Некорректный ввод, введите 1-4");
            }
        }
    }
    private static void createProcess() {
        System.out.print("Введите имя процесса (без .exe): ");
        String name = in.nextLine().trim();
        processManager.createProcess(name);
    }
    private static void showProcessesInfo() {
        processManager.showProcessesInfo();
    }
    private static void terminateProcess() {
        processManager.showProcessesInfo();
        System.out.print("Введите номер процесса для завершения: ");
        String num = in.nextLine().trim();
        int index;
        index = Integer.parseInt(num) - 1;
        System.out.print("Завершить процесс? (да/нет): ");
        String confirm = in.nextLine().trim().toLowerCase();
        if (confirm.equals("да")) {
            processManager.terminateProcess(index);
        }
        else {
            System.out.println("Отмена завершения");
        }
    }
    private static void terminateAllProcesses() {
        processManager.terminateAllProcesses();
    }
}
