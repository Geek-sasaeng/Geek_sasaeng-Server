package shop.geeksasang.service.announcement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.geeksasang.config.exception.BaseException;
import shop.geeksasang.config.exception.response.BaseResponseStatus;
import shop.geeksasang.domain.announcement.Announcement;
import shop.geeksasang.domain.member.Member;
import shop.geeksasang.dto.announcement.get.GetAnnouncementRes;
import shop.geeksasang.dto.login.JwtInfo;
import shop.geeksasang.dto.announcement.get.GetAnnouncementDetailReq;
import shop.geeksasang.dto.announcement.get.GetAnnouncementDetailRes;
import shop.geeksasang.repository.announcement.AnnouncementRepository;
import shop.geeksasang.repository.member.MemberRepository;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class AnnouncementService {
    private final AnnouncementRepository announcementRepository;
    private final MemberRepository memberRepository;

    //공지사항 전체 조회
    public List<GetAnnouncementRes> getAnnouncements(JwtInfo jwtInfo){

        int chiefId = jwtInfo.getUserId();

        //요청 보낸 사용자 Member 조회
        int memberId = jwtInfo.getUserId();
        Member findMember = memberRepository.findMemberByIdAndStatus(memberId).
                orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXISTS_PARTICIPANT));

        //저장된 announcements 조회
        List<Announcement> announcements = announcementRepository.findAnnouncementOrderByCreatedAt();

        //dto로 변경
        List<GetAnnouncementRes> results = announcementRepository.findAnnouncementOrderByCreatedAt()
                .stream().map(announcement -> GetAnnouncementRes.toDto(announcement))
                .collect(Collectors.toList());

        return results;

    }

    //조회: 공지사항 상세조회
    public GetAnnouncementDetailRes getAnnouncementDetail(GetAnnouncementDetailReq dto, int memberId){

        // 요청 보낸 멤버 조회 및 검증
        Member findMember = memberRepository.findMemberByIdAndStatus(memberId).
                orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXISTS_PARTICIPANT));

        Announcement announcement = announcementRepository.findByIdAndStatus(dto.getAnnouncementId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXIST_ANNOUNCEMENT));

        return GetAnnouncementDetailRes.toDto(announcement);
    }
}
