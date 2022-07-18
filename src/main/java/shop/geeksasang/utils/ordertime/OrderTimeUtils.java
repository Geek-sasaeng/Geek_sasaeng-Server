package shop.geeksasang.utils.ordertime;

import shop.geeksasang.config.type.OrderTimeCategoryType;

public class OrderTimeUtils {

    public static OrderTimeCategoryType selectOrderTime(int orderHour){

        //아침 : 6시 ~ 10시59분
        if (orderHour >= 6 && orderHour <11){
            return  OrderTimeCategoryType.BREAKFAST;
        }

        //점심 : 11시 ~ 16시59분
        else if(orderHour>=11 && orderHour<17){
            return OrderTimeCategoryType.DINNER;
        }

        //저녁 : 17시 ~ 20시59분
        else if(orderHour>=17 && orderHour<21){
            return OrderTimeCategoryType.DINNER;
        }

        //야식 : 21시 ~ 05:59분
        return OrderTimeCategoryType.MIDNIGHT_SNACKS;
    }
}
