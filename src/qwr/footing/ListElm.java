package qwr.footing;

public interface ListElm extends InfcElm {
    String  print();
    boolean addElm();   //Добавление элемента в коллекцию типа List

    String writ();      //запись элемента в файл данных

    int getWax();

    void overlap(ListElm obj);  //перекрытие новым элементом станрого

    void replace(ListElm obj, int src); //замещение этого элемента новым obj,
}//interface ListElm
