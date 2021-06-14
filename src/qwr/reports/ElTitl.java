package qwr.reports;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ElTitl {
    private final int     urow;//индекс верхней строки (начиная с нуля)
    private final int     drow;//индекс нижней строки (начиная с нуля)
    private final int     lcol;//индекс левого столбца (начиная с нуля)
    private final int     rcol;//индекс правого столбца (начиная с нуля)
    private final char    horz;//выравнивание по горизонтали horz = 'S'
    private final int     wid; //ширина колокки
    private final char    vert;//варавнивание по вертикали
    private final int     heig;//высота строки
    private final int     size;//размер шрифта / если меньше нуля то использовать стиль
    private final boolean bold;//жирный шрифт
    private final String  text;//текст в ячейке
//    private XSSFCellStyle   styl;//стиль фомитирования текста
    private static  XSSFWorkbook    wbook;
    private static  XSSFSheet       sheet; //ссылка на лист
    private static  XSSFCellStyle   style;
    private static  XSSFFont        font;
    private static  int     rowi;//индекс строки
    //-----------------------------------------------------------------
    public static void setWbook(XSSFWorkbook w){wbook=w;}
    public static void setSheet(XSSFSheet sh){
        sheet=sh;
        wbook=sheet.getWorkbook();
        font = wbook.createFont();
        style= wbook.createCellStyle();
    }//инициализация ссылки
    public static int  getRowL(){return sheet.getLastRowNum();}
    public static int  getRowi(){return rowi;}
    public static void setRowi(Integer i){rowi=i;}
    public static void prn(String s){ System.out.print("\nRow="+sheet.getLastRowNum()+"  "+s); }
    //-----------------------------------------------------------------
    public XSSFCellStyle mapping() {
        if (sheet.getLastRowNum()<drow) {//проверяю наличие строк и создаю недостающие
            for (int i=sheet.getLastRowNum(); i<drow; i++ ) sheet.createRow(i+1); }
        if (heig>0) sheet.getRow(drow).setHeight((short) heig);//высота строки

        sheet.getRow(urow).createCell(lcol).setCellType(CellType.STRING);
        if (!text.isBlank()) sheet.getRow(urow).getCell(lcol).setCellValue(text);

        font.setFontHeightInPoints((short)size);
        font.setBold(bold);//жирный
        style.setWrapText(true);//перенос текста в ячейке
        style.setFont(font);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        if (horz=='C') {style.setAlignment(HorizontalAlignment.CENTER);}
        else if (horz=='R') {style.setAlignment(HorizontalAlignment.RIGHT);}
        sheet.getRow(urow).getCell(lcol).setCellStyle(style);
        if (lcol<rcol || urow<drow) sheet.addMergedRegion(new CellRangeAddress(urow,drow,lcol, rcol) );
        return style;
    }//mapping
    //-----------------------------------------------------------------
    public void mapping(XSSFCellStyle   styleq) {
        if (sheet.getLastRowNum()<drow) {//проверяю наличие строк и создаю недостающие
            for (int i=sheet.getLastRowNum(); i<drow; i++ ) sheet.createRow(i+1); }
        if (heig>0) sheet.getRow(drow).setHeight((short) heig);//высота строки

        sheet.getRow(urow).createCell(lcol).setCellType(CellType.STRING);
        if (!text.isBlank()) sheet.getRow(urow).getCell(lcol).setCellValue(text);

        if (this.size<0 ){sheet.getRow(urow).getCell(lcol).setCellStyle(styleq);}
        else {
            font.setFontHeightInPoints((short)size);
            font.setBold(bold);//жирный
            style.setWrapText(true);//перенос текста в ячейке
            style.setFont(font);
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            if (horz=='C') {style.setAlignment(HorizontalAlignment.CENTER);}
            else if (horz=='R') {style.setAlignment(HorizontalAlignment.RIGHT);}
            sheet.getRow(urow).getCell(lcol).setCellStyle(style);
        }
        if (lcol<rcol || urow<drow) sheet.addMergedRegion(new CellRangeAddress(urow,drow,lcol, rcol) );
    }//mapping
    //---------------------------------------------------------------------------
    public static void printCellFormula(XSSFCellStyle styleq, int row, int col, String frm){
        if (sheet.getLastRowNum()<row) {//проверяю наличие строк и создаю недостающие
            for(int i=sheet.getLastRowNum();i<row;i++)sheet.createRow(i+1);}
        if (frm.isBlank()) { sheet.getRow(row).createCell(col).setCellValue(0);}
        else sheet.getRow(row).createCell(col).setCellFormula(frm);
        sheet.getRow(row).getCell(col).setCellStyle(styleq);
    }//printCellFormula
    //--- вычисление значения по формуле из ячейки и запись значения в ячейку ---
    public static double printCellFoprulaEvalator(int row, int col){
        String formula = sheet.getRow(row).getCell(col).getCellFormula();
        FormulaEvaluator evaluator = wbook.getCreationHelper().createFormulaEvaluator();
        CellValue cellValue = evaluator.evaluate(sheet.getRow(row).getCell(col));
        double value = cellValue.getNumberValue();
        sheet.getRow(row).getCell(col).setCellValue(value);
        return value;
    }//printCellFoprulaEvalator
    //===================== КОНСТРУКТОРЫ ======================================
    public ElTitl(int row, int col, String txt){
        this(row,row,col,col,'C',-1,'C',-1, 12,false,txt); }

    public ElTitl(int row, int lcol, int rcol, char horz,int heig, int size, boolean bold, String txt){
        this(row,row,lcol,rcol,horz,-1,'C',heig,
                size,bold,txt); }
    public ElTitl(int urow, int drow, int lcol, int rcol, char horz, int wid,
                  char vert, int heig, int size, boolean bold, String text) {
        this.urow = urow;//индекс верхней строки (начиная с нуля)
        this.drow = drow;//индекс нижней строки (начиная с нуля)
        this.lcol = lcol;//индекс левого столбца (начиная с нуля)
        this.rcol = rcol;//индекс правого столбца (начиная с нуля)
        this.horz = horz;//выравнивание по горизонтали
        this.wid  = wid; //ширина колокки
        this.vert = vert;//варавнивание по вертикали
        this.heig = heig;//высота строки
        this.size = size;//размер шрифта / если меньше нуля то использовать стиль
        this.bold = bold;//жирный шрифт
        this.text = text;//текст в ячейке
    }//ElTitl
    public ElTitl(int urow, int drow, int lcol, int rcol, int size, String text) {
        this.urow = urow;//индекс верхней строки (начиная с нуля)
        this.drow = drow;//индекс нижней строки (начиная с нуля)
        this.lcol = lcol;//индекс левого столбца (начиная с нуля)
        this.rcol = rcol;//индекс правого столбца (начиная с нуля)
        this.horz = 'S';//выравнивание по горизонтали
        this.wid  = -1; //ширина колокки
        this.vert = 'S';//варавнивание по вертикали
        this.heig = -1;//высота строки
        this.size = size;//размер шрифта / если меньше нуля то использовать стиль
        this.bold = false;//жирный шрифт
        this.text = text;//текст в ячейке
    }//ElTitl
}//class ElTitl