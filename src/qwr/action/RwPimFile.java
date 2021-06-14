package qwr.action;
//инициализация объектов для работы с файлами примаверы
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import qwr.Inizial.InGuid;
import qwr.model.Base.ElGuid;
import qwr.model.Base.TrGuid;
import qwr.model.TPlan.ElmGr;
import qwr.model.TPlan.ElmPb;
import qwr.model.TPlan.ElmTs;
import qwr.model.reference.*;
import qwr.reports.ERcol;
import qwr.reports.Elcol;
import qwr.reports.ElTitl;
import qwr.reports.Stylq;
import qwr.util.BgFile;
import qwr.Inizial.IniPrim;
import qwr.Inizial.InTPlan;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

import static org.apache.poi.ss.usermodel.CellType.STRING;
import static qwr.util.BgFile.prnq;
import static qwr.util.BgFile.prnt;

public class RwPimFile {
    public static ArrayList<ElGuid> lstGl = new ArrayList<>(15);//главы
    public static ArrayList<TrGuid> lstBl = new ArrayList<>(25);//здания
    public static ArrayList<ElGuid> lstRz = new ArrayList<>(25);//разделы РД
    //создаю колекцию строк из документа экспорта примаверы и передачи на пополнение
    public static ArrayList<ElmTs> lstPr =new ArrayList<>(106);

    public static HashSet<String> lpipl= new HashSet<String>();//список исполнителей

    //создаю коллекцию строк исходного документа
    public static ArrayList<ElmPb> lstPb =new ArrayList<>(120);

    public static String getNumRd(StringBuilder s){
        int i = s.lastIndexOf(".");//последнее вхождение точки в наименование
        return s.substring(i+1);
    }//getNumRd
    //------------------------------------------------------
    public static void readFileExportPrimavera(){
       IniPrim.inErcol();//инициализация полей примаверы
       prnq("Читаю файл экспорта примаверы "+ BgFile.flin);
       try(FileInputStream file = new FileInputStream(new File(BgFile.flin)))
       {
           XSSFWorkbook wbook = new XSSFWorkbook(file);
           int sheeti=wbook.getNumberOfSheets();
           assert prnq("Количество листов "+sheeti);
           //смотрю второй лист
           XSSFSheet sheet = wbook.getSheetAt(sheeti-1);
           if(!sheet.getSheetName().equals("USERDATA")){
               prnq(" Имя листа "+sheet.getSheetName()+" не соответствует USERDATA");
           }
           String sec = sheet.getRow(2).getCell(0).getStringCellValue();
           if (sec.indexOf("CurrencyFormat=Рубль")<0){
               prnq(" ERROR Не соответствует формат стоимости. Изменить денежную единицу при экспорте");
//                System.exit(0);//завершение работы
           }
           //смотрю первый лист -----------------------------------------------
           sheet = wbook.getSheetAt(0);
           if(!sheet.getSheetName().equals("TASK")){
               prnq(" Имя листа "+sheet.getSheetName()+" не соответствует TASK");
               System.exit(4);//завершение работы
           }
           //массив для обработки экспорта примаверы
           int[] cRDS =new int[33];//массив соответствия колонки и данных в class ElmRDS
           //разбор первой строки
           Row rew=sheet.getRow(0);
//           assert prnq("63: Столбцов в файле "+rew.getLastCellNum()+"<~>"+cRDS.length);//номер правой ячейки начиная с 1
           if (rew.getLastCellNum()>cRDS.length){
               //превышение количество толбцов в файле размеру массива строк
               prnq("!!!!!  Превышение количество cтолбцов в файле размеру массива строк");
           }
           //просмотр шапки таблицы примаверы
           for (Cell cl: rew){//цикл по строке шапки таблицы
               for (ERcol el: IniPrim.elst){//перебор вариантов полей
                   if (cl.getStringCellValue().equalsIgnoreCase(el.getFname())){
                       el.setIcol(cl.getColumnIndex());
                       cRDS[cl.getColumnIndex()]=el.getElcl();//соответствие колонки данным
                       break;
                   }
               }//for elst
           }//for row
           //печать результатов просмотра шапки таблицы примаверы
           assert prnq("Содержимое шапки таблицы");
//           for (ERcol e: IniPrim.elst ) prnq("& "+e.getIcol()+" = "+e.getId()+" "+e.getSname()+" ~"+e.getElcl());
           prnq("\nКуда писать на основе поля ELCL списка колонок");
           for (Integer i: cRDS) prnt(" "+i+" ");
           prnq("\n--- Дамп распознования таблицы -----");
           // в коллекции tlst типа ElmRDS сформирован список данных для подстановки
           int nerr=0;//подсчет количества ошибок
           int nfor=0;//подсчет количества формул
           int nerx=0;//подсчет прочих распознований
           StringBuilder sb; //временная переменая
           lstPr.clear(); //очищаю массив
           for (Row row : sheet) {
               if (row.getRowNum()<2) continue; //попускаю первые строчки заголовка таблицы
               lstPr.add(new ElmTs());
               for (Cell cell : row) {
                   switch (cell.getCellType()) {
                       case ERROR: prnq("ERROR "); nerr++; break;
                       case STRING://строка
                           //делаю сохранение ячеек строк в lstPr типа ElmTs
//                           assert prnt("  "+cell.getColumnIndex()+"/"+cRDS[cell.getColumnIndex()]+" ");//просмотр ячеек
                           lstPr.get(lstPr.size()-1).setstr(cRDS[cell.getColumnIndex()],cell.getStringCellValue());
                           break; //=>lstPr типа ElmTs
                       default: assert prnt("@ "+cell.getColumnIndex()+" "); nerx++;
                   }//switch
               }//for cell
//               assert prnq(" ");
           }//for row
           file.close();
           prnq("\nКоличество ячееек с ошибкой "+nerr+" прочитано формул "+nfor+" прочих распознаваний "+nerx);
           prnq("Подкачено "+lstPr.size()+" строк");
       }
       catch (Exception e){ e.printStackTrace(); }
       finally {        }
    }//readFileExportPrimavera
    //------------------------------------------------------------------------------

    //==== Пополнения документа простого списка из Примаверы уровнями группировки ===
    public static void addGroupAndSorts() {//группировка и сортировка работ
        assert prnq("*** группировка и сортировка работ");
        //отсортированная колекция номеров РД с данными сортировки
        ArrayList<String> lstRd=new ArrayList<>(500);
        //создаю список документации с кодами сортировки
        for (ElmTs ets: lstPr) {//цикл по списку работ
            String s = ets.getRdm();
            if (!lstRd.contains(s)) lstRd.add(s);//если такого нет то добавляем
        }//цикл по списку работ
        assert prnq("Kол-во РД "+lstRd.size());
//        for (String s:lstRd) prnq("* "+s+" "+lstRd.indexOf(s));
        //промежуточная коллекция для пополнения документа уровнями группировки
        SortedMap<Long, ElmPb> srtPr = new TreeMap<>();
        int     x=0; //количество учтенных работ в списке
        String  bl;//индекс здания
        String  gl;//индекс главы
        String  rl;//индекс раздела
        long    a; //значение сортировки
        for (ElmTs ets: lstPr) {//цикл по списку работ
            a=0;
            int j=ets.getRd().indexOf(".");//позиция первой точки
            bl=ets.getRd().substring(0,j);
            //перебираю список зданий
            boolean tstBl=false;
            for (TrGuid ebl: lstBl) {//цикл по зданиям
                if (ebl.getAltn().equals(bl)){
                    if (ebl.isSolvd()) {//здание еще НЕ встречалось
                        gl=ebl.getBase();
                        boolean tsGl=false;
                        for (ElGuid g: lstGl){//цикл по главам
                            if (g.getTitul().equals(gl)){//найдена глава
                                ebl.setSrtb(g.getChang());
                                //вношу главу в список
                                srtPr.put((long) (g.getChang()*Math.pow(10L,8L)),
                                        new ElmGr(g,2));  //ElGuid=> ElmPb => ElmGr
                                tsGl=true;//есть такая глава
                                break;
                            }//найдена глава
                        }//цикл по главам
                        if (!tsGl) prnq("\nТакая глава не найдена <"+gl+"> ");
                        //вношу здание в список
                        a= (long) (ebl.getChang()*Math.pow(10L,6L));//порядковый номер здания
                        a+=ebl.getSrtb()*Math.pow(10L,8L);//порядковый номер главы
                        srtPr.put(a,new ElmGr(ebl,3));//записал здание
                    }//здание еще НЕ встречалось
                    tstBl=true;//есть такое здание
                    break;
                }//if здание найдено
            }//цикл по зданиям lstBl
            if (!tstBl) prnq("\nТакое здание не найдено <"+bl+"> ");
            //просматриваю раздел РД
            int k=ets.getRd().indexOf(".",j+1);//позиция второй точки
            //int k=ets.getRd().lastIndexOf(".");//позиция второй точки
            if (j<k) {//есть вторая точка, есть раздел
                rl = ets.getRd().substring(j + 1, k);
                boolean tstrz=false;
                for (ElGuid erz: lstRz){//цикл по разделам
                    if (erz.getTitul().equals(rl)){//есть такой раздел
                        tstrz=true;
                        a+=erz.getChang()*Math.pow(10L,4L);//порядковый номер главы
                        srtPr.put(a,new ElmGr(erz,4));//записал раздел
                    }
                }//цикл по разделам lstRz
                if (!tstrz) prnq("\nТакой раздел не найден <"+rl+"> ");
            }//есть вторая точка, есть раздел
            //работаю по самой документации
            rl=ets.getRdm();
            boolean tstRd=false;
            for (String erd: lstRd){//цикл по РД
                if (erd.equals(rl)){//есть такое РД
                    a+=(lstRd.indexOf(erd)+1)*Math.pow(10L,2L)+x+1;//порядковый номер РД
                    srtPr.put(a,new ElmTs(ets,5));//записал раздел
                    x++;
                    tstRd=true;
                    break;
                }//есть такое РД
            }//цикл по РД
            if (!tstRd) prnq("\nТакого РД не найдена <"+rl+"> ");
        }//цикл по списку работ lstPr
        assert prnq("Количество подгруженных работ "+x+" ");
        //печатаю полученный список
        prnq("   key     Tst Grp Idr");
//        for (Long key: srtPr.keySet() )prnq(((srtPr.get(key) instanceof ElmTs)?"=":"^")+key+"-"+srtPr.get(key).getTst()+"~"+srtPr.get(key).getGrp()+"="+srtPr.get(key).getIdr());

        //создаю строку итогов первой
        lstPb.add(new ElmGr("ИТОГО","ИТОГО по всем разделам",1));
        //создаю простую коллекцию на основе сортированного списка
        for (Long key: srtPr.keySet() ) lstPb.add(srtPr.get(key));
        int i=0;//выполняю нумерацию работ
        for (ElmPb e:lstPb) if (e instanceof ElmTs) e.setNur(++i);
    }//sortstr
    //=============================================================================
    public static void SetFormSum(){
        //формирую массив уровней исходя что мне о них известны
        SortedSet<Integer> ulst =new TreeSet<>();
        for (ElmPb ep: lstPb) ulst.add(ep.getTst()); //цикл по строкам работ
        Object[] uarr = ulst.toArray();
        //проверяю корректность уровней
        int a=0; //указатель уровня в массиве
        int c;   //значение уровня в колекции строк
        int b;   //значение уровня в массиве уровней
        for ( int i=1; i<lstPb.size(); i++){//цикл по строкам работ
            c= lstPb.get(i).getTst();//значение уровня в колекции строк
            if (c<0) {prnq("Отрицательный уровень!");break;}//
            b= (int) uarr[a];//значение уровня в массиве уровней
//            assert prnt("\n "+i+") "+c+" ! "+b+" ");
            if (b==c) continue;//уровень не изменился
            if (b<c){//спускаюсь на следующий уровень
//                assert prnt("+");
                a++;
                b= (int) uarr[a];
                if (b!=c){
                    prnq("Нарушена последовательность уровней в строке "+i);
                    lstPb.get(i).setTst(b);//корректирую уровень
                    a--;//возвращаюсь наверх
                }//if (b!=c)
            } else {//поднимаюсь на верх
                do { a--; b= (int) uarr[a];
//                    assert prnt("-"+b+" ");
                } while ( c!=b );
            }//if (b<c)
        }//цикл по строкам работ
        assert prnq("\nКорректировка уровней завершена.");
        //печатаб список уровней
        for (Object i:uarr) prnt(" "+i); prnq("z");
        //ищу сумму глав
        StringBuilder sgl = new StringBuilder();
        int m=0;//количество суммируемых элементов
        for (int i = 1; i< lstPb.size(); i++){//цикл по строкам работ ***********0>1
 //           lstPb.get(i).setNur(i); //пишу номер строки в выборке
//            assert prnq("* "+lstPb.get(i).prn());
            if (lstPb.get(i).getTst()==(int)uarr[1]) { sgl.append(i).append('%');m++;}
        }//цикл по строкам работ
        //записываю формулу в нулевую строку
        prnq("Главы: "+sgl);
        //второй проход по зданиям
        StringBuilder sbl = new StringBuilder(89);
        int j=0;//ячейка куда пишем
        int k=0;//количество суммируемых элементов
        //перебор уровней в цикле
        int up=(int)uarr[1];//уровень группировки
        for (Object u:uarr) {//цикл по уровням u=текущий уровень
            if ((int)u<=up) continue;//пропускаю првый элемент
            assert prnt(u+"~ ");//печатаю прохождение уровней группироки
            for ( int i=0; i<lstPb.size(); i++){//цикл по строкам работ ************** 0>1
                if (lstPb.get(i).getTst()==up ){//текущий уровень равен начальному
                    if (j<i) {lstPb.get(j).setform(sbl,k);  sbl.delete(0,sbl.length()); k=0;}
                    j=i;
                }//текущий уровень равен начальному
                if (lstPb.get(i).getTst()==(int)u ){ sbl.append(i).append('%'); k++; }
            }//цикл по строкам работ
            if (j<lstPb.size()) {//записываю формулу после окончания цикла по работам
                lstPb.get(j).setform(sbl,k); sbl.delete(0,sbl.length()); k=0;}
            up=(int)u;
        }//цикл по уровням
        lstPb.get(0).setform(sgl,m);//записываю формулу в нулевую строку
        assert prnq("\nДобавлены формулы суммирования в группирующие уровни.");
    }//SetFormSum
    //------------------------------------------------------------------------------------
    public static void generXlsx(){
        //------------------ формирую темплан ----------------------
        XSSFWorkbook wbook = new XSSFWorkbook();//создаю новую книгу
        //создаю массив стилей
        XSSFCellStyle[] slt=new XSSFCellStyle[Stylq.cstl];
        for (int i=0; i<Stylq.cstl; i++) slt[i]=Stylq.creatStyle(wbook,i);
        XSSFSheet sheet = wbook.createSheet("TempPlan");//создаю лист
        Elcol.setSheet(sheet);//инициализация ссылки
        InTPlan.iniColReportTemplan();//инициализирую колонки выходного документа
        for (Elcol i: InTPlan.wtu) sheet.setColumnWidth(i.getNcol(),i.getWid());//ширина колонок
        int rowi=0;//счетчик строк
        XSSFRow rowx = sheet.createRow((short)rowi++);//создаем строку
        rowx.setHeight((short) 885);//высота строки
        Elcol.creatCell(slt[10],rowi-1,0,4,"УТВЕРЖДАЮ");
        Elcol.creatCell(slt[10],rowi-1,11,15,"СОГЛАСОВАНО");
        rowx = sheet.createRow((short)rowi++);//создаем строку
        Elcol.creatCell(slt[11],rowi-1,0,4,"Зам.генерального директора АО \"СХК\" по проекту \"Прорыв\" - руководитель проекта строительства ОДЭК  ");
        Elcol.creatCell(slt[11],rowi-1,11,15,"Директор ООО \"УС БАЭС\" ");
        rowx = sheet.createRow((short)rowi++);//создаем строку
        Elcol.creatCell(slt[11],rowi-1,0,4,"__________ А.В. Гусев \n" +
                "«    » _______________ 2020г.");
        Elcol.creatCell(slt[11],rowi-1,11,15,"_______________ Н.Г. Пешнина\n" +
                "«    » _______________ 2020 г.");
        rowx = sheet.createRow((short)rowi++);//создаем строку
        Elcol.creatCell(slt[10],rowi-1,1,15,"Тематический план ");
        rowx = sheet.createRow((short)rowi++);//создаем строку
        Elcol.creatCell(slt[11],rowi-1,1,15,"на выполнение СМР в 4 квартале 2020г. (период с 16 октября 2020 г. по 15 ноября 2020 г.)");
        Elcol.creatCell(slt[7],rowi-1,17,15,"К пересчета в текущие");
        rowx = sheet.createRow((short)rowi++);//создаем строку
        Elcol.creatCell(slt[11],rowi-1,1,15,"по договору на сооружение объектов производственного назначения \"ОАО «СХК» Строительство модуля фабрикации и пускового комплекса рефабрикации плотного смешанного уранплутониевого топлива для реакторов на быстрых нейтронах\"");
        rowx.createCell(17).setCellValue(14);//задаю коэффициент пересчета
        rowx = sheet.createRow((short)rowi++);//создаем строку
        rowx = sheet.createRow((short)rowi++);//создаем строку
//        Elcol.rowi=rowi;
        prnq("rowi="+rowi);

        for (Elcol e: InTPlan.wtu) e.creatCellTitl(slt[2]);//создаю заголовок таблицы

        Elcol.creatCellTitl(slt[2],6,7,9,"Количество физ. объемов работ");
        Elcol.creatCellTitl(slt[2],6,10,11,"Сметная стоимость работ в базовых ценах ");
        Elcol.creatCellTitl(slt[2],6,13,15,"Запланировано");
        Elcol.rwst=rowi;//обозначаю начало таблицы данных для формирования смещения
        //формирую таблицу данных
        for (ElmPb itn : lstPb){
            if (itn.getTst()<0) continue;//отрицательный отступ и пропускаю данную строку
            rowx = sheet.createRow((short)rowi);//создаем строку
            if (itn instanceof ElmTs){//это элемент работы, а не группирующего уровня
                for (Elcol i: InTPlan.wtu) {
                    if (i.printCellFormul(slt[i.getStl()],rowi)) {//пишем формулу или идем дальше
                        if (itn.getVal(i.getElcl()) instanceof String) {//является ли объект строкой
                            rowx.createCell(i.getNcol()).setCellValue((String) itn.getVal(i.getElcl()));
                            rowx.getCell(i.getNcol()).setCellStyle(slt[i.getStl()]);
                        } else if (itn.getVal(i.getElcl()) instanceof Integer) {//является ли объект числом
                            rowx.createCell(i.getNcol()).setCellValue((Integer) itn.getVal(i.getElcl()));
                            rowx.getCell(i.getNcol()).setCellStyle(slt[i.getStl()]);
                        } else if (itn.getVal(i.getElcl()) instanceof Double) {//является ли объект числом
                            rowx.createCell(i.getNcol()).setCellValue((Double) itn.getVal(i.getElcl()));
                            rowx.getCell(i.getNcol()).setCellStyle(slt[i.getStl()]);
                        }
                    }//if forlula
                }//for wtu
//            Stylq.addFormlN(slt[5],rowi,10,"SUM(L"+(rowi+1)+":U"+(rowi+1)+")");//добавляю формулу за пределы строки
            } else {//это элемент группирующего уровня, а не работы
                if (itn instanceof ElmTs){//это элемент работы, а не группирующего уровня
                    Elcol.creatCellTitl(slt[3], rowi, 1, 6, itn.getIdr());
                } else {
                    Elcol.creatCellTitl(slt[3], rowi, 1, 6, itn.getName());
                }
                //проверяю и подставляю формулы вертикального суммирования
                for (Elcol i: InTPlan.wtu) { i.printCellFormulSum(slt[12],rowi,itn.getForm()); }//for Elcol
            }//if
            rowi++;
        }//for elst (row)
        prnq("@"+rowx.getCell(1).getReference());
        BgFile.savefl(wbook);//сохранение результата работы
        prnq("--------------------------------");
    }//generXlsx
    //------------------------------------------------------------------------------
    public static void readFlileTx(){
        try(FileInputStream file = new FileInputStream(new File(BgFile.flin)))
        {
            prnq("Исходные данные из файла копии экрана примаверы "+BgFile.flin);
            XSSFWorkbook wbook = new XSSFWorkbook(file);
            int sheeti=wbook.getNumberOfSheets();
            assert prnq("Количество листов "+sheeti);
            //смотрю лист
            XSSFSheet sheet = wbook.getSheetAt(0);
            InTPlan.iniColInputFileTxt();//список распознавания колонок исходника
            //ищу описание колонок=шапку
            //ищу описание колонок=шапку
            int maxcol=23;//максимальная ширина таблицы
            //создаю массив соответствия колонки документа и данных в  class ElmRDS
            int[] cRDS =new int[maxcol+1];

            int brow=-1;//превая строка данных определяется после шапки таблицы
            for (Row row : sheet) {//цикл по строкам в поисках шапки таблицы
                if (row.getLastCellNum()>maxcol){//количество столбцов указано в размере массива Iniz.cRDS
                    prnq("Количество столбцов исходного документа превышает 32");
                    prnq("Выполнение программы прервано!");
                    System.exit(8);
                }
                for (Cell cell : row) {
                    for (ERcol el: InTPlan.clin) {//перебор вариантов полей
                        if (cell.getStringCellValue().equalsIgnoreCase(el.getFname())){
                            el.setIcol(cell.getColumnIndex());//записываю находку
                            cRDS[cell.getColumnIndex()]=el.getElcl();//соответствие колонки данным
                            break;
                        }//if
                    }//for el
                }//for cell
                if (InTPlan.clin.get(0).getIcol()>=0) { brow=row.getRowNum()+1; break;}//шапка найдена
            }//цикл по строкам в поисках шапки таблицы
            assert prnq("Начало чтения данных со строки "+brow);
            for (ERcol el: InTPlan.clin) {assert prnq(" "+el.getIcol()+" = ("+el.getFname()+")");}
            //проверяю первый столбец на наличие кода работы при инициализации колонок
            if (InTPlan.clin.get(0).getIcol()!=0 ){
                prnq(" В первой строку нет идентификатора работы. Дальнейшее распознование не возможно!");
                System.exit(9);
            }
            // читаю данные из исходного файла
            ElmTs q=new ElmTs();// элемент колекции для подготовки ввода
            int nerr=0;//подсчет количества ошибок
            int nerx=0;//подсчет прочих распознований
            int celi=0;//индекс ячейки
            //создаю строку итогов первой
            lstPb.add(new ElmGr("ИТОГО","ИТОГО по всем разделам",1));
            for (Row row : sheet) {//цикл по строкам исходного документа
                if (row.getRowNum()<brow) continue;//пропускаю строки шапки
                if (row.getRowNum()>12000){
                    prnq("\n Превышение количества строк  "+ sheet.getLastRowNum()); break;}
                q.clear();//предварительная очистка элемента
                String w=row.getCell(0).getStringCellValue();
                if (w.isBlank()){
                    prnq("\n Найдена строка с пустым значением в первой колонке"); break; }
//                assert prnt("+++"+row.getRowNum()+"  "+w);
                q.setTst(w.length()+2 -w.trim().length());//записываю смещение в первом столбце
                for (Cell cell : row) {//цикл по ячейкам строки
                    //пересчитываб индекс ячейки в элемент данных через Iniz.cRDS
                    celi= cRDS[cell.getColumnIndex()];//индекс ячейки
                    switch (cell.getCellType()) {
                        case ERROR: prnt("R "); nerr++; break;
                        case NUMERIC://число
                            q.setdbl(celi, cell.getNumericCellValue());
//                            assert prnt("\n "+celi+"n"+cell.getNumericCellValue()+" ");
                            break;
                        case STRING://строка
                            q.setstr(celi, cell.getStringCellValue());
//                            assert prnt("\n "+celi+"s"+cell.getStringCellValue()+" ");
                            break;
                        default: assert prnt("@ "+cell.getColumnIndex()+" "); nerx++;
                    }//switch
                }//for cell
//                assert prnq("");
                //определяю является считанная строка групирующим уровнем или данными
                if (q.isGroup()) { lstPb.add(new ElmGr(q)); }
                            else { lstPb.add(new ElmTs(q)); }
            }//цикл по строкам исходного документа
        }//чтение файла исходного документа
        catch (Exception e){ e.printStackTrace(); }
        finally {        }
        //выполняю нумерацию работ
        int i=0;
        for (ElmPb e:lstPb) if (e instanceof ElmTs) e.setNur(++i);
        }//readFlileTx
    //----------------------------------------------------------------------------------
    public static void readFlileXPlan() {
//        ElmTs q=new ElmTs();// элемент колекции для подготовки ввода
        short rowi=0;//счетчик строк
//        int celi=0;//индекс ячейки
        try(FileInputStream file = new FileInputStream(new File(BgFile.flin)))
        {
            XSSFWorkbook wbook = new XSSFWorkbook(file);
            int sheeti=wbook.getNumberOfSheets();
//            assert prnq("Количество листов "+sheeti);
            //смотрю лист
            XSSFSheet sheet = wbook.getSheetAt(0);
            InTPlan.iniColreadFlileXPlan();//список распознавания колонок исходника
            //ищу описание колонок=шапку
            int maxcol=23;//максимальная ширина таблицы
            //создаю массив соответствия колонки документа и данных в  class ElmRDS
            int[] cRDS =new int[maxcol+1];
            int brow=-1;//превая строка данных определяется после шапки таблицы
            int deltCol=maxcol+1;//фактическое смещение таблицы по горизонтали
            assert prnq("Просматриваю таблицу в поисках шапки ");
            label:
            for (Row row : sheet) {//цикл по строкам в поисках шапки таблицы
//                assert prnt("\n"+row.getRowNum()+" ");
                for (Cell cell : row) {//цикл по ячейкам строки
                    if (cell.getColumnIndex() > maxcol ) { assert prnt(" @"); break;}
//                    prnt(" "+cell.getColumnIndex());
                    //ищу только одно значение
                    if (cell.getCellType()!=STRING) continue;//если не строка пропускаю
                    int i=0;
                    for (ERcol el: InTPlan.clin) {//перебор вариантов полей
                        if (cell.getStringCellValue().equalsIgnoreCase(el.getFname())){

                            brow=row.getRowNum()+1;
                            el.setIcol(cell.getColumnIndex());//записываю находку
//                            deltCol=cell.getColumnIndex()-el.getId();
                            deltCol=cell.getColumnIndex()-i;
                            prnq(" ="+brow+" >"+cell.getColumnIndex()+" "+deltCol);
                            break  label;//полное завершение анализа шапки таблицы
                        }//поле найдено
                        i++;
                    }//перебор вариантов полей
                }//цикл по ячейкам строки
            }//цикл по строкам в поисках шапки таблицы
            //формирую массив соответствия
            for (int i = 0; i< InTPlan.clin.size(); i++){ cRDS[i]= InTPlan.clin.get(i).getElcl(); }

 //           for (ERcol el: Iniz.clin) { cRDS[el.getId()]=el.getElcl(); }//перебор вариантов полей
            prnt("В какие поля пишем столбцы: ");
            for (int i:cRDS) prnt("~ "+i);
            prnq("");
//            System.exit(12);
                // читаю данные из исходного файла
            ElmTs q=new ElmTs();// элемент колекции для подготовки ввода
            int nerr=0;//подсчет количества ошибок
            int nerx=0;//подсчет прочих распознований
            int plaze=0;//индекс ячейки
            //создаю строку итогов первой
            lstPb.add(new ElmGr("ИТОГО","ИТОГО по всем разделам",1));
            for (Row row : sheet) {//цикл по строкам исходного документа
                if (row.getRowNum() < brow+4) continue;//пропускаю строки шапки
                if (row.getRowNum() >165 ) { prnq("\n STOP Hendel"); break;}
//                prnt("\n>>"+(row.getRowNum()+1)+"  ");
                q.clear();//предварительная очистка элемента
                boolean tstrow=false;//котроль строки на подкачку по первому столбцу
                for (Cell cell : row) {//цикл по ячейкам строки
                    if (cell.getColumnIndex() < deltCol) continue;//пропускаю смещение ячейки
                    if (cell.getColumnIndex() >= maxcol) continue;//игнорирую дальние ячейки
                    plaze= cRDS[cell.getColumnIndex()-deltCol];//индекс ячейки
//                    prnt(" "+cell.getColumnIndex()+"/"+plaze);
                    switch (cell.getCellType()) {
                        case ERROR: prnt("R "); nerr++; break;
                        case BLANK: assert prnt("b "); break;
                        case _NONE: assert prnt("o "); break;
                        case NUMERIC://число
                            if (!tstrow){ q.setTst(12);}
                            q.setdbl(plaze, cell.getNumericCellValue());
                            tstrow=true;//есть обнаружение
                            assert prnt("n ");
                            break;
                        case STRING://строка
                            //смотрю смешение в первом столбце данных
                            if (!tstrow && cell.getColumnIndex() == deltCol){
                                q.setTst(cell.getStringCellValue().length()
                                        -cell.getStringCellValue().trim().length());
                                tstrow=true;//есть обнаружение
                            }
                            assert prnt("s ");
                            q.setstr(plaze, cell.getStringCellValue());
                            break;
                        case FORMULA:
                            if (!tstrow){ q.setTst(12);}
                            tstrow=true;//есть обнаружение
                            switch (cell.getCachedFormulaResultType()){
                                case NUMERIC:
                                    assert prnt("N ");
                                    q.setdbl(plaze, cell.getNumericCellValue());
                                    break;
                                case STRING: assert prnt("S ");break;
                                case ERROR:   assert prnt("E ");break;
                                case BLANK:   assert prnt("B ");break;
                                case _NONE:   assert prnt("O ");break;
                            }
                            break;//завершение анализа формулы
                        default: assert prnt("Ё "+cell.getColumnIndex()+" "); nerx++;
                    }//switch cell
                    if (!tstrow && (cell.getColumnIndex() == deltCol)) break;//первая строка пуста то дальше не смотрим
                }//цикл по ячейкам строки
                if (tstrow) {
                    prnq("+++"+(row.getRowNum()+1));
                    if (q.isGroup()) { lstPb.add(new ElmGr(q)); }
                    else { lstPb.add(new ElmTs(q)); }
                }//проверяю пропустить или записать строку
            }//цикл по строкам исходного документа
        }//чтение файла исходного документа
        catch (Exception e){ e.printStackTrace(); }
        finally {        }
        prnq("\n+++ Чтение исходного документа (темплана) завершено ***");
    }//readFlileXPlan
    //-------------------------------------------------------------------------
    public static void printReportMount() {
        XSSFWorkbook wbook = new XSSFWorkbook();
        //создаю массив стилей
        XSSFCellStyle[] slt=new XSSFCellStyle[Stylq.cstl];
        for (int i=0; i<Stylq.cstl; i++) slt[i]=Stylq.creatStyle(wbook,i);
        XSSFSheet sheet = wbook.createSheet("monthly_statement");//создаю лист
        Elcol.setSheet(sheet);//инициализация ссылки
        ElTitl.setSheet(sheet);
        InTPlan.iniPrintReportMount();//инициализирую колонки выходного документа wtum
        int rowi=0;//счетчик строк
        InTPlan.iniTitlReportMount();//инициализирую шапку документа
        for (ElTitl e: InTPlan.lsTitlRepMnt) e.mapping();
        for (Elcol e: InTPlan.wtum) sheet.setColumnWidth(e.getNcol(),e.getWid());//ширина колонок
        rowi=ElTitl.getRowL();
        XSSFRow rowx = sheet.createRow((short)++rowi);//создаем строку
        ElTitl.prn("L574");
        for (Elcol e: InTPlan.wtum) e.creatCellTitl(slt[2]);//создаю заголовок таблицы
        rowi++;
        String formulaq;
        //-------------------------------------------------------------------
        for (ElmPb itn : lstPb){
            if (itn.getTst()<0) continue;//отрицательный отступ и пропускаю данную строку
            rowx = sheet.createRow(rowi);//создаем строку
            if (itn instanceof ElmTs) {
                for (Elcol i: InTPlan.wtum) {
                    if(itn.getVal(i.getElcl()) instanceof String){//является ли объект строкой
                        rowx.createCell(i.getNcol()).setCellValue((String)itn.getVal(i.getElcl()));
                        rowx.getCell(i.getNcol()).setCellStyle(slt[i.getStl()]);
                    }
                    else if(itn.getVal(i.getElcl()) instanceof Integer){//является ли объект числом
                        rowx.createCell(i.getNcol()).setCellValue((Integer)itn.getVal(i.getElcl()));
                        rowx.getCell(i.getNcol()).setCellStyle(slt[i.getStl()]);
                    }
                    else if(itn.getVal(i.getElcl()) instanceof Double){//является ли объект числом
                        rowx.createCell(i.getNcol()).setCellValue((Double)itn.getVal(i.getElcl()));
                        rowx.getCell(i.getNcol()).setCellStyle(slt[i.getStl()]);
                    }
                }//for wtu
                //добавляю формулу за пределы строки
                formulaq="SUM(L"+(rowi+1)+":U"+(rowi+1)+")/1000";
                rowx.createCell(10).setCellFormula(formulaq);
                rowx.getCell(10).setCellStyle(slt[6]);
//                Stylq.addFormlN(slt[5],rowi,10,formulaq);
            } else {
                  Elcol.creatCellTitl(slt[3], rowi, 1, 6, itn.getName());
            }//if
            rowi++;
        }//for elst (row)
        ElTitl.prn("L609");
        BgFile.savefl(wbook, "ReportMonthly.xlsx");//сохранение результата работы
        prnq("--------------------------------");
    }//printReportMount
    //--------------------------------------------------------------------------
    public static void printReportDay() {
        definListPerfomens();//определение списка исполнителей
        calculAmountOfCost();//расчет количества из стоимости
        //---------------------------------------------------
        XSSFWorkbook wbook = new XSSFWorkbook();
        XSSFCellStyle[] slt=new XSSFCellStyle[Stylq.cstl];
        for (int i=0; i<Stylq.cstl; i++) slt[i]=Stylq.creatStyle(wbook,i);
        XSSFSheet sheet = wbook.createSheet("Dently_statement");//создаю лист
        Elcol.setSheet(sheet);//инициализация ссылки
        ElTitl.setSheet(sheet);
        InTPlan.iniPrintReportDay();//инициализирую колонки выходного документа wtum
        int rowi=0;//счетчик строк
        InTPlan.iniTitlReportDay();//инициализирую шапку документа
        for (ElTitl e: InTPlan.lsTitlRepDay) e.mapping(slt[2]);

        XSSFRow rowx;
        for (Elcol e: InTPlan.lecol) e.creatCellTitl(slt[2]);//создаю заголовок таблицы
        rowi=ElTitl.getRowL()+1;
        ElTitl.prn("L636** ");
        Elcol.setPointOffcet(1);
        //формирую таблицу данных
        for (ElmPb itn : lstPb){
            if (itn.getTst()<0) continue;//отрицательный отступ и пропускаю данную строку
            rowx = sheet.createRow((short)rowi);//создаем строку
            if (itn instanceof ElmTs){//это элемент работы, а не группирующего уровня
                for (Elcol i: InTPlan.lecol) {
                    if (i.printCellFormul(slt[i.getStl()],rowi)) {//пишем формулу или идем дальше
                        if (itn.getVal(i.getElcl()) instanceof String) {//является ли объект строкой
                            rowx.createCell(i.getNcol()).setCellValue((String) itn.getVal(i.getElcl()));
                            rowx.getCell(i.getNcol()).setCellStyle(slt[i.getStl()]);
                        } else if (itn.getVal(i.getElcl()) instanceof Integer) {//является ли объект числом
                            rowx.createCell(i.getNcol()).setCellValue((Integer) itn.getVal(i.getElcl()));
                            rowx.getCell(i.getNcol()).setCellStyle(slt[i.getStl()]);
                        } else if (itn.getVal(i.getElcl()) instanceof Double) {//является ли объект числом
                            rowx.createCell(i.getNcol()).setCellValue((Double) itn.getVal(i.getElcl()));
                            rowx.getCell(i.getNcol()).setCellStyle(slt[i.getStl()]);
                        }
                    }//if forlula
                }//for wtu
            } else {//это элемент группирующего уровня, а не работы
                Elcol.creatCellTitl(slt[3], rowi, 1, 5, itn.getName());
                //проверяю и подставляю формулы вертикального суммирования
                for (Elcol i: InTPlan.lecol) { i.printCellFormulSum(slt[12],rowi,itn.getForm()); }//for Elcol
            }//if
            rowi++;
        }//for elst (row)
        //формирование нижней части таблицы
        InTPlan.iniPrintReportDayDwn();
        for (Elcol e: InTPlan.lecod) e.creatHeaderTable(slt[2],rowi+2);//создаю заголовок таблицы
        //печатаю тело таблицы
        rowi=ElTitl.getRowL()+1;
        int rowend = (rowi++)-2;
        for (String s: lpipl) {//цикл по списоку исполнителей
            rowx = sheet.createRow(rowi++);//создаем строку
            rowx.createCell(1).setCellValue(s);
            rowx.getCell(1).setCellStyle(slt[7]);

            rowx.createCell(2).setCellValue(s);
            rowx.getCell(2).setCellStyle(slt[4]);

            ElTitl.printCellFormula(slt[5],rowi-1,0,"COUNTIF(B$10:B$"+rowend+",B"+rowi+")");
            ElTitl.printCellFormula(slt[5],rowi-1,3,"SUMIF(B$10:B$"+rowend+",B"+rowi+",L$10:L$"+rowend+")");
            ElTitl.printCellFormula(slt[5],rowi-1,4,"SUMIF(B$10:B$"+rowend+",B"+rowi+",O$10:O$"+rowend+")");
            ElTitl.printCellFormula(slt[5],rowi-1,5,"SUMIF(B$10:B$"+rowend+",B"+rowi+",P$10:P$"+rowend+")");
            ElTitl.printCellFormula(slt[5],rowi-1,6,"F"+rowi+"-E"+rowi);
//            String f="COUNTIF(B$10:B$"+rowend+",B"+rowi+")";//ВНИМАНИЕ вместо ";" нужно писать ","
        }//цикл по списоку исполнителей
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        ElTitl.prn("L687");
        BgFile.savefl(wbook, "ReportEveryWeek.xlsx");//сохранение результата работы
        prnq("--------------------------------");
    }//printReportDay
//#################################################################################
    public static void calculAmountOfCost(){//расчет количества из стоимости
        prnq("Перерасчет количества на основе стоимости");
        for (ElmPb e : lstPb){
            if (e instanceof ElmGr) continue;//группировку пропускаю

            e.setKost(e.getKsm()*e.getCst()/e.getCsm());
            e.setKpl(e.getKsm()*e.getCpl()/e.getCsm()+0.000000001);
//            e.setKost(12);
//            e.setKpl(55);
//            prnq(" "+e.prn());
        }//for
    }//calculAmountOfCost
    //--------------------------------------------------------------------------
    public static void definListPerfomens(){//определение списка исполнителей
        assert prnq(" *2* формирования перечня исполнителей* ");
//        HashSet<String> lpipl= new HashSet<String>();
        for (ElmPb itn : lstPb){
            if (itn instanceof ElmTs) lpipl.add(itn.getPipl()); }
//        for (String s: lpipl) prnq(" "+s);  prnq("****");
    }//definListPerfomens


    /**
     *
     */
    public static void beg(){
        assert prnt("$ class InGuid beg 61 $");
//        if (tstini){ prnq("Seconds Call IniGl:beg"); return; }tstini=true; //контрольодного прохода
        //инициализация групп органзаций
        //инициализация глав
        lstGl.add(new ElGuid("1","Глава 1. Подготовка территории."));
        lstGl.add(new ElGuid("2","Глава 2. Основные объекты строительства"));
        lstGl.add(new ElGuid("3","Глава 3. Объекты подсобного и обслуживающего назначения"));
        lstGl.add(new ElGuid("4","Глава 4. Объекты энергетического хозяйства"));
        lstGl.add(new ElGuid("5","Глава 5. Объекты транспортного хозяйства и связи"));
        lstGl.add(new ElGuid("6","Глава 6. Наружные сети и сооружения водоснабжения, водоотведения, теплоснабжения и газоснабжения"));
        lstGl.add(new ElGuid("7","Глава 7.Благоустройство и озеленение территории"));
        lstGl.add(new ElGuid("8","Глава 8. Временные здания и сооружения"));
        lstGl.add(new ElGuid("9","Глава 9. Прочие работы и затраты"));
        lstGl.add(new ElGuid("12","Глава 12 Проектные и изыскательские работы"));
        lstGl.add(new ElGuid("13","Глава 13 Подготовительный период"));
        //        lstGl.add(new ElGuid("",""));
        TrGuid.clear();
        //инициализация зданий
        lstBl.add(new TrGuid("Зд4","4","2","Здание 4-здание МФР"));
        lstBl.add(new TrGuid("Зд4пр","4пр","2","Здание 4-здание МФР"));
        lstBl.add(new TrGuid("Зд4А","4A","3","Здание 4А-здание переработки САО и НЛО"));
        lstBl.add(new TrGuid("Зд5","5","3","Здание 5-временное хранилище кондиционированных САО, НАО и ОНАО"));
        lstBl.add(new TrGuid("Зд16","16","3","Здание 16-адмннистративно-бытовой комплекс (с ЗПУ ПД АС) с людским контрольно-пропускным пунктом"));
        lstBl.add(new TrGuid("Зд22","22","3","Здание 22 - здание санпропускника"));
        lstBl.add(new TrGuid("Ср30","30","3","Сооружение 30 - сооружение ГО (А- II1-600-2002), резервуары запаса воды"));
        lstBl.add(new TrGuid("Зд33","33","3","Здание 33 - центральный материальный склад и склад химреагентов"));
        lstBl.add(new TrGuid("Ср5/4А","5/4А","3","Сооружения 5/4А, - пешеходно-технологические галереи"));
        lstBl.add(new TrGuid("Ср64/22/4","64/22+22/4","3","Сооружения  64/22, 22/4, - пешеходно-технологические галереи"));
        lstBl.add(new TrGuid("Ср22/16","22/16","3","Сооружения 22/16 - пешеходно-технологические галереи"));
        lstBl.add(new TrGuid("Ср41","41","3","Сооружение 41 - метеорологическая площадка"));
        lstBl.add(new TrGuid("НаблСкваж","НбСк","3","Наблидательные скважины по периметру зданий 4,4А и 5"));
        lstBl.add(new TrGuid("Ср7А","7А","4","Сооружение 7 А - дизельная электростанция с вынесенным топливо хранилищем"));
        lstBl.add(new TrGuid("Ср63","63","4","Сооружение 63 — КРУ-6 кВ"));
        lstBl.add(new TrGuid("Ср15Б","15Б","4","Сооружение 15Б - блочно-модульные трансформаторная подстанция"));
        lstBl.add(new TrGuid("Ср15В","15В","","Сооружение 15В - блочно модульная трансформаторная подстанция"));
        lstBl.add(new TrGuid("Ср15А","15А","6","Здание 15А - объединенная насосная станция хозпитьевого, производственного и противопож.водоснабжен."));
        lstBl.add(new TrGuid("Ср65","65","6","Сооружение 65 - блочно-модульные трансформаторная подстанция"));
//        lstBl.add(new TrGuid("НрСетиЭлек","","4","Наружные сети электроснабжения"));
        lstBl.add(new TrGuid("Ср31","31","5","Сооружение 31 - пункт дезактивации вагонов"));
        lstBl.add(new TrGuid("КПП32","32","5","Людской контрольно-пропускной пункт на городской зоне"));
        lstBl.add(new TrGuid("Ср32А","32А","5","Сооружения 32А - автотранспортные КПП"));
        lstBl.add(new TrGuid("Ср32Б","32Б","5","Сооружения 32Б - автотранспортные КПП"));
        lstBl.add(new TrGuid("Ср32В","32В","5","Сооружения 32В - автотранспортные КПП"));
        lstBl.add(new TrGuid("Ср32Г","32Г","5","Сооружение 32Г - железнодорожный КПП"));
        lstBl.add(new TrGuid("Зд34","34","5","Здание 34 - центр физ.защиты (караульное помещение, центральный пункт управления, убежище 34А)"));
        lstBl.add(new TrGuid("Зд34А","34А","5","Здание 34А - Здание 34А. Убежище ГО на 60 укрываемых"));
        lstBl.add(new TrGuid("Ср37","37","5","Сооружение 37 - площадка погрузки/разгрузки железнодорожных вагонов"));
        lstBl.add(new TrGuid("Ср21","21","6","Сооружение 21 - газобаллонная"));
        lstBl.add(new TrGuid("Ср24А","24А","6","Сооружение 24А - резервуары запаса питьевой воды 2x1000 м3"));
        lstBl.add(new TrGuid("Ср24Б","24Б","6","Сооружение 24Б - резервуары запаса питьевой воды 2x1900 м3"));
        lstBl.add(new TrGuid("Ср24В","24В","6","Сооружение 24B - фильтры - поглотители"));
        lstBl.add(new TrGuid("Ср27","27","6","Сооружение 27 - аргонная станция"));
        lstBl.add(new TrGuid("Ср28","28","6","Сооружения 28 - компрессорная низкого давления"));
        lstBl.add(new TrGuid("Ср29","29","6","Сооружение 29 - сооружение учета теплоты"));
        lstBl.add(new TrGuid("Ср61","61","6","Сооружение 61 - азотно-водородная станция"));
        lstBl.add(new TrGuid("Ср62","62","6","Сооружение 62 - площадка размещения газификаторов для азота"));
        lstBl.add(new TrGuid("Ср64","64","6","Сооружение 64 - холодильная станция"));
        lstBl.add(new TrGuid("Ср36","36","6","Сооружение 36 - очистные сооружения промливневых стоков и стоков, содержащих нефтепродукты"));
        lstBl.add(new TrGuid("Ср36А","36А","6","Сооружение 36А - очистные сооружения хозяйственно-бытовых сточных вод зоны свобод. и контр. доступа"));
        lstBl.add(new TrGuid("Ср4Б","4Б","6","Сооружение 4Б - вытяжная труба"));
        lstBl.add(new TrGuid("Ср38","38","6","Сооружение 38 - дренажная насосная станция"));
        lstBl.add(new TrGuid("Ср1-7","КНС-6","6","Сооружения 1-7 - канализационные насосные станции бытовых стоков, производственно-дождевых стоков"));
        lstBl.add(new TrGuid("НрСетиЭлек","НЭС","4","Наружные сети электроснабжения"));
        lstBl.add(new TrGuid("НрСетиКан","НВК","6","Внутриквартальные наружные сети водопровода и канализации"));
        lstBl.add(new TrGuid("НрСетиТепл","НТС","6","Наружные сети теплоснабжения"));
        lstBl.add(new TrGuid("НрСетиСв","НСС","5","Наружные сети связи и сигнализации"));
        lstBl.add(new TrGuid("НрСетиГаз","НГС","6","Наружные сети газоснабжения, холодоснабжения, сжатого воздуха"));
        lstBl.add(new TrGuid("А/Д","АД","5","Автодороги и подъезды"));
        lstBl.add(new TrGuid("Ж/Д","ЖД","5","Железнодорожные пути"));
        lstBl.add(new TrGuid("СисФизЗащ","Перм","5","Периметр площадки ОДЭК. Система физической защиты"));
        lstBl.add(new TrGuid("АБК","АБК","8","Административно-бытовой городок"));
        //        lstBl.add(new TrGuid("","","",""));
//        lstBl.add(new TrGuid("","","",""));
        //****************************************************************************************************
        //***************************************************************************************************
        ElGuid.clear();
        //инициализация разделов РД
        lstRz.add(new ElGuid("АС","Архитектурно-строительные решения"));
        lstRz.add(new ElGuid("КЖ","Конструкции железобетонные"));
//        lstRz.add(new ElGuid("КЖ.7","Конструкции железобетонные"));
//        lstRz.add(new ElGuid("КЖ.8","Конструкции железобетонные"));
//        lstRz.add(new ElGuid("КЖ.9","Конструкции железобетонные"));
        lstRz.add(new ElGuid("ГР","Гидроизоляция решения"));
        lstRz.add(new ElGuid("КМ","Конструкции металлические"));
        lstRz.add(new ElGuid("КМ обл","Облицовка"));
        lstRz.add(new ElGuid("ГП","Генеральный план"));
        lstRz.add(new ElGuid("АР","Архитектурные решения."));
        lstRz.add(new ElGuid("ОВ","Вентиляция,  отопление и теплоснабжение"));
        lstRz.add(new ElGuid("ОТ","Отопление и теплоснабжение."));
        lstRz.add(new ElGuid("АОВ","Управление и автоматика."));
        lstRz.add(new ElGuid("ВК","Системы водоснабжения и водоотведения"));
        lstRz.add(new ElGuid("АВК","Управление и автоматика."));
        lstRz.add(new ElGuid("СК","Спец. канализация"));
        lstRz.add(new ElGuid("ЭМ","Силовое электрооборудование"));
        lstRz.add(new ElGuid("ЭО","Электроосвещение."));
        lstRz.add(new ElGuid("ТХ","Технология производства"));
        lstRz.add(new ElGuid("КИП","Технологический контроль."));
        lstRz.add(new ElGuid("АК","Автоматизация комплексная."));
        lstRz.add(new ElGuid("ТВК","Система телевизионного контроля"));
        lstRz.add(new ElGuid("ТС","Тепломеханические решения."));
        lstRz.add(new ElGuid("ХС","Система холодоснабжения."));
        lstRz.add(new ElGuid("СРК","Система радиационного контроля."));
        lstRz.add(new ElGuid("ГВС","Система газо-воздухоснабжения"));
        lstRz.add(new ElGuid("САС","Система аварийной сигнализации."));
        lstRz.add(new ElGuid("СУиК ЯМ","Система учета и контроля ЯМ,РВ и РАО"));
        lstRz.add(new ElGuid("СМИК","Система мониторинга инжененрных систем."));
        lstRz.add(new ElGuid("СС","Сети связи."));
        lstRz.add(new ElGuid("ПС","Пожарная сигнализация."));
        lstRz.add(new ElGuid("СУДОС","СУДОС"));
        lstRz.add(new ElGuid("СОЭН","СОЭН"));
        lstRz.add(new ElGuid("СОТС","СОТС"));
        lstRz.add(new ElGuid("ИССФЗ","ИС СФЗ"));
        assert prnq(" > exit beg $");
    }//beg

}//class rwPimFile
