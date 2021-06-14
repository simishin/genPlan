/**
 * Список писем
 */
package qwr.model.nexus;

import qwr.footing.*;
import qwr.model.SharSystem.GrRecords;
import qwr.util.BgFile;

import java.util.HashMap;
import java.util.Map;

import static qwr.util.BgFile.prnt;
import static qwr.util.BgFile.sepr;

public class EMail extends TreeGuide implements InfcElm, LoadFile {
    public static Map<String, InfcElm> mar =new HashMap<>(2400);//чертежи
    public static final int sizeAr=19;//количество полей в текстовом файле данных
    private static int count=1;//cчетчик для элементов по умолчанию
    // int          hasp;   //1 )ключ, время создания записи в секундах с 1 января 1970г.
    // String       title;  //2B)номер письма регистрационный
    // String       descr;  //3I)тема письма
    // long         way;    //4 ) состояние элемента  1-по умолчанию 3-из локальных
    // int          order;  //5 )порядок следования
    //----------------------
    // String   ances;      //7 )предок номер и дата письма на которое дается ответ
    // int      ancei;      //6 )предок ключ письма на которое дается ответ
    // int      level;      //8 )уровень вложенности
    //----------------------
    private int     owner;  //9 )идентификатор создателя записи
    private int     chang;  //10)время редактирования записи в секундах с 1 января 1970г.
    private String  link;   //11)ссылка на содержание
    //-----------------------
    private int     typ;    //12)вид письма, исходящее(>0)/входящее(<0)
    private int     dinc;   //13)дата письма (дней с 1970г.)
    private int     dtit;   //14)дата официального получения (дней с 1970г.)
    private String  incn;   //15)входящий номер письма
    private String  adrs;   //16)адресат
    private String  auth;   //17)исполнитель письма письма(* String)
    private String  sign;   //18)кто подписал исходящее
    private String  stag;   //19)Статус, резолюция письма
    //конструкторы-----------------------------------------------------------------
    public EMail(String title, String descr, long stas, int order) {
        super("",title, descr, stas, order);
        this.owner  = BgFile.getUserIdPrj();//идентификатор пользователя
        this.hasp   = LineGuide.questHasp(mar);//cек1.01.1970
        this.chang  = this.hasp;//cек1.01.1970
        link=" "; incn =" "; adrs =""; auth =""; sign =""; stag=""; } //EMail---------
    public EMail() { super("","", "", 0, 0);
        this.owner  = BgFile.getUserIdPrj();//идентификатор пользователя
        this.hasp   = LineGuide.questHasp(mar);//cек1.01.1970
        this.chang  = this.hasp;//cек1.01.1970
        link=" "; incn =" "; adrs =""; auth =""; sign =""; stag=""; } //EMail---------
    public EMail(String nmail, int dmail, String fio){
        super("",nmail, " ", 17, count++);
        incn =nmail;
        dtit =dmail;
        dinc =dmail;
        auth =(fio.isEmpty()?" ": fio);
        sign =(fio.isEmpty()?" ": fio);
        this.owner   = BgFile.getUserIdPrj();//идентификатор пользователя
        this.hasp    = LineGuide.questHasp(mar);//cек1.01.1970
        this.chang   = this.hasp;//cек1.01.1970
        link=" "; adrs =""; stag="";
    }
    //специальный конструктор--------------------------------------------------------
    public EMail(String[] words) {
        super(words);
        this.owner =Integer.parseInt (words[9]); //9 )идентификатор создателя записи
        this.chang =Integer.parseInt (words[10]);//10)время редактирования в секундах с 1 января 1970г.
        this.link =words[11];                    //11)ссылка на содержание
        this.typ =Integer.parseInt (words[12]); //12)вид письма, исходящее(>0)/входящее(<0)
        this.dinc =Integer.parseInt (words[13]); //13)дата письма (дней с 1970г.)
        this.dtit =Integer.parseInt (words[14]); //14)дата официального получения (дней с 1970г.)
        this.incn =(words[15]);                //15)входящий номер письма
        this.adrs =(words[16]);                 //16)адресат
        this.auth =(words[17]);                 //17)исполнитель письма письма(* String)
        this.sign =(words[18]);                 //18)кто подписал исходящее
        this.stag =(words[19]);                 //19)Статус, резолюция письма
    }  //-----------------------------

    /**
     * полное копирование принимаемого элемента в элемент колекции кроме наименования
     * @param itm   принимаемый элемент
     * @return
     */
    @Override
    public void overlay(InfcElm itm) {
        EDraft obj = (EDraft) itm;
        super.overlay(itm);
        this.owner  =obj.getOwner();//9 )идентификатор создателя записи
        this.chang  =obj.getChang();//10)время редактирования записи в секундах с 1 января 1970г.
        this.link   =obj.getLink(); //11)ссылка на содержание
        this.typ =obj.getIzm();  //12)вид письма, исходящее(>0)/входящее(<0)
        this.dinc =obj.getDtv();  //13)дата письма (дней с 1970г.)
        this.dtit =obj.getDpv();  //14)дата официального получения (дней с 1970г.)
        this.incn =obj.getBsdoc();//15)входящий номер письма
        this.adrs =obj.getBild(); //16)адресат
        this.auth =obj.getRazd(); //17)исполнитель письма письма(* String)
        this.sign =obj.getVidr(); //18)кто подписал исходящее
        this.stag   =obj.getStag(); //19)Статус, резолюция письма
    }  //copy------------------------------------------------------------------------
    @Override//----------------------------------------------------------------------
    public boolean  compare(InfcElm itm) { //если равны, то истина (кроме статуса )
        EMail obj = (EMail) itm;
//        assert prnq(title+" \t"+super.compare(itm)+"\t"+izp+"/"+obj.getIzp());
        return super.compare(itm)
                && owner==obj.getOwner() //9 )идентификатор создателя записи
                && chang==obj.getChang() //10)время редактирования записи в секундах с 1 января 1970г.
                && link.equals(obj.getLink())   //11)ссылка на содержание
                && typ ==obj.getTyp() //12)вид письма, исходящее(>0)/входящее(<=0)
                && dinc ==obj.getDinc() //13)дата письма (дней с 1970г.)
                && dtit ==obj.getDtit() //14)дата официального получения (дней с 1970г.)
                && incn.equals(obj.getIncn()) //15)тема письма
                && adrs.equals(obj.getAdrs())   //16)адресат
                && auth.equals(obj.getAuth())   //17)исполнитель письма письма(* String)
                && sign.equals(obj.getSign())   //18)кто подписал исходящее
                && stag.equals(obj.getStag());  //19)Статус, резолюция письма
    }//compare-----------------------------------------------------------------------

    @Override//----------------------------------------------------------------------
    public String   writ(){ return super.writ()
            + owner +sepr + chang +sepr + (link.isEmpty()?" ": link) +sepr
            + typ +sepr + dinc +sepr + dtit +sepr + (incn.isEmpty()?" ": incn) +sepr
            + (adrs.isEmpty()?" ": adrs) +sepr + (auth.isEmpty()?" ": auth) +sepr
            + (sign.isEmpty()?" ": sign) +sepr + (stag.isEmpty()?" ":stag ) +sepr;}

    @Override
    public void replace(ListElm obj, int src) {

    }
    //-----------------------------------------------------------------------------
    /**
     * Приведение к стандарту встречающихся записи в исходных документах
     *     Проверяю на наличие в базе.если элемент найден, то делаю подстановку
     * @param nmail номер письма
     * @param dmail дата письма  (DateTim.convertStringR)
     * @param author автор письма (EjAuthor.normalize)
     * @return ключ письма
     */
    public static String normalize(String nmail, int dmail, String author){
        if (nmail==null) return " ";//строка слишком мала - не проверяю
        if (nmail.isBlank()) return " ";//строка слишком мала - не проверяю
        EMail obj;
         for (InfcElm itm:mar.values()){
             if (itm instanceof AltTitle) continue;//на всякий случай проверяю тип
             obj=(EMail) itm;
             if (obj.getTitle().equals(nmail)) { if (obj.getDtit()==dmail) return obj.getKey();}
             if (obj.getIncn().equals(nmail)) { if (obj.getDinc()==dmail) return obj.getKey();}
         }//for map
        //письма не найдено - добавляю
        obj = new EMail(nmail,dmail,author);
//        obj.setOrder(count++);
//        obj.setWay(17);
//        obj.setHasp(LineGuide.questHasp(mar));
        if(mar.putIfAbsent(obj.getKey(),obj)==null) GrRecords.MAILJ.shift();;
        return obj.getKey();
    }//normalize---------------------------------------------------------------------
    /**
     * вызывается из LDocPrj > readFileXslx
     * @param srt разобранная строка нового элемента
     * @param cRD массив сответствия разобранной строки полям нового элемента
     * @param sheetName имя листа таблицы источника нового элемента
     */
//    @Override
    public void     parse(String[] srt, int[] cRD, String sheetName) {
        EMail obj = new EMail();
        for (int i=0; i<cRD.length; i++) {//перебор вариантов полей
            switch (cRD[i]){
                case 0:                break;
                case 1:
                case 2:
                case 5:
                case 6:
                case 7:
                case 8:
                case 3:
                case 4:
                case 9:
                default:prnt("EMail > parse ~"+cRD[i]+">"+srt[i]+" ");continue;//что то пошло не так
            }//switch
        }//for lRctc
        obj.setOrder(count++);
        obj.setWay(3);
        obj.setHasp(LineGuide.questHasp(mar));
//        if (LineGuide.integrate(mar,obj,0)>0) GrRecords.DRAFT.shift();
        return;//добавил в список
    }    //parse---------------------------------------------------------------------
    @Override
    public String   getKey() { return getDtit()+"#"+title; } //-----------------------
    public String   getIncn() { return incn; }
    public void     setIncn(String incn) { this.incn = incn; }
    public String   getAdrs() { return adrs; }
    public void     setAdrs(String adrs) { this.adrs = adrs; }
    public String   getAuth() { return auth; }
    public void     setAuth(String auth) { this.auth = auth; }
    public String   getSign() { return sign; }
    public void     setSign(String sign) { this.sign = sign; }
    public String   getStag() { return stag; }
    public void     setStag(String stag) { this.stag = stag; }
    public int      getTyp() { return typ; }//11C) приведенный номер изменения (* int)
    public void     setTyp(int typ) { this.typ = typ; }//11C) приведенный номер изменения (* int)
    public int      getDinc() { return dinc; } //12D) дата официального получения (дней с 1970г.)
    public void     setDinc(int dinc) { this.dinc = dinc; } //12D) дата официального получения (дней с 1970г.)
    public int      getDtit() { return dtit; }
    public void     setDtit(int dtit) { this.dtit = dtit; }
    public int      getOwner() { return owner; }
    public void     setOwner(int owner) { this.owner = owner; }
    public int      getChang() { return chang; }
    public void     setChang(int chang) { this.chang = chang; }
    public String   getLink() { return link; }
    public void     setLink(String link) { this.link = link; }
    @Override
    public String   print(){  return
            incn +"\t"+ adrs +"   \t"+ auth +"\t"+ sign +"\t."+ stag +".\t"+ typ +"\t"+ dinc +
                    "\n\t"+ super.print();
    } //print------------------------------------------------------------------------

    @Override
    public boolean addElm() {
        return false;
    }

} //class EMail
