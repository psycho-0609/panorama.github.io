package com.uog.managerarticle.service;

import com.uog.managerarticle.entity.ArticleEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;


public interface IArticleService {

    List<ArticleEntity> findAll();
    ArticleEntity save(ArticleEntity entity, MultipartFile fileUpload, MultipartFile imageUpload)   throws IOException;
    ArticleEntity findById(Long id) throws Exception;
    void changeStatus(Long id, Integer status) throws Exception;
    void delete(Long id) throws Exception;
    List<ArticleEntity> finByStudentIdAndStatus(String id, Integer status);
    List<ArticleEntity> findByFacultyAndTopicAndStatus(String faculty, String topicCode, Integer status);
    List<ArticleEntity> findAllByStudentIdAndTopicCode(String id, String code);

    List<ArticleEntity> findAllByStudentIdAndStatus(String id, Integer status);

    List<ArticleEntity> findAllByStatus(Integer status);
    List<ArticleEntity> findAllByStudentId(String studentId);
    Long countAllByStatusAndStudentId(Integer status, String studentId);
    ArticleEntity findByName(String name);
    ArticleEntity findByIdAndStudentEmail(Long id, String email);
    ArticleEntity findByIdAndTopicName(Long id,String name);
    ArticleEntity saveComment(Long id, String comment) throws Exception;
    ArticleEntity update(ArticleEntity entity, MultipartFile fileUpload, MultipartFile imageUpload) throws Exception;
    List<ArticleEntity> findAllForGuest(String facultyId);
    List<ArticleEntity> findAllArticleWithoutCommentAfter14DayByFaculty(String id) throws ParseException;
    List<ArticleEntity> findAllArticleWithoutCommentByFaculty(String id);
    List<ArticleEntity> findAllAcceptedArticleByFaculty(String id);
    List<ArticleEntity> findAllRejectedArticleByFaculty(String id);


}
