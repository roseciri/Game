package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class IOUtils {

	private IOUtils() {
	}

	public static String read() {
		var in = new InputStreamReader(System.in);
		var br = new BufferedReader(in);
		try {
			return br.readLine();
		} catch (IOException e) {
			return "";
		}
	}
}
