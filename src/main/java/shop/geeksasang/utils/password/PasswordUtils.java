package shop.geeksasang.utils.password;

public class PasswordUtils {

    static public boolean isNotSamePassword(String password, String checkPassword){
        return !password.equals(checkPassword);
    }
}
