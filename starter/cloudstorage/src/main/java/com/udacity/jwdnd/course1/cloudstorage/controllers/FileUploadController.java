package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileUploadMapper;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;


@Controller
public class FileUploadController {
    private final String uploadDirectory = "C:/FolderUploads/"; // Thay đổi đường dẫn này đến thư mục lưu trữ tệp

    @Autowired
    private FileService fileService;

    @Autowired
    private FileUploadMapper fileUploadMapper;

    @PostMapping("/upload-file-on-system")
    public String uploadFileOnSystem(@RequestParam("fileUpload") MultipartFile file, Model model) {
        if (!file.isEmpty()) {
            try {
                // create new name file (rules: current time + original file name)
                String newFileName = System.currentTimeMillis() + "-" + file.getOriginalFilename();

                int newFileUpload = fileService.saveNewFileToDb(file, newFileName);
                if(newFileUpload == 1) {
                    fileService.moveFileToPathSave(file, newFileName);
                    model.addAttribute("successMessage", "File uploaded successfully.");
                } else {
                    model.addAttribute("errorMessage", "Error uploading the file.");
                }
            } catch (IOException e) {
                model.addAttribute("errorMessage", e.getMessage());
            }
        } else {
            model.addAttribute("errorMessage", "Please select a file to upload.");
        }

        // after upload file, come back to result.html page
        return "result";
    }

    // delete-file
    @PostMapping("/delete-file")
    // comment out by ThanhTLN
    public String deleteFileUpload(@RequestParam String deleteId, Model model) {
        try {
            if(!deleteId.trim().isEmpty()) {
                fileService.removeFileInFolder(deleteId);
                fileUploadMapper.deleteFile(deleteId);
                model.addAttribute("successMessage", "Your file was deleted successfully");
            } else {
                model.addAttribute("errorMessage", "Error deleting the file");
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
        }
        return "result";
    }


    // handle view file on browser
    @GetMapping("/viewFile/{filename:.+}")
    // comment out by ThanhTLN
    public ResponseEntity<Resource> viewFile(@PathVariable String filename) {
        try {
            Path filePath = Paths.get(uploadDirectory).resolve(filename);
            Resource resource = new UrlResource(filePath.toUri());
            // comment out by ThanhTLN
            String contentType = fileUploadMapper.getContenttypeByFileName(filename);

            if (resource.exists() && resource.isReadable()) {
                // return to view file
                return ResponseEntity.ok()
                        .contentType(MediaType.valueOf(contentType)) // change by file tylpe
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                // handle if file exist
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            // handle error if occur
            return ResponseEntity.status(500).build();
        }
    }
}
