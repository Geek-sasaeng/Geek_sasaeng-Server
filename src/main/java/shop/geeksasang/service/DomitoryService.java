package shop.geeksasang.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.geeksasang.domain.Domitory;
import shop.geeksasang.domain.Member;
import shop.geeksasang.repository.DomitoryRepository;
import shop.geeksasang.repository.UniversityRepository;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class DomitoryService {

    private final DomitoryRepository domitoryRepository;

    @Transactional(readOnly = false)
    public List<Domitory> getDomitories(int university_id){
        List<Domitory> domitory = domitoryRepository.findDomiByUniversity_id(university_id);

        return domitory;
    }

}
