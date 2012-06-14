/*
 * Created on 24-04-2003
 *
 */
package dk.itu.next.rea.transform.velocity;

/**
 * Utility class that is put into the Velocity context and lets
 * us manipulate string objects from within the templates
 * 
 * @author linvald@it-c.dk
 */
public class StringHelper {

	/**
	 * 
	 * @param s - typically a class or Interface name
	 * @return String - the capitalized string
	 */
	public String getCapitlizedFirstLetter(String s){
		if(s != null){
		String smallChar = s.substring(0, 1).toUpperCase();
		return smallChar + s.substring(1);
		}
		else{
			return "";
		}
	}
	/**
	 * 
	 * @param s - typically a class or Interface name
	 * @return String - the minimized string
	 */
	public String getSmallFirstLetter(String s){
		if(s != null){
		String smallChar = s.substring(0, 1).toLowerCase();
		return smallChar + s.substring(1);
		}
		else{
			return "";
		}
	}
	/**
	 * 
	 * @param o  - a java object
	 * @return String - the fully quilified package name of the object
	 */
	public String getFullyQualifiedName(Object o){
		return o.getClass().getName();
	}
	
	public String getConcatenatedString(String one, String two){
		return one+two;
	}
	/**
	 * 0 if equals, - if a greater + if a less
	 */
	public int compareTo(String a, String b){
		return a.compareTo(b); //0 if equals, - if a greater + if a less
	}
}

