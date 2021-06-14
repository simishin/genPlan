/**
 *
 */
package qwr.model.Base;

//карточка Заказчику смотри также class Ecleam
public class EiCart extends BaseElement {
    public static final int sizeAr=22;//количество полей в текстовом файле данных
    //              idnt;   //1) идентификатор письма (* int)
    //              titul;  //2) номер рабочей документации(* String)
    //              descr;  //3) описание (* String)
    //              sourc;  //4) способ и тип появления элемента (* short)
    //              isusr;  //5) используется в текущем проекте (*)
    //              solv;   //6) разрешено использование (*)
    //              usrcr;  //7) пользователь создавший (* int)
    //              datcr;  //8) время регистрации в базе(* int)(ключ)
    private int     numb;   //9) порядковый номер регистрации(*)
    private String  idrd;   //10)идентификатор рабочей документации(*)
    private int     izm;    //11)номер изменения рабочей документации
    private int     date;   //12)дата изменения рабочей документации
    private String  iniz;   //13)  Инициатор КЗ
    private String  depart; //14)"Организация \n(инициатор обращения)"
    private String  author; //15)"ФИО инициатора обращения"
    private int     bild;   //16)идентификатор "Здание/\nСооружение"-
    private int     razd;   //17)идентификатор раздел  "Вид СМР"
    private String  text;   //18) "Текст обращения"
    private String  neoc;   //19) "Номер записи в ЕОС"
    private String  nrpi;   //20) "Номер РПИ"
    private String  comnt;  //21) "Примечание УС БАЭС"
    private int     faza;   //22) идентификатор
    public EiCart(){this("","", (short) -3,false,false,0);}

    public EiCart(String titul, String descr, short sourc, boolean isusr,
                  boolean solv, int idnt) { super(titul, descr, sourc, isusr, solv, idnt); }

    public EiCart(EiCart obj) {//копирование элемента
        super(obj.titul, obj.descr, obj.typ, obj.isusr, obj.solvd, obj.chang);
        this.owner = obj.owner;//подменяю значения пользователь создавший
        this.key = obj.key;//подменяю значения время создания
    }//EiPath
    @Override
    public String writ() { return super.writ(); }//write


    public boolean read(String[] words) {
        if (super.read(words,sizeAr)) return true;
        numb= Integer.parseInt(words[9]);//9) дата последней регистрации
        return false;
    }//read


    public boolean equals(EiCart obj) { return super.equals(obj); }//equals
}//class EiCart
