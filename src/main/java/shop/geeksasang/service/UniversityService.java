package shop.geeksasang.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.geeksasang.config.response.BaseResponse;
import shop.geeksasang.domain.DeliveryParty;
import shop.geeksasang.domain.University;
import shop.geeksasang.repository.*;
import shop.geeksasang.domain.*;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class UniversityService {

    private final UniversityRepository universityRepository;

    // 대학교 조회: 전체 목록
    @Transactional(readOnly = false)
    public List<University> getAllUniversity(){
        List<University> getAllUniversityRes = universityRepository.findAll();
        return getAllUniversityRes;
    }

}
