package qwr.model.reference;

import qwr.footing.AltTitle;
import qwr.footing.InfcElm;
import qwr.footing.LineGuide;
import qwr.footing.ListElm;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EiStag extends LineGuide implements InfcElm {
    public static Map<String, InfcElm> mar =new HashMap<>(12);
    public static final int sizeAr=5;//количество полей в текстовом файле данных
    private static int count=1;//cчетчик для элементов по умолчанию
    // int       hasp;  //1)ключ, идентификатор элемента
    // String    title; //2)наименование элемента
    // String    descr; //3)описание элемента
    // long      way ;  //4) состояние элемента  1-по умолчанию 3-из локальных
    // int       order; //5)порядок следования
    //конструкторы-------------------------------------------------------------------
    public EiStag(String title, String descr, long way) {
//        super(Math.abs(Objects.hash(title, descr)), title, descr, way, count++);
        super(0, title, descr, way, count++);
        this.hasp=LineGuide.questHasp(mar,Math.abs(Objects.hash(title, descr)));
    }//EiSpecRD----------------------------------------------------------------------


    public EiStag() { super("", "", 0, 0); }
    @Override//----------------------------------------------------------------------
    public String getKey() { return String.valueOf(title); }

    @Override
    public boolean addElm() {
        return false;
    }

    public String writ() { return super.writ();}  //---------------------------------

    @Override
    public void replace(ListElm obj, int src) {

    }

    /**
     * Приведение к стандарту встречающейся документации в исходных документах
     *     Проверяю на наличие в базе.если элемент найден, то делаю подстановку
     * @param str строка исходного не нормализованного документа
     * @return нормализованное значение
     */
    public static String normalize(String str){
        assert str!=null: "! EiStag > normalize: str = null !";
        if (str.isBlank()) return " ";
        if (mar.containsKey(str)) {
            return mar.get(str).getTitle();}
        //элемент не найден. пополняю список
        mar.putIfAbsent(str, new EiChaptr(str,str,17));
        return str;
    }//normalize---------------------------------------------------------------------

    /**
     * Добавление элемента из справочника по умолчанию
     * @param titul - наименование которое будет указываться в ссылках
     */
    public static void addisen( String titul, String descr){
        assert titul!=null: "! EiStag > addisen: titul = null !";
        assert descr!=null: "! EiStag > addisen: str = null !";
        LineGuide.integrate(mar,new EiStag( titul,descr,4),1);
        /*
        if (mar.size()<1){
            mar.putIfAbsent(titul,new EiStag( titul,descr,4));
            return;}
        for(InfcElm vl: mar.values()) if(vl.getTitle().equals(titul)) return;
        mar.putIfAbsent(titul, new EiStag( titul,descr,4));

         */
    }//addisen-----------------------------------------------------------------------
    /**
     * Добавление элемента из справочника по умолчанию
     * @param titul - наименование которое будет указываться в ссылках
     * @param alias- наименованиекоторое присутствует в не нормированных документах
     */
    public static void addiAli( String titul, String alias){
        assert titul!=null: "! EiStag > addiAli: titul = null !";
        assert alias!=null: "! EiStag > addiAli: alias = null !";
        if (mar.size()<1){
            mar.putIfAbsent(titul, new EiStag( titul,titul,17));
            mar.putIfAbsent(alias, new AltTitle( titul,alias));
            return;}
        for (InfcElm vl: mar.values()){
            if (vl.getAlias().equals(alias))   return;
            if (vl.getTitle().equals(titul)) {
                mar.putIfAbsent(alias, new AltTitle( titul,alias));
                return;
            }//if
        }  //for  lnOrganz
        mar.putIfAbsent(titul, new EiStag( titul,titul,17));
        mar.putIfAbsent(alias, new AltTitle( titul,alias));
    }//addisen-----------------------------------------------------------------------
    @Override
    public void overlay(InfcElm obj) { super.overlay(obj); }

} //class EiSpecRD===================================================================
