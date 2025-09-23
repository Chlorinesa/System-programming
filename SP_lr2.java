class NewThread implements Runnable {
    int indexThread;

    public NewThread(int indexThread) {
        this.indexThread = indexThread;
    }

    @Override
    public void run() {
        System.out.println("Поток " + indexThread + " запущен, ID: "+ Thread.currentThread().getId());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Поток " + indexThread + " завершен, ID: "+ Thread.currentThread().getId());
    }
}
public class SP_lr2 {
    public static void main(String[] args) {
        for (int j = 0; j < 11; j++) {
            Thread thread = new Thread(new NewThread(j));
            thread.start();
        }
    }
}