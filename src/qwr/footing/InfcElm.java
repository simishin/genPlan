package qwr.footing;

public interface InfcElm {
            String  writ();              //вывод в локальную базу
            void    overlay(InfcElm obj);   //полное копирование принимаемого элемента
            boolean compare(InfcElm obj);//если равны, то истина (кроме статуса )
            String  print();             //печать содержимого элемента
            String  getTitle();          //2)наименование элемента
            long    getWay();           //4) состояние элемента
            boolean isAppld();           //используется
            boolean isLocal();           //создан локально
            boolean isSynxr();           //синхронизован
            boolean isActul();           //актуальный
            String  getKey();
    default int     getWax(){return Math.toIntExact((getWay() & 31));}
    default void    setWay(long way){} //4) состояние элемента
    default int     getHasp(){return 0;}
    default void    setHasp(int hasp){}
    default int     getOrder(){return -1;}
    default void    setOrder(int order){}
    default void    setTitle(String title) { }
    default String  getDescr(){return null;}
    default void    setDescr(String descr) { }
    default String  printTitle(){return " ";}//печатть заголовка таблицы
    default String  getAlias(){return getTitle();}

    default String getNotes(){return null;}
    default int     getAncei(){return -1;}
    default void    setAncei(int hasp) {}
    default int     getLevel(){return -1;}
    default int     getOwner(){return -1;}
    default int     getChang(){return -1;}
    default int     getItypf(){return -1;}
    default int     getItypt(){return -1;}
    default int     getItyps(){return -1;}
    default String  getLink(){return " ";}
}//interface InterfaceElement========================================================
