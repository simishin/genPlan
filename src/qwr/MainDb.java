/**
 * Чтение файла экспорта примаверы
 * Результат формирование темплна
 */
package qwr;

import qwr.action.RwPimFile;
import qwr.model.TPlan.ElmPb;
import qwr.model.TPlan.ElmTs;
import qwr.Inizial.InGuid;
import qwr.util.BgFile;

import static qwr.util.BgFile.prnq;

public class MainDb {
    public static void main(String[] args) {
        if (!BgFile.parsingComandStringXlsx(args)) System.exit(78); //новая инициалзация
//        prnq("Читаю файл заготовки темплана "+ BgFile.flin);
        prnq("--------------------------------");
        RwPimFile.beg();//инициализация значений справочников по умолчанию
        RwPimFile.readFileExportPrimavera(); //работа с файлами примаверы
//        for (ElmTs e:RwPimFile.lstPr) prnq("= "+e.getIdr());
        RwPimFile.addGroupAndSorts();//группировка и сортировка работ
        RwPimFile.SetFormSum();
        for (ElmPb e: RwPimFile.lstPb){ assert prnq(((e instanceof ElmTs)?"=":"^")+e.getNur()+") "+e.getTst()+"-" +e.getForm()); }
        RwPimFile.generXlsx();
    }//main
}//class MainDb
