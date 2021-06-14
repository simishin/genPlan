package qwr.model.reference;

import qwr.Inizial.InGuid;
import qwr.footing.AltTitle;
import qwr.footing.InfcElm;
import qwr.footing.LineGuide;
import qwr.footing.ListElm;

import java.util.HashSet;

public class EiHeader  extends LineGuide implements InfcElm {
    public static final int sizeAr=5;//количество полей в текстовом файле данных
    protected static int count=1;//cчетчик для элементов по умолчанию
    // int       hasp;  //1)ключ, идентификатор элемента
    // String    title; //2)наименование элемента
    // String    descr; //3)описание элемента
    // long      stat;  //4) состояние элемента
    // int       order; //5)порядок следования
    //конструкторы-----------------------------------------------------------------
    public EiHeader(String title, String descr, int stat) {
        super( title, descr,stat,count++);
        hasp=LineGuide.questHasp(InGuid.lnHeader,hasp); }
    //специальный конструктор--------------------------------------------------------
    public EiHeader(String[] words) { super(words); }

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
     * Приведение к стандарту встречающихся исполнителей в исходных документах
     * таблица ссответствий находится в списке InGuid.lnAuthor типа Map<String,EiAuthor>
     *     Проверяю на наличие в базе.если элемент найден, то делаю подстановку
     * @param str строка исходного не нормализованного документа
     * @return нормализованное значение
     */
        /*
    public static String normalize(String str){
        assert str!=null: "! EiHeader > normalize: str = null !";
        if (str.length()<3) return "";//строка слишком мала - не проверяю
        assert prnq("normalize> "+str);
        if (InGuid.lnHeader.containsKey(str)) return InGuid.lnHeader.get(str).getKey();
        //элемент не найден. пополняю список
        InGuid.lnHeader.putIfAbsent(str, new EiHeader(str,str,0));
        GrRecords.HEADR.shift();//ставлю флаг наличия модификации списка
        return str;
    }//normalize---------------------------------------------------------------------

         */
    /**
     * добавление в список недостающих значений полыенных из подчиненного списа
     * по окончанию пополнения список очищается
     * @param dearth  список недостающих значений
     */
    public static void addisen(HashSet<String> dearth, int stat) {
        for (String s:dearth){
            InGuid.lnHeader.putIfAbsent(s, new EiGrOrgz( s,"",stat));
        }//for dearth
        dearth.clear();//удаляю все элементы после подгрузки
    } //addisen--------------------------------------------------------------------

    /**
     * Добавление элемента из справочника по умолчанию
     * @param titul - наименование которое будет указываться в ссылках
     */
    public static void addisen( String titul, String descr){
        assert titul!=null: "! EiHeader > normalize: str = null !";
        assert descr!=null: "! EiHeader > normalize: str = null !";
        LineGuide.integrate(InGuid.lnHeader,new EiHeader( titul,descr,4),1);
        /*
        if (InGuid.lnHeader.size()<1){
            InGuid.lnHeader.putIfAbsent(titul, new EiHeader( titul,descr,4));
            return;}
        for (InfcElm vl:InGuid.lnHeader.values()) if (vl.getTitle().equals(titul)) { return; }
        InGuid.lnHeader.putIfAbsent(titul, new EiHeader( titul,descr,4));

         */
    }//addisen-----------------------------------------------------------------------
    /**
     * Добавление элемента из справочника по умолчанию
     * @param titul - наименование которое будет указываться в ссылках
     * @param alias- наименованиекоторое присутствует в не нормированных документах
     */
    public static void addiAli( String titul, String alias){
        assert titul!=null: "! lnHeader > normalize: str = null !";
        assert alias!=null: "! lnHeader > normalize: str = null !";
        if (InGuid.lnHeader.size()<1){
            InGuid.lnHeader.putIfAbsent(titul, new EiGrOrgz( titul,titul,17));
            InGuid.lnHeader.putIfAbsent(alias, new AltTitle( titul,alias));
            return;}
        for (InfcElm vl:InGuid.lnHeader.values()){
            if (vl.getAlias().equals(alias))   return;
            if (vl.getTitle().equals(titul)) {
                InGuid.lnHeader.putIfAbsent(alias, new AltTitle( titul,alias));
                return;
            }//if
        }  //for  lnOrganz
        InGuid.lnHeader.putIfAbsent(titul, new EiGrOrgz( titul,titul,17));
        InGuid.lnHeader.putIfAbsent(alias, new AltTitle( titul,alias));
    }//addisen-----------------------------------------------------------------------
    @Override
    public void overlay(InfcElm obj) { super.overlay(obj); }


}//class EiHeader====================================================================
