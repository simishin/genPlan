package qwr.Inizial;
//класс инициализации всех данных
import qwr.reports.ERcol;
import qwr.reports.ElTitl;
import qwr.reports.Elcol;
import java.util.*;

import static qwr.util.BgFile.prnq;

public class InTPlan {
    //инициализация списка колонок исходного документа
    public static ArrayList<ERcol> clin = new ArrayList<>(15);
    //--------начало формирования ежемесячного отчета----------------------
    public static ArrayList<Elcol> wtum = new ArrayList<>(36);
    //public Elcol(int wid, String name, int nrow, int ndt, int stl) { старая
    //public Elcol(int wid, String name, int nrow, int ndt, int stl, String frml) {
    //public Elcol(int nrow, int wid, int stl, String name, int elcl) {новая
    //public Elcol(int nrow, int wid, int stl, String name, String frml) {
//wtu.add(new Elcol(6,7,800,5,"№ п.п.",19));//4
    public static ArrayList<Elcol> lecod = new ArrayList<>(36);
    public static void iniPrintReportDayDwn() {//нижняя табличка отчета
        Elcol.claerCount();
        lecod.add(new Elcol(0,0,19," ",0));
        lecod.add(new Elcol(0,0,7," ",0));
        lecod.add(new Elcol(0,0,4,"Наименование СП организации",0));
        lecod.add(new Elcol(0,0,6,"План на месяц ",0));
        lecod.add(new Elcol(0,0,6,"План на дату отчета,",0));
        lecod.add(new Elcol(0,0,6,"Факт  на дату отчета",0));
        lecod.add(new Elcol(0,0,6,"Отклонение ",0));
        lecod.add(new Elcol(0,0,19,"Численность ",0));
        assert prnq("Количество колонок нижняя табличка еженеДельного отчета iniPrintReportDayDwn "+lecod.size());
    }//iniPrintReportDayDwn

    public static ArrayList<Elcol> lecol = new ArrayList<>(36);
    public static void iniPrintReportDay() {
        Elcol.claerCount();
        int sk=6;
        lecol.add(new Elcol(2,4,1200,9,"№ п.п.",19));//1200,"№ п.п.",2,14,4
        lecol.add(new Elcol(2,4,2300,4,"Исполнитель",8));//2300,"Исполнитель",2,6,3
        lecol.add(new Elcol(2,4,8000,4,"Наименование работ",3));//8000,"Наименование работ",2,2,3
        lecol.add(new Elcol(3,4,2000,4,"№ сметы",5));//2000,"№ сметы",3,4,3
        lecol.add(new Elcol(3,4,2000,4,"Инв № чертежа",4));//2000,"Инв № чертежа",3,3,3
        lecol.add(new Elcol(3,4,2000,4,"Ед. изм.",7));//2000,"Ед. изм.",3,7,4
        lecol.add(new Elcol(3,4,2000,sk,"Всего по проекту",10));//2000,"Всего по проекту",3,8,sk
        lecol.add(new Elcol(3,4,2000,sk,"Отстаок на 15.10.н",11));//2000,"Отстаок на 15.10.н",3,9,sk
        lecol.add(new Elcol(4,4,1500,sk,"План",12));//1500,"План",4,10,sk
        lecol.add(new Elcol(4,1500,sk,"Факт","R9+V9+Z9+AD9"));//1500,"Факт",4,0,sk,"R9+V9+Z9+AD9"
        lecol.add(new Elcol(4,1500,sk,"Отклонение","R9-Q9"));//1500,"Отклонение",4,0,sk,"Q9+U9-R9-V9"
        lecol.add(new Elcol(4,2000,sk,"План",16,true));//пллан стоимости на период в текущих//2000,"План",4,17,sk
        lecol.add(new Elcol(4,2000,sk,"Факт","T9+X9+AB9+AF9",true));//2000,"Факт",4,0,sk,"T9+X9+AB9+AF9"
        lecol.add(new Elcol(4,2000,sk,"Отклонение","P9-O9",true));//2000,"Отклонение",4,0,sk,"P9-O9"
        lecol.add(new Elcol(4,2000,sk,"План","S9+W9+AA9+AE9",true));//2000,"План",4,0,sk,"S9"
        lecol.add(new Elcol(4,2000,sk,"Факт","T9+X9+AB9+AF9",true));//2000,"Факт",4,0,sk,"T9"
        lecol.add(new Elcol(4,1500,sk,"План",23));//1500,"План",4,15,sk
        lecol.add(new Elcol(4,1500,sk,"Факт",23));//1500,"Факт",4,15,sk
        lecol.add(new Elcol(4,2000,sk,"План","$L9/$I9*Q9"));//2000,"План",4,0,sk,"$L9/$I9*Q9"
        lecol.add(new Elcol(4,2000,sk,"Факт","$L9/$I9*R9"));//2000,"Факт",4,0,sk,"$L9/$I9*R9"
        lecol.add(new Elcol(4,1500,sk,"План",23));//1500,"План",4,15,sk
        lecol.add(new Elcol(4,1500,sk,"Факт",23));//1500,"Факт",4,15,sk
        lecol.add(new Elcol(4,2000,sk,"План","$L9/$I9*U9"));//2000,"План",4,0,sk,"$L9/$I9*U9"
        lecol.add(new Elcol(4,2000,sk,"Факт","$L9/$I9*V9"));//2000,"Факт",4,0,sk,"$L9/$I9*V9"
        lecol.add(new Elcol(4,1500,sk,"План",23));//1500,"План",4,15,sk
        lecol.add(new Elcol(4,1500,sk,"Факт",23));//1500,"Факт",4,15,sk
        lecol.add(new Elcol(4,2000,sk,"План","$L9/$I9*Y9"));//2000,"План",4,0,sk,"$L9/$I9*Y9"
        lecol.add(new Elcol(4,2000,sk,"Факт","$L9/$I9*Z9"));//2000,"Факт",4,0,sk,"$L9/$I9*Z9"
        lecol.add(new Elcol(4,1500,sk,"План",23));//1500,"План",4,15,sk
        lecol.add(new Elcol(4,1500,sk,"Факт",23));//1500,"Факт",4,15,sk
        lecol.add(new Elcol(4,2000,sk,"План","$L9/$I9*AC9"));//2000,"План",4,0,sk,"$L9/$I9*AC9"
        lecol.add(new Elcol(4,2000,sk,"Факт","$L9/$I9*AD9"));//2000,"Факт",4,0,sk,"$L9/$I9*AD9"
        lecol.add(new Elcol(2,4,3000,4,"Причины отклонений от плана  ",0));//3000,"Причины отклонений от плана",2,16,4
        lecol.add(new Elcol(4,2000,sk,"_","Q9+U9+Y9+AC9"));//
        assert prnq("Количество колонок еженеДельного отчета iniPrintReportDay "+lecol.size());
    }//iniPrintReportDay

    public static void iniPrintReportMount() {//ормирования ежемесячного отчета wtum
        Elcol.claerCount();
        wtum.add(new Elcol(1,1100,9, "№№",  19)); //1100, "№№", 1, 14, 4
        wtum.add(new Elcol(1,7000, 4,"№ сметы", 6));//7000, "№ сметы", 1, 5, 3
        wtum.add(new Elcol(1,12500, 4,"наименование работы", 3));//12500, "наименование работы", 1, 2, 3
        wtum.add(new Elcol(1,2000, 6,"Сметная стоимость в базовых ценах 2000г., руб.", 13));//2000, "Сметная стоимость в базовых ценах 2000г., руб.", 1, 11, 5
        wtum.add(new Elcol(1,2000, 6,"остаток на начало периода", 14));//2000, "остаток на начало периода", 1, 12, 5
        wtum.add(new Elcol(1,2000, 6,"план на период", 15));//2000, "план на период", 1, 13, 5
        wtum.add(new Elcol(1,2000, 6,"факт за период", 0));//2000, "факт за период", 1, 15, 5
        wtum.add(new Elcol(1,2000, 9,"Исполнитель", 8));//2000, "Исполнитель", 1, 6, 3
        wtum.add(new Elcol(1,5200, 4,"примечание", 0));//5200, "примечание", 1, 16, 3
        assert prnq("Количество колонок ежеМесячного отчета iniPrintReportMount "+wtum.size());
    }//iniPrintReportMount
    public static ArrayList<ElTitl> lsTitlRepMnt = new ArrayList<ElTitl>(15);
    public static void iniTitlReportMount(){
        lsTitlRepMnt.add(new ElTitl(0,1,7,'C',1295,12,false,"ООО \"УС БАЭС\" \n  за период с 16.01.2020г.по16.02.2020г. \n \"Строительство модуля фабрикации и пускового комплекса рефабрикации плотного смешанного уранплутониевого топлива для реакторов на быстрых нейтронах\" "));
//        lsTitlRepMnt.add(new ElTitl(0,1,8,'C',885,14,true,"О"));
    }//IniTitlReportMount
    public static ArrayList<ElTitl> lsTitlRepDay = new ArrayList<ElTitl>(15);
    public static void iniTitlReportDay() {
        lsTitlRepDay.add(new ElTitl(0,3,11,'C',955,14,true,"Еженедельный отчет по ТП выполения СМР МФР за период  _______ 2020 "));
        lsTitlRepDay.add(new ElTitl(1,3,11,'C',355,14,true,"По состоянию на __.__.2020"));
        lsTitlRepDay.add(new ElTitl(2,2,3,4,-1,"Обоснование "));
        lsTitlRepDay.add(new ElTitl(2,2,5,10,-1,"Физ. Объемы "));
        lsTitlRepDay.add(new ElTitl(2,3,11,13,-1,"Стоимость работ на период ТП, тыс.руб. (в тек.ур.цен.)"));
        lsTitlRepDay.add(new ElTitl(2,3,14,15,-1,"Стоимость работ дату отчета"));
        lsTitlRepDay.add(new ElTitl(2,2,16,19,-1,"1 неделя ТП "));
        lsTitlRepDay.add(new ElTitl(2,2,20,23,-1,"2 неделя ТП "));
        lsTitlRepDay.add(new ElTitl(2,2,24,27,-1,"3 неделя ТП "));
        lsTitlRepDay.add(new ElTitl(2,2,28,31,-1,"4 неделя ТП "));
        lsTitlRepDay.add(new ElTitl(3,3,8,10,-1,"На период ТП"));
        lsTitlRepDay.add(new ElTitl(3,3,16,17,-1,"Физ. Объемы "));
        lsTitlRepDay.add(new ElTitl(3,3,18,19,-1,"Стоимость работ"));
        lsTitlRepDay.add(new ElTitl(3,3,20,21,-1,"Физ. Объем"));
        lsTitlRepDay.add(new ElTitl(3,3,22,23,-1,"Стоимость работ"));
        lsTitlRepDay.add(new ElTitl(3,3,24,25,-1,"Физ. Объем"));
        lsTitlRepDay.add(new ElTitl(3,3,26,27,-1,"Стоимость работ"));
        lsTitlRepDay.add(new ElTitl(3,3,28,29,-1,"Физ. Объем"));
        lsTitlRepDay.add(new ElTitl(3,3,30,31,-1,"Стоимость работ"));
        assert prnq("Количество элементов шапки документа iniTitlReportDay "+lsTitlRepDay.size());
    }//iniTitlReportDay
        /*

    //*******************
    int colsz=wtu.size()-1;
    prnq("Количество колонок "+(colsz+1));

     */
    //----------------------------------------------------------------------
    public static void iniColreadFlileXPlan() {//список распознавания колонок исходника
        //1-произвольная строка 2-сисметатизированная строка 3-индикатор
        // 4-целое число 5-вещественное число 6-стоимость 7-дата
        // 8-системное пустое поле 9-формула
//        ERcol.clearcnt();
        clin.add(new ERcol(19,"№ п/п"));//обязательное поле, "№ п/п"
        clin.add(new ERcol(4,"Инв. Чертежа "));//, "Инв. Чертежа "
        clin.add(new ERcol(5,"Инв. номера объектных и локальных смет"));//, "Инв. номера объектных и локальных смет"
        clin.add(new ERcol(6,"Локальный номер сметы"));//, "Локальный номер сметы"
        clin.add(new ERcol(3,"Наименование объектных, локальных смет"));//, "Наименование объектных, локальных смет"
        clin.add(new ERcol(0,"Комментарии"));//, "Комментарии"
        clin.add(new ERcol(7,"Ед. изм. физобъема"));//, "Ед. изм. физобъема"
        clin.add(new ERcol(10,"Всего по смете"));//, "Всего по смете"
        clin.add(new ERcol(0,"Остаток физ.объема на  15.10.2020г."));//, "Остаток физ.объема на  15.10.2020г."
        clin.add(new ERcol(0,"Объём по плану с 16.12.2020 г. по 15.01.2021 г."));//, "Объём по плану с 16.12.2020 г. по 15.01.2021 г."
        clin.add(new ERcol(13,"Всего СМР, тыс. руб."));//, "Всего СМР, тыс. руб."
        clin.add(new ERcol(9,"Трудоёмкость (чел/час)"));//, "Трудоёмкость (чел/час)"
        clin.add(new ERcol(14,"Остаток  на 15.10.2020г. "));//, "Остаток  на 15.10.2020г. "
        clin.add(new ERcol(15,"в базовых ценах 2000 г. "));//, "в базовых ценах 2000 г. "
        clin.add(new ERcol(16,"в текущем уровне цен"));//, "в текущем уровне цен"
        clin.add(new ERcol(0,"Трудоёмкость "));//, "Трудоёмкость "
        clin.add(new ERcol(8,"Исполнитель"));//, "Исполнитель"
    }
    //------------------------------------------------------------------------------
    public static void iniColInputFileTxt(){//список распознавания колонок исходника
        //1-произвольная строка 2-сисметатизированная строка 3-индикатор
        // 4-целое число 5-вещественное число 6-стоимость 7-дата
        // 8-системное пустое поле 9-формула
        clin.add(new ERcol(1,"№ работы"));//обязательное поле,"№№",1
        clin.add(new ERcol(2,"%idBase"));//,"№ по графику",2
        clin.add(new ERcol(4,"!RD"));//,"№ РД",4
        clin.add(new ERcol(17,"!измRD"));//,"№изменения РД",17
        clin.add(new ERcol(18,"!sRDin"));//,"Дата изменения",18
        clin.add(new ERcol(5,"№ сметы"));//,"№ сметы",5
        clin.add(new ERcol(6,"&smeta_sim"));//,"код сметы",6

        clin.add(new ERcol(9,"%piplSm100"));//,"трудозатраты всего",9
        clin.add(new ERcol(7,".ед.изм"));//,"Единица измерения",7
        clin.add(new ERcol(10,"%volSm100"));//,"Количество всего",10
        clin.add(new ERcol(13,"%cstSm100"));//,"Стоимость всего",13
        clin.add(new ERcol(14,"%cstSMost"));//,"Остаток стоимости",14
        clin.add(new ERcol(15,"%cstSNpln"));//,"План по стоимости",15
        clin.add(new ERcol(3,"Название операции"));//,"Название операции",3
    }//inClin
    //------------------------------------------------------------------------------
    public static void inClin(){//список распознавания колонок исходника
        //1-произвольная строка 2-сисметатизированная строка 3-индикатор
        // 4-целое число 5-вещественное число 6-стоимость 7-дата
        // 8-системное пустое поле 9-формула
        clin.add(new ERcol(1,"№ работы"));//обязательное поле,"№№",1
        clin.add(new ERcol(2,"%idBase"));//,"№ по графику",2
        clin.add(new ERcol(4,"!RD"));//,"№ РД",4
        clin.add(new ERcol(17,"!измRD"));//,"№изменения РД",17
        clin.add(new ERcol(18,"!sRDin"));//,"Дата изменения",18
        clin.add(new ERcol(5,"№ сметы"));//,"№ сметы",5
        clin.add(new ERcol(6,"&smeta_sim"));//,"код сметы",6

        clin.add(new ERcol(9,"%piplSm100"));//,"трудозатраты всего",9
        clin.add(new ERcol(7,".ед.изм"));//,"Единица измерения",7
        clin.add(new ERcol(10,"%volSm100"));//,"Количество всего",10
        clin.add(new ERcol(13,"%cstSm100"));//,"Стоимость всего",13
        clin.add(new ERcol(14,"%cstSMost"));//,"Остаток стоимости",14
        clin.add(new ERcol(15,"%cstSNpln"));//,"План по стоимости",15
        clin.add(new ERcol(3,"Название операции"));//,"Название операции",3
    }//inClin
    //инициализация списка содержания отчета
    public static ArrayList<Elcol> wtu = new ArrayList<>(36);
    public static void iniColReportTemplan(){
        wtu.add(new Elcol(6,7,800,5,"№ п.п.",19));//4
        wtu.add(new Elcol(6,7,2300,4,"№ РД",4));//3
        wtu.add(new Elcol(6,7,900,5,"№ изм.",17));//4
        wtu.add(new Elcol(6,7,2200,4,"Инвентарный № сметы",5));//7
        wtu.add(new Elcol(6,7,2200,4,"Локальный № сметы",6));//3
        wtu.add(new Elcol(6,7,3700,4,"Наименование",3));//8
        wtu.add(new Elcol(6,7,900,9,"Единица измерения",7));//8
        wtu.add(new Elcol(7,1600,6,"Всего по смете",10));//8
        wtu.add(new Elcol(7,1600,6,"Остаток по смете","H9*M9/K9"));//3
        wtu.add(new Elcol(7,1600,6,"Запланировано","H9*N9/K9"));//7
        wtu.add(new Elcol(7,1600,6,"Всего СМР",13,true));//4
        wtu.add(new Elcol(7,1600,6,"Трудоемкость",9));//4
        wtu.add(new Elcol(6,7,1600,6,"Остаток по смете",14,true));//4
        wtu.add(new Elcol(7,1600,6,"Запланировано в баз. ценах",15,true));//4
        wtu.add(new Elcol(7,1600,6,"Запланировано в текущих ценах","$R$6*N9",true));//4
        wtu.add(new Elcol(7,1600,6,"Запланированная трудоемкость","L9*N9/K9"));//4
        wtu.add(new Elcol(6,7,1600,4,"Исполнитель",8));//4
        wtu.add(new Elcol(6,7,1600,4,"Примечание",0));//4
        wtu.add(new Elcol(6,7,1600,9,"ИД работы",1));//4
        wtu.add(new Elcol(6,7,1600,9,"ИД по графику",2));//4
    }//inCol

    //инициализация значений статуса документв
    public static Map<String, Character> slst= new HashMap<String, Character>(25);//список значений статуса
    //инициализация значений статуса документв
    public static boolean inSlst(){
        //1=работаем 2=новое значение и работаем 3=идет корректировка
        // 4=не официально 5=анулировано, возвращено 6=ссылка 7=без номера
        slst.putIfAbsent("в реестре СХК нет",'+');//в реестре СХК нет
        slst.putIfAbsent("в реестре СХК нет",'+');//в реестре СХК нет
        slst.putIfAbsent("новый",'+');//новый раздел - СФ, срок АП-11.10.2019 (письмо СХК№11-11-110/20836 от 05.09.2019 (вх.2817)
        slst.putIfAbsent("у_заказчик",'К');//У_заказчик //У_Заказчика
        slst.putIfAbsent("неоф",'К');//Неоф.
        slst.putIfAbsent("сигн",'К');//Сигнал//сигн. 16.01.2019 (расположена в МФР РД- ОВ)
        slst.putIfAbsent("изм",'+');//изм.
        slst.putIfAbsent("взамен",'+');
        slst.putIfAbsent("замена",'В');//замена//Замена на нов. РД
        slst.putIfAbsent("доп",'+');//Доп//Доп.
        slst.putIfAbsent("возврат",'В');//Возврат//Возврат в СХК//возврат
        slst.putIfAbsent("коррект",'К');//Коррект.
        slst.putIfAbsent("корект",'К');//Корект.
        slst.putIfAbsent("кз",'К');//КЗ-1001
        slst.putIfAbsent("аннул",'А');//Аннул.//РД Аннул. от 15.02.2019 письмом СХК №11-11/04-12/1434, в связи с ТР "О замене охлаждения оборуд. холодильной станции (зд.4) с водяного на
        slst.putIfAbsent("согл",'+');//Соглас.//Согл.АП И6
        slst.putIfAbsent("что то",'+');//Что то напутанно с названием тит.л.
        slst.putIfAbsent("расположен",'@');//ЛС расположена в разделе ТХ//РД расположена в разделе внеплощ.сетей электроснабжения
        // расположение РД в разделе КЖ зд.4
        // смета расположена в разделе СК на отм.0,000
        slst.putIfAbsent("работы",'В');//Работы ПЭМ
        slst.putIfAbsent("изменен",'К');//Ждем изменение РД по 22/4 и 64/22, из-за нового расположения Склада готовой продукции  (Штаб №35 вх.2531 п.6)
//        slst.putIfAbsent("ов",1);//ОВ
//        slst.putIfAbsent("ак",1);//АК
//        slst.putIfAbsent("металлокон",1);//Металлоконструкции отнесены к оборудованию смотреть РКД (данные от Балина 12.10.2018); см.18-00488(ТХ)
//        slst.putIfAbsent("",1);
        //--------------------------------------------------------------------------
        return true;
    }//inSlst

}//