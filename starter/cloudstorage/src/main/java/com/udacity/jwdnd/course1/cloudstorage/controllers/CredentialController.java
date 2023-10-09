package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.entities.Credential;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller

public class CredentialController {
    @Autowired
    private CredentialService credentialService;

    @Autowired
    private CredentialMapper credentialMapper;

    @PostMapping("/create-new-credential")
    // comment out by ThanhTLN
    public String createNewCredential(@ModelAttribute Credential credential, Model model) throws IOException {
        try {
            // submit new credential
            int newcredential = credentialService.saveCredential(credential);
            if(newcredential == 1) {
                if(credential.getCredentialid() != null) {
                    model.addAttribute("successMessage", "Update credential successfully");
                } else {
                    model.addAttribute("successMessage", "Create new credential successfully");
                }
            } else {
                model.addAttribute("errorMessage", "Error when saving credential");
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
        }
        return "result"; // redirect to result page
    }

    // delete-note
    // comment out by ThanhTLN
    @PostMapping("/delete-credential")
    public String deleteCredential(@RequestParam String deleteId, Model model) {
        try {
            if(!deleteId.trim().isEmpty()) {
                credentialMapper.deleteCredential(deleteId);
                model.addAttribute("successMessage", "Your credential was deleted successfully");
            } else {
                model.addAttribute("errorMessage", "Error deleting this credential");
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
        }
        return "result";
    }
}
