package com.koreait.studysystem.mapper;

import com.koreait.studysystem.entity.StudyUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudyUserMapper {
    void insertUser(StudyUser user);
    StudyUser findByUsername(String username);
    StudyUser findById(Long id);
}