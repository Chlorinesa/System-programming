//В последовательности неотрицательных целых чисел не превышающих 10000, размером 1000 элементов найти число R удовлетворяющие следующим условиям:
//R минимально;
//R кратно 21;
//R произведение 2х различных элементов последовательности;
//если такого числа нет вывести -1
public class ConsistentSearchOnePass {
    public static void main(String[] args) {
        int[] mas = new int[1000];
        for (int i = 0; i < mas.length; i++) {
            mas[i] = ((int) (Math.random() * 10001));
        }
        int min = -1;
        int min_21 = -1;
        int min_3 = -1;
        int min_7 = -1;

        for (int i = 0; i < mas.length; i++) {
            int num = mas[i];
            if (min == -1 || num < min) {
                min = num;
            }
            if (num % 21 == 0) {
                if (min_21 == -1 || num < min_21) {
                    min_21 = num;
                }
            }
            if (num % 3 == 0) {
                if (min_3 == -1 || num < min_3) {
                    min_3 = num;
                }
            }
            if (num % 7 == 0) {
                if (min_7 == -1 || num < min_7) {
                    min_7 = num;
                }
            }
        }
        int R1 = min_21 * min;
        int R2 = min_3 * min_7;
        System.out.println(Math.min(R2, R1));
    }
}
