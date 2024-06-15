package com.hako.colorOfDays.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hako.colorOfDays.model.ColorLogEntity;
import com.hako.colorOfDays.persistence.ColorLogRepository;

import lombok.extern.slf4j.Slf4j;
import java.util.Optional;
import java.util.List;

@Slf4j
@Service
public class ColorLogService {
    @Autowired
    private ColorLogRepository repository;

    private boolean isValidEntity (final ColorLogEntity entity) {
        if (entity == null) {
            log.warn("The entity is null");
            return false;
        }

        if (entity.getUserId() == null) {
            log.warn("The userId is null");
            return false;
        }
        return true;
    }

    public String testService() {
        ColorLogEntity entity = ColorLogEntity.builder().text("my test item").build();
        // if (!isValidEntity(entity)) {
        //     log.warn("The entity is not valid");
        //     throw new RuntimeException("The entity is not valid");
        // }
        repository.save(entity);
        ColorLogEntity savedEntity = repository.findById(entity.getId()).get();
        return savedEntity.getText();
    }

    public ColorLogEntity create(final ColorLogEntity entity) {
        if (!isValidEntity(entity)) {
            log.warn("The entity is not valid");
            throw new RuntimeException("The entity is not valid");
        }

        repository.save(entity);

        log.info("Entity has saved");

        final Optional<ColorLogEntity> savedEntity = repository.findById(entity.getId());
        ColorLogEntity ret = savedEntity.orElse(entity);    // TODO: 반환값이 null일 경우 throw로 예외처리

        return ret;
    }

    public ColorLogEntity update(final ColorLogEntity entity) {
        if (!isValidEntity(entity)) {
            log.warn("The entity is not valid");
            throw new RuntimeException("The entity is not valid");
        }

        final Optional<ColorLogEntity> prevEntity = repository.findById(entity.getId());
        prevEntity.ifPresent(colorLog -> {
            colorLog.setColorB(entity.getColorB());
            colorLog.setColorG(entity.getColorG());
            colorLog.setColorR(entity.getColorR());
            colorLog.setText(entity.getText());
            repository.save(entity);
        });

        log.info("Entity has updated");

        final Optional<ColorLogEntity> updatedEntity = repository.findById(entity.getId());
        ColorLogEntity ret = updatedEntity.orElse(entity);    // TODO: 반환값이 null일 경우 throw로 예외처리

        return ret;
    }

    public List<ColorLogEntity> retrive(final String userId) {
        log.info("find user with id : [" + userId + "]");
        return repository.findByUserId(userId);
    }

    public List<ColorLogEntity> delete(final ColorLogEntity entity) {
        if (!isValidEntity(entity)) {
            log.warn("The entity is not valid");
            throw new RuntimeException("The entity is not valid");
        }

        try {
            repository.delete(entity);
        }
        catch (Exception e) {
            log.error("Error while deleting entity : {} msg {}", entity.getId(), e);
            throw new RuntimeException("Error while deleting entity");
        }
        log.info("Entity has deleted {}", entity.getId());
        return retrive(entity.getUserId());
    }
}
