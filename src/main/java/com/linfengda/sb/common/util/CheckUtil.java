package com.linfengda.sb.common.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.netty.util.internal.MathUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.io.*;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 描述: 考勤excel工具
 *
 * @author linfengda
 * @create 2018-10-05 18:36
 */
@Slf4j
public class CheckUtil {
    /**
     * 默认日期格式
     */
    private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
    /**
     * 默认列宽
     */
    private static final int DEFAULT_COLUMN_WIDTH = 23;

    public static void main(String[] args) {
        try {
            getOverTimeExcel("C:\\Users\\SI-GZ-1134\\Desktop\\生产IT部考勤.xlsx");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void getOverTimeExcel(String filePath) throws Exception {
        log.info("解析报表["+ filePath +"]");

        // 读取excel文件
        InputStream is = null;
        try {
            is = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            return;
        }
        Workbook wb = new XSSFWorkbook(is);;
        // 解析excel文件
        JSONArray jsonArray = new JSONArray();
        Sheet Sheet1 = wb.getSheet("Sheet1");
        Iterator<Row> it = Sheet1.rowIterator();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        while (it.hasNext()) {
            Row row = it.next();
            // 下班时间大于8点为加班
            Date outTime = getDateCellValue(row.getCell(7));
            if (null == outTime) {
                continue;
            }
            calendar.setTime(outTime);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            if (hour >= 20) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", getStringCellValue(row.getCell(1)));
                jsonObject.put("department", getStringCellValue(row.getCell(2)));
                jsonObject.put("date", dateFormat.format(getDateCellValue(row.getCell(3))));
                jsonObject.put("week", getStringCellValue(row.getCell(4)));
                jsonObject.put("inTime", timeFormat.format(getDateCellValue(row.getCell(6))));
                jsonObject.put("outTime", timeFormat.format(getDateCellValue(row.getCell(7))));
                jsonArray.add(jsonObject);
            }
        }
        log.info("解析报表结束-----------------------------------------------");

        log.info("生成报表-----------------------------------------------");
        Map<String, String> headMap = new LinkedHashMap<>();
        headMap.put("name", "姓名");
        headMap.put("department", "部门");
        headMap.put("date", "日期");
        headMap.put("week", "星期");
        headMap.put("inTime", "上班卡时间");
        headMap.put("outTime", "下班卡时间");
        exportExcelX("部门加班统计", headMap, jsonArray, new FileOutputStream(new File("C:\\Users\\SI-GZ-1134\\Desktop\\部门加班统计.xlsx")));
        log.info("生成报表结束-----------------------------------------------");
    }


    private static boolean isEmptyCell(Cell cell) {
        return null == cell || cell.getCellType() == Cell.CELL_TYPE_BLANK;
    }

    private static String getStringCellValue(Cell cell) {
        if (isEmptyCell(cell)) {
            return null;
        }
        cell.setCellType(CellType.STRING);
        return cell.getStringCellValue();
    }

    private static Date getDateCellValue(Cell cell) {
        if (isEmptyCell(cell)) {
            return null;
        }
        cell.setCellType(CellType.NUMERIC);
        return cell.getDateCellValue();
    }



    private static void exportExcelX(String title, Map<String, String> headMap, JSONArray jsonArray, OutputStream out) {
        // 声明一个工作薄, 带1000缓存
        SXSSFWorkbook workbook = new SXSSFWorkbook(1000);
        workbook.setCompressTempFiles(true);
        //表头样式
        CellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        Font titleFont = workbook.createFont();
        titleFont.setFontHeightInPoints((short) 20);
        titleFont.setBold(false);
        titleStyle.setFont(titleFont);
        // 列头样式
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        Font headerFont = workbook.createFont();
        headerFont.setFontHeightInPoints((short) 12);
        headerFont.setBold(false);
        headerStyle.setFont(headerFont);
        // 普通单元格样式
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        Font cellFont = workbook.createFont();
        cellFont.setBold(false);
        cellStyle.setFont(cellFont);

        // 日期单元格样式
        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.cloneStyleFrom(cellStyle);
        DataFormat dateFormat = workbook.createDataFormat();
        dateCellStyle.setDataFormat(dateFormat.getFormat(DEFAULT_DATE_PATTERN));


        // double小数的数值
        CellStyle doubleCellStyle = workbook.createCellStyle();
        doubleCellStyle.cloneStyleFrom(cellStyle);
        DataFormat doubleFormat = workbook.createDataFormat();
        doubleCellStyle.setDataFormat(doubleFormat.getFormat("#,##0.00"));


        // BigDecimal小数的数值
        CellStyle bigDecimalCellStyle = workbook.createCellStyle();
        bigDecimalCellStyle.cloneStyleFrom(cellStyle);
        DataFormat bigDecimalFormat = workbook.createDataFormat();
        bigDecimalCellStyle.setDataFormat(bigDecimalFormat.getFormat("#,##0.00"));

        // int类型的数值
        CellStyle integerCellStyle = workbook.createCellStyle();
        integerCellStyle.cloneStyleFrom(cellStyle);
        DataFormat integerFormat = workbook.createDataFormat();
        integerCellStyle.setDataFormat(integerFormat.getFormat("#0"));

        // 生成一个(带标题)表格
        SXSSFSheet sheet = workbook.createSheet();
        //设置列宽(至少设置为DEFAULT_COLUMN_WIDTH)
        int[] arrColWidth = new int[headMap.size() + 1];
        // 产生表格标题行,以及设置列宽
        String[] properties = new String[headMap.size()];
        String[] headers = new String[headMap.size()];
        sheet.setColumnWidth(0, DEFAULT_COLUMN_WIDTH * 128);
        int ii = 0;
        for (Map.Entry<String, String> entry : headMap.entrySet()) {
            String fieldName = entry.getKey();
            properties[ii] = fieldName;
            headers[ii] = entry.getValue();
            int bytes = fieldName.getBytes().length;
            arrColWidth[ii] = bytes < DEFAULT_COLUMN_WIDTH ? DEFAULT_COLUMN_WIDTH : bytes;
            sheet.setColumnWidth(ii + 1, arrColWidth[ii] * 256);
            ii++;
        }
        // 遍历集合数据，产生数据行
        int rowIndex = 0;
        int dataIndex = 0;
        for (Object obj : jsonArray) {
            if (rowIndex == 65535 || rowIndex == 0) {
                if (rowIndex != 0) {
                    //如果数据超过了，则在第二页显示
                    sheet = workbook.createSheet();
                }

                //表头 rowIndex=0
                SXSSFRow titleRow = sheet.createRow(0);
                titleRow.createCell(0).setCellValue(title);
                titleRow.getCell(0).setCellStyle(titleStyle);
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, headMap.size()));

                //列头 rowIndex =1
                SXSSFRow headerRow = sheet.createRow(1);
                SXSSFCell cellIndex = headerRow.createCell(0);
                cellIndex.setCellValue("序号");
                cellIndex.setCellStyle(headerStyle);
                for (int i = 0; i < headers.length; i++) {
                    SXSSFCell cell = headerRow.createCell(i + 1);
                    cell.setCellValue(headers[i]);
                    cell.setCellStyle(headerStyle);
                }
                //数据内容从 rowIndex=2开始
                rowIndex = 2;
            }
            dataIndex++;
            JSONObject jo = (JSONObject) JSONObject.toJSON(obj);
            SXSSFRow dataRow = sheet.createRow(rowIndex);
            SXSSFCell indexCell = dataRow.createCell(0);
            indexCell.setCellValue(dataIndex);
            indexCell.setCellStyle(integerCellStyle);
            for (int i = 0; i < properties.length; i++) {
                SXSSFCell newCell = dataRow.createCell(i + 1);
                Object o = jo.get(properties[i]);
                String cellValue = null;
                if (null == o) {
                    cellValue = "";
                    newCell.setCellStyle(cellStyle);
                    newCell.setCellValue(cellValue);
                } else if (o instanceof Date) {
                    newCell.setCellStyle(dateCellStyle);
                    newCell.setCellValue((Date) o);
                } else if (o instanceof Float || o instanceof Double) {
                    double v = new BigDecimal(o.toString()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    newCell.setCellValue(v);
                    newCell.setCellStyle(doubleCellStyle);
                } else if (o instanceof BigDecimal) {
                    double v = ((BigDecimal) o).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    newCell.setCellValue(v);
                    newCell.setCellStyle(bigDecimalCellStyle);
                } else if (o instanceof Integer) {
                    newCell.setCellValue((Integer) o);
                    newCell.setCellStyle(integerCellStyle);
                } else {
                    cellValue = o.toString();
                    newCell.setCellStyle(cellStyle);
                    newCell.setCellValue(cellValue);
                }
            }
            rowIndex++;
        }
        try {
            workbook.write(out);
            workbook.close();
            workbook.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
