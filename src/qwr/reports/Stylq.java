package qwr.reports;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.Color;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Stylq {

    public static int cstl=13; //количество стилей используется при генерации массива
    static boolean lastStl=false;

    public static XSSFCellStyle creatStyle(XSSFWorkbook wbook, int i){
        XSSFFont font = wbook.createFont();
//        font.setFontName("Courier New");
        font.setItalic(false);//наклонный
        switch (i){
            case 1://заголовок документа
                font.setFontHeightInPoints((short)14);
                font.setBold(true);//жирный
                break;
            case 2://шапка таблицы
                font.setFontHeightInPoints((short)8);
                font.setBold(false);//жирный
                break;
            case 3://группировка
                font.setFontHeightInPoints((short)10);
                font.setBold(false);//жирный
                break;
            case 7://скрытый текст
                font.setColor(new XSSFColor(new Color(210, 233, 243)));
                font.setFontHeightInPoints((short)6);
                font.setBold(false);//жирный
                break;
            case 10://заголовок документа
                font.setFontHeightInPoints((short)12);
                font.setBold(true);//жирный
                break;
            case 11://заголовок документа
                font.setFontHeightInPoints((short)10);
                font.setBold(false);//жирный
                break;
            default:
                font.setFontHeightInPoints((short)10);
                font.setBold(false);//жирный
        }
        XSSFCellStyle style= wbook.createCellStyle();
        style.setWrapText(true);//перенос текста в ячейке
        style.setFont(font);
        switch (i){//работаю без бордюра
            case 1: break;
            case 3: break;
            case 10: break;
            case 11: break;
            case 12: break;
            default:
                style.setBorderRight (BorderStyle.HAIR );
                style.setBorderBottom(BorderStyle.HAIR );
                style.setBorderLeft  (BorderStyle.HAIR );
                style.setBorderTop   (BorderStyle.HAIR );
        }//switch
        lastStl=false;//это еще не последний создаваемый стиль
         style.setVerticalAlignment(VerticalAlignment.CENTER);//сработало по вертикали
        switch (i) {
            case 0: break;//все по умолчанию
            case 7: break;//скрытый текст
            case 2://шапка таблицы
                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.index);
            case 1://заголовок документа

            case 5://номер строки Integer
                style.setAlignment(HorizontalAlignment.CENTER);//сработало по горизонтали
                break;
            case 12://группировка Double
                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.index);
                style.setAlignment(HorizontalAlignment.RIGHT);//сработало по горизонтали
                break;
            case 3://группировка
                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.index);
                CreationHelper createHelperq = wbook.getCreationHelper();
                style.setDataFormat(createHelperq.createDataFormat().getFormat("#.00"));
            case 4://text
                style.setAlignment(HorizontalAlignment.LEFT);//сработало по горизонтали
                break;
            case 6:// 6) Double
                style.setAlignment(HorizontalAlignment.RIGHT);//сработало по горизонтали
                CreationHelper createHelper = wbook.getCreationHelper();
                style.setDataFormat(createHelper.createDataFormat().getFormat("#.##"));
                break;
            case 8:// 8) Date
                style.setAlignment(HorizontalAlignment.CENTER);//сработало по горизонтали
                CreationHelper createHelperD = wbook.getCreationHelper();
                style.setDataFormat(createHelperD.createDataFormat().getFormat("m/d/yy"));
                break;
            case 9://text
            case 10://text
            case 11://text
                style.setAlignment(HorizontalAlignment.CENTER);//сработало по горизонтали
            // у последного стиля не ставить break, иначе не создадутся стили
            default: lastStl=true;
        }//switch
        return style;
    }//creatStyle
    //--распознование даты из не нормированного формата ячеек таблицы ручного ввода--
    public static int strToDate(String s){
        s=s.trim()+"-";
//        assert prnt(" "+s);
        int length = s.length();
        if (length<4) return 0;
        boolean b=false; //наличие текста
        int n=0;
        int i=0;
        int a=0;
        int d=0;
        int m=0;
        int g=0;
        for (int j=0; i<length && j<3; i++){
            char ch=s.charAt(i);
            //assert prnt(" "+i+":"+(int)ch+" ");
            if (ch> 47 && ch<59 ) {//это цифры
                if (!b) { b=true;n=i;  }//первая цифра
                continue;
            } else if (!b) continue;
                b=false;
                j++;
                String c=s.substring(n,i);
                a = Integer.valueOf(c);
    //            assert prnt(" j"+j+" a"+a+" ");
                switch (c.length()) {
                    case 0: continue;
                    case 1:
                    case 2: break;
                    case 3:
                        if (j == 0) a = a <= 31 ? a : a / 10;
                        else a = a <= 12 ? a : a / 10;
                        break;
                    case 4: if (j==3) break;
                        m = a % 100;
                        a /= 100;
                        if (j==1 ) d=a;
                        j=2;
                        break;
                    case 5: if (j==3 && a>2023) a/=10;
                            else return 0;
                            break;
                    case 6:
                        if (j==3 ) { a/=100;break;}
                        g = a % 100;
                        a /= 100;
                        m = a % 100;
                        a /= 100;
                        j=4;
                        break;
                    case 7: return 0;
                    case 8:
                        g = a % 10000;
                        a /= 10000;
                        m = a % 100;
                        a /= 100;
                        j=4;
                        break;
                    default: return 0;
                }//switch
                if(d==0) d=a; else if (j==2 && m==0) m=a; else if (g==0 && j>2) g=a;
        }//for
//        assert prnq(">"+d+"-"+m+"-"+g+"; ");
        try {
            return (int) ChronoUnit.DAYS.between(
            LocalDate.of(1899, 12, 30), LocalDate.of(g, m, d));
        } catch (Exception e){return 0;}
    }//strToDate
}//class