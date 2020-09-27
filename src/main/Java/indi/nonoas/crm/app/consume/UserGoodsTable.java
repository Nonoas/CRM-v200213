package indi.nonoas.crm.app.consume;

import indi.nonoas.crm.beans.GoodsBean;
import indi.nonoas.crm.beans.UserGoods;
import indi.nonoas.crm.beans.UserBean;
import indi.nonoas.crm.beans.vo.UserGoodsVO;
import indi.nonoas.crm.dao.my_orm_dao.GoodsDao;
import indi.nonoas.crm.service.UsrGdsService;
import indi.nonoas.crm.utils.SpringUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import per.nonoas.delegate.Event;
import per.nonoas.delegate.EventHandler;

import java.util.ArrayList;
import java.util.List;


/**
 * @author : Nonoas
 * @time : 2020-08-07 15:45
 */
public class UserGoodsTable extends TableView<UserGoodsVO> {

    private final UsrGdsService usrGdsService = (UsrGdsService) SpringUtil.getBean("UsrGdsServiceImpl");
    private final ObservableList<UserGoodsVO> obList = FXCollections.observableArrayList();
    private final ObservableList<TableColumn<UserGoodsVO, ?>> columns = getColumns();
    private UserBean vipBean;
    private final EventHandler eventHandler = new EventHandler();
    private UserGoods selectBean;
    private final Label phd_noUser = new Label("尚未选择会员");
    private final Label phd_noData = new Label("该用户没有任何商品");

    private final TableColumn<UserGoodsVO, String> col_goodsID = new TableColumn<>("商品编号");
    private final TableColumn<UserGoodsVO, String> col_goodsName = new TableColumn<>("商品名称");
    private final TableColumn<UserGoodsVO, Number> col_amount = new TableColumn<>("剩余数量");
    private final TableColumn<UserGoodsVO, String> col_type = new TableColumn<>("商品类型");


    public UserGoodsTable() {
        setItems(obList);
        setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);
        setPlaceholder(phd_noUser);
        initColumn();
        getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectBean = voToBean(newValue);
            if (newValue != null) {
                eventHandler.execute();
            }
        });
    }

    @SuppressWarnings("unchecked")
    private void initColumn() {

        col_goodsID.setCellValueFactory(param -> param.getValue().idProperty());
        col_goodsName.setCellValueFactory(param -> param.getValue().nameProperty());
        col_amount.setCellValueFactory(param -> param.getValue().amountProperty());
        col_type.setCellValueFactory(param -> param.getValue().typeProperty());

        columns.addAll(col_goodsID, col_goodsName, col_amount, col_type);
    }

    public void addBean(UserGoods bean) {

    }

    public UserGoods getSelectBean() {
        return selectBean;
    }

    public void showAllData() {
        obList.clear();
        List<UserGoodsVO> vos = getUserGoodsVOs();
        if (vos == null)
            return;
        obList.addAll(vos);
    }

    /**
     * 查询所有 用户-商品
     *
     * @return 用户-商品列表,查询为空则为null
     */
    private List<UserGoods> getUserGoods() {
        String userID = vipBean.getId();
        return usrGdsService.selectByUser(userID);
    }

    /**
     * 将bean转为VO
     *
     * @param bean 将bean转为VO
     * @return VO
     */
    private UserGoodsVO beanToVO(UserGoods bean) {
        if (bean == null) return null;
        GoodsBean goodsBean = GoodsDao.getInstance().selectById(bean.getGoodsId());
        UserGoodsVO vo = new UserGoodsVO();
        vo.setId(goodsBean.getId());
        vo.setName(goodsBean.getName());
        vo.setAmount(bean.getAmount());
        vo.setType(goodsBean.getType());
        return vo;
    }

    /**
     * 将vo转为bean
     *
     * @param vo vo
     * @return bean
     */
    private UserGoods voToBean(UserGoodsVO vo) {
        if (vo == null) return null;
        UserGoods bean = new UserGoods();
        bean.setUserId(vipBean.getId());
        bean.setGoodsId(vo.getId());
        bean.setAmount(vo.getAmount());
        return bean;
    }

    private List<UserGoodsVO> getUserGoodsVOs() {
        List<UserGoods> userGoodsList = getUserGoods();
        if (userGoodsList == null) return null;
        List<UserGoodsVO> vos = new ArrayList<>(userGoodsList.size());
        for (UserGoods userGoods : userGoodsList) {
            vos.add(beanToVO(userGoods));
        }
        return vos;
    }

    public void addEvent(Event event) {
        eventHandler.addEvent(event);
    }


    public void setVipBean(UserBean vipBean) {
        this.vipBean = vipBean;
        setEmptyText();
        showAllData();
    }

    /**
     * 设置表格为空时的内容
     */
    private void setEmptyText() {
        if (vipBean == UserBean.SANKE)
            setPlaceholder(phd_noUser);
        else
            setPlaceholder(phd_noData);
    }


}
