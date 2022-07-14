package shop.geeksasang.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.geeksasang.config.exception.BaseException;
import shop.geeksasang.config.exception.BaseResponseStatus;
import shop.geeksasang.domain.DeliveryParty;
import shop.geeksasang.domain.DeliveryPartyHashTag;
import shop.geeksasang.domain.HashTag;
import shop.geeksasang.repository.DeliveryPartyHashTagRepository;
import shop.geeksasang.repository.HashTagRepository;


import java.util.ArrayList;
import java.util.List;


@Transactional
@Service
@RequiredArgsConstructor
public class DeliveryPartyHashTagService {

    private final DeliveryPartyHashTagRepository deliveryPartyHashTagRepository;
    private final HashTagRepository hashTagRepository;

    @Transactional(readOnly = false)
    public void saveHashTag(DeliveryParty deliveryParty, List<Integer>hashTagIds){

        List<HashTag> hashTagList = new ArrayList<>();

        if(!hashTagIds.isEmpty()) {
            for (Integer hashTagId : hashTagIds) {
                HashTag hashTag = hashTagRepository.findById(hashTagId)
                        .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXISTS_HASHTAG));
                hashTagList.add(hashTag);

                deliveryParty.connectHashTag(hashTagList);
                deliveryPartyHashTagRepository.save(new DeliveryPartyHashTag(deliveryParty, hashTag));
            }
        }
        deliveryParty.connectHashTag(hashTagList);

    }

}
