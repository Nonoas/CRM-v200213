package indi.nonoas.crm.utils;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.*;

import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.List;

/**
 * @author : Nonoas
 * @time : 2020-08-04 20:51
 */
public class JXLUtil {

    /**
     * @param title       ������
     * @param listContent �б�����
     * @param os          �����
     * @return �ɹ��򷵻�true��ʧ�ܷ���false
     */
    public static boolean exportExcel(String[] title, String[] fieldNames, List<Object> listContent, OutputStream os) {

        // ���¿�ʼ�����EXCEL
        try {

            /* **********进价��************ */
            WritableWorkbook workbook = Workbook.createWorkbook(os);

            /* * **********进价��************ */

            WritableSheet sheet = workbook.createSheet("Sheet1", 0);

            /* * **********�����ݺ��ӡ��Ĭ��Ϊ�ݴ򣩡���ӡֽ***************** */
            jxl.SheetSettings sheetSet = sheet.getSettings();
            sheetSet.setProtected(false);

            /* * ************���õ�Ԫ������************** */
            WritableFont NormalFont = new WritableFont(WritableFont.ARIAL, 10);
            WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);

            // ���ڱ������
            WritableCellFormat wcf_title = new WritableCellFormat(BoldFont);
            wcf_title.setBorder(Border.ALL, BorderLineStyle.THIN); // ����
            wcf_title.setVerticalAlignment(VerticalAlignment.CENTRE); // ���ִ�ֱ����
            wcf_title.setAlignment(Alignment.CENTRE); // ����ˮƽ����
            wcf_title.setWrap(false); // �����Ƿ���

            // �������ľ���
            WritableCellFormat wcf_text = new WritableCellFormat(NormalFont);
            wcf_text.setBorder(Border.NONE, BorderLineStyle.THIN); // ����
            wcf_text.setVerticalAlignment(VerticalAlignment.CENTRE); // ���ִ�ֱ����
            wcf_text.setAlignment(Alignment.CENTRE); // ����ˮƽ����
            wcf_text.setWrap(false); // �����Ƿ���

            /* * ***************EXCEL��һ���б���********************* */
            for (int i = 0; i < title.length; i++) {
                sheet.addCell(new Label(i, 0, title[i], wcf_title));
            }
            /* * ***************EXCEL进价********************* */
            int rIndex = 1;
            for (Object obj : listContent) {
                int cIndex = 0;
                for (String fieldName : fieldNames) {
                    Field field = obj.getClass().getDeclaredField(fieldName);
                    field.setAccessible(true);
                    Object val = field.get(obj);
                    if (val == null)
                        val = "";
                    sheet.addCell(new Label(cIndex, rIndex, val.toString(), wcf_text));
                    cIndex++;
                }
                rIndex++;
            }
            /* * **********�����ϻ����е�����д��EXCEL�ļ���******** */
            workbook.write();
            /* * *********�ر��ļ�************* */
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
