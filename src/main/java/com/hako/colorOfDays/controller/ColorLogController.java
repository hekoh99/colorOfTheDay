package com.hako.colorOfDays.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity.BodyBuilder;

import com.hako.colorOfDays.service.ColorLogService;
import com.hako.colorOfDays.dto.*;
import com.hako.colorOfDays.model.ColorLogEntity;

import java.util.stream.*;
import java.util.List;
import java.util.ArrayList;


@RestController
@RequestMapping("colorLog")
public class ColorLogController {
    @Autowired
    private ColorLogService service;

    @GetMapping("/test")
    public ResponseEntity<?> testColorLog() {
        String str = service.testService();
        List<String> list = new ArrayList<>();
        list.add(str);
        ResponseDto<String> response = ResponseDto.<String>builder().data(list).build();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    public ResponseEntity<?> retriveColorLog(@RequestBody ColorLogDto dto) {
        ResponseDto<ColorLogDto> response = null;
        BodyBuilder rBody = null;
        try {
            String tmpUserId = "tmp-user";
            ColorLogEntity entity = ColorLogDto.toEntity(dto);

            entity.setUserId(tmpUserId);

            List<ColorLogDto> dtos = service.retrive(entity);
            response = ResponseDto.<ColorLogDto>builder().data(dtos).build();
            rBody = ResponseEntity.ok();
        } catch (Exception e) {
            String error = e.getMessage();
            response = ResponseDto.<ColorLogDto>builder().error(error).build();
            rBody = ResponseEntity.badRequest();
        }

        return rBody.body(response);
    }

    /**
     * @apiNote
     * 특정 날짜의 colorlog 값을 가져옴
     * ex) /colorLog/detail?date=19&year=2024&month=6
     * @param dto
     * @param year
     * @param month
     * @param date
     * @return  ResponseEntity
     */
    @GetMapping("/detail")
    public ResponseEntity<?> retriveDateColorLog(@RequestParam int year, @RequestParam int month, @RequestParam int date) {
        ResponseDto<ColorLogDto> response = null;
        BodyBuilder rBody = null;
        try {
            String tmpUserId = "tmp-user";
            ColorLogEntity entity = ColorLogEntity.builder()
                                        .userId(tmpUserId)
                                        .date(date)
                                        .month(month)
                                        .year(year)
                                        .build();

            ColorLogDto resultDto = service.retriveByDate(entity);
            List<ColorLogDto> dtos = new ArrayList<>();
            dtos.add(resultDto);

            response = ResponseDto.<ColorLogDto>builder().data(dtos).build();
            rBody = ResponseEntity.ok();
        } catch (Exception e) {
            String error = e.getMessage();
            response = ResponseDto.<ColorLogDto>builder().error(error).build();
            rBody = ResponseEntity.badRequest();
        }

        return rBody.body(response);
    }

    @PostMapping
    public ResponseEntity<?> createColorLog(@RequestBody ColorLogDto dto) {
        ResponseDto<ColorLogDto> response = null;
        BodyBuilder rBody = null;
        try {
            String tmpUserId = "tmp-user";
            ColorLogEntity entity = ColorLogDto.toEntity(dto);

            entity.setUserId(tmpUserId);

            List<ColorLogDto> dtos = service.create(entity);
            response = ResponseDto.<ColorLogDto>builder().data(dtos).build();
            rBody = ResponseEntity.ok();
        } catch (Exception e) {
            String error = e.getMessage();
            response = ResponseDto.<ColorLogDto>builder().error(error).build();
            rBody = ResponseEntity.badRequest();
        }

        return rBody.body(response);
    }

    @PutMapping
    public ResponseEntity<?> updateColorLog(@RequestBody ColorLogDto dto) {
        ResponseDto<ColorLogDto> response = null;
        BodyBuilder rBody = null;

        try {
            String tmpUserId = "tmp-user";
            ColorLogEntity entity = ColorLogDto.toEntity(dto);

            entity.setUserId(tmpUserId);

            List<ColorLogDto> dtos = service.update(entity);
            response = ResponseDto.<ColorLogDto>builder().data(dtos).build();
            rBody = ResponseEntity.ok();
        } catch (Exception e) {
            String error = e.getMessage();
            response = ResponseDto.<ColorLogDto>builder().error(error).build();
            rBody = ResponseEntity.badRequest();
        }
        return rBody.body(response);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteColorLog(@RequestBody ColorLogDto dto) {
        ResponseDto<ColorLogDto> response = null;
        BodyBuilder rBody = null;

        try {
            String tmpUserId = "tmp-user";
            ColorLogEntity entity = ColorLogDto.toEntity(dto);

            entity.setUserId(tmpUserId);

            List<ColorLogDto> dtos = service.delete(entity);
            response = ResponseDto.<ColorLogDto>builder().data(dtos).build();
            rBody = ResponseEntity.ok();
        } catch (Exception e) {
            String error = e.getMessage();
            response = ResponseDto.<ColorLogDto>builder().error(error).build();
            rBody = ResponseEntity.badRequest();
        }
        return rBody.body(response);
    }
}
