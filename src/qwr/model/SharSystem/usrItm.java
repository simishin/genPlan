/**
 * Класс описывающий пользователей проектов
 * имеет логин, полное имя, дату создания и кто создал,
 * дату последней регистрации, флаг далее не использовать,
 * флаг использования в проектах, уровень пользователя:
 * 1-администратор проекта, 2-получен из внешних проектов(-1),
 * 3-активин в данном проекте(1), 4-имя компьютера(0)
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
package qwr.model.SharSystem;

import qwr.util.DateTim;

//import static qwr.MainAs.prnq;
import static qwr.util.BgFile.*;
//import static qwr.util.strini.sepr;

//описание пользователя
public class usrItm {
    private String login;//имя пользователя (логин)
    private String fio;//полное имя
    private boolean typ;//компьютер или человек
    private boolean ers; //Далее НЕ использовать
    private boolean spr; //флаг супервизора (должен быть хотябы один супеервизор)
    private boolean exst;//флаг внешнего пользователя
    private boolean isUs;//используется в проекте
    private boolean exUs;//активно присутствует во внешних проектах
    private String datin;//дата создания
    private String usrCr;//пользователь создавший
    private String datold;//дата последней регистрации
    private String kp;//имя компьютера последней регистрации
    //----------------------------------------------------------
    public usrItm(){//call: itFl:82,97;
        this.login=System.getProperty("user.name");
        this.fio=System.getProperty("user.name");
        this.typ=true;//компьютер
        this.spr=false;//супервизор
        this.ers=false;//Далее НЕ использовать
        this.exst=false;//из внешний источников
        this.datin=getNowData();//дата создания
        this.usrCr=getUserPrj();//пользователь создавший
        this.datold=getNowData();//дата последней регистрации
        this.kp =getCmpName();//имя компьютера последней регистрации
        this.isUs=false;//используется в проекте
    }//rItm
    /**конструктор объекта
     * вызывается из: RmenuCntr:168>addUsCmp; strini:165>fullbase
     * @param s login и fio объекта
     * @param b b=true, то из внешних нанных
     */
    public usrItm(String s,boolean b){//b=true, то из внешних нанных
        this.login=s;
        this.fio=s;
        this.typ=b;//компьбютер
        this.spr=false;//супервизор
        this.ers=false;//Далее НЕ использовать
        this.exst=b;//из внешний источников
        this.datin=getNowData();//дата создания
        this.usrCr=b ? "" : getUserPrj();//пользователь создавший
        this.datold=b ? "" : getNowData();//дата последней регистрации
        this.kp =b ? "" : getCmpName();//имя компьютера последней регистрации
        this.isUs=b;//используется в проекте
    }//usrItm
    /**
     * создание пользователя из меню пользователей call:WndUsers:202>=iAd
     * @param lg логин
     * @param fi имя
     * @param spr флаг администратора
     */
    public usrItm(String lg,String fi, boolean spr){//call:WndUsers:202>=iAd
        this.login=lg;
        this.fio=fi;
        this.typ=false;//компьбютер
        this.spr=spr;//супервизор
        this.ers=false;//Далее НЕ использовать
        this.exst=false;//из внешний источников
        this.datin=getNowData();//дата создания
        this.usrCr=getUserPrj();//пользователь создавший
        this.datold=getNowData();//дата последней регистрации
        this.kp =getCmpName();//имя компьютера последней регистрации
        this.isUs=false;//используется в проекте
    }//usrItm
    public usrItm(String lg,String fi,boolean typ, boolean ers, boolean spr,
                  boolean exst, boolean isUs, String i, String j, String kp){
        this.login=lg;
        this.fio=fi;
        this.typ=typ;//компьбютер
        this.ers=ers;//Далее НЕ использовать
        this.spr=spr;//супервизор
        this.exst=exst;//местный
        this.isUs=isUs;//используется в проекте
       //boolean exUs;//активно присутствует во внешних проектах
        this.datin=i;//дата создания
        this.usrCr=getUserPrj();//пользователь создавший
        this.datold=j;//дата последней регистрации
        this.kp =kp;//имя компьютера последней регистрации
    }//usrItm
    @Override
    public String toString(){return this.login;}
    public String prn(){return (spr?"a":"-")+" "+(ers?"x":"+")+" "+
            (isUs?"u":"n")+" "+(exst?"e":"i")+" "+(typ?"c":"p")+" "+this.login+" d:"+
            datin+" u:"+usrCr+" d:"+datold+" u:"+kp+"$"+datin.length();}
    public String getLogin(){return login;}
    public String getFio(){return fio;}
    public String getKp(){return this.kp;}
    public String getDatin(){return datin;}
    public String getUsrCr(){return usrCr;}
    public String getDatold(){return datold;}
    public boolean isTyp(){return typ;}
    public boolean isSpr(){return spr;}
    public boolean isErs(){return ers;}
    public boolean isExst(){return exst;}
    public boolean isIsUs(){return isUs;}

    public void setLogin(String s){login=s;}
    public void setFio(String s){fio=s;}
    public void setKp(String s){kp=s;}
    public void setTyp(boolean s){typ =s;}
    public void setErs(boolean s){ers =s;}
    public void setSpr(boolean s){spr =s;}
    public void setExst(boolean s){exst =s;}
    public void setIsUs(boolean s){isUs =s;}
    public void setUsrCr(String s){this.usrCr=s;}
    public void setDatin(String s){datin=s;}
    public void setDatold(String s){datold=s;}
    public static usrItm setItem(usrItm n) {//подгрузка нового объекта
        usrItm s =new usrItm();
        s.login=n.login;//имя пользователя (логин)
        s.fio=n.fio;//полное имя
        s.typ=n.typ;//компьютер или человек
        s.ers=n.ers; //Далее НЕ использовать
        s.spr=n.spr; //флаг супервизора (должен быть хотябы один супеервизор)
        s.exst=true;//флаг внешнего пользователя
        s.isUs=false;//используется в проекте
        s.exUs=n.exUs || n.isUs;//активно присутствует во внешних проектах
        s.datin=n.datin;//дата создания
        s.usrCr=n.usrCr;//пользователь создавший
        return s;
    }//setItem
    /**-----------------------------------------------------------------------------
     * Забираю данные из внешнего источника
     * @param n внешний источник
     * @param p = true приоритет данных из внешнего источника, иначе локальных
     * @return = true если были внесены изменения в локальные данные, иначе false
     */
    public boolean setItem(usrItm n, boolean p){//копирование объекта с приоритетом
        assert prnq("usrItm>setItem ~ "+"="+n.getLogin()+" "+usrCr+" "+n.usrCr);
        if (//проверяю на совпадение значений
     //нет смысла   this.login==n.login &&//имя пользователя (логин)
        this.fio.equals(n.fio)&&//полное имя
        this.typ==n.typ&&//компьютер или человек
        this.ers==n.ers&& //Далее НЕ использовать
        this.spr==n.spr&& //флаг супервизора (должен быть хотябы один супеервизор)
     //меняется   this.exst==n.exst&&//флаг внешнего пользователя
        this.isUs==n.isUs&&//используется в проекте
        this.exUs==n.exUs&&//активно присутствует во внешних проектах
        this.datin.equals(n.datin)&&//дата создания
        this.usrCr.equals(n.usrCr)&&//пользователь создавший
        this.datold.equals(n.datold)&&//дата последней регистрации
        this.kp.equals(n.kp)//имя компьютера последней регистрации
        ) {assert prnq("usrItm>setItem ~Q ");
            return false;}
        if (p){//приоритет новых
            assert prnq("usrItm>setItem ~T ");
            this.fio=n.fio;//полное имя
            this.typ=n.typ;//компьютер или человек
            this.ers=n.ers; //Далее НЕ использовать
            this.spr=n.spr; //флаг супервизора (должен быть хотябы один супеервизор)
            this.exst=true;//флаг внешнего пользователя
 //           this.isUs=n.isUs;//используется в проекте
            this.exUs=n.exUs || n.isUs;//активно присутствует во внешних проектах
            this.datin=n.datin;//дата создания
            this.usrCr=n.usrCr;//пользователь создавший
        } else //приоритет локальных(прежних) данных
//            assert prnq("~F ");
            this.exUs=n.exUs || n.isUs;//активно присутствует во внешних проектах
        if (DateTim.previous(this.datold,n.datold)){
            this.datold=n.datold;//дата последней регистрации
            this.kp=n.kp;//имя компьютера последней регистрации
        }
        return true;
    }//setItem
    //------------------------------------------------------------------------------
    public boolean contains(String filter) {
        assert prnq("usritm:159 > contains " + filter);
        return true;
    }//contains
    //-- описание фильтра для вывода в листинге на экране -----------------
    public boolean contains(String filter,boolean typ, boolean ers, boolean spr,
                            boolean exst, boolean isUs) {
        boolean y=spr&this.spr | ers&this.ers | typ&this.typ | isUs&this.isUs | exst&this.exst;
        boolean z=login.startsWith(filter);
        z=filter.isEmpty() ? y : y & login.startsWith(filter);
//        assert prnq("usritm:87 > contains " + filter+" "+spr);
        return filter.isEmpty() ? y : y & login.startsWith(filter);
    }//contains
    //------------------------------------------------------------------------------
    public  String writ(){
        return login+sepr+
                fio+sepr+
                (typ ? "Y" : "n")+sepr+
                (ers ? "Y" : "n") +sepr+
                (spr ? "Y" : "n")+sepr+
                (exst ? "Y" : "n")+sepr+
                (isUs ? "Y" : "n")+sepr+
                datin+sepr+
                usrCr+sepr+
                datold+sepr+
               kp+"\n" ;
    }//writ
    //------------------------------------------------------------------------------
    public boolean read(String s){
        if (s.length()<30) {assert prnq("Error length USER");return false;}
        String[] words = s.split(sepr);
        if (words.length<1) { assert prnq("ext:CFG>load:USI* Error length:" +
                words.length );  return false; }
        if (words.length>11) {
            login = words[1];
            fio = words[2];
            typ = words[3].startsWith("Y");
            ers = words[4].startsWith("Y");
            spr = words[5].startsWith("Y");
            exst = words[6].startsWith("Y");
            isUs = words[7].startsWith("Y");
            datin = DateTim.tstlnd(words[8]) ? words[8]:"";
            usrCr = words[9];
            datold = DateTim.tstlnd(words[10]) ? words[10]:"";
            kp = words[11];
        }else {assert prnq("itFl>readPl:93 - Not full pole"); return false; }
        return true;
    }//read
    public boolean isGut(){//проверка пользователя
        return !typ && !ers;//он не компьютер и не устарел
    }//isGut
    public boolean isRigh(GrRecords el){//проверяю пользователя на права
        return spr;//пока только супервизор
    }
}//class usritm
