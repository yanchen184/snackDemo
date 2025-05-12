package com.yc.snackoverflow;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;


@SpringBootTest
class SnackOverFlowApplicationTests {

    @Test
    void contextLoads() {
        // 假設我們有以下的敘述性統計數據
        List<String> headers = Arrays.asList("統計項目", "值", "描述");
        List<List<String>> data = Arrays.asList(
              Arrays.asList("平均值", "23.45", "樣本的平均值"),
              Arrays.asList("最大值", "98.76", "樣本中的最大值"),
              Arrays.asList("最小值", "1.23", "樣本中的最小值"),
              Arrays.asList("標準差", "4.56", "樣本的標準差")
        );

        // 打印表格到日誌
        logTable(headers, data);
    }

    public static void logTable(List<String> headers, List<List<String>> data) {
        // 計算每列的寬度
        int[] columnWidths = calculateColumnWidths(headers, data);

        // 打印表格
        StringBuilder tableBuilder = new StringBuilder();

        // 打印表頭
        tableBuilder.append(formatRow(headers, columnWidths)).append("\n");
        tableBuilder.append(generateSeparator(columnWidths)).append("\n");

        // 打印數據行
        for (List<String> row : data) {
            tableBuilder.append(formatRow(row, columnWidths)).append("\n");
        }

        // 將表格輸出到日誌
        System.out.println(tableBuilder);
    }

    private static int[] calculateColumnWidths(List<String> headers, List<List<String>> data) {
        int columns = headers.size();
        int[] columnWidths = new int[columns];

        // 初始化每列的寬度為表頭的寬度
        for (int i = 0; i < columns; i++) {
            columnWidths[i] = headers.get(i).length();
        }

        // 更新每列寬度為數據行中最寬的值
        for (List<String> row : data) {
            for (int i = 0; i < row.size(); i++) {
                columnWidths[i] = Math.max(columnWidths[i], row.get(i).length());
            }
        }

        return columnWidths;
    }

    private static String formatRow(List<String> row, int[] columnWidths) {
        StringBuilder rowBuilder = new StringBuilder();
        for (int i = 0; i < row.size(); i++) {
            rowBuilder.append(String.format(" %s %s", row.get(i), " ".repeat(columnWidths[i] - row.get(i).length() + 2)));
            if (i < row.size() - 1) {
                rowBuilder.append("|");
            }
        }
        return rowBuilder.toString();
    }

    private static String generateSeparator(int[] columnWidths) {
        StringBuilder separatorBuilder = new StringBuilder();
        for (int width : columnWidths) {
            separatorBuilder.append("-".repeat(width + 4));
            separatorBuilder.append("+");
        }
        // 去掉最後一個多餘的 "+"
        separatorBuilder.setLength(separatorBuilder.length() - 1);
        return separatorBuilder.toString();
    }

}
