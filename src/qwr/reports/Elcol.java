package qwr.reports;
//класс описания колонок
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import static qwr.util.BgFile.prnq;

public class Elcol {
    static XSSFSheet sheet;     //ссылка на лист
    public static int     rwst=0;//номер первой строки данных = смещение таблицы
    //-------------------------------------------------------------------------------
    static        int     counter=0;
    private final int     ncol;//порядковый номер колонки
    private final int     nrow;//номр верхней строки
    private final int     wid; //ширина колокки
    private final String  name;//наименование колонки в шапке
    private final int     drow;//номр  нижней строки
    private final int     stl; //стиль
    private final int     elcl;//номер колонки данных
    private final String  frml;//формула в ячейке
    private final boolean bsm; //вертикальное суммирование
    private String        ref; //имя столбца таблицы для генерации формул
    //------------------------------------------------
    public static void  setSheet(XSSFSheet sh){ sheet=sh;}//инициализация ссылки

    public static int   setPointOffcet(int i){//для Elcol.printCellFormulSum();
        rwst=sheet.getLastRowNum()+i;//для формирования вертикального суммирования
        assert prnq("\nСмещение начало таблицы "+sheet.getLastRowNum()+"+"+i);
        return rwst;
    }//------------------------------------------------------------------------------
    public static void  claerCount(){counter=0;}
    public boolean      isBsm()   { return bsm; }//вертикальное суммирование
    public int          getNrow() { return nrow; }//номр верхней строки
    public int          getNcol() { return ncol; }//порядковый номер колонки
    public int          getWid()  { return wid; }//ширина колокки
    public String       getName() { return name; }//наименование колонки
    public int          getDrow() { return drow; }//тип данных в колонке
    public int          getStl()  { return stl; }//размер шрифта/стиль
    public int          getElcl() { return elcl; }//номер колонки данных
    public String       getFrml() { return frml; }//формула в ячейке
    //общий конструктор--------------------------------------------------------------
    public Elcol(int nrow, int wid, int stl, String name, int elcl, boolean bsm) {
        this( nrow, nrow, wid, stl, name, elcl,"",bsm); }
    public Elcol(int nrow, int wid, int stl, String name,  String frmll, boolean bsm) {
        this( nrow, nrow, wid, stl, name, 0,frmll,bsm); }
    public Elcol(int nrow, int drow, int wid, int stl, String name, int elcl, boolean bsm) {
        this( nrow, drow, wid, stl, name, elcl,"",bsm); }
    public Elcol(int nrow, int wid, int stl, String name, int elcl) {
        this( nrow, nrow, wid, stl, name, elcl,"",false); }
    public Elcol(int nrow, int drow, int wid, int stl, String name, int elcl) {
        this( nrow, drow, wid, stl, name, elcl,"",false); }
    public Elcol(int nrow, int wid, int stl, String name, String frml) {
        this( nrow, nrow, wid, stl, name,0,frml,false); }
    public Elcol(int nrow, int drow, int wid, int stl, String name, String frml) {
        this( nrow, drow, wid, stl, name,0,frml,false); }
    public Elcol(int nrow, int drow, int wid, int stl, String name, int elcl, String frml, boolean bsm) {
        this.ncol =counter++;//порядковый номер колонки
        this.nrow =nrow;//номр верхней строки
        this.wid  =wid; //ширина колокки
        this.name =name;//наименование колонки
        this.drow =drow;//номр нижней строки
        this.stl  =stl; //стиль
        this.elcl =elcl;//номер колонки данных
        this.frml =frml;//формула в ячейке
        this.bsm  =bsm; //вертикальное суммирование
    }//Elcol
    //-----------------------------------------------------------------------------
    public boolean creatHeaderTable(CellStyle cs, int offset){//создаю заголовок таблицы
//        assert prnq("\ncreatHeaderTable "+this.drow+"+"+offset+"/"+this.ncol);
        if (sheet.getLastRowNum()<drow+offset) {//проверяю наличие строк и создаю недостающие
            for (int i=sheet.getLastRowNum(); i<drow+offset; i++ ) sheet.createRow(i+1); }
        sheet.getRow(this.nrow+offset).createCell(this.ncol).setCellStyle(cs);
        sheet.getRow(this.nrow+offset).getCell(this.ncol).setCellValue(this.name);
        if (this.nrow!=this.drow)
            sheet.addMergedRegion(new CellRangeAddress(this.nrow+offset,
                    this.drow+offset,this.ncol, this.ncol) );
        //заполняю данные по именам столбцов
        this.ref=sheet.getRow(this.nrow+offset).getCell(this.ncol).getReference()
                .replaceFirst(String.valueOf(this.nrow+offset+1),"");
        return true;
    }//creatCell
    //-----------------------------------------------------------------------------
    public boolean creatCellTitl(CellStyle cs){//создаю заголовок таблицы
        if (sheet.getLastRowNum()<drow) {//проверяю наличие строк и создаю недостающие
            for (int i=sheet.getLastRowNum(); i<drow; i++ ) sheet.createRow(i+1); }
        sheet.getRow(this.nrow).createCell(this.ncol).setCellStyle(cs);
        sheet.getRow(this.nrow).getCell(this.ncol).setCellValue(this.name);
        if (this.nrow!=this.drow)
            sheet.addMergedRegion(new CellRangeAddress(this.nrow,this.drow,this.ncol, this.ncol) );
        //заполняю данные по именам столбцов
        this.ref=sheet.getRow(this.nrow).getCell(this.ncol).getReference()
                .replaceFirst(String.valueOf(this.nrow+1),"");
        return true;
    }//creatCell
    //-------------------------------------------------------------------
    public static boolean creatCellTitl(CellStyle cs, int rowj, int clb, int cle, String s){
//        if (rowj>=rowi) return false;
        sheet.getRow(rowj).createCell(clb).setCellStyle(cs);
        sheet.getRow(rowj).getCell(clb).setCellValue(s);
        if (clb!=cle) sheet.addMergedRegion(new CellRangeAddress(rowj,rowj,clb, cle) );
        return true;
    }//creatCell
    //------------------------------------------------------------
    public static void creatCell(CellStyle cs, int rowj, int fc, int lc, String s){
        sheet.getRow(rowj).createCell(fc).setCellStyle(cs);
        sheet.getRow(rowj).getCell(fc).setCellValue(s);
        if (fc<lc) sheet.addMergedRegion(new CellRangeAddress(rowj,rowj,fc, lc) );
    }//creatCell
    //--------------------------------------------------------------
    public void printCellFormulSum(CellStyle cs, int rowj, String forml){//создание формулы вертикального суммирования
        if (!this.bsm) return;//вертикальное суммирование отсутствует
        if (forml.isBlank()){ sheet.getRow(rowj).createCell(ncol).setCellValue(0);}
        else sheet.getRow(rowj).createCell(this.ncol).
                setCellFormula(creatForm(rwst+1,this.ref,forml));
        sheet.getRow(rowj).getCell(this.ncol).setCellStyle(cs);
    }//creatCellSum
    /*********************************************************************
     * модификация формулы с заменой номера строки с "9" на реальный
     * если номер строки не помечен символом "$"
     * @param rowi номер строки в таблице начинающийся с нуля
     * @return отредактированная формула */
    public String getFrml(int rowi) {
        if (this.frml.length()<2 || rowi<1) {  return "Error";  }
        StringBuilder s= new StringBuilder(this.frml);
        s.ensureCapacity(94);//увеличиваю объем буфера
        String d= Integer.toString(rowi+1);
        Boolean b=false;
        for (int k=s.length()-1, n=k; k>=0; k--){
            if (s.charAt(k)>='0'&& s.charAt(k)<='9') {
                if (!b) { b = true;  n = k; } //это цифра
            } else {//не цифра
                if (s.charAt(k)!='$'&& b) { s.replace(k+1,n+1,d); }
                b=false;
            }   }
        return String.valueOf(s);
    }//формула в ячейке
    //------------------------------------------------------------------------
    public boolean printCellFormul(CellStyle cs, int rowj){
        if (this.frml.isBlank()) return true;//нет формулы выходим
//        prnq("\n "+rowj+"/"+this.ncol+" >"+this.frml+"<");
        if (this.frml.length()<2) prnq("ERROR in formula :"+this.frml+"~ ");
        else {
            sheet.getRow(rowj).createCell(this.ncol).setCellFormula(getFrml(rowj));
            sheet.getRow(rowj).getCell(this.ncol).setCellStyle(cs);
        }
        return false;
    }//creatCellForm
    /*******************************************************************************************
     * формирование формулы вида SUM(D2:D3)+D8+SUM(D10:D12)+SUM(D16:D17) на основе
     * @param def смешения номера строки в формируемой таблице (минимум на 1)
     * @param ncol имя колонки в которой идет суммирование по вертикали ( напримеер "D")
     * @param forml перечень строк. которые нужно суммировать (например "2%3%8%10%11%12%16%17%")
     * @return результат в виде формулы для подстановки SUM(D2:D3)+D8+SUM(D10:D12)+SUM(D16:D17)
     */
    public static String creatForm(int def, String ncol, String forml){
        if (forml.isBlank()) {prnq(" Ёrror input Formula ");return null;}
        if (ncol.isBlank()) {prnq(" Ёrror input Colonka ");return null;}
        StringBuilder frm =new StringBuilder(78);
        String[] ars=forml.split("%");//2%8%10%12%16%
        int r;
        int k=-1;
        boolean p=false; //это порядок
        for (String es:ars){//цикл по списку номеров строк
            r=Integer.parseInt(es)+def; //r=Integer.parseInt(es)+def+1;
            if (k<0) { k=r; continue;} //первый захват
            //prnt(" "+es+" : "+k+" >");
            if (r==k+1){//это порядок
                if (!p) { p = true; frm.append("SUM(").append(ncol).append(k).append(":").append(ncol); }//порядок начался
            } else {// порядок нарушен
                if (p) { p = false; frm.append(k).append(")+"); } //порядок закончился
                else { frm.append(ncol).append(k).append('+'); }//беспорядок продолжается
            }
            k=r;
        }//цикл по списку номеров строк
        if (p) { frm.append(k).append(")"); }
            else { frm.append(ncol).append(k); }
        return String.valueOf(frm);
    }//creatForm
}//class
/*
//===================================================================================
варианты текстовых полей

row, s col, style, text                       2+1   1-1-1   A-2
rowu, rowd, s col, style, text                3+1   2-1-1   A-3
row, s coll, colr, style, text               3+1    1-2-1   B-3
rowu, rowd,s coll, colr, style, text         4+1    2-2-2   B-4-C


row,s col, wid, style, text                  3+1    1-2-1   A-3-x
row,s col, heig,style, text                  3+1    1-2-1   B-3-x

rowu, rowd,s col, wid, style, text           4+1    2-2-2   A-4
row, s  coll, colr, heig,style, text          4+1   1-3-1   B-4-x


горизонтальный

row,  coll, colr, style, text          3
row,  coll, colr, heig, style, text    4
вертикальный

rowu, rowd, s col, style, text          3ХХХХ

квадрат
ClaimsToCustomer

row, s col, style, text                 2
row,s col, heig,style, text             3
rowu, rowd,s coll, colr, style, tex      4
rowu, rowd,s coll, colr, wid,  style, text  5
столбец
row,s col, wid, style, text             3
rowu, rowd,s col, wid, style, text      4


 private         int     urow;//индекс верхней строки (начиная с нуля)
    private         int     drow;//индекс нижней строки (начиная с нуля)
    private         int     lcol;//индекс левого столбца (начиная с нуля)
    private         int     rcol;//индекс правого столбца (начиная с нуля)
    private         char    horz;//выравнивание по горизонтали horz = 'S'
    private         int     wid; //ширина колокки
    private         char    vert;//варавнивание по вертикали
    private         int     heig;//высота строки
    private         int     size;//размер шрифта / если меньше нуля то использовать стиль
    private         boolean bold;//жирный шрифт
    private         String  text;//текст в ячейке

    сделать 3 класса ячеек - верткаль, горизонталь, квадрат
    //********************************************************************

 */