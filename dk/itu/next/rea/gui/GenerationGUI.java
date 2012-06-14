package dk.itu.next.rea.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.apache.tools.ant.Project; 

import dk.itu.next.ant.AntModel;
import dk.itu.next.rea.transform.ermodel.ERtoEJBMapper;
import dk.linvald.gui.StatusPanel;
import dk.linvald.gui.LabelTextFieldAndButton;
import dk.linvald.gui.LabelTextFieldAndButtonGroup;
import dk.linvald.gui.StatusPanelOutStreamed;



/**
 * @author Jesper Linvald
 * 
 * Description:
 */
public class GenerationGUI extends JFrame implements ActionListener{
	
	private final String BUILD_FILE = "build.xml";
	
	private LabelTextFieldAndButton _browseXml, _chooseOutputdir;
	private Container _cp;
	private StatusPanelOutStreamed _status;
	private JPanel _browseButtonPanel, _runAntButtonPanel;
	private final String BROWSE_BUTTON_NAME = "ER Specification";
	private final String OUTPUT_BUTTON_NAME = "Output directory";
	private final String GENERATE_BUTTON_NAME = "1. GENERATE";
	private final String RUN_XDOCLET_BUTTON_NAME = "2a.Run XDoclet on generated file";
	private final String RUN_JAR = "2b. Jar application (first run XDoclet)";
	
	/* The path to the specification: choosen by the user */
	private File _pathToERModelFile = null;
	/** Where all generated files go */
	private File _pathToOutPutDir = null;
	/** Class that takes care of parsing the spec and generate *EJB.java files */
	ERtoEJBMapper mapper = null;
	
	private JButton _generateButton, _runXdoclet, _runJar;
	PrintStream aPrintStream;
	

	private AntModel _antModel = null;
	private Project _project;
	private ArrayList _targets;
	private Properties  _propertyFileProperties;
	
	
	public GenerationGUI(){
		super("ER to EJB transformer");
		intiGui();
		initBuildFile();
		initButtons();
		addComponents();
	}
	public void intiGui(){
		 this._cp = this.getContentPane();
		 this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//_cp.setLayout(new GridLayout(3,1));
		_cp.setLayout(new BorderLayout());
		_status = new StatusPanelOutStreamed();
		_status.setPreferredSize(new Dimension(_status.getWidth(),200));
		_status.setSize(_status.getWidth(),500);
		pack();

	}
	

	
	public void initButtons(){
		_browseButtonPanel = new JPanel();
		_runAntButtonPanel = new JPanel();
		
		this._browseXml = new LabelTextFieldAndButton(BROWSE_BUTTON_NAME,BROWSE_BUTTON_NAME);
		this._browseXml.addABorder(true);
		_browseXml.addActionListenerToButton(this);
		
		
		this._chooseOutputdir = new LabelTextFieldAndButton(OUTPUT_BUTTON_NAME,OUTPUT_BUTTON_NAME);
		this._chooseOutputdir.addABorder(true);
		_chooseOutputdir.addActionListenerToButton(this);
		
		_generateButton = new JButton(GENERATE_BUTTON_NAME);
		_generateButton.setName(GENERATE_BUTTON_NAME);
		_generateButton.addActionListener(this);
		
		this._runXdoclet = new JButton (RUN_XDOCLET_BUTTON_NAME);
		this._runXdoclet.setName(RUN_XDOCLET_BUTTON_NAME);
		this._runXdoclet.addActionListener(this);
		
		this._runJar = new JButton (RUN_JAR);
		this._runJar.setName(RUN_JAR);
		this._runJar.addActionListener(this);
		
		_runAntButtonPanel.setLayout(new BorderLayout());
		_runAntButtonPanel.add(_generateButton,BorderLayout.WEST);
		_runAntButtonPanel.add(_runXdoclet, BorderLayout.CENTER);
		_runAntButtonPanel.add(_runJar, BorderLayout.EAST);

	}
	
	private void addComponents(){
		_browseButtonPanel.setLayout(new BorderLayout());
		_browseButtonPanel.add(_browseXml, BorderLayout.NORTH);
		_browseButtonPanel.add(_chooseOutputdir, BorderLayout.SOUTH);
		_cp.add(_runAntButtonPanel, BorderLayout.CENTER);
		_cp.add(_browseButtonPanel, BorderLayout.NORTH);
		_cp.add(_status, BorderLayout.SOUTH);
		this.setVisible(true);
	}
	
	
	public void initBuildFile(){
			_antModel = new AntModel(new File(BUILD_FILE),this._status);
			this._project = _antModel.get_project();		
			this._targets = this._antModel.getTargetsList();
			//get the jar and xdoclet target - others are "private"
			//this.populateDropDown(this.comboTargets, this._targets);

		/*	this._propertyFileProperties = this._antModel.getAntFileProperties();
			if(this._propertyFileProperties.size()>0){
			  for (Enumeration en = _propertyFileProperties.keys(); en.hasMoreElements(); ) {
				String key = (String) en.nextElement();
				this.comboPropertyFileProperties.addItem(key);
			  }
			}
			else{
			  this.comboPropertyFileProperties.addItem("No propertyfile found");
			}
				this._inlineProperties =  this._antModel.getInlineProperties();
						if(this._inlineProperties.size()>0){
						  for (Enumeration en = _inlineProperties.keys(); en.hasMoreElements(); ) {
								String key = (String) en.nextElement();
								this.comboProperties.addItem(key);
						  }
						}
						else{
						  this.comboPropertyFileProperties.addItem("No propertyfile found");
						}
				intializeDebugLevelDropDown();
				*/

	}
	
	
	public Dimension getPreferredSize(){
		return new Dimension(600,350);
	}
	
	public static void main(String[] args) {
		new GenerationGUI();
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if(src instanceof JButton){
			JButton b = (JButton)src;
			String name = b.getName();
			if (name.equals(BROWSE_BUTTON_NAME)) {
				JFileChooser chooser = new JFileChooser();
				int returnVal = chooser.showOpenDialog(getContentPane());
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					_pathToERModelFile = chooser.getSelectedFile();
					_browseXml.setText(_pathToERModelFile.toString());
					String path;
					String format="";
					try {
						path = _pathToERModelFile.getCanonicalPath();
						format = path.replaceAll("\\\\","//");
					} catch (IOException e2) {
						System.err.println("IOException:" + e2);
					}
					_antModel.updateProperty("erspec",format);
					mapper = new ERtoEJBMapper(_pathToERModelFile);
				}
			}else if(name.equals(OUTPUT_BUTTON_NAME)){
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = chooser.showOpenDialog(getContentPane());
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					_pathToOutPutDir = chooser.getSelectedFile();
					_chooseOutputdir.setText(_pathToOutPutDir.toString());
					//set the property in the ant.properties so that ant makes use of it
					//format
					String path;
					String format="";
					try {
						path = _pathToOutPutDir.getCanonicalPath();
						format = path.replaceAll("\\\\","//");
					} catch (IOException e2) {
						System.err.println("IOException:" + e2);
					}
					_antModel.updateProperty("output.dir",format);
					
				}
			}else if(name.equals(GENERATE_BUTTON_NAME)){
				if(_pathToOutPutDir == null || _pathToERModelFile == null){
					_status.setStatus("You must select both an output dir and a specification file...");
				}else{
					mapper.transform(this._pathToERModelFile, this._pathToOutPutDir);
				}			
			}else if(name.equals(RUN_XDOCLET_BUTTON_NAME)){
				_antModel.executeTarget("xdoclet");
			}else if(name.equals(RUN_JAR)){
				mapper.transform(this._pathToERModelFile, this._pathToOutPutDir);
				_antModel.executeTarget("jar");
			}
		}
		if(src.equals(this._browseXml)){
			System.out.println("source is browsexml");
		}
	}
}
