import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadXls {
    public static void main(String[] args) throws IOException, InvalidFormatException {
        FileInputStream xlsFile = new FileInputStream(new File("C:\\Users\\gongz\\2010-2019 artificial intelligence.xlsx"));
        Workbook workbook = WorkbookFactory.create(xlsFile);
        String str = null;
        Sheet sheet = workbook.getSheetAt(3);
        Iterator<Row> iterator = sheet.iterator();
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        while (iterator.hasNext()) {
            Row currentRow = iterator.next();
            Iterator<Cell> cellIterator = currentRow.iterator();
            while (cellIterator.hasNext()) {
                Cell currentCell = cellIterator.next();
                StringBuilder s = new StringBuilder();
                if (currentCell.getCellTypeEnum() == CellType.STRING) {
                    s.append(currentCell.getStringCellValue());
                } else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
                    s.append(currentCell.getNumericCellValue());
                }
                str = s.toString();
                Pattern pattern = Pattern.compile("(, 1\\d\\d\\d,)|(, 20\\d\\d,)");
                Matcher matcher = pattern.matcher(str);
                if (matcher.find()) {
                    String tmp = matcher.group();
                    tmp = tmp.substring(tmp.length()-5,tmp.length()-1);
                    int year = Integer.parseInt(tmp);
                    if (!map.containsKey(year)) {
                        map.put(year, 0);
                    }
                    map.put(year, map.get(year)+1);
                }
            }
        }
        print(map);
    }
    static void print(HashMap<Integer, Integer> map) {
        StringBuilder year = new StringBuilder();
        StringBuilder count = new StringBuilder();
        for (Map.Entry<Integer, Integer> pair : map.entrySet()) {
            year.append(pair.getKey()+"\n");
            count.append(pair.getValue()+"\n");
        }
        System.out.println(year.toString());
        System.out.println("********************");
        System.out.println(count.toString());
    }
}