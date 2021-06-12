package indi.nonoas.crm.dao;

import indi.nonoas.crm.pojo.dto.VipInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * @author : Nonoas
 * @time : 2020-09-03 21:30
 */
@Repository
public interface VipMapper {
    /**
     * 通过会员卡号查询会员信息
     *
     * @param id 会员卡号
     * @return VIPBean对象
     */
    VipInfo getUserById(String id);

    /**
     * 通过卡号或姓名精确查询
     *
     * @param id   会员卡号
     * @param name 会员姓名
     * @return VipBean
     */
    VipInfo getInfoByIdOrName(@Param("id") String id,
                              @Param("name") String name);

    /**
     * 查询所有会员信息
     *
     * @return VIPBean的ArrayList集合
     */
    ArrayList<VipInfo> selectAllUser();

    /**
     * 多条件筛选信息
     *
     * @param id         会员卡号
     * @param name       姓名
     * @param card_level 会员等级
     * @return VIPBean的ArrayList对象, 没有查询结果时为null
     */
    ArrayList<VipInfo> selectByFiltrate(@Param("id") String id,
                                        @Param("name") String name,
                                        @Param("card_level") String card_level);

    /**
     * 过滤器查找
     *
     * @param id       卡号
     * @param name     姓名
     * @param level    会员等级
     * @param dateFrom 加入时间范围（起始）
     * @param dateTo   加入时间范围（结束）
     * @return VIPBean的ArrayList对象, 没有查询结果时为null
     */
    ArrayList<VipInfo> selectByDateFiltrate(@Param("id") String id,
                                            @Param("name") String name,
                                            @Param("level") String level,
                                            @Param("dateFrom") String dateFrom,
                                            @Param("dateTo") String dateTo);


    /**
     * 插入会员信息
     *
     * @param vipBean VIPBean对象
     */
    void insertInfo(VipInfo vipBean);

    /**
     * 通过卡号删除会员信息
     *
     * @param id 会员卡号
     */
    void deleteByID(String id);

    /**
     * 更新用户信息
     *
     * @param vipBean VIPBean对象
     */
    void updateInfo(VipInfo vipBean);

}
