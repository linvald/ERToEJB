/*
 * Created on 07-09-2003 by jesper
 * This class should 
 */
package dk.itu.next.rea.transform.ejb;

import java.util.ArrayList;

/**
 * @author jesper
 */
public class EJBModel {
	
	private ArrayList _ejbs;
	private String _modelName;
	private String _basePackagePath; //package declaration without the modelName
	
	
	public EJBModel(){
		this._ejbs = new ArrayList();
	}
	/**
	 * @return
	 */
	public ArrayList get_ejbs() {
		return _ejbs;
	}


	/**
	 * @param list
	 */
	public void set_ejbs(ArrayList list) {
		_ejbs = list;
	}


	/**
	 * @return
	 */
	public String get_modelName() {
		return _modelName;
	}

	/**
	 * @param string
	 */
	public void set_modelName(String string) {
		_modelName = string;
	}
	
	public void addEJB(EJB ejb){
		this._ejbs.add(ejb);
	}
	/**
	 * @return
	 */
	public String get_basePackagePath() {
		return _basePackagePath;
	}

	/**
	 * @param string
	 */
	public void set_basePackagePath(String string) {
		_basePackagePath = string;
	}

}
