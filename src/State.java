/*
 * this class describes a state which includes neighbors, indexes and the areaType
 */

public class State {
	State[] neighbors;
	int i, j;
	Definitions.AreaType areaType;

	public State(int i, int j, char c) {
		this.i = i;
		this.j = j;
		areaType = StateUtils.charToAreaDictionary.get(c);
	}
}
