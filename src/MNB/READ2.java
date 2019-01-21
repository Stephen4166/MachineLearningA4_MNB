package MNB;
import java.io.*;
import java.util.*;
import java.lang.*;

public class READ2 {
	//public void read(ArrayList<ArrayList<String>> str, String dir) {
	public void read(ArrayList<ArrayList<String>> str) {
		HashSet<String> set = getSet();
		Scanner sc2 = null;
	    try {
	    	sc2 = new Scanner(new File("D:\\CS17Fall\\Machine Learning\\Assignment\\Assignment4\\20news-bydate\\20news-bydate-test\\comp.graphics\\38758"));
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();  
	    }
	    
	    Scanner s2 = new Scanner(sc2.nextLine());

	    while (sc2.hasNextLine()) {
	    	ArrayList<String> line = new ArrayList<String>(); 
	    	s2 = new Scanner(sc2.nextLine());
	        while (s2.hasNext()) {
	            String s = s2.next();
	            //System.out.println(s);
	            //String lowS = s.toLowerCase();
	            if (set.contains(s.toLowerCase())) {
	            	continue;
	            }
	            
	            String s03 = s.replaceAll("[^A-Za-z]","");
	            line.add(s03.toLowerCase());
	        }
	        str.add(line);
	    }
	    
	    eliminate(str);
	}

	public void eliminate(ArrayList<ArrayList<String>> str) {
		int index = indexLine(str); 
		for (int i = 0; i <= index; i++) {
			str.remove(0);
		}
	}
	
	public int indexLine(ArrayList<ArrayList<String>> str) {
		for (int i = 0; i < str.size(); i++) {
			if (str.get(i).get(0).equals("lines")) {
				return i;
			}
		}
		return -1;
	}
	
	public void print(ArrayList<ArrayList<String>> str) {
		for (int i = 0; i < str.size(); i++) {
			for (int j =0; j < str.get(i).size(); j++) {
				System.out.print(str.get(i).get(j) + "   ");
			}
			System.out.println("");
		}
	}
	
	public HashSet<String> getSet() {
		HashSet<String> set = new HashSet<String> ();
		ArrayList<ArrayList<String>> str = new ArrayList<ArrayList<String>>();
		 
		Scanner sc2 = null;
	    try {
	    	
	        sc2 = new Scanner(new File("D:\\CS17Fall\\Machine Learning\\Assignment\\Assignment4\\stopwords.txt"));
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();  
	    }
	    
	    Scanner s2 = null;
		while (sc2.hasNextLine()) {
		    	ArrayList<String> line = new ArrayList<String>(); 
		    	s2 = new Scanner(sc2.nextLine());
		        while (s2.hasNext()) {
		            String s = s2.next();
		            line.add(s);
		        }
		        str.add(line);
		    }
		
		for (int i = 0; i < str.size(); i++) {
			for (int j = 0; j < str.get(i).size(); j++) {
				set.add(str.get(i).get(j));
			}			
		}
		return set;
	}
	
	public void readAllFile (ArrayList<ArrayList<String>> allData, String directory) {
		ArrayList<String> txtName = new ArrayList<String>();
		File folder = new File(directory);
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
		    txtName.add(listOfFiles[i].getName());
		}
		for (int i = 0; i < txtName.size(); i++) {
			concateStr(directory, "//");
			concateStr(directory, txtName.get(i));
			ArrayList<ArrayList<String>> eachData = new ArrayList<ArrayList<String>>();
//			read(eachData, directory);
			concateArrayList(allData, eachData);
		}
	}
	
	public void concateStr(String str1, String str2) {
		str1 = str1+str2;
	}
	
	public void concateArrayList(ArrayList<ArrayList<String>> arr1, 
								 ArrayList<ArrayList<String>> arr2) {
		for (int i = 0; i < arr2.size(); i++) {
			arr1.add(arr2.get(i));
		}
	}
	public static void main(String[] args) {
		READ2 r = new READ2();
		ArrayList<String> str = new ArrayList<String>();
		ArrayList<ArrayList<String>> allData = new ArrayList<ArrayList<String>>();
		r.read(allData);
		r.print(allData);
		r.print(allData);
	}
}
