package shop.geeksasang.factory.domain;

import shop.geeksasang.domain.Dormitory;
import shop.geeksasang.domain.Location;

public class DormitoryFactory {

    public static Dormitory crate(){
        return new Dormitory(null, "세종", new Location(1.11, 1.22));
    }
}
