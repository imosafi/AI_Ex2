
/*
 * this class is used to define useful enums
 */
public class Definitions {

	public enum PolicyType {
		R(0), RD(1), D(2), LD(3), L(4), LU(5), U(6), RU(7), UNDEF(-1);

		private final int id;

		PolicyType(int id) {
			this.id = id;
		}
	}

	public enum AreaType {
		S(0), G(100), R(-1), D(-3), H(-10), W, UNDEF;

		private final int id;

		AreaType() {
			this.id = 0;
		}

		AreaType(int id) {
			this.id = id;
		}

		public int getValue() {
			return id;
		}
	}
}
