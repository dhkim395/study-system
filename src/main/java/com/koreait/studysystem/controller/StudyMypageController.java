package com.koreait.studysystem.controller;

import com.koreait.studysystem.entity.StudyBoard;
import com.koreait.studysystem.entity.StudyUser;
import com.koreait.studysystem.mapper.StudyUserMapper;
import com.koreait.studysystem.service.StudyBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class StudyMypageController {private final StudyBoardService boardService;
    private final StudyUserMapper userMapper;

    @GetMapping("/mypage")
    public String mypage(Model model, Principal principal) {
        StudyUser loginUser = userMapper.findByUsername(principal.getName());

        List<StudyBoard> created = boardService.getCreatedStudies(loginUser.getId());
        List<StudyBoard> joined = boardService.getJoinedStudies(loginUser.getId());

        model.addAttribute("user", loginUser);
        model.addAttribute("createdStudies", created);
        model.addAttribute("joinedStudies", joined);

        return "mypage";
    }
}
