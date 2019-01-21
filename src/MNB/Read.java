package MNB;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Read {
	
	File[] labels = null;
	Set<String> V = new HashSet<String> ();
	
//	Get the stopWords
	Set<String> getStopWords () {
		Set<String> stopWords = new HashSet<String> ();
		
		Scanner sc = null;
		try {
			sc = new Scanner(new File("StopWords.txt"));
			while (sc.hasNextLine()) {
				stopWords.add(sc.nextLine());
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (sc != null) {
				sc.close();
			}
		}
		return stopWords;
	}
	
//	Read text from a file
	List<String> read (File file) {
		List<String> point = new ArrayList<String> ();
		Set<String> stopWords = getStopWords ();
		Scanner sc = null;
		try {
			sc = new Scanner (file);
			
//			skip the head 
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				if (line.matches("^Lines.*$")) {
					break;
				}
			}
			
//			read
			while (sc.hasNext()) {
				String word = sc.next().replaceAll ("[^a-zA-Z0-9']", "").toLowerCase();
				if (stopWords.contains(word)) {
					continue;
				}  else {
					point.add(word);
					V.add(word);
				}
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (sc != null) {
				sc.close();
			}
		}
		
		return point;
	}
	
//	Read from class folder
	List<List<String>> readClassFolder (File file) {
		List<List<String>> dataSet = new ArrayList<List<String>> ();
		File[] files = file.listFiles();
		for (File f : files) {
			dataSet.add(read (f));
		}
		return dataSet;
	}
	
//	Read from training set root folder
	List<List<List<String>>> readTrainingFolder (String path) {
		List<List<List<String>>> trainingData = new ArrayList<List<List<String>>> ();
		File file = new File (path);
		labels = file.listFiles();
		for (int i=4; i<9; i++) {
			trainingData.add(readClassFolder (labels[i]));
		}
		return trainingData;
	} 
	
//	Read from test set root folder
	List<List<List<String>>> readTestFolder (String path) {
		List<List<List<String>>> testData = new ArrayList<List<List<String>>> ();
		File file = new File (path);
		File[] files = file.listFiles();
		for (int i=4; i<9; i++) {
			for (File f : files) {
				if (f.getName().equals(labels[i].getName())) {
					testData.add(readClassFolder (f));
				}
			}
		}
		return testData;
	}
	
	public static void main(String[] args) {
		Read r = new Read ();
		List<List<List<String>>> trainingSet = r.readTrainingFolder (args[0]);
		List<List<List<String>>> testSet = r.readTestFolder(args[1]);
		
		Model m = new Model (trainingSet, r.V);
		System.out.print("The selected sub-folders are:\n");
		for (int i=4; i<9; i++) {
			System.out.println(r.labels[i].getName());
		}
		System.out.println("\nThe accuracy of the test portion is " + m.getAccuracy(testSet, r.V));
	}
}
