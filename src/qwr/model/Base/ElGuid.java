/**
 * Базовый Элемент всех справочников
 * имеет наименование, описание, дату создания и кто создал,
 * флаг далее не использовать, флаг использования в проектах,
 * способ появления элемента: 1(3)-получен из глобальных справочников,
 * 2(2)-из внешних справочников, 3(1)-создан в локально,
 * 4(0)-получен из справочников по умолчанию.
 * при использовании в проекте ставится флаг.
 * Флаг разрешения использования сбрасывается если флаг используется
 * не установлен или данный флаг сброшен во внешних справочниках
 * Если оба флага сброшены и он не используется во внешних проектах
 * то элемент может быть удален кроме как из справочника по умолчанию.
 * Процедуру удаления может запустить только администратор.
 * Изменение элемента может произвести только администратор,
 * если данный элемент не задействован во внешних проектах.
 * При подключении нового проекта проверяется соответствие элементов,
 * если обнаружено несоответствие проект не синхронизируется (флаг сброшен).
 * В момент создания элемента проверяется существование одноименного
 * элемента во внешних справочниках с которыми установлена синхронизация
 */
package qwr.model.Base;

public class ElGuid extends BaseElement {//Guid=справочник,руководство
    public static final int sizeAr=8;//количество полей в текстовом файле данных

    public ElGuid(String titul, String descr) {
        super(titul, descr, (short)0, false, true,0); }

    public ElGuid(ElGuid obj) {//копирование элемента
        super(obj.titul, obj.descr, obj.typ, obj.isusr, obj.solvd, obj.chang);
        this.owner = obj.owner;//подменяю значения пользователь создавший
        this.key = obj.key;//подменяю значения время создания
    }//EiPath
    @Override
    public String writ() { return super.writ(); }//write


    public boolean read(String[] words) {return super.read(words,sizeAr); }//read


    public boolean equals(ElGuid obj) { return super.equals(obj); }//equals

}//ElGuid