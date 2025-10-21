/**
 * Класс, представляющий банковский счет с синхронизированными операциями
 */
class Account{
    private int balance;
    private int needBalance;

    /**
     * Конструктор счета с начальным балансом
     * @param balance начальный баланс счета
     */
    Account(int balance){
        this.balance = balance;
        this.needBalance = 300;
    }

    /**
     * Возвращает баланс
     * @return баланс
     */
    public synchronized int getBalance(){
        return balance;
    }

    /**
     * Синхронизированный метод для увеличения баланса указанную сумму
     * @param amount сумма для пополнения
     */
    public synchronized void enlargeBalance(int amount){
        balance += amount;
        notifyAll();
        System.out.println("На баланс положили " + amount + ", баланс = " + getBalance());
    }

    /**
     * Синхронизированный метод для снятия на указанную сумму, если она не больше имеющейся суммы на балансе
     * @param amount сумма для снятия
     */
    public synchronized void withdrawBalance(int amount){
        if (balance - amount >= 0){
            balance -= amount;
            System.out.println("С баланса сняли " + amount + ", баланс = " + getBalance());
        } else{
            System.out.println("Сумма снятия больше баланса");
        }
    }

    /**
     * Синхронизированный метод, который проверяет достигнут ли желаемый баланс
     * @return true если текущий баланс меньше или равен желаемому, иначе false
     */
    public synchronized boolean waitBalance(){
        return balance <= needBalance;
    }

    /**
     * Синхронизированный метод, который ожидает пока баланс не достигнет желаемой суммы
     * @throws InterruptedException если поток был прерван во время ожидания
     */
    public synchronized void waitThread() throws InterruptedException{
        while (waitBalance()){
            wait();
        }
    }
}

/**
 * Класс, представляющий операцию со счетом, реализуют интерфейс Runnable
 */
class Bill implements Runnable{
    Account account;

    /**
     * Конструктор операции со счетом
     * @param account счет для выполнения операций
     */
    Bill(Account account){
        this.account = account;
    }

    /**
     * Запускает поток и вызывает метод для пополнения баланса, пока не достигнет желаемую сумму
     */
    @Override
    public void run(){
        try {
            while (account.waitBalance()){
                int amountEnlarge = (int) (Math.random() * 100) + 1;//рандомная сумма для пополнения
                account.enlargeBalance(amountEnlarge);
                Thread.sleep(1000);
            }
            System.out.println("Баланс достиг желаемой суммы");

        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}

/**
 * Главный класс для демонстрации работы со счетом
 */
public class BankBill{
    /**
     * Точка входа в программу
     * @param args аргументы командной строки
     * @throws InterruptedException если поток был прерван во время выполнения
     */
    public static void main(String[] args) throws InterruptedException{
        Account account = new Account(0);
        Thread bill = new Thread(new Bill(account));
        bill.start();
        account.waitThread();
        bill.join();
        int amountWithdraw = (int) (Math.random() * 100) + 1;//рандомная сумма для снятия
        account.withdrawBalance(amountWithdraw);
    }
}