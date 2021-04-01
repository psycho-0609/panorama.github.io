package com.uog.managerarticle.service.impl;

import com.uog.managerarticle.dto.FacultyDtoReport;
import com.uog.managerarticle.entity.ArticleEntity;
import com.uog.managerarticle.entity.FacultyEntity;
import com.uog.managerarticle.repository.ArticleRepository;
import com.uog.managerarticle.repository.FacultyRepository;
import com.uog.managerarticle.service.IFacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class FacultyServiceImp implements IFacultyService {

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public List<FacultyEntity> findAll() {

        return facultyRepository.findAll();
    }

    @Override
    public List<FacultyDtoReport> findAllForStatisticsReportByFaculty() {
        List<FacultyEntity> entities = facultyRepository.findAll();
        List<FacultyDtoReport> faculties = new ArrayList<>();
        Long totalArticle = articleRepository.count();
        for (FacultyEntity entity:entities){
            Long totalArticleOfFal = articleRepository.countAllByStudentFacultyId(entity.getId());
            FacultyDtoReport faculty = new FacultyDtoReport();
            faculty.setId(entity.getId());
            faculty.setName(entity.getName());
            faculty.setTotalArticle(totalArticleOfFal);
            if(totalArticle != 0) {
                faculty.setPercent((float) (totalArticleOfFal*100/totalArticle));
            }
            else {
                faculty.setPercent((float) 0);
            }
            faculty.setCoordinatorEntity(entity.getMarketingCoordinators());
            faculty.setAcceptedArticle(articleRepository.countAllByStudentFacultyIdAndStatus(entity.getId(),1));
            faculty.setRejectedArticle(articleRepository.countAllByStudentFacultyIdAndStatus(entity.getId(), 0));
            faculties.add(faculty);
        }
        return faculties;
    }

    @Override
    public List<FacultyDtoReport> findAllForExceptionReport() throws ParseException {
        List<FacultyEntity> entities = facultyRepository.findAll();
        List<FacultyDtoReport> faculties = new ArrayList<>();
        for (FacultyEntity entity:entities){
            Long totalArticleOfFal = articleRepository.countAllByStudentFacultyId(entity.getId());
            FacultyDtoReport faculty = new FacultyDtoReport();
            faculty.setId(entity.getId());
            faculty.setName(entity.getName());
            faculty.setTotalArticle(totalArticleOfFal);
            faculty.setCoordinatorEntity(entity.getMarketingCoordinators());
            faculty.setTotalArticleNoComment(articleRepository.countAllByStudentFacultyIdAndComment(entity.getId(),null));
            faculty.setTotalArNoComAf14(getArticleWithoutCommentAfter14DayByFaculty(entity.getId()));
            faculties.add(faculty);
        }
        return faculties;
    }

    @Override
    public FacultyEntity findById(String id) throws Exception {
        Optional<FacultyEntity> entity = facultyRepository.findById(id);
        if(!entity.isPresent()){
            throw new Exception("Not Found Faculty");
        }
        return entity.get();
    }

    @Override
    public FacultyEntity save(FacultyEntity entity) {
        return facultyRepository.save(entity);
    }

    @Override
    public void update(FacultyEntity entity) throws Exception {
        Optional<FacultyEntity> facultyEntity = facultyRepository.findById(entity.getId());
        if(!facultyEntity.isPresent()){
            throw new Exception("Not Found");
        }
        facultyRepository.save(entity);
    }

    @Override
    public void delete(String id) throws Exception {
        Optional<FacultyEntity> entity = facultyRepository.findById(id);
        if(!entity.isPresent()){
            throw new Exception("Not Found Faculty");
        }
        facultyRepository.delete(entity.get());
    }

    private int getArticleWithoutCommentAfter14DayByFaculty(String id) throws ParseException {
        List<ArticleEntity> entities = new ArrayList<>();
        final SimpleDateFormat df = new SimpleDateFormat( "dd-MM-yyyy" );
        Calendar calendar = GregorianCalendar.getInstance();
        Date now = new Date();
        for(ArticleEntity entity:articleRepository.findAllByCommentAndStudentFacultyId(null, id)){
            calendar.setTime(entity.getCreatedDate());
            calendar.add(GregorianCalendar.DATE,14);
            Date date = df.parse(df.format(calendar.getTime()));
            if(date.before(now)){
                entities.add(entity);
            }
        }
        return entities.size();
    }
}
