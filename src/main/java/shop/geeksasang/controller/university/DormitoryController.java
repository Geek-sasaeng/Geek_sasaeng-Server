package shop.geeksasang.controller.university;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shop.geeksasang.config.response.BaseResponse;

import shop.geeksasang.dto.dormitory.GetDormitoriesRes;

import shop.geeksasang.service.university.DormitoryService;
import shop.geeksasang.utils.jwt.NoIntercept;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DormitoryController {

    private final DormitoryService dormitoryService;

    @ApiOperation(value = "조회: 대학교별 기숙사 목록 조회", notes = "대학교 id 입력받아 기숙사 목록을 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다."),
            @ApiResponse(code = 2606, message = "기숙사가 존재하지 않습니다."),
            @ApiResponse(code = 4000, message = "서버 오류입니다.")
    })
    @NoIntercept
    @GetMapping("/{universityId}/dormitories")
    public BaseResponse<List<GetDormitoriesRes>> getDormitoriesByUniversity(@PathVariable int universityId) {
        List<GetDormitoriesRes> response = dormitoryService.getDormitories(universityId);
        return new BaseResponse<>(response);
    }

}
