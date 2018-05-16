
/*
 * stateUtils is used mainly to get the valid neighbors of a state.
 * it contains useful functionality
 */

import java.util.HashMap;
import java.util.Map;

public class StateUtils {
	static final int NeighborsSize = 8;
	static final int RNeighborIndex = 0;
	static final int RDNeighborIndex = 1;
	static final int DNeighborIndex = 2;
	static final int LDNeighborIndex = 3;
	static final int LNeighborIndex = 4;
	static final int LUNeighborIndex = 5;
	static final int UNeighborIndex = 6;
	static final int RUNeighborIndex = 7;

	static Map<Character, Definitions.AreaType> charToAreaDictionary;

	static {
		charToAreaDictionary = new HashMap<Character, Definitions.AreaType>();
		charToAreaDictionary.put('G', Definitions.AreaType.G);
		charToAreaDictionary.put('S', Definitions.AreaType.S);
		charToAreaDictionary.put('R', Definitions.AreaType.R);
		charToAreaDictionary.put('H', Definitions.AreaType.H);
		charToAreaDictionary.put('D', Definitions.AreaType.D);
		charToAreaDictionary.put('W', Definitions.AreaType.W);
	}

	public static State[] GetValidNeighbors(State[][] matrix, int i, int j) {
		int maxLength = matrix.length - 1;
		State[] neighborsArr = new State[NeighborsSize];

		// check right neighbor
		if (j + 1 <= maxLength)
			if (matrix[i][j + 1].areaType != Definitions.AreaType.W)
				neighborsArr[RNeighborIndex] = matrix[i][j + 1];

		// right bottom diagonal
		if (i + 1 <= maxLength && j + 1 <= maxLength)
			if (matrix[i + 1][j + 1].areaType != Definitions.AreaType.W &&
					matrix[i + 1][j].areaType != Definitions.AreaType.W &&
					matrix[i][j + 1].areaType != Definitions.AreaType.W)

				neighborsArr[RDNeighborIndex] = matrix[i + 1][j + 1];

		// check down neighbor
		if (i + 1 <= maxLength)
			if (matrix[i + 1][j].areaType != Definitions.AreaType.W)
				neighborsArr[DNeighborIndex] = matrix[i + 1][j];

		// left bottom diagonal
		if (i + 1 <= maxLength && j - 1 >= 0)
			if (matrix[i + 1][j - 1].areaType != Definitions.AreaType.W &&
					matrix[i][j - 1].areaType != Definitions.AreaType.W &&
					matrix[i + 1][j].areaType != Definitions.AreaType.W)
				neighborsArr[LDNeighborIndex] = matrix[i + 1][j - 1];

		// check left neighbor
		if (j - 1 >= 0)
			if (matrix[i][j - 1].areaType != Definitions.AreaType.W)
				neighborsArr[LNeighborIndex] = matrix[i][j - 1];

		// left top diagonal
		if (i - 1 >= 0 && j - 1 >= 0)
			if (matrix[i - 1][j - 1].areaType != Definitions.AreaType.W &&
					matrix[i - 1][j].areaType != Definitions.AreaType.W &&
					matrix[i][j - 1].areaType != Definitions.AreaType.W)
				neighborsArr[LUNeighborIndex] = matrix[i - 1][j - 1];

		// check up neighbor
		if (i - 1 >= 0)
			if (matrix[i - 1][j].areaType != Definitions.AreaType.W)
				neighborsArr[UNeighborIndex] = matrix[i - 1][j];

		// right top diagonal
		if (i - 1 >= 0 && j + 1 <= maxLength)
			if (matrix[i - 1][j + 1].areaType != Definitions.AreaType.W &&
					matrix[i - 1][j].areaType != Definitions.AreaType.W &&
					matrix[i][j + 1].areaType != Definitions.AreaType.W)
				neighborsArr[RUNeighborIndex] = matrix[i - 1][j + 1];
		return neighborsArr;
	}
}
