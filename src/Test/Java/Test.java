import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author : Nonoas
 * @time : 2020-08-03 16:33
 */
public class Test {
    public static void main(String[] args) throws WriteException, IOException {
        // 创建workbook对象                                    输出流
        OutputStream outputStream=new FileOutputStream("D:/asdasd.xls");
        WritableWorkbook workbook = Workbook.createWorkbook(outputStream);

// 创建一张表                                表名    下标
        WritableSheet sheet1 = workbook.createSheet("sheet1", 0);

//        新建单元格    列号 行号 单元格的值
        Label gender = new Label(0, 0, "性别");

// 添加单元格
        sheet1.addCell(gender);

// 将文件写出
        workbook.write();

// 关闭
        workbook.close();
    }
}
