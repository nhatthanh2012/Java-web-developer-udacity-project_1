package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entities.FileUploads;
import com.udacity.jwdnd.course1.cloudstorage.entities.Users;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileUploadMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class FileService {
    public static final String PATH_TO_SAVE = "C:/FolderUploads/";
    @Autowired
    private UserService userService;
    @Autowired
    private FileUploadMapper fileUploadMapper;
    // upload new file
    // comment out by ThanhTLN
    public int saveNewFileToDb(MultipartFile file, String newFileName) throws IOException {
        // get info user login
        Users userLogin = userService.getUserInformation();

        String fileSize = String.valueOf(file.getSize());
        String fileName = newFileName;
        String contentType = file.getContentType();
        byte[] fileData = file.getBytes();

        // add data to model
        FileUploads fileUpload = new FileUploads();
        fileUpload.setFilename(fileName);
        fileUpload.setContenttype(contentType);
        fileUpload.setFilesize(fileSize);
        fileUpload.setFiledata(fileData);
        fileUpload.setUserid(userLogin.getUserId());

        // save model into database
        return fileUploadMapper.insert(fileUpload);
    }

    public void moveFileToPathSave(MultipartFile file, String newFileName) throws IOException  {
        String fileName = newFileName;
        // check folder exist
        File directory = new File(PATH_TO_SAVE);

        if (!directory.exists()) {
            // create directory if it doesn't exist
            directory.mkdirs();
        }
        // path to folder upload file C:
        String uploadDir = PATH_TO_SAVE + fileName;
        File fileToSave = new File(uploadDir);
        file.transferTo(fileToSave);
    }

    // remove file in directory
    // comment out by ThanhTLN
    public void removeFileInFolder(String deleteId) {
        String fileName = fileUploadMapper.getFileName(deleteId);
        // path to file to delete
        String filePath = "C:/FolderUploads/" + fileName;
        File fileToDelete = new File(filePath);

        // remove
        fileToDelete.delete();
    }
}
