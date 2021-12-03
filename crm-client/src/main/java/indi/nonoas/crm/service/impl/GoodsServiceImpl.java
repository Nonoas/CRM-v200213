package indi.nonoas.crm.service.impl;

import indi.nonoas.crm.dao.GoodsMapper;
import indi.nonoas.crm.dao.PkgContentMapper;
import indi.nonoas.crm.pojo.dto.GoodsDto;
import indi.nonoas.crm.service.GoodsService;
import java.util.ArrayList;
import java.util.LinkedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : Nonoas
 * @time : 2020-09-01 20:34
 */
@Service("GoodsServiceImpl")
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private PkgContentMapper pkgContentMapper;

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

    /**
     * 判断是否有套餐包含商品
     *
     * @param dto 当前商品
     * @return 包含：true
     */
    @Override
    public boolean pkgContains(GoodsDto dto) {
        return null != pkgContentMapper.selectIdByGoodsId(dto.getId());
    }


}
