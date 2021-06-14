package qwr.footing;

import static qwr.util.BgFile.sepr;

public  abstract class MatrGuide extends TreeGuide {
    protected   int       owner; //7) создатель элемента
    protected   int       creat; //8) создание элемента
    protected   int       chang; //9) время последней регистрации (* int)
    //общий конструктор--------------------------------------------------------------

    public MatrGuide(String title, String descr, int nestd, int owner, int creat, int chang) {
        super("", title, descr, 9,0);
        this.ancei = nestd;//прородитель
        this.owner = owner;
        this.creat = creat;
        this.chang = chang;
    }//MatrGuide---------------------------------------------------------------------
    public  int     getOwner() { return owner; }
    public  void    setOwner(int owner) { this.owner = owner; }
    public  int     getCreat() { return creat; }
    public  void    setCreat(int creat) { this.creat = creat; }
    public  int     getChang() { return chang; }
    public  void    setChang(int chang) { this.chang = chang; }
    //-------------------------------------------------------------------------------
/*    protected boolean read(String[] words, int sizeAr){
        assert sizeAr>9:"--BaseElement size arrey < 9";
        if (super.read(words,sizeAr)) return true;
        this.owner=Integer.parseInt (words[7]);
        this.creat=Integer.parseInt (words[8]);
        this.chang=Integer.parseInt (words[9]);
        return false;
    }//read
    
 */
    public String   writ() { return super.writ()+owner+sepr+creat+sepr+chang+sepr;}
    public String   print(){
        return hasp+"\t ["+ title +"] \t"+(way & 15)+"  \t"+ "  \t"+ descr;}
}// abstract class MatrGuide=========================================================
