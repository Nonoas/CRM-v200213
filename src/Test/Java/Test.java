import indi.nonoas.crm.service.UsrGdsOdrService;
import indi.nonoas.crm.utils.SpringUtil;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author : Nonoas
 * @time : 2020-08-03 16:33
 */
@SpringBootTest(classes = indi.nonoas.crm.ApplicationStarter.class)
public class Test {

    @org.junit.jupiter.api.Test
    void test() {
        UsrGdsOdrService serviceImpl = (UsrGdsOdrService) SpringUtil.getBean("UsrGdsOdrServiceImpl");
        System.out.println(serviceImpl.selectUserGoodsOrder());
    }

}
