import java.util.*;
import java.io.*;
import javax.swing.*;
public class Test {

	public static void main(String[] args) {
		try {
		FileWriter fw = new FileWriter("test.txt");
		fw.write("Hello How are you");
		
		fw.close();
		Scanner s = new Scanner(new FileInputStream("test.txt"));
		String[] out = s.
		}
		catch(FileNotFoundException e) {
			
		}
		catch(IOException d) {
			
		}
		
	}
}
