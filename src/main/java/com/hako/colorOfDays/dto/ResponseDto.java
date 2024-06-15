/**
 * FILE : ResponseDto.java
 * 
 * 응답값으로 사용할 DTO.
 */

package com.hako.colorOfDays.dto;

import java.util.List;
import com.hako.colorOfDays.model.*;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseDto<T> {
    private String error;
    private List<T> data;
}
