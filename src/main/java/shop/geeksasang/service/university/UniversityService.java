package shop.geeksasang.service.university;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.geeksasang.dto.university.GetUniversitiesRes;
import shop.geeksasang.repository.university.UniversityRepository;

import java.util.List;
import java.util.stream.Collectors;

import static shop.geeksasang.config.TransactionManagerConfig.JPA_TRANSACTION_MANAGER;

@Transactional
@Service
@RequiredArgsConstructor
public class UniversityService {

    private final UniversityRepository universityRepository;

    // 대학교 조회: 전체 목록
    @Transactional(readOnly = true, transactionManager = JPA_TRANSACTION_MANAGER)
    public List<GetUniversitiesRes> getAllUniversity(){
        return universityRepository.findAll().stream()
                .map(GetUniversitiesRes::of)
                .collect(Collectors.toList());

    }
}
