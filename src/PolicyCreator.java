
/*
 * this class is responsible of doing iterations until the utility converge
 * then find the optimal policy (we use value iteration in this process)
 */
import java.util.Arrays;

public class PolicyCreator {
	State[][] areaMatrix;
	double[][] rewardMatrix;
	double[][] utilityMatrix;
	double[][] tempUtilityMatrix;
	double matrixMaxError;
	int size;
	double gamma; // set to a better value, and change value in each iteration.
					// should be double?
	public Definitions.PolicyType[][] policyMatrix;
	int[] bla1;
	int[] bla2;

	public PolicyCreator() {

	}

	public void initializeBoard(int size, char[][] input) {
		this.size = size;
		areaMatrix = new State[size][size];
		rewardMatrix = new double[size][size];
		policyMatrix = new Definitions.PolicyType[size][size];
// tempUtilityMatrix = new int[size][size];
		gamma = 1;
		matrixMaxError = 0.005;

// bla1 = new int[3];
// bla2 = new int[3];
// Arrays.fill(bla1, 5);

		SetAreaMatrix(input);
		SetRewardMatrix();
		SetUtilityMatrix();
		setNeightbors();
	}

	private void SetAreaMatrix(char[][] input) {
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				areaMatrix[i][j] = new State(i, j, input[i][j]);

	}

	private void SetUtilityMatrix() {
		utilityMatrix = deepCopyIntMatrix(rewardMatrix);
		tempUtilityMatrix = deepCopyIntMatrix(rewardMatrix);
	}

	private void SetRewardMatrix() {
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				rewardMatrix[i][j] = areaMatrix[i][j].areaType.getValue();
	}

	public void setNeightbors() {
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				if (isNodeNotWaterNorGoal(i, j))
					areaMatrix[i][j].neighbors = StateUtils.GetValidNeighbors(areaMatrix, i, j);
	}

	public static double[][] deepCopyIntMatrix(double[][] input) {
		if (input == null)
			return null;
		double[][] result = new double[input.length][];
		for (int r = 0; r < input.length; r++) {
			result[r] = input[r].clone();
		}
		return result;
	}

	public void consolidateUtility() {
		int temp = 0;
		do {
			utilityMatrix = deepCopyIntMatrix(tempUtilityMatrix);
// tempUtilityMatrix.clone();
// bla2 = bla1.clone();
			for (int i = 0; i < size; i++)
				for (int j = 0; j < size; j++)
					if (isNodeNotWaterNorGoal(i, j))
						tempUtilityMatrix[i][j] = rewardMatrix[i][j] + GetMaxProfitAction(i, j);
// bla1[2]++;
		}
		while (!IsUtilityMatrixesStable());

// while (!IsUtilityMatrixesStable()) {
// utilityMatrix = tempUtilityMatrix.clone();
// for (int i = 0; i < size; i++)
// for (int j = 0; j < size; j++)
// if (isNodeNotWaterNorGoal(i, j))
// tempUtilityMatrix[i][j] = rewardMatrix[i][j] + GetMaxProfitAction(i, j);

// if (temp++ == 10) // change
// break;
// }

	}

	private boolean IsUtilityMatrixesStable() {
		double[][] utilitySubMatrix = deepCopyIntMatrix(tempUtilityMatrix);
		double maxError = 0;
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++) {
				utilitySubMatrix[i][j] -= utilityMatrix[i][j];
				if (maxError < utilitySubMatrix[i][j])
					maxError = utilitySubMatrix[i][j];
			}
		return maxError <= matrixMaxError;
	}

	// double check this function
	private double GetMaxProfitAction(int i, int j) {
		double[] actionsValues = new double[StateUtils.NeighborsSize];
		Arrays.fill(actionsValues, Integer.MIN_VALUE);

		// right neighbor
		if (areaMatrix[i][j].neighbors[StateUtils.RNeighborIndex] != null)
			actionsValues[StateUtils.RNeighborIndex] = 1 * utilityMatrix[i][j + 1];

		// right down neighbor
		if (areaMatrix[i][j].neighbors[StateUtils.RDNeighborIndex] != null)
			actionsValues[StateUtils.RDNeighborIndex] = 0.7 * utilityMatrix[i + 1][j + 1]
					+ 0.15 * utilityMatrix[i][j + 1] + 0.15 * utilityMatrix[i + 1][j];

		// down neighbor
		if (areaMatrix[i][j].neighbors[StateUtils.DNeighborIndex] != null)
			actionsValues[StateUtils.DNeighborIndex] = 1 * utilityMatrix[i + 1][j];

		// left down neighbor
		if (areaMatrix[i][j].neighbors[StateUtils.LDNeighborIndex] != null)
			actionsValues[StateUtils.LDNeighborIndex] = 0.7 * utilityMatrix[i + 1][j - 1]
					+ 0.15 * utilityMatrix[i + 1][j] + 0.15 * utilityMatrix[i][j - 1];

		// left neighbor
		if (areaMatrix[i][j].neighbors[StateUtils.LNeighborIndex] != null)
			actionsValues[StateUtils.LNeighborIndex] = 1 * utilityMatrix[i][j - 1];

		// left up neighbor
		if (areaMatrix[i][j].neighbors[StateUtils.LUNeighborIndex] != null)
			actionsValues[StateUtils.LUNeighborIndex] = 0.7 * utilityMatrix[i - 1][j - 1]
					+ 0.15 * utilityMatrix[i][j - 1] + 0.15 * utilityMatrix[i - 1][j];

		// up neighbor
		if (areaMatrix[i][j].neighbors[StateUtils.UNeighborIndex] != null)
			actionsValues[StateUtils.UNeighborIndex] = 1 * utilityMatrix[i - 1][j];

		// up neighbor
		if (areaMatrix[i][j].neighbors[StateUtils.RUNeighborIndex] != null)
			actionsValues[StateUtils.RUNeighborIndex] = 0.7 * utilityMatrix[i - 1][j + 1]
					+ 0.15 * utilityMatrix[i - 1][j] + 0.15 * utilityMatrix[i][j + 1];

		double maxValue = Double.NEGATIVE_INFINITY;
		for (int k = 0; k < StateUtils.NeighborsSize; k++)
			if (actionsValues[k] > maxValue)
				maxValue = actionsValues[k];
		return maxValue;
	}

	private boolean isNodeNotWaterNorGoal(int i, int j) {
		return areaMatrix[i][j].areaType != Definitions.AreaType.W && areaMatrix[i][j].areaType != Definitions.AreaType.G;
	}

	public void findOptimalPolicy() {
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				if (isNodeNotWaterNorGoal(i, j))
					policyMatrix[i][j] = getBestPolicyStep(i, j);
		printPolicyMatrix(policyMatrix);
	}

	private void printPolicyMatrix(Definitions.PolicyType[][] policyMatrix) {
		for (int i = 0; i < policyMatrix.length; i++) {
			for (int j = 0; j < policyMatrix.length; j++) {
				if (isNodeNotWaterNorGoal(i, j))
					System.out.print(policyMatrix[i][j].toString() + " ");
			}
			System.out.println();
		}
	}

	private Definitions.PolicyType getBestPolicyStep(int i, int j) {
		Definitions.PolicyType bestPolicy = Definitions.PolicyType.UNDEF;
		double bestPolicyValue = Double.NEGATIVE_INFINITY;
		double tempValue = Double.NEGATIVE_INFINITY;

		// right neighbor
		if (areaMatrix[i][j].neighbors[StateUtils.RNeighborIndex] != null) {
			tempValue = 1 * utilityMatrix[i][j + 1];
			if (tempValue > bestPolicyValue) {
				bestPolicyValue = tempValue;
				bestPolicy = Definitions.PolicyType.R;
			}
		}

		// right down neighbor
		if (areaMatrix[i][j].neighbors[StateUtils.RDNeighborIndex] != null) {
			tempValue = 0.7 * utilityMatrix[i + 1][j + 1]
					+ 0.15 * utilityMatrix[i][j + 1] + 0.15 * utilityMatrix[i + 1][j];
			if (tempValue > bestPolicyValue) {
				bestPolicyValue = tempValue;
				bestPolicy = Definitions.PolicyType.RD;
			}
		}

		// down neighbor
		if (areaMatrix[i][j].neighbors[StateUtils.DNeighborIndex] != null) {
			tempValue = 1 * utilityMatrix[i + 1][j];
			if (tempValue > bestPolicyValue) {
				bestPolicyValue = tempValue;
				bestPolicy = Definitions.PolicyType.D;
			}
		}

		// left down neighbor
		if (areaMatrix[i][j].neighbors[StateUtils.LDNeighborIndex] != null)
			tempValue = 0.7 * utilityMatrix[i + 1][j - 1]
					+ 0.15 * utilityMatrix[i + 1][j] + 0.15 * utilityMatrix[i][j - 1];
		if (tempValue > bestPolicyValue) {
			bestPolicyValue = tempValue;
			bestPolicy = Definitions.PolicyType.LD;
		}

		// left neighbor
		if (areaMatrix[i][j].neighbors[StateUtils.LNeighborIndex] != null) {
			tempValue = 1 * utilityMatrix[i][j - 1];
			if (tempValue > bestPolicyValue) {
				bestPolicyValue = tempValue;
				bestPolicy = Definitions.PolicyType.L;
			}
		}

		// left up neighbor
		if (areaMatrix[i][j].neighbors[StateUtils.LUNeighborIndex] != null) {
			tempValue = 0.7 * utilityMatrix[i - 1][j - 1]
					+ 0.15 * utilityMatrix[i][j - 1] + 0.15 * utilityMatrix[i - 1][j];
			if (tempValue > bestPolicyValue) {
				bestPolicyValue = tempValue;
				bestPolicy = Definitions.PolicyType.LU;
			}
		}

		// up neighbor
		if (areaMatrix[i][j].neighbors[StateUtils.UNeighborIndex] != null) {
			tempValue = 1 * utilityMatrix[i - 1][j];
			if (tempValue > bestPolicyValue) {
				bestPolicyValue = tempValue;
				bestPolicy = Definitions.PolicyType.U;
			}
		}

		// right up neighbor
		if (areaMatrix[i][j].neighbors[StateUtils.RUNeighborIndex] != null) {
			tempValue = 0.7 * utilityMatrix[i - 1][j + 1]
					+ 0.15 * utilityMatrix[i - 1][j] + 0.15 * utilityMatrix[i][j + 1];
			if (tempValue > bestPolicyValue) {
				bestPolicyValue = tempValue;
				bestPolicy = Definitions.PolicyType.RU;
			}
		}

		return bestPolicy;
	}

}
