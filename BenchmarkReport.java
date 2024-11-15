package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.*;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;
import java.io.FileReader;
import java.io.BufferedReader;
import java.text.DecimalFormat;

public class BenchmarkReport {

    public static void main(String[] args) {
        DecimalFormat df = new DecimalFormat("0.00%");
        int[] datasetSize = new int[10];
        int[][] counts = new int[10][50];
        long[][] times = new long[10][50];
        String[][] data = new String[10][5];
        String line = "";
        try {
            String userFilePath;
            JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            int returnValue = jfc.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = jfc.getSelectedFile();
                userFilePath = selectedFile.getAbsolutePath();
                BufferedReader br = new BufferedReader(new FileReader(userFilePath));
                String[] split;
                int row = 0;
                while ((line = br.readLine()) != null) {
                     split = line.split(",");
                     datasetSize[row] = Integer.parseInt(split[0]);
                     for (int i = 1; i < 101; i++){
                         if(i % 2 == 1){
                             counts[row][i/2] = Integer.parseInt(split[i]);
                         }
                         else {
                             times[row][(i/2)-1] = Long.parseLong(split[i]);
                         }
                     }
                     row++;
                }

                for (int i = 0; i < datasetSize.length; i++){
                    int size = datasetSize[i];
                    double averageCount = getAverage(counts[i]);
                    double countCoef  = getCoef(counts[i], averageCount);
                    double averageTime  = getAverage(times[i]);
                    double timeCoef  = getCoef(times[i], averageTime);

                    data[i] = new String[] {String.valueOf(size), String.valueOf(averageCount), df.format(countCoef), String.valueOf(averageTime), df.format(timeCoef)};
                }

            } else {
                throw new FileNotFoundException();
            }
        } catch (Exception e) {
            System.out.println("Error: ");
            e.printStackTrace();
        }
        String column[] = {"Size", "Avg Count", "Coef Count", "Avg Time", "Coef Time"};
        JTable table = new JTable(data, column);
        JScrollPane pane = new JScrollPane(table);
        JFrame frame = new JFrame("Benchmark Report");
        frame.add(pane);
        frame.setSize(400, 250);
        frame.setVisible(true);
    }

    private static double getAverage(int[] arr) {
        int sum = 0;
        for (int d : arr) sum += d;
        return 1.0d * sum / arr.length;
    }
    private static double getCoef(int[]arr, double average) {
        double variance = 0;
        for (int i = 0; i < arr.length; i++) {
            variance += Math.pow(arr[i] - average, 2);
        }
        variance /= arr.length;
        double sqrt = Math.sqrt(variance);
        return sqrt / average;
    }
    private static double getAverage(long[] arr) {
        int sum = 0;
        for (long d : arr) sum += d;
        return 1.0d * sum / arr.length;
    }
    private static double getCoef(long[]arr, double average) {
        double variance = 0;
        for (int i = 0; i < arr.length; i++) {
            variance += Math.pow(arr[i] - average, 2);
        }
        variance /= arr.length;
        double sqrt = Math.sqrt(variance);
        return sqrt / average;
    }
}