package MNB;

import java.util.*;

public class Model {
	int numLabels;
	int N;
	int[] Nc;
	double[] prior_c;
	List<Map<String, Integer>> Tct = new ArrayList<Map<String, Integer>> ();
	int[] countTokens;
	List<Map<String, Double>> condprob = new ArrayList<Map<String, Double>> ();
	List<Set<String>> Vi = new ArrayList<Set<String>> ();
	
	Model(List<List<List<String>>> trainingSet, Set<String> V) {
		numLabels = trainingSet.size();
		Nc = new int [numLabels];
		countTokens = new int[numLabels];
		
		N = 0;
		for (int i=0; i<trainingSet.size(); i++) {
			N += trainingSet.get(i).size();
		}
		
		prior_c = new double [N];
		for (int i=0; i<numLabels; i++) {
			Nc[i] = trainingSet.get(i).size();
			prior_c[i] = (double)Nc[i] / N; 
		}
		
	
//		For each class:
		for (int i=0; i<numLabels; i++) {
	
			int tempCount = 0;
			Set<String> tempV = new HashSet<String> ();
			List<List<String>> Dc = trainingSet.get(i);
			Map<String, Integer> temp = new HashMap<String, Integer> ();
			
			for (int j=0; j<Dc.size(); j++) {
				for (String t : Dc.get(j)) {
					tempCount ++;
					tempV.add(t);
					if (temp.containsKey(t)) {
						temp.put(t, temp.get(t)+1);
					} else {
						temp.put(t, 1);
					}
				}
			}
			
			Vi.add(tempV);
			
			countTokens[i] = tempCount;
//			Add into the Tct collection;
			Tct.add(temp);
			
//			compute the condprob
			Map<String, Double> tempCondProb = new HashMap<String, Double> ();
			for (String s : V) {
				if (Vi.get(i).contains(s)) {
					double value = (double)Tct.get(i).get(s) / (double)(countTokens[i] + V.size());
					tempCondProb.put(s, value);
				} else {
					double value = 1.0 / (double) (countTokens[i]+V.size());
					tempCondProb.put(s, value);
				}
			}
			condprob.add(tempCondProb);
		}
		
	}
	
	boolean applyMNB (List<String> test, Set<String> V, int index) {
		double[] score = new double[numLabels];
		for (int i=0; i<score.length; i++) {
			score[i] = 0;
		}
		
//		Extract tokens from doc
		Set<String> W = new HashSet<String> ();
		for (String s : test) {
			W.add(s);
		}
		
		for (int i=0; i<numLabels; i++) {
			score[i] = Math.log(prior_c[i]);
			for (String s : W) {
				if (V.contains(s)) {
					score[i] += Math.log(condprob.get(i).get(s));
				} else {
					score[i] += Math.log(1.0 / (countTokens[i]+V.size()));      
				}
			
			}
		}
		
		int maxIndex = 0;
		double max = score[0];
		for (int i=0; i<numLabels; i++) {
			if (score[i] > max) {
				maxIndex = i;
				max = score[i];
			} 
		}
		
		if (maxIndex == index) {
			return true;
		} else {
			return false;
		}
		
	}
	
	double getAccuracy (List<List<List<String>>> testSet, Set<String> V) {
		int countIns = 0;
		int countP = 0;
		for (int i=0; i<testSet.size(); i++) {
			countIns += testSet.get(i).size();
			
			for (int j=0; j<testSet.get(i).size(); j++) {
				//List<String> instance = testSet.get(i).get(j);
				
				if (applyMNB (testSet.get(i).get(j), V, i)) {
				//if (applyMNB (instance, V, i)) {
					countP ++;
				}
			}
		}
		
		double accuracy = (double) countP / countIns;
		return accuracy;
	}

}
