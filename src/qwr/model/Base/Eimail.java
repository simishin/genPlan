/**     описание элемента письма построены на базовом классе, где номер письма
 * соответствует наименованию, дата письсма соответствует идентификатору,
 * описание письма соответствует описанию, дата регистрации в базе соответствует
 * времени создания */
package qwr.model.Base;
import qwr.model.SharSystem.GrRecords;
import qwr.model.Tabl.EiClaim;

import static qwr.util.BgFile.prnq;
import static qwr.util.BgFile.sepr;

public class Eimail extends BaseElement {
    public static final int sizeAr=16;//количество полей в текстовом файле данных
    //              titul;  //2)номер письма(* String) ++++++
    //              chang;  //1)дата письма(* int) дней от 1990 года
    private String  sendr;  //источние
    private String  recip;  //адресат
    private String  content; //содержание письма +++++++
    //              key;    //8)дата регистрации в базе(* int)(ключ)++++++
    private String  issue;  //тема письма
    //              descr;  //3)исполнитель письма письма(* String)
    //              typ;    //4) способ и тип появления элемента (* short)
    //              isusr;  //5) используется в текущем проекте (*)
    //              solvd;  //6) разрешено использование (*)
    //              owner;  //7) пользователь создавший (* int)
    private int     attach; //ссылка на приложения
    private boolean tuotp;  //исходящее/входящее
    private String  atNmail;//на номер письма
    private int     atDmail;//на письмо от даты (дней от 1990 года)
    //конструкторы-----------------------------------------------------------------
    public Eimail() { super("",0); }
     public Eimail(String nmail, int dmail, String fio) {
        super(nmail,fio, (short) 1,true,true,dmail);
    }//------------------------------------------------------------------------------

    public Eimail(Eimail obj) {//копирование элемента
        super(obj.titul, obj.descr, obj.typ, obj.isusr, obj.solvd, obj.chang);
        this.owner = obj.owner;//подменяю значения пользователь создавший
        this.key = obj.key;//подменяю значения время создания
        this.sendr  = obj.sendr;
        this.recip  = obj.recip;
        this.content = obj.content;
        this.issue  = obj.issue;
        this.attach = obj.attach;
        this.tuotp  = obj.tuotp;
        this.atNmail= obj.atNmail;
        this.atDmail= obj.atDmail;
    }//Eimail

    @Override//----------------------------------------------------------------------
    public String writ() { return super.writ()+
        sendr+sepr+  //источние (String 9)
        recip+sepr+  //адресат (String 10)
        content +sepr+ //исполнитель письма (String 11)
        issue+sepr+  //тема письма (String 12)
        attach+sepr+ //ссылка на соержание(int 13)
        (tuotp  ? "Y" : "n")+sepr+  //исходящее/входящее (14)
        atNmail+sepr+//на номер письма (String 15)
        atDmail;     //на письмо от даты (int 16)
    }//write

    public boolean read(String s) {
        //проверяю длинну строки на минимальную длинну
        if (s.length()<30) {assert prnq("Error length");return false;}
        String[] words = s.split(sepr);//создаю масив значений
        if (super.read(words,16)) return false;
        sendr = words[9];
        recip = words[10];
        content = words[11];
        issue = words[12];
        attach= Integer.parseInt(words[13]);
        tuotp = words[14].startsWith("Y");
        atNmail = words[15];
        atDmail = Integer.parseInt(words[16]);
        return true;
    }//read

    public boolean read(String[] words) {
        if (super.read(words,sizeAr)) return true;
        sendr= words[9];//9) дата последней регистрации
        return false;
    }//read

//    @Override
//    public String toString(){return "qqqqqq";}

    public boolean equals(Eimail obj){ return
        (this.chang ==obj.chang) &&          //дата письма (* int 1)
        (this.titul.equals(obj.titul)) && //номер письма (* String 2) ==++++++
        (this.descr.equals(obj.descr)) && //исполнитель письма (* String 3)
        (this.typ ==obj.typ) &&        //способ появления элемента (short 4)
        (this.isusr==obj.isusr) &&        //используется в текущем проекте (5)
        (this.solvd ==obj.solvd) &&          //разрешено использование (6)
        (this.owner ==obj.owner) && //пользователь создавший (String 7)
        (this.key ==obj.key) &&        //дата регистрации в базе с1.01.1970(*long 8)+++++
        (this.sendr.equals(obj.sendr)) && //источние (String 9)
        (this.recip.equals(obj.recip)) && //адресат (String 10)
        (this.content.equals(obj.content)) &&//описание письма (String 11)
        (this.issue.equals(obj.issue)) && //тема письма (String 12)
        (this.attach==obj.attach) &&      //ссылка на соержание(int 13)
        (this.tuotp==obj.tuotp) &&        //исходящее/входящее (14)
        (this.atNmail.equals(obj.atNmail)) &&//на номер письма (String 15)
        (this.atDmail==obj.atDmail);      //на письмо от даты (int 16)
    }//equals

    public boolean merger(Eimail obj) {//проверка на совпдение
        if (!this.compare(obj)) return false;//идем дальше
        GrRecords.MAILJ.shift();//факт изменения
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
        return true;//
    }//merger------------------------------------------------------------------------
    @Override
    public String print(){return key+"\t ["+typ+"] \t"+owner+"\t  "+
        (isusr ? "Y":"n")+"  \t"+(solvd ? "Y":"n")+"  \t"+titul+"\t^"+descr+"\t"+chang;}

    public boolean compare(Eimail obj){
        return (this.titul.equals(obj.titul)) && (this.descr.equals(obj.descr)) &&
                (this.chang ==obj.chang);
    }//compare
    public boolean compare(EiClaim iclm){
        return (this.titul.equals(iclm.getNmail())) && (this.descr.equals(iclm.getAuthor())) &&
                (this.chang ==iclm.getDmail());
    }//compare

}//class Eimail