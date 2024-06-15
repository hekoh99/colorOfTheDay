package com.hako.colorOfDays.persistence;

import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ColorLogTableCreator {
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
}
