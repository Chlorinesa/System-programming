import java.math.BigInteger;
import java.util.Scanner;
import java.util.concurrent.*;
public class SP_lr1 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Scanner in = new Scanner(System.in);
        System.out.print("Введите первое число: ");
        int a = in.nextInt();
        System.out.print("Введите второе число: ");
        int b = in.nextInt();
        System.out.println("1 - Итеративное умножение\n" +
                "2 - Рекурсивное умножение с битовой оптимизацией\n"+
                "3 - Умножение битовыми операциями (русский крестьянин)\n" +
                "4 - Умножение через BigInteger\n" +
                "5 - Умножение через логарифмы\n" +
                "6 - Умножение через формулу квадратов\n" +
                "7 - Умножение Карацубы\n" +
                "8 - Параллельное умножение\n" );
        System.out.print("Введите номер выбранной операции: ");
        int x = in.nextInt();
            switch (x) {
                case 1: System.out.println(a + "*" + b + " = " + mulIterative(a, b)); break;
                case 2: System.out.println(a + "*" + b + " = " + mulRecursiveBit(a, b)); break;
                case 3: System.out.println(a + "*" + b + " = " + mulBit(a, b)); break;
                case 4: System.out.println(a + "*" + b + " = " + mulBigInteger(a, b)); break;
                case 5: System.out.println(a + "*" + b + " = " + mulLogarithm(a, b)); break;
                case 6: System.out.println(a + "*" + b + " = " + mulSquareFormula(a, b)); break;
                case 7: System.out.println(a + "*" + b + " = " + mulKaratsuba(a, b)); break;
                case 8: System.out.println(a + "*" + b + " = " + mulParallel(a, b)); break;
                default: System.out.println("Операции не существует"); break;
            }
    }

    static int mulIterative(int a, int b) {
        if (a == 0 || b == 0) return 0;
        int result = 0;
        int absB = Math.abs(b);
        for (int i = 0; i < absB; i++) {
            result += a;
        }
        return (b < 0) ? -result : result;
    }
    static int mulRecursiveBit(int a, int b) {
        if (b == 0) return 0;
        if (b == 1) return a;
        if (b == -1) return -a;

        if ((b & 1) == 0) {
            int half = mulRecursiveBit(a, b >> 1);
            return half + half;
        }
        else {
            if (b > 0)
                return a + mulRecursiveBit(a, b - 1);
            else
                return -mulRecursiveBit(a, -b);
        }
    }

    static int mulBit(int a, int b) {
        if (a == 0 || b == 0) return 0;
        int result = 0;
        int absA = Math.abs(a);
        int absB = Math.abs(b);

        while (absB > 0) {
            if ((absB & 1) == 1) {
                result += absA;
            }
            absA <<= 1;
            absB >>= 1;
        }
        return ((a < 0)^(b < 0))? -result : result;
    }

    static BigInteger mulBigInteger(int a, int b) {
        return BigInteger.valueOf(a).multiply(BigInteger.valueOf(b));
    }

    static int mulLogarithm(int a, int b) {
        if (a == 0 || b == 0) return 0;
        double logResult = Math.log(Math.abs(a)) + Math.log(Math.abs(b));
        long roundedResult = Math.round(Math.exp(logResult));
        if (roundedResult > Integer.MAX_VALUE) {
            throw new ArithmeticException("Переполнение при умножении");
        }
        return (int) roundedResult * (((a < 0)^(b < 0)) ? -1 : 1);
    }

    static int mulSquareFormula(int a, int b) {
        long sum = (long) a + b;
        long aSq = (long) a * a;
        long bSq = (long) b * b;
        long result = (sum * sum - (aSq + bSq)) / 2;
        if (result > Integer.MAX_VALUE || result < Integer.MIN_VALUE) {
            throw new ArithmeticException("Переполнение при умножении");
        }
        return (int) result;
    }

    static long mulKaratsuba(long x, long y) {
        if (x < 10 || y < 10) return x * y;
        int n = Math.max(String.valueOf(Math.abs(x)).length(), String.valueOf(Math.abs(y)).length());
        int m = n / 2;
        long power = (long) Math.pow(10, m);
        long a = x / power;
        long b_val = x % power;
        long c = y / power;
        long d = y % power;
        long ac = mulKaratsuba(a, c);
        long bd = mulKaratsuba(b_val, d);
        long adbc = mulKaratsuba(a + b_val, c + d) - ac - bd;

        return ac * (long) Math.pow(10, 2 * m) + adbc * power + bd;
    }

    static int mulParallel(int a, int b) throws InterruptedException, ExecutionException {
        if (a == 0 || b == 0) return 0;
        int absB = Math.abs(b);
        int threadCount = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        int chunkSize = (absB + threadCount - 1) / threadCount;
        Future<Integer>[] futures = new Future[threadCount];

        for (int i = 0; i < threadCount; i++) {
            final int start = i * chunkSize;
            final int end = Math.min(start + chunkSize, absB);
            futures[i] = executor.submit(() -> {
                int partialSum = 0;
                for (int j = start; j < end; j++) {
                    partialSum += a;
                }
                return partialSum;
            });
        }
        int total = 0;
        for (Future<Integer> future : futures) {
            total += future.get();
        }
        executor.shutdown();
        return (b < 0)? -total : total;
    }
}