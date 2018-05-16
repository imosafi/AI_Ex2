import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/*
 * this class is used to read and parse the input.txt file
 * I assume that input.txt location is at the main project folder
 */
public class InputFileParser {
	private static final int MATRIX_SIZE_ROW_INDEX = 0;
	private static final int MATRIX_BEGINNING_ROW_INDEX = 1;

	Path path;
	int matrixSize;
	char[][] inputMatrix;

	public InputFileParser() {
		path = FileSystems.getDefault().getPath("", "input.txt");
	}

	public void parseFileContent() {
		try {
			ArrayList<String> fileLines = (ArrayList<String>)Files.readAllLines(path);
			matrixSize = Integer.parseInt(fileLines.get(MATRIX_SIZE_ROW_INDEX));
			inputMatrix = new char[matrixSize][matrixSize];
			for (int i = 0; i < matrixSize; i++)
				inputMatrix[i] = fileLines.get(i + MATRIX_BEGINNING_ROW_INDEX).toCharArray();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}
