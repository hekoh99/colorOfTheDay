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
@Table(name="ColorLog")
public class ColorLogEntity {
    @Id
    @GeneratedValue(generator = "system-id")
    @GenericGenerator(name="system-id")
    private Integer id;
    private String userId;
    private String text;
    private Integer colorR;
    private Integer colorG;
    private Integer colorB;

    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date date;

    public static ColorLogDto toDto(final ColorLogEntity entity) {
        return ColorLogDto.builder()
                .id(entity.getId())
                .text(entity.getText())
                .colorB(entity.getColorB())
                .colorG(entity.getColorG())
                .colorR(entity.getColorR())
                .date(entity.getDate())
                .build();
    }
}
