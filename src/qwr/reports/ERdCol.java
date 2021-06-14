/**
 * новый элемент описания колонки данных из электронной таблицы вместо ERcol
 * с нанализом повторкний названий и альтернативными названиями колонок
 */
package qwr.reports;

public class ERdCol {
//    private       int     icol;   //номер колонки в которой обнаружено данное поле
    private final String  fname;  //системный идентификатор поля
    private final int     elcl;   //номер колонки данных в элементе типа class ElmRDS
    //---конструктор-----------------------------------------------------------------
    public ERdCol(String fname) {
//        this.icol   = -1;//заполняется в процессе анализа
        this.fname  = fname;
        this.elcl   = -999;//номер колонки данных в элементе типа class ElmRDS
    }//ERdCol
    public ERdCol(int elcl, String fname) {
//        this.icol   = -1;//заполняется в процессе анализа
        this.fname  = fname;
        this.elcl   = elcl;//номер колонки данных в элементе типа class ElmRDS
    }//ERdCol

//    public void     setIcol(int icol) { this.icol = icol; }
//    public int      getIcol()   { return icol; }   //номер колонки в которой обнаружено данное поле
    public String   getFname()  { return fname; }//системный идентификатор поля
    public int      getElcl()   { return elcl; }   //номер колонки данных
    public int      getElclA()  { return Math.abs(elcl); }   //номер колонки данных

}//class ERdCol======================================================================
