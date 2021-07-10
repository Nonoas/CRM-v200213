package indi.nonoas.crm.service.impl;

import indi.nonoas.crm.pojo.dto.GoodsDto;
import indi.nonoas.crm.dao.GoodsMapper;
import indi.nonoas.crm.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author : Nonoas
 * @time : 2020-09-01 20:34
 */
@Service("GoodsServiceImpl")
public class GoodsServiceImpl implements GoodsService {

    private GoodsMapper goodsMapper;

    @Override
    public GoodsDto selectById(String id) {
        return goodsMapper.selectById(id);
    }

    @Override
    public ArrayList<GoodsDto> selectAll() {
        return goodsMapper.selectAll();
    }

    @Override
    public ArrayList<GoodsDto> selectByFiltrate(String id, String name, String type) {
        return goodsMapper.selectByFiltrate(id, name, type);
    }

    @Override
    public void deleteByID(String id) {
        goodsMapper.deleteByID(id);
    }

    @Override
    public void insertInfo(GoodsDto bean) {
        goodsMapper.insertInfo(bean);
    }

    @Override
    public void update(GoodsDto goodsBean) {
        goodsMapper.update(goodsBean);
    }

    @Override
    public LinkedList<String> selectGoodsTypes() {
        return goodsMapper.selectGoodsTypes();
    }

    //===========================================================================
    //                            setterע��
    //===========================================================================
    @Autowired
    public void setGoodsMapper(GoodsMapper goodsMapper) {
        this.goodsMapper = goodsMapper;
    }
}
