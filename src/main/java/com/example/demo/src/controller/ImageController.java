package com.example.demo.src.controller;

import com.example.demo.src.dto.response.UploadImageResponseDto;
import com.example.demo.src.service.ImageService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bring")
public class ImageController {
    private final ImageService imageService;

    @ApiOperation("이미지 업로드")
    @PostMapping("/uploadImage")
    public UploadImageResponseDto uploadImage(@RequestPart("image") MultipartFile multipartFiles) {
        return imageService.uploadImage(multipartFiles);
    }

//    @PostMapping("/deleteImage")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void deleteImage(@Authenticated AuthInfo authInfo,@Valid @RequestBody DeleteImageRequestDto deleteImageRequestDto) {
//        imageService.deleteImage(authInfo.getEmail(), deleteImageRequestDto.getImageUrls());
//    }
}
