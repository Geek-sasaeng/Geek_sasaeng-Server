package shop.geeksasang.controller.applelogin.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Name {

    private String lastName;
    private String firstName;

    public Name(String lastName, String firstName) {
        this.lastName = lastName;
        this.firstName = firstName;
    }
}
