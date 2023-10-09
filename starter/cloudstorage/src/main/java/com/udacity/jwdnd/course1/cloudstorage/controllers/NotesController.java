package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.entities.Notes;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller

public class NotesController {
    @Autowired
    private NotesService notesService;

    @Autowired
    private NotesMapper notesMapper;

    @PostMapping("/create-new-note")
    // comment out by ThanhTLN
    public String createNewNote(@ModelAttribute Notes notes, Model model) throws IOException {
        try {
            // submit new notes
            int newNotes = notesService.saveNote(notes);
            if(newNotes == 1) {
                if(notes.getNoteid() != null) {
                    model.addAttribute("successMessage", "Update note successfully");
                } else {
                    model.addAttribute("successMessage", "Create new note successfully");
                }
            } else {
                model.addAttribute("errorMessage", "Error when saving note");
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
        }
        return "result";// redirect to result page
    }

    // delete-note
    // comment out by ThanhTLN
    @PostMapping("/delete-note")
    public String deleteNote(@RequestParam String deleteId, Model model) {
        try {
            if(!deleteId.trim().isEmpty()) {
                notesMapper.deleteNote(deleteId);
                model.addAttribute("successMessage", "Your note was deleted successfully");
            } else {
                model.addAttribute("errorMessage", "Error deleting this note");
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
        }
        return "result";
    }
}
