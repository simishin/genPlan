package qwr.model.TPlan;
//альтернатива class Elmnt для его группировки
// проверка сооветствия объектов по типу --instanceof

import qwr.model.Base.ElGuid;
import qwr.model.Base.TrGuid;

/**
 * Возможные уровни группировки
 * 1-по главам (список) > 2, 7
 * 2-по зданиям (список) > 3
 * 3-по разделам РД (список) > 6
 * 4-по системам (дерево) > 6
 * 5-по рабочей документации (список-дерево) > 4, 6
 * 6-по видам, этапам работ (список)
 * 7-по технологическим группам зданий-сооружений (дерево) > 4
 */
public class ElmGr implements ElmPb {
//    private static final int mtst=-1;//максимальное смещение относительный уровень группировки
    private String  idr;    //01 идентификатор группировки
    private String  name;   //03 описание группировки из справоочника
    private String  pipl;   //08 формула суммирования
//    private int     izm;    //17 номер изменения раб.док = тип группировки
    private int     nur;    //19 номер записи
    private int     tst;    //величина отступа = относительный уровень группировки
    private int     grp;    //количество суммируемых элементов в данной строке

//    public int      getGrp() { return grp; }//количество суммируемых элементов
    public String   getIdr() { return idr; }
    public String   getName() { return name; }
    public int      getNur(){return this.nur; }
    public int      getTst(){return this.tst;}
    @Override
    public void     setTst(int tst) { this.tst = tst; }
    public void     setNur(int i){this.nur =i;}
//    public void     setform(StringBuilder s){ this.pipl=s.toString(); }
    public void     setform(StringBuilder s, int grp){ this.pipl=s.toString(); this.grp=grp;}
    public String   getForm(){return this.pipl;}
//    public String   getRd(){return "";}

    public Object getVal(int i){
        switch (i){
            case 0 :return " ";
            case 1 :return this.idr;    //01 идентификатор работы
            case 3 :return this.name;   //03 наименование раб.док
            case 8 :return this.pipl;   //08 исполнитель
//            case 17:return this.izm;    //17 номер изменения раб.док
            case 19:return this.nur;    //19 номер записи
            default:return " @ "; //не пропустить строку
        }//switch
    }//getVal
    //--------------------------------------------------------------------
    public ElmGr(ElGuid e, int urg) {
        this.idr    =e.getTitul();
        this.name   =e.getDescr();
        this.pipl   ="";//формула суммирования
//        this.izm    =1; //тип группировки
        this.nur    =e.getChang(); //номер записи
        this.tst    =urg; //относительный уровень группировки
        this.grp    =-1;//количество суммируемых элементов в данной строке
    }//ElmGr
    public ElmGr(TrGuid e, int urg) {
        this.idr    =e.getTitul();
        this.name   =e.getDescr();
        this.pipl   ="";//формула суммирования
//        this.izm    =1; //тип группировки
        this.nur    =e.getChang(); //номер записи
        this.tst    =urg; //относительный уровень группировки
        this.grp    =-1;//количество суммируемых элементов в данной строке
    }//ElmGr
    public ElmGr(ElmTs e) {
        this.idr    =e.getIdr();
        this.name   =e.getIdr();
        //this.name   =e.getName();
        this.pipl   ="";//формула суммирования
//        this.izm    =1; //тип группировки
        this.nur    =e.getNur(); //номер записи
        this.tst    =e.getTst(); //относительный уровень группировки
        this.grp    =0;//количество суммируемых элементов в данной строке
    }
    public ElmGr(ElmGr q){//копирование элемента
        this.idr = q.idr;
        this.name= q.name;
        this.pipl= q.pipl;
//        this.izm = q.izm;    //номер изменения раб.док
        this.nur = q.nur;
        this.tst = q.tst;//-1 пропустить строку/0 оставиь/+1 включить
        this.grp = q.grp;//количество суммируемых элементов в данной строке
    }//ElmGr

    public ElmGr(String idr, String name, int tst) {
        this.idr  = idr; //01 идентификатор группировки
        this.name = name;//03 описание группировки из справоочника
        this.pipl = "";  //08 формула суммирования
        this.nur  = 0;   //19 номер записи
        this.tst  = tst; //величина отступа = относительный уровень группировки
        this.grp  = -1;  //количество суммируемых элементов в данной строке
    }

    public ElmGr(){
        this.idr    ="";//идентификатор работы
        this.name   ="";//наименование раб.док
        this.pipl   ="";//исполнитель
//        this.izm    = 0;//номер изменения раб.док
        this.nur    = 0;//номер записи
        this.tst    = 0;//величина отступа
        this.grp    =-1;//количество суммируемых элементов в данной строке
    }//ElmGr
    public void clear(){
        this.idr    ="";//идентификатор работы
        this.name   ="";//наименование раб.док
        this.pipl   ="";//исполнитель
//        this.izm    = 0;//номер изменения раб.док
        this.nur    = 0;
        this.tst    =-1;//величина отступа
        this.grp    =-1;//количество суммируемых элементов в данной строке
    }//clear


    public String prn(){ return ""+ this.nur +"-"+this.tst+">"+ this.idr +"~"
           +" : "+ " % "+ this.name+" > "+ " :~"+ this.pipl+" ;";
    }
}//class ElmGr