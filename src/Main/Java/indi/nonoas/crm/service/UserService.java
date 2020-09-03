package indi.nonoas.crm.service;

import indi.nonoas.crm.beans.UserBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * @author : Nonoas
 * @time : 2020-09-04 1:34
 */
@Transactional
public interface UserService {
    /**
     * 自动生成会员卡号
     *
     * @return 32进制数 会员卡号
     */
    String generateVipID();

    /**
     * 通过卡号或姓名精确查询
     *
     * @param id   会员卡号
     * @param name 会员姓名
     * @return VipBean
     */
    UserBean getInfoByIdOrName(String id, String name);

    /**
     * 查询所有会员信息
     *
     * @return VIPBean的ArrayList集合
     */
    ArrayList<UserBean> selectAllUser();

    /**
     * 多条件筛选信息
     *
     * @param id         会员卡号
     * @param name       姓名
     * @param card_level 会员等级
     * @return VIPBean的ArrayList对象, 没有查询结果时为null
     */
    ArrayList<UserBean> selectByFiltrate(String id, String name, String card_level);

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
    ArrayList<UserBean> selectByDateFiltrate(String id, String name, String level, String dateFrom, String dateTo);


    /**
     * 插入会员信息
     *
     * @param vipBean VIPBean对象
     */
    void insertInfo(UserBean vipBean);

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
    void updateInfo(UserBean vipBean);
}
