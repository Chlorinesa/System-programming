import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Scanner;

class CopyFiles{
    public static void copyFileUsingNio(Path source, Path dest) throws IOException{
        if(!Files.exists(source)){
            throw new IOException("Файл не найден: " + source.toAbsolutePath());
        }
        Files.copy(source, dest, StandardCopyOption.REPLACE_EXISTING);
    }
}

public class SP_lr6_NIO_Copy_files{
    public static void main(String[] args) throws IOException{
        Scanner in = new Scanner(System.in);

        // Последовательное копирование
        System.out.println("Введите имя файла для копирования: ");
        String strSourceName = in.next().trim();
        Path source = Paths.get(strSourceName);
        Path dest1 = Paths.get("dest1.txt");
        Path dest2 = Paths.get("dest2.txt");

        long startTime = System.nanoTime();
        CopyFiles.copyFileUsingNio(source, dest1);
        CopyFiles.copyFileUsingNio(source, dest2);
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println("Последовательное копирование заняло: " + duration + " нс");

        // Параллельное копирование
        System.out.println("Введите имя файла для параллельного копирования: ");
        String strSourceName2 = in.next().trim();
        Path source2 = Paths.get(strSourceName2);
        Path destParallel1 = Paths.get("destParallel1.txt");
        Path destParallel2 = Paths.get("destParallel2.txt");

        long startTime2 = System.nanoTime();
        Arrays.asList(destParallel1, destParallel2).parallelStream().forEach(destFile -> {
            try{
                CopyFiles.copyFileUsingNio(source2, destFile);
            }catch(IOException e){
                e.printStackTrace();
            }
        });
        long endTime2 = System.nanoTime();
        long duration2 = endTime2 - startTime2;
        System.out.println("Параллельное копирование заняло: " + duration2 + " нс");
    }
}