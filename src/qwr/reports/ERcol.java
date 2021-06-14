package qwr.reports;

import java.util.ArrayList;

//класс описания распознанных столбцов
public class ERcol {
    public static ArrayList<ERcol> arr = new ArrayList<>(32);
    private static int shiftRow=0;//смещение данных в таблице по вертикали после шапки
    private       int     icol;   //номер колонки в которой обнаружено данное поле
    private final String  fname;  //системный идентификатор поля
    private final int     elcl;   //номер колонки данных в элементе типа class ElmRDS
    //-------------------------------------------------------------------------
    public ERcol(int elcl, String fname)
    {
        this.icol   = -1;
        this.fname  = fname;
        this.elcl   = elcl;//номер колонки данных в элементе типа class ElmRDS
    }
    public ERcol( int icol, int elcl, String fname)
    {
        this.icol   = icol;
        this.fname  = fname;
        this.elcl   = elcl;//номер колонки данных в элементе типа class ElmRDS
    }

    public static void add(ArrayList<ERcol> array, int elcl, String fname) {
        array.add(new ERcol(-1,elcl,fname));
    } //add--------------------------------------------------------------------------
    public static void add(int elcl,String fname){arr.add(new ERcol(-1,elcl,fname));}
    public static ArrayList get(){return arr;}
    public static int getShiftRow() { return shiftRow; }
    public static void clear(int shiftRow) { arr.clear();  ERcol.shiftRow = shiftRow; }

    public void     setIcol(int icol) { this.icol = icol; }
    public int      getIcol()   { return icol; }   //номер колонки в которой обнаружено данное поле
    public String   getFname()  { return fname; }//системный идентификатор поля
    public int      getElcl()   { return elcl; }   //номер колонки данных
}// class ERcol
