package shop.geeksasang.dto.announcement.get;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import shop.geeksasang.config.status.BaseStatus;
import shop.geeksasang.domain.announcement.Announcement;

@Getter
@Setter
public class GetAnnouncementDetailRes {

    @ApiModelProperty(example = "1")
    @ApiParam(value = "공지사항 id 값")
    private int announcementId;

    @ApiModelProperty(example = "긱사생 업데이트 공지사항")
    @ApiParam(value = "공지사항 제목")
    private String title;

    @ApiModelProperty(example = "이번에 version1.0 이 출시되었습니다.")
    @ApiParam(value = "공지사항 세부 내용")
    private String content;

    @ApiModelProperty(example = "img_url")
    @ApiParam(value = "공지사항에 들어갈 이미지 url")
    private String imgUrl;

    @ApiModelProperty(example = "2022-07-13 16:29:30")
    @ApiParam(value = "공지사항 생성 시각")
    private String createdAt;

    @ApiModelProperty(example = "2022-07-13 16:29:30")
    @ApiParam(value = "공지사항 수정 시각")
    private String updatedAt;

    @ApiModelProperty(example = "ACTIVE")
    @ApiParam(value = "공지사항 상태")
    private BaseStatus status;

    /*편의 메서드*/
    public static GetAnnouncementDetailRes toDto(Announcement announcement){
        return GetAnnouncementDetailRes.builder()
                .announcementId(announcement.getId())
                .title(announcement.getTitle())
                .content(announcement.getContent())
                .imgUrl(announcement.getImgUrl())
                .createdAt(announcement.getCreatedAt())
                .updatedAt(announcement.getUpdatedAt())
                .status(announcement.getStatus())
                .build();
    }

    @Builder
    public GetAnnouncementDetailRes(int announcementId, String title, String content, String imgUrl, String createdAt, String updatedAt, BaseStatus status){
        this.announcementId = announcementId;
        this.title = title;
        this.content = content;
        this.imgUrl = imgUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
    }


}
