package shop.geeksasang.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
}
