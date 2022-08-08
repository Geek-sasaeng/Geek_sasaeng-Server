package shop.geeksasang.factory.domain;

import shop.geeksasang.domain.HashTag;

public class HashTagFactory {
    public static HashTag create(){
        return new HashTag(1, "HashTag");
    }
}
