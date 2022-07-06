package shop.geeksasang.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shop.geeksasang.config.response.BaseResponse;
import shop.geeksasang.domain.Domitory;
import shop.geeksasang.service.DomitoryService;

import java.util.List;

@RestController
@RequestMapping("/domitory")
@RequiredArgsConstructor
public class DomitoryController {
    private final DomitoryService domitoryService;

    @GetMapping
    public BaseResponse<List<Domitory>> getDomitoryByUniversity(@RequestParam int universityId) {
        List<Domitory> getDomitoryRes = domitoryService.getDomitoriesByUniversityId(universityId);
        return new BaseResponse<>(getDomitoryRes);
    }
}
