package shop.geeksasang.controller.applelogin.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
public class UserObject {

    private Name name;
    private String email;


    public UserObject(Name name, String email) {
        this.name = name;
        this.email = email;
    }

    @NoArgsConstructor
    @Setter
    @Getter
    static class Name{
        private String firstName;
        private String lastName;

        public Name(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }
    }

    public Name getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getLastName(){
        return getLastName();
    }

    public String getFirstName(){
        return getFirstName();
    }
}

