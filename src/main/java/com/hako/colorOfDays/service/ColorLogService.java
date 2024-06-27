package com.hako.colorOfDays.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.logging.ConditionEvaluationReportLoggingListener;
import org.springframework.stereotype.Service;

import com.hako.colorOfDays.dto.ColorLogDto;
import com.hako.colorOfDays.model.ColorLogEntity;
import com.hako.colorOfDays.persistence.ColorLogRepository;
import com.hako.colorOfDays.persistence.ColorLogTableManager;

import lombok.extern.slf4j.Slf4j;
import java.util.Optional;
import java.util.List;

@Slf4j
@Service
public class ColorLogService {
    @Autowired
    private ColorLogRepository repository;
    @Autowired
    private ColorLogTableManager tableManager;

    public String testService() {
        return "hello world";
    }

    public List<ColorLogDto> create(final ColorLogEntity entity) {
        //TODO: entity 유효성 검사 할 것.
        tableManager.insertColorLog(entity);
        log.info("entity has saved");
        return tableManager.getColorLogByUserId(entity);
    }

    public List<ColorLogDto> update(final ColorLogEntity entity) {
        tableManager.updateColorLog(entity);
        log.info("entity has updated");
        return tableManager.getColorLogByUserId(entity);
    }

    public List<ColorLogDto> delete(final ColorLogEntity entity) {
        tableManager.deleteColorLog(entity);
        log.info("entity has deleted");
        return tableManager.getColorLogByUserId(entity);
    }
    
    public List<ColorLogDto> retrive(final ColorLogEntity entity) {
        return tableManager.getColorLogByUserId(entity);
    }

    public ColorLogDto retriveByDate(final ColorLogEntity entity) {
        return tableManager.getColorLogByDate(entity);
    }
}
