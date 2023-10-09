package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entities.Credential;
import com.udacity.jwdnd.course1.cloudstorage.entities.Notes;
import com.udacity.jwdnd.course1.cloudstorage.entities.Users;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NotesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    public static final String PATH_TO_SAVE = "C:/FolderUploads/";
    @Autowired
    private UserService userService;

    @Autowired
    private EncryptionService encryptionService;

    @Autowired
    private CredentialMapper credentialMapper;

    // upload new file
    // comment out by ThanhTLN
    public int saveCredential(Credential credential) throws IOException {
        // get info user login
        Users userLogin = userService.getUserInformation();

        // add (credentialid url username key password userid) to model
        Credential newCredential = new Credential();
        newCredential.setCredentialid(credential.getCredentialid());
        newCredential.setUrl(credential.getUrl());
        newCredential.setUsername(credential.getUsername());
        newCredential.setUserid(userLogin.getUserId());

        // encrypt before save
        SecureRandom random = new SecureRandom();
        // declare new key
        byte[] key = new byte[16];
        // random kye
        random.nextBytes(key);

        // comment out by ThanhTLN
        String encodedKey = Base64.getEncoder().encodeToString(key);

        // encrypted pass
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);

        newCredential.setKey(encodedKey);
        newCredential.setPassword(encryptedPassword);

        if(newCredential.getCredentialid() == null) {
            // save model into database
            return credentialMapper.createNewCredential(newCredential);
        } else {
            // update Credential
            return credentialMapper.updateCredential(newCredential);
        }
    }

    // comment out by ThanhTLN
    public List<Credential> getAllCredential(Integer userId) {
        List<Credential> credentialList = credentialMapper.getCredentialList(userId);
        for (Credential credential : credentialList) {
            // decrypt pass to display
            String decryptedPassword = encryptionService.decryptValue(credential.getPassword(), credential.getKey());
            credential.setDecryptedPassword(decryptedPassword);
        }

        return credentialList;
    }
}
