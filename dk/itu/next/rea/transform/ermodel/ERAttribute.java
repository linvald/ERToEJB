/*
 * Created on 02-09-2003 by jesper
 * This class should 
 */
package dk.itu.next.rea.transform.ermodel;

/**
 * @author jesper
 */
public class ERAttribute implements ERThing {
	
	private String _key, _value;
	
	
	public void addAttributeKeyValue(String key, String value){
	
	}
	

	public String toString(){
		return "<attribute name='"+get_key()+ "' value='"+get_value()+"'/>";


	}

	/* (non-Javadoc)
	 * @see dk.itu.next.rea.transform.ermodel.ERThing#getName()
	 */
	public String getName() {
		return "";
	}

	/**
	 * @return
	 */
	public String get_key() {
		return _key;
	}

	/**
	 * @return
	 */
	public String get_value() {
		return _value;
	}

	/**
	 * @param string
	 */
	public void set_key(String string) {
		_key = string;
	}

	/**
	 * @param string
	 */
	public void set_value(String string) {
		_value = string;
	}

}
