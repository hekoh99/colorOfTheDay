package com.hako.colorOfDays.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.hako.colorOfDays.model.ColorLogEntity;
import java.util.List;

@Repository
public interface ColorLogRepository extends JpaRepository<ColorLogEntity, Integer> {
    @Query(value = "select * from ColorLog t where t.userId = ?1", nativeQuery = true)
    List<ColorLogEntity> findByUserId(String userId);
}
