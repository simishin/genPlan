package qwr.model.reference;

import qwr.footing.AltTitle;
import qwr.footing.InfcElm;
import qwr.footing.LineGuide;
import qwr.footing.ListElm;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EiTypDoc extends LineGuide implements InfcElm {
    public static Map<String, InfcElm> lnSpecRD=new HashMap<>(12);
    public static final int sizeAr=5;//количество полей в текстовом файле данных
    private static int count=1;//cчетчик для элементов по умолчанию
    // int       hasp;  //1)ключ, идентификатор элемента
    // String    title; //2)наименование элемента
    // String    descr; //3)описание элемента
    // long      way ;  //4) состояние элемента  1-по умолчанию 3-из локальных
    // int       order; //5)порядок следования
    //конструкторы-------------------------------------------------------------------
    public EiTypDoc(String title, String descr, long way) {
//        super(Math.abs(Objects.hash(title, descr)), title, descr, way, count++);
        super(0, title, descr, way, count++);
        this.hasp=LineGuide.questHasp(lnSpecRD,Math.abs(Objects.hash(title, descr)));
    }//EiSpecRD----------------------------------------------------------------------


    public EiTypDoc() { super("", "", 0, 0); }
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
        assert str!=null: "! EiSpecRD > normalize: str = null !";
        if (str.isBlank()) return " ";
        if (lnSpecRD.containsKey(str)) {
//            assert prnq("normalize> "+str+">"+InGuid.lnChaptr.get(str).getKey());
            return lnSpecRD.get(str).getTitle();}
        //элемент не найден. пополняю список
        lnSpecRD.putIfAbsent(str, new EiChaptr(str,str,17));
//        GrRecords.CHPTR.shift();//ставлю флаг наличия модификации списка
//        assert prnq("normalize> "+str+"^");
        return str;
    }//normalize---------------------------------------------------------------------

    /**
     * Добавление элемента из справочника по умолчанию
     * @param titul - наименование которое будет указываться в ссылках
     */
    public static void addisen( String titul, String descr){
        assert titul!=null: "! EiSpecRD > addisen: titul = null !";
        assert descr!=null: "! EiSpecRD > addisen: str = null !";
        LineGuide.integrate(lnSpecRD,new EiTypDoc( titul,descr,4),1);
        /*
        if (lnSpecRD.size()<1){
            lnSpecRD.putIfAbsent(titul,new EiSpecRD( titul,descr,4));
            return;}
        for(InfcElm vl:lnSpecRD.values()) if(vl.getTitle().equals(titul)) return;
        lnSpecRD.putIfAbsent(titul, new EiSpecRD( titul,descr,4));

         */
    }//addisen-----------------------------------------------------------------------
    /**
     * Добавление элемента из справочника по умолчанию
     * @param titul - наименование которое будет указываться в ссылках
     * @param alias- наименованиекоторое присутствует в не нормированных документах
     */
    public static void addiAli( String titul, String alias){
        assert titul!=null: "! EiSpecRD > addiAli: titul = null !";
        assert alias!=null: "! EiSpecRD > addiAli: alias = null !";
        if (lnSpecRD.size()<1){
            lnSpecRD.putIfAbsent(titul, new EiTypDoc( titul,titul,17));
            lnSpecRD.putIfAbsent(alias, new AltTitle( titul,alias));
            return;}
        for (InfcElm vl:lnSpecRD.values()){
            if (vl.getAlias().equals(alias))   return;
            if (vl.getTitle().equals(titul)) {
                lnSpecRD.putIfAbsent(alias, new AltTitle( titul,alias));
                return;
            }//if
        }  //for  lnOrganz
        lnSpecRD.putIfAbsent(titul, new EiTypDoc( titul,titul,17));
        lnSpecRD.putIfAbsent(alias, new AltTitle( titul,alias));
    }//addisen-----------------------------------------------------------------------
    @Override
    public void overlay(InfcElm obj) { super.overlay(obj); }

} //class EiSpecRD===================================================================
