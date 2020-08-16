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

    static void underlineToBigCamel() {
        Connection connection = DBOpener.getConnection();
        String sql = "insert into user_info (id) values(?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            for (int i = 0; i < 1000; i++) {
                ps.setString(1, 20200816 + i + "");
                ps.addBatch();
                System.out.println(i);
            }
            ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        underlineToBigCamel();
    }
}
