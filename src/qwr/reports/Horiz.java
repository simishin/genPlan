package qwr.reports;

import org.apache.poi.xssf.usermodel.XSSFCellStyle;

public class Horiz extends BaseCell implements Decor  {
    private final int heig;//высота строки
    //-------------------------------------------------------------------------
// public BaseCell(int rowu, int rowd, int coll, int colr, int stil, String text)
    public Horiz(int row, int col, String text) {
        super(row,row, col, col, 0, text); this.heig=0; }

    public Horiz(int row, int col, int stil, String text) {
        super(row,row, col, col, stil, text); this.heig=0; }


    public Horiz(int row, int col, int heig, int stil, String text) {
        super(row, row, col, col, stil, text); this.heig=heig; }

    public Horiz(int row, int coll, int colr,  int heig, int stil, String text) {
        super(row, row, coll, colr, stil, text); this.heig=heig; }
    //-------------------------------------------------------------------------
    @Override
    public int printOffsetCell(XSSFCellStyle cs, int offset) {
        //создаю элемент таблицы с заданными координатами и запиываю в него текст
        int r=super.printOffsetCell(cs, offset);
        //устанавливаю высоту строки
        if (this.heig>0) sheet.getRow(rowd).setHeight((short) heig);//высота строки
        return r;
    }//printOffsetCell
}//class Horiz
