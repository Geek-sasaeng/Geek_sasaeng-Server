package shop.geeksasang.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.geeksasang.config.response.BaseResponse;
import shop.geeksasang.domain.University;
import shop.geeksasang.service.UniversityService;

import java.util.List;

@RestController
@RequestMapping("/universities")
@RequiredArgsConstructor
public class UniversityController {
    private final UniversityService universityService;

    // 대학교 조회: 전체 목록
    @GetMapping
    public BaseResponse<List<University>> getAllUniversity(){
        List<University> getUniversityRes = universityService.getAllUniversity();
        return new BaseResponse<>(getUniversityRes);
    }

}
