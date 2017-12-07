package tools;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import transferData.ReadExcelUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by P0061799 on 2017/9/25.
 */
public class ExcelData {
    private Logger logger = LoggerFactory.getLogger(ReadExcelUtils.class);
    public Workbook workbook;
    public Sheet sheet;
    public Cell cell;
    int rows;
    int columns;
    Row row;
    public String filepath;
    public String caseName;
    public ArrayList<String> arrkey = new ArrayList<String>();
    String sourceFile;
    /**
     * @param filepath   excel文件名
     //* @param caseName   sheet名
     */
    public ExcelData(String filepath) {
        if (filepath == null) {
            return;
        }
        String ext = filepath.substring(filepath.lastIndexOf("."));
        try {
            InputStream is = new FileInputStream(filepath);
            if (".xls".equals(ext)) {
                workbook = new HSSFWorkbook(is);
            } else if (".xlsx".equals(ext)) {
                workbook = new XSSFWorkbook(is);
            } else {
                workbook = null;
            }
        } catch (FileNotFoundException e) {
            logger.error("FileNotFoundException", e);
        } catch (IOException e) {
            logger.error("IOException", e);
        }
    }
    /**
     * 获得excel表中的数据
     */
    public Object[][] getExcelData() throws  Exception {
        if (workbook == null) {
            throw new Exception("Workbook对象为空！");
        }
        sheet = workbook.getSheetAt(0);
        rows = sheet.getLastRowNum();
        row = sheet.getRow(0);
        columns =row.getPhysicalNumberOfCells();
        // 为了返回值是Object[][],定义一个多行单列的二维数组
        HashMap<String, String>[][] arrmap = new HashMap[rows][1];
        // 对数组中所有元素hashmap进行初始化
        if (rows > 1) {
            for (int i = 0; i <rows; i++) {
                arrmap[i][0] = new HashMap<>();
            }
        } else {
            System.out.println("excel中没有数据");
        }

        // 获得首行的列名，作为hashmap的key值
        for (int c = 0; c < columns; c++) {
            String cellvalue = getCellFormatValue(row.getCell(c)).toString();
            arrkey.add(cellvalue);
        }
        // 遍历所有的单元格的值添加到hashmap中
        for (int r = 1; r <=rows; r++) {
            for (int c = 0; c < columns; c++) {
                String cellvalue = getCellFormatValue(sheet.getRow(r).getCell(c)).toString();
                arrmap[r-1][0].put(arrkey.get(c), cellvalue);
            }
        }
        return arrmap;
    }
    /**
     * 根据Cell类型设置数据
     *
     * @param cell
     * @return
     */
    private Object getCellFormatValue(Cell cell) {

        Object cellvalue = "";

        if (cell != null) {
// 判断当前Cell的Type
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC:// 如果当前Cell的Type为NUMERIC
                case Cell.CELL_TYPE_FORMULA: {
// 判断当前的cell是否为Date
                    if (DateUtil.isCellDateFormatted(cell)) {
// 如果是Date类型则，转化为Data格式
// data格式是带时分秒的：2013-7-10 0:00:00
// cellvalue = cell.getDateCellValue().toLocaleString();
                        Date date = cell.getDateCellValue();
                        cellvalue = date;
                    } else {// 如果是纯数字

// 取得当前Cell的数值
                        cellvalue = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                }
                case Cell.CELL_TYPE_STRING:// 如果当前Cell的Type为STRING
// 取得当前的Cell字符串
                    cellvalue = cell.getRichStringCellValue().getString();
                    break;
                default:// 默认的Cell值
                    cellvalue = "";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;
    }

    /**
     * 获得excel文件的路径
     * @return
     * @throws IOException
     */
    public String getPath() throws IOException {
        File directory = new File(".");
        sourceFile = directory.getCanonicalPath() + "\\src\\resources\\"
                + filepath + ".xls";
        return sourceFile;
    }



}

