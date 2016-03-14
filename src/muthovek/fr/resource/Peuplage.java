package muthovek.fr.resource;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;



public class Peuplage {
	
	public List<HashMapContext> fillContextInt(Set<String> setTousMotsFrancais, LinkedList<String> texteEntierParMot,int tailleDuContexte){
		List<HashMapContext>mapDeContextes=new ArrayList<HashMapContext>();
		int m=0;
		int indexDuMot=0;
		for (String mot:setTousMotsFrancais){
			HashMapContext mapSetMotsFrancais=new HashMapContext();
			
			indexDuMot=texteEntierParMot.indexOf(mot);
			List<String>contexteTmp=new LinkedList<String>();
			if (indexDuMot-tailleDuContexte>0&&indexDuMot+tailleDuContexte<texteEntierParMot.size()){
				contexteTmp=texteEntierParMot.subList(indexDuMot-tailleDuContexte, indexDuMot+tailleDuContexte);
//				System.err.println("CAS 1");
//				System.err.println(mot);
//				System.out.println(contexteTmp);
				
			}
			else if (indexDuMot-tailleDuContexte<0&&indexDuMot+tailleDuContexte<texteEntierParMot.size()){
				contexteTmp=texteEntierParMot.subList(0, texteEntierParMot.indexOf(mot));
//				System.err.println("CAS 2");
//				System.err.println(mot);
//				System.out.println(contexteTmp);
			}
			else{
				contexteTmp=texteEntierParMot.subList(texteEntierParMot.indexOf(mot), texteEntierParMot.size());
//				System.err.println("CAS 3");
//				System.err.println(mot);
//				System.out.println(contexteTmp);
			}
			mapSetMotsFrancais.setSrc(mot);
			mapSetMotsFrancais.setTrg(contexteTmp);
			LinkedList<Integer>valueInt=new LinkedList<Integer>();
			for (String unite : setTousMotsFrancais){
				if (contexteTmp.toString().contains(unite)){
					m++;
				}
				else{
					m=0;
				}
				valueInt.add(m);
			}
			mapSetMotsFrancais.setVector(valueInt);
//			System.out.println("Vecteur du mot : "+mapSetMotsFrancais.getSrc()+ " : "+mapSetMotsFrancais.getVector());
			mapDeContextes.add(mapSetMotsFrancais);
		}
		return mapDeContextes;
	}

	public static LinkedHashMap<String, List<Integer>> fillMap(LinkedHashMap<String, List<String>>src){
		LinkedHashMap <String, List<Integer>> myMap = new LinkedHashMap<String, List<Integer>>();
		for (String w:src.keySet()){
			LinkedList <Integer>indice = new LinkedList<Integer>();
			int nbOccurrences=0;
			for (List<String> c:src.values()){
				
				for (String motChunk:c){
					if (motChunk.contains(w)){
						nbOccurrences++;	
					}
				}
				indice.add(nbOccurrences);
			}
			
			myMap.put(w, indice);
		}
		return myMap;
	}
	
	
	public LinkedHashMap<String, List<Integer>> fillIntegerMap(LinkedHashMap<String, List<String>>src, HashSet <String>set){
		LinkedHashMap <String, List<Integer>>mapSetMotsFrancaisInteger=new LinkedHashMap<String, List<Integer>>();
		
		for (Entry<String, List<String>> entry : src.entrySet()){
			int m=0;
			List<Integer>valueInt=new ArrayList<Integer>();
			List<String>valeurs=entry.getValue();
				for (String motTexteIntegral:set){
					if (valeurs.contains(motTexteIntegral)){
						m=1;	
					}
					else{
						m=0;
					}
					valueInt.add(m);
				}
			mapSetMotsFrancaisInteger.put(entry.getKey(), valueInt);
		}
		
		return mapSetMotsFrancaisInteger;
		
	}
	
	
	
//		LinkedHashMap <String, List<Integer>> myMap = new LinkedHashMap<String, List<Integer>>();
//		LinkedList <Integer>indice = new LinkedList<Integer>();
//		for (String sK:src.keySet()){
////			for (List<String> sV:src.values()){
////				for(String mot:sV){
//					int nbOccurrences=0;
//					
//						for (List<String> tV:trg.values()){
//							for(String mot2:tV){
//								if (mot2.contains(sK)){
//									nbOccurrences++;
//									indice.add(nbOccurrences);
//									myMap.put(sK, indice);
//								}
//							}
//						}
					
					
//				}

//			}
//			LinkedList <Integer>indice = new LinkedList<Integer>();
//			for (String c:chunk){
//				String motsDesChunks[]=c.split("\\s");
//				int nbOccurrences=0;
//				for (String motChunk:motsDesChunks){
//					if (motChunk.contains(w)){
//						nbOccurrences++;	
//					}
//				}
				
//			}
//			
//		}
//		System.out.println(myMap.keySet());
//		System.out.println(myMap.values());
//		return myMap;
//	}


	public List<String> defineFixedContext(String tabCosine[], int tailleDuContexte){
		//		String tabCosine[]=src.split("\\s");
		String contexte [] = new String [tabCosine.length] ;
		List <String> listeContexte=new ArrayList<String>();
		for (int j=0; j<tabCosine.length;j++){

			if (j<tailleDuContexte){
				contexte=Arrays.copyOfRange(tabCosine, j, j+tailleDuContexte);
				listeContexte = new ArrayList<String>(Arrays.asList(contexte));
			}
			else if (j>=tailleDuContexte&&j<=tabCosine.length-tailleDuContexte){
				contexte=Arrays.copyOfRange(tabCosine, j-(tailleDuContexte/2), j+(tailleDuContexte/2));	
				listeContexte = new ArrayList<String>(Arrays.asList(contexte));
			}
			else{
				contexte=Arrays.copyOfRange(tabCosine, j-tailleDuContexte, j);	
				listeContexte = new ArrayList<String>(Arrays.asList(contexte));
			}
		}
		return listeContexte;
	}


	public LinkedHashMap <String, List<String>> fillContext(LinkedHashSet<String>set, String tab [],int tailleDuContexte){
		LinkedHashMap <String, List<String>>mapSetMotsFrancais=new LinkedHashMap<String, List<String>>();
		int index=0;
		
		for (String mot:set){
			List<String>contexte=new ArrayList<String>();
			for (int i=index;i<tab.length;i++){
				if (tab[i].contains(mot)){
					if (i<tailleDuContexte){
						contexte.addAll(Arrays.asList(Arrays.copyOfRange(tab, i, i+tailleDuContexte)));
					}
					else if (i>=tailleDuContexte&&i<=tab.length-tailleDuContexte){
						contexte.addAll(Arrays.asList(Arrays.copyOfRange(tab, i-(tailleDuContexte/2), i+(tailleDuContexte/2))));	

					}
					else{
						contexte.addAll(Arrays.asList(Arrays.copyOfRange(tab, i-tailleDuContexte, i)));	
					}
					mapSetMotsFrancais.put(mot,contexte);
				}
			}
		}
		return mapSetMotsFrancais;
	}

	public LinkedHashMap <String, List<String>> fillChunkContext(LinkedHashSet<String>setString,List<NWRecord>recordList ){
		Set<String>set=new HashSet<String>();
		LinkedHashMap <String, List<String>>mapSetMotsFrancais=new LinkedHashMap<String, List<String>>();
		List <String>listSet=new ArrayList<String>();
		for (NWRecord record: recordList){
			set=Arrays.stream((record.getSrc()+record.getTrg()).split(" ")).collect(Collectors.toSet());
			listSet.add(set.toString());
		}
		for (String mot:setString){
			List<String>contexte=new ArrayList<String>();
			for (String listStringContext:listSet){
				if (listStringContext.contains(mot)){
					contexte.add(listStringContext);
				}
			}
			mapSetMotsFrancais.put(mot,contexte);
		}
		return mapSetMotsFrancais;
	}
	
	public LinkedHashMap <String, List<Integer>> fillChunkInt(LinkedHashSet<String>setString,List<HashMapContext>recordList ){
		LinkedHashMap <String, List<Integer>>mapSetMotsFrancais=new LinkedHashMap<String, List<Integer>>();
		List <String>listSet=new ArrayList<String>();
		for (HashMapContext record: recordList){
			listSet.add((record.getSrc()+record.getTrg()));
		}
		for (String mot:setString){
			int counter=0;
			List<Integer>vecteur=new ArrayList<Integer>();
			for (String listStringContext:listSet){
				if (listStringContext.contains(mot)&&mot!=" "&&listStringContext!=""){
					counter++;
					vecteur.add(counter);
				}
				else{
					counter=0;
					vecteur.add(counter);
				}
			}
			mapSetMotsFrancais.put(mot,vecteur);
//			System.out.println(mot+" : "+vecteur);
		}
		return mapSetMotsFrancais;
	}

	public int [] mapToInt (LinkedHashMap <String, List<Integer>>map){
		int[] intArray =null;
		for (Entry<String, List<Integer>> entry : map.entrySet()){
			intArray = ArrayUtils.toPrimitive(entry.getValue().toArray(new Integer[entry.getValue().size()]));
		}
		return intArray;	
	}




	//	public static LinkedHashMap<String, List<Integer>> preFillMap(String word[], String chunk[]){
	//		LinkedHashMap <String, List<Integer>> myMap = new LinkedHashMap<String, List<Integer>>();
	//		for (String w:word){
	//			LinkedList <Integer>indice = new LinkedList<Integer>();
	//			for (String c:chunk){
	//				String motsDesChunks[]=c.split("\\s");
	//				int nbOccurrences=0;
	//				for (String motChunk:motsDesChunks){
	//					if (motChunk.contains(w)){
	//						nbOccurrences++;	
	//					}
	//				}
	//				indice.add(nbOccurrences);
	//			}
	//			myMap.put(w, indice);
	//		}
	//		return myMap;
	//	}



	//		for (List<Integer> k:mapTest.values()){
	//			int[] array = new int[k.size()];
	//			for (int i = 0; i < k.size(); i++) {
	//			    array[i] = k.get(i); // Watch out for NullPointerExceptions!
	//			}
	//			for (List<Integer> k2:mapTest2.values()){
	//				int[] array2 = new int[k2.size()];
	//				for (int i = 0; i < k2.size(); i++) {
	//				    array2[i] = k2.get(i); // Watch out for NullPointerExceptions!
	//				}
	//				
	//			}
	//		}


}
