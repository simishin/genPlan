package qwr.Inizial;

import qwr.reports.ERcol;

import java.util.ArrayList;

//инициализация объектов для работы с файлами примаверы
public class IniPrim {
    public static ArrayList<ERcol> elst =new ArrayList<ERcol>(50);
    public static void inErcol(){
//        ERcol.clearcnt();//сбрасываю счетчик записей
        //0-строка-значение 1-строка-список 2-индикатор 3-число 4-вещественное число 5-стоимость 6-дата 7-системное пустое поле
        elst.add(new ERcol(0,"delete_record_flag"));//"Delete This Row"
        elst.add(new ERcol(1,"task_code"));//,"Идентификатор операции",1
        elst.add(new ERcol(22,"status_code"));//,"Статус операции",22
        elst.add(new ERcol(0,"wbs_id"));//,"Код ИСР",0
        elst.add(new ERcol(0,"actv_code_idct0lvqsncy0la_id"));//,"!Глава",0
        elst.add(new ERcol(1,4,"actv_code_rd_id"));//,"!RD",1,4
        elst.add(new ERcol(1,17,"actv_code_idc40lfqvfje_id"));//,"!измRD",3,17
        elst.add(new ERcol(6,18,"user_field_11926"));//,"!sRDin",6,18
        elst.add(new ERcol(0,"user_field_11938"));//,"!fRDin",0
        elst.add(new ERcol(0,"user_field_11496"));//,"!Duble",0
        elst.add(new ERcol(0,"actv_code_0jpqntcux9cz0ydqsnge"));//,"ГОД графика 2",0
        elst.add(new ERcol(20,"start_date"));//,"(*)Начало",20
        elst.add(new ERcol(21,"end_date"));//,"(*)Окончание",21
        elst.add(new ERcol(2,"user_field_11939"));//,"%idBase",2
        elst.add(new ERcol(0,"actv_code_idct0lvqsncy0la_id"));//,"!Глава",0
        elst.add(new ERcol(3,"task_name"));//,"Название операции",3
        elst.add(new ERcol(7,"actv_code_ltc10lqu0ljqt9c8_id"));//,".ед.изм",7
        elst.add(new ERcol(5,"user_field_10569"));//,"&invent_sim Актуальный",5
        elst.add(new ERcol(6,"user_field_10568"));//,"&smeta_sim Актуальный",6
        elst.add(new ERcol(0,"user_field_4300"));//,"&invent_sim",0
        elst.add(new ERcol(0,"user_field_11294"));//,"&gross",0
        elst.add(new ERcol(0,"user_field_11295"));//,"&volume",0
        elst.add(new ERcol(0,"user_field_11389"));//,"!N",0
        elst.add(new ERcol(9,"user_field_11940"));//,"%piplSm100",9
        elst.add(new ERcol(10,"user_field_11928"));//,"%volSm100",10
        elst.add(new ERcol(13,"user_field_11941"));//,"%cstSm100",13
        elst.add(new ERcol(14,"user_field_11942"));//,"%cstSMost",14
        elst.add(new ERcol(15,"user_field_11943"));//,"%cstSNpln",15
        elst.add(new ERcol(0,"user_field_11711"));//,"!пл21-06",0
        elst.add(new ERcol(0,"user_field_11712"));//,"!пл21-07",0
        elst.add(new ERcol(0,"user_field_11713"));//,"!пл21-08",0
        elst.add(new ERcol(0,"user_field_11714"));//,"!пл21-09",0
        elst.add(new ERcol(0,"user_field_11715"));//,"!пл21-10",0
        elst.add(new ERcol(0,"user_field_11716"));//,"!пл21-11",0
        elst.add(new ERcol(0,"user_field_11717"));//,"!пл21-12",0
        elst.add(new ERcol(0,"user_field_11922"));//,"!пл21-1кв",0
        elst.add(new ERcol(0,"user_field_11718"));//,"!пл21и",0
        //       elst.add(new ERcol("","",0));
/*
elst.add(new ERcol("delete_record_flag","Delete This Row",0));
        elst.add(new ERcol("task_code","Идентификатор операции",1));
        elst.add(new ERcol("status_code","Статус операции",22));
        elst.add(new ERcol("wbs_id","Код ИСР",0));
        elst.add(new ERcol("actv_code_idct0lvqsncy0la_id","!Глава",0));
        elst.add(new ERcol("actv_code_rd_id","!RD",1,4));
        elst.add(new ERcol("actv_code_idc40lfqvfje_id","!измRD",3,17));
        elst.add(new ERcol("user_field_11926","!sRDin",6,18));
        elst.add(new ERcol("user_field_11938","!fRDin",0));
        elst.add(new ERcol("user_field_11496","!Duble",0));
        elst.add(new ERcol("actv_code_0jpqntcux9cz0ydqsnge","ГОД графика 2",0));
        elst.add(new ERcol("start_date","(*)Начало",20));
        elst.add(new ERcol("end_date","(*)Окончание",21));
        elst.add(new ERcol("user_field_11939","%idBase",2));
        elst.add(new ERcol("actv_code_idct0lvqsncy0la_id","!Глава",0));
        elst.add(new ERcol("task_name","Название операции",3));
        elst.add(new ERcol("actv_code_ltc10lqu0ljqt9c8_id",".ед.изм",7));
        elst.add(new ERcol("user_field_10569","&invent_sim Актуальный",5));
        elst.add(new ERcol("user_field_10568","&smeta_sim Актуальный",6));
        elst.add(new ERcol("user_field_4300","&invent_sim",0));
        elst.add(new ERcol("user_field_11294","&gross",0));
        elst.add(new ERcol("user_field_11295","&volume",0));
        elst.add(new ERcol("user_field_11389","!N",0));
        elst.add(new ERcol("user_field_11940","%piplSm100",9));
        elst.add(new ERcol("user_field_11928","%volSm100",10));
        elst.add(new ERcol("user_field_11941","%cstSm100",13));
        elst.add(new ERcol("user_field_11942","%cstSMost",14));
        elst.add(new ERcol("user_field_11943","%cstSNpln",15));
        elst.add(new ERcol("user_field_11711","!пл21-06",0));
        elst.add(new ERcol("user_field_11712","!пл21-07",0));
        elst.add(new ERcol("user_field_11713","!пл21-08",0));
        elst.add(new ERcol("user_field_11714","!пл21-09",0));
        elst.add(new ERcol("user_field_11715","!пл21-10",0));
        elst.add(new ERcol("user_field_11716","!пл21-11",0));
        elst.add(new ERcol("user_field_11717","!пл21-12",0));
        elst.add(new ERcol("user_field_11922","!пл21-1кв",0));
        elst.add(new ERcol("user_field_11718","!пл21и",0));
 */
    }//inErcol
    public static void ini(){

    }//ini
}// class IniPrim
