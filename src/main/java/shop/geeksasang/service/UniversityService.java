package shop.geeksasang.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.geeksasang.domain.University;
import shop.geeksasang.dto.domitory.GetDomitoriesRes;
import shop.geeksasang.dto.university.GetUniversitiesRes;
import shop.geeksasang.repository.*;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class UniversityService {

    private final UniversityRepository universityRepository;

    // 대학교 조회: 전체 목록
    @Transactional(readOnly = false)
    public List<GetUniversitiesRes> getAllUniversity(){
        return universityRepository.findAll().stream()
                .map(university -> GetUniversitiesRes.of(university))
                .collect(Collectors.toList());

    }

    public List<GetDomitoriesRes> getDomitories(int universityId) {
        University university = universityRepository.findDomitoriesByUniversityId(universityId).orElseThrow(() -> new RuntimeException());
        return university.getDomitories().stream()
                .map(domitory -> GetDomitoriesRes.of(domitory))
                .collect(Collectors.toList());
    }
}
