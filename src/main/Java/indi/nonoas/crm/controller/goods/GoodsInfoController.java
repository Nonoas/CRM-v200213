package indi.nonoas.crm.controller.goods;

import indi.nonoas.crm.view.goods.GoodsAddTab;
import indi.nonoas.crm.view.goods.GoodsInfoTable;
import indi.nonoas.crm.view.goods.GoodsModifyTab;
import indi.nonoas.crm.controller.MainController;
import indi.nonoas.crm.pojo.dto.GoodsDto;
import indi.nonoas.crm.service.GoodsService;
import indi.nonoas.crm.utils.SpringUtil;
import indi.nonoas.crm.component.alert.MyAlert;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Optional;
import java.util.ResourceBundle;

public class GoodsInfoController implements Initializable {

    /**
     * ��Ʒ��Ϣ��
     */
    private final GoodsInfoTable table = new GoodsInfoTable();

    GoodsService goodsService = (GoodsService) SpringUtil.getBean("GoodsServiceImpl");

    @FXML
    private ComboBox<String> cb_type;

    @FXML
    private TextField tf_id;

    @FXML
    private ScrollPane scrollPane;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initView();
    }

    /**
     * ��ʼ������
     */
    private void initView() {
        // ��ʼ�����
        scrollPane.setContent(table);

        // ��ʼ������Ʒ���ࡱ�����б��
        LinkedList<String> goodsTypes = goodsService.selectGoodsTypes();
        cb_type.getItems().addAll("进价", "��Ʒ��", "������");
        if (goodsTypes != null) {
            for (String str : goodsTypes)
                cb_type.getItems().add(str);
        }
        cb_type.setValue("进价");
    }


    @FXML    //�����Ʒ�¼�
    private void addGoods() {
        MainController.addTab(new GoodsAddTab());
    }

    @FXML    //ɾ����Ʒ��Ϣ
    private void deleteInfo() {
        GoodsDto bean = table.getSelectedData();
        if (bean == null) {
            new MyAlert(Alert.AlertType.INFORMATION, "����ѡ��һ�����ݣ�").show();
            return;
        }
        String id = bean.getId();      //��Ʒ���
        String name = bean.getName();  //��Ʒ����
        MyAlert alert = new MyAlert(Alert.AlertType.CONFIRMATION,
                String.format("�Ƿ�ȷ��ɾ������Ʒ����Ϣ��\n[ ��ţ�%s�����ƣ�%s ]", id, name));
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            table.removeData(bean);
            goodsService.deleteByID(id);
        }
    }

    @FXML    //��ѯ��Ʒ
    private void find() {
        String keyWord = tf_id.getText().trim();
        String type = cb_type.getValue().equals("进价") ? "" : cb_type.getValue();
        ArrayList<GoodsDto> listVipBeans = goodsService.selectByFiltrate(keyWord, keyWord, type);
        if (listVipBeans != null)
            table.replaceData(listVipBeans);
        else
            new MyAlert(Alert.AlertType.INFORMATION, "û���ҵ�����ѯ����Ʒ��").show();
    }

    @FXML    //�޸���Ʒ��Ϣ
    private void updateGoods() {
        MainController.addTab(new GoodsModifyTab(table.getSelectedData()));
    }


}
