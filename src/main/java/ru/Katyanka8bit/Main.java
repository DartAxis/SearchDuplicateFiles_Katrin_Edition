package ru.Katyanka8bit;


import java.io.File;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        String path;
        String logText = "Программа запущена : "+ LocalDateTime.now();
        System.out.println(logText);
        if (args.length != 1) {
            showMenu();
            System.exit(1);
        } else {
            path = args[0];
            long timeStart = System.currentTimeMillis();
            List<File> listPaths = FilePaths.getListPaths(path);
            logText = "Найдено всего "+listPaths.size()+" файлов";
            System.out.println(logText);
            if (listPaths.isEmpty()) {
                showMenu();
                System.out.println("Неверно указан путь директории или к нему нет доступа");
                System.exit(1);
            }
            Map<String, List<String>> fileDuplicate = new ConcurrentHashMap<>();
            startThread(listPaths, fileDuplicate);


            long timeStop = System.currentTimeMillis();
            long fileSize = FileSize.getSumFilesSize(fileDuplicate);
            System.out.println("Затрачено времени: " + (timeStop - timeStart) / 1000 + " сек.");
            if (fileSize == 0) {
                System.out.println("Дубликаты файлов не найдены");
            }
            System.out.println("Очистим всего: " + fileSize + "Mb");
            List<String> deleteFiles = new ArrayList<>();
            for(List<String> list: fileDuplicate.values()){
                for (int i = 1; i < list.size() ; i++) {
                    deleteFiles.add(list.get(i));
                }
            }
            System.out.println("Для удаления :"+deleteFiles.size()+" файлов");


        }
    }

    public static void showMenu() {
        System.out.println("Duplicates v. 1.0");
        System.out.println("java -jar duplicates.jar [path]\n");
        System.out.println("For example: ");
        System.out.println("java -jar duplicates.jar c:\\Windows");

    }

    public static void startThread(List<File> listPaths, Map<String, List<String>> fileDuplicate) {
        ExecutorService threadExecutor = Executors.newFixedThreadPool(16);
        for (File file : listPaths) {
            threadExecutor.submit(() -> {
                String hash = HashSum.getHashSumFile(file.toString());
                if (hash != null) {
                    if (fileDuplicate.containsKey(hash)) {
                        fileDuplicate.get(hash).add(file.toString());
                    } else {
                        List<String> list = new ArrayList<>();
                        list.add(file.toString());
                        fileDuplicate.put(hash, list);
                    }
                }
            });
        }
        threadExecutor.shutdown();
        try {
            threadExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
