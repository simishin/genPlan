/**
 * вспомогательный класс утилит конвертации и преобразований
 */
package qwr.util;

import java.util.Locale;

import static qwr.util.BgFile.prnt;

public class Tranq {
    /**
     * преобразование строки в целое с отброской дробной части
     * @param s исходная строка
     * @return целое
     */
    public static int cnverti(String s){
        if (s.isBlank()) return 0;
        int j=s.indexOf('.');
        try { return j==-1 ? Integer.parseInt(s): Integer.parseInt(s.substring(0,j));
        }catch(NumberFormatException e){
            prnt("\nError convert Integer~"+s+"; ");return -1;}
    }//cnverti-----------------------------------------------------------------------
    /**
     * Удаление не печатных символов и лишних пробелов
     * @param s  Исходная строка
     * @return  Очищенная строка
     */
    public static String purifyinq(String s){
        if (s.isBlank()) return "";
        int length = s.length();
        char[] y = new char[length+1];
        int newLen = 1;
        y[0]= s.charAt(0);//извлечение символа по индексу
        for (int  j = newLen ; j < length; j++) {
            char ch=s.charAt(j);
            if ((ch > 32) || (ch==32 && y[newLen-1]>32)) {
                y[newLen] = ch;
                newLen++;
            }
        }  //for
        return new String(y, 0, newLen);
    }  // purifyin-------------------------------------------------------------------
    /**
     * Удаление не печатных символов и лишних пробелов с сохранением разделителей
     * @param s  Исходная строка
     * @return  Очищенная строка
     */
    public static String purifyin(String s){
        if (s.isBlank()) return " ";
        int length = s.length();
        char[] y = new char[length+2];
        int newLen = 1;
        y[0]= s.charAt(0);//извлечение символа по индексу
        for (int  j = newLen ; j < length; j++) {
            char ch=(s.charAt(j) > 32) ? s.charAt(j) : 32;
            if (ch==32 && y[newLen-1]==32) continue;
            y[newLen++] = ch;
        }  //for
        y[newLen++] =32;
        return new String(y, 0, newLen);
    }  // purifyin-------------------------------------------------------------------
    /**
     * выделение номеров проектов из списка без учета  изменений
     * @param str  исходная строка списка проектов
     * @return
     */
    public static String normalq(String str){
        assert str!=null: "! EDraft > normalize: str = null !";
        if(str.isBlank()) return " ";
        StringBuilder s=new StringBuilder(str.toUpperCase(Locale.ROOT));
        int i=0;
        int j=0;
        String sa;
        for (int k=0; k<s.length(); k=i+1) {
            i=s.indexOf(" ",k);
            j=s.indexOf("И",k);
            prnt("\n"+k+"\t"+i+"\t"+j+" \t=");
            if (i<j || j==-1) j=i;
            if (k==j) continue;
            sa= s.substring(k,j);
            prnt(sa+"$");
        }
        return str;
    }//normalq


}  //class Tranq=====================================================================
