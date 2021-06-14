package qwr.model.SharSystem;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.CheckBox;

public class LPath {
    private int dPathCl;
    private final StringProperty nm;//сам путь
    private final SimpleStringProperty pth;//сам путь
//    private String spz;//наименование часового пояса
    private final SimpleStringProperty spz;//наименование часового пояса
    private int zi; //смещение часового пояса
    private final SimpleBooleanProperty  wrb; //доступность записи ТЕСТ
    private final SimpleBooleanProperty acb; //актуальность, ПРОШЛА СИНХРОНИЗАЦИЯ ТЕСТ
    private CheckBox mnb; //основные путь данным или копиям ЗАДАЕТСЯ
    private CheckBox glb; //глобальные данные или проектные ЗАДАЕТСЯ
    private final SimpleBooleanProperty ens;//доступность в данный момент ТЕСТ
    private CheckBox cbxa;//просто CheckBox синхронизация ЗАДАЕТСЯ
//    private StringProperty y = new SimpleStringProperty(" x");
//    private StringProperty n = new SimpleStringProperty(" -");

    public LPath(){
        this("abc",0,true,true);
    }//LPath
    public LPath(String s, int i, boolean bs, boolean bg){
        this.dPathCl=0;
        this.nm=new SimpleStringProperty(s);
        this.pth=new SimpleStringProperty(s);
//        this.spz ="ъйц";
        this.spz = new SimpleStringProperty("QWL");
        this.zi =i;

        this.acb=new SimpleBooleanProperty(false);
        this.ens=new SimpleBooleanProperty(false);
        this.wrb=new SimpleBooleanProperty(false);
        ens.set(true);
        this.mnb=new CheckBox();
        this.glb=new CheckBox();
        this.cbxa=new CheckBox();
        cbxa.setSelected(true);//задаю значение по умолчанию
        cbxa.setIndeterminate(true);//задаю третье состояние
        cbxa.setAllowIndeterminate(true);//даю возможность выбирать из трех состояний
    }//LPath
    //--------------------------------------------------------
    public String getSpz(){return spz.get();}
    public void setSpz(String s){spz.set(s);}
    //--------------------------------------------------------
    @Override //переопределяю метод для вывода в выпадающее меню ОБЯЗАТЕЛЬНО
    public String toString () {
        return nm.get();
    }
    public String getnPath(){return nm.get();}
    //--------------------------------------------------------
    public String getPth(){return pth.get();}
    public void setPth(String s){pth.set(s);}
    //-------------------------------------------------------
    public CheckBox getMnb(){ return mnb;}
    public void setMnb(CheckBox c){this.mnb=c;}
    public String strMnb(){return mnb.isSelected()? "Y": "n";}
    //--------------------------------------------------------
    public CheckBox getGlb(){ return glb;}
    public void setGlb(CheckBox c){this.glb=c;}
    public String strGlb(){return glb.isSelected()? "Y": "n";}
    //----------------------------------------------------------
    public CheckBox getCbxa(){ return cbxa;}
    public void setCbxa(CheckBox c){this.cbxa=c;}
    public String strCbxa(){return cbxa.isSelected()? "Y": "n";}
    //----------------------------------------------------------
    public boolean isAcb(){return acb.get();}
    public SimpleBooleanProperty acbProperty(){return acb;}
    public void setAcb(boolean b){this.acb.set(b);}
    //----------------------------------------------------------
    public boolean isEns(){return ens.get();}
    public SimpleBooleanProperty ensProperty(){return ens;}
    public void setEns(boolean b){this.ens.set(b);}
    //----------------------------------------------------------
    public boolean isWrb(){return wrb.get();}
    public SimpleBooleanProperty wrbProperty(){return wrb;}
    public void setWrb(boolean b){this.wrb.set(b);}
    //----------------------------------------------------------
    public String getznPath(){return String.valueOf(zi);}
    public int getZi(){return zi;}
    public void setZi(int i){zi=i;}
    //----------------------------------------------------------

    public int getdPathCl(){return dPathCl;}
    public void setdPathCl(int i){this.dPathCl=i;}

//    public String getSpz(){return spz;}
//    public void setSpz(String s){this.spz =s;}

    public StringProperty nPathProperty() { return nm;  }
    public StringProperty znPathProperty(){
        return (new SimpleStringProperty(String.valueOf(zi)));}
    //из контроллера в редактировании значения ячейки
    public void setObjectName(String newValue) {new SimpleStringProperty(newValue);
    }

    //   public boolean isnProperty(){return true;}
}//class LPath
