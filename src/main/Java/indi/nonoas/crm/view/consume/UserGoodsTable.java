package indi.nonoas.crm.view.consume;

import indi.nonoas.crm.pojo.dto.GoodsDto;
import indi.nonoas.crm.pojo.UserGoods;
import indi.nonoas.crm.pojo.dto.VipInfoDto;
import indi.nonoas.crm.pojo.vo.UserGoodsVO;
import indi.nonoas.crm.service.GoodsService;
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
    private final GoodsService goodsService = (GoodsService) SpringUtil.getBean("GoodsServiceImpl");
    private final ObservableList<UserGoodsVO> obList = FXCollections.observableArrayList();
    private final ObservableList<TableColumn<UserGoodsVO, ?>> columns = getColumns();
    private VipInfoDto vipBean;
    private final EventHandler eventHandler = new EventHandler();
    private UserGoods selectBean;
    private final Label phd_noUser = new Label("��δѡ���Ա");
    private final Label phd_noData = new Label("���û�û���κ���Ʒ");

    private final TableColumn<UserGoodsVO, String> col_goodsID = new TableColumn<>("��Ʒ���");
    private final TableColumn<UserGoodsVO, String> col_goodsName = new TableColumn<>("��Ʒ����");
    private final TableColumn<UserGoodsVO, Number> col_amount = new TableColumn<>("ʣ������");
    private final TableColumn<UserGoodsVO, String> col_type = new TableColumn<>("��Ʒ����");


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
     * ��ѯ���� �û�-��Ʒ
     *
     * @return �û�-��Ʒ�б�,��ѯΪ����Ϊnull
     */
    private List<UserGoods> getUserGoods() {
        String userID = vipBean.getId();
        return usrGdsService.selectByUser(userID);
    }

    /**
     * ��beanתΪVO
     *
     * @param bean ��beanתΪVO
     * @return VO
     */
    private UserGoodsVO beanToVO(UserGoods bean) {
        if (bean == null) return null;
        GoodsDto goodsBean = goodsService.selectById(bean.getGoodsId());
        UserGoodsVO vo = new UserGoodsVO();
        vo.setId(goodsBean.getId());
        vo.setName(goodsBean.getName());
        vo.setAmount(bean.getAmount());
        vo.setType(goodsBean.getType());
        return vo;
    }

    /**
     * ��voתΪbean
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


    public void setVipBean(VipInfoDto vipBean) {
        this.vipBean = vipBean;
        setEmptyText();
        showAllData();
    }

    /**
     * ���ñ��Ϊ��ʱ������
     */
    private void setEmptyText() {
        if (vipBean == VipInfoDto.SANKE)
            setPlaceholder(phd_noUser);
        else
            setPlaceholder(phd_noData);
    }


}
