package com.uog.managerarticle.service;

import com.uog.managerarticle.dto.FacultyDtoReport;
import com.uog.managerarticle.entity.FacultyEntity;

import java.text.ParseException;
import java.util.List;

public interface IFacultyService {

    List<FacultyEntity> findAll();

    List<FacultyDtoReport> findAllForStatisticsReportByFaculty();
    List<FacultyDtoReport> findAllForExceptionReport() throws ParseException;
    FacultyEntity findById(String id) throws Exception;
    FacultyEntity save(FacultyEntity entity);
    void update(FacultyEntity entity) throws Exception;
    void delete(String id) throws Exception;
}
