package com.ivanfranchin.orderapi.rest.controller;

import com.ivanfranchin.orderapi.rest.dto.*;
import com.ivanfranchin.orderapi.service.JobTagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ivanfranchin.orderapi.config.SwaggerConfig.BEARER_KEY_SECURITY_SCHEME;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tags")
public class JobTagsController {

    private final JobTagService jobTagService;
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @GetMapping("/getAllTags/{userId}")
    public List<JobTagDto> getAllTags(@PathVariable("userId") Integer userId) throws Exception {
        return jobTagService.getAllTagsByUserId(userId);
    }
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @PostMapping("/createTag")
    public GenericResponse createTag(@RequestBody RequestJobTagDto requestJobTagDto) throws Exception {
        return jobTagService.createNewTagByUserIdAndTagName(requestJobTagDto);
    }
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @PutMapping("/updateTag")
    public GenericResponse updateTag(@RequestBody RequestJobTagDto requestJobTagDto) throws Exception {
        return jobTagService.updateTagByUserIdAndTagId(requestJobTagDto);
    }

}
