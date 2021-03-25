package com.uog.managerarticle.repository;

import com.uog.managerarticle.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {

    List<ArticleEntity> findAllByStudentIdAndStatus(String id, Integer status);

    @Query(value = "SELECT new ArticleEntity(a.id, a.name, a.size, a.thumbnail,a.title, a.status, a.student, a.topic, a.createdDate, a.comment) from ArticleEntity as a "+
            "left join StudentEntity s on a.student.id = s.id  "+
            "where s.faculty.code =?1 and a.topic.code=?2 and a.status=?3")
    List<ArticleEntity> findAllByFacultyAndTopic(String facultyCode, String topicCode, Integer status);


    List<ArticleEntity> findAllByStatus(Integer status);

    List<ArticleEntity> findAllByStudentIdAndTopicCode(String id, String code);

    List<ArticleEntity> findAllByStudentId(String id);

    Long countAllByStatusAndStudentId(Integer status, String studentId);
    ArticleEntity findByName(String name);

    ArticleEntity findByIdAndStudentEmail(Long id, String email);

    ArticleEntity findByIdAndTopicName(Long id,String name);

    ArticleEntity findByStudent_FacultyName(String faculty);
    // for report
    Long countAllByTopicIdAndStudentFacultyIdAndStatus(Long topicId, String facultyId, Integer status);
    Long countAllByTopicIdAndStudentFacultyId(Long topicId, String facultyId);
    Long countAllByTopicIdAndStatus(Long id, Integer status);
    Long countAllByStatus(Integer status);
    Long countAllByTopicId(Long id);
    List<ArticleEntity> findAllByStudentFacultyIdAndStatus(String facultyId,Integer status);
    List<ArticleEntity> findAllByCommentAndStudentFacultyId(String comment, String id);
    Long countAllByStudentFacultyId(String id);
    Long countAllByStudentFacultyIdAndStatus(String id, Integer status);
    Long countAllByStudentFacultyIdAndComment(String id, String comment);


    List<ArticleEntity> findAllByStudentIdAndTopicCodeAndStatus(String id, String code, Integer status);
}
