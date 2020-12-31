package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CredentialController {

    private CredentialService credentialService;
    private UserService userService;

    public CredentialController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @PostMapping("/credential/add")
    public ModelAndView addCredential(Authentication authentication, Credential credential, Model model){
        credential.setUserId(this.userService.getUser(authentication.getName()).getUserid());
        try{
            credentialService.addCredential(credential);

            model.addAttribute("success", true);
            model.addAttribute("message", "New credential added!");
        } catch (Exception e){
            model.addAttribute("error", true);
            model.addAttribute("message", "Cannot add credential." + e.getMessage());
        }
        return new ModelAndView("result");
    }

    @GetMapping("/credential/delete/{credentialId}")
    public ModelAndView deleteCredential(@PathVariable Integer credentialId, Model model) {
        try{
            credentialService.deleteCredential(credentialId);
            System.out.println("deleting cred id:" + credentialId);

            model.addAttribute("success", true);
            model.addAttribute("message", "Credential deleted!");
        } catch (Exception e){
            model.addAttribute("error", true);
            model.addAttribute("message", "Cannot delete credential." +e.getMessage());
        }
        return new ModelAndView("result");
    }
}
