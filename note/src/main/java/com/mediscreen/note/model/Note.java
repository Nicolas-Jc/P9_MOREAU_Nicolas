package com.mediscreen.note.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Document(collection = "note")
public class Note {
    @Id
    private String id;

    @NotNull
    private Integer patientId;

    @NotNull
    private String noteDate;

    @NotBlank(message = "Note content is mandatory")
    private String doctorNote;

    public Note(Integer patientId, String noteDate, String doctorNote) {
        this.patientId = patientId;
        this.noteDate = noteDate;
        this.doctorNote = doctorNote;
    }

    public Note(String id, Integer patientId, String noteDate, String doctorNote) {
        this.id = id;
        this.patientId = patientId;
        this.noteDate = noteDate;
        this.doctorNote = doctorNote;
    }

    public Note() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public String getNoteDate() {
        return noteDate;
    }

    public void setNoteDate(String noteDate) {
        this.noteDate = noteDate;
    }

    public String getDoctorNote() {
        return doctorNote;
    }

    public void setDoctorNote(String doctorNote) {
        this.doctorNote = doctorNote;
    }


}
