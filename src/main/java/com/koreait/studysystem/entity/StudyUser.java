package com.koreait.studysystem.entity;

import lombok.Data;
import java.util.List;

@Data
public class StudyUser {
    private Long id;
    private String username;
    private String password;

    // 내가 만든 스터디
    private List<StudyBoard> createdStudies;

    // 내가 신청한 스터디 (중간 테이블로 매핑)
    private List<StudyBoard> joinedStudies;
}