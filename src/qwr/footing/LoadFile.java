package qwr.footing;

import java.util.Map;

public interface LoadFile {
    /**
     * вызывается из LDocPrj > readFileXslx
     * @param srt разобранная строка нового элемента
     * @param cRD массив сответствия разобранной строки полям нового элемента
     * @param sheetName имя листа таблицы источника нового элемента
     */
    void    parse(String[] srt, int[] cRD, String sheetName);

}  // interface LoadFile
