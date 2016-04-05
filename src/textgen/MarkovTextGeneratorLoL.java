package textgen;

import java.util.Map;
import java.util.Map.Entry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.Set;


/** 
 * An implementation of the MTG interface that uses a list of lists.
 * @author UC San Diego Intermediate Programming MOOC team 
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

	// The list of words with their next words
	private List<ListNode> wordList; 
	
	// The starting "word"
	private String starter;
	
	// The random number generator
	private Random rnGenerator;
	
	public MarkovTextGeneratorLoL(Random generator)
	{
		wordList = new LinkedList<ListNode>();
		starter = "";
		rnGenerator = generator;
	}
	
	
	/** Train the generator by adding the sourceText */
	@Override
	public void train(String sourceText)
	{
		// TODO: Implement this method
		if(wordList.size() !=0){
			return ;
		}
		String[] source = sourceText.split(" ");
		starter = source[0];
		String prev = starter;
		Map<String,List<String>> visited = new HashMap<>();
		for(int i = 0; i < source.length-1; i++) {
			String curr = source[i];
			String next = source[i+1];
			if(visited.containsKey(curr)){
				visited.get(curr).add(next);
			}
			else {
				visited.put(curr, new ArrayList<String>());
				visited.get(curr).add(next);
			}
		}
		if(!visited.containsKey(source[source.length-1])){
			visited.put(source[source.length-1], new ArrayList<String>());
			visited.get(source[source.length-1]).add(source[0]);
		}
		else{
			visited.get(source[source.length-1]).add(source[0]);
		}
		for(String key : visited.keySet()) {
			ListNode toAdd = new ListNode(key);
			for(String word : visited.get(key)) {
				toAdd.addNextWord(word);
			}
			wordList.add(toAdd);
		}
	}
	
	/** 
	 * Generate the number of words requested.
	 */
	@Override
	public String generateText(int numWords) {
	    // TODO: Implement this method
		if (wordList.size()==0) {
			return "";
		}
		if(numWords == 0){
			return "";
		}
		String s = new String();
		String curr = starter;
		s += curr;
		for(int i = 1 ; i< numWords; i++) {
			for(int j = 0; j < wordList.size(); j++){
				if(wordList.get(j).getWord().equals(curr)) {
					String w = wordList.get(j).getRandomNextWord(rnGenerator);
					s += " " + w;
					curr = w;
					break;
				}
			}
		}
		return s;
	}
	
	
	// Can be helpful for debugging
	@Override
	public String toString()
	{
		String toReturn = "";
		for (ListNode n : wordList)
		{
			toReturn += n.toString();
		}
		return toReturn;
	}
	
	/** Retrain the generator from scratch on the source text */
	@Override
	public void retrain(String sourceText)
	{
		// TODO: Implement this method.
		String[] source = sourceText.split(" ");
		starter = source[0];
		String prev = starter;
		Map<String,List<String>> visited = new HashMap<>();
		for(int i = 0; i < source.length-1; i++) {
			String curr = source[i];
			String next = source[i+1];
			if(visited.containsKey(curr)){
				visited.get(curr).add(next);
			}
			else {
				visited.put(curr, new ArrayList<String>());
				visited.get(curr).add(next);
			}
		}
		if(!visited.containsKey(source[source.length-1])){
			visited.put(source[source.length-1], new ArrayList<String>());
			visited.get(source[source.length-1]).add(source[0]);
		}
		else{
			visited.get(source[source.length-1]).add(source[0]);
		}
		for(String key : visited.keySet()) {
			ListNode toAdd = new ListNode(key);
			for(String word : visited.get(key)) {
				toAdd.addNextWord(word);
			}
			wordList.add(toAdd);
		}
	}
	
	// TODO: Add any private helper methods you need here.
	
	
	/**
	 * This is a minimal set of tests.  Note that it can be difficult
	 * to test methods/classes with randomized behavior.   
	 * @param args
	 */
	public static void main(String[] args)
	{
		// feed the generator a fixed random value for repeatable behavior
		MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
		//String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
		//System.out.println(textString);
		//gen.train(textString);
		//System.out.println(gen);
		
		
		String textString1 = "hi there hi Leo";
		System.out.println(textString1);
		gen.retrain(textString1);
		System.out.println(gen);
		
		System.out.println(gen.generateText(4));
		String textString2 = "You say yes, I say no, "+
				"You say stop, and I say go, go, go, "+
				"Oh no. You say goodbye and I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"I say high, you say low, "+
				"You say why, and I say I don't know. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"Why, why, why, why, why, why, "+
				"Do you say goodbye. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"You say yes, I say no, "+
				"You say stop and I say go, go, go. "+
				"Oh, oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello,";
		//System.out.println(textString2);
		//gen.retrain(textString2);
		//System.out.println(gen);
		//System.out.println(gen.generateText(20));
	}

}

/** Links a word to the next words in the list 
 * You should use this class in your implementation. */
class ListNode
{
    // The word that is linking to the next words
	private String word;
	
	// The next words that could follow it
	private List<String> nextWords;
	
	ListNode(String word)
	{
		this.word = word;
		nextWords = new LinkedList<String>();
	}
	
	public String getWord()
	{
		return word;
	}

	public void addNextWord(String nextWord)
	{
		nextWords.add(nextWord);
	}
	
	
	public String getRandomNextWord(Random generator)
	{
		// TODO: Implement this method
	    // The random number generator should be passed from 
	    // the MarkovTextGeneratorLoL class
	    int index = generator.nextInt(nextWords.size());
	    return nextWords.get(index);
	}

	public String toString()
	{
		String toReturn = word + ": ";
		for (String s : nextWords) {
			toReturn += s + "->";
		}
		toReturn += "\n";
		return toReturn;
	}
	
}


