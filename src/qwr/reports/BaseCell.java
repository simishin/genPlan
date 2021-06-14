package qwr.reports;

import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import static qwr.util.BgFile.prnq;

public class BaseCell {
    protected  static XSSFSheet sheet;     //ссылка на лист
    protected   final int     rowu;   //индекс верхней строки (начиная с нуля)
    protected   final int     rowd;   //индекс нижней строки (начиная с нуля)
    protected   final int     coll;   //индекс левого столбца (начиная с нуля)
    private     final int     colr;   //индекс правого столбца (начиная с нуля)
    private     final int     stil;   //номер стиля из массива для данного поля
    private     final String  text;   //текст в ячейке
    //------------------------------------------------------------------------------
    public static void  setSheet(XSSFSheet sh){ sheet=sh;}//инициализация ссылки

    public BaseCell(int rowu, int rowd, int coll, int colr, int stil, String text) {
        this.rowu = rowu;
        this.rowd = rowu > rowd ? rowu : rowd;
        this.coll = coll;
        this.colr = coll > colr ? coll : colr;
        this.stil = stil;
        this.text = text;
    }
    //создаю элемент таблицы с заданными координатами и запиываю в него текст
    public int printOffsetCell(XSSFCellStyle cs, int offset){//создаю
        assert cs!=null : "не определен стиль";
        assert this.coll>=0 : "coll < 0";
        assert this.colr>=0 : "colr < 0";
        assert this.rowu>=0 : "rowu < 0";
        assert this.rowd>=0 : "rowd < 0";
        assert this.rowd+offset >=0 : "rowd+offset < 0";
//        assert prnq("\ncreatHeaderTable "+this.drow+"+"+offset+"/"+this.ncol);
        if (sheet.getLastRowNum()<rowd+offset) {//проверяю наличие строк и создаю недостающие
            for (int i=sheet.getLastRowNum();i<rowd+offset;i++) sheet.createRow(i+1);
            assert prnq("Созданы строки до "+rowd+offset);
        }
        sheet.getRow(this.rowu+offset).createCell(this.coll).setCellStyle(cs);
        sheet.getRow(this.rowu+offset).getCell(this.coll).setCellValue(this.text);
        if (this.rowu!=this.rowd || this.coll != this.colr)
            sheet.addMergedRegion(new CellRangeAddress(this.rowu+offset,
                    this.rowd+offset,this.coll, this.colr) );
        //заполняю данные по именам столбцов

        return rowd+offset;
    }//printOffsetCell
    //============ STATIC ==========================================================
    public static void printCellFormula(XSSFCellStyle styleq,int row,int col,String frm){
        if (sheet.getLastRowNum()<row) {//проверяю наличие строк и создаю недостающие
            for(int i=sheet.getLastRowNum();i<row;i++)sheet.createRow(i+1);}
        if (frm.isBlank()) { sheet.getRow(row).createCell(col).setCellValue(0);}
        else sheet.getRow(row).createCell(col).setCellFormula(frm);
        sheet.getRow(row).getCell(col).setCellStyle(styleq);
    }//printCellFormula
    //--- вычисление значения по формуле из ячейки и запись значения в ячейку ---
    public static double printCellFoprulaEvalator(int row, int col){
        FormulaEvaluator evaluator = sheet.getWorkbook().getCreationHelper().
                createFormulaEvaluator();
        CellValue cellValue = evaluator.evaluate(sheet.getRow(row).getCell(col));
        double value = cellValue.getNumberValue();
        sheet.getRow(row).getCell(col).setCellValue(value);
        return value;
    }//printCellFoprulaEvalator
}//BaseCell
