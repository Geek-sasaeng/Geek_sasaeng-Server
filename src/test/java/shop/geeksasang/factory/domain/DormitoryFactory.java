package shop.geeksasang.factory.domain;

import shop.geeksasang.domain.university.Dormitory;
import shop.geeksasang.domain.location.Location;

public class DormitoryFactory {

    public static Dormitory create(){
        return new Dormitory(null, "세종", new Location(1.11, 1.22));
    }
}
