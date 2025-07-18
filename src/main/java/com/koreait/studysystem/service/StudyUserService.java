package com.koreait.studysystem.service;


import com.koreait.studysystem.entity.StudyUser;
import com.koreait.studysystem.mapper.StudyUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudyUserService {

    private final StudyUserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public void register(StudyUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userMapper.insertUser(user);
    }

    public StudyUser findByUsername(String username) {
        return userMapper.findByUsername(username);
    }
}