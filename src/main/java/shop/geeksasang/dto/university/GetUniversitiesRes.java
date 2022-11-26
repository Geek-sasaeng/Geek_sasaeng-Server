package shop.geeksasang.dto.university;

import lombok.Getter;
import lombok.Setter;
import shop.geeksasang.domain.university.University;

@Getter
@Setter
public class GetUniversitiesRes {
    private int id;
    private String name;
    private String emailAddress;
    private String universityImgUrl;

    public GetUniversitiesRes(int id, String name, String emailAddress, String universityImgUrl) {
        this.id = id;
        this.name = name;
        this.emailAddress = emailAddress;
        this.universityImgUrl = universityImgUrl;
    }

    public static GetUniversitiesRes of(University university) {
        return new GetUniversitiesRes(university.getId(), university.getName(), university.getEmailAddress(), university.getUniversityImgUrl());
    }
}
