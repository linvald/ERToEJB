/*
 * Created on 02-09-2003 by jesper
 * This class should ease the reading of ER models 
 */
package dk.itu.next.rea.transform.ermodel;

import javax.xml.parsers.*;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @author jesper
 */
public class ERModelReader {
	
	private DocumentBuilderFactory _factory;
	private DocumentBuilder _builder;
	private Document _document;
	private Node _rootNode;
	
	private ArrayList _entityList;
	private ArrayList _relationList;
	
	/**
	 * 
	 * @param pathToXmlModel - the full path to the ER XML document
	 */
	public ERModelReader(File pathToXmlModel)throws Exception{
		try {		
			_factory = DocumentBuilderFactory.newInstance();
			_builder = _factory.newDocumentBuilder();
			_document = _builder.parse(pathToXmlModel);
			_rootNode = _document.getDocumentElement();
			_entityList = parseEntities();
			_relationList = parseRelations();
			
		
			
		} catch (FactoryConfigurationError e) {
			System.err.println("FactoryConfigurationError" + e);
				throw new Exception("FactoryConfigurationError", e);
		} catch (ParserConfigurationException e) {
			System.err.println("ParserConfigurationException" + e);
				throw new Exception("ParserConfigurationException", e);
		} catch (SAXException e) {
			System.err.println("SAXException" + e);
				throw new Exception("SAXException", e);
		} catch (IOException e) {
			System.err.println("IOException" + e);
				throw new Exception("IOException", e);
		}		
	}
	
	
	/**
	 * 
	 */
	
	public String getModelName(){
		NamedNodeMap map =  _rootNode.getAttributes();
		Node nameNode = map.getNamedItem("name");
		return nameNode.getNodeValue();
	}
	



	private ArrayList parseRelations(){
		NodeList nl = _document.getElementsByTagName("relation");
		ArrayList relations = new ArrayList();
		ERRelation relation = null;
		try {
			if (nl.getLength() > 0) {
				for (int i = 0; i < nl.getLength(); i++) {
					Node entityNode = (Node) nl.item(i);
					relation = handleERRelationTag(entityNode);
					NodeList children = entityNode.getChildNodes();
					if (children.getLength() > 0) {
						for (int k = 0; k < children.getLength(); k++) {
							Node child = (Node) children.item(k);
							if (child.getNodeType() == Node.ELEMENT_NODE) {
								this.handleERRelationChild(child, relation);
							}
						}
					}
					relations.add(relation);
				}
			}
		} catch (DOMException e) {
			e.printStackTrace();
		}
		return relations;
	}
	
	private ERRelation handleERRelationTag(Node relationNode) {
		//<relation to="Silk" name="outflow" cardinality="n"/>
		ERRelation relation =  new ERRelation();
		NamedNodeMap nNmap = relationNode.getAttributes();
		for (int j = 0; j < nNmap.getLength(); j++) {
			Attr attr = (Attr) nNmap.item(j);
			String attributeName = attr.getName();
			String attributeValue = attr.getNodeValue();
			if (attributeName != null) {
				attributeName = attributeName.trim();			
				if (attributeName.equals("name")) {
					relation.set_relationName(attributeValue);
				}
				if (attributeName.equals("direction")) {
					relation.set_direction(attributeValue);
				}
			}
		}
		return relation;
	}
	
	private void handleERRelationChild(Node child, ERRelation relation) {
		String tagName = child.getNodeName().trim();
		NamedNodeMap nNmap = child.getAttributes();
		if (tagName != null) {
			if (tagName.equals("source")) {
				for (int j = 0; j < nNmap.getLength(); j++) {
					Attr attr = (Attr) nNmap.item(j);
					String attributeName = attr.getName();
					String attributeValue = attr.getNodeValue();
					if (attributeName.equals("name")) {
						relation.set_relationsource(attributeValue);
					}
					if (attributeName.equals("cardinality")) {
						relation.set_sourceCardinality(attributeValue);
					}
				}
			}else if (tagName.equals("target")) {
				for (int j = 0; j < nNmap.getLength(); j++) {
					Attr attr = (Attr) nNmap.item(j);
					String attributeName = attr.getName();
					String attributeValue = attr.getNodeValue();
					if (attributeName.equals("name")) {
						relation.set_relationtarget(attributeValue);
					}
					if (attributeName.equals("cardinality")) {
						relation.set_targetCardinality(attributeValue);
					}
				}
			}else {
				System.out.println(
					"ERModelReader.handleERRelationChild() - recieved a childnode that is neither a source nor a target:"
						+ tagName);
			}
		}
	}
	
	private ArrayList parseEntities() {
		NodeList nl = _document.getElementsByTagName("entity");
		ArrayList entities = new ArrayList();
		EREntity entity = null;
		try {
			if (nl.getLength() > 0) {
				for (int i = 0; i < nl.getLength(); i++) {
					Node entityNode = (Node) nl.item(i);
					entity = handleEREntityTag(entityNode);
					NodeList children = entityNode.getChildNodes();
					if (children.getLength() > 0) {
						for (int k = 0; k < children.getLength(); k++) {
							Node child = (Node) children.item(k);
							if (child.getNodeType() == Node.ELEMENT_NODE) {
								this.handleEREnittyChild(child, entity);
							}
						}
					}
					if(entity!=null)
					entities.add(entity);
				}
			}
		} catch (DOMException e) {
			e.printStackTrace();
		}
		return entities;
	}
	
	private void handleEREnittyChild(Node child, EREntity entity) {
			String tagName = child.getNodeName().trim();
			if (tagName != null) {
				if (tagName.equals("attribute")) {
					entity.addAttribute(this.handleERAttribute(child));
				}else{
					System.out.println(
						"ERModelReader.getEntities() - recieved a childnode that is neither an attribute nor a relation:"
							+ tagName);
				}
			}
	}
	
	
	private EREntity handleEREntityTag(Node entityNode ) {
		if (entityNode != null) {
			NamedNodeMap nNmap = entityNode.getAttributes();
			for (int j = 0; j < nNmap.getLength(); j++) {
				Attr attr = (Attr) nNmap.item(j);
				String attributeName = attr.getName();
				String attributeValue = attr.getNodeValue();
				if (attributeName != null) {
					if (attributeValue == "" || attributeValue == null) {
						attributeValue = "";
					}
					if (attributeName.equals("name")) {
							return new EREntity(attributeValue);
					}						
				}
			}
		}
		return null;
	}
	

	
	private ERAttribute handleERAttribute(Node attributeNode) {
		//	<attribute name="kind" value="take"/>
		ERAttribute attribute = new ERAttribute();
		NamedNodeMap nNmap = attributeNode.getAttributes();
		for (int j = 0; j < nNmap.getLength(); j++) {
			Attr attr = (Attr) nNmap.item(j);
			String attributeName = attr.getName();
			String attributeValue = attr.getNodeValue();

			if (attributeName != null) {
				if (attributeValue == "" || attributeValue == null) {
					attributeValue = "";
				}
				if(attributeName.equals("name")){
					attribute.set_key(attributeValue);
				}
				if(attributeName.equals("value")){
					attribute.set_value(attributeValue);
				}
			}			
		}
		return attribute;
	}	
	
	public ArrayList getEntities(){
		return this._entityList;
	}
	
	private ArrayList getTargetingEntities(String entityName){
		//doing this after resolving all entities
		//traverse entities and ask each if others have a relation to="entityName"
		ArrayList foreighKeyTargets = new ArrayList();
		for(Iterator i = getEntities().iterator(); i.hasNext();){
			EREntity current = (EREntity)i.next();
			ArrayList relations = current.get_relations();
			if (relations.size() > 0) {
				for (Iterator iter = relations.iterator(); iter.hasNext();) {
					ERRelation relation = (ERRelation) iter.next();
					if (relation.get_relationTarget().equals(entityName)) {
						foreighKeyTargets.add(relation.get_relationTarget());
					}
				}
			}
		} 
		return foreighKeyTargets;
	}
	
	public ArrayList getERRelations(){
		return this._relationList;
	}
	
	public HashMap getForeignKeyHolders(){
		HashMap entityRelatedTo = new HashMap();
		for(Iterator i = getEntities().iterator(); i.hasNext();){
			EREntity current = (EREntity)i.next();
			ArrayList targetingMe = getTargetingEntities(current.get_entityName());
			entityRelatedTo.put(current.get_entityName(),targetingMe);
		}
		return entityRelatedTo;
	}
}
