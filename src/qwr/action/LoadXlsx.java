/**
 * Подгрузка проектной документации и формирование ссответствующих справочников
 */
package qwr.action;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import qwr.footing.InfcElm;
import qwr.footing.LoadFile;
import qwr.model.Tabl.EiClaim;
import qwr.model.nexus.EDraft;
import qwr.reports.ERcol;
import qwr.util.Tranq;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;

import static org.apache.poi.ss.usermodel.CellType.STRING;
import static qwr.util.BgFile.prnq;
import static qwr.util.BgFile.prnt;

public class LoadXlsx  {
    private static String fNameLoadXslx;//имя подгружаемоно файла таблиц карточек
    private static int shiftRow=0;//смещение данных в таблице по вертикали после шапки
    //инициализация списка колонок исходного документа
    private static ArrayList<ERcol> ercol = new ArrayList<>(32);
    private static ArrayList<String> lsTabl = new ArrayList<>(8);//список имен листов для чтения
    //-----------------------------------------------------------------------

    public static void iniERcolNewClaims() {//база анализа карточек
        fNameLoadXslx="Реестр обращений ЕОС-Качество от 22.01.2021.xlsx";
        lsTabl.clear();//предварительная очистка
        lsTabl.add("СМР");
        lsTabl.add("ОВ");
        lsTabl.add("ТХ и ВК");
        lsTabl.add("ЭМР");
        ercol.clear();//предварительная очистка
        ERcol.add(ercol,2,"№ п/п");//A
        ERcol.add(ercol,4,"Обращение\n (№ письма)");//B
        ERcol.add(ercol,5,"Организация \n(инициатор обращения)");//C
        ERcol.add(ercol,6,"ФИО инициатора обращения");//D
        ERcol.add(ercol,7,"Инвентарный номер РД");//E
        ERcol.add(ercol,8,"Здание/\nСооружение");//F
        ERcol.add(ercol,9,"Вид СМР");//G
        ERcol.add(ercol,10,"Текст обращения");//H
        ERcol.add(ercol,11,"Номер записи в ЕОС");//I
        ERcol.add(ercol,13,"Комментарии ООО \"УС БАЭС\"");
        ERcol.add(ercol,0,"");//K
        ERcol.add(ercol,0,"РД выдана");//L
        ERcol.add(ercol,0,"РД не выдана");//M
        ERcol.add(ercol,0,"Не рассмотрено АП");//N
        ERcol.add(ercol,0,"Отклонено");//O
        ERcol.add(ercol,0,"ПЭМ-СТН");//P
        ERcol.add(ercol,0,"Титан-2");//Q
        ERcol.add(ercol,0,"__");//R
        ERcol.add(ercol,0,"Замечания ООО \"УС БАЭС\"");//S
        ERcol.add(ercol,0,"Замечания АО \"СХК\"");//T
        prnq("> Инициализация полей (lRsxk) "+ ercol.size()+" "+fNameLoadXslx);
    }//iniERcolNewClaims-============================================================


    public static void iniERcolOldClaims() {//база анализа карточек по намому реестру
        fNameLoadXslx="Реестр карточек заказчика от 22.01.2021.xlsx";
        //максимальная ширина таблицы определяется по количеству описанных столбцов
        shiftRow=2;//смещение данных в таблице по вертикали после шапки
        lsTabl.clear();//предварительная очистка//предварительная очистка
        lsTabl.add("База (АРХИВ)");
        lsTabl.add("СМР");
        lsTabl.add("ОВ");
        lsTabl.add("ТХ и ВК");
        lsTabl.add("ЭМР");
        ercol.clear();//предварительная очистка
//        ERcol.add(lRctc,2,"vasz");
        ERcol.add(ercol,2,"№ п/п");//A целое
        ERcol.add(ercol,3,"Карточка Заказчика");//B
        ERcol.add(ercol,8,"№ Здание/сооружение");//C список зданий
        ERcol.add(ercol,9,"Система (вид СМР) ");//D список из 6 позиций
        ERcol.add(ercol,7,"Инв.№ документа, Изм.");//E номерА РД с изменением и датой
        ERcol.add(ercol,18,"Инициатор, ФИО, организация");//F список фамилий (содержит письмо)
        ERcol.add(ercol,5,"Организация");//G список из 3
        ERcol.add(ercol,10,"Описание вопроса");//H большой текст
        ERcol.add(ercol,14,"Влияние на СМР/ТП");//I номера писем с датами
        ERcol.add(ercol,15,"Решение");//J два числа через дефис или косую
        ERcol.add(ercol,0,"Срок рассмотрения по регламенту");//K
        ERcol.add(ercol,0,"Дата фактического  рассмотрения");//L
        ERcol.add(ercol,16,"Краткое пояснение");//M
        ERcol.add(ercol,17,"Изменение\n" +"(инв.№) ");//N
        ERcol.add(ercol,13,"Комментарий \"УК \"УЭС\"");//O
        ERcol.add(ercol,0,"Просрочено");//P
        ERcol.add(ercol,0,"");//Q
        ERcol.add(ercol,0,"");//R
        ERcol.add(ercol,0,"");//S
        ERcol.add(ercol,0,"");//T
        ERcol.add(ercol,0,"Влияние на КСГ и ТП");//U
        ERcol.add(ercol,0,"Влияние на КСГ и ТП");//V
        ERcol.add(ercol,0,"Выдано, но не испралено");//W
        ERcol.add(ercol,0,"Корректировка стадии П");//X
        ERcol.add(ercol,0,"АО \"КОНЦЕРН ТИТАН-2\"");//Y
        ERcol.add(ercol,0,"");//Z
        ERcol.add(ercol,0,"Замечания УС БАЭС");//AA
        ERcol.add(ercol,0,"Замечания СХК");//AB
        prnq("> Инициализация полей (lRsxk) "+ ercol.size()+" "+fNameLoadXslx);
        LoadXlsx.readFileXslx(new EiClaim(),fNameLoadXslx,lsTabl, ercol, LoadXlsx.shiftRow);
    }//iniERcolUsbaes ---------------------------------------------------------------

    //-------------------------------------------------------------------------------
    public static void iniLoadDocPrj() {//база анализа карточек по намому реестру
        assert prnq("$ LoadXlsx > iniLoadDocPrj $");
        fNameLoadXslx ="rdsort.xlsx";
        //максимальная ширина таблицы определяется по количеству описанных столбцов
        shiftRow=2;//смещение данных в таблице по вертикали после шапки
        lsTabl.clear();//предварительная очистка
        lsTabl.add("Reports");
        ercol.clear();//предварительная очистка
        ERcol.add(ercol,0,"№ п.п.");
        ERcol.add(ercol,1,"№ РД");
        ERcol.add(ercol,3,"№ изм.");
        ERcol.add(ercol,4,"дата измен РД");
        ERcol.add(ercol,5,"Здание");
        ERcol.add(ercol,6,"Раздел");
        ERcol.add(ercol,7,"Вид");
        ERcol.add(ercol,8,"Статус");
        ERcol.add(ercol,2,"наименование РД");
        ERcol.add(ercol,9,"дата  пред измен РД");
        ERcol.add(ercol,10,"№ пред изм.");

        prnq("> Инициализация полей (lRsxk) "+ ercol.size()+" "+fNameLoadXslx);
        LoadXlsx.readFileXslx(new EDraft(),fNameLoadXslx,lsTabl, ercol, LoadXlsx.shiftRow);
    }//iniLoadDocPrj ---------------------------------------------------------------
    /**
     * чтение первичных данных из файла обработанного списка рабочей документации
     * @param a класс элемента чей будет parse вызван для сохренение через него даных
     * @param fNameLoadXslx имя файла электронной таблицы
     * @param lsTabl список имен листов
     * @param lRcol список наименований колонок для сопоставления <ERcol>
     * @param shiftRow смещение данных на количество строк от первой строки шапки
     */
    public static boolean readFileXslx(LoadFile a, String fNameLoadXslx, ArrayList<String> lsTabl,
                                           ArrayList<ERcol> lRcol, int shiftRow) {
        assert prnq("$ LoadXlsx > readFileXslx $");
        assert !lRcol.isEmpty():"ERROR ERcol.size is NULL не задано !!!";
        Path path = Paths.get(fNameLoadXslx);
        if (!Files.exists(path)){prnq("ERROR file is no exists! "+path);return true;}
        if (!Files.isReadable(path)){prnq("ERROR not read file! "+path);return true;}
        prnq("\nЧитаю файл :<"+fNameLoadXslx+"> ******\n");
        try(FileInputStream file = new FileInputStream(new File(fNameLoadXslx))){
            XSSFWorkbook wbook = new XSSFWorkbook(file);
            int sheeti=wbook.getNumberOfSheets();
            assert prnq("Кол-во листов "+sheeti+" Кол-во столбцов "+lRcol.size());
            int nadd=0;//подсчет количества ошибок
            int nerx=0;//подсчет прочих распознований
            //создаю массив строк в замен EiClaim itm= new EiClaim();
            String[] ldsrt = new String[lRcol.size()+1];
            //цикл по листам
            for (int numSheet =0; numSheet<sheeti; numSheet++){//цикл по листам
                XSSFSheet sheet = wbook.getSheetAt(numSheet);
                boolean tSh=true;//проверяю на необходимость распознавания лист
                for(String sns: lsTabl)if(sns.equals(sheet.getSheetName())) tSh=false;
                if (tSh) continue;//такого листа не заявлено обхожу
                //создаю массив соответствия колонки документа и данных в  class ElmRDS
                int[] cRDS =new int[lRcol.size()+1];//создаю массив соответствия
                int brow=-1;//превая строка данных определяется после шапки таблицы
                labelTitl:
                for (Row row : sheet) {//цикл по строкам в поисках шапки таблицы
                    for (Cell cell : row) {//цикл по ячейкам строки
                        if (cell.getColumnIndex() > lRcol.size() ) { break;}//пропускаю лишние ячейки
                        //ищу только одно значение
                        if (cell.getCellType()!=STRING) continue;//если не строка пропускаю
                        for (ERcol el: lRcol) {//перебор вариантов полей
                            if (cell.getStringCellValue().equalsIgnoreCase(el.getFname())){
                                cRDS[cell.getColumnIndex()]=el.getElcl();//соответствие колонки данным
                                brow=row.getRowNum()+shiftRow;//место данных от шакки
                                break;  //переход к следующей ячейки
                            }//поле найдено
                        }//for lRctc перебор вариантов полей
                    }//for cell цикл по ячейкам строки
                }//for row цикл по строкам в поисках шапки таблицы
                for(ERcol e:lRcol)prnt(" "+e.getIcol()+"~"+e.getElcl());prnq("#");
                prnq("Писать на основе списка. Лист >"+sheet.getSheetName()+"<");
                for(Integer i: cRDS) prnt(" "+i+" "); prnq("---");
                //-------------------------------------------------------------------
                int plaze=0;//индекс ячейки
                String str = null;
                int lastrow=0;//для проверки на наличие строк в файле после блока данных
                for (Row row : sheet) { //цикл по строкам листа
                    //проверка на окончание таблицы данных
                    if (row.getRowNum()-lastrow>12) break; lastrow=row.getRowNum();
                    if (row.getRowNum()<brow){prnt("%");continue;} //попускаю первые строчки заголовка таблицы
                    for (Cell cell : row) {

                        if (cell.getColumnIndex() > lRcol.size() ){break;}//пропускаю ячейки
                        plaze= cRDS[cell.getColumnIndex()];//индекс ячейки
                        str="";
                        if (plaze<1) continue;//пропускаю ячейки которые ни куда не пишутся
                        switch (cell.getCellType()) {
                            case ERROR: break;
                            case BLANK: break;
//                            case _NONE: assert prnt("o "); break;
                            case NUMERIC://число
                                str= String.valueOf(cell.getNumericCellValue());
                                break;
                            case STRING://строка
                                if (cell.getStringCellValue().isBlank()){ break; }
                                str=cell.getStringCellValue();
                                break;
                            case FORMULA:
                                switch (cell.getCachedFormulaResultType()){
                                    case NUMERIC:
                                        str=String.valueOf(cell.getNumericCellValue());
                                        break;
                                    case STRING:
                                        str=cell.getStringCellValue();
                                        break;
                                }//switch FORMULA
                                break;//завершение анализа формулы
                            default: nerx++;//счетчик непонятных распознавания
                        }//switch
                        ldsrt[cell.getColumnIndex()]= Tranq.purifyin(str.trim());//сохраняю в массиве строк
                    }//for cell
                    a.parse(ldsrt,cRDS,sheet.getSheetName());
                    nadd++;
                }//for row
            }//for numsheet----------------------------------------------------------
            file.close();
            prnq("\nНераспозано "+nerx+ "  Подкачено "+nadd+" строк\n");
        }// try чтение файла исходного документа
        catch (Exception e){ e.printStackTrace(); return true;}
        finally {        }
        return false;
    } //readFileXslx ----------------------------------------------------------------
}  //class LoadXlsx===================================================================
