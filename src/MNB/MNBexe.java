package MNB;
import java.util.*;

public class MNBexe {
	HashMap<Integer, HashMap<String, Double>> condProbRec = new HashMap<Integer, HashMap<String, Double>>();  
	public MNBexe(List<List<List<String>>> dataset) {
		List<String> rec = new ArrayList<String>();
		getArr(rec, dataset);
		int B = getB(dataset);
		
		for (int i = 0; i < dataset.size(); i++) {			
			HashMap<String, Double> cpi = new HashMap<String, Double>();
			List<String> rec2 = new ArrayList<String>();
			getArr2(rec2, dataset.get(i));
			int Tct2 = rec2.size();
			for (int j = 0; j< rec.size(); j++) {
				int Tct = getTct(dataset.get(i), rec.get(j));
				
				double condProb = getCB(Tct, Tct2, B);
				cpi.put(rec.get(j), condProb);
			}
			condProbRec.put(i, cpi);
		}
	}
	
	//Execute the MNB
	public double getAccuracy(List<List<List<String>>> dataset,
						   List<List<List<String>>> testset) {
		double totalLine = 0.0;
		double correctLine = 0.0;
		for (int i = 0; i < testset.size(); i++) {
			for (int j = 0; j < testset.get(i).size(); j++) {
				totalLine ++;
				
				if (i == getLineClass(dataset, testset.get(i).get(j))) {
					correctLine ++;
				}
			}
		}
		return correctLine/totalLine; 
	}
	//Execute single line
	public int getLineClass(List<List<List<String>>> dataset, List<String> line) {
		int result = -1;
		double max = -1000.0;
		
		for (int i = 0; i < dataset.size(); i++) {
			double temp = 1.0;
			//double temp2 = 0.0;
			
			List<String> rec3 = new ArrayList<String>();
			getArr2(rec3, dataset.get(i));
			int Tct02 = rec3.size();
			int B = getB(dataset);
			double d3 = (double)Tct02 + (double)B;
			
			for (int j = 0; j < line.size(); j++) {
				//System.out.println(condProbRec.get(i).get(line.get(j)));
				if (condProbRec.get(i).get(line.get(j)) == null) {
					//continue;
					
					double ttt = 1.0/d3;
					temp = temp * ttt;
					continue;
				}
				temp = temp * condProbRec.get(i).get(line.get(j)); 
				//System.out.println(condProbRec.get(i).get(line.get(j)));
				//temp2 = temp2 + Math.log(condProbRec.get(i).get(line.get(j)));
			}
			//System.out.println(getPri(dataset, i));
			temp = temp * getPri(dataset, i);
			//System.out.println(getPri(dataset, i));
			//temp2 = temp2 + Math.log(getPri(dataset, i));
			//System.out.println(temp2);
			if (temp > max) {
				max = temp;
			//if (temp2 > max) {	
				//max = temp2;
				result = i;
			}
		}
		return result;
	}
	
	//Get priority
	public double getPri(List<List<List<String>>> dataset, int index) {
		double countTotal = 0.0;
		double countIndex = 0.0;
		for (int i = 0; i < dataset.size(); i++) {
			for (int j = 0; j < dataset.get(i).size(); j++) {
				countTotal++;
			}
		}
		for (int j = 0; j < dataset.get(index).size(); j++) {
			countIndex++;
		}
		double result = countIndex/countTotal;
		return result;
	}
	
	//Filter the duplicate text and store the texts in an List
	public void getArr(List<String> rec, List<List<List<String>>> dataset) {	
		HashSet<String> strRec = new HashSet<String>();		
		for (int i = 0; i < dataset.size(); i++) {
			for (int j =0; j < dataset.get(i).size(); j++) {
				for (int k =0; k < dataset.get(i).get(j).size(); k++) {
					if (!strRec.contains(dataset.get(i).get(j).get(k))) {
						strRec.add(dataset.get(i).get(j).get(k));
						rec.add(dataset.get(i).get(j).get(k));
					}
				}
				
			}
		}
	}
	
	public void getArr2(List<String> rec, List<List<String>> datasetIth) {			
		for (int i = 0; i < datasetIth.size(); i++) {
			for (int j =0; j < datasetIth.get(i).size(); j++) {			
					rec.add(datasetIth.get(i).get(j));					
			}
		}
	}
	
	//Calculate the Tct for string str in class i
	public int getTct(List<List<String>> datasetIth, String str) {
		int count = 0;
		for (int i = 0; i < datasetIth.size(); i++) {
			for (int j =0; j < datasetIth.get(i).size(); j++) {
				if (datasetIth.get(i).get(j).equals(str)) {
					count ++;
				}
			}
		}
		return count;
	}
	
	//Calculate the condProb 
	public double getCB(int Tct, int Tct2, int B) {
		double d1 = (double) Tct + 1;
		double d2 = (double) Tct2 + B;		
		return d1/d2;
	}
	
	//Get B
	public int getB(List<List<List<String>>> dataset) {
		int count = 0;
		HashSet<String> rec = new HashSet<String>();
		for (int i = 0; i < dataset.size(); i++) {
			for (int j = 0; j < dataset.get(i).size(); j++) {
				for (int k = 0; k < dataset.get(i).get(j).size(); k++) {
					if (!rec.contains(dataset.get(i).get(j).get(k))) {
						rec.add(dataset.get(i).get(j).get(k));
						count ++;
					}
				}
			}
		}
		return count;
	}
	
//	public static void main(String[] args) {
////		List<List<List<String>>> dataset = new List<List<List<String>>>(); 
////		List<String> line1 = new List<String>(); 
////		List<String> line2 = new List<String>(); 
////		List<String> line3 = new List<String>(); 
////		List<String> line4 = new List<String>();
////		line1.add("Chinese");
////		line1.add("Beijing");
////		line1.add("Chinese");
////		line2.add("Chinese");
////		line2.add("Chinese");
////		line2.add("Shanghai");
////		line3.add("Chinese");
////		line3.add("Macao");
////		line4.add("Tokyo");
////		line4.add("Japan");
////		line4.add("Chinese");
////		
////		List<List<String>> class1 = new List<List<String>>();
////		class1.add(line1);
////		class1.add(line2);
////		class1.add(line3);
////		List<List<String>> class2 = new List<List<String>>();
////		class2.add(line4);
////		
////		dataset.add(class1);
////		dataset.add(class2);
////		
////		MNBexe test = new MNBexe(dataset);
////		
////		//System.out.println(test.getCB(5,8,6));
////		//test.getB(dataset);
////		
////		List<String> line5 = new List<String>();
////		line5.add("Chinese");
////		line5.add("Chinese");
////		line5.add("Chinese");
////		line5.add("Tokyo");
////		line5.add("Japan");
////		
////		test.getLineClass(dataset, line5);
////		
////		List<List<String>> class00 = new List<List<String>>();
////		class00.add(line5);
////		List<List<List<String>>> testset = new List<List<List<String>>>(); 
////		testset.add(class00);
////		//System.out.println(test.getAccuracy(dataset, testset));
////		System.out.println(Math.log(2));
//		
//		
//		
//		
//		
//		Read r = new Read ();
//		List<List<List<String>>> a = r.readTrainingFolder ("20news-bydate-train");
////		System.out.println(r.labels[1].getName() + "  " + r.labels[0].getName());
//		List<List<List<String>>> b = r.readTestFolder ("20news-bydate-test");
//		MNBexe m = new MNBexe (a);
//		System.out.println(m.getAccuracy (a, b));
//		for (int i=3; i<8; i++) {
//			System.out.println(r.labels[i].getName());
//		}
//		
//		System.out.println();
//		for (int i=3; i<8; i++) {
//			System.out.println(r.testLabels[i].getName());
//		}
//	}
}
