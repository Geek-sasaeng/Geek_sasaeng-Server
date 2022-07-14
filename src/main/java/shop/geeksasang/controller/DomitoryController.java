package shop.geeksasang.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shop.geeksasang.config.response.BaseResponse;
import shop.geeksasang.domain.Domitory;

import shop.geeksasang.dto.domitory.GetDomitoriesRes;

import shop.geeksasang.dto.domitory.DomitoryDto;
import shop.geeksasang.repository.DomitoryRepository;

import shop.geeksasang.service.DomitoryService;
import shop.geeksasang.service.UniversityService;
import shop.geeksasang.utils.jwt.NoIntercept;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class DomitoryController {

    private final DomitoryService domitoryService;

    @ApiOperation(value = "조회: 대학교별 기숙사 목록 조회", notes = "대학교 id 입력받아 기숙사 목록을 조회한다.")
    @NoIntercept
    @GetMapping("/{universityId}/domitories")
    public BaseResponse<List<GetDomitoriesRes>> getDomitoriesByUniversity(@PathVariable int universityId) {
        List<GetDomitoriesRes> response = domitoryService.getDomitories(universityId);
        return new BaseResponse<>(response);
    }

}
