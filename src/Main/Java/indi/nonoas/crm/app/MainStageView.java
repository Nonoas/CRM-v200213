package indi.nonoas.crm.app;

import de.felixroske.jfxsupport.AbstractFxmlView;
import de.felixroske.jfxsupport.FXMLView;
import indi.nonoas.crm.dao.OrderDao;
import org.apache.log4j.Logger;

/**
 * @author : Nonoas
 * @time : 2020-09-02 12:10
 */
@FXMLView(value = "/fxml/main.fxml", css = {"/css/application.css"})
public class MainStageView extends AbstractFxmlView {

    private final Logger logger = Logger.getLogger(MainStageView.class);

    public MainStageView() {
        startBackgroundTask();
    }

    private void startBackgroundTask() {
        //É¾³ý¾É¶©µ¥
        new Thread(() -> {
            OrderDao.getInstance().delete365DaysAgo();
            logger.debug("É¾³ýÒ»ÄêÇ°µÄ¶©µ¥");
        }).start();
    }
}
