package com.koreait.studysystem.service;

import com.koreait.studysystem.entity.StudyUser;
import com.koreait.studysystem.mapper.StudyUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudyInfoService implements UserDetailsService {
    private final StudyUserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) {
        StudyUser user = userMapper.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("사용자 없음");
        }

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles("USER")  // 권한 설정
                .build();
    }
}
