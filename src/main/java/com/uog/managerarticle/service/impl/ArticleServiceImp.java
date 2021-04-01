package com.uog.managerarticle.service.impl;

import com.uog.managerarticle.entity.ArticleEntity;
import com.uog.managerarticle.entity.StudentEntity;
import com.uog.managerarticle.repository.ArticleRepository;
import com.uog.managerarticle.repository.StudentRepository;
import com.uog.managerarticle.service.IArticleService;
import com.uog.managerarticle.user.CustomUserDetail;
import com.uog.managerarticle.user.UserInfor;
import com.uog.managerarticle.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.*;

@Service
public class ArticleServiceImp implements IArticleService {

    private final ArticleRepository articleRepository;

    private final StudentRepository studentRepository;


    @Autowired
    public ArticleServiceImp(ArticleRepository articleRepository, StudentRepository studentRepository) {
        this.articleRepository = articleRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public List<ArticleEntity> findAll() {
        return articleRepository.findAll();
    }

    @Override
    @Transactional(rollbackFor = IOException.class)
    public ArticleEntity save(ArticleEntity entity, MultipartFile fileUpload, MultipartFile imageUpload) throws IOException {
        CustomUserDetail userDetail = UserInfor.getPrincipal();
        StudentEntity student = studentRepository.findByEmail(userDetail.getUsername());
        String nameArticle = StringUtils.cleanPath(fileUpload.getOriginalFilename());
        String thumbnail = StringUtils.cleanPath(imageUpload.getOriginalFilename());
        entity.setName(nameArticle);
        entity.setThumbnail(thumbnail);
        entity.setContent(fileUpload.getBytes());
        entity.setSize(fileUpload.getSize());
        entity.setStudent(student);
        entity.setStatus(-1);
        entity.setCreatedDate(new Date());
        ArticleEntity articleSaved = articleRepository.save(entity);

        FileUtils.saveFileUploaded(articleSaved.getId(), imageUpload);
        FileUtils.saveFileUploaded(articleSaved.getId(), fileUpload);
        return articleSaved;
    }

    @Override
    @Transactional(rollbackFor = IOException.class)
    public ArticleEntity update(ArticleEntity entity, MultipartFile fileUpload, MultipartFile imageUpload) throws Exception {
        Optional<ArticleEntity> oldEntity = articleRepository.findById(entity.getId());

        if(!oldEntity.isPresent()){
            throw new Exception("not found article");
        }
        ArticleEntity oldArticle = oldEntity.get();
        String dir = "./file-article/"+oldArticle.getId();
        entity.setStatus(oldArticle.getStatus());
        entity.setStudent(oldArticle.getStudent());
        entity.setTopic(oldArticle.getTopic());
        entity.setStatus(-1);
        entity.setComment(null);
        entity.setCreatedDate(new Date());

        if(fileUpload.getOriginalFilename().equals("")){
            entity.setContent(oldArticle.getContent());
            entity.setSize(oldArticle.getSize());
            entity.setName(oldArticle.getName());
        }else{
            String nameArticle = StringUtils.cleanPath(fileUpload.getOriginalFilename());
            entity.setName(nameArticle);
            entity.setContent(fileUpload.getBytes());
            entity.setSize(fileUpload.getSize());
            FileUtils.saveFileUploaded(oldArticle.getId(), fileUpload);
            FileUtils.deleteFile(dir +"/"+oldArticle.getName());
        }
        if (imageUpload.getOriginalFilename().equals("")){
            entity.setThumbnail(oldArticle.getThumbnail());
        }else{
            String thumbnail = StringUtils.cleanPath(imageUpload.getOriginalFilename());
            entity.setThumbnail(thumbnail);
            FileUtils.saveFileUploaded(oldArticle.getId(), imageUpload);
            FileUtils.deleteFile(dir +"/"+oldArticle.getThumbnail());
        }
        ArticleEntity articleSaved = articleRepository.save(entity);

        return articleSaved;
    }

    @Override
    public List<ArticleEntity> findAllForGuest(String facultyId) {
       return articleRepository.findAllByStudentFacultyIdAndStatus(facultyId,1);

    }

    @Override
    public List<ArticleEntity> findAllArticleWithoutCommentAfter14DayByFaculty(String id) throws ParseException {
        List<ArticleEntity> entities = new ArrayList<>();
        final SimpleDateFormat df = new SimpleDateFormat( "dd-MM-yyyy" );
        Calendar calendar = GregorianCalendar.getInstance();
        Date now = new Date();
        for(ArticleEntity entity:articleRepository.findAllByCommentAndStudentFacultyId(null,id)){
            calendar.setTime(entity.getCreatedDate());
            calendar.add(GregorianCalendar.DATE,14);
            Date date = df.parse(df.format(calendar.getTime()));
            if(date.before(now)){
                entities.add(entity);
            }
        }
        return entities;
    }

    @Override
    public List<ArticleEntity> findAllArticleWithoutCommentByFaculty(String id) {
        return articleRepository.findAllByCommentAndStudentFacultyId(null,id);
    }

    @Override
    public List<ArticleEntity> findAllAcceptedArticleByFaculty(String id) {
        return articleRepository.findAllByStudentFacultyIdAndStatus(id,1);
    }

    @Override
    public List<ArticleEntity> findAllRejectedArticleByFaculty(String id) {
        return articleRepository.findAllByStudentFacultyIdAndStatus(id,0);
    }


    @Override
    public ArticleEntity findById(Long id) throws Exception {
        Optional<ArticleEntity> entity =  articleRepository.findById(id);
        if(entity.isPresent()){
            return entity.get();
        }
        throw new Exception("Not Found Article");
    }

    @Override
    public void changeStatus(Long id, Integer status) throws Exception {
        Optional<ArticleEntity> entity = articleRepository.findById(id);
        if (entity.isPresent()){
            ArticleEntity articleEntity = entity.get();
            articleEntity.setStatus(status);
            articleRepository.save(articleEntity);
        }else{
            throw new Exception("Not found article");
        }

    }

    @Override
    public void delete(Long id) throws Exception {
        Optional<ArticleEntity> entity = articleRepository.findById(id);
        if(entity.isPresent()){
            ArticleEntity articleEntity = entity.get();
            String dir = "./file-article/"+articleEntity.getId();
            articleRepository.delete(articleEntity);
            FileUtils.deleteFile(dir);
            System.out.println(dir);

        }else{
            throw new Exception("not found Article");
        }
    }

    @Override
    public List<ArticleEntity> finByStudentIdAndStatus(String id,Integer status) {
        return articleRepository.findAllByStudentIdAndStatus(id, status);
    }

    @Override
    public List<ArticleEntity> findByFacultyAndTopicAndStatus(String faculty, String topicCode, Integer status) {

        return articleRepository.findAllByFacultyAndTopic(faculty,topicCode,status);
    }

    @Override
    public List<ArticleEntity> findAllByStudentIdAndTopicCode(String id, String code) {
       return articleRepository.findAllByStudentIdAndTopicCode(id,code);
    }

    @Override
    public List<ArticleEntity> findAllByStudentIdAndStatus(String id, Integer status) {
        return articleRepository.findAllByStudentIdAndStatus(id,status);
    }

    @Override
    public List<ArticleEntity> findAllByStatus(Integer status) {
        return articleRepository.findAllByStatus(status);
    }

    @Override
    public List<ArticleEntity> findAllByStudentId(String studentId) {
        return articleRepository.findAllByStudentId(studentId);
    }

    @Override
    public Long countAllByStatusAndStudentId(Integer status, String studentId) {
        return articleRepository.countAllByStatusAndStudentId(status, studentId);
    }

    @Override
    public ArticleEntity findByName(String name) {
        return articleRepository.findByName(name);
    }



    @Override
    public ArticleEntity findByIdAndStudentEmail(Long id, String email) {
        return articleRepository.findByIdAndStudentEmail(id,email);
    }

    @Override
    public ArticleEntity findByIdAndTopicName(Long id, String name) {
        return articleRepository.findByIdAndTopicName(id,name);
    }

    @Override
    public ArticleEntity saveComment(Long id, String comment) throws Exception {
        Optional<ArticleEntity> articleEntity = articleRepository.findById(id);
        if(articleEntity.isPresent()){
            ArticleEntity entity = articleEntity.get();
            entity.setComment(comment);
            return articleRepository.save(entity);
        }else{
           throw new Exception("Not Found article");
        }
    }
    





}
