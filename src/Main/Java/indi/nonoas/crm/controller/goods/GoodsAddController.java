package indi.nonoas.crm.controller.goods;

import java.net.URL;
import java.util.ResourceBundle;

import indi.nonoas.crm.view.alert.MyAlert;
import indi.nonoas.crm.bean.GoodsBean;
import indi.nonoas.crm.dao.GoodsDao;
import indi.nonoas.crm.config.ImageSrc;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class GoodsAddController implements Initializable {
	
	private final GoodsDao goodsDao = GoodsDao.getInstance();

	private Tab parentTab;
	@FXML
    private TextField tf_unit;
    @FXML
    private TextField tf_puchasPrice;
    @FXML
    private TextField tf_quantity;
    @FXML
    private TextField tf_minDiscount;
    @FXML
    private TextField tf_commission;
    @FXML
    private Button btn_upload;
    @FXML
    private CheckBox chc_isClose;
    @FXML
    private TextField tf_commissionRate;
    @FXML
    private ComboBox<String> cbb_type;
    @FXML
    private TextField tf_sellPrice;
    @FXML
    private HBox hbox_root;
    @FXML
    private TextField tf_name;
    @FXML
    private TextField tf_id;
    @FXML
    private ImageView img_photo;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// 设置初始图片
		img_photo.setImage(new Image(ImageSrc.PHOTO_PATH));
		//初始化CombBox
		cbb_type.getItems().addAll("产品类","服务类");
		cbb_type.setValue("产品类");
	}

	@FXML
	private void uploadPhoto() {
		
	}

	@FXML
	private void cancelInfo() {
		if (chc_isClose.isSelected()) { // 如果选择了提交后关闭，则关闭当前tab
			TabPane tabPane = parentTab.getTabPane();
			tabPane.getTabs().remove(parentTab);
			return;
		}
		tf_sellPrice.setText("");
		tf_quantity.setText("");
		tf_unit.setText("");
		tf_puchasPrice.setText("");
		tf_minDiscount.setText("");
		tf_commissionRate.setText("");
		tf_commission.setText("");
		tf_name.setText("");
		tf_id.setText("");
	}

	@FXML
	private void commitIfo() {
		if (!isCommitable())
			return;
		GoodsBean bean=new GoodsBean();
		bean.setId(tf_id.getText().trim());
		bean.setName(tf_name.getText().trim());
		
		String sp=tf_sellPrice.getText().trim();	//预售单价
		bean.setSell_price(Double.parseDouble(sp));
		
		String pp=tf_puchasPrice.getText().trim();	//进货单价
		double pur_price=pp.equals("")?0:Double.parseDouble(pp);
		bean.setPurchase_price(pur_price);
		
		String quan=tf_quantity.getText().trim();	//初始库存
		double quantity=quan.equals("")?0:Double.parseDouble(quan);
		bean.setQuantity(quantity);
		
		String md=tf_minDiscount.getText().trim();	//最小折扣
		double min_dis=md.equals("")?0:Double.parseDouble(md);
		bean.setMin_discount(min_dis);
		
		String de=tf_commission.getText().trim();	//提成金额
		double comison=de.equals("")?0:Double.parseDouble(de);
		bean.setDeduction(comison);
		
		String deRate=tf_commissionRate.getText().trim();	//提成比例
		double com_rate=deRate.equals("")?0:Double.parseDouble(deRate);
		bean.setDeduction_rate(com_rate);
		
		bean.setBase_unit(tf_unit.getText().trim());	//计量单位
		
		bean.setType(cbb_type.getValue());
		
//		bean.setPhoto(photo);
		goodsDao.insertInfo(bean);
		
		if (chc_isClose.isSelected()) { // 如果选择了提交后关闭，则关闭当前tab
			TabPane tabPane = parentTab.getTabPane();
			tabPane.getTabs().remove(parentTab);
		}
	}

	/**
	 * 判断是否可以提交信息
	 * 
	 * @return 可以为true，不可用为false
	 */
	private boolean isCommitable() {
		String id = tf_id.getText().trim(); // 编号
		String name = tf_name.getText().trim(); // 名称
		String sellPrice = tf_sellPrice.getText().trim(); // 预售单价
		String level = cbb_type.getValue(); // 商品类型
		if (id.equals("") || name.equals("") || sellPrice.equals("") || level.equals("")) {
			new MyAlert(AlertType.WARNING, "商品编号、商品名称、预售单价、所属类别不能为空！").show();
			return false;
		}
		return true;
	}

	/** 通过外界传递当前的tab引用 */
	public void setPane(Tab tab) {
		this.parentTab = tab;
	}

}
