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
        // ����workbook����                                    �����
        OutputStream outputStream=new FileOutputStream("D:/asdasd.xls");
        WritableWorkbook workbook = Workbook.createWorkbook(outputStream);

// ����һ�ű�                                ����    �±�
        WritableSheet sheet1 = workbook.createSheet("sheet1", 0);

//        �½���Ԫ��    �к� �к� ��Ԫ���ֵ
        Label gender = new Label(0, 0, "�Ա�");

// ��ӵ�Ԫ��
        sheet1.addCell(gender);

// ���ļ�д��
        workbook.write();

// �ر�
        workbook.close();
    }
}
