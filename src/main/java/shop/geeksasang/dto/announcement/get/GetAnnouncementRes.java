package shop.geeksasang.dto.announcement.get;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import shop.geeksasang.domain.Announcement;

@Getter
@Setter
@Builder
public class GetAnnouncementRes {

    @ApiModelProperty(example = "1", value = "공지사항 pk")
    private int id;

    @ApiModelProperty(example = "2022-07-11 15:30:00",value = "공지사항 수정 시간")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private String updatedAt;

    @ApiModelProperty(example = "2022-07-11 15:30:00",value = "공지사항 생성 시간")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private String createdAt;

    @ApiModelProperty(example = "애플리케이션 업데이트 정보",value = "공지사항 제목")
    private String title;

    @ApiModelProperty(example = "공지사항 내용",value = "공지사항 내용")
    private String content;

    @ApiModelProperty(example = "http://geeksasaeng.shop/s3/neo.jpg",value = "공지사항에 추가되는 이미지 url")
    private String imgUrl;

    static public GetAnnouncementRes toDto(Announcement announcement){
        return GetAnnouncementRes.builder()
                .id(announcement.getId())
                .updatedAt(announcement.getUpdatedAt())
                .createdAt(announcement.getCreatedAt())
                .title(announcement.getTitle())
                .content(announcement.getContent())
                .imgUrl(announcement.getImgUrl())
                .build();
    }

}
