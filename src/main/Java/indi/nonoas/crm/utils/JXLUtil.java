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
     * @param title       表格标题
     * @param listContent 列表内容
     * @param os          输出流
     * @return 成功则返回true，失败返回false
     */
    public static boolean exportExcel(String[] title, String[] fieldNames, List<Object> listContent, OutputStream os) {

        // 以下开始输出到EXCEL
        try {

            /* **********创建工作簿************ */
            WritableWorkbook workbook = Workbook.createWorkbook(os);

            /* * **********创建工作表************ */

            WritableSheet sheet = workbook.createSheet("Sheet1", 0);

            /* * **********设置纵横打印（默认为纵打）、打印纸***************** */
            jxl.SheetSettings sheetSet = sheet.getSettings();
            sheetSet.setProtected(false);

            /* * ************设置单元格字体************** */
            WritableFont NormalFont = new WritableFont(WritableFont.ARIAL, 10);
            WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);

            // 用于标题居中
            WritableCellFormat wcf_title = new WritableCellFormat(BoldFont);
            wcf_title.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
            wcf_title.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
            wcf_title.setAlignment(Alignment.CENTRE); // 文字水平对齐
            wcf_title.setWrap(false); // 文字是否换行

            // 用于正文居左
            WritableCellFormat wcf_text = new WritableCellFormat(NormalFont);
            wcf_text.setBorder(Border.NONE, BorderLineStyle.THIN); // 线条
            wcf_text.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
            wcf_text.setAlignment(Alignment.CENTRE); // 文字水平对齐
            wcf_text.setWrap(false); // 文字是否换行

            /* * ***************EXCEL第一行列标题********************* */
            for (int i = 0; i < title.length; i++) {
                sheet.addCell(new Label(i, 0, title[i], wcf_title));
            }
            /* * ***************EXCEL正文数据********************* */
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
            /* * **********将以上缓存中的内容写到EXCEL文件中******** */
            workbook.write();
            /* * *********关闭文件************* */
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
