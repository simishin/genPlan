package qwr.model.Base;

public class TrGuid extends BaseElement {
    public static final int sizeAr=9;//количество полей в текстовом файле данных
    //идентификатор idnt;   //1) время последнего изменения внешнего справочника (* int)
    // наименование titul;  //2) путь к внешнему справочнику(* String)
    // описание     descr;  //3) наименование часового пояса внешнего справочника (* String)
    // способ       sourc;  //4) источник получения элемента (* short)
    // используется isusr;  //5) текущая синхронизация выполнена (*)-НЕ ИМЕЕТ СМЫСЛА
    //5) достапность внешнего источника на данный момент (*)
    // разрешено    solv;   //6) разрешено использование (*)- возможно отключние
    // создавшего   usrcr;  //7) пользователь создавший (* int)
    // время        datcr;  //8) время регистрации в базе(* int)(ключ)
//    private boolean readi;  //9) достапность внешнего источника на данный момент (*)

    private         String  base;   //ссылка на базовый элемент
    private         String  altn;   //альтернативное имя
    private         long    srtb;   //сортировка от базового элемента
    //-----------------------------------------------
    public String   getBase() { return base; }
    public String   getAltn() { return altn; }
    public long     getSrtb() { return srtb; }
    public void     setSrtb(long srtb) { this.srtb = srtb; }
    public boolean  isSrtb(){ return this.srtb < 0; }
    //конструкторы-----------------------------------------------------------------
    public TrGuid() { super("",0); this.base=""; }

    public TrGuid(String name, String altn, String base, String descr ) {
                super(name, descr,(short)0, false, true,0);
                this.base=base; this.altn=altn; this.srtb =-1;
    }
    public TrGuid(TrGuid obj) {//копирование элемента
        super(obj.titul, obj.descr, obj.typ, obj.isusr, obj.solvd, obj.chang);
        this.owner = obj.owner;//подменяю значения пользователь создавший
        this.key = obj.key;//подменяю значения время создания
    }//EiPath
    @Override
    public String writ() { return super.writ(); }//write

    public boolean read(String[] words) {
        if (super.read(words,sizeAr)) return true;
        base= words[9];//9) дата последней регистрации
        return false;
    }//read

    public boolean equals(TrGuid obj) { return super.equals(obj); }//equals


}// class TrGuid
