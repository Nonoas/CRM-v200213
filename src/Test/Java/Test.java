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



    public static String change32To10(String num) {
        int f=32;
        int t=10;
        return new java.math.BigInteger(num, f).toString(t);
    }

}
