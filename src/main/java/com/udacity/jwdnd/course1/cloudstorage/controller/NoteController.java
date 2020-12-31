package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NoteController {
    private NoteService noteService;
    private UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping("/note/add")
    public ModelAndView addNote(Authentication authentication, Note note, Model model){
        note.setUserId(this.userService.getUser(authentication.getName()).getUserid());
        try{
            noteService.addNote(note);
            model.addAttribute("success", true);
            model.addAttribute("message", "New note added!");
        } catch (Exception e){
            model.addAttribute("error", true);
            model.addAttribute("message", "There was an error saving this note. Please try again." +e.getMessage());
        }
        return new ModelAndView("result");
    }

    @GetMapping("/note/delete/{noteId}")
    public ModelAndView deleteNote(@PathVariable Integer noteId, Model model) {
//        noteService.deleteNote(noteId);
//        return "redirect:/home";
        try{
            noteService.deleteNote(noteId);
            System.out.println("New note created. note id:" + noteId);

            model.addAttribute("success", true);
            model.addAttribute("message", "Note deleted!");
        } catch (Exception e){
            model.addAttribute("error", true);
            model.addAttribute("message", "There was an error deleting this note. Please try again." +e.getMessage());
        }
        return new ModelAndView("result");
    }
}
