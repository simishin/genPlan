/**
 * получение формы ежедневного отчета на основании темплана стандартной формы
 * где первая ячейка данных находится в столбце F и строке 11 - нумерация столбцов
 * при генерации отчета создаются формулы вертикального суммирования.
 * требуется ручное выполнение персчета в таблице и оптимизация высоты строк
 * имя входного файла задается в командной строке
 */
package qwr;

import qwr.action.RwPimFile;
import qwr.model.TPlan.ElmPb;
import qwr.model.TPlan.ElmTs;
import qwr.util.BgFile;

import static qwr.util.BgFile.prnq;

public class MainReports {

    //------------------------------------------------------------------------------
    public static void main(String[] args) {
        BgFile.setDefFileInput("templan.xlsx");
        if(!BgFile.parsingComandStringXlsx(args))System.exit(78); //новая инициалзация
        prnq("Читаю файл заготовки темплана "+ BgFile.flin);
        RwPimFile.readFlileXPlan();
        prnq("\nПодгружено "+ RwPimFile.lstPb.size()+" строк ");

//        for (ElmPb e: RwPimFile.lstPb){ assert prnq("* "+e.getNur()+") "+e.getTst()+"-" +e.getForm()); }
//        RwPimFile.SetFormSum();
//        for (ElmPb e: RwPimFile.lstPb){ assert prnq(((e instanceof ElmTs)?"=":"^")+e.getNur()+") "+e.getTst()
//                +"-" +e.getForm()+"^"+e.getIdr()+" "+e.getRd()+" "+e.getSmin()+" "+e.getSmln()); }
        RwPimFile.SetFormSum();
        int i=0;
        for (ElmPb e: RwPimFile.lstPb){ assert prnq((i++)+" "+((e instanceof ElmTs)?"=":"^")+e.getNur()+") "+e.getTst()+"-" +e.getForm()+"^"+e.getIdr()); }
        RwPimFile.printReportMount();
        RwPimFile.printReportDay();
    }//main
}//class Main
