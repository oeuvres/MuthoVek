package muthovek.fr.similarityMaths;

import java.util.List;

public class Cosine {
  private double dp = 0.0;
  private double magnitudeA = 0.0;
  private double magnitudeB = 0.0;

  private double similarityScore(){
    return (this.dp) / (this.magnitudeA * this.magnitudeB);
  }
  
  public double cosine_similarity(int[] vec1, int[] vec2) {
    this.dp = dot_product(vec1, vec2);
    this.magnitudeA = find_magnitude(vec1);
    this.magnitudeB = find_magnitude(vec2);
    return similarityScore(); //(dp) / (magnitudeA * magnitudeB);
  }
  
  public double cosineSimilarity(List<Integer> vett1, List<Integer> vett2){
    this.dp = dot_product(vett1, vett2);
    this.magnitudeA = find_magnitude(vett1);
    this.magnitudeB = find_magnitude(vett2);
    return similarityScore();
  }

  private double find_magnitude(List<Integer> vett) {
    double sum_mag = 0;
    for (int i = 0; i < vett.size(); i++) {
      sum_mag = sum_mag + vett.get(i) * vett.get(i);
    }
    return Math.sqrt(sum_mag);
  }

  private double dot_product(List<Integer> vett1, List<Integer> vett2) {
    double sum = 0;
    for (int i = 0; i < vett1.size(); i++) {
      sum = sum + vett1.get(i) * vett2.get(i);
    }
    return sum;
  }

  private static double find_magnitude(int[] vec) {
    double sum_mag = 0;
    for (int i = 0; i < vec.length; i++) {
      sum_mag = sum_mag + vec[i] * vec[i];
    }
    return Math.sqrt(sum_mag);
  }

  private static double dot_product(int[] vec1, int[] vec2) {
    double sum = 0;
    for (int i = 0; i < vec1.length; i++) {
      sum = sum + vec1[i] * vec2[i];
    }
    return sum;
  }

}