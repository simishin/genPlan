package qwr.model.reference;

import qwr.Inizial.InGuid;
import qwr.footing.AltTitle;
import qwr.footing.InfcElm;
import qwr.footing.LineGuide;
import qwr.footing.ListElm;
import qwr.model.SharSystem.GrRecords;

public class EiChaptr extends LineGuide implements InfcElm {
    public static final int sizeAr=5;//количество полей в текстовом файле данных
    protected static int count=1;//cчетчик для элементов по умолчанию
    // int       hasp;  //1)ключ, идентификатор элемента
    // String    title; //2)наименование элемента
    // String    descr; //3)описание элемента
    // long      way;   //4) состояние элемента
    // int       order; //5)порядок следования
    //конструкторы-----------------------------------------------------------------
    public EiChaptr(String title, String descr, int stat) {
        super( title, descr,stat,count++);
        hasp=LineGuide.questHasp(InGuid.lnChaptr,hasp);}
    //специальный конструктор--------------------------------------------------------
    public EiChaptr(String[] words) { super(words); }  //-----------------------------

    //    @Override//----------------------------------------------------------------------
    public String getKey() { return String.valueOf(title); }

    @Override
    public boolean addElm() {
        return false;
    }

    public String writ() { return super.writ();}

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
        assert str!=null: "! EiChaptr > normalize: str = null !";
        if (str.isBlank()) return " ";
        if (InGuid.lnChaptr.containsKey(str)) {
            return InGuid.lnChaptr.get(str).getTitle();}
        //элемент не найден. пополняю список
        InGuid.lnChaptr.putIfAbsent(str, new EiChaptr(str,str,17));
        GrRecords.CHPTR.shift();//ставлю флаг наличия модификации списка
        return str;
    }//normalize---------------------------------------------------------------------
    /**
     * Добавление элемента из справочника по умолчанию
     * @param titul - наименование которое будет указываться в ссылках
     */
    public static void addisen( String titul, String descr){
        assert titul!=null: "! EiChaptr > normalize: str = null !";
        assert descr!=null: "! EiChaptr > normalize: str = null !";
        LineGuide.integrate(InGuid.lnChaptr,new EiChaptr( titul,descr,4),1);
    }//addisen-----------------------------------------------------------------------
    /**
     * Добавление элемента из справочника по умолчанию
     * @param titul - наименование которое будет указываться в ссылках
     * @param alias- наименованиекоторое присутствует в не нормированных документах
     */
    public static void addiAli( String titul, String alias){
        assert titul!=null: "! EiChaptr > normalize: str = null !";
        assert alias!=null: "! EiChaptr > normalize: str = null !";
        if (InGuid.lnChaptr.size()<1){
            InGuid.lnChaptr.putIfAbsent(titul, new EiChaptr( titul,titul,17));
            InGuid.lnChaptr.putIfAbsent(alias, new AltTitle( titul,alias));
            return;}
        for (InfcElm vl:InGuid.lnChaptr.values()){
            if (vl.getAlias().equals(alias))   return;
            if (vl.getTitle().equals(titul)) {
                InGuid.lnChaptr.putIfAbsent(alias, new AltTitle( titul,alias));
                return;
            }//if
        }  //for  lnOrganz
        InGuid.lnChaptr.putIfAbsent(titul, new EiChaptr( titul,titul,17));
        InGuid.lnChaptr.putIfAbsent(alias, new AltTitle( titul,alias));
    }//addisen-----------------------------------------------------------------------
    @Override
    public void overlay(InfcElm obj) { super.overlay(obj); }

}//class EiChapter===================================================================
