package shop.geeksasang.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.geeksasang.config.exception.BaseException;
import shop.geeksasang.config.exception.response.BaseResponseStatus;
import shop.geeksasang.domain.Announcement;
import shop.geeksasang.dto.notification.get.GetAnnouncementDetailReq;
import shop.geeksasang.dto.notification.get.GetAnnouncementDetailRes;
import shop.geeksasang.repository.AnnouncementRepository;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;

    //조회: 공지사항 상세조회
    public GetAnnouncementDetailRes getAnnouncementDetail(GetAnnouncementDetailReq dto){
        Announcement announcement = announcementRepository.findByIdAndStatus(dto.getAnnouncementId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXIST_ANNOUNCEMENT));

        return GetAnnouncementDetailRes.toDto(announcement);
    }
}
