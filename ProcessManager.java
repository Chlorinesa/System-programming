import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProcessManager {
    private final List<ProcessHandle> processes = new ArrayList<>();
    public void createProcess(String name) {
        if (name.isEmpty()) {
            System.out.println("Имя не введено");
            return;
        }
        String command = name.endsWith(".exe") ? name : name + ".exe";
        try {
            Process process = new ProcessBuilder(command).start();
            ProcessHandle handle = process.toHandle();
            processes.add(handle);
            System.out.println("Запущен процесс: " + command + " (PID=" + handle.pid() + ")");
        } catch (IOException e) {
            System.out.println("Не удалось запустить процесс: " + e.getMessage());
        }
    }
    public void showProcessesInfo() {
        if (processes.isEmpty()) {
            System.out.println("Нет активных процессов");return;
        }
        for (int i = 0; i < processes.size(); i++) {
            ProcessHandle ph = processes.get(i);
            boolean alive = ph.isAlive();
            String status = alive ? "активен" : "завершён";
            System.out.println("№ " + (i + 1) + " PID - " + ph.pid() + "  Статус - " + status);
        }
    }
    public void terminateProcess(int index) {
        if (processes.isEmpty()) {
            System.out.println("Нет процессов для завершения");return;
        }
        if (index < 0 || index >= processes.size()) {
            System.out.println("Номер за пределами списка");return;
        }
        ProcessHandle ph = processes.get(index);
        if (ph.isAlive()) {
            boolean terminated = ph.destroy();
            if (!terminated) {
                ph.destroyForcibly();
            }
            System.out.println("Процесс завершён");
        } else {
            System.out.println("Процесс уже завершён");
        }
        processes.remove(index);
    }
    public void terminateAllProcesses() {
        for (ProcessHandle ph : processes) {
            if (ph.isAlive()) {
                if (!ph.destroy()) {
                    ph.destroyForcibly();
                }
            }
        }
        processes.clear();
        System.out.println("Все процессы завершены");
    }
}
