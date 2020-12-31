package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class CredentialService {
    private CredentialMapper credentialMapper;
    private EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    @PostConstruct
    public void postConstruct(){System.out.println("Creating a Credential service bean");}

    public void addCredential(Credential credential){
        System.out.println("current credential: " + credential.getCredentialId());
        if(credential.getCredentialId() == null) {
            System.out.println("creating new credential..");
            String key = this.encryptionService.generateKey();
            credential.setKey(key);
            credential.setPassword(this.encryptionService.encryptValue(credential.getPassword(), key));
            credentialMapper.insertCredential(credential);
        } else {
            System.out.println("Updating current credential id: " + credential.getCredentialId());
            credential.setUrl(credential.getUrl());
            credential.setUsername(credential.getUsername());
            String key = this.encryptionService.generateKey();
            credential.setKey(key);
            String encodedPassword = this.encryptionService.encryptValue(credential.getPassword(), key);
            credential.setPassword(encodedPassword);
            credentialMapper.updateCredential(credential);
        }
    }

    public List<Credential> getAllCredByUserId(Integer userId){
        return this.credentialMapper.getCredByUserId(userId);
    }


    public void deleteCredential(Integer credId){
        this.credentialMapper.deleteCredential(credId);
    }
}
