/*
 * Created on 06-09-2003 by jesper
 * This class should 
 */
package dk.itu.next.rea.transform.ejb;

import dk.itu.next.rea.transform.velocity.StringHelper;

/**
 * @author jesper
 */
public class EJBRelation {
	
	//relation from Silk to SilkVendor
	//relation name = SilkVendor
	//relation many = true
	//relation signature = "Silk_SilkVendor"
	private String _relatedTo;
	private String _relationFrom;
	private boolean _birdirectional;
	private String _relationSignature;
	private boolean _relationFromIsMany,_relationToIsMany;
	
	
	public EJBRelation(String relationFrom, String relationTo, boolean isBidirectional,boolean relationFromIsMany, boolean relationToIsMany){
		this.set_birdirectional(isBidirectional);
		this.set_relationFrom(relationFrom);
		this.set_relatedTo(relationTo);
		this.set_relationFromIsMany(relationFromIsMany);
		this.set_relationToIsMany(relationToIsMany);
		this.set_relationSignature(relationFrom, relationTo);
	}


	
	/**
	 * @return
	 */
	public String get_relatedTo() {
		return _relatedTo;
	}


	/**
	 * @param string
	 */
	public void set_relatedTo(String string) {
		_relatedTo = string;
	}

	/**
	 * @return
	 */
	public boolean is_birdirectional() {
		return _birdirectional;
	}

	/**
	 * @return
	 */
	public String get_relationSignature() {
		return _relationSignature;
	}

	/**
	 * @param b
	 */
	public void set_birdirectional(boolean b) {
		_birdirectional = b;
	}


	/**
	 * @param string
	 */
	public void set_relationSignature(String from, String to) {
		StringHelper helper = new StringHelper();
		int bigger = helper.compareTo(from.toLowerCase(),to.toLowerCase());
		if(bigger<0){
			//from is less
			this._relationSignature = from+"_"+to;
		}else{
			this._relationSignature = to+"_"+from;
		}
	}

	/**
	 * @return
	 */
	public String get_relationFrom() {
		return _relationFrom;
	}

	/**
	 * @param string
	 */
	public void set_relationFrom(String string) {
		_relationFrom = string;
	}

	/**
	 * @return
	 */
	public boolean is_relationFromIsMany() {
		return _relationFromIsMany;
	}

	/**
	 * @return
	 */
	public boolean is_relationToIsMany() {
		return _relationToIsMany;
	}

	/**
	 * @param b
	 */
	public void set_relationFromIsMany(boolean b) {
		_relationFromIsMany = b;
	}

	/**
	 * @param b
	 */
	public void set_relationToIsMany(boolean b) {
		_relationToIsMany = b;
	}

}
