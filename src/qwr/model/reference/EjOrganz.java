/**
 * Описание организаций
 * не реализована привязка организации к типу организации
 */
package qwr.model.reference;

import qwr.Inizial.InGuid;
import qwr.footing.*;
import qwr.model.SharSystem.GrRecords;

import java.util.HashSet;

public class EjOrganz extends TreeGuide implements InfcElm {//организации
    public static final int sizeAr=8;//количество полей в текстовом файле данных
    protected static int count=1;//cчетчик для элементов по умолчанию
    // int       hasp;  //1)ключ, идентификатор элемента
    // String    title; //2)наименование элемента
    // String    descr; //3)описание элемента
    // long      stat;  //4) состояние элемента
    // int       order; //5)порядок следования
    // String    ances; //7)предок
    // int       ancei; //6)предок
    //конструкторы-----------------------------------------------------------------
    public EjOrganz(String ances, String title, String descr, int stat) {
        super( ances, title, descr,stat,count++);
        hasp=LineGuide.questHasp(InGuid.lnOrganz,hasp);}
    //специальный конструктор--------------------------------------------------------
    public EjOrganz(String[] words) { super(words); }

    public String getKey() { return String.valueOf(title); }

    @Override
    public boolean addElm() {
        return false;
    }

    public String writ() { return super.writ();}//---------------------------------

    @Override
    public void replace(ListElm obj, int src) {

    }

    /**
     * Приведение к стандарту встречающихся исполнителей в исходных документах
     * таблица ссответствий находится в списке InGuid.lnAuthor типа Map<String,EiAuthor>
     *     Проверяю на наличие в базе.если элемент найден, то делаю подстановку
     * @param str строка исходного не нормализованного документа
     * @return нормализованное значение
     */
    public static String normalize(String str){
        assert str!=null: "! EiOrganz > normalize: str = null !";
        if (str.length()<3) return " ";//строка слишком мала - не проверяю
//        assert prnq("normalize> "+str);
        if (InGuid.lnOrganz.containsKey(str)) return InGuid.lnOrganz.get(str).getTitle();
        //элемент не найден. пополняю список
        InGuid.lnOrganz.putIfAbsent(str, new EjOrganz(" ",str,str,17));
        GrRecords.ORGNZ.shift();//ставлю флаг наличия модификации списка
        return str;

    }//normalize---------------------------------------------------------------------
    /**
     * добавление в список недостающих значений полыенных из подчиненного списа
     * по окончанию пополнения список очищается
     * @param dearth  список недостающих значений
     */
    public static void addisen(HashSet<String> dearth, int stat) {
        for (String s:dearth){
            InGuid.lnOrganz.putIfAbsent(s, new EjAuthor( s,"",stat));
        }//for dearth
        dearth.clear();//удаляю все элементы после подгрузки
    } //addisen--------------------------------------------------------------------
    /**
     * Добавление элемента из справочника по умолчанию
     * @param titul - наименование которое будет указываться в ссылках
     */
    public static void addisen( String titul, String ances){
        assert titul!=null: "! EiOrganz > addisen: titul = null !";
        assert ances!=null: "! EiOrganz > addisen: ances = null !";
        int r=LineGuide.integrate(InGuid.lnOrganz,new EjOrganz(ances, titul,"",4),1);
    }//addisen-----------------------------------------------------------------------
    /**
     * Добавление элемента из справочника по умолчанию
     * @param titul - наименование которое будет указываться в ссылках
     * @param alias- наименованиекоторое присутствует в не нормированных документах
     */
    public static void addiAli( String titul, String alias){
        assert titul!=null: "! EiOrganz > addiAli: titul = null !";
        assert alias!=null: "! EiOrganz > addiAli: alias = null !";
//        assert prnq(">>> "+"\t"+titul+"  \t~"+alias);
        if (InGuid.lnOrganz.size()<1){
            InGuid.lnOrganz.putIfAbsent(titul, new EjOrganz( "",titul,titul,17));
            InGuid.lnOrganz.putIfAbsent(alias, new AltTitle( titul,alias));
            return;}
        for (InfcElm vl:InGuid.lnOrganz.values()){
            if (vl.getAlias().equals(alias))   return;
            if (vl.getTitle().equals(titul)) {
                InGuid.lnOrganz.putIfAbsent(alias, new AltTitle( titul,alias));
                return;
            }//if
        }  //for  lnOrganz
        InGuid.lnOrganz.putIfAbsent(titul, new EjOrganz("", titul,titul,17));
        InGuid.lnOrganz.putIfAbsent(alias, new AltTitle( titul,alias));
    }//addisen-----------------------------------------------------------------------
    @Override
    public void overlay(InfcElm obj) { super.overlay(obj); }
}// class EiOrganz ==================================================================
