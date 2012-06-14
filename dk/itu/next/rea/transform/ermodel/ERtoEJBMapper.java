/*
 * Created on 02-09-2003 by jesper
 * This class should 
 */
package dk.itu.next.rea.transform.ermodel;
	
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import dk.itu.next.rea.transform.ejb.EJB;
import dk.itu.next.rea.transform.ejb.EJBModel;
import dk.itu.next.rea.transform.ejb.EJBRelation;
import dk.itu.next.rea.transform.velocity.TemplateRunner;



/**
 * @author jesper
 */		
public class ERtoEJBMapper {
	
	private ERModelReader _erModelReader;
	private EJBModel _ejbModel;
	
	private ArrayList _entities;
	private ArrayList _relations;
	
	private TemplateRunner _templateRunner;
	
	private final String _packageName = "dk.itu.rea.next.ejbs";
	
	public ERtoEJBMapper(File pathToERModel){	
			_ejbModel = new EJBModel();	
			try {
				this._erModelReader = new ERModelReader(pathToERModel);
				System.out.println("Succesfully initialized ERModelReader");
				_entities = this._erModelReader.getEntities();
				System.out.println("Number of entities recognized:" +_entities.size() );
				_relations = this._erModelReader.getERRelations();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	

	
	public void transform(File erModel){	
				if(erModel.exists()){
					ERtoEJBMapper mapper = new ERtoEJBMapper(erModel);
					_templateRunner = new TemplateRunner(mapper.getEJBModel());		
					_templateRunner.runJBossTemplates();
					for (Iterator iter = mapper.getEJBModel().get_ejbs().iterator(); iter.hasNext();) {
						EJB ejb = (EJB) iter.next();
						System.out.println(ejb);	
					}
				}else{
					System.out.println("Couldnt find ER spec...");
				}
	}
	
	public void transform(File erModel, File outDir){	
				if(erModel.exists()){
					ERtoEJBMapper mapper = new ERtoEJBMapper(erModel);		
					_templateRunner = new TemplateRunner(mapper.getEJBModel(), outDir);	
					//_templateRunner.set_outDir(outDir.getAbsolutePath());	
					_templateRunner.runJBossTemplates();
					for (Iterator iter = mapper.getEJBModel().get_ejbs().iterator(); iter.hasNext();) {
						EJB ejb = (EJB) iter.next();
						System.out.println(ejb);	
					}
				}else{
					System.out.println("Couldnt find ER spec...");
					System.out.println("ER Model:" + erModel + " outDir:" + outDir);
				}
	}
	
	/**
	 * This method resolves which entities this entity relates to
	 * @param entity
	 * @return ArrayList of ERRelations
	 */
	private ArrayList resolveRelationsForEREntity(EREntity entity) {
		ArrayList relations = new ArrayList();
		HashMap alreadyInIt = new HashMap();
		for (Iterator iter = _relations.iterator(); iter.hasNext();) {
			ERRelation relation = (ERRelation) iter.next();
			if (relation.get_relationsource().equals(entity.get_entityName())
				&& !alreadyInIt.containsKey(relation.get_relationName())) {
				//				public EJBRelation(String relationFrom, String relationTo, boolean isBidirectional,boolean relationFromIsMany, boolean relationToIsMany){
				EJBRelation ejbRelation =
					new EJBRelation(
						relation.get_relationsource(),
						relation.get_relationTarget(),
						relation.get_direction().equals("bidirectional"),
						(relation.get_sourceCardinality().equals("many")),
						(relation.get_targetCardinality().equals("many")));
				ejbRelation.set_relationSignature(
					relation.get_relationsource(),
					relation.get_relationTarget());
				relations.add(ejbRelation);
				alreadyInIt.put(relation.get_relationName(), relation.get_relationName());
			}
		}
		return removeDuplicates(relations);
	}
	
	/**
	 * This method resolves which other entities relates to this entity
	 * @param entity
	 * @return ArrayList of ERRelations pointing to entity
	 */
	private ArrayList resolveForeignRelationsForEREntity(EREntity entity) {
		ArrayList relations = new ArrayList();
		for (Iterator iter = _relations.iterator(); iter.hasNext();) {
			ERRelation relation = (ERRelation) iter.next();
			if (relation.get_relationTarget().equals(entity.get_entityName())
				&& relation.get_direction().equals("bidirectional")
				&& !relation.get_relationName().equals(entity.get_entityName())) {
				//					public EJBRelation(String relationFrom, String relationTo, boolean isBidirectional,boolean relationFromIsMany, boolean relationToIsMany){
				EJBRelation ejbRelation =
					new EJBRelation(
						relation.get_relationTarget(), //reverse target and source
						relation.get_relationsource(),
						true,
						(relation.get_targetCardinality().equals("many")),//reverse again
						(relation.get_sourceCardinality().equals("many")));
						ejbRelation.set_relationSignature(relation.get_relationsource(),relation.get_relationTarget());
				relations.add(ejbRelation);
			}
		}
		return removeDuplicates(relations);
	}
	
	private ArrayList removeDuplicates(ArrayList list)  {
		Set uniqueEntries = new HashSet();    
		for (Iterator iter = list.iterator(); iter.hasNext(); ) {
			Object element = iter.next();      
			if (!uniqueEntries.add(element)) // if current element is a duplicate,        
				iter.remove();                 // remove it    
		} 
		return list; 
	}
	
	/**
	 * This method construct an ERModel
	 */
	private EJBModel getEJBModel(){
		_ejbModel.set_modelName(this._erModelReader.getModelName());
		_ejbModel.set_basePackagePath(this._packageName);
		for (Iterator iter = this._entities.iterator(); iter.hasNext();) {
			EREntity entity = (EREntity) iter.next();
			EJB ejb = new EJB();
			ejb.set_ejbName(entity.get_entityName());
			ArrayList fields = entity.get_attributes();
			ArrayList ejbFields = new ArrayList();
			for (Iterator iterator = fields.iterator(); iterator.hasNext();) {
				ERAttribute attribute = (ERAttribute) iterator.next();
				ejbFields.add(attribute);
			}
			ejb.set_fields(ejbFields);
			ejb.set_foreignRelations(this.resolveForeignRelationsForEREntity(entity));
			ejb.set_relations(resolveRelationsForEREntity(entity));
			ejb.set_jndiName("ejb/" + this._ejbModel.get_modelName()+"/"+ejb.get_ejbName());
			_ejbModel.addEJB(ejb);
		}
		return _ejbModel;
	}
	
	
	public static void main(String[] args) {
		System.out.println("Running ERtoEJBMapper with parameter:");
		
		File f = null;
		if(args.length>0){
			//use file arg
			f = new File(args[0]);
		}else{
			f = new File("c://eclipse//workspace//ERToEJB//resources//ERSample.xml");
		}	
		if(f.exists()){
			ERtoEJBMapper mapper = new ERtoEJBMapper(f);
			TemplateRunner runner = new TemplateRunner(mapper.getEJBModel());
			runner.runJBossTemplates();
			for (Iterator iter = mapper.getEJBModel().get_ejbs().iterator(); iter.hasNext();) {
				EJB ejb = (EJB) iter.next();
				System.out.println(ejb);	
			}
		}else{
			System.out.println("Couldnt find ER spec...arg:" + f);
		}
}
	/**
	 * @return
	 */
	public TemplateRunner get_templateRunner() {
		return _templateRunner;
	}

}
