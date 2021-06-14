package qwr;

import qwr.Inizial.InGuid;
import qwr.action.ClaimsToCustomer;
import qwr.action.LoadXlsx;
//import qwr.model.Base.EiPath;
//import qwr.model.Base.EiUser;
import qwr.footing.Ticked;
import qwr.model.SharSystem.GrRecords;
import qwr.model.nexus.ECard;
import qwr.model.nexus.EMail;
import qwr.util.BgFile;

import static qwr.Inizial.InGuid.lnOrganz;

public class MainClaims {

    //------------------------------------------------------------------------------
    public static void main(String[] args) {
        //просматриваю пердустановки ОС
        BgFile.defineComputerUserSeting(); //просматриваю пердустановки ОС
        //тестовые установки
//        lpPath.add(new EiPath("titul",999));
//        mpUser.putIfAbsent(1L,new EiUser("form_Main_prozedur","form_Main_prozedur"));
//        lsFile.add(new EiFile("/home/sim/IdeaProjects/genPlan/rq/ddd.mln"));
        BgFile.parsingComandString(args);//анализирую командную строку
        InGuid.beg();//устанавливаю справочники по умолчанию + иницилизурую массивы
       //---------------------------------------------------------------------------
        BgFile.loadConfiguration();//загрузка локальной конфигурации проекта
//        InGuid.print("lnOrganz",lnOrganz);
        BgFile.loadLocalDataSources();//загрузка локальных данных
//        BgFile.uploadDataExternalSources(); //подгрузка данных из внешних источников
        Ticked.printList("ECard-", ECard.lst);
        ECard a1 = new ECard("qwert1",17,-1);
        a1.addElm();
        Ticked.printList("ECard+", ECard.lst);

        BgFile.printFlagModif();//количество изменений
        BgFile.clearFlagModif();//Очистка счетчика изменений
        GrRecords.CARTJ.shift();//Устанавливаю флаг изменений
        LoadXlsx.iniLoadDocPrj();//подгрузка списка рабочей документации
//        InGuid.print("lnRDoc",EDraft.mar);//список чертежей
        BgFile.printFlagModif(); //количество изменений
        //задаю имя исходного файла
//        BgFile.setDefFileInput("!!! Регистрация КЗ в УС БАЭС.xlsx");
        //загрузка инициализации колонок исходного документа
        LoadXlsx.iniERcolOldClaims();//инициализации колонок исходного документа
        //загрузка данных по карточкам
//        ClaimsToCustomer.readFileClaimsDef( lclem);//загрузка данных по карточкам
//
//        ClaimsToCustomer.identifyIntellect(lclem,lsalt);//анализ не распознаных писем
//        assert prnq("количество писем "+lsMail.size());
        //формирование на основе карточек списка писем
//        ClaimsToCustomer.exstractMailFromClaims(lclem,lsMail);
//        assert prnq("количество писем + "+lsMail.size());
        //запись списка писем
        BgFile.saveLocalDataSources(true);//сохранение данных в локальных файлах
//        InGuid.print("lnGrOrgz",lnGrOrgz);
//        InGuid.print("lnOrganz",lnOrganz);
//        InGuid.print("lnHeader",lnHeader);
//        InGuid.print("lnBuild",lnBuild);
//        InGuid.print("lnChaptr",lnChaptr);
//        InGuid.print("lnAuthor",lnAuthor);
//        InGuid.print("lnSpecRD", EiSpecRD.lnSpecRD);
//        InGuid.print("EiStag", EiStag.mar);
//        InGuid.print("EMail", EMail.mar);//список чертежей
    }//main
}//class Main
