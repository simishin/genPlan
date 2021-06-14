/**
 * Исходные данные из файла копии экрана примаверы
 * Результат в виде темплана с согласованием и тутверждением
 */
package qwr;

import qwr.action.RwPimFile;
import qwr.model.TPlan.ElmPb;
import qwr.model.TPlan.ElmTs;
import qwr.Inizial.InGuid;
import qwr.util.BgFile;
import static qwr.util.BgFile.prnq;

public class MainTx {
    public static void main(String[] args) {
        if (!BgFile.parsingComandStringXlsx(args)) System.exit(78); //новая инициалзация
//        prnq("Читаю файл заготовки темплана "+ BgFile.flin);
        prnq("--------------------------------");
        InGuid.beg();//инициализация значений справочников по умолчанию
        RwPimFile.readFlileTx(); //работа с файлами примаверы
        prnq("\nПодгружено "+ RwPimFile.lstPb.size()+" строк ");
//        for (ElmPb e: RwPimFile.lstPb){ assert prnq("* "+e.getNur()+") "+e.getTst()+"-" +e.getForm()); }
        RwPimFile.SetFormSum();
        for (ElmPb e: RwPimFile.lstPb){ assert prnq(((e instanceof ElmTs)?"=":"^")+e.getNur()+") "+e.getTst()+"-" +e.getForm()+"^"+e.getIdr()); }
        RwPimFile.generXlsx();
    }//main
}
