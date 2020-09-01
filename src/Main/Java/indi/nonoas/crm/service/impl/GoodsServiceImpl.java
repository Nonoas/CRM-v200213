package indi.nonoas.crm.service.impl;

import indi.nonoas.crm.beans.GoodsBean;
import indi.nonoas.crm.dao.GoodsMapper;
import indi.nonoas.crm.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author : Nonoas
 * @time : 2020-09-01 20:34
 */
@Service("GoodsServiceImpl")
public class GoodsServiceImpl implements GoodsService {

    private GoodsMapper goodsMapper;

    @Override
    public GoodsBean selectById(String id) {
        return goodsMapper.selectById(id);
    }

    @Override
    public ArrayList<GoodsBean> selectAll() {
        return goodsMapper.selectAll();
    }

    @Override
    public ArrayList<GoodsBean> selectByFiltrate(String id, String name, String type) {
        return goodsMapper.selectByFiltrate(id, name, type);
    }

    @Override
    public void deleteByID(String id) {
        goodsMapper.deleteByID(id);
    }

    @Override
    public void insertInfo(GoodsBean bean) {
        goodsMapper.insertInfo(bean);
    }

    @Override
    public void update(GoodsBean goodsBean) {
        goodsMapper.update(goodsBean);
    }

    //===========================================================================
    //                            setter×¢Èë
    //===========================================================================
    @Autowired
    public void setGoodsMapper(GoodsMapper goodsMapper) {
        this.goodsMapper = goodsMapper;
    }
}
