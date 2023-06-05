package com.mediscreen.clientui.beans;

import java.time.LocalDate;

public class NoteBean {

    private String id;

    private Integer patientId;

    private LocalDate noteDate;

    private String doctorNote;

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

    public LocalDate getNoteDate() {
        return noteDate;
    }

    public void setNoteDate(LocalDate noteDate) {
        this.noteDate = noteDate;
    }

    public String getDoctorNote() {
        return doctorNote;
    }

    public void setDoctorNote(String doctorNote) {
        this.doctorNote = doctorNote;
    }

    @Override
    public String toString() {
        return "NoteBean{" +
                "id='" + id + '\'' +
                ", patientId=" + patientId +
                ", noteDate=" + noteDate +
                ", doctorNote='" + doctorNote + '\'' +
                '}';
    }
}
