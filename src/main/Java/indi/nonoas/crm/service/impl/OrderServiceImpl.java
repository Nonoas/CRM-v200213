package indi.nonoas.crm.service.impl;

import indi.nonoas.crm.pojo.*;
import indi.nonoas.crm.pojo.dto.GoodsDto;
import indi.nonoas.crm.pojo.dto.VipInfo;
import indi.nonoas.crm.pojo.vo.OrderRecordVO;
import indi.nonoas.crm.dao.GoodsMapper;
import indi.nonoas.crm.dao.OrderMapper;
import indi.nonoas.crm.dao.VipMapper;
import indi.nonoas.crm.dao.UsrGdsMapper;
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

    private OrderMapper odrMapper;

    private UsrGdsMapper ugMapper;

    private VipMapper vipMapper;

    private GoodsMapper goodsMapper;

    @Override
    public List<OrderRecordVO> selectGdsOrds() {
        return odrMapper.selectGdsOrds();
    }

    @Override
    public void delete365dAgo() {
        odrMapper.delete365dAgo();
    }

    @Override
    public void placeGoodsOrder(OrderBean order,
                                List<OrderDetailBean> orderDetails,
                                List<UserGoods> userGoods,
                                List<GoodsDto> goodsBeans,
                                VipInfo vipBean) {
        //��������
        odrMapper.insertOrder(order);
        //������������
        odrMapper.insertOrderDetails(orderDetails);

        //�����ɢ��������������
        if (vipBean != VipInfo.SANKE) {
            //�û�����
            vipMapper.updateInfo(vipBean);
            //�û���Ʒ
            ugMapper.replaceUserGoods(userGoods);
        }
        //��Ʒ����
        goodsMapper.updateGoodsAmount(goodsBeans);
    }


    /**
     * ������Ʒ������
     *
     * @return ��Ʒ������
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
     * ������Ʒ������
     *
     * @return ��Ʒ������
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


    //===========================================================================
    //                            setterע��
    //===========================================================================

    @Autowired
    public void setOdrMapper(OrderMapper odrMapper) {
        this.odrMapper = odrMapper;
    }

    @Autowired
    public void setUgMapper(UsrGdsMapper ugMapper) {
        this.ugMapper = ugMapper;
    }

    @Autowired
    public void setUserMapper(VipMapper vipMapper) {
        this.vipMapper = vipMapper;
    }

    @Autowired
    public void setGoodsMapper(GoodsMapper goodsMapper) {
        this.goodsMapper = goodsMapper;
    }
}
