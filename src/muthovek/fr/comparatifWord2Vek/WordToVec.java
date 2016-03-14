package muthovek.fr.comparatifWord2Vek;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.text.sentenceiterator.LineSentenceIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.sentenceiterator.SentencePreProcessor;
import org.deeplearning4j.text.tokenization.tokenizer.TokenPreProcess;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.EndingPreProcessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;

public class WordToVec {
	public void vectWithW2V() throws IOException{
		final EndingPreProcessor preProcessor = new EndingPreProcessor();
	    TokenizerFactory tokenizer = new DefaultTokenizerFactory();
	    tokenizer.setTokenPreProcessor(new TokenPreProcess() {
	        @Override
	        public String preProcess(String token) {
	            token = token.toLowerCase();
	            String base = preProcessor.preProcess(token);
	            base = base.replaceAll("\\d", "d");
	            return base;
	        }
	    });
		
		
		int batchSize = 1000;
	    int iterations = 3;
	    int layerSize = 150;
	    SentenceIterator iter = new LineSentenceIterator(new File("test.txt"));
	    iter.setPreProcessor(new SentencePreProcessor() {
	        /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
	        public String preProcess(String sentence) {
	            return sentence.toLowerCase();
	        }
	    });
	    
	    Word2Vec vec = new Word2Vec.Builder()
	            .batchSize(batchSize) //# words per minibatch.
	            .minWordFrequency(5) // 
	            .useAdaGrad(false) //
	            .layerSize(layerSize) // word feature vector size
	            .iterations(iterations) // # iterations to train
	            .learningRate(0.025) // 
	            .minLearningRate(1e-3) // learning rate decays wrt # words. floor learning
	            .negativeSample(10) // sample size 10 words
	            .iterate(iter) //
	            .tokenizerFactory(tokenizer)
	            .build();
	    vec.fit();
	    
	    
	    Collection<String> similar = vec.wordsNearest("avoir", 10);
	    WordVectorSerializer.writeWordVectors(vec, "pathToWriteto.txt");

	    System.err.println(similar);
	}
}
