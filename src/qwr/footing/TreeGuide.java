/**
 * древовидный справочние tre: имя верхнего элемента и сортировка
 */
package qwr.footing;

import java.util.HashSet;
import java.util.Map;

import static qwr.util.BgFile.sepr;

public abstract class TreeGuide extends LineGuide implements InfcElm{//общее обозначение
    protected   String  ances;      //7)предок
    protected   int     ancei;      //6)предок
    protected   int     level;      //8)уровень вложенности
    //общий конструктор--------------------------------------------------------------
    public TreeGuide(String ances, String title, String descr, long way, int order) {
        super( title, descr,way,order);
        this.ancei = 0;    this.ances = ances;   this.level=-1;
    }//TreeGuide---------------------------------------------------------------------
    //специальный конструктор--------------------------------------------------------
    public TreeGuide(String[] words) { super(words);
        this.ancei =Integer.parseInt (words[6]);
        this.ances =(words[7]);
        this.level =Integer.parseInt (words[8]);
    }//TreeGuide---------------------------------------------------------------------
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
    /**
     * полное копирование принимаемого элемента в элемент колекции кроме наименования
     * @param obj   принимаемый элемент
     * @return
     */
    public void overlap(ListElm obj) { super.overlap(obj);
       this.ancei =obj.getAncei(); this.ances =obj.getNotes(); level=obj.getLevel(); }
    public String getNotes() { return ances; }
    public void     setAnces(String ances) { this.ances = ances; }
    public int      getAncei() { return ancei; }
    public void     setAncei(int ancei) { this.ancei = ancei; }
    public int      getLevel() { return level; }
    public void     setLevel(int level) { this.level = level; }

    //    @Override//----------------------------------------------------------------------
    public boolean  compare(InfcElm obj) { //если равны, то истина (кроме статуса )
        return super.compare(obj) && ances.equals(obj.getNotes());
    }//compare-----------------------------------------------------------------------
    @Override//----------------------------------------------------------------------
    public String   writ() {
        return super.writ()+ ancei +sepr+ (ances.isEmpty()?" ":ances ) +sepr+ level +sepr;}
    @Override//----------------------------------------------------------------------
    public String   print(){ return hasp+"\t"+level+"L\t("+ancei+")\t"+order+"\t[ "+
                (way & 31)+" ]\t"+ title +"\t("+ances+")\t<"+ descr+">";}
}//abstract class TreeGuide==========================================================
