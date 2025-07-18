package com.koreait.studysystem.mapper;

import com.koreait.studysystem.entity.StudyBoard;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StudyBoardMapper {
    List<StudyBoard> findByCreatorId(Long creatorId);       // 내가 만든 스터디
    List<StudyBoard> findJoinedStudiesByUserId(Long userId); // 내가 신청한 스터디
    void insertStudy(StudyBoard board);
    StudyBoard findById(Long id);
    int countParticipants(Long boardId);
    boolean isAlreadyJoined(@Param("boardId") Long boardId, @Param("userId") Long userId);
    void joinStudy(@Param("boardId") Long boardId, @Param("userId") Long userId);
    List<StudyBoard> findPaged(@Param("offset") int offset, @Param("limit") int limit);
    int countAll();
    List<StudyBoard> searchStudies(@Param("keyword") String keyword, @Param("offset") int offset, @Param("limit") int limit);
    int countSearchResults(@Param("keyword") String keyword);

}