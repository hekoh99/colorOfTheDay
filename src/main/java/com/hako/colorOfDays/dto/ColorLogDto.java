package com.hako.colorOfDays.dto;

import java.sql.Date;
import com.hako.colorOfDays.model.*;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ColorLogDto {
    private Integer id;
    private String text;
    private Integer colorR;
    private Integer colorG;
    private Integer colorB;
    private Integer date;
    private Integer month;
    private Integer year;

    public ColorLogDto(final ColorLogEntity entity) {
        this.id = entity.getId();
        this.text = entity.getText();
        this.colorB = entity.getColorB();
        this.colorG = entity.getColorG();
        this.colorR = entity.getColorR();
        this.date = entity.getDate();
        this.month = entity.getMonth();
        this.year = entity.getYear();
    }

    public static ColorLogEntity toEntity(final ColorLogDto dto) {
        return ColorLogEntity.builder()
                .id(dto.getId())
                .text(dto.getText())
                .colorR(dto.getColorR())
                .colorG(dto.getColorG())
                .colorB(dto.getColorB())
                .date(dto.getDate())
                .month(dto.getMonth())
                .year(dto.getYear())
                .build();
    }
}
