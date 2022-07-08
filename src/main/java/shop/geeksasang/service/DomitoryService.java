package shop.geeksasang.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.geeksasang.config.exception.BaseException;
import shop.geeksasang.domain.Domitory;
import shop.geeksasang.domain.Member;
import shop.geeksasang.repository.DomitoryRepository;
import shop.geeksasang.repository.UniversityRepository;
import static shop.geeksasang.config.exception.BaseResponseStatus.*;

import java.util.List;




@Transactional
@Service
@RequiredArgsConstructor
public class DomitoryService {

    private final DomitoryRepository domitoryRepository;
    private final UniversityRepository universityRepository;

    @Transactional(readOnly = false)
    public List<Domitory> getDomitories(int university_id){

        // id가 존재 하지 않을 때 : 학교 없음
        if(universityRepository.findById(university_id).isEmpty()) {
            throw new BaseException(NOT_EXISTS_UNIVERSITY_ID);
        }
//        // id에 해당하는 기숙사가 존재하지 않을 때
//        if(domitoryRepository.findDomitoryByUniversity_id(university_id).isEmpty()){
//            throw new BaseException(NOT_EXISTS_DOMITORY);
//        }

        List<Domitory> domitory = domitoryRepository.findDomitoryByUniversity_id(university_id);

        return domitory;
    }

}
