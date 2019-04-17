package rover;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

public class Start {

	int x = 2;
	static Random r = new Random();
	static LinkedHashMap<int[], String> mars;

	public static void init() {
		mars = new LinkedHashMap<>();
		int x = 80;
		int y = 20;
		int rx = x / 2;
		int ry = y / 2;
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				int[] p = new int[] { i, j };
				if (r.nextDouble() < 0.25 && !(rx == i && ry == j))
					mars.put(p, "#");
			}
		}
		mars.put(new int[] { rx, ry }, "north");
	}

	public static int[] maximum(Set<int[]> set) {
		int[] x = new int[2];
		for (int[] e : set) {
			if (e[0] > x[0])
				x[0] = e[0];
			if (e[1] > x[1])
				x[1] = e[1];
		}
		return x;
	}

	public static String get(Map<int[], String> kloetze, int[] p) {
		Set<Entry<int[], String>> entrySet = kloetze.entrySet();
		for (Entry<int[], String> entry : entrySet) {
			if (entry.getKey()[0] == p[0] && entry.getKey()[1] == p[1])
				return entry.getValue();
		}
		return null;
	}

	public static void print() {
		Set<int[]> keySet = mars.keySet();
		for (int[] e : keySet) {
		if (e[0] == 39 && e[1] == 10)
		 System.err.println(mars.get(e) + " " + e.hashCode());
		 }

		int[] max = maximum(mars.keySet());
		for (int j = 0; j < max[1]; j++) {
			for (int i = 0; i < max[0]; i++) {
				// System.out.println(i + "," + j + ": " + get(mars, new int[] { i, j }));

				if (get(mars, new int[] { i, j }) == null) {
					System.out.print(" ");
					continue;
				}
				if (get(mars, new int[] { i, j }).equals("#"))
					System.out.print("#");
				else if (get(mars, new int[] { i, j }).equals("north"))
					System.out.print("^");
				else if (get(mars, new int[] { i, j }).equals("south"))
					System.out.print("V");
				else if (get(mars, new int[] { i, j }).equals("east"))
					System.out.print(">");
				if (get(mars, new int[] { i, j }).equals("west"))
					System.out.print("<");

			}
			System.out.println();
		}
		for (int i = 0; i < max[0]; i++) {
			System.out.print("=");
		}
		System.out.println();
	}

	public static void main(String[] args) {

		if (args.length > 1) {
			long seed = Long.parseLong(args[1]);
			r.setSeed(seed);
			System.out.println("Seed: " + seed);
		}
		init();
		String pg = args[0];
		print();
		for (int i = 0; i < pg.length(); i++) {
			movement(pg.charAt(i));
			print();

		}
	}

	public static void movement(char command) {
		if (command == 'f') {
			int[] position = findeRover();
			if (get(mars, position).equals("north"))
				position[1]--;
			else if (get(mars, position).equals("south"))
				position[1]++;
			else if (get(mars, position).equals("east"))
				position[0]++;
			else if (get(mars, position).equals("west"))
				position[0]--;
		} else if (command == 'b') {
			int[] position = findeRover();
			if (get(mars, position).equals("south"))
				position[1]--;
			else if (get(mars, position).equals("north"))
				position[1]++;
			else if (get(mars, position).equals("west"))
				position[0]++;
			else if (get(mars, position).equals("east"))
				position[0]--;
		} else if (command == 'l') {
			int[] p = findeRover();
			if (get(mars, p).equals("north"))
				mars.put(p, "west");
			else if (get(mars, p).equals("south"))
				mars.put(p, "east");
			else if (get(mars, p).equals("east"))
				mars.put(p, "north");
			else if (get(mars, p).equals("west"))
				mars.put(p, "south");
		} else if (command == 'r') {
			int[] p = findeRover();
			if (get(mars, p).equals("west"))
				mars.put(p, "north");
			else if (get(mars, p).equals("east"))
				mars.put(p, "south");
			else if (get(mars, p).equals("north"))
				mars.put(p, "east");
			else if (get(mars, p).equals("south"))
				mars.put(p, "west");
		}

	}

	private static int[] findeRover() {
		
		Set<Entry<int[], String>> entrySet = mars.entrySet();
		for (Entry<int[], String> entry : entrySet) {

			if (entry.getValue() != null && !entry.getValue().equals("#")){
				System.out.println("Das ist die Value " + entry.getValue());
				return entry.getKey();
			}
		}
		throw new IllegalStateException("Rover missing in action");
	}


}
