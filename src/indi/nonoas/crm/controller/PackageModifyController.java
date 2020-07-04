package indi.nonoas.crm.controller;

import java.net.URL;
import java.util.ResourceBundle;

import indi.nonoas.crm.table.PackageContentTable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class PackageModifyController implements Initializable {

	private PackageContentTable pkgGoodsTable=new PackageContentTable();
	
	private Tab paretTab;
	@FXML
	private Button btn_upload;

	@FXML
	private TextField tf_other;

	@FXML
	private CheckBox chc_isClose;

	@FXML
	private HBox hbox_root;

	@FXML
	private TextField tf_money;

	@FXML
	private TextField tf_name;

	@FXML
	private ScrollPane sp_goods;

	@FXML
	private TextField tf_id;

	@FXML
	private TextField tf_integral;

	@FXML
	private ImageView img_photo;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		sp_goods.setContent(pkgGoodsTable);
	}

	@FXML
	void cancelInfo() {

	}

	@FXML
	void commitIfo() {
		
	}
	
	/** 传递当前tab的引用*/
	public void setPane(Tab tab) {
		this.paretTab=tab;
	}

    public void addGoods(ActionEvent actionEvent) {
    }

	public void deleteGoods(ActionEvent actionEvent) {
	}

	public void clearGoods(ActionEvent actionEvent) {
	}
}
