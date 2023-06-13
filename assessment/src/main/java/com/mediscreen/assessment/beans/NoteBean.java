package com.mediscreen.assessment.beans;

import java.time.LocalDate;

public class NoteBean {

    private String id;

    private Integer patientId;

    private String noteDate;

    private String doctorNote;

    public NoteBean(String id, Integer patientId, String noteDate, String doctorNote) {
        this.id = id;
        this.patientId = patientId;
        this.noteDate = noteDate;
        this.doctorNote = doctorNote;
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
