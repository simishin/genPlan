package qwr;

import qwr.footing.Ticked;
import qwr.model.nexus.ECard;

import java.util.Locale;

import static qwr.util.BgFile.prnq;
import static qwr.util.BgFile.prnt;

public class MainTest {
//    public static Map<String, InfcElm> lnTest=new HashMap<>(80);//виды фирм


    public static void main(String[] args) {
        prnq("\n------- Testing ----------");
/*        String si;
        String[] ars = {" 16-01962\n  16-01363и3 и5    \n" +
                "15-00720    И2\n" + "15-08721  19-09720    И2",
        "16-01962\n" + "16-01963   16-01363и3     \n" +
                "15-00720    И2\n" +  "15-08721  19-09720",
                "  ","26-21363и3","36-91363"};
        String[] arg ={  "Ждаморева\n" +
                "О.П. № 1926 \n" +
                "от 14.06.2019 \n" +
                "и № 2116 от 03.07.2019\n",
               ""
        };

        for (int i=0; i<ars.length; i++) {
            si = Tranq.purifyin(ars[i]);
            prnq(i+">\t" + si + "<\n\t"+ normal(si));
        }

 */
        //    ECard.printList("ECard~");
//        Ticked.printList("ECard-", ECard.lst);
        ECard a0 = new ECard("qwert0",12,1);
        ECard a1 = new ECard("qwert1",12,-1);
        ECard a11 = new ECard("qwert1",12,-1);
        ECard a2 = new ECard("qwert2",12,-1);
        ECard a3 = new ECard("qwert3",12,-1);
        ECard a4 = new ECard("qwert4",12,-1);
        ECard a5 = new ECard("qwert5",12,-1);
        ECard a6 = new ECard("qwert6",12,-1);
        ECard a7 = new ECard("qwert7",12,-1);
        a3.setOrder(2);
//        a5.setOrder(3);

        a0.addElm(1);
        new ECard("qwert1",12,-1,"~").addElm(0);
//        a2.addElm(0,1);//3
//        a3.addElm(0);//2
//        a4.addElm(0,1);
        a5.addElm(0);//3
        a11.addElm(0);//3
//        a6.addElm(0,1);
        a7.addElm(0);
//        ECard.printList("ECard!");
        new ECard("qwert0",12,1).addElm(0);
        new ECard("qwert1",12,-1,"%").addElm(1);
        ECard.printMaps("\n~");
    }//main


    /**
     * нормализация списка номеров проектов с разделением на номер и изменение
     * @param str исходная строка списка проектов
     * @return
     */
    public static String normal(String str){
        assert str!=null: "! EDraft > normalize: str = null !";
        if(str.isBlank()) return " ";
        //приведение разделителей
        StringBuilder s=new StringBuilder(str.toUpperCase(Locale.ROOT)
                .replace(',',' ').replace(';',' '));
        StringBuilder rez=new StringBuilder(s.length()+1);
        int i,j,n;
        String sa, sb;
        prnt("k\ti\tj\tn\t (i<j)\t (j<n)\t"+s.length());
        for (int k=0; k<s.length(); k=i+1) {
            i=s.indexOf(" ",k);
            j=s.indexOf("И",k);
            n=s.indexOf(" ",i+1);
            prnt("\n"+k+"\t"+i+"\t"+j+" \t"+n+"\t"+(i<j)+" \t"+(j<n)+" \t=");
            if (i<j || j==-1){
                prnt("@");
                sa= s.substring(k,i);
                if (j<n && j>0 && (j-i)<4) sb= s.substring(j,n);
                else sb="";
            }
            else  {
                prnt("#");
                sa= s.substring(k,j);
                sb= s.substring(j,i);
            }
            if (k==j || k==i )continue;
            prnt(sa+"~~"+sb+"$");
            rez.append(sa).append(sb).append(" ");
        }
        prnq("\n");
        return String.valueOf(rez);
    }
}// class MainTest
