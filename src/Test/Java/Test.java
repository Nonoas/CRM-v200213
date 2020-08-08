import indi.nonoas.crm.utils.DBOpener;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author : Nonoas
 * @time : 2020-08-03 16:33
 */
public class Test {

    static String underlineToBigCamel(String str) {
        String[] strs = str.split("_");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strs.length; i++) {
            strs[i] = strs[i].substring(0, 1).toUpperCase() + strs[i].substring(1);
            sb.append(strs[i]);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(underlineToBigCamel("user_id"));
    }
}
