package qwr.model.TPlan;
//интерфейс для классов: ElmGr ElmTs Elmnt
public interface ElmPb {
    Object  getVal(int i);  //возврат значения по номеру поля с различными типами
    void    clear();        //очиистка
    void    setNur(int i);  //установка номер записи
    void    setform(StringBuilder sbl, int grp);
    void    setTst(int b);
//    int     getGrp();       //количество суммируемых элементов в данной строке
    int     getNur();       //номер записи
    String  getIdr();       //идентификатор группировки
    String  getName();      //описание группировки из справоочника
    String prn();
    //методы по умолчанию
    default int     getTst(){ return 0;} //величина отступа = относительный уровень группировки
    default String  getForm() { return null; }
    default String  getRd()   { return null; }
    default String  getSmin() { return null; }
    default String  getSmln() { return null; }
    default String  getPipl() { return null; }
    default double  getKsm() { return 0; }
    default double  getCsm() { return 0; }
    default double  getCst() { return 0; }
    default double  getCpl() { return 0; }
    default double  getCpt() { return 0; }
    default void    setKpl(double i){}
    default void    setKost(double i){}
}//ElmPb
/*
 * Возможные уровни группировки
 * 1-по главам (список) > 2, 7
 * 2-по зданиям (список) > 3
 * 3-по разделам РД (список) > 6
 * 4-по системам (дерево) > 6
 * 5-по рабочей документации (список-дерево) > 4, 6
 * 6-по видам, этапам работ (список)
 * 7-по технологическим группам зданий-сооружений (дерево) > 4
 */