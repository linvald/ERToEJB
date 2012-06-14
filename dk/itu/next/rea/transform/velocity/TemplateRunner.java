/*
 * @author linvald@it-c.dk 
 * Created on 31-07-2003
  */
package dk.itu.next.rea.transform.velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.Template;
import java.io.*; 
 
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

import dk.itu.next.rea.transform.ejb.EJBModel;

/**
 * @author Jesper Linvald
 * This class takes care of running templates given a Model
 */
public class TemplateRunner {
	private String _outDir = "dk/itu/rea/next/ejbs/";
	private VelocityEngine _vEngine;
	private VelocityContext _context;
	private VelocityContextWriter _writer;
	private EJBModel _ejbModel;
	
	public TemplateRunner(EJBModel model) {
		this._ejbModel = model;
		makeOutDir(new File(""));
		velocityInit();
	}
	
	public TemplateRunner(EJBModel model, File sysOutdir) {
		this._ejbModel = model;
		//this.set_outDir(sysOutdir + "/" + this.get_outDir() + "/");
		makeOutDir(sysOutdir);
		velocityInit();
	}
	
	/**
		 * Should only be called after the _reaModel has been set
		 * Initializes the engine and the puts the model to the context
		 */
		public void velocityInit() {
			try {
				_vEngine = new VelocityEngine();
				_vEngine.init("resources/velocity.properties");
				//tells where the macros are
				_context = new VelocityContext();
				_context.put("ejbmodel", _ejbModel);
				//we need a StringHelper to aid us
				StringHelper stringHelper = new StringHelper();
				_context.put("stringhelper", stringHelper);
				_writer = new VelocityContextWriter(new File(this.get_outDir()));
				_context.put("writer",_writer);		
			}
			catch (ResourceNotFoundException rnfe) {
				System.out.println("Example : error : cannot find template ");
			}
			catch (ParseErrorException pee) {
				System.out.println(
					"Example : Syntax error in template " + ":" + pee);
			}
			catch (Exception e) {
				System.out.println("Exception:" + e);
			}
		}
	
	
	public void runTemplate(String templateName, String fileOutputName)
		throws IOException, Exception {
		try {
			Template t = _vEngine.getTemplate(templateName);
			//Write to stream
			FileWriter writer =
				new FileWriter(this.get_outDir() + fileOutputName);
			t.merge(_context, writer);
			writer.flush();
			writer.close();
		}
		catch (IOException e) {
			throw e;
		}
		catch (Exception e) {
			throw e;
		}
	}
	public void makeOutDir(File f) {
		
		String dir = f.getAbsolutePath() + "/" + this._outDir + "/"+_ejbModel.get_modelName() ;
		System.out.println("Outdir: " + dir);
		File fi = new File(dir);
		this.set_outDir(dir);
		fi.mkdirs();
	}

	
	public void runJBossTemplates(){
		System.out.println("Running template");
		StringHelper stringHelper = new StringHelper();
		//VelocityContextWriter _writer = new VelocityContextWriter();
		//_writer= new VelocityContextWriter();
		//_writer.setDirectory(new File(this.get_outDir()));
		_context = new VelocityContext();
		_context.put("ejbmodel", _ejbModel);
		_context.put("stringhelper", stringHelper);
		_context.put("writer", _writer);
		try {
			Template ejbTemplate = _vEngine.getTemplate("ejb.vm");
			ejbTemplate.merge(_context, _writer);
			
			Template counterTemplate = _vEngine.getTemplate("Counter.vm");
			Template uidTemplate = _vEngine.getTemplate("UIDGenerator.vm");
			
			counterTemplate.merge(_context,_writer);
			uidTemplate.merge(_context,_writer);
			
			_writer.flush();
			_writer.close();
		} catch (IOException e) {
			System.out.println("Couldnt merget");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Couldnt merget");
			e.printStackTrace();
		}
	}
	

	/**
	 * @return
	 */
	public String get_outDir() {
		return _outDir;
	}
	/**
	 * @param string
	 */
	public void set_outDir(String string) {
		System.out.println("Setting outDir in templaterunner:" + string);
		_outDir = string;
	}
}
