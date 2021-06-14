//Примитив для построения справочников общего назначения
package qwr.footing;

import qwr.util.DateTim;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import static qwr.util.BgFile.*;

public abstract class LineGuide implements InfcElm, ListElm {
    protected int       hasp;  //1)ключ, идентификатор элемента
    protected String    title; //2)наименование элемента
    protected String    descr; //3)описание элемента
    protected long      way;  //4) состояние элемента 1-по умолчанию 3-из локальных
    protected int       order; //5)порядок следования
    //общий конструктор--------------------------------------------------------------
    public LineGuide(int hasp, String title, String descr, long way, int order) {
        this.title = title;
        this.descr = descr;
        this.way   = way;
        this.hasp  = hasp;
        this.order = order;
    }//LineGuide------------------------------------------------------------

    public LineGuide(String title, String descr, long way, int order) {
        assert title!=null: "LineGuide ERROR title=null";
        this.hasp  = Math.abs(Objects.hash(title, descr));
        this.title = title;
        this.descr = descr;
        this.way   = way;
        this.order = order;
    }//LineGuide------------------------------------------------------------
    //специальный конструктор--------------------------------------------------------
    public LineGuide(String[] words) {
        assert words.length>4:"--LineGuide size arrey "+words.length+" < 4";
        hasp =Integer.parseInt (words[1]);
        order =Integer.parseInt (words[2]);
        way =Short.parseShort (words[3]);//4) присутствует во внешних справочниках
        title= words[4];
        descr = words[5];
    } //LineGuide--------------------------------------------------------------------
    /**
     * поиск совободного ключа в коллекции типа МАР
     * @param arrayMap имя колекции для поиска
     * @param hasp
     * @return
     */
    protected static int questHasp(Map<String, InfcElm> arrayMap, int hasp) {
        if (arrayMap.size()<1) return hasp;
        int j=hasp;
        boolean b;
        do { b=false;
            for (InfcElm value : arrayMap.values())
                if (value.getHasp()==j) { b=true; break; }
            if (b) {j--;}
        } while (b);
        return j;
    }   //questHasp------------------------------------------------------------------
    protected static int questHasp(Map<String, InfcElm> arrayMap) {
        return questHasp(arrayMap, DateTim.newSeconds());
    }
    @Override//----------------------------------------------------------------------
    public int      hashCode() { return Math.abs(Objects.hash(title, descr)); }
    //-------------------------------------------------------------------------------
    public int      getWax() { return Math.toIntExact((way & 31)); }
    public long     getWay() { return way; }
    public void     setWay(long way) { this.way = way; }
    public int      getHasp() { return hasp; }
    public void     setHasp(int hasp) { this.hasp = hasp; }
    public int      getOrder() { return order; }
    public void     setOrder(int order) { this.order = order; }
    public String   getTitle() { return title; }
    public void     setTitle(String title) { this.title = title; }
    public String   getDescr() { return descr; }
    public void     setDescr(String descr) { this.descr = descr; }
    
    public boolean isLocal(){ return (way & 8)==0;}     //создан локально
    public boolean isSynxr(){ return (way & 4)!=0;}     //синхронизован
    public boolean isActul(){ return (way & 2)!=0;}     //актуальный
    public boolean isBlock(){ return (way & 1)==0;}     //блокирован
    public boolean isAppld(){ return (way & 16)!=0;}    //используется
    public int     getLocSt(int kit){
        assert kit<7 && kit>=0 : "loadStatExtern ERROR 0<=kit<7";
        return Math.toIntExact((way >>>((kit & 7) *5)) & 31);} //---------------------

    @Override//----------------------------------------------------------------------
    public boolean  equals(Object o) {
        if (o instanceof AltTitle) return false;
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LineGuide that = (LineGuide) o;
        return Objects.equals(descr, that.descr) && Objects.equals(title, that.title);
    }
    @Override
    public boolean compare(InfcElm obj) { //если равны, то истина (кроме статуса )
        if (obj instanceof AltTitle) return false;
        return  //this.getHasp()==(obj.getHasp()) &&
                this.title.equals(obj.getTitle()) && this.order==obj.getOrder() &&
                (this.descr.isBlank()&&obj.getDescr().isBlank()||
                this.descr.equals(obj.getDescr()));
    }//compare-----------------------------------------------------------------------
    public String   writ() { return sepr+hasp+sepr+order+sepr+(way & 31)+sepr+
            title+sepr+(descr.isEmpty()?" ":descr )+sepr;}
    public String   print(){
        return hasp+"\t"+order+"\t[ "+(way & 31)+" ]\t"+ title +"\t"+ descr;}

     /**
     * полное копирование принимаемого элемента в элемент колекции кроме наименования
     * @param obj   принимаемый элемент
     * @return
     */
    public void overlay(InfcElm obj) {
        hasp =obj.getHasp();    //1)ключ, идентификатор элемента
        descr=obj.getDescr();   //3)описание элемента
        order=obj.getOrder();   //5)порядок следования
        way  =obj.getWax();     //4) состояние элемента 1-по умолчанию 3-из локальных
    }  //copy------------------------------------------------------------------------
    public void overlap(ListElm obj) {
        hasp =obj.getHasp();    //1)ключ, идентификатор элемента
        descr=obj.getDescr();   //3)описание элемента
//        order=obj.getOrder();   //5)порядок следования
        way  =obj.getWax();     //4) состояние элемента 1-по умолчанию 3-из локальных
    }  //copy------------------------------------------------------------------------

    /**
     * интеграция данных в колекцию, кроме альтернативных (по другой ветке)
     * вызывается из GrRecords.*.readRecord, class.parse, class.addisen
     * @param array  коллекция для включения элемента
     * @param obj  элемент для обработки
     * @param src  тип источника элемента от пути:  (Заначения  typ для EiFile и EiPath)
     *  0 - из документов и по умолчанию
     *  1 - файлы и папки локальной базы
     *  2 - файлы и папки внешних данных не синхронизуемых (данные берутся но не проверяются)
     *  3,4,5,6,7. - файлы и папки внешних данных подлежащих синхронизации изменения данных
     * @return 0 без изменений, 1 переписаны поля, 2 заменяем, 3 добавлен, 4 первый,
     * -1 пропускаю элемент, -2 игнорирован по несответствию, -3 запрещенное состояние
     */
    public static int integrate(Map<String, InfcElm> array, InfcElm obj, int src){
        assert !(obj instanceof AltTitle):"ERROR LineGuide>integrate obj is AltTitle";
        assert array!=null:"ERROR LineGuide>integrate array is null";

//        if ((obj.getWax() & 3) ==0){ assert prnq("@ LineGuide > integrate: " +
 //               "запрещенное состояние !" +obj.getTitle()+" "+obj.getWax()); return -3; }
        if (obj.getWax() ==13){ return -1; }// пропускаю внешний элемент
        if (array.isEmpty()){array.put(obj.getKey(),obj); return 4;}//если колекция пуста

        switch (src){
            case 0://поступление из документов
                if (array.putIfAbsent(obj.getKey(),obj)==null)  return 3;//совпадений не найдено-добавляю
                else {
                    InfcElm vl = array.get(obj.getKey());
                    if (vl.compare(obj)){ return 0; }
                    assert prnq("\nv "+vl.print()+"\no "+obj.print());
                    //prnq("="+);
                    vl.overlay(obj);
                    return 1;
               }  //
            case 1://поступление из локальной базы  и по умолчанию
                if (array.putIfAbsent(obj.getKey(),obj)==null)  return 3;//совпадений не найдено-добавляю
                else {
                    InfcElm vl = array.get(obj.getKey());
                    if (vl instanceof AltTitle){
                        array.put(obj.getKey(),obj);
                        assert prnq("@integrate :"+obj.getKey()+"~ Change AltTitle");
                        return 2;
                    }
                    if (obj.getWax() != 17) {//пропускаю косвенно созданный
                        vl.overlay(obj);//копируем из локальной поверх (уничтожение дублера)
                        return 1;
                    }
                    return -1;
                }
        } //switch
        int t=0; //результат сканирования коллекциии

        //проверяю источник элемента - получен из внешних баз или вновь создан
        //от этого зависит необходимость анализа числокого ключа

        if (obj.getWax()==2||obj.getWax()==4||obj.getWax()==20||obj.getWax()==17){
            t=1;
        //элемент создан ручным вводом(2),по умолчанию(4),из документа(20),косвенно(17)
            for (InfcElm vl:array.values()){
                if (vl instanceof AltTitle) continue; //обход альтернативных ключей
                if (vl.getTitle().equals(obj.getTitle())){
                    //проверяю описание
                    if (vl.getDescr().equals(obj.getDescr())){ return 0; }//полное совпадение
                    if (obj.getWax()==4) { return 0; }//по умолчанию - игнорирую
                    if (obj.getWax()==17) { return 0; }//косвенно - игнорирую
                    //для созданого ручным вводом(2) и из документа(20)
                    if (obj.isAppld() || !obj.isLocal() || obj.isSynxr()){return 0;}//гнорирую
                    //корректирую описание
                    vl.setDescr(obj.getDescr());
                    return 1;
                }//if найден элемент по ключу
            } //for  array
            //создан локально и не найден - создаю
            assert prnq(obj.getClass()+"> isOverlap-3 "+obj.getHasp()+" "+obj.getTitle());
            obj.setHasp(LineGuide.questHasp(array,obj.getHasp())); //нахожу уникальный ключ
            switch (obj.getWax()){
                case 2:  obj.setWay(3);break;  //ручным вводом(2)
                case 4:  obj.setWay(3);break;  //по умолчанию(4)
                case 20: obj.setWay(3);break;  //из документа(20)
                case 17: obj.setWay(19);break; //косвенно(17)
            }
            array.put(obj.getKey(),obj);//добавляю элемент
            return 2;
        } else { //проверяю источник элемента  --------------------------------------
            //элемен получен из даз данных, включая локальную
            t=3;
            for (InfcElm vl:array.values()){
                if (vl instanceof AltTitle) continue; //обход альтернативных ключей
                if (vl.getTitle().equals(obj.getTitle()))
                    {
                        if (vl.getHasp()==(obj.getHasp())) {
                            t=4; break;
                        } else {
                            t=5; break;
                        }
                    }
            } //for  array
        } //проверяю источник элемента
        //разбираю результат сканирования коллекции
        switch (t){
            case 0: return 0;//полное совпадение
            case 2: return 1;//корректирую описание
            case 1://создан локально и не найден - создаю
                assert prnq(obj.getClass()+"> isOverlap-3 "+obj.getHasp()+" "+obj.getTitle());
                obj.setHasp(LineGuide.questHasp(array,obj.getHasp())); //нахожу уникальный ключ
                array.put(obj.getKey(),obj);//добавляю элемент
                return 1;

        }
        return 0;
    } //integrate----------------------------------------------------------------------

    protected int addElm(ArrItm m, int src) { return m.addElm(this,src); }
}//abstract class LineGuide =========================================================