/*
 * Created on 02-09-2003 by jesper
 * This class maps an ER entity 
 */
package dk.itu.next.rea.transform.ermodel;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author jesper
 */
public class EREntity implements ERThing{
	private String _entityName;
	private ArrayList _attributes;
	private ArrayList _relations;
	
	public EREntity(String entityName){
		this._attributes = new ArrayList();
		this._relations = new ArrayList();
		this.set_entityName(entityName);
	}
	
	/**
	 * @return
	 */
	public ArrayList get_attributes() {
		return _attributes;
	}

	/**
	 * @return
	 */
	public String get_entityName() {
		return _entityName;
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
	public void set_attributes(ArrayList list) {
		_attributes = list;
	}
	
	public void addAttribute(ERAttribute att){
		this._attributes.add(att);
	}
	/**
	 * @param string
	 */
	public void set_entityName(String string) {
		_entityName = string;
	}

	/**
	 * @param list
	 */
	public void set_relations(ArrayList list) {
		_relations = list;
	}
	public void addRelation(ERRelation relation){
		this._relations.add(relation);
	}
	
	public boolean hasRelations(){
		return (this.get_relations().size()>0);
	}
	
	public boolean hasAttributes(){
		return (this.get_attributes().size()>0);
	}
	
	public String toString(){
		//<entity name="Silk">
		String s = "<entity name='" + this.get_entityName()+"'>\n";
		ArrayList children = this.get_relations();
		for(Iterator i = children.iterator(); i.hasNext();){
			s+=((ERThing)i.next()).toString()+"\n";
		}
		s+="</entity>";
		return s;
	}

	/* (non-Javadoc)
	 * @see dk.itu.next.rea.transform.ermodel.ERThing#getName()
	 */
	public String getName() {
		return this.get_entityName();
	}
}
