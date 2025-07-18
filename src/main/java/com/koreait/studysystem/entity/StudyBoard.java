package com.koreait.studysystem.entity;

import lombok.Data;

@Data
public class StudyBoard {
    private Long id;
    private String title;
    private String description;
    private int maxParticipants;
    private String deadline;
    private Long createdBy;
}
