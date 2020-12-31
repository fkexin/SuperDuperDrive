package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;


@Service
public class FileService {
    private FileMapper fileMapper;
    private UserService userService;

    public FileService(FileMapper fileMapper, UserService userService) {
        this.fileMapper = fileMapper;
        this.userService = userService;
    }

    @PostConstruct
    public void postConstruct(){System.out.println("Creating a File Service bean");}


    public void insertFile(Authentication authentication,MultipartFile fileUpload) {
        try {
            File file = new File();
            file.setUserid(userService.getUser(authentication.getName()).getUserid());
            file.setFilename(fileUpload.getOriginalFilename());
            file.setFilesize(fileUpload.getSize());
            file.setContenttype(fileUpload.getContentType());
            file.setFiledata(fileUpload.getBytes());
            fileMapper.insertFile(file);
            System.out.println("New file uploaded. File id:" + file.getFileid());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<File> getAllFilesByUserId(Integer userid){return fileMapper.getAllFilesByUserId(userid);}
    public File getFileById(Integer fileid){return fileMapper.getFileById(fileid);}

    public void deleteFile(Integer fileid) {
        fileMapper.deleteFile(fileid);
    }


}
