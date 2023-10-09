package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entities.Notes;
import com.udacity.jwdnd.course1.cloudstorage.entities.Users;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NotesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.IOException;

@Service
public class NotesService {
    public static final String PATH_TO_SAVE = "C:/FolderUploads/";
    @Autowired
    private UserService userService;
    @Autowired
    private NotesMapper notesMapper;
    // upload new file
    // comment out by ThanhTLN
    public int saveNote(Notes notes) throws IOException {

        // get info user login
        Users userLogin = userService.getUserInformation();

        // add (noteid, notetitle, notedescription, userid) to model
        Notes newNote = new Notes();
        newNote.setNoteid(notes.getNoteid());
        newNote.setNotetitle(notes.getNotetitle());
        newNote.setNotedescription(notes.getNotedescription());
        newNote.setUserid(userLogin.getUserId());

        if(notes.getNoteid() == null) {
            // save model into database
            return notesMapper.createNewNote(newNote);
        } else {
            // update note
            return notesMapper.updateNote(newNote);
        }
    }
}
