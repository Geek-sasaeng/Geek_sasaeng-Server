package shop.geeksasang.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.geeksasang.config.exception.BaseException;
import shop.geeksasang.domain.Domitory;
import shop.geeksasang.domain.Member;
import shop.geeksasang.dto.domitory.GetDomitoriesRes;
import shop.geeksasang.repository.DomitoryRepository;
import shop.geeksasang.repository.UniversityRepository;
import static shop.geeksasang.config.exception.BaseResponseStatus.*;

import java.util.List;
import java.util.stream.Collectors;


@Transactional
@Service
@RequiredArgsConstructor
public class DomitoryService {

    private final DomitoryRepository domitoryRepository;

    public List<GetDomitoriesRes> getDomitories(int university_id){

        List<Domitory> domitories = domitoryRepository.findDomitoryByUniversityId(university_id);

        if(domitories.isEmpty()){
            throw new BaseException(NOT_EXISTS_DOMITORY);
        }

        return domitories.stream()
                .map(domitory -> GetDomitoriesRes.of(domitory))
                .collect(Collectors.toList());
    }

}
