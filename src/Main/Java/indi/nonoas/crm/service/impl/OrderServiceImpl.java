package indi.nonoas.crm.service.impl;

import indi.nonoas.crm.beans.vo.OrderRecordVO;
import indi.nonoas.crm.dao.OrderMapper;
import indi.nonoas.crm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author : Nonoas
 * @time : 2020-08-06 12:00
 */
@Service("OrderServiceImpl")
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper odrMapper;

    @Override
    public List<OrderRecordVO> selectGdsOrds() {
        return odrMapper.selectGdsOrds();
    }

    @Override
    public void delete365dAgo() {
        odrMapper.delete365dAgo();
    }


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


    @Autowired
    public void setOdrMapper(OrderMapper odrMapper) {
        this.odrMapper = odrMapper;
    }

}
