//Задача:
//В последовательности неотрицательных целых чисел не превышающих 10000, размером 1000 элементов найти число R удовлетворяющие следующим условиям:
//R минимально;
//R кратно 21;
//R произведение 2х различных элементов последовательности;
//если такого числа нет вывести -1
public class ConsistentSearch {
    public static void main(String[] args) {
        int[] mas = new int[1000];
        for (int i = 0; i < mas.length; i++) {
            mas[i] = ((int) (Math.random() * 10001));
        }
        int minMul = -1;
        for (int i = 0; i < mas.length; i++) {
            for (int j = i + 1; j < mas.length; j++) {
                int first = mas[i];
                int second = mas[j];
                int mul = first * second;
                if (mul % 21 == 0) {
                    if ((minMul==-1||mul < minMul)&&IntStream.of(mas).anyMatch(x ->x == mul)){
                        minMul = mul;
//                      System.out.println(first + " " + second);
                    }
                }
            }
        }
        System.out.println(minMul);
    }
}
