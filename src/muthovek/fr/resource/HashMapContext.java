package muthovek.fr.resource;

import java.util.LinkedHashSet;
import java.util.List;


/**
 * @author Marianne Reboul
 *
 */
public class HashMapContext {
	private String src;
	private List<String> trg;
	private LinkedHashSet<String> value;
	private List<Integer> vector;
	private List<Double> score;
	/**
	 * @return the src
	 */
	public String getSrc() {
		return src;
	}
	/**
	 * @param src the src to set
	 */
	public void setSrc(String src) {
		this.src = src;
	}
	/**
	 * @return the trg
	 */
	public List<String> getTrg() {
		return trg;
	}
	/**
	 * @param trg the trg to set
	 */
	public void setTrg(List<String> trg) {
		this.trg = trg;
	}
	
	
	/**
	 * @return the trg
	 */
	public LinkedHashSet<String> getValue() {
		return value;
	}
	/**
	 * @param trg the trg to set
	 */
	public void setValue(LinkedHashSet<String> value) {
		this.value = value;
	}
	/**
	 * @return the score
	 */
	public List<Integer> getVector() {
		return vector;
	}
	/**
	 * @param score the score to set
	 */
	public void setVector(List<Integer> vector) {
		this.vector = vector;
	}
	/**
	 * @return the score
	 */
	public List<Double> getScore() {
		return score;
	}
	/**
	 * @param score the score to set
	 */
	public void setScore(List<Double> score) {
		this.score = score;
	}
	
	public boolean containsSrc(String entree){
		
		
		
		return true;
		
	}
	
}
