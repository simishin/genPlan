package qwr.reports;

import org.apache.poi.xssf.usermodel.XSSFCellStyle;

//формирование прямоугольного элемента таблицы
public class BlokCell extends BaseCell implements Decor {
    private final int heig;//высота строки

    public BlokCell(int rowu, int rowd, int coll, int colr, int stil, String text) {
        super(rowu, rowd, coll, colr, stil, text);
        heig = -1;
    }
    public BlokCell(int row, int col, int stil, String text) {
        super(row, row, col, col, stil, text);
        heig = -1;
    }
    public BlokCell(int row, int col, int heig, int stil, String text) {
        super(row, row, col, col, stil, text);
        this.heig = heig;
    }
    //-------------------------------------------------------------------------
    @Override
    public int printOffsetCell(XSSFCellStyle cs, int offset) {
        //создаю элемент таблицы с заданными координатами и запиываю в него текст
        int r=super.printOffsetCell(cs, offset);
        //устанавливаю высоту строки
        if (this.heig>0) sheet.getRow(rowd).setHeight((short) heig);//высота строки
        return r;
    }//printOffsetCell
}//class BlokCell
