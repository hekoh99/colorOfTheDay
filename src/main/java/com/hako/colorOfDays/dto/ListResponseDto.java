package com.hako.colorOfDays.dto;

import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import java.util.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ListResponseDto<T> {
    private String error;
    private List<T> data;
}
