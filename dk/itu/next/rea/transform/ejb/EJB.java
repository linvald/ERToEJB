/*
 * Created on 06-09-2003 by jesper
 * This class should 
 */
package dk.itu.next.rea.transform.ejb;

import java.util.ArrayList;
import java.util.Iterator;

import dk.itu.next.rea.transform.ermodel.ERAttribute;
import dk.itu.next.rea.transform.ermodel.ERRelation;

/**
 * @author jesper
 */
public class EJB {
	
	private String _ejbName;
	private ArrayList _fields = new ArrayList();
	private ArrayList _relations = new ArrayList();
	private ArrayList _foreignRelations = new ArrayList(); //ie a SellSilk pointing to a Silk
	private String _jndiName;
	/**
	 * @return
	 */
	public ArrayList get_fields() {
		return _fields;
	}

	/**
	 * @return
	 */
	public ArrayList get_relations() {
		return _relations;
	}

	/**
	 * @param list
	 */
	public void set_fields(ArrayList list) {
		_fields = list;
	}

	/**
	 * @param list
	 */
	public void set_relations(ArrayList list) {
		_relations = list;
	}
	public String toString() {
		String s = "{EJB name='" + this.get_ejbName()+"'" ;
		if (this.get_fields().size() > 0) {
			s += " fields=";
			int count = 0;			
			for (Iterator iter = get_fields().iterator(); iter.hasNext();) {
				count++;
				ERAttribute ea = (ERAttribute)iter.next();
				String field = ea.get_key();
				if (count < get_fields().size()) {
					s += field + ", ";
				} else {
					s += field;
				}
			}
		}
		
		if(this.get_relations().size()>0){
			s+=" related to:";
			int count = 0;	
			for (Iterator iter = get_relations().iterator(); iter.hasNext();) {
				count++;
				EJBRelation relation = (EJBRelation) iter.next();
				if (count < get_relations().size()) {
					s += relation.get_relatedTo() + ", ";
				} else {
					s += relation.get_relatedTo();
				}
				s+="(Bidirectional:"+relation.is_birdirectional()+")";
			}
		}
		if(this.get_foreignRelations().size()>0){
					s+=" foreign relations:";
					int count = 0;	
					for (Iterator iter = get_foreignRelations().iterator(); iter.hasNext();) {
						count++;
						EJBRelation relation = (EJBRelation) iter.next();
						if (count < get_foreignRelations().size()) {
							s += relation.get_relationFrom() + ", ";
						} else {
							s += relation.get_relationFrom();
						}
					}
				}
		s+="}";
		return s;
	}

	/**
	 * @return
	 */
	public String get_ejbName() {
		return _ejbName;
	}

	/**
	 * @param string
	 */
	public void set_ejbName(String string) {
		_ejbName = string;
	}

	/**
	 * @return
	 */
	public ArrayList get_foreignRelations() {
		return _foreignRelations;
	}

	/**
	 * @param list
	 */
	public void set_foreignRelations(ArrayList list) {
		_foreignRelations = list;
	}

	/**
	 * @return
	 */
	public String get_jndiName() {
		return _jndiName;
	}

	/**
	 * @param string
	 */
	public void set_jndiName(String string) {
		_jndiName = string;
	}

}
