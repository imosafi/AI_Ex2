/*
 * main class containing the main function
 * class name is identical to the exercise name
 */
public class java_ex2 {

	public static void main(String[] args) {
		InputFileParser inputFileParser = new InputFileParser();
		OutputWriter outputWriter = new OutputWriter();
		PolicyCreator policyCreator = new PolicyCreator();

		inputFileParser.parseFileContent();
		policyCreator.initializeBoard(inputFileParser.matrixSize, inputFileParser.inputMatrix);
		policyCreator.consolidateUtility();
		policyCreator.findOptimalPolicy();

		for (int i = 0; i < inputFileParser.matrixSize; i++)
			for (int j = 0; j < inputFileParser.matrixSize; j++)
				if (policyCreator.policyMatrix[i][j] != null)
					outputWriter.writeToFile(i + "," + j + "," + policyCreator.policyMatrix[i][j].toString());

		outputWriter.closeStream();

	}

}
