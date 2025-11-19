import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SP_lr8_play_song_and_download_image {
    private static final String SONG_PATH= "music/";
    private static final String IMAGE_PATH = "images/";
    private static ExecutorService executor = Executors.newFixedThreadPool(5);

    public static void main(String[] args) {
        try {
            new File(SONG_PATH).mkdirs();
            new File(IMAGE_PATH).mkdirs();
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("1 - Скачать и открыть картинку");
                System.out.println("2 - Скачать и включить музыку");
                System.out.println("3 - Музыка играет параллельно скачиванию картинки");
                String choice = scanner.nextLine();
                switch (choice) {
                    case "1": downloadAndOpenImage(scanner);break;
                    case "2": downloadAndPlaySong(scanner);break;
                    case "3": parallelSongAndImage(scanner);break;
                    default: System.out.println("Неверный выбор!");
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
        } finally {
            executor.shutdown();
        }
    }

    // Task 1: Download and open image
    private static void downloadAndOpenImage(Scanner scanner) {
        System.out.print("Введите URL картинки: ");
        String imageUrl = scanner.nextLine().trim();
        executor.submit(() -> {
            try {
                String fileName = IMAGE_PATH + "image_" + System.currentTimeMillis() + getFileExtension(imageUrl);
                downloadFile(imageUrl, fileName);
                System.out.println(" Картинка скачана: " + new File(fileName).getName());
                // Отображение скаченной картинки
                openImageFile(new File(fileName));
                System.out.println(" Картинка открыта!");

            } catch (IOException e) {
                System.err.println(" Ошибка загрузки картинки: " + e.getMessage());
            }
        });
    }

    // Task 2: Download and play song
    private static void downloadAndPlaySong(Scanner scanner) {
        System.out.print("Введите URL музыки: ");
        String songUrl = scanner.nextLine().trim();
        executor.submit(() -> {
            try {
                String fileName = SONG_PATH + "track_" + System.currentTimeMillis() + getFileExtension(songUrl);
                downloadFile(songUrl, fileName);
                System.out.println(" Музыка скачана: " + new File(fileName).getName());
                // Воспроизведение музыки
                playWithSystemPlayer(new File(fileName));
                System.out.println(" Музыка запущена!");
            } catch (IOException e) {
                System.err.println(" Ошибка загрузки музыки: " + e.getMessage());
            }
        });
    }

    // Task 3: Parallel play song and download image
    private static void parallelSongAndImage(Scanner scanner) {
        System.out.print("Введите URL музыки: ");
        String songUrl = scanner.nextLine().trim();
        System.out.print("Введите URL картинки: ");
        String imageUrl = scanner.nextLine().trim();
        final String songFileName = SONG_PATH + "track_" + System.currentTimeMillis() + getFileExtension(songUrl);
        final String imageFileName = IMAGE_PATH + "image_" + System.currentTimeMillis() + getFileExtension(imageUrl);
        // Загрузка музыки и воспроизведение
        executor.submit(() -> {
            try {
                downloadFile(songUrl, songFileName);
                System.out.println("Музыка скачана: " + new File(songFileName).getName());
                // Воспроизведение музыки после загрузки
                playWithSystemPlayer(new File(songFileName));
                System.out.println("Музыка запущена!");
            } catch (IOException e) {
                System.err.println("Ошибка загрузки музыки: " + e.getMessage());
            }
        });

        // Параллельная загрузка картинки
        executor.submit(() -> {
            try {
                downloadFile(imageUrl, imageFileName);
                System.out.println("✓ Картинка скачана: " + new File(imageFileName).getName());
                openImageFile(new File(imageFileName));
                System.out.println("Картинка открыта!");
            } catch (IOException e) {
                System.err.println(" Ошибка загрузки картинки: " + e.getMessage());
            }
        });

        System.out.println(" Параллельная загрузка запущена...");
    }

    private static String getFileExtension(String url) {
        if (url.contains(".mp3")) return ".mp3";
        if (url.contains(".wav")) return ".wav";
        if (url.contains(".png")) return ".png";
        if (url.contains(".jpeg")) return ".jpeg";
        if (url.contains(".gif")) return ".gif";
        return ".jpg";
    }

    private static void downloadFile(String fileUrl, String fileName) throws IOException {
        URL url = new URL(fileUrl);
        try (ReadableByteChannel byteChannel = Channels.newChannel(url.openStream());
             FileOutputStream stream = new FileOutputStream(fileName)) {
            stream.getChannel().transferFrom(byteChannel, 0, Long.MAX_VALUE);
        }
    }
    private static void playWithSystemPlayer(File musicFile) {
        try {
            Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start", "\"\"", "\"" + musicFile.getAbsolutePath() + "\""});
        } catch (Exception e) {
            System.err.println("Ошибка запуска плеера: " + e.getMessage());
        }
    }

    private static void openImageFile(File imageFile) {
        try {
            Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start", "\"\"", "\"" + imageFile.getAbsolutePath() + "\""});
        } catch (Exception e) {
            System.err.println("Ошибка открытия картинки: " + e.getMessage());
        }
    }
}