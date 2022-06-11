package excellsheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class OperationsImplementation implements Operations {
	
	XSSFWorkbook workbook = new XSSFWorkbook();
	XSSFSheet sheet2 = workbook.createSheet("Extracted Vulnearability Details");
	XSSFSheet sheet3 = workbook.createSheet("Count Details");
	File excel = new File("C:\\Users\\Manasi\\Documents\\initial-excel.xlsx");
	ArrayList<ArrayList<String>> allElements = new ArrayList<ArrayList<String>>();
	Map<Integer, Object[]> extractedDataToBeWritten = new TreeMap<Integer, Object[]>();
	Map<String, Object[]> countData = new TreeMap<String, Object[]>();
	Map<String, ArrayList<Integer>> mapElementsForCount = new HashMap<String, ArrayList<Integer>>();
	String app_name, severity,low = "Low", medium= "Medium", high = "High";

	@Override
	public void readFromInitialExcel() {
		try {
			FileInputStream fis = new FileInputStream(excel);
			XSSFWorkbook book = new XSSFWorkbook(fis);
			XSSFSheet sheet1 = book.getSheetAt(0);
			Iterator<Row> itr = sheet1.iterator();
			Boolean flag = false;
			while (itr.hasNext()) {
				if (!flag) {
					flag = true;
					System.out.println("");
					System.out.println("Extracted data from given excel sheet...");
					for (int i = 0; i < 9; i++) {
						Row row = itr.next();
						app_name = row.getCell(0).getStringCellValue();
						System.out.print(app_name + "\t");
						severity = row.getCell(1).getStringCellValue();
						System.out.println(severity + "\t");
						System.out.print("");
					}
				}
			}
			System.out.println("Extracted data successfully displayed!!!");
			System.out.println("");
		} catch (FileNotFoundException fe) {
			fe.printStackTrace();
		} catch (IOException ie) {
			ie.printStackTrace();
		}
	}

	@Override
	public void writeExtractedDataToNewExcel() {
		try {
			FileInputStream fis = new FileInputStream(excel);
			XSSFWorkbook book = new XSSFWorkbook(fis);
			XSSFSheet sheet1 = book.getSheetAt(0);
			Iterator<Row> itr = sheet1.iterator();
			Boolean flag = false;
			while (itr.hasNext()) {
				if (!flag) {
					flag = true;
					for (int i = 0; i < 9; i++) {
						Row row = itr.next();
						app_name = row.getCell(0).getStringCellValue();
						severity = row.getCell(1).getStringCellValue();
						ArrayList<String> currentRow = new ArrayList();
						currentRow.add(app_name);
						currentRow.add(severity);
						extractedDataToBeWritten.put(i, new Object[] { i, app_name, severity });
						allElements.add(currentRow);
					}
				}
				Set<Integer> keyset = extractedDataToBeWritten.keySet();
				int rownum = 0;
				for (Integer key : keyset) {
					Row row = sheet2.createRow(rownum++);
					Object[] objArr = extractedDataToBeWritten.get(key);
					int cellnum = 0;
					for (Object obj : objArr) {
						Cell cell = row.createCell(cellnum++);
						if (obj instanceof String)
							cell.setCellValue((String) obj);
						else if (obj instanceof Integer)
							cell.setCellValue((Integer) obj);
					}
				}
			}
			book.close();
			fis.close();
			FileOutputStream out = new FileOutputStream(new File("Vulnearability-Details.xlsx"));
			workbook.write(out);
			out.close();
		} catch (FileNotFoundException fe) {
			fe.printStackTrace();
		} catch (IOException ie) {
			ie.printStackTrace();
		}
	}

	@Override
	public void countBasedOnCategory() {
		for (int i = 0; i < 9; i++) {
			boolean isExists = mapElementsForCount.containsKey(allElements.get(i).get(0));
			if (isExists) {
				ArrayList<Integer> temp = mapElementsForCount.get(allElements.get(i).get(0));
				if (Objects.equals(allElements.get(i).get(1), low)) {
					int index_low = 0;
					int value = temp.get(index_low);
					value = value + 1;
					temp.set(index_low, value);
				} else if (Objects.equals(allElements.get(i).get(1), medium)) {
					int index_medium = 1;
					int value = temp.get(index_medium);
					value = value + 1;
					temp.set(index_medium, value);
				} else if (Objects.equals(allElements.get(i).get(1), high)) {
					int index_high = 2;
					int value = temp.get(index_high);
					value = value + 1;
					temp.set(index_high, value);
				}
				mapElementsForCount.put(allElements.get(i).get(0), temp);
			} else {
				ArrayList<Integer> temp = new ArrayList<Integer>(Arrays.asList(0, 0, 0));
				if (Objects.equals(allElements.get(i).get(1), low)) {
					int index_low = 0;
					int value = temp.get(index_low);
					value = value + 1;
					temp.set(index_low, value);
				} else if (Objects.equals(allElements.get(i).get(1), medium)) {
					int index_medium = 1;
					int value = temp.get(index_medium);
					value = value + 1;
					temp.set(index_medium, value);
				} else if (Objects.equals(allElements.get(i).get(1), high)) {
					int index_high = 2;
					int value = temp.get(index_high);
					value = value + 1;
					temp.set(index_high, value);
				}
				mapElementsForCount.put(allElements.get(i).get(0), temp);
			}
		}
		System.out.println("Count based on category in array format...");
		for (String i : mapElementsForCount.keySet()) {
			String key = i.toString();
			ArrayList<Integer> value = mapElementsForCount.get(i);
			System.out.println(key + " " + value);
		}
		System.out.println("Count based on categories successfully displayed!!!");
		System.out.println("");
	}

	@Override
	public void writeCountToExcel() {
		try {
			XSSFRow row;
			countData.put("App Name", new Object[] { "Low", "Medium", "High" });
			for (String i : mapElementsForCount.keySet()) {
				countData.put(i.toString(), new Object[] { Integer.toString(mapElementsForCount.get(i).get(0)),
						Integer.toString(mapElementsForCount.get(i).get(1)), Integer.toString(mapElementsForCount.get(i).get(2)) });
			}
			Set<String> keyid = countData.keySet();
			int rowid = 0;
			for (String key : keyid) {
				row = sheet3.createRow(rowid++);
				Object[] objectArr = countData.get(key);
				int cellid = 0;
				Cell cell = row.createCell(cellid++);
				cell.setCellValue((String) key);
				for (Object obj : objectArr) {
					cell = row.createCell(cellid++);
					cell.setCellValue((String) obj);
				}
			}
			FileOutputStream out = new FileOutputStream(new File("Count-Details.xlsx"));
			workbook.write(out);
			System.out.println("Count-Details.xlsx written successfully on disk.");
		} catch (FileNotFoundException fe) {
			fe.printStackTrace();
		} catch (IOException ie) {
			ie.printStackTrace();
		}

	}

	
}
