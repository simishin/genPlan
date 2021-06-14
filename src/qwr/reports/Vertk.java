package qwr.reports;
//описание столбца
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

import static qwr.util.BgFile.prnq;
// public BaseCell(int rowu, int rowd, int coll, int colr, int stil, String text)
public class Vertk extends BaseCell implements Decor{
    public static int     rwst=0;//номер первой строки данных = смещение таблицы
    private final int     wid;   //ширина колокки
    private       String  ref;   //имя столбца таблицы для генерации формул
    private       String  forml = null;  //формула в ячейке
    private       boolean bsm   = false; //вертикальное суммирование
    //-------------------------------------------------------------------------
    public static int   setPointOffcet(int i){//для Elcol.printCellFormulSum();
        rwst=sheet.getLastRowNum()+i;//для формирования вертикального суммирования
        assert prnq("\nСмещение начало таблицы "+sheet.getLastRowNum()+"+"+i);
        return rwst;
    }//setPointOffcet
    //--------------------------------------------------------------------------
    public void printCellFormulSum(CellStyle cs, int rowj){//создание формулы вертикального суммирования
        if (!this.bsm) return;//вертикальное суммирование отсутствует
        if (forml.isBlank()){ sheet.getRow(rowj).createCell(coll).setCellValue(0);}
        else sheet.getRow(rowj).createCell(this.coll).
                setCellFormula(creatForm(rwst+1,this.ref,forml));
        sheet.getRow(rowj).getCell(this.coll).setCellStyle(cs);
    }//creatCellSum
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
                if (!p) { p = true; frm.append("SUM(").append(ncol).append(k).
                        append(":").append(ncol); }//порядок начался
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
    //------------------------------------------------------------------------
    public boolean printCellFormul(CellStyle cs, int rowj){
        if (rowj<1){prnq("Error string "+rowj);return true;}
        if (this.forml.isBlank()) return true;//нет формулы выходим
//        prnq("\n "+rowj+"/"+this.ncol+" >"+this.frml+"<");
        if (this.forml.length()<2) prnq("ERROR in formula :"+this.forml+"~ ");
        else {
            // модификация формулы с заменой номера строки с "9" на реальный
            // если номер строки не помечен символом "$"
            StringBuilder s= new StringBuilder(this.forml);
            s.ensureCapacity(94);//увеличиваю объем буфера
            String d= Integer.toString(rowj+1);
            Boolean b=false;
            for (int k=s.length()-1, n=k; k>=0; k--){
                if (s.charAt(k)>='0'&& s.charAt(k)<='9') {
                    if (!b) { b = true;  n = k; } //это цифра
                } else {//не цифра
                    if (s.charAt(k)!='$'&& b) { s.replace(k+1,n+1,d); }
                    b=false;
                }
            }
            sheet.getRow(rowj).createCell(this.coll).setCellFormula(String.valueOf(s));
            sheet.getRow(rowj).getCell(this.coll).setCellStyle(cs);
        }
        return false;
    }//creatCellForm
    /*********************************************************************
     * модификация формулы с заменой номера строки с "9" на реальный
     * если номер строки не помечен символом "$"
     * @param rowi номер строки в таблице начинающийся с нуля
     * @return отредактированная формула */
    public String getFrml(int rowi) {
        if (rowi<1||this.forml.length()<2){prnq("Error string "+rowi);return "";}
        StringBuilder s= new StringBuilder(this.forml);
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
    //-------------------------------------------------------------------------

    // public BaseCell(int rowu, int rowd, int coll, int colr, int stil, String text)
    public Vertk(int row, int col, int stil, String text) {
        super(row,row, col, col, stil, text); this.wid=0; }//1х1=4

    public Vertk(int row, int col, int wid, int stil, String text) {
        super(row, row, col, col, stil, text); this.wid=wid; }//1х1+1=5

    public Vertk(int rowu, int rowd, int col, int wid, int stil, String text) {
        super(rowu, rowd, col, col, stil, text); this.wid=wid; }//2 строки х1+1=6

    public Vertk(int row, int col, String text, String forml, boolean bsm) {
        super(row,row, col, col, 0, text);
        this.wid=0; this.forml=forml; this.bsm=bsm; }//1х1=3

    public Vertk(int row, int col, int wid, int stil, String text,
                 String forml, boolean bsm) {
        super(row,row,col,col,stil,text);
        this.wid=wid;this.forml=forml;this.bsm=bsm;}//1 х1+1+Ф=7

    public Vertk(int rowu, int rowd, int col, int wid, int stil, String text,
                 String forml, boolean bsm) {
        super(rowu,rowd,col,col,stil,text);
        this.wid=wid;this.forml=forml;this.bsm=bsm;}//2 строки х1+1+Ф=8
    //-------------------------------------------------------------------------
    @Override
    public int printOffsetCell(XSSFCellStyle cs, int offset) {
        //создаю элемент таблицы с заданными координатами и запиываю в него текст
        int r=super.printOffsetCell(cs, offset);
        //устанавливаю заданную ширину колонки
        if (this.wid>10) sheet.setColumnWidth(coll,this.wid);//ширина колонок
        //сохраняю наименование колонки для генерации форул вертикального суммирования
        this.ref=sheet.getRow(this.rowu+offset).getCell(this.coll).getReference()
                .replaceFirst(String.valueOf(this.rowu+offset+1),"");
        return r;
    }//printOffsetCell
}//class Vertk
