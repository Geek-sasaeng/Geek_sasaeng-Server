package shop.geeksasang.factory.domain;

import shop.geeksasang.domain.FoodCategory;

public class FoodCategoryFactory {

    public static FoodCategory create(){
        return new FoodCategory(1, "Food Category");
    }
}
