class EggAndChicken extends Thread {
    private int time;
    public static final int raceTime = 100;
    private static volatile boolean raceFinished = false;
    private static EggAndChicken leader;
    private static final Object lock = new Object();
    String name;
    EggAndChicken(String name, int priority) {
        this.name = name;
        setPriority(priority);
        this.time = 0;
    }
    public int getDistance() {
        return time;
    }
    @Override
    public void run() {
        for (int i = 0; i < 8; i++) {
            while (!raceFinished) {
                synchronized (lock) {
                    if (raceFinished) {
                        break;
                    }
                    time += (int) (Math.random() * 8) + 4;
                    if(name.equals("Яйцо")){
                        System.out.println("\u001B[33m"+ name + "!  с приоритетом: " + getPriority());
                    }
                    else {
                        System.out.println("\u001B[31m"+ name + "!  с приоритетом: " + getPriority());
                    }
                    if (leader == null || time > leader.getDistance()) {
                        leader = this;
                    }
                    if (time >= raceTime) {
                        raceFinished = true;
                        SP_lr4_1.eggOrChicken(name);
                        break;
                    }
                }
                if (!raceFinished) {
                    synchronized (lock) {
                        if (leader != this && (leader.getDistance() - time) > 18) {
                            setPriority(Thread.MAX_PRIORITY);
                            leader.setPriority(Thread.MIN_PRIORITY);
                            System.out.println("\u001B[34m" + name + " получает максимальный приоритет");
                        } else if (leader == this) {
                            setPriority(Thread.MAX_PRIORITY);
                        } else {
                            setPriority(Thread.NORM_PRIORITY);
                        }
                    }
                }
                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }
    }
}
public class SP_lr4_1 {
    static String lastWord = "";
    static final Object lock = new Object();

    public static void main(String[] args) {
        Thread chicken = new Thread(new EggAndChicken("Курица", Thread.MIN_PRIORITY));
        Thread egg = new Thread(new EggAndChicken("Яйцо", Thread.MAX_PRIORITY));
        chicken.start();
        egg.start();
        if (egg.isAlive() && chicken.isAlive()) {
            try {
                chicken.join();
                egg.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("\u001B[34m " + "в споре выяснилось, что первым появилось: " + lastWord + "!");
        }
    }

    public static void eggOrChicken(String name) {
        synchronized (lock) {
            lastWord = name;
        }
    }
}