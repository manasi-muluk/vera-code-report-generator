package excellsheet;

public class ExcelVeracodeReportGenerator {
	public static void main(String[] args) {
		OperationsImplementation op = new OperationsImplementation();
		op.readFromInitialExcel();
		op.writeExtractedDataToNewExcel();
		op.countBasedOnCategory();
		op.writeCountToExcel();
	}
}
