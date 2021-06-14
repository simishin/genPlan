/**
 * карточка Заказчика содержащая список вопросов с привязкой к письмам
 */
package qwr.model.nexus;

import qwr.footing.ArrItm;
import qwr.footing.LineGuide;
import qwr.footing.ListElm;
import qwr.footing.Ticked;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static qwr.util.BgFile.prnq;
import static qwr.util.BgFile.sepr;

public class ECard extends Ticked {

    public static ArrItm    m = new ArrItm(12);

    public static ArrayList<ListElm> lst =new ArrayList<>(2) {
    };//чертежи
    public static Map<String, ListElm> lsts =new HashMap<>(12);//to lst
    public static Map<Integer,ListElm> lsti =new HashMap<>(12);//to lst
    public static final int sizeAr=17;//количество полей в текстовом файле данных
    private static int count=1;//cчетчик для элементов по умолчанию
    // int       hasp;  //1)ключ, время создания записи в секундах с 1 января 1970г.
    // String    title; //2)номер карточки
    // String    descr; //3)описание элемента
    // long      way;   //4) состояние элемента  1-по умолчанию 3-из локальных
    // int       order; //5)порядок следования
    //----------------------
    // int     ancei;  //6 )КЛЮЧ предок
    // int     level;  //7 )уровень вложенности
    // int     owner;  //8 )идентификатор создателя записи
    // int     chang;  //9 )время редактирования записи в секундах с 1 января 1970г.
    // int     itypf;  //10)адресат
    // int     ityps;  //11)исполнитель
    // int     itypt;  //12)кто подписал
    // String  link;   //13)ссылка на содержание
    // String  notes;  //14)примечание Статус, резолюция
    //-----------------------
    private String  adrs;   //15)адресат
    private String  auth;   //16)исполнитель
    private String  sign;   //17)кто подписал
    private ArrayList<Integer> mails;  //список писем
    private ArrayList<Integer> xxxx;    //список замечаний
    //конструкторы-----------------------------------------------------------------
    public          ECard() { super("", "", 0, -1);
        adrs =""; auth =""; sign =""; } //EMail---------

    public          ECard(String title,  long way, int order) {
        super(title, "@", way, order); adrs =""; auth =""; sign =""; }

    public          ECard(String title,  long way, int order, String descr) {
        super(title, descr, way, order); adrs =""; auth =""; sign =""; }
    //специальный конструктор--------------------------------------------------------
    public          ECard (String[] words){ super(words);
        this.adrs =(words[15]);
        this.auth =(words[16]);
        this.sign =(words[17]);
    }//  ECard-----------------------------------------------------------------------

    public static void printList(String s) { m.printList(s); }
    public static void printMaps(String s) { m.printMaps(s); }
    @Override//----------------------------------------------------------------------
    public String   writ() {
        return super.writ()+ (adrs.isEmpty()?"~": adrs) +sepr+
                (auth.isEmpty()?"~": auth) +sepr+ (sign.isEmpty()?"~": sign) +sepr;
    }//writ---------------------------

    @Override //перекрытие новым элементом станрого----------------------------------
    public void overlap(ListElm obj) {
        super.overlap(obj);
        this.adrs=((ECard) obj).adrs;
        this.auth=((ECard) obj).auth;
        this.sign=((ECard) obj).sign;
    } //overlap----------------------------------------------------------------------

    @Override //замещение этого элемента новым obj,
    public void replace(ListElm obj, int src) {
        prnq("& ECard > replace NO DEFINE SOURCE "+src);
    } //replace

    @Override
    public String   print() {
        return super.print();
    }

    @Override
    public String   getKey() { return title; }
    public boolean  addElm(){ return super.addElm(lst,lsti,lsts); } //addElm

    public String   getAdrs() { return adrs; }
    public void     setAdrs(String adrs) { this.adrs = adrs; }
    public String   getAuth() { return auth; }
    public void     setAuth(String auth) { this.auth = auth; }
    public String   getSign() { return sign; }
    public void     setSign(String sign) { this.sign = sign; }

    public boolean addElm(int src){ return (super.addElm(m,src)>0); }
//    public static void     setSizAr(int sizeAr){ m.setSizAr(sizeAr);}
}   //class ECleam ==================================================================
