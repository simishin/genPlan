/**
 * элемент для посторения дерева в коллекции типа List
 */
package qwr.footing;

import qwr.util.BgFile;
import qwr.util.DateTim;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static qwr.util.BgFile.*;

public abstract class Ticked extends LineGuide implements ListElm, InfcElm{//общее обозначение
    // int          hasp;   //1 )ключ, время создания записи в секундах с 1 января 1970г.
    // String       title;  //2B)номер письма регистрационный
    // String       descr;  //3I)тема письма
    // long         way;    //4 ) состояние элемента  1-по умолчанию 3-из локальных
    // int          order;  //5 )порядок следования=индекс массива List
    //----------------------
    protected   int     ancei;  //6 )КЛЮЧ предок
    protected   int     level;  //7 )уровень вложенности
    protected   int     owner;  //8 )идентификатор создателя записи
    protected   int     chang;  //9 )время редактирования записи в секундах с 1 января 1970г.
    protected   int     itypf;  //10)
    protected   int     ityps;  //11)
    protected   int     itypt;  //12)
    protected   String  link;   //13)ссылка на содержание
    protected   String  notes;  //14)примечание
    //общий конструктор--------------------------------------------------------------
    public Ticked(String title, String descr, long way, int order) {
        super( title, descr,way,order);
        this.ancei = 0;     this.level=-1;  this.chang=-1;
        this.owner  = BgFile.getUserIdPrj();//идентификатор пользователя
        this.hasp   = DateTim.newSeconds();//cек1.01.1970
        this.link=""; this.notes = "";
    }//TreeGuide---------------------------------------------------------------------
    //специальный конструктор--------------------------------------------------------
    public Ticked(String[] words) { super(words);
        this.ancei =Integer.parseInt (words[6]);//6 )КЛЮЧ предок
        this.level =Integer.parseInt (words[7]);//7 )уровень вложенности
        this.owner =Integer.parseInt (words[8]);//8 )идентификатор создателя записи
        this.chang =Integer.parseInt (words[9]);//9 )время редактирования записи в секундах
        this.itypf =Integer.parseInt (words[10]);
        this.ityps =Integer.parseInt (words[11]);
        this.itypt =Integer.parseInt (words[12]);
        this.link  =(words[13]);                 //13)ссылка на содержание
        this.notes =(words[14]);                 //14)примечание
    }//TreeGuide---------------------------------------------------------------------
    @Override//----------------------------------------------------------------------
    public String   writ() {
        return super.writ()+ ancei +sepr+ level +sepr+ owner +sepr+ chang +sepr+
                itypf +sepr+ ityps +sepr+ itypt +sepr+
                (link.isEmpty()?" ": link) +sepr+ (notes.isEmpty()?" ": notes) +sepr;}
    @Override//----------------------------------------------------------------------
    public String   print(){ return order+":\t"+level+"L\t("+ancei+")\t"+hasp+"\t[ "+
            (way & 31)+" ]\t"+ owner+"\tc"+chang+"\tf"+itypf+"\ts"+ityps+"\tt"+itypt+
            "\t"+ title +"\t("+ link +")\t"+notes+"\t<"+ descr+">";}
    /**
     * полное копирование принимаемого элемента в элемент колекции кроме наименования
     * @param obj   принимаемый элемент
     * @return
     */
    public void     overlay(InfcElm obj) { super.overlay(obj);
        ancei =obj.getAncei();   level=obj.getLevel();   owner=obj.getOwner();
        chang=obj.getChang();    itypf=obj.getItypf();   ityps=obj.getItyps();
        itypt=obj.getItypt();    link =obj.getLink();    notes =obj.getNotes();
    }//copy---------------------------------------------------------------------
    public void     overlap(ListElm obj) { super.overlap(obj);
        ancei =obj.getAncei();   level=obj.getLevel();   owner=obj.getOwner();
        chang=obj.getChang();    itypf=obj.getItypf();   ityps=obj.getItyps();
        itypt=obj.getItypt();    link =obj.getLink();    notes =obj.getNotes();
    }//copy---------------------------------------------------------------------



    public boolean  compare(InfcElm obj) { //если равны, то истина (кроме статуса )
        return super.compare(obj)
                && ancei==obj.getAncei() //6 )КЛЮЧ предок
                && level==obj.getLevel() //7 )уровень вложенности
                && owner==obj.getOwner() //8 )идентификатор создателя записи
                && (chang==obj.getChang() || obj.getChang()==0 ) //9 )время редактирования записи в секундах с 1 января 1970г.
                && itypf==obj.getItypf()
                && ityps==obj.getItyps()
                && itypt==obj.getItypt()
                && link.equals(obj.getLink())   //13)ссылка на содержание
                && notes.equals(obj.getNotes());//14)примечание
    }//compare-----------------------------------------------------------------------
    public int      getAncei() { return ancei; }
    public void     setAncei(int ancei) { this.ancei = ancei; }
    public int      getLevel() { return level; }
    public void     setLevel(int level) { this.level = level; }
    public int      getOwner() { return owner; }
    public void     setOwner(int owner) { this.owner = owner; }
    public int      getChang() { return chang; }
    public void     setChang(int chang) { this.chang = chang; }
    public int      getItypf() { return itypf; }
    public void     setItypf(int itypf) { this.itypf = itypf; }
    public int      getItyps() { return ityps; }
    public void     setItyps(int ityps) { this.ityps = ityps; }
    public int      getItypt() { return itypt; }
    public void     setItypt(int itypt) { this.itypt = itypt; }
    public String   getLink() { return link; }
    public void     setLink(String link) { this.link = link; }
    public String   getNotes() { return notes; }
    public void     setNotes(String notes) { this.notes = notes; }

    /**
     * Добавление элемента в коллекцию типа List
     * @param lst  коллекция
     * @param lsti
     * @param lsts
     * @return  истина если элемент добавлен
     */
    public boolean  addElm(ArrayList<ListElm> lst, Map<Integer, ListElm> lsti,
                           Map<String, ListElm> lsts){
        assert prnq("@ Ticked > addElm\t"+lst.size()+"/"+order+"\t"+lsti.size()+"\t"
                +lsts.size());
        int i;
        if (this.order<0){ //ищу свободное место для элемента с неопределенным местом
            if (!lst.isEmpty()) for(i=0; i<lst.size(); i++) if(lst.get(i)==null){
                this.order = i;
                lst.set(this.order,this);
                return true;
            }
            this.order = lst.size();
            lst.add(this);
            return true;
        }
        if (this.order>=lst.size()) {
            lst.ensureCapacity(this.order);
            for (i=lst.size(); i<this.order; i++){ lst.add(null); }
            lst.add(this);
            return true;
        }
        if (lst.get(this.order)==null){ lst.set(this.order,this); return true; }
        assert prnt(" $ ");
        return false;
    }//addElm -----------------------------------------------------------------------

    /**
     * Установка размера коллекции
     * @param lst имя коллекции
     * @param size размер коллекции
     */
    public static void setSize(ArrayList<ListElm> lst, int size){
        lst.ensureCapacity(size);
//        assert prnq("@ Ticked > setSize "+lst.size()+"/"+size);
    }//setSize-----------------------------

    public static void printList(String s,List<ListElm> array){
        prnq("--"+s+"  "+array.size());
//        for(ListElm itm : array) prnq(itm.print());
        for (int i=0; i<array.size(); i++)
            if (array.get(i)!=null) prnq(i+"= "+array.get(i).print());
            else prnq(i+"* ");
        prnq("-------------end---");
    }//printList

//    public String getKey() { return super.getKey(); }
    /**
     * соединение, корректировка связей группировки со списком
     * @param unifying группировка типа LineGuide  -объединяющий
     * @param subject список типа  TreeGuide   -подданный,предмет
     * @param dearth список недостающих элементов объединения
     * @return   истина, если нужно пополнить список объединений
     */
    public static boolean bondingTree(Map<String, InfcElm> unifying,
                                      Map<String, InfcElm> subject, HashSet<String> dearth) {
        labl:
        for (InfcElm vs: subject.values()){
//            String s=vs.getAnces(); prnq(">>>>"+s+"<");
            if (vs.getNotes()==null) continue;
            if (vs.getNotes().isBlank()) continue;
            for(InfcElm vu:unifying.values())
                if (vu.getTitle().equals(vs.getNotes())) {//элемент найден
                    vs.setAncei(vu.getHasp()); //записываю ключ объединения
                    continue labl;
                } //for if unifying
            dearth.add(vs.getNotes());//не найден создаю список недостающих элементов
        } //for subject
        return dearth.size()!=0; //истина, если нужно пополнить список объединений
    }//bondingTree-------------------------------------------------------------------

    protected int addElm(ArrItm m, int src) { return super.addElm(m,src); }
}//abstract class Ticked==========================================================
