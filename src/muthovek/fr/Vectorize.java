package muthovek.fr;

import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.annolab.tt4j.TokenHandler;
import org.annolab.tt4j.TreeTaggerWrapper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import muthovek.fr.resource.HashMapContext;
import muthovek.fr.resource.Peuplage;
import muthovek.fr.resource.ResourceCollection;
import muthovek.fr.similarityMaths.Cosine;



public class Vectorize {
	
	private static final String SOURCE_HOME = "/home/odysseus/workspacetmp/MuthoVek";

	public void vectorize() throws Exception {
		System.setProperty("treetagger.home", "/home/odysseus/Bureau/TreeTagger/");
		LinkedHashSet <String> setTousMotsFlechis=new LinkedHashSet <String>();
		LinkedList<String>listeDesTextes=new LinkedList<String>();
		ResourceCollection myTexts = ResourceCollection.newInstance();
		
		System.err.println("MUTHOVEK - SOURCE_HOME:" + SOURCE_HOME);
		
		File rootDirectory = new File(SOURCE_HOME + "/Textes");
		File[] listOfFiles = rootDirectory.listFiles();
		
		for (File file:listOfFiles){
			int i=1;
			myTexts.add(""+i+"", ""+file+"");
			listeDesTextes.add(FileUtils.readFileToString(file));
			i++;
		}
		String content=StringUtils.join(listeDesTextes.toArray(),",");		
		
		LinkedList<String>listeFlechie=new LinkedList<String>();
		@SuppressWarnings({ "unchecked", "rawtypes" })
		PTBTokenizer ptbt = new PTBTokenizer(new StringReader(content),
				new CoreLabelTokenFactory(), "");
		for (CoreLabel label; ptbt.hasNext(); ) {
			label = (CoreLabel) ptbt.next();
			String mot=label.word();
			listeFlechie.add(mot);
			setTousMotsFlechis.add(mot);
		}
		System.err.println(listeFlechie.size());
		LinkedList<String>listeLemmatisee=new LinkedList<String>();
		LinkedHashSet <String> setTousMotsLemmatises=new LinkedHashSet <String>();
		TreeTaggerWrapper<String> tt = new TreeTaggerWrapper<String>();
		tt.setModel("french.par");
		tt.setHandler(new TokenHandler<String>() {
			public void token(String token, String pos, String lemma) {
				listeLemmatisee.add(lemma);
				setTousMotsLemmatises.add(lemma);
			}
		});
		tt.process(listeFlechie);
		tt.destroy();
		FileUtils.writeStringToFile(new File("test.txt"), listeLemmatisee.toString());
		int tailleDuContexte=3;
		Peuplage peuplage=new Peuplage();
		System.out.println("avant peuplage");
		List<HashMapContext>contexte=peuplage.fillContextInt(setTousMotsLemmatises, listeLemmatisee, tailleDuContexte);
		System.out.println("après peuplage");
		Cosine cosine = new Cosine();
		List<HashMapContext>listeGlobale=new ArrayList<HashMapContext>();
		for (HashMapContext context:contexte){
			listeGlobale.add(context);
		}
		LinkedHashMap <String, List<Integer>>mapSetMotsFrancaisPost=peuplage.fillChunkInt(setTousMotsFlechis, listeGlobale);
		
		
		
	/*CHANGER ICI LE TERME QU'ON CHERCHE*/	
		HashMap<String, HashMapContext> dict=initMainDistribDict("littérature",mapSetMotsFrancaisPost, cosine);
		System.out.println(dict);
	}
	
	private HashMap<String, HashMapContext> initMainDistribDict(String cleCherchee,Map<String, List<Integer>> mapSetMotsFrancaisInteger, Cosine cosine) {
		long startTime = System.currentTimeMillis(); 
		HashMap<String,HashMapContext> dict=new HashMap<String,HashMapContext>();

//		for (Entry<String, List<Integer>> entry : mapSetMotsFrancaisInteger.entrySet()){
		if (mapSetMotsFrancaisInteger.containsKey(cleCherchee)){
			HashMapContext entree=new HashMapContext();
			
			LinkedHashSet<String>list = new LinkedHashSet<String>();
			List<Double>listeScores=new ArrayList<Double>();
			for (Entry<String, List<Integer>> entry2 : mapSetMotsFrancaisInteger.entrySet()){
				double myResult=0;
				List<Integer> termVector1 = mapSetMotsFrancaisInteger.get(cleCherchee);
				List<Integer> termVector2 = entry2.getValue();
				myResult=cosine.cosineSimilarity(termVector1, termVector2);

				if (myResult>0.54&&entry2.getKey().length()>2&&entry2.getKey()!=null&&entry2.getKey()!=cleCherchee){
					//          0.54
					/* A 0.3 on a beaucoup de bruit, mais des expressions intéressantes. On commence à être sévère vers 0.5*/
					/* Il est intéressant de voir les résultats individuels de chaque traducteur, pour ce qui est de la littéralité :
					 *  si les synonymes de noms apparaissent à similarité haute, l'auteur est plutôt littéraliste, puisqu'il emploie les noms aux 
					 *  mêmes endroits que la source, et sinon, il s'éloigne*/
					list.add(entry2.getKey());
					listeScores.add(myResult);
					if (cleCherchee.length()>2&&cleCherchee!=null){
						entree.setSrc(cleCherchee);
						entree.setValue(list);
						entree.setScore(listeScores);

					}
				}
			}
			if (dict.containsKey(entree.getSrc())==true&&entree.getSrc()!=null){
				List<String>tempListTrg=new ArrayList<String>();
				tempListTrg.addAll(list);
				tempListTrg.addAll(dict.get(entree.getSrc()).getTrg());
				dict.remove(entree.getSrc());
				entree.setTrg(tempListTrg);
			}
				dict.put(entree.getSrc(),entree);
			System.out.println(entree.getSrc()+" : "+ entree);
		}

		long endTime = System.currentTimeMillis();

		System.out.println("****************");
		System.out.println( "DICTIONNAIRE DISTRIBUTIONNEL PRINCIPAL : "+((endTime-startTime)/1000) );
		System.out.println("****************");

		return dict;
	}	
}
