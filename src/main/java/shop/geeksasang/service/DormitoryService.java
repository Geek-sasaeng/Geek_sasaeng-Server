package shop.geeksasang.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.geeksasang.config.exception.BaseException;
import shop.geeksasang.domain.Dormitory;
import shop.geeksasang.dto.dormitory.GetDormitoriesRes;
import shop.geeksasang.repository.DormitoryRepository;

import java.util.List;
import java.util.stream.Collectors;

import static shop.geeksasang.config.exception.response.BaseResponseStatus.NOT_EXISTS_DORMITORY;


@Transactional
@Service
@RequiredArgsConstructor
public class DormitoryService {

    private final DormitoryRepository dormitoryRepository;

    public List<GetDormitoriesRes> getDormitories(int university_id){

        List<Dormitory> dormitories = dormitoryRepository.findDormitoryByUniversityId(university_id);

        if(dormitories.isEmpty()){
            throw new BaseException(NOT_EXISTS_DORMITORY);
        }

        return dormitories.stream()
                .map(dormitory -> GetDormitoriesRes.of(dormitory))
                .collect(Collectors.toList());
    }

}
