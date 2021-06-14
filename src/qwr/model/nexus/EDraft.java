/**
 * номера рабочей документации с дополнительными параметрами
 */
package qwr.model.nexus;

import qwr.footing.*;
import qwr.model.SharSystem.GrRecords;
import qwr.model.reference.EjBuild;
import qwr.model.reference.EiChaptr;
import qwr.model.reference.EiTypDoc;
import qwr.model.reference.EiStag;
import qwr.util.BgFile;
import qwr.util.Tranq;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static qwr.util.BgFile.*;

public class EDraft extends TreeGuide implements InfcElm, LoadFile {
    public static Map<String, InfcElm> mar =new HashMap<>(2400);//чертежи
    public static final int sizeAr=20;//количество полей в текстовом файле данных
    private static int count=1;//cчетчик для элементов по умолчанию
    // int          hasp;   //1 )ключ, идентификатор элемента (время регистрации)
    // String       title;  //2B)наименование элемента  = номер документа
    // String       descr;  //3I)описание элемента      = наименование документа
    // long         way;    //4 ) состояние элемента  1-по умолчанию 3-из локальных
    // int          order;  //5 )порядок следования
    //----------------------
    // String   ances;      //7 )предок номер и дата письма на которое дается ответ
    // int      ancei;      //6 )предок ключ письма на которое дается ответ
    // int      level;      //8 )уровень вложенности
    //----------------------
    private int     owner;  //9 )идентификатор создателя записи
    private int     chang;  //10)время редактирования записи в секундах с 1 января 1970г.
    private String  link;   //11)ссылка на содержание
    //-----------------------
    private String  bsdoc;  //12*) ссылка на базовый документ
    private String  bild;  //13E) номер здания приведенный
    private String  razd;   //14F) Раздел (E)
    private String  vidr;   //15G) Вид (F)
    private String  stag;   //16H) Статус последней версии документа
    private int     izm;    //17C) приведенный номер изменения (* int)
    private int     dtv;    //18D) дата официального получения (дней с 1970г.)
    private int     dpv;    //19J) дата получения предыдущей версии
    private int     izp;    //20K) номер предыдущей версии
    //конструкторы-----------------------------------------------------------------
    public EDraft(String title, String descr, long stas, int order) {
        super(" ",title, descr, stas, order);
        this.owner  = BgFile.getUserIdPrj();//идентификатор пользователя
        this.hasp   = LineGuide.questHasp(mar);//cек1.01.1970
        this.chang  = this.hasp;//cек1.01.1970
        link=" "; bsdoc=" "; bild =" "; razd=" "; vidr=" "; stag=" "; } //EDraft-------
    public EDraft() { super(" ","", "", 0, 0);
        this.owner  = BgFile.getUserIdPrj();//идентификатор пользователя
        this.hasp   = LineGuide.questHasp(mar);//cек1.01.1970
        this.chang  = 0;
        link=" "; bsdoc=" "; bild =" "; razd=" "; vidr=" "; stag=" "; } //EDraft-------
    //специальный конструктор--------------------------------------------------------
    public EDraft(String[] words) {
        super(words);
        this.owner =Integer.parseInt (words[9]); //9 )идентификатор создателя записи
        this.chang =Integer.parseInt (words[10]);//10)время редактирования в секундах с 1 января 1970г.
        this.link =words[11];                    //11)ссылка на содержание
        this.bsdoc =(words[12]);
        this.bild =(words[13]);
        this.razd =(words[14]);
        this.vidr =(words[15]);
        this.stag =(words[16]);
        this.izm =Integer.parseInt (words[17]);
        this.dtv =Integer.parseInt (words[18]);
        this.dpv =Integer.parseInt (words[19]);
        this.izp =Integer.parseInt (words[20]);
    }  //-----------------------------
    /**
     * полное копирование принимаемого элемента в элемент колекции кроме наименования
     * @param itm   принимаемый элемент
     * @return
     */
    @Override
    public void overlay(InfcElm itm) {
        EDraft obj = (EDraft) itm;
        super.overlay(itm);
        this.owner  =obj.getOwner();//9 )идентификатор создателя записи
        this.chang  =obj.getChang();//10)время редактирования записи в секундах с 1 января 1970г.
        this.link   =obj.getLink(); //11)ссылка на содержание
        this.bsdoc  =obj.getBsdoc();
        this.bild   =obj.getBild();
        this.razd   =obj.getRazd();
        this.vidr   =obj.getVidr();
        this.stag   =obj.getStag();
        this.izm    =obj.getIzm();
        this.dtv    =obj.getDtv();
        this.dpv    =obj.getDpv();
        this.izp    =obj.getIzm();//инверсное сохранение
    }  //copy------------------------------------------------------------------------
    @Override
    public void overlap(ListElm itm) {
        EDraft obj = (EDraft) itm;
        super.overlap(itm);
        this.owner  =obj.getOwner();//9 )идентификатор создателя записи
        this.chang  =obj.getChang();//10)время редактирования записи в секундах с 1 января 1970г.
        this.link   =obj.getLink(); //11)ссылка на содержание
        this.bsdoc  =obj.getBsdoc();
        this.bild   =obj.getBild();
        this.razd   =obj.getRazd();
        this.vidr   =obj.getVidr();
        this.stag   =obj.getStag();
        this.izm    =obj.getIzm();
        this.dtv    =obj.getDtv();
        this.dpv    =obj.getDpv();
        this.izp    =obj.getIzm();//инверсное сохранение
    }  //copy------------------------------------------------------------------------

 


    @Override//----------------------------------------------------------------------
    public boolean  compare(InfcElm itm) { //если равны, то истина (кроме статуса )
        EDraft obj = (EDraft) itm;
//        assert prnq(title+" \t"+super.compare(itm)+"\t"+chang+"/"+obj.getChang());
        return super.compare(itm)
                && owner==obj.getOwner() //9 )идентификатор создателя записи
                && (chang==obj.getChang() || obj.getChang()==0 )//10)время редактирования записи в секундах с 1 января 1970г.
                && link.equals(obj.getLink())   //11)ссылка на содержание
                && bsdoc.equals(obj.getBsdoc())
                && bild.equals(obj.getBild())
                && razd.equals(obj.getRazd())
                && vidr.equals(obj.getVidr())
                && stag.equals(obj.getStag())
                && izm>=obj.getIzm()
                && dtv>=obj.getDtv()
                && dpv==obj.getDpv()
                && izp>=obj.getIzp()    ;
    }//compare-----------------------------------------------------------------------

    @Override//----------------------------------------------------------------------
    public String   writ(){ return super.writ()
            + owner +sepr + chang +sepr + (link.isEmpty()?" ": link) +sepr
            + (bsdoc.isEmpty()?" ":bsdoc ) +sepr
            + (bild.isEmpty()?" ": bild) +sepr + (razd.isEmpty()?" ":razd ) +sepr
            + (vidr.isEmpty()?" ":vidr ) +sepr + (stag.isEmpty()?" ":stag ) +sepr
            + izm +sepr + dtv +sepr + dpv +sepr + izp +sepr;}

    @Override
    public void replace(ListElm obj, int src) {

    }
    //-----------------------------------------------------------------------------
    /**
     * Приведение к стандарту встречающихся записи в исходных документах
     *     Проверяю на наличие в базе.если элемент найден, то делаю подстановку
     * @param str строка исходного не нормализованного документа с возможностью
     *            множественных значений
     * @return значение без изменения
     */
    public static String normalize(String str){
        assert str!=null: "! EDraft > normalize: str = null !";
        if (str.length()<5) return " ";//строка слишком мала - не проверяю
//        StringBuilder s=new StringBuilder(str.toUpperCase(Locale.ROOT));
        StringBuilder s=new StringBuilder(str.toUpperCase(Locale.ROOT)
                .replace(',',' ').replace(';',' '));
        StringBuilder rez=new StringBuilder(s.length()+1);
        int i,j,n;
        String sa, sb;
//        prnt("k\ti\tj\tn\t (i<j)\t (j<n)\t"+s.length());
        for (int k=0; k<s.length(); k=i+1) {
            i=s.indexOf(" ",k);
            j=s.indexOf("И",k);
            n=s.indexOf(" ",i+1);
//            prnt("\n"+k+"\t"+i+"\t"+j+" \t"+n+"\t"+(i<j)+" \t"+(j<n)+" \t=");
            if (i<j || j==-1){
//                prnt("@");
                sa= s.substring(k,i);
                if (j<n && j>0 && (j-i)<4) sb= s.substring(j,n);
                else sb="";
            }
            else  {
//                prnt("#");
                sa= s.substring(k,j);
                sb= s.substring(j,i);
            }
            if (k==j || k==i )continue;
//            prnt(sa+"~~"+sb+"$");
            rez.append(sa).append(sb).append(" ");
            if (mar.putIfAbsent(sa, new EDraft(sa," ",17,-1))==null)
                GrRecords.DRAFT.shift();//ставлю флаг наличия модификации списка
        }//for
        return String.valueOf(rez);
    }//normalize---------------------------------------------------------------------
    /**
     * вызывается из LDocPrj > readFileXslx
     * @param srt разобранная строка нового элемента
     * @param cRD массив сответствия разобранной строки полям нового элемента
     * @param sheetName имя листа таблицы источника нового элемента
     */
//    @Override
    public void     parse(String[] srt, int[] cRD, String sheetName) {
        EDraft obj = new EDraft();
        for (int i=0; i<cRD.length; i++) {//перебор вариантов полей
            switch (cRD[i]){
                case 0:                break;
                case 1:
                    if (srt[i].isBlank())  return; //проверка на пустоту
                    obj.title =EDraft.normalize(srt[i]);
                    break;  //2B)= номер документа
                case 2: obj.descr =srt[i]; break;  //3I)= наименование документа
                case 5:
                    if (!srt[i].isBlank()) obj.bild = EjBuild.normalize(srt[i]);
                    else   obj.bild =" ";
                    break;  //7E) номер здания приведенный
                case 6:
                    if (!srt[i].isBlank()) obj.razd=EiChaptr.normalize(srt[i]);
                    else   obj.razd=" ";
                    break;  //8F) Раздел (E)
                case 7:
                    if (!srt[i].isBlank()) obj.vidr= EiTypDoc.normalize(srt[i]);
                    else   obj.vidr=" ";
                    break;  //9G) Вид (F)
                case 8:
                    if (!srt[i].isBlank()) obj.stag= EiStag.normalize(srt[i]);
                    else   obj.stag=" ";
                    break;//10H)Статус
                case 3: obj.izm   =Tranq.cnverti(srt[i]); break;//11C) приведенный номер изменения (* int)
                case 4: obj.dtv   =Tranq.cnverti(srt[i]); break;//12D) дата официального получения (дней с 1970г.)
                case 9: obj.dpv   =Tranq.cnverti(srt[i]); break;//13J) дата получения предыдущей версии
                case 10:obj.izp   =Tranq.cnverti(srt[i]); break;//14K) номер предыдущей версии

                default:prnt("EDraft>parse ~"+cRD[i]+">"+srt[i]+" ");continue;//что то пошло не так
            }//switch
        }//for lRctc
//        if (obj.getTitle().isBlank()) return;//проверка на пустоту
        obj.setOrder(count++);
        obj.setWay(3);
        obj.setHasp(LineGuide.questHasp(mar,obj.hashCode()));
        if (LineGuide.integrate(mar,obj,0)>0) GrRecords.DRAFT.shift();
        return;//добавил в список
    }    //parse---------------------------------------------------------------------

    @Override
    public String   getKey() { return title; } //--------------------------------------
    public String   getBsdoc() { return bsdoc; }
    public void     setBsdoc(String bsdoc) { this.bsdoc = bsdoc; }
    public String   getBild() { return bild; }
    public void     setBild(String bild) { this.bild = bild; }
    public String   getRazd() { return razd; }
    public void     setRazd(String razd) { this.razd = razd; }
    public String   getVidr() { return vidr; }
    public void     setVidr(String vidr) { this.vidr = vidr; }
    public String   getStag() { return stag; }
    public void     setStag(String stag) { this.stag = stag; }
    public int      getIzm() { return izm; }//11C) приведенный номер изменения (* int)
    public void     setIzm(int izm) { this.izm = izm; }//11C) приведенный номер изменения (* int)
    public int      getDtv() { return dtv; } //12D) дата официального получения (дней с 1970г.)
    public void     setDtv(int dtv) { this.dtv = dtv; } //12D) дата официального получения (дней с 1970г.)
    public int      getDpv() { return dpv; }
    public void     setDpv(int dpv) { this.dpv = dpv; }
    public int      getIzp() { return izp; }
    public void     setIzp(int izp) { this.izp = izp; }
    public int      getOwner() { return owner; }
    public void     setOwner(int owner) { this.owner = owner; }
    public int      getChang() { return chang; }
    public void     setChang(int chang) { this.chang = chang; }
    public String   getLink() { return link; }
    public void     setLink(String link) { this.link = link; }
    @Override
    public String   print(){  return
        bsdoc+"\t"+ bild +"   \t"+razd+"\t"+vidr+"\t."+ stag +".\t"+izm+"\t"+dtv+"\t"+
                super.print();
    } //print------------------------------------------------------------------------

    @Override
    public boolean addElm() {
        return false;
    }
}   //class NmbWrDocPrj =============================================================
