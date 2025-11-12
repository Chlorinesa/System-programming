import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ege_24_3tasks {
    public static void main(String[] args) throws IOException {
        // Задача 5 Тип 24 27691
        sequenceA();
        // Задача 3 Тип 24 27689
        sequenceXYZXYZ();
        // Задача 12 Тип 24 33526
        frequentCharacterBetween();
    }

    /**
      Текстовый файл содержит только заглавные буквы латинского алфавита (ABC…Z).
      Определите символ, который чаще всего встречается в файле между двумя одинаковыми символами.
      Например, в тексте CBCABABACCC есть комбинации CBC, ABA (два раза), BAB и CCC.
      Чаще всего— 3 раза — между двумя одинаковыми символами стоит B, в ответе для этого случая надо написать B.
     */
    public static void frequentCharacterBetween() {
        Map<Character, Integer> frequencyMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("24.txt"))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            String content = sb.toString();
            for (int i = 0; i < content.length() - 2; i++) {
                char left = content.charAt(i);
                char middle = content.charAt(i + 1);
                char right = content.charAt(i + 2);

                if (left == right) {
                    frequencyMap.put(middle, frequencyMap.getOrDefault(middle, 0) + 1);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        char mostFrequentChar = ' ';
        int maxFrequency = 0;
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() > maxFrequency) {
                maxFrequency = entry.getValue();
                mostFrequentChar = entry.getKey();
            }
        }

        System.out.println("Символ, который чаще всего встречается между двумя одинаковыми символами: " + mostFrequentChar);
    }
    /**
     Текстовый файл состоит не более чем из 106 символов A, B и C. Определите максимальное количество идущих подряд символов A.
     */
    public static void sequenceA() {
        int maxCount = 0;
        int currentCount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader("zadanie24_1.txt"))) {
            int symbol;
            while ((symbol = reader.read()) != -1) {
                char letter = (char) symbol;
                if (letter == 'A') {
                    currentCount++;
                    if (currentCount > maxCount) {
                        maxCount = currentCount;
                    }
                } else {
                    currentCount = 0;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Максимальное количество подряд идущих A: " + maxCount);
    }
    /**
     Текстовый файл состоит не более чем из 106 символов X, Y и Z.
     Определите максимальную длину цепочки вида XYZXYZXYZ... (составленной из фрагментов XYZ, последний фрагмент может быть неполным).
     */
    public static void sequenceXYZXYZ() {
        int maxLength = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader("24_demo.txt"))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            String content = sb.toString();
            Pattern pattern = Pattern.compile("(XYZ)*X?Y?");
            Matcher matcher = pattern.matcher(content);

            while (matcher.find()) {
                int length = matcher.group().length();
                if (length > maxLength) {
                    maxLength = length;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Максимальная длина цепочки XYZXYZ...: " + maxLength);
    }
}