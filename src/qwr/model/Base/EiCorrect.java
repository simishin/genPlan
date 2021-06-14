/**
 * Элементы для фиксации прошедших изменений данных во внешних файлах
 */
package qwr.model.Base;

public class EiCorrect {
    private long    key; //ключ записи подлежащей контролю корректировке
    private long    swap;//ключ новой записи для замены предыдущей
    private String  refr;//наименования справочника - поля данных(FileGroupRecords)
    private short   typi;//состояние элемента в локальном справочнике
    private short   type;//сосотояние во внешнем файле
    private String  path;//путь и имя внешнего справочника
    private int     chang;//временя точка начала процесса корруктировки
}//class EiCorrect
