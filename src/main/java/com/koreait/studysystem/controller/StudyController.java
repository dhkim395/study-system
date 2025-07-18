package com.koreait.studysystem.controller;

import com.koreait.studysystem.entity.StudyBoard;
import com.koreait.studysystem.entity.StudyUser;
import com.koreait.studysystem.mapper.StudyUserMapper;
import com.koreait.studysystem.service.StudyBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/study")
public class StudyController {

    private final StudyBoardService boardService;
    private final StudyUserMapper userMapper;

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("study", new StudyBoard());
        return "create-study";
    }

    @PostMapping("/create")
    public String createSubmit(@ModelAttribute StudyBoard study, Principal principal) {
        StudyUser user = userMapper.findByUsername(principal.getName());
        boardService.createStudy(study, user.getId());
        return "redirect:/study/list";
    }
    @PostMapping("/{id}/apply")
    public String applyStudy(@PathVariable Long id, Principal principal, Model model) {
        StudyUser user = userMapper.findByUsername(principal.getName());

        try {
            boardService.applyToStudy(id, user.getId());
            model.addAttribute("message", "신청 완료!");
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
        }

        return "redirect:/mypage";
    }
    @GetMapping("/list")
    public String listStudies(@RequestParam(defaultValue = "1") int page, Model model) {
        int pageSize = 10;

        List<StudyBoard> studies = boardService.getPagedStudies(page, pageSize);
        int totalPages = boardService.getTotalPages(pageSize);

        model.addAttribute("studies", studies);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "study-list";
    }
    @GetMapping("/search")
    public String searchStudies(@RequestParam String keyword,
                                @RequestParam(defaultValue = "1") int page,
                                Model model) {

        int pageSize = 10;
        List<StudyBoard> studies = boardService.search(keyword, page, pageSize);
        int totalPages = boardService.getSearchPageCount(keyword, pageSize);

        model.addAttribute("studies", studies);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("keyword", keyword);

        return "study-search";
    }

    @GetMapping("/{id}")
    public String studyDetail(@PathVariable Long id, Model model, Principal principal) {
        StudyBoard study = boardService.getById(id);
        model.addAttribute("study", study);

        boolean canApply = false;
        String applyMessage = "";

        if (principal != null) {
            StudyUser user = userMapper.findByUsername(principal.getName());

            boolean alreadyApplied = boardService.isAlreadyApplied(id, user.getId());
            int currentCount = boardService.getParticipantCount(id);

            if (alreadyApplied) {
                applyMessage = "이미 신청한 스터디입니다.";
            } else if (currentCount >= study.getMaxParticipants()) {
                applyMessage = "모집 정원이 초과되었습니다.";
            } else {
                canApply = true;
            }

            model.addAttribute("user", user);
        } else {
            applyMessage = "로그인 후 신청할 수 있습니다.";
        }

        model.addAttribute("canApply", canApply);
        model.addAttribute("applyMessage", applyMessage);

        return "study-detail";
    }

    @PostMapping("/{id}/apply")
    public String applyStudy(@PathVariable Long id, Principal principal, RedirectAttributes redirectAttributes) {
        StudyUser user = userMapper.findByUsername(principal.getName());

        try {
            boardService.applyToStudy(id, user.getId());
            redirectAttributes.addFlashAttribute("message", "신청 완료!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/study/" + id;
    }


}
