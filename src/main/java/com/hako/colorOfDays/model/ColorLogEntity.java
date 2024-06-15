/**
 *  File : ColorLogEntity.java
 * 
 *  날짜별로 저장될 하루의 color log에 대한 entity
 */


package com.hako.colorOfDays.model;

import java.sql.Date;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import com.hako.colorOfDays.dto.ColorLogDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class ColorLogEntity {
    @Id
    private Integer id;
    private String userId;
    private String text;
    private Integer colorR;
    private Integer colorG;
    private Integer colorB;
    private Integer date;
    private Integer month;
    private Integer year;

    // @CreatedDate
    // @DateTimeFormat(pattern = "yyyy-mm-dd")
    // private Date createDate;
}
