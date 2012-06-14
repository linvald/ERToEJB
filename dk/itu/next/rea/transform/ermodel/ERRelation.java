/*
 * Created on 02-09-2003 by jesper
 * This class maps a ER relation
 */
package dk.itu.next.rea.transform.ermodel;

/**
 * @author jesper
 */
public class ERRelation implements ERThing{
	
	private String _relationName;
	private String _target;
	private String _source;
	private String _sourceCardinality;
	private String _targetCardinality;
	private String _direction;
	
	class ERCardinality{
		public final static String ONE = "one";
		public final static String MANY = "many";
	}
	/**
	 * @return
	 */
	public String get_relationsource() {
		return _source;
	}

	/**
	 * @param from
	 */
	public void set_relationsource(String from) {
		_source = from;
	}

	/**
	 * @return
	 */
	public String get_relationTarget() {
		return _target;
	}

	/**
	 * @return
	 */
	public String get_relationName() {
		return _relationName;
	}

	/**
	 * @param entity
	 */
	public void set_relationtarget(String entity) {
		_target = entity;
	}

	/**
	 * @param string
	 */
	public void set_relationName(String string) {
		_relationName = string;
	}
	
	public String toString(){
		//	<relation to="CashAtHand" name="inflow" cardinality="n"/>
		return"<relation name='"+this.get_relationName()+"' direction='" + this.get_direction()+">\n"+
					"<source name='"+this.get_relationsource()+"' cardinality='"+this.get_sourceCardinality()+"'/>\n"+ 
					"<target name='"+this.get_relationTarget()+"' cardinality='"+this.get_targetCardinality()+"'/>\n"+ 
			  "</relation>";
	}

	/* (non-Javadoc)
	 * @see dk.itu.next.rea.transform.ermodel.ERThing#getName()
	 */
	public String getName() {
		return this.get_relationName();
	}
	/**
	 * @return
	 */
	public String get_direction() {
		return _direction;
	}

	/**
	 * @param string
	 */
	public void set_direction(String string) {
		_direction = string;
	}

	/**
	 * @return
	 */
	public String get_sourceCardinality() {
		return _sourceCardinality;
	}

	/**
	 * @return
	 */
	public String get_targetCardinality() {
		return _targetCardinality;
	}

	/**
	 * @param string
	 */
	public void set_sourceCardinality(String string) {
		_sourceCardinality = string;
	}

	/**
	 * @param string
	 */
	public void set_targetCardinality(String string) {
		_targetCardinality = string;
	}

}
