package Snake;

import java.util.ArrayList;
import java.util.List;

public class Generator {

	private final static String START_POSITION = "1,11";
	private final static String RIGHT = "RIGHT";
	private final static String LEFT = "LEFT";
	private final static String DOWN = "DOWN";
	private final static String UP = "UP";

	private List<Point> matrix = new ArrayList<>();

	private String directionRank;

	private Point previousPoint;
	private Point currentPoint;
	private int currentSector;

	private List<String> enemySnakeCoordinats = new ArrayList<>();
	private String enemySnakeCoordinat;
	private String enemySnakeDirection;

	private int slot1;
	private int slot2;
	private int slot3;
	private int slot4;

	public Generator(String io) {
		this.generateMatrix(io);
		this.setStartPoint();

		this.setSectorSlots();
		this.setDirectionRank();
	}

	public String getCurrentPoint() {
		return this.currentPoint.currentCoordinat;
	}

	public String getNextPoint() {
		this.setDirectionRank();

		for (String direction : this.directionRank.split(",")) {
			if (this.setAvailPoint(direction)) {
				this.setCurrentSector();
				return this.currentPoint.currentCoordinat;
			}
		}
		return null;
	}

	public void substructMatrixByPrevPoint() {
		for (Point i : this.matrix) {
			if (i.currentCoordinat.equalsIgnoreCase(this.previousPoint.currentCoordinat)) {
				continue;
			}

			if (i.right != null && i.right.equalsIgnoreCase(this.previousPoint.currentCoordinat)) {
				i.right = null;
			}

			if (i.left != null && i.left.equalsIgnoreCase(this.previousPoint.currentCoordinat)) {
				i.left = null;
			}

			if (i.down != null && i.down.equalsIgnoreCase(this.previousPoint.currentCoordinat)) {
				i.down = null;
			}

			if (i.up != null && i.up.equalsIgnoreCase(this.previousPoint.currentCoordinat)) {
				i.up = null;
			}
		}

		this.setSectorSlots();
	}

	public void substructMatrixByCoordinat(String p) {
		String[] arrPos = p.split(",");
		int x = Integer.parseInt(arrPos[0]);
		int y = Integer.parseInt(arrPos[1]);

		String[] arrPos2 = this.enemySnakeCoordinat.split(",");
		int prevx = Integer.parseInt(arrPos2[0]);
		int prevy = Integer.parseInt(arrPos2[1]);

		if (x == prevx && y > prevy) {
			this.enemySnakeDirection = DOWN;
		} else if (x == prevx && y < prevy) {
			this.enemySnakeDirection = UP;
		} else if (x > prevx && y == prevy) {
			this.enemySnakeDirection = RIGHT;
		} else if (x < prevx && y == prevy) {
			this.enemySnakeDirection = LEFT;
		}

		this.enemySnakeCoordinats.add(p);
		this.enemySnakeCoordinat = p;

		for (Point i : this.matrix) {
			if (i.currentCoordinat.equalsIgnoreCase(p)) {
				continue;
			}

			if (i.right != null && i.right.equalsIgnoreCase(p)) {
				i.right = null;
			}

			if (i.left != null && i.left.equalsIgnoreCase(p)) {
				i.left = null;
			}

			if (i.down != null && i.down.equalsIgnoreCase(p)) {
				i.down = null;
			}

			if (i.up != null && i.up.equalsIgnoreCase(p)) {
				i.up = null;
			}
		}

		this.setSectorSlots();
	}

	private void setSectorSlots() {
		this.slot1 = this.getSectorSlot(0, 12, 0, 12);
		this.slot2 = this.getSectorSlot(12, 25, 0, 12);
		this.slot3 = this.getSectorSlot(0, 12, 12, 25);
		this.slot4 = this.getSectorSlot(12, 25, 12, 25);
	}

	private void setStartPoint() {
		this.currentPoint = this.matrix.get(0);
		for (Point i : this.matrix) {
			if (i.currentCoordinat.equalsIgnoreCase(START_POSITION)) {
				this.currentPoint = i;
			}
		}
		this.setCurrentSector();
	}

	private boolean setAvailPoint(String d) {
		if (this.currentPoint.right != null && d.equals(RIGHT)) {
			for (Point i : this.matrix) {
				if (this.currentPoint.right.equals(i.currentCoordinat)) {
					this.previousPoint = this.currentPoint;
					this.currentPoint = i;
					return true;
				}
			}
		} else if (this.currentPoint.left != null && d.equals(LEFT)) {
			for (Point i : this.matrix) {
				if (this.currentPoint.left.equals(i.currentCoordinat)) {
					this.previousPoint = this.currentPoint;
					this.currentPoint = i;
					return true;
				}
			}
		} else if (this.currentPoint.down != null && d.equals(DOWN)) {
			for (Point i : this.matrix) {
				if (this.currentPoint.down.equals(i.currentCoordinat)) {
					this.previousPoint = this.currentPoint;
					this.currentPoint = i;
					return true;
				}
			}
		} else if (this.currentPoint.up != null && d.equals(UP)) {
			for (Point i : this.matrix) {
				if (this.currentPoint.up.equals(i.currentCoordinat)) {
					this.previousPoint = this.currentPoint;
					this.currentPoint = i;
					return true;
				}
			}
		}

		return false;
	}

	private void setDirectionRank() {
		if (this.slot1 + this.slot2 >= this.slot3 + this.slot4) {
			this.directionRank = RIGHT + "," + LEFT + "," + UP + "," + DOWN;
		} else {
			this.directionRank = RIGHT + "," + LEFT + "," + DOWN + "," + UP;
		}

	}

	private void generateMatrix(String p) {

		List<int[]> lstPos = new ArrayList<>();

		String[] lstStrPos = p.split(";");
		for (String strPos : lstStrPos) {
			String[] arrPos = strPos.split(",");
			if (arrPos.length == 2) {
				lstPos.add(new int[] { Integer.parseInt(arrPos[0]), Integer.parseInt(arrPos[1]) });
			}
		}

		for (int i = 0; i < 25; i++) {
			for (int j = 0; j < 25; j++) {
				Point cor = new Point();

				boolean isFound = false;

				for (int[] k : lstPos) {
					if (k[0] == i && k[1] == j) {
						isFound = true;
					}
				}

				if (isFound)
					continue;

				cor.currentCoordinat = (i + "," + j);

				if ((j + 1) <= 24) {
					for (int[] k : lstPos) {
						if (k[0] == i && k[1] == j + 1) {
							isFound = true;
						}
					}

					if (isFound) {
						isFound = false;
					} else {
						cor.down = (i + "," + (j + 1));
					}
				}

				if ((j - 1) >= 0) {
					for (int[] k : lstPos) {
						if (k[0] == i && k[1] == j - 1) {
							isFound = true;
						}
					}

					if (isFound) {
						isFound = false;
					} else {
						cor.up = (i + "," + (j - 1));
					}

				}

				if ((i + 1) <= 24) {
					for (int[] k : lstPos) {
						if (k[0] == i + 1 && k[1] == j) {
							isFound = true;
						}
					}

					if (isFound) {
						isFound = false;
					} else {
						cor.right = ((i + 1) + "," + j);
					}

				}

				if ((i - 1) >= 0) {
					for (int[] k : lstPos) {
						if (k[0] == i - 1 && k[1] == j) {
							isFound = true;
						}
					}

					if (!isFound) {
						cor.left = ((i - 1) + "," + j);
					}

				}

				this.matrix.add(cor);
			}
		}
	}

	private void setCurrentSector() {
		String[] arrPos = this.currentPoint.currentCoordinat.split(",");
		if (arrPos.length == 2) {
			int x = Integer.parseInt(arrPos[0]);
			int y = Integer.parseInt(arrPos[1]);

			if (x > 0 && x < 13 && y > 0 && y < 13) {
				this.currentSector = 1;
			} else if (x >= 13 && x < 25 && y > 0 && y < 13) {
				this.currentSector = 2;
			} else if (x > 0 && x < 13 && y >= 13 && y < 25) {
				this.currentSector = 3;
			} else {
				this.currentSector = 4;
			}
		}
	}

	private int getSectorSlot(int sx, int ex, int sy, int ey) {
		int count = 0;

		for (int i = sx; i < ex; i++) {
			for (int j = sy; j < ey; j++) {
				for (Point p : this.matrix) {
					if (p.currentCoordinat.equalsIgnoreCase(i + "," + j)) {
						count++;
					}
				}
			}
		}

		return count;
	}
}
