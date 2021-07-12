package indi.nonoas.crm.view;

import de.felixroske.jfxsupport.AbstractFxmlView;
import de.felixroske.jfxsupport.FXMLView;

import java.util.ArrayList;

/**
 * @author : Nonoas
 * @time : 2020-08-10 23:47
 */
@FXMLView(value = "/fxml/welcome.fxml",
        css = {"/css/bootstrap3.css"})
public class WelcomeView extends AbstractFxmlView {

    public WelcomeView() {
    }

    public static void main(String[] args) {
        ArrayList<Integer> integers = new ArrayList<>(1000);

        for (int i = 0; i < 1000000; i++) {
            integers.add(i);
        }

        long l = System.currentTimeMillis();

        for (Integer i : integers) {
            System.out.println(i);
        }

        long l2 = System.currentTimeMillis();

        integers.forEach(System.out::println);

        System.out.println((System.currentTimeMillis() - l2 - l2 + l) + "毫秒");
    }

}
