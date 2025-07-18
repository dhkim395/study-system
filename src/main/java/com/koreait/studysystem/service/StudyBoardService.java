package com.koreait.studysystem.service;

import com.koreait.studysystem.entity.StudyBoard;
import com.koreait.studysystem.mapper.StudyBoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudyBoardService {

    private final StudyBoardMapper studyBoardMapper;

    public List<StudyBoard> getCreatedStudies(Long userId) {
        return studyBoardMapper.findByCreatorId(userId);
    }

    public List<StudyBoard> getJoinedStudies(Long userId) {
        return studyBoardMapper.findJoinedStudiesByUserId(userId);
    }
    public void createStudy(StudyBoard board, Long userId) {
        board.setCreatedBy(userId);
        studyBoardMapper.insertStudy(board);
    }
    public void applyToStudy(Long boardId, Long userId) {
        StudyBoard board = studyBoardMapper.findById(boardId);
        if (board == null) throw new RuntimeException("스터디 없음");

        if (studyBoardMapper.isAlreadyJoined(boardId, userId)) {
            throw new RuntimeException("이미 신청했습니다.");
        }

        int currentCount = studyBoardMapper.countParticipants(boardId);
        if (currentCount >= board.getMaxParticipants()) {
            throw new RuntimeException("인원이 가득 찼습니다.");
        }

        studyBoardMapper.joinStudy(boardId, userId);
    }

    public StudyBoard getById(Long id) {
        return studyBoardMapper.findById(id);
    }
    public List<StudyBoard> getPagedStudies(int page, int size) {
        int offset = (page - 1) * size;
        return studyBoardMapper.findPaged(offset, size);
    }

    public int getTotalPages(int pageSize) {
        int totalCount = studyBoardMapper.countAll();
        return (int) Math.ceil((double) totalCount / pageSize);
    }
    public List<StudyBoard> search(String keyword, int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        return studyBoardMapper.searchStudies(keyword, offset, pageSize);
    }

    public int getSearchPageCount(String keyword, int pageSize) {
        int total = studyBoardMapper.countSearchResults(keyword);
        return (int) Math.ceil((double) total / pageSize);
    }
    public boolean isAlreadyApplied(Long boardId, Long userId) {
        return studyBoardMapper.isAlreadyJoined(boardId, userId);
    }

    public int getParticipantCount(Long boardId) {
        return studyBoardMapper.countParticipants(boardId);
    }

}