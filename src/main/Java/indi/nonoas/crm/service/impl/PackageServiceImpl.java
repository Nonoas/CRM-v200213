package indi.nonoas.crm.service.impl;

import indi.nonoas.crm.dao.PackageMapper;
import indi.nonoas.crm.pojo.PackageDto;
import indi.nonoas.crm.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : Nonoas
 * @time : 2021-06-11 15:18
 */
@Service("PackageServiceImpl")
public class PackageServiceImpl implements PackageService {

    @Autowired
    private PackageMapper pkgMapper;

    @Override
    public List<PackageDto> selectAll() {
        return pkgMapper.selectList(null);
    }

    @Override
    public PackageDto selectById(String id) {
        return pkgMapper.selectById(id);
    }

    @Override
    public void insert(PackageDto dto) {
        pkgMapper.insert(dto);
    }

    @Override
    public void update(PackageDto dto) {
        pkgMapper.updateById(dto);
    }

    @Override
    public void deleteById(String id) {
        pkgMapper.deleteById(id);
    }

    @Override
    public List<PackageDto> findByFilter(String id, String name, double money1, double money2) {
        return pkgMapper.findByFilter(id, name, money1, money2);
    }
}
