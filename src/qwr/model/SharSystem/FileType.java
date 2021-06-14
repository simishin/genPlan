/**
 * перечень всех существующих типов файлов.
 * флаг загрузки проекта используется для выявления внешних файлов которые нужно
 * анализировать при автоматическом промотре внешних каталогов
 */
package qwr.model.SharSystem;

import qwr.model.Base.EiFile;
import qwr.model.Base.EiPath;
import qwr.util.BgFile;

import java.io.*;
import java.nio.file.*;

import static qwr.Inizial.InGuid.lsFile;
import static qwr.util.BgFile.*;

public enum FileType {
xlsx(345,"Файл электронных таблиц проекта",false){
    public String   loadFileName;   //имя и путь файла подгрузки данных из таблицы
    public boolean save(){
        assert prnq("\nСохранение результата работы в файле "+pach);
        assert pach!=null:"-->saveAs pach==null";
        try  {
            FileOutputStream out = new FileOutputStream(new File(BgFile.flout));
            BgFile.wbookPublic.write(out);
            out.close();
            System.out.println( "Результат сохранен");
        } catch (Exception e){ e.printStackTrace(); }
        return true;
    }//saveAs

},//=================================================================================
lsf(0,"Файл списка всех файлов проекта",false) {
    public boolean save(){
        assert prnq("ext:TST>saveAs");
        assert pach!=null:"--ext:TST>saveAs pach==null";
        return true;
    }//saveAs
    //занесение в справочник файлов------------------------------------------------------
    public boolean include(Path path, EiPath dir){//беру путь и сканирую его в поисках файлов
        if (!Files.exists(path)){//проверяю на существование папки
            prnq("\tError directory is NOT >"+path);return true;}
        boolean b;
        try (DirectoryStream<Path> files = Files.newDirectoryStream(path, BgFile.getGlob())){
            for (Path entry : files) {//просматриваю файлы в указанной папке
//                assert prnq("30 >"+entry+"  ="+lsFile.size());
                if(Files.exists(entry) && Files.isRegularFile(entry)){//он существует и он файл
                    b=false;
                    for (EiFile e:lsFile) {//добавляю в список файлов
                        //assert prnq("FileType 32 include >"+e.getTitul()+"<>"+entry);
                        if (e.getTitul().equals(entry.toString())) continue;//есть такой
                        b=true;
                    }//for lsFile
                    if (b)  lsFile.add(new EiFile(entry.toString(),dir));//добавляю элемент
                }//if exists
            }//for files
        } catch (IOException x) { System.err.println(">"+x); return true; }
        return true;
    }//includt All
},//==============================================================================
ini(12,"Файл инициализации проекта",false){
    private String defNameFile ="listprj";//имя файла списка проектов
    private boolean noDefFile =true; //файл инициализации не задан

    public boolean definePach(String nmPr){
        assert nmPr!=null:"set pach=null";
        assert prnq("@ Set default inizialided " + pach + "$ ");
        if (!noDefFile) return false;
        //файл конфигурации не задан
        noDefFile = false;
        pach = nmPr.concat(fileSeparator).concat(defNameFile).concat(getX());
        assert prnq("Set default inizialided " + pach + "$ ");
        return true;
    }//setPach
    @Override//--------занесение в справочник файлов---------------------------------
    public boolean include(Path path, EiPath dir){
        if (!String.valueOf(path).endsWith(name())) return true;//не тот тип файла
        if(Files.exists(path) && Files.isRegularFile(path)&& noDefFile){
            noDefFile =false;//предотвращение перезаписи
            pach= String.valueOf(path.toAbsolutePath());
            defNameFile= String.valueOf(path.toAbsolutePath());
//            assert prnq("Set inizialided "+defNameFile);
        }//if
        return false;
    }//includt
    //----------------------------------------------------------------------------
    public  boolean load(String nmPr){
        assert pach!=null: "! ext:INI> load: pach = null !";
        try(BufferedReader br=new BufferedReader(new FileReader(pach)))
        {   String s;
            while ((s = br.readLine()) != null) { lsfpr.add(s);}//дописываю проекты из файла
        } catch (IOException ex){System.out.println(ex.getMessage());return false;}
        assert prnq("файл инициализации загружен >"+pach);
        return true;
    }//load INI
    //---------------------------------------------------------------------------
    public boolean save(){//задается новый путь и имя файла
        assert pach!=null:"--ext:INI>saveAs pach==null";
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(pach)))
        {   for (String s: lsfpr ){ bw.write(s+"\n" ); }
            bw.flush();//очистка буффера
            prnq("Save file (INI): "+pach);
        } catch (IOException ex) {System.out.println(ex.getMessage());return false;}
        return true;
    }//saveAs INI
}, //список проектов
//==============================================================================
cfg(32,"Файл конфигурации проекта",true){
    private String defNameFile ="firstprj";//имя файла конфигурации проекта
    private boolean noDefFile =true; //файл конфигурации не задан
    @Override//----------------------------------------------------------------------
    public boolean definePach(String nmPr){
        assert nmPr!=null:"set pach=null";
        if (!noDefFile) return false;//файл конфигурации не задан
        pach = nmPr.concat(fileSeparator).concat(defNameFile).concat(getX());
        noDefFile = false;
        assert prnq("Set default Configuration " + pach + "$ ");
        lsfpr.add(String.valueOf(pach));//добавляю в спиок файлов локадьных проекта
        return true;
    }//setPach
    @Override//----------занесение в справочник файлов------------------------------
    public boolean include(Path path, EiPath dir){
        if (!String.valueOf(path).endsWith(name())) return true;//не тот тип файла
        if(Files.exists(path) && Files.isRegularFile(path)){
            lsfpr.add(String.valueOf(path));//добавляю в спиок файлов локадьных проекта
            for (EiFile e:lsFile) {//добавляю в список файлов
                if (e.getTitul().equals(path)) return false;}//есть такой
            lsFile.add(new EiFile(String.valueOf(path),dir));//добавляю элемент
//            assert prnq("File include to list "+path);
            if(noDefFile){//определяю текущий файл конфигурации
                noDefFile =false;//предотвращение перезаписи
                defNameFile= path.toString();//устанавливаю текущий проект
                pach= String.valueOf(path);
//                assert prnq("Set Configuration "+pach+"$ ");
            }//if
        }//if
        return false;
    }//includt
    @Override//--------------------------------------------------------------------
    public  boolean save(){
        this.backUp();
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(pach)))
        {   bw.write(this.getDs()+"\n" );//назначение файла
            GrRecords.TITUL.writPL(bw);//описание данного файла
            GrRecords.PATHS.writPL(bw);//список путей
            GrRecords.PATHR.writPL(bw);//вызываю запись *****************
            GrRecords.USERS.writPL(bw);//список пользователей
            GrRecords.USERI.writPL(bw);//вызываю запись
            GrRecords.ENDFL.writPL(bw);
            bw.flush();//очистка буффера
            System.out.println("File: "+ pach+" is Save");
        } catch (IOException ex){System.out.println(ex.getMessage());return false;}//catch
        return true;
    }//save CFG
}, //конфигурация проекта
//==============================================================================
mln(32,"Список входящих и исходящих писем",true),//=========================
crt(32,"Список карточек заказчика",false),//================================
rdc(32,"Список рабочей документации",false),//==============================
gui(32,"Общие категории",false),//==========================================
lui(32,"Локальные категории",false);
//==============================================================================
private final int     l;      //задание минимального размера файла данного типа
private final String  ds;     //описание типа файла
private final String  exst;   //суффикс
private final boolean b;      //данный внешний файл нужно анализировать
private     boolean modif;  //флаг модификации данных в одном из полей
protected   String  pach;   //путь c именем к локальному файлу
private     boolean present; //файл имеется в наличии
//-- конструкторы --------------------------------------------------------------
FileType(int l, String ds, boolean b){
    exst=".".concat(name());
    pach=null; present =false;
    this.l =l; this.ds=ds; this.b=b; this.modif=false;}//конструктор


    //--- общие методы--------------------------------------------------------------
//public void    setB(boolean b) { this.b = b; }
//public boolean isB(){return b;}
public boolean  isPresent() { return present; }//файл существует и доступен
public void     setPresent(boolean present) { this.present = present; }//файл существует и доступен
public void     setModif() { this.modif = true; }
public void     clrModif() { this.modif = false; }
public boolean  isModif() { return modif; }
public String   getX() {return exst; }
public String   getDs(){return ds;}//описание типа файла
public long     size(){return l;}//задание минимального размера файла данного типа
public boolean  definePach(String nmPr){
    assert nmPr!=null:"set pach=null";this.pach=nmPr; return false;}
public String   getPach(){return this.pach;}
public void     setPach(String pach) { this.pach = pach; }

/**
 * создаю резервную копию файла методом переименования и удаления пердыдущей версии
 * @return    */
public boolean backUp(){
    assert pach!=null: "! ext> backUp: pach = null !";
    try { if (!Files.exists(Paths.get(pach))) return true;//нет файла
            Files.move(Paths.get(pach),
                    Paths.get(pach.substring(0,pach.length()-1).concat("$")),
            StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) { e.printStackTrace(); return false;}
    return true;
}//backUp
//методы для перегрузки -----------------------------------------------------------
public  boolean load(String nmPr){ assert prnq("Absent Metod");return true;}

//занесение в справочник файлов---возвращает истина, если не тот тип файла-------
public boolean include(Path path, EiPath dir){
    assert prnq("Absent Metod");return false;}//----------------------------------

public  boolean save(){
    assert prnt("\nFileType:"+name()+">save (");
    assert pach!=null:"--FileType>save pach==null";
    this.backUp();
    try(BufferedWriter bw = new BufferedWriter(new FileWriter(pach)))
    {   bw.write(this.getDs()+"\n" );//назначение файла
        GrRecords.TITUL.writPL(bw);//описание данного файла
        for(GrRecords recrd: GrRecords.values())if(recrd.getTypFile().equals(name()))
            {recrd.writPL(bw);
                assert prnt("`"+recrd.name());
            }//for-if
        GrRecords.ENDFL.writPL(bw);//установка метки завершение записи
        prnq(" ) File: "+ pach+" is UpLoad");
    }catch(IOException ex){System.out.println(ex.getMessage());return false;}//catch
    return true;
}//saveAs----------------------------------------------------------------------------

}//enum FileType=====================================================================