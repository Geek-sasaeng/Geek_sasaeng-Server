package shop.geeksasang.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shop.geeksasang.config.response.BaseResponse;
import shop.geeksasang.domain.Domitory;
import shop.geeksasang.dto.domitory.GetDomitoriesRes;
import shop.geeksasang.service.DomitoryService;
import shop.geeksasang.service.UniversityService;
import shop.geeksasang.utils.jwt.NoIntercept;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DomitoryController {
    //private final DomitoryService domitoryService;
    private final UniversityService universityService;

    // 기숙사 목록 조회
//    @ApiOperation(value = "조회: 대학교별 기숙사 목록 조회", notes = "대학교 id 입력받아 기숙사 목록을 조회한다.")
//    @NoIntercept
//    @GetMapping
//    public BaseResponse<List<Domitory>> getDomitoryByUniversity(@RequestParam int universityId) {
//        List<Domitory> getDomitoryRes = domitoryService.getDomitoriesByUniversityId(universityId);
//        return new BaseResponse<>(getDomitoryRes);
//    }

    @ApiOperation(value = "조회: 대학교별 기숙사 목록 조회", notes = "대학교 id 입력받아 기숙사 목록을 조회한다.")
    @NoIntercept
    @GetMapping("/{universityId}/domitories")
    public BaseResponse<List<GetDomitoriesRes>> getDomitoryByUniversity(@PathVariable int universityId) {
        List<GetDomitoriesRes> response = universityService.getDomitories(universityId);
        return new BaseResponse<>(response);
    }
}
