package qwr.model.TPlan;
//элемент работы из примаверы

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static qwr.util.BgFile.prnt;

public class ElmTs implements ElmPb {
    private String  idr;    //01 идентификатор работы
    private String  idb;    //02 идентификатор базовой работы
    private String  name;   //03 наименование работы
    private String  rd;     //04 номер раб.док
    private String  smin;   //05 инвентарный номер сметы
    private String  smln;   //06 локальный номер сметы
    private String  edi;    //07 единица измерния
    private String  pipl;   //08 исполнитель
    private double  lhor;   //09 сметые трудозатраты
    private double  ksm;    //10 общее количество
    private double  kost;   //11 остаток на начало ------------
    private double  kpl;    //12 план на период  --------------
    private double  csm;    //13 общая стоимость
    private double  cst;    //14 остаток на начало
    private double  cpl;    //15 пллан стоимости на период
    private double  cpt;    //16 пллан стоимости на период в текущих
    private int     izm;    //17 номер изменения раб.док
    private int     dtiz;   //18 дата изменения раб.док
    private int     nur;    //19 номер записи
    private int     dtbg;   //20 дата начала работы
    private int     dten;   //21 дата окончания работы
    private short   stat;   //22 статус работы 1-не начата,2-выполняется,3-завершена
    private int     tst;    //величина отступа
//    private int     grp;    //количество суммируемых элементов в данной строке

    //--------------------------------------------
    public Object getVal(int i){
        switch (i){
            case 0 :return " ";
            case 1 :return this.idr;    //01 идентификатор работы
            case 2 :return this.idb;    //02 идентификатор базовой работы
            case 3 :return this.name;   //03 наименование раб.док
            case 4 :return this.rd;     //04 раб.док
            case 5 :return this.smin;   //05 инвентарный номер сметы
            case 6 :return this.smln;   //06 локальный номер сметы
            case 7 :return this.edi;    //07 единица измерния
            case 8 :return this.pipl;   //08 исполнитель
            case 9 :return this.lhor;   //09 сметые трудозатраты
            case 10:return this.ksm;    //10 общее количество
            case 11:return this.kost;   //11 остаток на начало ------------
            case 12:return this.kpl;    //12 план на период  --------------
            case 13:return this.csm;    //13 общая стоимость
            case 14:return this.cst;    //14 остаток на начало
            case 15:return this.cpl;    //15 пллан стоимости на период
            case 16:return this.cpt;    //16 пллан стоимости на период в текущих
            case 17:return this.izm;    //17 номер изменения раб.док
            case 18:return this.dtiz;   //18 дата изменения раб.док
            case 19:return this.nur;    //19 номер записи
            case 20:return this.dtbg;   //20 дата начала работы
            case 21:return this.dten;   //21 дата окончания работы
            case 22:return this.stat;   //22 статус работы 1-не начата,2-выполняется,3-завершена
            case 23:return 0;
            default:return " @ "; //не пропустить строку
        }//switch
    }//getVal
    public ElmTs(){
        this.idr    ="";//идентификатор работы
        this.idb    ="";//идентификатор базовой работы
        this.name   ="";//наименование раб.док
        this.rd     ="";//раб.док
        this.smin   ="";//инвентарный номер сметы
        this.smln   ="";//локальный номер сметы
        this.edi    ="";//единица измерния
        this.pipl   ="";//исполнитель
        this.lhor   = 0;//сметые трудозатраты
        this.ksm    = 0;//общее количество
        this.kost   = 0;
        this.kpl    = 0;
        this.csm    = 0;//общая стоимость
        this.cst    = 0;//остаток на начало
        this.cpl    = 0;//пллан стоимости на период
        this.cpt    = 0;//пллан стоимости на период в текущих
        this.izm    = 0;//номер изменения раб.док
        this.dtiz   = 0;//дата изменения раб.док
        this.nur    = 0;
        this.dtbg   = 0;   //20 дата начала работы
        this.dten   = 0;   //21 дата окончания работы
        this.stat   = 0;   //22 статус работы 1-не начата,2-выполняется,3-завершена
        this.tst    = 0;//величина отступа
//        this.grp    =-1;//количество суммируемых элементов в данной строке
    }//Elmnt
    public void clear(){
        this.idr    ="";//идентификатор работы
        this.idb    ="";//идентификатор базовой работы
        this.name   ="";//наименование раб.док
        this.rd     ="";//раб.док
        this.smin   ="";//инвентарный номер сметы
        this.smln   ="";//локальный номер сметы
        this.edi    ="";//единица измерния
        this.pipl   ="";//исполнитель
        this.lhor   = 0;//сметые трудозатраты
        this.ksm    = 0;//общее количество
        this.kost   = 0;
        this.kpl    = 0;
        this.csm    = 0;//общая стоимость
        this.cst    = 0;//остаток на начало
        this.cpl    = 0;//пллан стоимости на период
        this.cpt    = 0;//пллан стоимости на период в текущих
        this.izm    = 0;//номер изменения раб.док
        this.dtiz   = 0;//дата изменения раб.док
        this.nur    = 0;
        this.dtbg   = 0;   //20 дата начала работы
        this.dten   = 0;   //21 дата окончания работы
        this.stat   = 0;   //22 статус работы 1-не начата,2-выполняется,3-завершена
        this.tst    =-1;//величина отступа
//        this.grp    =-1;//количество суммируемых элементов в данной строке
    }//clear
    public ElmTs(ElmTs q){//копирование элемента
        this.idr    = q.idr;    //01 идентификатор работы
        this.idb    = q.idb;    //идентификатор базовой работы
        this.name   = q.name;   //03 наименование работы
        this.rd     = q.rd;     //04 раб.док
        this.smin   = q.smin;   //05 инвентарный номер сметы
        this.smln   = q.smln;   //06 локальный номер сметы
        this.edi    = q.edi;    //07 единица измерния
        this.pipl   = q.pipl;   //08 исполнитель
        this.lhor   = q.lhor;   //09 сметые трудозатраты
        this.ksm    = q.ksm;    //10 общее количество
        this.kost   = q.kost;   //11 остаток на начало ------------
        this.kpl    = q.kpl;    //12 план на период  --------------
        this.csm    = q.csm;    //13 общая стоимость
        this.cst    = q.cst;    //14 остаток на начало
        this.cpl    = q.cpl;    //15 пллан стоимости на период
        this.cpt    = q.cpt;    //16 пллан стоимости на период в текущих
        this.izm    = q.izm;    //17 номер изменения раб.док
        this.dtiz   = q.dtiz;   //18 дата изменения раб.док
        this.nur    = q.nur;    //19 номер записи
        this.dtbg   = q.dtbg;   //20 дата начала работы
        this.dten   = q.dten;   //21 дата окончания работы
        this.stat   = q.stat;   //22 статус работы 1-не начата,2-выполняется,3-завершена
        this.tst    = q.tst;    //-1 пропустить строку/0 оставиь/+1 включить
//        this.grp    = q.grp;    //количество суммируемых элементов в данной строке
    }//Elmnt

    public ElmTs(ElmTs q, int i) {
        this.idr    = q.idr;    //01 идентификатор работы
        this.idb    = q.idb;    //идентификатор базовой работы
        this.name   = q.name;   //03 наименование работы
        this.rd     = q.rd;     //04 раб.док
        this.smin   = q.smin;   //05 инвентарный номер сметы
        this.smln   = q.smln;   //06 локальный номер сметы
        this.edi    = q.edi;    //07 единица измерния
        this.pipl   = q.pipl;   //08 исполнитель
        this.lhor   = q.lhor;   //09 сметые трудозатраты
        this.ksm    = q.ksm;    //10 общее количество
        this.kost   = q.kost;   //11 остаток на начало ------------
        this.kpl    = q.kpl;    //12 план на период  --------------
        this.csm    = q.csm;    //13 общая стоимость
        this.cst    = q.cst;    //14 остаток на начало
        this.cpl    = q.cpl;    //15 пллан стоимости на период
        this.cpt    = q.cpt;    //16 пллан стоимости на период в текущих
        this.izm    = q.izm;    //17 номер изменения раб.док
        this.dtiz   = q.dtiz;   //18 дата изменения раб.док
        this.nur    = q.nur;    //19 номер записи
        this.dtbg   = q.dtbg;   //20 дата начала работы
        this.dten   = q.dten;   //21 дата окончания работы
        this.stat   = q.stat;   //22 статус работы 1-не начата,2-выполняется,3-завершена
        this.tst    = i;    //относительный уровень группировки
//        this.grp    = q.grp;    //количество суммируемых элементов в данной строке
    }
    public void setform(StringBuilder s, int grp){ this.pipl=s.toString(); }
    public void setKost(double kost) { this.kost = kost; }
    public void setKpl(double kpl) { this.kpl = kpl; }

    public double getKsm() { return ksm; }
    public double getCsm() { return csm; }
    public double getCst() { return cst; }
    public double getCpl() { return cpl; }
    public double getCpt() { return cpt; }

    public String   getIdr()  { return idr; }
    public void     setNur(int nur) { this.nur = nur; }
    public int      getNur() {return nur; } //19 номер записи
    public String   getName() { return name; }
    public String   getPipl() { return pipl; }//исполнитель
    public int      getTst()  { return tst; }
    public void     setTst(int tst) { this.tst = tst; }
    public String   getSmin() { return smin; }
    public String   getSmln() { return smln; }
    public String   getRd()   { return rd; }
    //-------------------------------------------------------------------------
    public String   getRdm()  {//только номер РД
        return this.rd.substring(this.rd.lastIndexOf(".")+1); }
    //----- определяю является считанная строка групирующим уровнем или данными ---
    //это группировка если поля смет пусты и
    // есть значение в одном из полей идентификатора или номера РД
    public boolean isGroup(){
        return this.smin.isBlank() && this.smln.isBlank() &&
                (this.idr.isBlank() ^ this.rd.isBlank()); }
    //-----------------------------------------------------------------
    public boolean setstr(int plaze,String s){//Читаю строку и разбрасываю







        //очищаю строку от лишних символов
        s=s.trim();
        int length = s.length();
        char[] y = new char[length+1];
        int newLen = 0;
        y[newLen]= s.charAt(newLen++);
        for (int  j = newLen ; j < length; j++) {
            char ch=s.charAt(j);
            if ((ch > 32) || (ch==32 && y[newLen-1]>32)) {
                y[newLen] = ch;
                newLen++;
            }
        }
         String x = new String(y, 0, newLen);
        //применяю очищенную строку
        switch (plaze){
        case 0:                break;
        case 1:  this.idr  =x; break; //идентификатор (A)
        case 2:  this.idb  =x; break; //02 идентификатор базовой работы
        case 3:  this.name =x; break; //03 наименование раб.док
        case 4:  this.rd   =x; break; //04 раб.док
        case 5:  this.smin =x; break; //05 инвентарный номер сметы
        case 6:  this.smln =x; break; //06 локальный номер сметы
        case 7:  this.edi  =x; break; //07 единица измерния
        case 8:  this.pipl =x; break; //08 исполнитель
        case 9 : this.lhor=cnvert(x); break;  //09 сметые трудозатраты
        case 10: this.ksm= cnvert(x); break;  //общее количество
        case 13: this.csm= cnvert(x); break;  //общая стоимость
        case 14: this.cst= cnvert(x); break;  //остаток на начало
        case 15: this.cpl= cnvert(x); break;  //пллан стоимости на период  (S)
        case 16: this.cpt= cnvert(x); break;  //пллан стоимости на период  (T)
        case 17: this.izm =cnverti(x); break;  //17 номер изменения раб.док
        case 18: this.dtiz=cnvertd(x); break;  //18 дата изменения раб.док
        case 19: if (x.length()>3) {this.idr =x;break;}
                    else this.nur =cnverti(x); break;
        case 20: this.dtbg=cnvertd(x); break;   //20 дата начала работы
        case 21: this.dten=cnvertd(x); break;   //21 дата окончания работы
        case 22: if (x.equalsIgnoreCase("Не начата")) this.stat=1;
                    else if (x.equalsIgnoreCase("Выполняется")) this.stat=2;
                        else this.stat=3;
                break;
        default:prnt("&~"+x+" "); return true; //что то пошло не так
        }
        return false;
    }//setstr
    //---------------------------------------------------------------------
    public boolean setdbl(int plaze,double x){ switch (plaze){//читаю число и разбрасываю
        case 0 :              break;
        case 9 : this.lhor=x; break;  //09 сметые трудозатраты
        case 10: this.ksm=x;  break;  //общее количество
        case 13: this.csm=x;  break;  //общая стоимость
        case 14: this.cst=x;  break;  //остаток на начало
        case 15: this.cpl=x;  break;  //пллан стоимости на период  (S)
        case 16: this.cpt=x;  break;  //16 пллан стоимости на период в текущих
        case 17: this.izm =(int) x; break;  //17 номер изменения раб.док
        case 18: this.dtiz=(int) x; break;  //18 дата изменения раб.док
        case 19: this.nur =(int) x; prnt(" *"+x);
                                    break;  //19 номер записи
        case 20: this.dtbg=(int) x; break;  //20 дата начала работы
        case 21: this.dten=(int) x; break;  //21 дата окончания работы
//        case 11: this.kost=x; break; //остаток на начало
//        case 12: this.kpl=x; break;  //план на период
//        case 24: this.tst=(int) x; prnt("W"+x); break;//-1 пропустить/0 оставиь/+1 включить
        default:  prnt("$~"); return true; //что то пошло не так
        }
        return false;
    }//setdbl
    //---------------------------------------------------------
    public double cnvert(String s){
        return Double.parseDouble(s.replace(".","")
                .replace(".","").replace(',','.'));
    }//cnvert

    public int cnverti(String s){
        try { return Integer.parseInt(s);
        } catch (NumberFormatException e) { prnt("\n Error convert Integer");return -1; }
    }//cnverti

    public int cnvertd(String s){
        try {
            LocalDate ld =  LocalDate.parse(s,  DateTimeFormatter.ofPattern("dd.MM.yy"));
            return (int) ChronoUnit.DAYS.between(LocalDate.of(1899, 12, 30), ld);
        } catch (Exception e){return 0;}
    }//cnvertd
    //-----------------------------------------------------------------------------
    public static String clearCharString( String s) {
        //очищаю строку от лишних символов
//        prnq("- "+s);
        int length = s.length();
        char[] y = new char[length + 1];//создаю пустой массив
        int i=0;
        char cf = 32;
        for (int j = 0; j < length; j++) {
            char ch = s.charAt(j);
            if (ch<32 || ch==32 && cf==ch) continue;
            y[i++]=ch;
            cf=ch;
        }//цикл по строке
       return new String(y, 0, i);
    }//convertString

    //------------------------------------------------------------------------------
    public String prn(){ return "^"+ this.nur +"-"+this.tst+" > "+ this.idr +"~"+
//            this.idb + "  "+this.rd+" & "+this.izm+" : "+this.dtiz+" : "+
//            " % "+ this.name+" > "+
//            this.smin +" : "+ this.smln+
//            ":\n"+
//            this.lhor+" : "+this.edi+
            " s"+this.ksm+" o"+ this.kost+" p"+this.kpl+
            " s"+this.csm+" o "+ this.cst+" p"+ this.cpl+" ="+ this.pipl+
            " ;";
    }
}//class