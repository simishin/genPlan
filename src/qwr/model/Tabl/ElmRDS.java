package qwr.model.Tabl;
//список чертежей
public class ElmRDS {
//    static int counter=0;
    private final int     fstr;    //порядковый номер строки по исходному документу
    private final int     nstr;    //порядковый номер строки по исходному документу
    private final String  lnkb;    //ссылка на базовый документ
    private final String  idz;    //номер здания приведенный
    private final String  ird;    //приведенный номер РД
    private int     izm;    //приведенный номер изменения
    private final String  name;   //Наименование проекта (B)
    private String  razd;   //Раздел (E)
    private String  vidr;   //Вид (F)
    private char    stat;   //Статус последней версии документа
    private int     dtv;    //дата официального получения
    private int     dpv;    //дата получения предыдущей версии
    private int     izp;    //номер предыдущей версии
    private final String  rep;    //примечание к РД (Z)
    //1=работаем 2=новое значение и работаем 3=идет корректировка
    // 4=не официально 5=анулировано, возвращено 6=ссылка 7=без номера
    //-------------------------------------------------
    public Object getVal(int i){
        switch (i){
            case 0 :return " ";
            case 1 :return " ";
            case 2 :return 0;
            case 3 :return 0;
            case 4 :return this.fstr;     //порядковый номер строки по исходному документу
            case 5 :return this.nstr;     //порядковый номер строки по исходному документу
            case 6 :return this.lnkb;    //ссылка на базовый документ
            case 7 :return this.idz;    //номер здания приведенный
            case 8 :return this.ird;    //приведенный номер РД
            case 9 :return this.izm;    //приведенный номер изменения
            case 10 :return this.name;   //Наименование проекта (B)
            case 11 :return this.razd;   //Раздел (E)
            case 12 :return this.vidr;   //Вид (F)
            case 13 :return this.stat;   //Статус последней версии документа
            case 14 :return this.dtv;    //дата официального получения
            case 15 :return this.dpv;    //дата получения предыдущей версии
            case 16 :return this.izp;    //номер предыдущей версии
            case 17 :return this.rep;    //примечание к РД (Z)
            case 18 :return true;
            default:return "!?! ";
        }//switch
    }//getVal
    //-------------------------------------------------
    public int getIzm() { return izm; }
    public void setIzm(int izm) { this.izm = izm; }
    public int getDtv() { return dtv; }
    public void setDtv(int dtv) { this.dtv = dtv; }
    public int getDpv() { return dpv; }
    public void setDpv(int dpv) { this.dpv = dpv; }
    public String getRazd() { return razd; }
    public void setRazd(String razd) { this.razd = razd; }
    public char getStat() { return stat; }
    public void setStat(char stat) { this.stat = stat; }
    public int getIzp() { return izp; }
    public void setIzp(int izp) { this.izp = izp; }
    public String getVidr() { return vidr; }
    public void setVidr(String vidr) { this.vidr = vidr; }

    public ElmRDS() {
        this(0,0,"","","",0,"","","", (char) 63,0,"");
    }//ElmRDS

    public ElmRDS(int fstr, int nstr, String lnkb, String idz, String ird, int izm,
                  String name, String razd, String vidr, char stat, int dtv, String rep) {
        this.fstr = fstr;   //порядковый номер строки
        this.nstr = nstr;   //порядковый номер строки по исходному документу
        this.lnkb = lnkb;   //ссылка на базовый документ
        this.idz  = idz;    //номер здания приведенный
        this.ird  = ird;    //приведенный номер РД
        this.izm  = izm;    //приведенный номер изменения
        this.name = name;   //Наименование проекта (B)
        this.razd = razd;   //Раздел (E)
        this.vidr = vidr;   //Вид (F)
        this.stat = stat;   //Статус последней версии документа
        this.dtv  = dtv;    //дата официального получения
        this.dpv  = dtv;    //дата получения предыдущей версии
        this.izp  = izm;    //номер предыдущей версии
        this.rep  = rep;    //примечание к РД (Z)
    }//ElmRDS

    public ElmRDS(ElmRDS q) {
        this.fstr = q.fstr;   //порядковый номер строки
        this.nstr = q.nstr;   //порядковый номер строки по исходному документу
        this.lnkb = q.lnkb;   //ссылка на базовый документ
        this.idz  = q.idz;    //номер здания приведенный
        this.ird  = q.ird;    //приведенный номер РД
        this.izm  = q.izm;    //приведенный номер изменения
        this.name = q.name;   //Наименование проекта (B)
        this.razd = q.razd;   //Раздел (E)
        this.vidr = q.vidr;   //Вид (F)
        this.stat = q.stat;   //Статус последней версии документа
        this.dtv  = q.dtv;    //дата официального получения
        this.dpv  = q.dpv;    //дата получения предыдущей версии
        this.izp  = q.izp;    //номер предыдущей версии
        this.rep  = q.rep;    //примечание к РД (Z)
    }//ElmRDS

    public String prn() {
        return "ё " +
//        this.fstr   + ":" + // 1 порядковый номер строки
        this.nstr + "_" + // 2  //порядковый номер строки по исходному документу
        this.lnkb +" b  "+ // 3 ссылка на базовый документ
        this.idz  + "z " + // 4 номер здания приведенный
//        this.ird  + ":" + // 5 приведенный номер РД
        this.izm  + "i " + // 6 приведенный номер изменения
        this.razd + "r " + // 8 Раздел (E)
        this.vidr + "v " + // 9 Вид (F)
        this.stat + "s " + // 0 Статус (H)
        this.name +"^"+ // 7 Наименование проекта (B)
        this.dtv + ":" +  // 1 сигнал у Заказчика (I)
        this.dpv + ":" +  // 2 дата получения предыдущей версии
        this.izp + ":" +  // 3 номер предыдущей версии
        this.rep;         // 4 номер накладной 1 (J)
    }//prn
}//class ElmRDS
