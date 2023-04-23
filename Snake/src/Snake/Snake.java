package Snake;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Snake {

	public static void main(String[] args) throws IOException {

		Generator gen = new Generator();
		List<Point> matrix = gen.generateMatrix(args[0]);
		Point currentPoint = matrix.get(0);
		for (Point i : matrix) {
			if (i.currentCoordinat.equalsIgnoreCase("1,11")) {
				currentPoint = i;
			}
		}
		System.out.println(currentPoint.currentCoordinat);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String s;
		while ((s = br.readLine()) != null) {
			matrix = gen.substructMatrix(matrix, s);

			String oldPoint = currentPoint.currentCoordinat;

			int sector1 = gen.getSectorSlot(matrix, 0, 12, 0, 12);
			int sector2 = gen.getSectorSlot(matrix, 12, 25, 0, 12);
			int sector3 = gen.getSectorSlot(matrix, 0, 12, 12, 25);
			int sector4 = gen.getSectorSlot(matrix, 12, 25, 12, 25);
			int currentSector = gen.getCurrentSector(oldPoint);

			String directionRank = gen.getDirectionRank(currentSector, sector1, sector2, sector3, sector4);

			String[] dRank = directionRank.split(",");

			for (String i : dRank) {
				if (gen.getPoint(i, currentPoint, matrix) != null) {
					currentPoint = gen.getPoint(i, currentPoint, matrix);
					System.out.println(currentPoint.currentCoordinat);
					break;
				}
			}
			matrix = gen.substructMatrix(matrix, oldPoint);
		}
	}

}
