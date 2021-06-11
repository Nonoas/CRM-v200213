package indi.nonoas.crm.service.impl;

import indi.nonoas.crm.dao.PackageMapper;
import indi.nonoas.crm.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : Nonoas
 * @time : 2021-06-11 15:18
 */
@Service("PackageServiceImpl")
public class PackageServiceImpl implements PackageService {

    @Autowired
    private PackageMapper pkgMapper;

    @Override
    public void deleteById(String id) {
        pkgMapper.deleteById(id);
    }
}
