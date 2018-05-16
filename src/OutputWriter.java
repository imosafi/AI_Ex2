import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/*
 * this class is used to write the output to output.txt file
 * I assume that output.txt location should be the main project folder
 */
public class OutputWriter {
	private FileWriter fileWriter;
	private BufferedWriter bufferedWriter;

	public OutputWriter() {
		try {
			fileWriter = new FileWriter("output.txt");
			bufferedWriter = new BufferedWriter(fileWriter);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeToFile(String content) {
		try {
			bufferedWriter.write(content);
			bufferedWriter.newLine();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void closeStream() {
		try {
			if (bufferedWriter != null)
				bufferedWriter.close();
			if (fileWriter != null)
				fileWriter.close();
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
