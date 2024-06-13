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
    private Date date;

    public ColorLogDto(final ColorLogEntity entity) {
        this.id = entity.getId();
        this.text = entity.getText();
        this.colorB = entity.getColorB();
        this.colorG = entity.getColorG();
        this.colorR = entity.getColorR();
        this.date = entity.getDate();
    }

    public static ColorLogEntity toEntity(final ColorLogDto dto) {
        return ColorLogEntity.builder()
                .id(dto.getId())
                .text(dto.getText())
                .colorB(dto.getColorB())
                .colorG(dto.getColorG())
                .colorR(dto.getColorR())
                .date(dto.getDate())
                .build();
    }
}
