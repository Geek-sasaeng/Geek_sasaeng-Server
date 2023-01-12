package shop.geeksasang.controller.common;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import shop.geeksasang.config.response.BaseResponse;
import shop.geeksasang.dto.s3.PostImageRes;
import shop.geeksasang.service.common.AwsS3Service;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class AwsS3Controller {

    private final AwsS3Service awsS3Service;

    @ApiOperation(value = "이미지 업로드를 위한 API", notes = "업로드할 이미지와 JWT를 사용해 이미지를 업로드하고 이미지의 URL을 리턴 받는다.")
    @ApiResponses({
            @ApiResponse(code = 1000 , message = "요청에 성공하였습니다."),
            @ApiResponse(code = 4000 , message =  "서버 오류입니다.")
    })
    @PostMapping("/upload")
    public BaseResponse<PostImageRes> uploadFile(@RequestParam MultipartFile images) throws IOException {
        String imageUrl = awsS3Service.upload(images.getInputStream(), images.getOriginalFilename(), images.getSize());
        return new BaseResponse<>(new PostImageRes(imageUrl));
    }
}
