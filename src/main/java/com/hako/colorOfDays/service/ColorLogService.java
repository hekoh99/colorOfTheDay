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
import java.util.ArrayList;
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
    
    public List<String> getTable() {
        List<String> ret = tableManager.getExistingTable();
        return ret;
    }

    public ColorLogDto create(final ColorLogEntity entity) {
        //TODO: entity 유효성 검사 할 것.
        tableManager.insertColorLog(entity);
        log.info("entity has saved");
        return tableManager.getColorLogByDate(entity);
    }

    public ColorLogDto update(final ColorLogEntity entity) {
        tableManager.updateColorLog(entity);
        log.info("entity has updated");
        return tableManager.getColorLogByDate(entity);
    }

    public List<ColorLogDto> delete(final ColorLogEntity entity) {
        tableManager.deleteColorLog(entity);
        log.info("entity has deleted");
        return tableManager.getColorLogByUserId(entity);
    }
    
    public List<ColorLogDto> retrive(final ColorLogEntity entity) {
        if (entity.getMonth() == null || entity.getYear() == null) {
            List<String> tables = getTable();
            if (tables.size() == 0) {
                List<ColorLogDto> result = new ArrayList<>(0);      // 비어있는 리스트 반환
                return result;
            }
            if (tables.size() == 1)
                return tableManager.getColorLogByUserId(tables.get(0), entity.getUserId());     // 가장 오래된 log table의 값을 전달
            else {
                List<ColorLogDto> list1 = tableManager.getColorLogByUserId(tables.get(0), entity.getUserId());
                List<ColorLogDto> list2 = tableManager.getColorLogByUserId(tables.get(1), entity.getUserId());
                List<ColorLogDto> joined = new ArrayList<>();
                joined.addAll(list1);
                joined.addAll(list2);
                
                return joined;
            }
        }
        return tableManager.getColorLogByUserId(entity);
    }

    public ColorLogDto retriveByDate(final ColorLogEntity entity) {
        return tableManager.getColorLogByDate(entity);
    }

    public List<ColorLogDto> retriveMonth(final ColorLogEntity entity) {
        return tableManager.getColorLogMonth(entity);
    }
}
