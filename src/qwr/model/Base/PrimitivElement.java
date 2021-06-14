package qwr.model.Base;

import java.util.Objects;

import static qwr.util.BgFile.prnq;
import static qwr.util.BgFile.sepr;

public abstract class PrimitivElement<TYP> {
    protected TYP       keyEl;  //8) время создания в секундах с 1 января 1970г.(ключ)
    protected TYP       keyBl;  //ссылка на базовый элемент
    protected String    titul;  //2) наименование (*)
    protected boolean   isusr;  //5) используется в текущем проекте
    protected boolean   solvd;  //6) разрешено использование
    //общий конструктор--------------------------------------------------------------
    public PrimitivElement(TYP keyEl, String titul) {
        this.keyEl = keyEl;
        this.keyBl = null;
        this.titul = titul;
        this.isusr = false;
        this.solvd = true;
    }//PrimitivElement

    public TYP      getKeyEl() { return keyEl; }
    public void     setKeyEl(TYP keyEl) { this.keyEl = keyEl; }
    public TYP      getKeyBl() { return keyBl; }
    public void     setKeyBl(TYP keyBl) { this.keyBl = keyBl; }
    public String   getTitul() { return titul; }
    public void     setTitul(String titul) { this.titul = titul; }
    public boolean  isIsusr() { return isusr; }
    public void     setIsusr(boolean isusr) { this.isusr = isusr; }
    public boolean  isSolvd() { return solvd; }
    public void     setSolvd(boolean solvd) { this.solvd = solvd; }

    @Override//----------------------------------------------------------------------
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrimitivElement<?> that = (PrimitivElement<?>) o;
        return Objects.equals(keyEl, that.keyEl) && Objects.equals(titul, that.titul);
    }
    @Override
    public int hashCode() { return Objects.hash(keyEl, titul); }

    protected boolean read(String[] words, int sizeAr){
        assert sizeAr>5:"--BaseElement size arrey < 8";
        if (words.length<=sizeAr) { //проверяю количество элементов в массиве
            assert prnq("read> Error size array ("+sizeAr+")");return true;}//аварийное завершение
//        keyEl   = Integer.parseInt(words[1]);//8) время регистрации в базе(* int)(ключ)
//        keyBl = Integer.parseInt(words[2]);//7) пользователь создавший
        solvd = words[3].startsWith("Y");//6) доступно чтение(*)
        isusr = words[4].startsWith("Y");//5) синхронизация выполнена (*)
        titul = words[5];//2) путь к внешнему справочнику(* String)
        return false;
    }//read--------------------------------------------------------------------------
    public String   writ() { return sepr+//создание строки для записи в текстовый файл
            keyEl +sepr+ keyBl +sepr+ (solvd ? "Y":"n")+sepr+
            (isusr ? "Y":"n")+sepr+  titul+sepr;}
    public String print(){return keyEl+"\t ["+keyBl+"] \t"+
            (isusr ? "Y":"n")+"  \t"+(solvd ? "Y":"n")+"  \t"+titul;}
}//abstract class PrimitivElement======================================================
