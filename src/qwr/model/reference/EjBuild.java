/**
 * Перечень зданий с привязкой к главам
 */
package qwr.model.reference;

import qwr.Inizial.InGuid;
import qwr.footing.*;
import qwr.model.SharSystem.GrRecords;

public class EjBuild extends TreeGuide implements InfcElm{
    public    static final int sizeAr=8;//количество полей в текстовом файле данных
    protected static       int count=1;//cчетчик для элементов по умолчанию
    // int       hasp;  //1)ключ, идентификатор элемента
    // String    title; //2)наименование элемента
    // String    descr; //3)описание элемента
    // long      stat;  //4) состояние элемента
    // int       order; //5)порядок следования
    // String    ances; //7)предок
    // int       ancei; //6)предок
    //конструкторы-----------------------------------------------------------------
    public EjBuild(String ances, String title, String descr, int stat) {
        super( ances, title, descr,stat,count++);
        hasp=LineGuide.questHasp(InGuid.lnBuild,hasp);}//EiBuild
    //специальный конструктор--------------------------------------------------------
    public EjBuild(String[] words) { super(words); }  //-----------------------------

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
     * Приведение к стандарту встречающихся здани в исходных документах
     *     Проверяю на наличие в базе.если элемент найден, то делаю подстановку
//     * @param str строка исходного не нормализованного документа
     * @return нормализованное значение
     */
    public static String normalize(String str){
        assert str!=null: "! EiBuild > normalize: str = null !";
        if (str.isBlank()) return " ";
        if (InGuid.lnBuild.containsKey(str)) return InGuid.lnBuild.get(str).getTitle();
        //элемент не найден. пополняю список
        InGuid.lnBuild.putIfAbsent(str, new EjBuild("",str,str,17));
        GrRecords.BUILD.shift();//ставлю флаг наличия модификации списка
        return str;
    }//normalize---------------------------------------------------------------------

    public static void addisen( String titul, String ances, String descr){
        assert titul!=null: "! EiBuild > addisen: titul = null !";
        assert descr!=null: "! EiBuild > addisen: descr = null !";
        assert ances!=null: "! EiBuild > addisen: ances = null !";
        LineGuide.integrate(InGuid.lnBuild,new EjBuild(ances,titul,descr,4),1);
    }//addisen-----------------------------------------------------------------------


    public static void addisen( String titul, String alias){
        assert titul!=null: "! EiBuild > addisen: titul = null !";
        assert alias!=null: "! EiBuild > addisen: alias = null !";
        if (InGuid.lnBuild.size()<1){
            InGuid.lnBuild.putIfAbsent(titul, new EjBuild( "",titul,"",17));
            InGuid.lnBuild.putIfAbsent(alias, new AltTitle( titul,alias));
            return;}
//        assert prnq("@ "+titul+" "+alias);
        for (InfcElm vl:InGuid.lnBuild.values()){
//            prnq(vl.print());
            if (vl.getAlias().equals(alias))   return;
            if (vl.getTitle().equals(titul)) {
                InGuid.lnBuild.putIfAbsent(alias, new AltTitle( titul,alias));
//                prnq("EiBuild---");
                return;
            }//if
        }  //for  lnOrganz
        InGuid.lnBuild.putIfAbsent(titul, new EjBuild( "",titul,"",17));
        InGuid.lnBuild.putIfAbsent(alias, new AltTitle( titul,alias));
        GrRecords.BUILD.shift();//факт изменения
    }//addisen-----------------------------------------------------------------------
    @Override
    public void overlay(InfcElm obj) { super.overlay(obj); }



}//class EiBuild=====================================================================
