package com.mediscreen.assessment.model;

public class NoteModel {

    private String id;

    private Integer patientId;

    private String noteDate;

    private String doctorNote;

    public NoteModel(String id, Integer patientId, String noteDate, String doctorNote) {
        this.id = id;
        this.patientId = patientId;
        this.noteDate = noteDate;
        this.doctorNote = doctorNote;
    }

    public String getDoctorNote() {
        return doctorNote;
    }

}
