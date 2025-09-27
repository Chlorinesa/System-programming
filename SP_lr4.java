class Chicken implements Runnable {
    String name;
    Chicken(String name) {
        this.name = name;
    }
    @Override
    public void run() {
        for(int i = 0; i< 8; i++){
            try {
                Thread.sleep(800);
                SP_lr4.eggOrChicken(name);
                System.out.println("\u001B[31m"+name + "!");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}
class Egg implements Runnable {
    String name;
    Egg(String name) {
        this.name = name;
    }
    @Override
    public void run() {
        for(int i = 0; i<8; i++){
            try {
                Thread.sleep(800);
               SP_lr4.eggOrChicken(name);
                System.out.println("\u001B[33m "+name+ "!");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}

public class SP_lr4{
    private static String lastWord = "";
    private static final Object lock = new Object();

    public static void main(String[] args) {
        Thread chicken = new Thread(new Chicken("Курица"));
        Thread egg = new Thread(new Egg("Яйцо"));
        chicken.start();
        egg.start();
        if (egg.isAlive()&&chicken.isAlive()) {
            try {
                chicken.join();
                egg.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("\u001B[32m " + "в споре выяснилось, что первым появилось: " + lastWord+ "!");
        }
    }
    public static void eggOrChicken(String name) {
        synchronized (lock) {
            lastWord = name;
        }
    }
}
