package Snake;

import java.util.ArrayList;
import java.util.List;

public class Generator {

	public Point getPoint(String d, Point currentPoint, List<Point> matrix) {
		if (currentPoint.right != null && d.equals("RIGHT")) {
			for (Point i : matrix) {
				if (currentPoint.right.equals(i.currentCoordinat)) {
					return i;
				}
			}
		} else if (currentPoint.left != null && d.equals("LEFT")) {
			for (Point i : matrix) {
				if (currentPoint.left.equals(i.currentCoordinat)) {
					return i;
				}
			}
		} else if (currentPoint.down != null && d.equals("DOWN")) {
			for (Point i : matrix) {
				if (currentPoint.down.equals(i.currentCoordinat)) {
					return i;
				}
			}
		} else if (currentPoint.up != null && d.equals("UP")) {
			for (Point i : matrix) {
				if (currentPoint.up.equals(i.currentCoordinat)) {
					return i;
				}
			}
		}

		return null;
	}

	public String getDirectionRank(int sector, int slot1, int slot2, int slot3, int slot4) {

		if (slot1 + slot2 >= slot3 + slot4) {
			return "RIGHT,LEFT,UP,DOWN";
		} else {
			return "RIGHT,LEFT,DOWN,UP";
		}


	}

	public int getCurrentSector(String coordinat) {
		int ret = 0;
		String[] arrPos = coordinat.split(",");
		if (arrPos.length == 2) {
			int x = Integer.parseInt(arrPos[0]);
			int y = Integer.parseInt(arrPos[1]);

			if (x > 0 && x < 13 && y > 0 && y < 13) {
				ret = 1;
			} else if (x >= 13 && x < 25 && y > 0 && y < 13) {
				ret = 2;
			} else if (x > 0 && x < 13 && y >= 13 && y < 25) {
				ret = 3;
			} else {
				ret = 4;
			}
		}
		return ret;
	}

	public int getSectorSlot(List<Point> point, int sx, int ex, int sy, int ey) {
		int count = 0;

		for (int i = sx; i < ex; i++) {
			for (int j = sy; j < ey; j++) {
				for (Point p : point) {
					if (p.currentCoordinat.equalsIgnoreCase(i + "," + j)) {
						count++;
					}
				}
			}
		}

		return count;
	}

	public List<Point> substructMatrix(List<Point> point, String p) {
		List<Point> ret = new ArrayList<>();
		for (Point i : point) {
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

			ret.add(i);
		}

		return ret;
	}

	public List<Point> generateMatrix(String p) {

		List<int[]> lstPos = new ArrayList<>();

		String[] lstStrPos = p.split(";");
		for (String strPos : lstStrPos) {
			String[] arrPos = strPos.split(",");
			if (arrPos.length == 2) {
				lstPos.add(new int[] { Integer.parseInt(arrPos[0]), Integer.parseInt(arrPos[1]) });
			}
		}

		List<Point> matrix = new ArrayList<>();
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

				matrix.add(cor);
			}
		}

		return matrix;
	}

}
