package shop.geeksasang.utils.login;

public class LoginDtoFilter {
    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return true;
        }
        return obj.equals(0);
    }
}
