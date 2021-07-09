package indi.nonoas.crm.service.impl;

import indi.nonoas.crm.dao.PackageMapper;
import indi.nonoas.crm.dao.PkgContentMapper;
import indi.nonoas.crm.pojo.PackageContentDto;
import indi.nonoas.crm.pojo.PackageDto;
import indi.nonoas.crm.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author : Nonoas
 * @time : 2021-06-11 15:18
 */
@Service("PackageServiceImpl")
public class PackageServiceImpl implements PackageService {

    @Autowired
    private PackageMapper pkgMapper;

    @Autowired
    private PkgContentMapper pkgContentMapper;

    @Override
    public List<PackageDto> selectAll() {
        return pkgMapper.selectList(null);
    }

    @Override
    public PackageDto selectById(String id) {
        return pkgMapper.selectById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insert(PackageDto dto, List<PackageContentDto> pkgContents) {
        pkgMapper.insert(dto);
        pkgContentMapper.insertInfos(pkgContents);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(PackageDto dto, List<PackageContentDto> pkgGoods) {
        pkgMapper.updateById(dto);
        pkgContentMapper.deleteById(dto.getId());
        pkgContentMapper.insertInfos(pkgGoods);
    }

    @Override
    public void deleteById(String id) {
        pkgMapper.deleteById(id);
        pkgContentMapper.deleteById(id);
    }

    @Override
    public List<PackageDto> findByFilter(String id, String name, double money1, double money2) {
        return pkgMapper.findByFilter(id, name, money1, money2);
    }

    /**
     * Í¨¹ýÌ×²Íid²éÑ¯Ì×²ÍÄÚÈÝ
     *
     * @param pkgId Ì×²Íid
     * @return Ì×²ÍÄÚÈÝ£¬¿É¿Õ
     */
    @Override
    public List<PackageContentDto> listPkgContentByPkgId(String pkgId) {
        return pkgContentMapper.selectById(pkgId);
    }
}
