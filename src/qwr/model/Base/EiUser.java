/**
 * Класс описывающий пользователей проектов
 * имеет логин, полное имя, дату создания и кто создал,
 * дату последней регистрации, флаг далее не использовать,
 * флаг использования в проектах, уровень пользователя:
 * (3) администратор текущего проекта, (2) администратор из внешних
 * проектов, (1) пользователь внешних проектов, (0) пользователь проекта,
 * (-1) новый пользователь,(-2) новый пользователь из внешних проектов.
 * (-3) инициализация нового пользователя
 * Администратор может создавать пользователей проекта,
 * редактировать внешние пути, запускать процедуру удаления
 * объекта, но не может пополнять списки в отличии от
 * пользователя в данном проекте что бы сократить регистрации
 * под администратором ( в байте уровня может храниться пароль)
 * Флаг не использовать может быть установлен,если логин не встречается
 * в записях проекта(переопределен) после чего его наследуют
 * внешние проекты. Если логин не используется во внешних проектах,
 * то сбрасывается флаг используется в проектах и объект может быть удален.
 * Администратор может на себя забрать объекты созданные
 * пользователем проекта (перопределить) при неизменной дате создания.
 * Пользователь создается администратором или активируется из
 * внешнего пользователя с определением флага "админ" (см. ElGuid)
 */
package qwr.model.Base;

import static qwr.util.BgFile.prnq;

public class EiUser extends BaseElement {
    public static final int sizeAr=8;//количество полей в текстовом файле данных
    //              chang;   //1) время последней регистрации (* int)
    //              titul;  //2) имя пользователя (логин)(* String)
    //              descr;  //3) полное имя (* String)
    //              typ;    //4) способ и тип появления элемента (* short)
    //              isusr;  //5) используется в текущем проекте (*)
    //              solv;   //6) разрешено использование (*)
    //              owner;  //7) пользователь создавший (* int)
    //              key;    //8) время регистрации в базе(* int)(ключ)
    //общий конструктор--------------------------------------------------------------
    public EiUser(String titul){super(titul,2);}//добавление нового
    public EiUser(String titul, String descr) { super(titul, descr); }//ссистемные
    //---копирование-----------------------------------------------------------------
    public EiUser(EiUser obj) {//копирование элемента
        super(obj.titul, obj.descr, obj.typ, obj.isusr, obj.solvd, obj.chang);
        this.owner = obj.owner;//подменяю значения пользователь создавший
        this.key = obj.key;//подменяю значения время создания
    }//EiUser
    @Override//----------------------------------------------------------------------
    public String writ() { return super.writ(); }//write
    @Override//----------------------------------------------------------------------
    public String toString(){ return this.titul; }

    public boolean read(String[] words) {
        return super.read(words, sizeAr);
    }//read

    public boolean equals(EiUser obj) { return
        (this.titul.equals(obj.titul)) && //имя пользователя (логин)(* String)
        (this.descr.equals(obj.descr)) && //3) полное имя (* String)
        (this.key ==obj.key);             //8) дата регистрации в базе(* long)
    }//equals
    //слияние объектов бобавлением к существующему внешнего
    public boolean merger(EiUser obj) {
        if (!this.getTitul().equals(obj.getTitul())) return false;//идем дальше
        //совпал логин - забираю данные из внешнего источника
        /*
        Проработать ситуацию, если данные во внешних файлах разнятся
         */
//        assert prnq("@@@ "+this.getTyp());
        switch (this.typ & 15){
            case 1: //создан системой
                //проверяю на совпадение
                    this.descr=obj.descr;   //3) полное имя (* String)
                    this.typ=obj.typ;       //4) способ и тип появления
                    this.isusr= obj.isusr;  //5) используется в текущем проекте (*)
                    this.solvd=obj.solvd;   //6) разрешено использование (*)
                    this.owner=obj.owner;   //7) пользователь создавший (* int)
                    this.key=obj.key;       //8) время регистрации в базе(* int)(ключ)
                break;
            case 2://создан пользователем
            case 10://получен из внешнего источника
                break;
            default: prnq("*** Metod merger EiUser is not specify ***");return true;
        }//switch
        return true;
    }//merger
}//EiUser