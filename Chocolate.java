import java.util.Scanner;
class ChocolateShop{
    int money;
    int price;
    int wrap;
    ChocolateShop(int money, int price, int wrap){
        this.money = money;
        this.price = price;
        this.wrap = wrap;
    }
    public void countChocolate(){
        System.out.println("Кол-во шоколадок= " + (money/price+ money/price/wrap));

    }
    public void profitablePrice(){
        int currentMoney = money;
        boolean found = false;
        int maxChocolate = 0;
        while (currentMoney > 0) {
            if (currentMoney%price == 0 && (currentMoney/price)%wrap == 0) {
                maxChocolate = currentMoney/price+ currentMoney/price/wrap;
                System.out.println("Для выгодной покупки нужно потратить " + currentMoney + " за " +  maxChocolate+ " шоколадки ");
                found = true;
                break;
            }
            currentMoney--;
        }
        if (!found) {
            System.out.println("Не удалось найти подходящую сумму для максимально выгодной покупки");
        }
    }

}
public class Chocolate {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Введите имеющиеся деньги");
        int money = in.nextInt();
        System.out.println("Введите цену за шоколадку");
        int price = in.nextInt();
        System.out.println("Введите количество обёрток, нужное, чтобы получить ещё одну шоколадку");
        int wrap = in.nextInt();
        ChocolateShop countCh = new ChocolateShop(money,price, wrap);
        countCh.countChocolate();

        System.out.println("Введите количество обёрток, нужное, чтобы получить ещё одну шоколадку");
        int wrap2 = in.nextInt();
        System.out.println("Введите цену за шоколадку");
        int price2 = in.nextInt();
        System.out.println("Введите сколько денег максимально вы готовы отдать за шоколадки");
        int maxMoney = in.nextInt();
        ChocolateShop profitablePr = new ChocolateShop(maxMoney,price2, wrap2);
        profitablePr.profitablePrice();
    }
}