package shop.geeksasang.service.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.geeksasang.config.exception.BaseException;
import shop.geeksasang.config.exception.response.BaseResponseStatus;
import shop.geeksasang.domain.member.Member;
import shop.geeksasang.repository.member.MemberRepository;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class LogoutService {
    private final MemberRepository memberRepository;

    public void logout(int userId){
        Member member = memberRepository.findMemberByIdAndStatus(userId)
                .orElseThrow(()->new BaseException(BaseResponseStatus.NOT_EXISTS_PARTICIPANT));

        member.updateFcmToken(null);
    }
}
