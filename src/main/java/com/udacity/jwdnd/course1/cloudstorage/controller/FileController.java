package com.udacity.jwdnd.course1.cloudstorage.controller;

import java.io.IOException;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


@RestController
public class FileController{
    private UserService userService;
    private FileService fileService;
    private FileMapper fileMapper;

    public FileController(UserService userService, FileService fileService, FileMapper fileMapper) {
        this.userService = userService;
        this.fileService = fileService;
        this.fileMapper = fileMapper;
    }

    @PostMapping("/file")
    public ModelAndView uploadFile(@RequestParam("fileUpload") MultipartFile fileUpload, Authentication authentication, Model model) throws IOException{
        if(fileUpload.isEmpty()){
            model.addAttribute("error", true);
            model.addAttribute("message", "No file selected to upload!");
            return new ModelAndView("result");
        }
        String filename = fileUpload.getOriginalFilename();
        if (fileMapper.getFileByFilename(userService.getUser(authentication.getName()).getUserid(), filename)
                != null){
            model.addAttribute("error", true);
            model.addAttribute("message", "Duplicate file name. Please rename the file and try again.");
            return new ModelAndView("result");
        }

        fileService.insertFile(authentication, fileUpload);
        model.addAttribute("success", true);
        model.addAttribute("message", "New file uploaded successfully!");
        return new ModelAndView("result");

    }

    @GetMapping("/file/delete/{fileid}")
    public ModelAndView deleteFile(@PathVariable(value = "fileid") Integer fileid, Model model) {
        try {
            fileService.deleteFile(fileid);
            model.addAttribute("success", true);
            model.addAttribute("message", "File deleted successfully!");
        } catch (Exception e) {
            model.addAttribute("error", true);
            model.addAttribute("message", "There was an error deleting this file. Please try again.");
        }

        return new ModelAndView("result");
    }

    @GetMapping("/file/view/{fileid}")
    public ResponseEntity<Resource> downloadFile(@PathVariable(value = "fileid") Integer fileid){
        File file = fileService.getFileById(fileid);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContenttype()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(new ByteArrayResource(file.getFiledata()));
    }
}
