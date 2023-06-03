package Snake;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Snake {

	public static void main(String[] args) throws IOException {
		Generator gen = new Generator(args[0]);

		System.out.println(gen.getCurrentPoint());

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String s;
		while ((s = br.readLine()) != null) {
			gen.substructMatrixByCoordinat(s);

			System.out.println(gen.getNextPoint());

			gen.substructMatrixByPrevPoint();
		}
	}

}
