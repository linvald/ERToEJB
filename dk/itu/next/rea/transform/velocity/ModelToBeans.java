package dk.itu.next.rea.transform.velocity;



/**
 * This is the main entry point into setting things in motion
 * It contains the main method from which args are read (specifying model.xml etc. @see ReaXmlToReaModel)
 * and then runs all the Velocity templates
 * 
 * Given a Rea Model (generated from xml-spec through ReaXmlToReaModel)
 * this class takes care of generating beans etc. through invoking the correct Velocity templates
 * according to deploytarget etc.
 * 
 * To models for this endaevour exists:
 * <ul>
 * <li/>Having multible templates for each beanentity - ie. one for deployment, one for remote interfaces etc.
 * <li/>Maintaining one template in which XDoclet tags are generated from which all other files are generated
 * by XDoclet - We use the last one
 * </ul>
 * @author linvald@it-c.dk
 *
 */
public class ModelToBeans {
	

	private int _deployTarget;
	private String _reaXmlFile = "ReaModel.xml";//default overridden by args
	private TemplateRunner templateRunner = null;
	/**  
	 * If debug we print generated code to console
	 * - only usefull with small models
	 */
	public static boolean DEBUG = false; //to a file or to the console
	
	public ModelToBeans(){
		
	}

	/**
	 * Please observe the interdepencies between args, ModelToBeans and ReaXmlToReaModel
	 * ReaXmlToReaModel depends on args specifying the input xml (ArgsResolver)
	 * These are observed in the order in which the classes/methods are put to action in main
	 * @param args
	 */
	public static void main(String[] args) {
		// todo specify args[1] to be deploytargets
		ModelToBeans mtb = new ModelToBeans();

		mtb.runTemplateTransformations();
		
	}


	
	/**
	 * Runs all the templates specific to _deployTarget
	 * Each enumerated item in class DeployTarget should have a 
	 * corresponding switch-entry
	 * 
	 * - it either writes to console or files depending on DEBUG-switch
	 * 	 resolved by ArgsResolver.java
	 */
	public void runTemplateTransformations() {
		//FileMaker maker = null;
		switch (_deployTarget) {
			case ModelToBeans.DeployTarget.DEPLOY_TARGET_JBOSS :
				System.out.println("Generating beans specific to JBoss");
				//generate jboss specific
				templateRunner.runJBossTemplates();
				
			break;

			case ModelToBeans.DeployTarget.DEPLOY_TARGET_JRUN :
				System.out.println("Generating beans specific to JRun");
				System.out.println("No templates available..");
				break;
			default :
				break;
		}
	}

	/**
	 * Enumeration of the possible deploytargets 
	 * - if you add an entry also add a switch-entry in the runAllTemplates()
	 * 	 that runs the templates specific to the deploytarget
	 * @author Next.rea
	 */
	public class DeployTarget{
		//possible to add other targets -  add a case to the switch in generateDeploymentDescriptor()
		public final static int DEPLOY_TARGET_JBOSS = 1;
		public final static int DEPLOY_TARGET_JRUN = 2;	
	}
	/**
	 * @return the xml file containing the reamodel
	 */
	public String get_reaXmlFile() {
		return _reaXmlFile;
	}

	/**
	 * @param string -the path to the xmlfile
	 */
	public void set_reaXmlFile(String string) {
		_reaXmlFile = string;
	}

	/**
	 * @return the deploytarget
	 */
	public int get_deployTarget() {
		return _deployTarget;
	}
}
	


