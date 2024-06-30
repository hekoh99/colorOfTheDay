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

    private String getTableName(ColorLogEntity entity) {
        String tableName = String.format("colorlog_%04d_%02d", entity.getYear(), entity.getMonth());
        return tableName;
    }

    @Transactional
    public boolean checkIfTableExists(String tableName) {
        String sql = String.format("SELECT COUNT(*) FROM Information_schema.tables\n" + //
                        "WHERE table_schema = 'comd_db'\n" + //
                        "AND table_name = '%s';", tableName);
        Query query = entityManager.createNativeQuery(sql).unwrap(NativeQuery.class);
        Object row = (Object)query.getSingleResult();
        Integer result = Integer.parseInt(String.valueOf(row));

        if (result == 1) {
            return true;
        }
        log.info("table does not exist");
        return false;
    }

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
        String tableName = getTableName(entity);
        // 테이블이 존재하지 않으면 생성.
        if (!checkIfTableExists(tableName)) {
            createColorLogTable(entity.getYear(), entity.getMonth());
        }

        /**
         * [insert example]
         * insert into colorlog_2024_06 (text, date, colorR, colorG, colorB, userId) 
         *      value ("hi", 29, 12, 145, 55, "test-user");
         */
        String text = (entity.getText() != null) ? entity.getText() : "";
        String sql = String.format("INSERT INTO %s" + 
                        "(text, date, colorR, colorG, colorB, userId)" +
                        " value(\"%s\", %d, %d, %d, %d, \"%s\");", tableName, text, entity.getDate(), entity.getColorR(), entity.getColorG(), entity.getColorB(), entity.getUserId());
        entityManager.createNativeQuery(sql).executeUpdate();
    }

    @Transactional
    public void updateColorLog(ColorLogEntity entity) {
        String tableName = getTableName(entity);
        String sql = String.format("UPDATE %s set text=\"%s\", colorR=%d, colorG=%d, colorB=%d where id=%d;", tableName, entity.getText(), entity.getColorR(), entity.getColorG(), entity.getColorB(), entity.getId());
        entityManager.createNativeQuery(sql).executeUpdate();
    }

    @Transactional
    public void deleteColorLog(ColorLogEntity entity) {
        String tableName = getTableName(entity);
        String sql = String.format("Delete from %s where id=%d", tableName, entity.getId());
        entityManager.createNativeQuery(sql).executeUpdate();
    }

    @Transactional
    public List<ColorLogDto> getColorLogByUserId(ColorLogEntity entity) {
        String sql = String.format("select * from colorlog_%04d_%02d as ColorLog where ColorLog.userId = \"%s\";", entity.getYear(), entity.getMonth(), entity.getUserId());
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

    @Transactional
    public ColorLogDto getColorLogById(ColorLogEntity entity) {
        String sql = String.format("select * from colorlog_%04d_%02d where id = %d;", entity.getYear(), entity.getMonth(), entity.getId());
        log.info(sql); //TODO: 지울 것.
        Query query = entityManager.createNativeQuery(sql).unwrap(NativeQuery.class);
        Object[] row = (Object[])query.getSingleResult();
        
        ColorLogDto result = ColorLogDto.builder()
            .id((Integer)row[0])
            .text((String)row[1])
            .date((Integer)row[2])
            .colorR((Integer)row[3])
            .colorG((Integer)row[4])
            .colorB((Integer)row[5])
            .month(entity.getMonth())
            .year(entity.getYear())
            .build();
        return result;
    }

    @Transactional
    public ColorLogDto getColorLogByDate(ColorLogEntity entity) {
        ColorLogDto result = ColorLogDto.builder()
            .id(-1)
            .date(entity.getDate())
            .month(entity.getMonth())
            .year(entity.getYear())
            .build();
        
        try {
            String tableName = getTableName(entity);
            if (!checkIfTableExists(tableName)) {
                // do nothing.
            }
            else {
                String sql = String.format("select * from colorlog_%04d_%02d where date = %d;", entity.getYear(), entity.getMonth(), entity.getDate());
                Query query = entityManager.createNativeQuery(sql).unwrap(NativeQuery.class);
                Object[] row = (Object[])query.getSingleResult();

                result.setId((Integer)row[0]);
                result.setText((String)row[1]);
                result.setDate((Integer)row[2]);
                result.setColorR((Integer)row[3]);
                result.setColorG((Integer)row[4]);
                result.setColorB((Integer)row[5]);
            }
        } catch (Exception e) {
            String errorMsg = String.format("Error occured while excuting query [%s]", e.getMessage());
            log.error(errorMsg);
        }
        return result;
    }
}
