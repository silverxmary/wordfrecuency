package d;

//please import the libraries as external jars 
//you also try creating a package d so you can import the class there
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
//I added portterStemmer to steam words
import org.tartarus.snowball.ext.PorterStemmer;
//I added gson create the json output
import com.google.gson.Gson;
//created an object output to convert then to json output
class Output {

	private String results;
	private Object words;
	private Object  totalOccurances;
	private List<Integer> sentences;
	public String getResults() {
		return results;
	}
	public void setResults(String results) {
		this.results = results;
	}
	
	public void setWords(Object words) {
		this.words = words;
	}
	
	public void setTotalOccurances(Object totalOccurances) {
		this.totalOccurances = totalOccurances;
	}
	public List<Integer> getSentences() {
		return sentences;
	}
	public void setSentences(List<Integer> sentences) {
		this.sentences = sentences;
	}

	
}

public class WordFrequencyStatsJava { 

   public static void main(String[] args){ 
      String str = "Take this paragraph of text and return an alphabetized list of ALL unique words. "
    		  +"A unique word is any form of a word often communicated with essentially the same meaning. "
    		  +"For example, fish and fishes could be defined as a unique word by using their stem fish. "
    		  +"For each unique word found in this entire paragraph, determine the how many times the word appears in total. "
    		  +"Also, provide an analysis of what sentence index position or positions the word is found. "
    		  +"The following words should not be included in your analysis or result set: "
    		  + " 'a', 'the', 'and', 'of', 'in', 'be', 'also' and 'as'. "
    		  + "Your final result MUST be displayed in a readable console output in the same format as the JSON sample object shown below."; 
       
      WordFrequencyStatsJava freqTest = new WordFrequencyStatsJava(); 
      freqTest.getFreqStats(str); 
   } 
   public void getFreqStats(String str){ 
	   //Create a Stream convert to lower case and split by space
      Stream<String> stream = Stream.of(str.toLowerCase().split("\\W+")).parallel();
      
	    
      //collecting the words and their frequencies using the collect() method of the Stream class Adding it to a map
      Map<String, Long> wordFreq = stream
            .collect(Collectors.groupingBy(String::toString,Collectors.counting())); 
      //order the map for keys comparing the words.
      Map<Object, Object> newMapSortedByKey = wordFreq.entrySet().stream()
              .sorted((e1,e2) -> e1.getKey().compareTo(e2.getKey()))
              .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1,e2) -> e1, LinkedHashMap::new));
      
      //print the wordso of the map sorted.
      String[] sentences=str.split("[!?.]+");
      List<Object> out = new ArrayList<>();    

      newMapSortedByKey.forEach((k,v)->{
    	  List<Integer> sentenceIn = new ArrayList<>();
    	  for(int i=0; i<sentences.length; i++){
    		String[] splitedSentence = sentences[i].toLowerCase().split("\\b+"); //split on space
    		if(Arrays.asList(splitedSentence).contains(k)){
    			sentenceIn.add(i);
    		}else continue;
    		
    	  }
    	
      //System.out.println("word: "+ k + " Count:" + v + " In sentences: " );
      //sentenceIn.forEach((a)->System.out.println(a));
    	  //create the object and add it to a list of objects
      Output output = createDummyObject(k, v, sentenceIn);
      out.add(output);
      //{word: and, count: 2, sentences:{0}, {1}}
    
      }); 
     
      //1. Convert object to JSON string
      Gson gson = new Gson();
      String json = gson.toJson(out);
      System.out.println("{ results:"+json+"}");   
      }
   	  //to create the object of the output
   private static Output createDummyObject(Object words, Object totalOccurances, List<Integer> sentences  ) {

	  Output output = new Output();
	  output.setSentences(sentences);
	  output.setWords(words);
	  output.setTotalOccurances(totalOccurances);
     
       return output;

   }
}

