package qwr.model.Tabl;
//Элемент данных из регистрации карточек Заказчика
import qwr.action.ClaimsToCustomer;
import qwr.footing.InfcElm;
import qwr.footing.LoadFile;
import qwr.model.nexus.EDraft;
import qwr.model.nexus.EMail;
import qwr.model.reference.EjAuthor;
import qwr.model.reference.EjBuild;
import qwr.model.reference.EiChaptr;
import qwr.model.reference.EjOrganz;
import qwr.util.DateTim;
import qwr.util.Tranq;

import java.util.HashMap;
import java.util.Map;

import static qwr.util.BgFile.prnt;

public class EiClaim implements LoadFile {//Claim = Претензия, требование
    public static Map<String, InfcElm> mar =new HashMap<>(900);
    private int     npp;    //2  "№ п/п"
    private String  nClms;  //3  КЗ
    private String  nmail;  //4  "Обращение\n (№ письма)"
    private String  dept;   //5  "Организация \n(инициатор обращения)"
    private String  author;    //6  "ФИО инициатора обращения"
    private String  draft;   //7  "Инвентарный номер РД"
    private String  bild;   //8 "Здание/\nСооружение"
    private String  razd;   //9 "Вид СМР"
    private String  text;   //10 "Текст обращения"
    private String  neoc;   //11 "Номер записи в ЕОС"
    private String  nrpi;   //12 "Номер РПИ"
    private String  comnt;  //13 "Примечание УС БАЭС"
    private String  impct;  //14 влияние
    private String  resol;  //15 Решение ГУ ПН "Прорыв"
    private String  descr;  //16 Краткое пояснение ГУ ПН "Прорыв"
    private String  nwRdi;  //17 изменение РД

    private int     dmail;  //  дата письма
    private int     faza;   // Стадия рассмотрения замечания
    private String  sheet;  // имя листа откуда записали данные
    private int     keymail;// ключ связанного письма
    //-------------------------------------------------------------------------------
    public  String  getnClms() { return nClms; }
    public  String  getNmail() { return nmail; }
    public  void    setNmail(String nmail) { this.nmail = nmail; }
    public  void    setDmail(int dmail) { this.dmail = dmail; }
    public  int     getDmail() { return dmail; } //  дата письма
    public  String  getAuthor() { return author; } //6  "ФИО инициатора обращения"
    public  void    setAuthor(String author) { this.author = author; }//6  "ФИО инициатора обращения"
    public  void    setSheet(String sheet) { this.sheet = sheet; }
    public  String  getSheet() { return sheet; }
    public  int     getKeymail() { return keymail; }
    public  void    setKeymail(int keymail) { this.keymail = keymail; }

    public boolean have(){ return (draft !=null && text!=null && nClms!=null);}//элемент существует
    //-------------------------------------------------------------------------------
    public String prn(){ return "\t_"+sheet+"__"+this.dmail+"__"+this.nmail+
            "__"+this.author + "_\t_"+this.nClms+"_\t_"+this.draft +"_\t_"+this.dept; }

    public String print(){ return this.author +"\t"+this.nmail+"\t"+this.dmail; }

    /**
     * Пополняю список карточек на основе полученного массива строк таблицы и
     * анализа шапки таблицы
     * @param ldsrt строка таблицы в виде массива строк
     * @param cRD результат анализа шапки таблицы
     * @param sheetName
     * @return добавление в массив карточек (ClaimsToCustomer.lclem)
     */
    public static boolean have(String[] ldsrt, int[] cRD, String sheetName) {
        EiClaim itm= new EiClaim();//элемент для заполнения
        for (int i=0; i<cRD.length; i++) {//перебор вариантов полей
//            assert prnt("\n"+el.getIcol()+"\t"+el.getElcl()+"\t");
            if (cRD[i]<1) continue;//данное поле не найдено
            if (ldsrt[i].length()<1) continue;
            String s=ldsrt[i].trim();
            int length = s.length();
            char[] y = new char[length+1];
            int newLen = 0;
            y[newLen]= s.charAt(newLen++);//извлечение символа по индексу
            for (int  j = newLen ; j < length; j++) {
                char ch=s.charAt(j);
                if ((ch > 32) || (ch==32 && y[newLen-1]>32)) {
                    y[newLen] = ch;
                    newLen++;
                }
            }
            String x = new String(y, 0, newLen);
            switch (cRD[i]){
                case 0:                break;
                case 1:                break;
                case 2: itm.npp   =cnverti(x); break;  //2  "№ п/п"
                case 3: itm.nClms =x; break;  //3  КЗ
                case 4: itm.nmail =x; break;  //4  "Обращение\n (№ письма)"
                case 5: itm.dept  =x; break;  //5  "Организация \n(инициатор обращения)"
                case 6: itm.author =x; break;  //6  "ФИО инициатора обращения"
                case 7: itm.draft =x; break;  //7  "Инвентарный номер РД"
                case 8: itm.bild  =x; break;  //8 "Здание/\nСооружение"
                case 9: itm.razd  =x; break;  //9 "Вид СМР"
                case 10:itm.text  =x; break;  //10 "Текст обращения"
                case 11:itm.neoc  =x; break;  //11 "Номер записи в ЕОС"
                case 12:itm.nrpi  =x; break;  //12 "Номер РПИ"
                case 13:itm.comnt =x; break; //13 "Примечание УС БАЭС"
                case 14:itm.impct =x; break; //14 влияние
                case 15:itm.resol =x; break; //15 Решение ГУ ПН "Прорыв"
                case 16:itm.descr =x; break;  //16 Краткое пояснение ГУ ПН "Прорыв"
                case 17:itm.nwRdi =x; break;  //17 изменение РД
                case 18://распознавание письма

                    //проверяю на цифры, № и символ о или О
                    int ln=0;
                    for (int j = 0; j < x.length(); j++) {
                        char c = x.charAt(j);
                        if (c>='0' && c<='9') {ln=j; break;}
                        if (c=='№') { ln=j+1; break;}
                    }//for string X
                    if ( ln==0 ) {//нет ни цифр ни №
                        itm.author = EjAuthor.normalize(x);
                        itm.nmail=null;
                        itm.dmail=0;
                    } else {//есть цифра
                        itm.author = EjAuthor.normalize(x.substring(0,ln-2));
                        int j=DateTim.convertStringR(x.substring(ln).concat(" "));
                        int m=x.indexOf("от");
//                        assert prnq(""+j+"\t"+ln+"\t"+m+"\t"+x);
                        if (j>3 && ln<m ) {//есть признак даты
                            itm.nmail=x.substring(ln,m).trim();
                            itm.dmail=j;
                        } else {//нет признака даты
                            itm.nmail=x.substring(ln).trim();
                            itm.dmail=0;
                        }
                    }
                    break;
                default:prnt("EiClaim > have ~"+cRD[i]+">"+x+" ");continue;//что то пошло не так
            }//switch
        }//for lRctc
        itm.sheet=sheetName;//записываю имя листа
        if ((itm.draft !=null && itm.text!=null && itm.nClms!=null)) {
            ClaimsToCustomer.lclem.add(new EiClaim(itm)); }
        return true;//внести в список
    }
    /**
     * вызывается из LDocPrj > readFileXslx
     * @param srt разобранная строка нового элемента
     * @param cRD массив сответствия разобранной строки полям нового элемента
     * @param sheetName имя листа таблицы источника нового элемента
     */
//    @Override
    public void     parse(String[] srt, int[] cRD, String sheetName) {
        EiClaim obj= new EiClaim();//элемент для заполнения
        for (int i=0; i<cRD.length; i++) {//перебор вариантов полей
            String x = srt[i];
            switch (cRD[i]){
                case 0:                break;
                case 1:                break;
                case 2: obj.npp   = Tranq.cnverti(x); break;  //2  "№ п/п"
                case 3: obj.nClms =x; break;  //3  КЗ
                case 4: obj.nmail =x; break;  //4  "Обращение\n (№ письма)"
                case 5:
                    if (!srt[i].isBlank()) obj.dept= EjOrganz.normalize(srt[i]);
                    else   obj.dept=" ";
                    break;  //5  "Организация \n(инициатор обращения)"
                case 6:
                    if (!srt[i].isBlank()) obj.author= EjAuthor.normalize(srt[i]);
                    else   obj.author=" ";
                    break;  //6  "ФИО инициатора обращения"
                case 7:
                    obj.draft = EDraft.normalize(srt[i]);
                    break;  //7  "Инвентарный номер РД"
                case 8:
                    if (!srt[i].isBlank()) obj.bild= EjBuild.normalize(srt[i]);
                    else   obj.bild=" ";
                    break;  //8 "Здание/\nСооружение"
                case 9:
                    if (!srt[i].isBlank()) obj.razd= EiChaptr.normalize(srt[i]);
                    else   obj.razd=" ";
                    break;  //9 "Вид СМР"
                case 10:obj.text  =x; break;  //10 "Текст обращения"
                case 11:obj.neoc  =x; break;  //11 "Номер записи в ЕОС"
                case 12:obj.nrpi  =x; break;  //12 "Номер РПИ"
                case 13:obj.comnt =x; break; //13 "Примечание УС БАЭС"
                case 14:obj.impct =x; break; //14 влияние
                case 15:obj.resol =x; break; //15 Решение ГУ ПН "Прорыв"
                case 16:obj.descr =x; break;  //16 Краткое пояснение ГУ ПН "Прорыв"
                case 17:obj.nwRdi =x; break;  //17 изменение РД
                case 18://распознавание письма

                    //проверяю на цифры, № и символ о или О
                    int ln=0;
                    for (int j = 0; j < x.length(); j++) {
                        char c = x.charAt(j);
                        if (c>='0' && c<='9') {ln=j; break;}
                        if (c=='№') { ln=j+1; break;}
                    }//for string X
                    if ( ln==0 ) {//нет ни цифр ни №
                        obj.author = EjAuthor.normalize(x);
                        obj.nmail=null;
                        obj.dmail=0;
                    } else {//есть цифра
                        obj.author = EjAuthor.normalize(x.substring(0,ln-2));
                        int j=DateTim.convertStringR(x.substring(ln).concat(" "));
                        int m=x.indexOf("от");
//                        assert prnq(""+j+"\t"+ln+"\t"+m+"\t"+x);
                        if (j>3 && ln<m ) {//есть признак даты
                            obj.nmail=x.substring(ln,m).trim();
                            obj.dmail=j;
                        } else {//нет признака даты
                            obj.nmail=x.substring(ln).trim();
                            obj.dmail=0;
                        }
                    }
                    EMail.normalize(obj.nmail,obj.dmail,obj.author);
                    break;
                default:prnt("EiClaim > have ~"+cRD[i]+">"+x+" ");continue;//что то пошло не так
            }//switch
        }//for lRctc
        obj.sheet=sheetName;//записываю имя листа
//        if ((obj.rdin!=null && obj.text!=null && obj.nClms!=null)) {
//            ClaimsToCustomer.lclem.add(new EiClaim(obj)); }
        return;//добавил в список
    }    //parse---------------------------------------------------------------------

        //-------------------------------------------------------------------------------
    public EiClaim(EiClaim obj){
        npp=obj.npp;    //2  "№ п/п"
        nClms=obj.nClms;  //3  КЗ
        nmail=obj.nmail;  //4  "Обращение\n (№ письма)"
        dept=obj.dept;   //5  "Организация \n(инициатор обращения)"
        author =obj.author;    //6  "ФИО инициатора обращения"
        draft =obj.draft;   //7  "Инвентарный номер РД"
        bild=obj.bild;   //8 "Здание/\nСооружение"
        razd=obj.razd;   //9 "Вид СМР"
        text=obj.text;   //10 "Текст обращения"
        neoc=obj.neoc;   //11 "Номер записи в ЕОС"
        nrpi=obj.nrpi;   //12 "Номер РПИ"
        comnt=obj.comnt;  //13 "Примечание УС БАЭС"
        impct=obj.impct;  //14 влияние
        resol=obj.resol;  //15 Решение ГУ ПН "Прорыв"
        descr=obj.descr;  //16 Краткое пояснение ГУ ПН "Прорыв"
        nwRdi=obj.nwRdi;  //17 изменение РД

        dmail=obj.dmail;  //  дата письма
        faza=obj.faza;   // Стадия рассмотрения замечания
        sheet=obj.sheet;  // имя листа откуда записали данные
    }//EiClaim
    public EiClaim(){// очистка
        npp     =-1;    //2  "№ п/п"
        nClms   =null;  //3  КЗ
        nmail   =null;  //4  "Обращение\n (№ письма)"
        dept    =null;  //6  "Организация \n(инициатор обращения)"
        author =null;  //7  "ФИО инициатора обращения"
        draft =null;  //8  "Инвентарный номер РД"
        bild    =null;  //9 "Здание/\nСооружение"
        razd    =null;  //10 "Вид СМР"
        text    =null;  //11 "Текст обращения"
        neoc    =null;  //12 "Номер записи в ЕОС"
        nrpi    =null;  //13 "Номер РПИ"
        comnt   =null;  //14 "Примечание УС БАЭС"
        impct   =null;  //14 влияние
        resol   =null;  //15 Решение ГУ ПН "Прорыв"
        descr   =null;  //16 Краткое пояснение ГУ ПН "Прорыв"
        nwRdi   =null;  //17 изменение РД
        dmail   =-1;    //5  дата письма
        faza    =-1;
    }//EiClaim
     //-------------------------------------------------------------------------------
    public static int cnverti(String s){
        try { return Integer.parseInt(s);
        }catch(NumberFormatException e){prnt("\nError convert Integer");return -1;}}//cnverti

    //------------------------------------------------------------------------------


}//class Ecleam
