package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class NoteService {
    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    @PostConstruct
    public void postConstruct(){System.out.println("Creating NoteService bean");}

    public void addNote(Note note){
        Note cur = noteMapper.getNoteByNoteId(note.getNoteId());
        if(cur != null) {
            cur.setNoteTitle(note.getNoteTitle());
            cur.setNoteDescription(note.getNoteDescription());
            noteMapper.updateNote(cur);
        } else{
            noteMapper.insertNote(note);
        }
    }

    public List<Note> getAllNotesByUserId(Integer userId){
        return this.noteMapper.getNoteByUserId(userId);
    }

    public Note getNoteByNoteId(Integer noteId) {
        return this.noteMapper.getNoteByNoteId(noteId);
    }

    public List<Note> getAllNotes(){
        return this.noteMapper.getAllNotes();
    }

    public void deleteNote(Integer note){
        this.noteMapper.deleteNote(note);
    }
    public void editNote(Note note) {
        this.noteMapper.updateNote(note);
    }
}
