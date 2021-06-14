package qwr.footing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static qwr.util.BgFile.prnq;
import static qwr.util.BgFile.prnt;

public class ArrItm implements  ListElm{
    public ArrayList<ListElm> ml;//
    public Map<String, ListElm> ms;//to lst
    public Map<Integer,ListElm> mi;//to lst
    public Map<Integer,ListElm> md;//to lst
    public  int     sizeAr;//количество полей в текстовом файле данных
    private boolean qchange;//флан изменения/замещения элемента
    public ArrItm(int j) {
        ml =new ArrayList<>() {    };//
        ms =new HashMap<>();//to lst
        mi =new HashMap<>();//to lst
        md =new HashMap<>();//to lst
        sizeAr=j;//количество полей в текстовом файле данных
    }
    @Override
    public String print() {
        return null;
    }
    @Override
    public String getTitle() {
        return null;
    }
    @Override
    public long getWay() {
        return 0;
    }
    @Override
    public boolean isAppld() {
        return false;
    }
    @Override
    public boolean isLocal() {
        return false;
    }
    @Override
    public boolean isSynxr() {
        return false;
    }
    @Override
    public boolean isActul() {
        return false;
    }
    @Override
    public String getKey() {
        return null;
    }
    @Override
    public boolean addElm() {
        return false;
    }
    @Override
    public String writ() {
        return null;
    }
    @Override
    public void overlay(InfcElm obj) { }
    @Override
    public boolean compare(InfcElm obj) {
        return false;
    }
    @Override
    public int getWax() {
        return 0;
    }
    @Override
    public void overlap(ListElm obj) { }
    @Override
    public void replace(ListElm obj, int src) {
        prnq(this.getClass()+"ArrItm > NO DEFINE SOURCE "+src);
    }

    public void setSizAr(int sizeAr) {   ml.ensureCapacity(sizeAr); }

    public void printList(String s) { //печать линейной коллекции
        prnq("--"+s+"  "+ml.size());
        prnq("i=or: levL\t(anc)\thsp\t[way]\town\tchg\tif\tis\tit\ttit\t(lnk)\tnot\tdscr");
        for (int i=0; i<ml.size(); i++)
            if (ml.get(i)!=null) prnq(i+"= "+ml.get(i).print());
            else prnq(i+"* ");
        prnq("-------------end---");
    } //printList печать линейной коллекции
    public void printMaps(String s){
        prnq(s+" \tl:"+ml.size()+" \ti:"+mi.size()+" \td:"+md.size()+" \ts:"+ms.size());
        prnq("\tord\tway\thsp\t\ttitl");
        for (int i=0; i<ml.size(); i++)
            if (ml.get(i)!=null) prnq(i+":\t"+ml.get(i).getOrder() +"\t"
                    +ml.get(i).getWax() +"\t"+ml.get(i).getHasp()+"\t"
                    +ml.get(i).getTitle()+"\t"+ml.get(i).getDescr());
            else prnq(i+"* ");
        prnq("---");
        for (Map.Entry<Integer,ListElm> o: mi.entrySet()){
            prnq( (o.getKey()==o.getValue().getHasp() ? "i" : "I#")+
                    "\t"+o.getValue().getOrder()+"\t"+o.getValue().getWax()+
            "\t"+o.getKey()+"\t" +o.getValue().getHasp()+"\t"+o.getValue().getTitle()+
                    "\t"+o.getValue().getDescr());  } prnq("---");
        for (Map.Entry<Integer,ListElm> o: md.entrySet()){
            prnq(   (o.getKey()==o.getValue().getHasp() ? "d" : "D#")+
                    "\t"+o.getValue().getOrder()+"\t"+o.getValue().getWax()+
                    "\t"+o.getKey()+"\t" +o.getValue().getHasp()+"\t"+o.getValue().getTitle()+
                    "\t"+o.getValue().getDescr());  } prnq("---");
        for (ListElm o: ms.values()) prnq("  s\t"+o.getOrder()+"\t"+o.getWax()+"\t"
                +o.getHasp()+"\t"+o.getTitle()+"\t"+o.getDescr()); prnq("---");
    } //printMaps

    public boolean isEmpty() { return ml.isEmpty(); } //isEmpty проверка на пустоту

    public void clear() {
        ml.clear();
        if (!mi.isEmpty())
            assert prnq("@ ArrItm>addElm FIRST lstI is not clear");
        mi.clear();
        if (!ms.isEmpty())
            assert prnq("@ ArrItm>addElm FIRST lstS is not clear");
        ms.clear();
        if (!md.isEmpty())
            assert prnq("@ ArrItm>addElm FIRST lstD is not clear");
        md.clear();
    }//clear

    public int findHasp(ListElm o) { //поиск свободного ключа
        int j=o.getHasp();
        boolean qk;
        do { qk=false; if (mi.containsKey(j)) { j--; qk=true;} } while (qk);
        return j;
    } //findHasp

    public void addElm(ListElm o) { //добавление элемента
        ml.add(o);
        this.setOrder(ml.indexOf(o));
        mi.putIfAbsent(o.getHasp(),o); //==null if add normal
        ms.putIfAbsent(o.getTitle(),o);
    }//addElm

    /**
     * Добавление элемента в объект системы коллекций this известного происхождения
     * @param obj добавляемый объект
     * @param src происхождение: 0 - косвенное создание (04) * 1 - по умолчанию (08)
     * 2 - из документов (03),
     * 3 - из локального источника, 4 - из внешних не согласующихся источников
     * 5, 6, 7, 8, 9, 10, 11, .... из внешних синхронизуемых источников
     * @return код результата операции: 0 без изменений, 1 коррекция WAY
     * 2 заменяем (новый ключ), 3 добавлен, 4 первый, -1 пропускаю элемент,
     * -2 игнорирован по несответствию, -3 запрещенное состояние, -4 не проработано
     */
    public int addElm(ListElm obj, int src) { //добавление элемента
        assert prnt("$ ArrItm > addElm \tsrc:"+src+"\t"+obj.getTitle()+"\t"+obj.getDescr()+"\t");
        assert obj !=null:"ERROR ArrItm>addElm ListElm is null";
        if (src>5) return -4; //не проработано

        //исключаю из анализа элементы, которые не интересны
        if (src>2) switch (obj.getWax()) {
            case 0: //внешние данные еще не прочитаны
                return -3; //запрещенное состояние
            case  9://синхр  удален
            case 12://внешний элемент не обрабатывать
                return -1; //пропускаю элемент
        }//исключаю из анализа элементы, которые не интересны
        // во всех остальных случаях дописываю новый элемент

        //сравнение элементов и установка ключа
        boolean qt,qh=false; //совпадений не найдено
        int j=obj.getHasp();
        boolean qk;
        do { qk=false; if (mi.containsKey(j)) { j--; qk=true; qh=true;} } while (qk);
        obj.setHasp(j); //меняю ключ
        qt=ms.containsKey(obj.getTitle());//совпадений найдено
        assert prnt(" t~"+qt+"  \th~"+qh+"  \t"+j+"\t");
        if (qt && src==0) { //дублирование косвенно созданного
            assert prnq("DUBL indirectly");
            return -1; //пропускаю элемент
        } //дублирование косвенно созданного

        //корректирую Way для созданных косвенно, по умолчанию, из документов
        if (src<3){ //по умолчанию, из документов, косвенное создание
            int u= src==0 ? 4 : (src==1 ? 8 : 3);
            obj.setWay(u);
        } //корректирую Way для созданных косвенно, по умолчанию, из документов

        ListElm oex = null;  //ссылка на найденный элемент при совпадении
        if (qt) oex= ms.get(obj.getTitle()); //при совпадении title
        //------------------------------------------------------------------------------

        if (qt && src==1 && oex.getWax()!=4){ //дублирование по умолчанию
            assert prnq("DUBL DEFAULT");
            return -1; //пропускаю элемент
        } //дублирование по умолчанию

        if (qt && qh && src==3) {//из локального источника
            assert prnq("DUBL LOCAL");
            return -1; //пропускаю элемент
        } //из локального источника
        if (qt && qh && src==4) {//из внешних не согласующихся источников
            assert prnq("DUBL NO coordinate");
            return -1; //пропускаю элемент
        } //из внешних не согласующихся источников
        //замещение косвенно созданного элемента из любых источников кроме косвенного
        if (qt && oex.getWax()==4 && obj.getWax()!=4){
            assert prnq("  OVERLAP indirectly   "+oex.getHasp()+" / "+obj.getHasp()
                    +"\t"+ oex.getWax()+"/"+obj.getWax());
            int k= oex.getOrder();
            md.putIfAbsent(oex.getHasp(),oex);//сохраняю ссылку на старый ключ
            mi.remove(oex.getHasp());
            oex.overlap(obj);//перекрываю значение
            mi.putIfAbsent(oex.getHasp(),oex);
//           oex.setOrder(k); //на всякий случай
            return 2; //заменяем (новый ключ)
        }//замещение косвенно созданного элемента из любых источников кроме косвенного

        if (qh && qt && oex.compare(obj)) {//полное совпадение. кооректирую way
            if (src<4) oex.setWay(obj.getWax());
            else {
                /*long ki=oex.getWay();
                long kj=15<<(src-3)*4;
                ki &= kj;
                kj=obj.getWax()<<(src-3)*4;
                ki |=kj;
                oex.setWay(ki);*/
                oex.setWay((oex.getWay()&(15<<(src-3)*4))|(obj.getWax()<<(src-3)*4));
            }
            assert prnq("\t DUBLE ");
            return 1;
        } //полное совпадение. кооректирую way

        //формирую объем коллекции --------------------------------------------------
        if (obj.getOrder()>=ml.size()){//формирую объем коллекции
            ml.ensureCapacity(obj.getOrder()+3);
            for (int i=ml.size(); i<=obj.getOrder(); i++){ ml.add(null); }
        } else if (obj.getOrder()<0 || ml.get(obj.getOrder())!=null){
            obj.setOrder(ml.size());
            ml.add(null);
        }//формирую объем коллекции
        if (src>3 ) { //дополняю для внешних  way
            long z =  obj.getWax() |(obj.getWax()<<(src-3)*4);
            obj.setWay(z);
        }  //дополняю для внешних  way
        ml.set(obj.getOrder(),obj); //дописываю новый элемент
        //--------------------------------------------------------------------------
        if ((src<3 && !qt)||(!qh && !qt ) ){ //просто добавляем элемент
            mi.putIfAbsent(obj.getHasp(),obj); //==null if add normal
            ms.putIfAbsent(obj.getTitle(),obj);
            assert prnq("\t ADD (HH) ");
            return 3;// добавлен
        } //просто добавляем элемент


        if (qt && src==2) {//дублирование из документов(в любом случае создаю элемент)
            assert prnq("  REPLAZE document   "+oex.getHasp()+" / "+obj.getHasp()
                    +"\t"+ oex.getWax()+"/"+obj.getWax());
            md.putIfAbsent(oex.getHasp(),oex);
            mi.remove(oex.getHasp());
            mi.putIfAbsent(obj.getHasp(),obj);
            ms.replace(oex.getTitle(),obj);
            return 2; //заменяем (новый ключ)
        } //дублирование из документов
        //работа с локальным и внешними источниками. игнорирование описано выше
        //делаем одно из двух либо основным становится новый либо сохраняется старый
        boolean qx=true;

/*
        if (qh && qt){ //совпадение  при различных полях
            assert prnq("\t OVERLAP (DD) ");
            return -4; //2 заменяем
        }
        if (!qh && qt){ //один элемент имеет несколько ключей
            assert prnq("\t OVERLAP (HD) ");
            return -4; //2 заменяем
        }//один элемент имеет несколько ключей
        
        if (qh && !qt){ //наложение ключей
            assert prnq("\t OVERLAP (DH) ");
            return -4; //2 заменяем
        }//наложение ключей
        //анализ
        return 0;

 */


        //---------------------------------------------------------------------------
        if (qx){ //делаем  основным новый
            assert prnq("  REPLAZE NEW");
            md.putIfAbsent(oex.getHasp(),oex);
            mi.remove(oex.getHasp());
            mi.putIfAbsent(obj.getHasp(),obj);
            ms.replace(oex.getTitle(),obj);
            return 2; //заменяем (новый ключ)
        } else {  //основным сохраняется старый
            assert prnq("  REPLAZE OLD");
            md.putIfAbsent(obj.getHasp(),obj);
            return 0; //без изменений
        }
    } //addElm
} //class ArrItm
