package com.hako.colorOfDays.persistence;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import jakarta.persistence.Query;

import com.hako.colorOfDays.dto.ColorLogDto;
import com.hako.colorOfDays.model.ColorLogEntity;

@Slf4j
@Repository
public class ColorLogTableManager {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void createColorLogTable(final Integer year, Integer month) {
        String tableName = String.format("colorlog_%04d_%02d", year, month);
        entityManager.createNativeQuery("CREATE TABLE  IF NOT EXISTS "+ tableName + "(\n" + //
                        "    id INT NOT NULL AUTO_INCREMENT,\n" + //
                        "    text TEXT NULL,\n" + //
                        "    date INT NOT NULL,\n" + //
                        "    colorR INT,\n" + //
                        "    colorG INT,\n" + //
                        "    colorB INT,\n" + //
                        "    userId VARCHAR(50),\n" + //
                        "    CONSTRAINT testTable_PK PRIMARY KEY(id),\n" + //
                        "    UNIQUE INDEX date_UNIQUE (date ASC)\n" + //
                        ");").executeUpdate();
    }

    @Transactional
    public void insertColorLog(ColorLogEntity entity) {
        
        String tableName = String.format("colorlog_%04d_%02d", entity.getYear(), entity.getMonth());
        // insert into colorlog_2024_06 (text, date, colorR, colorG, colorB, userId) value ("hi", 29, 12, 145, 55, "test-user");
        String text = (entity.getText() != null) ? entity.getText() : "";
        String sql = String.format("INSERT INTO %s" + 
                        "(text, date, colorR, colorG, colorB, userId)" +
                        " value(\"%s\", %d, %d, %d, %d, \"%s\");", tableName, text, entity.getDate(), entity.getColorR(), entity.getColorG(), entity.getColorB(), entity.getUserId());
        log.info(sql); //TODO: 지울 것.
        entityManager.createNativeQuery(sql).executeUpdate();
    }

    @Transactional
    public List<ColorLogDto> getColorLogByUserId(ColorLogEntity entity) {
        String sql = String.format("select * from colorlog_%04d_%02d as ColorLog where ColorLog.userId = \"%s\";", entity.getYear(), entity.getMonth(), entity.getUserId());
        log.info(sql); //TODO: 지울 것.
        Query query = entityManager.createNativeQuery(sql).unwrap(NativeQuery.class);
        List<Object[]> rows = query.getResultList();

        List<ColorLogDto> result = new ArrayList<>(rows.size());
        for (Object[] row : rows) {
                result.add(ColorLogDto.builder()
                        .id((Integer)row[0])
                        .text((String)row[1])
                        .date((Integer)row[2])
                        .colorR((Integer)row[3])
                        .colorG((Integer)row[4])
                        .colorB((Integer)row[5])
                        .month(entity.getMonth())
                        .year(entity.getYear())
                        .build());
        }
        return result;
    }
}
