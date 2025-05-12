package com.yc.snackoverflow.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("whisper")
@CrossOrigin("http://localhost:5173")
public class WhisperController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)

    public String whisper(@RequestParam String path) {
        log.info("whisper : [{}]", path);
        String response = executeWhisper(path);
        return response;
    }

    public static String executeWhisper(String path) {
        String encodedPath = path.replace("/", "\\");

        // 創建命令字串
//        path = "C:" + File.separator + "Users" + File.separator + "yanchen" + File.separator + "Downloads" + File.separator + "_32210967.m4a";
        String[] command = {"whisper", path, "--model", "medium", "--language", "Chinese"};
        StringBuilder output = new StringBuilder();

        try {
            // 創建進程建造者
            ProcessBuilder processBuilder = new ProcessBuilder(command);


            // 设置环境变量
            Map<String, String> environment = processBuilder.environment();
            environment.put("PYTHONIOENCODING", "utf-8");

            // 啟動進程
            Process process = processBuilder.start();

            // 獲取進程輸出流
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));
            String line;

            // 讀取命令執行的輸出
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            // 等待命令執行完畢
            int exitCode = process.waitFor();
//            output.append("命令執行完畢，退出碼: ").append(exitCode);

        } catch (IOException | InterruptedException e) {
            return e.getMessage() + "\n" + output;
        }
        output.toString()
                .replace("，", ",")        .replace("\\u", "\\u4e00")
                .replace(" ", "");
        return output.toString();
    }
}
