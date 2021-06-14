package qwr.footing;

import java.util.Map;

import static qwr.util.BgFile.prnq;
import static qwr.util.BgFile.sepr;

public class AltTitle implements InfcElm {//альтернативное обозначение
    protected String    title;  //наименование элемента
    protected String    alias;  //альтернативное название
    //общий конструктор--------------------------------------------------------------
    public AltTitle(String title, String alias) {
        this.title = title;
        this.alias = alias;
    }//AltTitle----------------------------------------------------------------------
    @Override
    public boolean isAppld() { return false; }
    @Override
    public boolean isLocal() { return false; }
    @Override
    public boolean isSynxr() { return false; }

    @Override
    public boolean isActul() { return false; }

    @Override
    public String getKey() { return alias; }
    @Override
    public void overlay(InfcElm obj) { }
    @Override
    public String getTitle() { return title; }
    @Override
    public String getAlias() { return alias; }
    @Override
    public String writ() { return sepr+title+sepr+alias+sepr;}
    @Override
    public boolean compare(InfcElm obj) { return false; }
    @Override
    public String print() { return "\ttitl: "+ title +"\t alias:"+ alias+"~"; }
    @Override
    public long getWay() { return 9; }

    public static boolean integrate(Map<String, InfcElm> lnOrganz, String[] words){//перекрытие
        for (InfcElm vl:lnOrganz.values()) if (vl.getAlias().equals(words[2])) {
            if (vl.getTitle().equals(words[1])) return false;//есть ткой элемент
            prnq("AltTitle>isOverlap is Overlap");
            return false;
        } //for по всему списку
        //добавляю элемент в список
        lnOrganz.put(words[2],new AltTitle(words[1],words[2]));//добавляю элемент
        return true;
    } //isOverlap--------------------------------------------------------------------
}//abstract class AlternativeDesignation=============================================
