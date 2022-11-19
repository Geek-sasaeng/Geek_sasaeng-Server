package shop.geeksasang.dto.member.get;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import shop.geeksasang.domain.Member;
import shop.geeksasang.dto.member.get.vo.DormitoriesVo;

import java.util.List;
@Getter @Setter
public class GetMemberInfoRes {

    private List<DormitoriesVo> dormitoryList; //학교에 존재하는 모든 기숙사 리스트
    private int dormitoryId; //현재 설정된 기숙사 id
    private String dormitoryName; //현재 설정된 기숙사 이름
    private String loginId; //현재 로그인 id
    private String imgUrl; //현재 프로필 이미지


    public static GetMemberInfoRes toDto(Member member, List<DormitoriesVo> dormitoryList){
        return GetMemberInfoRes.builder()
                .dormitoryId(member.getDormitory().getId())
                .dormitoryName(member.getDormitory().getName())
                .loginId(member.getLoginId())
                .imgUrl(member.getProfileImgUrl())
                .dormitoryList(dormitoryList).build();
    }

    @Builder
    public GetMemberInfoRes(List<DormitoriesVo> dormitoryList, int dormitoryId, String dormitoryName, String loginId
                                    ,String imgUrl){
        this.dormitoryName = dormitoryName;
        this.dormitoryId = dormitoryId;
        this.dormitoryList = dormitoryList;
        this.loginId = loginId;
        this.imgUrl = imgUrl;
    }

}