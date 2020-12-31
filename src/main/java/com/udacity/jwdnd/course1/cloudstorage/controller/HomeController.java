package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/home")
public class HomeController {
    private UserService userService;
    private FileService fileService;
    private NoteService noteService;
    private CredentialService credentialService;
    private EncryptionService encryptionService;

    public HomeController(UserService userService, FileService fileService, NoteService noteService, CredentialService credentialService, EncryptionService encryptionService) {
        this.userService = userService;
        this.fileService = fileService;
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }

    @GetMapping
    public String getHomePage(Authentication authentication, Model model){
        User user = this.userService.getUser(authentication.getName());
        Integer userId = user.getUserid();
        model.addAttribute("notes", this.noteService.getAllNotesByUserId(userId));
        model.addAttribute("note", new Note());
        model.addAttribute("noteDelete", new Note());

        model.addAttribute("credentials", this.credentialService.getAllCredByUserId(userId));
        model.addAttribute("credential", new Credential());
        model.addAttribute("credentialDelete", new Credential());
        model.addAttribute("encryptionService", encryptionService);

        model.addAttribute("files", this.fileService.getAllFilesByUserId(userId));
        model.addAttribute("file", new File());
        model.addAttribute("fileDelete", new File());

        return "home";
    }




    
}
