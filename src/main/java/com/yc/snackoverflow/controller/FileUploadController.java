package com.yc.snackoverflow.controller;

import com.yc.snackoverflow.handler.ResultData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/upload")
@CrossOrigin("http://localhost:5173")
@Tag(name = "File Upload", description = "API endpoint for file uploads")
public class FileUploadController {

    @Value("${app.upload.dir:${user.home}/snackoverflow/uploads}")
    private String uploadDir;

    /**
     * Upload a file
     */
    @Operation(summary = "Upload file", description = "Upload a file to the server")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "File uploaded successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid file or empty file"),
            @ApiResponse(responseCode = "500", description = "Server error during upload")
    })
    @PostMapping
    public ResultData<String> uploadFile(
            @Parameter(description = "File to upload", required = true)
            @RequestParam("file") MultipartFile file) {
        
        if (file.isEmpty()) {
            return ResultData.fail("Please select a file to upload");
        }

        try {
            // 創建上傳路徑（使用配置的路徑而非硬編碼）
            Path uploadPath = Paths.get(uploadDir);

            // 如果目錄不存在則創建
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // 檢查文件類型（僅接受圖片和文檔）
            String contentType = file.getContentType();
            if (contentType == null || (!contentType.startsWith("image/") && 
                !contentType.equals("application/pdf") && 
                !contentType.equals("application/msword") && 
                !contentType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document") && 
                !contentType.equals("application/vnd.ms-excel") && 
                !contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))) {
                return ResultData.fail("Only images, PDFs, Word and Excel files are allowed");
            }

            // 檢查文件大小（最大10MB）
            if (file.getSize() > 10 * 1024 * 1024) {
                return ResultData.fail("File size exceeds maximum limit (10MB)");
            }

            // 獲取文件名並添加UUID前綴避免衝突
            String originalFilename = file.getOriginalFilename();
            String fileExtension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String uniqueFilename = UUID.randomUUID() + fileExtension;
            
            // 保存文件
            Path filePath = uploadPath.resolve(uniqueFilename);
            Files.copy(file.getInputStream(), filePath);
            
            log.info("File uploaded successfully: {}", uniqueFilename);
            return ResultData.success("File uploaded successfully", uniqueFilename);
            
        } catch (IOException e) {
            log.error("Failed to upload file", e);
            return ResultData.fail("Failed to upload file: " + e.getMessage());
        }
    }
}