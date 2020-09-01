import indi.nonoas.crm.utils.SpringUtil;
import indi.nonoas.crm.service.impl.LoginServiceImpl;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author : Nonoas
 * @time : 2020-08-03 16:33
 */
@SpringBootTest(classes = indi.nonoas.crm.ApplicationStarter.class)
public class Test {

    @org.junit.jupiter.api.Test
    void test() {
        LoginServiceImpl serviceImpl = (LoginServiceImpl) SpringUtil.getBean("LoginServiceImpl");
        serviceImpl.verify("admin","admin");
    }

}
