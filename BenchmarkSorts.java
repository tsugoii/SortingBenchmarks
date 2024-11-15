package com.company;

import java.io.*;
import java.util.Random;

public class BenchmarkSorts {

    static int sizeMultiplier = 100;

    // https://www.baeldung.com/java-jvm-warmup
    protected static void warmupLoader() {
        for (int i = 0; i < 10000000; i++) {
            SortInterface s = new YourSort();
            try {
                s.iterativeSort(createArray(10));
                s.recursiveSort(createArray(10));
            } catch (UnsortedException e) {
                e.printStackTrace();
            }
        }
    }

    static String[] toCsvLines(int[][] counts, long[][] times) {
        int rows = counts.length;
        int cols = counts[0].length;
        StringBuilder sb;
        String[] result = new String[rows];
        for (int i = 0; i < rows; i++) {
            sb = new StringBuilder();
            sb.append((i + 1) * sizeMultiplier);
            sb.append(",");
            for (int j = 0; j < cols; j++) {
                sb.append(counts[i][j]);
                sb.append(",");
                sb.append(times[i][j]);
                sb.append(",");
            }
            result[i] = sb.toString();
        }
        return result;
    }

    static int[] createArray(int size) {
        Random random = new Random();
        int[] list = new int[size];
        for (int i = 0; i < size; i++) {
            list[i] = random.nextInt();
        }
        return list;
    }

    public static void main(String[] args) {
        warmupLoader();
        SortInterface s = new YourSort();
        int[][] counts = new int[10][50];
        long[][] times = new long[10][50];
        try {
            for (int i = 0; i < 10; i++)
                for (int j = 0; j < 50; j++) {
                    s.iterativeSort(createArray((i + 1) * sizeMultiplier));
                    counts[i][j] = s.getCount();
                    times[i][j] = s.getTime();
                }

            //create iterative report
            saveFile("IterativeReport", toCsvLines(counts, times));

            warmupLoader();
            s = new YourSort();
            counts = new int[10][50];
            times = new long[10][50];

            for (int i = 0; i < 10; i++)
                for (int j = 0; j < 50; j++) {
                    s.recursiveSort(createArray((i + 1) * sizeMultiplier));
                    counts[i][j] = s.getCount();
                    times[i][j] = s.getTime();
                }
            //create recursive report
            saveFile("RecursiveReport", toCsvLines(counts, times));
            System.out.println("Files Saved to: " + System.getProperty("user.dir"));
        } catch (UnsortedException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void saveFile(String fileName, String[] lines) throws FileNotFoundException {
        // (System.getProperty("user.dir") + "\\RecursiveReport.txt");
        // (System.getProperty("user.dir") + "\\IterativeReport.txt");
        File fout = new File(System.getProperty("user.dir") + "\\" + fileName + ".txt");
        try (FileOutputStream fos = new FileOutputStream(fout); BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));) {
            for (String s : lines) {
                bw.write(s);
                bw.newLine();
            }
        } catch (IOException ignored) {

        }
    }
}
