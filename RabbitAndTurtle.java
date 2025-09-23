class AnimalThread extends Thread {
    private String name;
    private int distance;
    public static final int raceDistance = 100;
    private static volatile boolean raceFinished = false;
    private static AnimalThread leader;
    private static final Object lock = new Object();

    AnimalThread(String name, int priority) {
        this.name = name;
        setPriority(priority);
        this.distance = 0;
    }
    public int getDistance() {
        return distance;
    }
    @Override
    public void run() {
        while (!raceFinished) {
            synchronized (lock) {
                if (raceFinished) {
                    break;
                }
                distance += (int) (Math.random() * 8) + 4;
                System.out.println(name + " преодолел(а) " + distance + "м. с приоритетом: " + getPriority());

                if (leader == null || distance > leader.getDistance()) {
                    leader = this;
                }
                if (distance >= raceDistance) {
                    raceFinished = true;
                    System.out.println(name + " с приоритетом " + getPriority() + " финишировал первый, ID: " + Thread.currentThread().getId());
                    break;
                }
            }
            if (!raceFinished) {
                synchronized (lock) {
                    if (leader != this && (leader.getDistance() - distance) > 18) {
                        setPriority(Thread.MAX_PRIORITY);
                        leader.setPriority(Thread.MIN_PRIORITY);
                        System.out.println(name + " получает максимальный приоритет");
                    } else if (leader == this) {
                        setPriority(Thread.MAX_PRIORITY);
                    } else {
                        setPriority(Thread.NORM_PRIORITY);
                    }
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class RabbitAndTurtle {
    public static void main(String[] args) {
        AnimalThread rabbit = new AnimalThread("rabbit", Thread.MIN_PRIORITY);
        AnimalThread turtle = new AnimalThread("turtle", Thread.MAX_PRIORITY);
        rabbit.start();
        turtle.start();
    }
}
