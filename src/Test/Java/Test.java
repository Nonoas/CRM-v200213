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
    public static void main(String[] args) {
        String sql1 = "insert into user_goods values('20200029','0001',1)";
        String sql2 = "insert into user_good values('20200029','0002',2)";
        Connection conn = DBOpener.getConnection();
        PreparedStatement ps1;
        PreparedStatement ps2;
        try {
            conn.setAutoCommit(false);
            ps1 = conn.prepareStatement(sql1);
            ps1.execute();
            ps2 = conn.prepareStatement(sql2);
            ps2.execute();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
