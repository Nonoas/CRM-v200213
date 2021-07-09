package indi.nonoas.crm.service;

import indi.nonoas.crm.pojo.PackageContentDto;
import indi.nonoas.crm.pojo.PackageDto;

import java.util.List;

/**
 * @author : Nonoas
 * @time : 2021-06-11 15:15
 */
public interface PackageService {

    /**
     * 查询所有项目信息
     *
     * @return 所有项目信息
     */
    List<PackageDto> selectAll();

    /**
     * 通过id查找项目信息
     *
     * @param id 项目id
     * @return 对应的项目信息bean类对象
     */
    PackageDto selectById(String id);

    /**
     * 插入一条项目信息
     *
     * @param bean PackageBean对象
     */
    void insert(PackageDto bean, List<PackageContentDto> pkgContents);

    /**
     * 更新套餐信息
     *
     * @param bean     套餐类
     * @param pkgGoods 套餐下的商品信息
     */
    void update(PackageDto bean, List<PackageContentDto> pkgGoods);

    void deleteById(String id);

    /**
     * 通过过滤查找项目信息
     *
     * @param id     编号
     * @param name   名称
     * @param money1 金钱下限
     * @param money2 金钱上限
     * @return 所有满足过滤条件的项目信息, 可以为null
     */
    List<PackageDto> findByFilter(String id, String name, double money1, double money2);

    /**
     * 通过套餐id查询套餐内容
     * @param pkgId 套餐id
     * @return 套餐内容，可空
     */
    List<PackageContentDto> listPkgContentByPkgId(String pkgId);

}
