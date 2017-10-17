package test;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2017/6/27 0027.
 */
public class time {
    public static void main(String[] args) {
//        payReqDto.getAmount().multiply(new BigDecimal(100))
       BigDecimal a =  new BigDecimal(3);

       BigDecimal b = a.multiply(new BigDecimal(7));

        System.out.println(b);
    }
}
