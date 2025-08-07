import java.util.*;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        String[] texts = new String[25];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("aab", 30_000);
        }

        // Список для хранения всех потоков
        List<Thread> threads = new ArrayList<>();

        long startTs = System.currentTimeMillis(); // start time
        // Обрабатываем каждую строку в отдельном потоке
        for (String text : texts) {
            Thread thread = new Thread(() -> {
                int maxSize = 0;
                for (int i = 0; i < text.length(); i++) {
                    for (int j = 0; j < text.length(); j++) {
                        if (i >= j) {
                            continue;
                        }
                        boolean bFound = false;
                        for (int k = i; k < j; k++) {
                            if (text.charAt(k) == 'b') {
                                bFound = true;
                                break;
                            }
                        }
                        if (!bFound && maxSize < j - i) {
                            maxSize = j - i;
                        }
                    }
                }
                System.out.println(text.substring(0, 100) + " -> " + maxSize);
            });
            // Добавляем поток в список
            threads.add(thread);
            // Запускаем поток
            thread.start();
        }

        // Ждём завершения всех потоков
        for (Thread thread : threads) {
            thread.join(); // основной поток ждёт завершения каждого из дочерних
        }

        long endTs = System.currentTimeMillis(); // end time
        System.out.println("Time: " + (endTs - startTs) + "ms");
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}