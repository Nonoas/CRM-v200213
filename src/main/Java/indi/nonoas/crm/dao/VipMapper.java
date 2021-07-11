package indi.nonoas.crm.dao;

import indi.nonoas.crm.pojo.dto.VipInfoDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * @author : Nonoas
 * @time : 2020-09-03 21:30
 */
@Repository
public interface VipMapper {

    VipInfoDto getUserById(String id);

    VipInfoDto getInfoByIdOrName(@Param("id") String id,
                                 @Param("name") String name);

    ArrayList<VipInfoDto> selectAllUser();

    /**
     * 过滤查询会员信息
     *
     * @param id         卡号
     * @param name       姓名
     * @param card_level 会员等级
     * @return 会员信息，可为 null
     */
    ArrayList<VipInfoDto> selectByFiltrate(@Param("id") String id,
                                           @Param("name") String name,
                                           @Param("card_level") String card_level);

    /**
     * 筛选查询会员信息
     *
     * @param id       卡号
     * @param name     姓名
     * @param level    会员等级
     * @param dateFrom 入会起始日期
     * @param dateTo   入会结束日期
     * @return 会员信息集合，可为 null
     */
    ArrayList<VipInfoDto> selectByDateFiltrate(@Param("id") String id,
                                               @Param("name") String name,
                                               @Param("level") String level,
                                               @Param("dateFrom") String dateFrom,
                                               @Param("dateTo") String dateTo);


    /**
     * 插入会员信息
     *
     * @param vipBean 会员信息
     */
    void insertInfo(VipInfoDto vipBean);

    /**
     * 删除会员信息
     *
     * @param id 会员id
     */
    void deleteByID(String id);

    /**
     * 更新会员信息
     *
     * @param vipBean 会员信息
     */
    void updateInfo(VipInfoDto vipBean);

}
