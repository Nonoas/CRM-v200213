package indi.nonoas.crm.view.vip;

import indi.nonoas.crm.component.progress.TableProgressIndicator;
import indi.nonoas.crm.component.table.ConditionTable;
import indi.nonoas.crm.pojo.dto.VipInfoDto;
import indi.nonoas.crm.service.VipService;
import indi.nonoas.crm.utils.SpringUtil;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户信息表
 *
 * @author Nonoas
 */
public class VipInfoTable extends ConditionTable<VipInfoDto> {

    private final Logger logger = Logger.getLogger(VipInfoTable.class);

    private static final String MENU_CODE = "VipInfo";

    private final VipService vipService = (VipService) SpringUtil.getBean("UserServiceImpl");

    private final ObservableList<VipInfoDto> items = getItems();


    public VipInfoTable(List<VipInfoDto> items) {
        super(MENU_CODE, items, VipInfoDto.class);
        this.items.addAll(items);
        showAllInfos();
    }


    /**
     * 显示所有会员信息
     */
    public void showAllInfos() {
        clearData();
        setPlaceholder(new TableProgressIndicator());

        Task<List<VipInfoDto>> task = new Task<List<VipInfoDto>>() {
            @Override
            protected List<VipInfoDto> call() {
                List<VipInfoDto> beans = vipService.selectAllUser();
                if (beans != null)
                    return beans;
                else
                    return new ArrayList<>(0);
            }
        };
        new Thread(task).start();
        task.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.size() != 0)
                items.addAll(newValue);
            else
                setPlaceholder(new Label("没有数据"));
        });
    }

    /**
     * 添加用户信息
     *
     * @param dto 用户信息
     */
    public void addBean(VipInfoDto dto) {
        items.add(dto);
    }

}
