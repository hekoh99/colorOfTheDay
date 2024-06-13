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


@RestController
@RequestMapping("colorLog")
public class ColorLogController {
    @Autowired
    private ColorLogService service;

    private void setExceptionBody(BodyBuilder rBody, ResponseDto<ColorLogDto>  response, Exception e) {
        String err = e.getMessage();
        response = ResponseDto.<ColorLogDto>builder().error(err).build();
        rBody = ResponseEntity.badRequest();
    }

    @GetMapping("/test")
    public ResponseEntity<?> testColorLog() {
        String str = service.testService();
        ResponseDto<String> response = ResponseDto.<String>builder().data(str).build();
        return ResponseEntity.ok().body(response);
    }

    /**
     * 
     * @apiNote
     *      type : json.
     *      desc : create color record of the day
     *      {
     *          "text":"my day",
     *          "colorR":10,
     *          "colorG":211,
     *          "colorB":56,
     *          "date":"2024-05-11"    
     *      }
     * @param dto
     * @return 
     *      ResponseEntity
     */
    @PostMapping
    public ResponseEntity<?> createTodo(@RequestBody ColorLogDto dto) {
        ResponseDto<ColorLogDto> response = null;
        BodyBuilder rBody = null;
        try {
            String tmpUserId = "tmp-user";            
            ColorLogEntity entity = ColorLogDto.toEntity(dto);

            entity.setId(null);
            entity.setUserId(tmpUserId);

            ColorLogEntity savedColorLog = service.create(entity);
            ColorLogDto resDto = ColorLogEntity.toDto(savedColorLog);
            response = ResponseDto.<ColorLogDto>builder().data(resDto).build();
            rBody = ResponseEntity.ok();
        } catch (Exception e) {
            setExceptionBody(rBody, response, e);
        }
        return rBody.body(response);
    }
    
    /**
     * 
     * @apiNote
     *      type : json
     *      desc : modify color record of the day. The attribute "id" must be specified
     *      {
     *          "id":1,
     *          "text":"my day",
     *          "colorR":10,
     *          "colorG":211,
     *          "colorB":56,
     *          "date":"2024-05-11"    
     *      }
     * @param
     *      dto
     * @return
     *      ResponseEntity
     * 
     */
    @PutMapping
    public ResponseEntity<?> updateColorLog(@RequestBody ColorLogDto dto) {
        ResponseDto<ColorLogDto> response = null;
        BodyBuilder rBody = null;
        
        try {
            String tmpUserId = "tmp-user";
            ColorLogEntity entity = ColorLogDto.toEntity(dto);

            entity.setUserId(tmpUserId);

            ColorLogEntity savedColorLog = service.update(entity);
            ColorLogDto resDto = ColorLogEntity.toDto(savedColorLog);
            response = ResponseDto.<ColorLogDto>builder().data(resDto).build();
            rBody = ResponseEntity.ok();
        } catch (Exception e) {
            setExceptionBody(rBody, response, e);
        }

        return rBody.body(response);
    }

    /**
     * @apiNote
     *      type : query string
     *      desc : ex) localhost:8080/colorLog?userId=tmp-user
     * @param userId
     * @return ResponseEntity
     */
    @GetMapping
    public ResponseEntity<?> retriveColorLog(@RequestParam String userId) {
        ListResponseDto<ColorLogDto> response = null;
        BodyBuilder rBody = null;
        try {
            List<ColorLogEntity> entities = service.retrive(userId);
            List<ColorLogDto> dtos = entities.stream().map(ColorLogDto::new).collect(Collectors.toList());
            response = ListResponseDto.<ColorLogDto>builder().data(dtos).build();
            rBody = ResponseEntity.ok();
        } catch (Exception e) {
            String err = e.getMessage();
            response = ListResponseDto.<ColorLogDto>builder().error(err).build();
            rBody = ResponseEntity.badRequest();
        }
        return rBody.body(response);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteColorLog(@RequestBody ColorLogDto dto) {
        ListResponseDto<ColorLogDto> response = null;
        BodyBuilder rBody = null;

        try {
            String tmpUserId = "tmp-user";
            ColorLogEntity entity = ColorLogDto.toEntity(dto);

            entity.setUserId(tmpUserId);

            List<ColorLogEntity> entities = service.delete(entity);
            List<ColorLogDto> dtos = entities.stream().map(ColorLogDto::new).collect(Collectors.toList());
            response = ListResponseDto.<ColorLogDto>builder().data(dtos).build();
            rBody = ResponseEntity.ok();
        }
        catch (Exception e) {
            String err = e.getMessage();
            response = ListResponseDto.<ColorLogDto>builder().error(err).build();
            rBody = ResponseEntity.badRequest();
        }
        return rBody.body(response);
    }
}
