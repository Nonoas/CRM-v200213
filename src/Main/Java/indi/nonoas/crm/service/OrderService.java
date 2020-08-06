package indi.nonoas.crm.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author : Nonoas
 * @time : 2020-08-06 12:00
 */
public class OrderService {

    /**
     * 生成商品订单号
     *
     * @return 商品订单号
     */
    public static synchronized String goodsOrderNum() {
        final String prefix = "SP";
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return prefix + dtf.format(dateTime);
    }

    /**
     * 生成商品订单号
     *
     * @return 商品订单号
     */
    public static synchronized String packageOrderNum() {
        final String prefix = "TC";
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return prefix + dtf.format(dateTime);
    }

}
