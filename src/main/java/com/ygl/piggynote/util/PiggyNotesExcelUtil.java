package com.ygl.piggynote.util;

import com.ygl.piggynote.bean.DailyRecordBean;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

/**
 * Created by yanggavin on 15/5/5.
 */
public class PiggyNotesExcelUtil {
    private static final String DEFAULT_ENCODING = "UTF-8";
    private static WritableWorkbook excelWorkBook;

    public static void writeAllRecordsToExcelAndResponseFile(HttpServletResponse response, String userName, List<DailyRecordBean> allRecords){
        try {
            OutputStream os = response.getOutputStream();
            response.reset();
            response.setContentType("multipart/form-data");
            response.setCharacterEncoding(DEFAULT_ENCODING);
            response.setHeader("Content-Disposition", "attachment;filename=" +
                    URLEncoder.encode(getExcelFileName(userName), DEFAULT_ENCODING));
            PiggyNotesExcelUtil.createExcelFile4AllUserRecordsAndResponse(os, allRecords);
        }catch(Exception e){

        }
    }

    private static void createExcelFile4AllUserRecordsAndResponse(OutputStream os, List<DailyRecordBean> records) throws WriteException, IOException {
        excelWorkBook = Workbook.createWorkbook(os);
        try {
            WritableSheet sheet = createWorkBookAndWriteDataTitle(os);
            writeAllRecordsToExcel(sheet, records);
            excelWorkBook.write();
            excelWorkBook.close();
            os.close();
        }catch(Exception e){

        }
    }

    private static WritableSheet createWorkBookAndWriteDataTitle(OutputStream os) throws WriteException, IOException {
        WritableSheet sheet = excelWorkBook.createSheet("全部账目", 0);
        sheet.addCell(new Label(0, 0, "序号"));
        sheet.addCell(new Label(1, 0, "类型"));
        sheet.addCell(new Label(2, 0, "金额"));
        sheet.addCell(new Label(3, 0, "日期"));
        sheet.addCell(new Label(4, 0, "备注"));
        return sheet;
    }

    private static void writeAllRecordsToExcel(WritableSheet sheet, List<DailyRecordBean> records) throws WriteException {
        int rowIndex = 0;
        String moneyType = "";
        String amount = "";
        String createDate = "";
        DailyRecordBean drb = null;
        for (int i = 0; i < records.size(); i++) {
            drb = records.get(i);
            rowIndex++;
            moneyType = drb.getMoneyType().getDes();
            amount = drb.getAmount() + "";
            createDate = DateUtil.getShortDateStr(drb.getCreateDate());
            sheet.addCell(new Label(0, rowIndex, rowIndex + ""));
            sheet.addCell(new Label(1, rowIndex, moneyType));
            sheet.addCell(new Label(2, rowIndex, amount));
            sheet.addCell(new Label(3, rowIndex, createDate));
            sheet.addCell(new Label(4, rowIndex, drb.getRemark()));
        }
    }

    private static String getExcelFileName(String userName){
        return String.format("piggynotes_%s_%s.xls", DateUtil.getShortDateStr(new Date()), userName);
    }
}
