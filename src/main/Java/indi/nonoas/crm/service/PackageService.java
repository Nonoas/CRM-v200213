package indi.nonoas.crm.service;

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
    void insert(PackageDto bean);

    /**
     * 更新一条项目信息
     *
     * @param bean PackageBean对象
     */
    void update(PackageDto bean);

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

}
