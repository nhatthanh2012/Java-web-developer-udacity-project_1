package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.entities.Credential;
import com.udacity.jwdnd.course1.cloudstorage.entities.FileUploads;
import com.udacity.jwdnd.course1.cloudstorage.entities.Notes;
import com.udacity.jwdnd.course1.cloudstorage.entities.Users;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileUploadMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomePageController {
    @Autowired
    private FileUploadMapper fileUploadMapper;

    @Autowired
    private NotesMapper notesMapper;

    @Autowired
    private CredentialMapper credentialMapper;

    @Autowired
    UserService userService;

    @Autowired
    CredentialService credentialService;

    @GetMapping(value = {"/", "/home"})
    // comment out by ThanhTLN
    public String getToHomePage(Model model) {
        // get user info
        Users userLogin = userService.getUserInformation();
        // get file list
        List<FileUploads> fileUploads = fileUploadMapper.getFileUpload(userLogin.getUserId());
        // get note list
        List<Notes> notesList = notesMapper.getNotesList(userLogin.getUserId());
        // get Credential list
        List<Credential> credentialList = credentialService.getAllCredential(userLogin.getUserId());

        model.addAttribute("fileUploads", fileUploads);
        model.addAttribute("notesList", notesList);
        model.addAttribute("credentialList", credentialList);

        return "home"; 
    }
}
