import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

class CopyFiles {
    public static void copyFileUsingStream(File source, File dest) throws IOException {
        if (!source.exists()) {
            throw new FileNotFoundException("Файл не найден: " + source.getPath());
        }
        try (InputStream is = new FileInputStream(source);
             OutputStream os = new FileOutputStream(dest)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        }
    }
}

public class SP_lr5_Copy_files{
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);

        System.out.println("Введите имя файла для копирования:");
        String strSourceName = in.next().trim();

        File source = new File(strSourceName);
        File dest1 = new File("dest1.txt");
        File dest2 = new File("dest2.txt");

        long startTime = System.nanoTime();
        CopyFiles.copyFileUsingStream(source, dest1);
        CopyFiles.copyFileUsingStream(source, dest2);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);

        System.out.println("Последовательное копирование заняло " + duration);

        System.out.println("Введите имя файла для параллельного копирования:");
        String strSourceName2 = in.next().trim();

        File source2 = new File(strSourceName2);
        File destParallel1 = new File("destParallel1.txt");
        File destParallel2 = new File("destParallel2.txt");

        long startTime2 = System.nanoTime();

        Arrays.asList(destParallel1, destParallel2).parallelStream().forEach(destFile -> {
            try {
                CopyFiles.copyFileUsingStream(source2, destFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        long endTime2 = System.nanoTime();
        long duration2 = (endTime2 - startTime2);
        System.out.println("Параллельное копирование заняло " + duration2);
    }
}