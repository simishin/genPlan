/**
 * Описание рабочей документации
 */
package qwr.model.Base;

public class EiRDoc extends BaseElement {
    public static final int sizeAr=17;//количество полей в текстовом файле данных
    //              idnt;   //1) приведенный номер изменения (* int)
    //              titul;  //2) номер рабочей документации(* String)
    //              descr;  //3) Наименование проекта  (* String)
    //              sourc;  //4) способ и тип появления элемента (* short)
    //              isusr;  //5) используется в текущем проекте (*)
    //              solv;   //6) разрешено использование (*)
    //              usrcr;  //7) пользователь создавший (* int)
    //              datcr;  //8) время регистрации в базе(* int)(ключ)
 //   private  int     fstr;    //порядковый номер строки по исходному документу
 //   private  int     nstr;    //порядковый номер строки по исходному документу
    private  String  lnkb;   //9) ссылка на базовый документ
    private  String  idz;    //10) номер здания приведенный
//>    private  String  ird;    //приведенный номер РД
//>    private int     izm;    //приведенный номер изменения
//>    private  String  name;   //Наименование проекта (B)
    private String  razd;   //11) Раздел (E)
    private String  vidr;   //12) Вид (F)
    private char    stat;   //13) Статус последней версии документа
    private int     dtv;    //14) дата официального получения
    private int     dpv;    //15) дата получения предыдущей версии
    private int     izp;    //16) номер предыдущей версии
    private String  rep;    //17) примечание к РД (Z)
    public EiRDoc(){this("","", (short) -3,false,false,0);}

    public EiRDoc(String titul, String descr, short sourc, boolean isusr,
                  boolean solv, int idnt) { super(titul, descr, sourc, isusr, solv, idnt); }

    public EiRDoc(EiRDoc obj) {//копирование элемента
        super(obj.titul, obj.descr, obj.typ, obj.isusr, obj.solvd, obj.chang);
        this.owner = obj.owner;//подменяю значения пользователь создавший
        this.key = obj.key;//подменяю значения время создания
    }//EiPath
    @Override
    public String writ() { return super.writ(); }//write


    public boolean read(String[] words) {
        if (super.read(words,sizeAr)) return true;
        lnkb= words[9];//9) дата последней регистрации
        return false;
    }//read

    public boolean equals(EiRDoc obj) { return super.equals(obj); }//equals

//    @Override
    public Object getTitle() { return titul; }

//    @Override
    public int getHasp() {
        return 0;
    }
}//EiRDoc
