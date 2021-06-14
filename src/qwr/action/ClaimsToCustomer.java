/**
 * Все методы и данные для инициализации работы с катрочками заказчика
 * //максимальная ширина таблицы определяется по количеству описанных столбцов
 */
package qwr.action;
//работа с карточками Заказчика
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import qwr.model.Base.Eimail;
import qwr.model.SharSystem.GrRecords;
import qwr.reports.ERcol;
import qwr.model.Tabl.EiClaim;
import qwr.util.DateTim;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.apache.poi.ss.usermodel.CellType.STRING;
import static qwr.util.BgFile.*;

//Объявление глобальных переменных
public class ClaimsToCustomer {
    public static String fNameLoadClaimXslx;//имя подгружаемоно файла таблиц карточек
    private static int shiftRow=0;//смещение данных в таблице по вертикали после шапки
    //инициализация списка колонок исходного документа
    public static ArrayList<ERcol> lRctc = new ArrayList<>(32);
    public static ArrayList<EiClaim> lclem = new ArrayList<>(900);
    public static ArrayList<String> lsTbl = new ArrayList<>(8);//список имен листов для чтения
    public static HashMap<String,String> lsalt=new HashMap<>();//таблица замен
    //-----------------------------------------------------------------------
    public static void iniERcolOldClaims() {//база анализа карточек по намому реестру
        fNameLoadClaimXslx="Реестр карточек заказчика от 22.01.2021.xlsx";
        //максимальная ширина таблицы определяется по количеству описанных столбцов
        shiftRow=2;//смещение данных в таблице по вертикали после шапки
        lsTbl.clear();//предварительная очистка
        lsTbl.add("База (АРХИВ)");
        lsTbl.add("СМР");
        lsTbl.add("ОВ");
        lsTbl.add("ТХ и ВК");
        lsTbl.add("ЭМР");
//        ERcol.add(lRctc,2,"vasz");
        lRctc.add(new ERcol(2,"№ п/п"));//A целое
        lRctc.add(new ERcol(3,"Карточка Заказчика"));//B
        lRctc.add(new ERcol(8,"№ Здание/сооружение"));//C список зданий
        lRctc.add(new ERcol(9,"Система (вид СМР) "));//D список из 6 позиций
        lRctc.add(new ERcol(7,"Инв.№ документа, Изм."));//E номерА РД с изменением и датой
        lRctc.add(new ERcol(18,"Инициатор, ФИО, организация"));//F список фамилий (содержит письмо)
        lRctc.add(new ERcol(5,"Организация"));//G список из 3
        lRctc.add(new ERcol(10,"Описание вопроса"));//H большой текст
        lRctc.add(new ERcol(14,"Влияние на СМР/ТП"));//I номера писем с датами
        lRctc.add(new ERcol(15,"Решение"));//J два числа через дефис или косую
        lRctc.add(new ERcol(0,"Срок рассмотрения по регламенту"));//K
        lRctc.add(new ERcol(0,"Дата фактического  рассмотрения"));//L
        lRctc.add(new ERcol(16,"Краткое пояснение"));//M
        lRctc.add(new ERcol(17,"Изменение\n" +"(инв.№) "));//N
        lRctc.add(new ERcol(13,"Комментарий \"УК \"УЭС\""));//O
        lRctc.add(new ERcol(0,"Просрочено"));//P
        lRctc.add(new ERcol(0,""));//Q
        lRctc.add(new ERcol(0,""));//R
        lRctc.add(new ERcol(0,""));//S
        lRctc.add(new ERcol(0,""));//T
        lRctc.add(new ERcol(0,"Влияние на КСГ и ТП"));//U
        lRctc.add(new ERcol(0,"Влияние на КСГ и ТП"));//V
        lRctc.add(new ERcol(0,"Выдано, но не испралено"));//W
        lRctc.add(new ERcol(0,"Корректировка стадии П"));//X
        lRctc.add(new ERcol(0,"АО \"КОНЦЕРН ТИТАН-2\""));//Y
        lRctc.add(new ERcol(0,""));//Z
        lRctc.add(new ERcol(0,"Замечания УС БАЭС"));//AA
        lRctc.add(new ERcol(0,"Замечания СХК"));//AB
        prnq("> Инициализация полей (lRsxk) "+lRctc.size()+" "+fNameLoadClaimXslx);
    }//iniERcolUsbaes ---------------------------------------------------------------

    public static void iniERcolNewClaims() {//база анализа карточек
        fNameLoadClaimXslx="Реестр обращений ЕОС-Качество от 22.01.2021.xlsx";
        lsTbl.clear();//предварительная очистка
        lsTbl.add("СМР");
        lsTbl.add("ОВ");
        lsTbl.add("ТХ и ВК");
        lsTbl.add("ЭМР");
        lRctc.add(new ERcol(2,"№ п/п"));//A
        lRctc.add(new ERcol(4,"Обращение\n (№ письма)"));//B
        lRctc.add(new ERcol(5,"Организация \n(инициатор обращения)"));//C
        lRctc.add(new ERcol(6,"ФИО инициатора обращения"));//D
        lRctc.add(new ERcol(7,"Инвентарный номер РД"));//E
        lRctc.add(new ERcol(8,"Здание/\nСооружение"));//F
        lRctc.add(new ERcol(9,"Вид СМР"));//G
        lRctc.add(new ERcol(10,"Текст обращения"));//H
        lRctc.add(new ERcol(11,"Номер записи в ЕОС"));//I
        lRctc.add(new ERcol(13,"Комментарии ООО \"УС БАЭС\""));
        lRctc.add(new ERcol(0,""));//K
        lRctc.add(new ERcol(0,"РД выдана"));//L
        lRctc.add(new ERcol(0,"РД не выдана"));//M
        lRctc.add(new ERcol(0,"Не рассмотрено АП"));//N
        lRctc.add(new ERcol(0,"Отклонено"));//O
        lRctc.add(new ERcol(0,"ПЭМ-СТН"));//P
        lRctc.add(new ERcol(0,"Титан-2"));//Q
        lRctc.add(new ERcol(0,"__"));//R
        lRctc.add(new ERcol(0,"Замечания ООО \"УС БАЭС\""));//S
        lRctc.add(new ERcol(0,"Замечания АО \"СХК\""));//T
        prnq("> Инициализация полей "+lRctc.size()+" ");
    }//iniERcolNewClaims-============================================================
    /**
     * Считывание данных таблицы карточер для дальнейшего анализа
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * берутся из файла fNameLoadClaimXslx с перечнем листов <String>lsTbl,
     * количеством колонок анализа maxcol, смещением от шапки таблицы shiftRow
     * варианты полей с местом записи в <ERcol>lRctc
     * результат дописывается в ArrayList<EiClaim> lclem
     * ^ версия метода работой черезмассив строк размером по количеству столбцов
     */
    public static void readFileClaimsDef(ArrayList<EiClaim> lclem ) {
        assert prnq("$ ClaimsToCustomer > readFileClaimsDef $");
        prnq("\nАнализируется файл :<"+fNameLoadClaimXslx+"> ******\n");
        try(FileInputStream file = new FileInputStream(new File(fNameLoadClaimXslx)))
        {
            XSSFWorkbook wbook = new XSSFWorkbook(file);
            int sheeti=wbook.getNumberOfSheets();
            assert prnq("Кол-во листов "+sheeti+" Кол-во столбцов "+lRctc.size());
            int nerr=0;//подсчет количества ошибок
            int nerx=0;//подсчет прочих распознований
            //создаю массив строк в замен EiClaim itm= new EiClaim();
            String[] ldsrt = new String[lRctc.size()+1];
            //цикл по листам
            for (int numSheet =0; numSheet<sheeti; numSheet++){//цикл по листам
                XSSFSheet sheet = wbook.getSheetAt(numSheet);
                boolean tSh=true;//проверяю на необходимость распознавания лист
                for(String sns: lsTbl)if(sns.equals(sheet.getSheetName())) tSh=false;
                if (tSh) continue;//такого листа не заявлено обхожу
                //создаю массив соответствия колонки документа и данных в  class ElmRDS
                int[] cRDS =new int[lRctc.size()+1];//создаю массив соответствия
                int brow=-1;//превая строка данных определяется после шапки таблицы
                labelTitl:
                for (Row row : sheet) {//цикл по строкам в поисках шапки таблицы
                    for (Cell cell : row) {//цикл по ячейкам строки
                        if (cell.getColumnIndex() > lRctc.size() ) { break;}//пропускаю лишние ячейки
                        //ищу только одно значение
                        if (cell.getCellType()!=STRING) continue;//если не строка пропускаю
                        for (ERcol el: lRctc) {//перебор вариантов полей
                            if (cell.getStringCellValue().equalsIgnoreCase(el.getFname())){
                                cRDS[cell.getColumnIndex()]=el.getElcl();//соответствие колонки данным
                                brow=row.getRowNum()+shiftRow;//место данных от шакки
                                break;
                            }//поле найдено
                        }//for lRctc перебор вариантов полей
                    }//for cell цикл по ячейкам строки
                }//for row цикл по строкам в поисках шапки таблицы
                assert prnq("Содержимое шапки "+sheet.getSheetName()+">"+brow);
                for(ERcol e:lRctc)prnt(" "+e.getIcol()+"~"+e.getElcl());prnq("#");
                prnq("Куда писать на основе поля ELCL списка колонок");
                for(Integer i: cRDS) prnt(" "+i+" "); prnq("---");
                //-------------------------------------------------------------------
                int plaze=0;//индекс ячейки
                String str = null;
                for (Row row : sheet) {
                    if (row.getRowNum()<brow){prnt("%");continue;} //попускаю первые строчки заголовка таблицы
                    for (Cell cell : row) {
                        if (cell.getColumnIndex() > lRctc.size() ){break;}//пропускаю ячейки
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
                        ldsrt[cell.getColumnIndex()]=str;//сохраняю в массиве строк
                    }//for cell
                    EiClaim.have(ldsrt,cRDS,sheet.getSheetName());
                }//for row
            }//for numsheet----------------------------------------------------------
            file.close();
            prnq("\nКоличество ячееек с ошибкой "+nerr+"  Нераспозано "+nerx+
                    "  Подкачено "+lclem.size()+" строк\n");
        }//чтение файла исходного документа
        catch (Exception e){ e.printStackTrace(); }
        finally {        }
    }//readFileClaims-===============================================================
    /**
     * Корректировка распознавния поля письма
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * выводит найденные не распознвнные места на экран и более пока ни чего не делает
     * @param lclem временный список карточек
     * @param lsalt таблица замен
     */
    public static void identifyIntellect(ArrayList<EiClaim> lclem,
                                         HashMap<String, String> lsalt) {
        assert prnq("$ ClaimsToCustomer > identifyIntellect $");
        for (EiClaim iclm: lclem){
            //проверяю на наличие письма по полю даты
            if (iclm.getDmail()>43000) continue;//письмо найдено -обход
            if (! DateTim.isNumbr(iclm.getAuthor())) continue;//нет цифр в строке
            prnq(" "+iclm.getAuthor()+"\t\t"+iclm.getSheet()+"\t"+iclm.getnClms()+
                    "\t@"+iclm.getDmail());

        }//for lclem
        //проверка стабильности загрузки писем в списке карточек
        /*
        assert prnq("количество карточек подгружено (891)"+lclem.size());
        int ds=0;
        int kc=0;
        for (EiClaim iclm: lclem){
            if (iclm.getDmail()<43000) { continue;}//нет письма по анализу даты
            ds+=iclm.getDmail();
            kc++;
        }//for lclem
        assert prnq("контроль подгрузки sum(16279984) "+ds+"\tcol(373) "+kc);
        assert prnq("количество писем подгруженных из базы (351)"+lsMail.size());

         */
    }//identifyIntellect-============================================================
    /**
     * Из прочтеного файла с карточками заказчика выдкляю номера писем и заношу их в
     * общую таблицу. Полученные идентификаторы писем вставляю во временню базу карточек
     * @param lclem временный список карточек
     * @param lsMail список писем
     *   Беру письмо из карточки. Номер письма не совпал с базой -создаю, номер
     * письма совпал с базой - проверяю дату письма и исполнителя. Если что-то не
     * совпало, то создаю пополняю список писем.
     */
    public static void exstractMailFromClaims(ArrayList<EiClaim> lclem,
                                              Map<Long,Eimail> lsMail) {
        boolean isadd;//флаг добавления письма в список
        //цикл по временным карточкам
        assert prnq("$ ClaimsToCustomer > exstractMailFromClaims $");
        for (EiClaim iclm: lclem){
            isadd=true;//флаг добавления письма в список
            //проверяю на наличие письма по полю даты
            if (iclm.getDmail()<43000) { continue;}//нет письма по анализу даты
            //что известно о письме к карточке
            //fio;    //6  "ФИО инициатора обращения"
            //nmail;  //4  "Обращение\n (№ письма)"
            //dmail;  //  дата письма
//            assert prnt("\n"+iclm.getFio()+"\t"+iclm.getNmail()+"\t"+iclm.getDmail());
            //проверяю наличие данного письма в базе по номеру и по дате
            for (Eimail imail:lsMail.values()){
                if (imail.compare(iclm)){
                    iclm.setKeymail(imail.getKey());
                    isadd=false;//флаг добавления письма в список
                    break;
                }
            }//for lsMail
            //проверяю наличия ссылки на письмо в списке карточек, если есть то обход
            if (iclm.getKeymail()<2 && isadd){//ключ не определен значит создаем письмо
                Eimail itm= new Eimail(iclm.getNmail(),iclm.getDmail(),iclm.getAuthor());
                long j=itm.getKey();
                while (lsMail.containsKey(j)) j--;
                itm.setKey(Math.toIntExact(j));
                iclm.setKeymail(Math.toIntExact(j)); //сохраняю ссылку в карточеке
                lsMail.put(j,itm);//добавляю элемент
                GrRecords.MAILJ.shift();//ставлю флаг наличия модификации списка писем
                prnt("+"+iclm.getAuthor()+"\t"+iclm.getNmail()+"\t"+iclm.getDmail());
                prnt("\t^ "+itm.getKey()+"\t"+lsMail.size()+"\n");
            }////ключ не определен значит создаем письмо
        }//for lclem
        assert prnq("*");
    }//exstractMailFromClaims

}//class ClaimsToCustomer ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ
