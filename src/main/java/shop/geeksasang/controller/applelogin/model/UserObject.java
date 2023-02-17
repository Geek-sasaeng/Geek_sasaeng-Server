package shop.geeksasang.controller.applelogin.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@NoArgsConstructor
@ToString
public class UserObject {

    private Name name;
    private String email;

    public UserObject(Name name, String email) {
        this.name = name;
        this.email = email;
    }


    public Name getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getLastName(){
        return name.getLastName();
    }

    public String getFirstName(){
        return name.getFirstName();
    }
}

