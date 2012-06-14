package dk.itu.next.rea.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import dk.itu.next.rea.transform.ermodel.ERtoEJBMapper;
import dk.linvald.gui.StatusPanel;


public class BrowserPanel extends JPanel implements ActionListener {
  
	JLabel jLabel2 = new JLabel();
  JButton _butBrowseTargetLocation = new JButton();
  JTextField _textERFileLocation = new JTextField();
  JTextField _textTargetLocation = new JTextField();
  JButton _butBrowsER = new JButton();
  JButton _butGenerate = new JButton();
  JLabel jLabel1 = new JLabel();
  JCheckBox _checkUtil = new JCheckBox();
  JCheckBox _checkValueObj = new JCheckBox();
  JCheckBox _checkJar = new JCheckBox();
  JCheckBox _checkJBoss = new JCheckBox();
  JLabel jLabel3 = new JLabel();
  JTextField _textDeployTo = new JTextField();
  JButton _butBrowseDeployTo = new JButton();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  ERtoEJBMapper mapper = null;
  
  private final String BROWSE_BUTTON_NAME = "Browse specification";
  private final String OUTPUT_BUTTON_NAME = "Output dir";
  private final String GENERATE_BUTTON_NAME = "GENERATE";
  private final String DEPLOY_DIR = "Browse deploydir";
  private final String UTIL = "util";
  private final String VALUEOBJ = "VO";
  private final String JAR = "jar";
  private final String JBOSS = "jboss";
  StatusPanel _status;
  ConfigObj config;
  
  public BrowserPanel(StatusPanel status) {
  	this._status = status;
	try {
	  jbInit();
	}
	catch(Exception ex) {
	  ex.printStackTrace();
	}
	config = new ConfigObj();
  }
  void jbInit() throws Exception {
	jLabel1.setText("Location of ER specification");
	_butGenerate.setText("Generate");
	_butGenerate.setName(GENERATE_BUTTON_NAME);
	_butGenerate.addActionListener(this);
	_butBrowsER.setText("Browse");
	_butBrowsER.addActionListener(this);
	_butBrowsER.setName(BROWSE_BUTTON_NAME);
	_textTargetLocation.setText("");
	_textERFileLocation.setText("");
	_butBrowseTargetLocation.setText("Browse");
	_butBrowseTargetLocation.setName(OUTPUT_BUTTON_NAME);
	_butBrowseTargetLocation.addActionListener(this);
	jLabel2.setText("Output to");
	this.setLayout(gridBagLayout1);
	_checkUtil.setText("Util object");
	_checkUtil.addActionListener(this);
	_checkValueObj.addActionListener(this);
	_checkJar.addActionListener(this);
	_checkJBoss.addActionListener(this);
	_checkUtil.setName(UTIL);
	_checkUtil.setSelected(true);
	_checkValueObj.setText("Value object");
	_checkValueObj.setName(VALUEOBJ);
	_checkValueObj.setSelected(true);
	_checkJar.setText("Make jar");
	_checkJar.setName(JAR);
	_checkJBoss.setText("JBoss deployment descriptors");
	_checkJBoss.setName(JBOSS);
	_checkJBoss.setSelected(true);
	jLabel3.setToolTipText("");
	jLabel3.setText("Deploy to");
	_textDeployTo.setText("");
	_butBrowseDeployTo.setText("Browse");
	_butBrowseDeployTo.setName(DEPLOY_DIR);
	_butBrowseDeployTo.addActionListener(this);
		
	this.add(jLabel1,  new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(15, 18, 0, 6), 36, 7));
	this.add(jLabel2,  new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(12, 18, 0, 41), 90, 6));
	this.add(_butBrowseTargetLocation,  new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0
			,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(14, 11, 0, 18), 60, -2));
	this.add(_textERFileLocation,  new GridBagConstraints(2, 0, 2, 1, 1.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(15, 11, 0, 0), 297, 3));
	this.add(_textTargetLocation,  new GridBagConstraints(2, 1, 2, 1, 1.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(12, 11, 0, 0), 298, 3));
	this.add(_butBrowsER,  new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0
			,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(15, 10, 0, 18), 61, -2));
	this.add(_checkUtil,  new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
			,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(21, 18, 0, 0), 0, 0));
	this.add(_checkValueObj,  new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
			,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(22, 22, 0, 0), 0, 0));
	this.add(_checkJBoss,  new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0
			,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(23, 17, 0, 0), 0, 0));
	this.add(_checkJar,  new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0
			,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(23, 0, 0, 56), 0, 0));
	this.add(_butGenerate,  new GridBagConstraints(0, 4, 2, 1, 0.0, 0.0
			,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(6, 18, 16, 13), 83, -2));
	this.add(jLabel3,  new GridBagConstraints(0, 3, 2, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(23, 24, 0, 61), 66, 6));
	this.add(_textDeployTo,  new GridBagConstraints(2, 3, 2, 1, 1.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(15, 10, 0, 0), 295, 5));
	this.add(_butBrowseDeployTo,   new GridBagConstraints(4, 3, 1, 1, 0.0, 0.0
			,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(16, 8, 0, 18), 57, 0));
  }
	
	public void setValues(){
		config._checkJar = this._checkJar.isSelected();
		config._checkJBoss = this._checkJBoss.isSelected();
		config._checkUtil = this._checkUtil.isSelected();
		config._checkValueObj = this._checkValueObj.isSelected();
	}
	
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if(src instanceof JButton){
			JButton b = (JButton)src;
			String name = b.getName();
			if (name.equals(BROWSE_BUTTON_NAME)) {
				JFileChooser chooser = new JFileChooser();
				int returnVal = chooser.showOpenDialog(this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					config._ERSpecfile = chooser.getSelectedFile();
					this._textERFileLocation.setText(config._ERSpecfile.toString());
					mapper = new ERtoEJBMapper(config._ERSpecfile);
				}
			}else if(name.equals(OUTPUT_BUTTON_NAME)){
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = chooser.showOpenDialog(this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					this.config._outputLocaction = chooser.getSelectedFile();
					this._textTargetLocation.setText(config._outputLocaction.toString());
				}
			}else if(name.equals(GENERATE_BUTTON_NAME)){
				if(config._outputLocaction == null || config._ERSpecfile == null){
					_status.setStatus("You must select both an output dir and a specification file...");
				}
				mapper.transform(config._ERSpecfile, config._outputLocaction);
			}else if (name.equals(DEPLOY_DIR)) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = chooser.showOpenDialog(this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					config._jbossDeployDir = chooser.getSelectedFile();
					this._textDeployTo.setText(config._jbossDeployDir.toString());
				}

			}
		}else if(src instanceof JCheckBox){
			JCheckBox box =(JCheckBox)src;
			String name = box.getName();
			if(name.equals(UTIL)){
				config._checkUtil = this._checkUtil.isSelected();
			}else if(name.equals(VALUEOBJ)){
				config._checkValueObj = this._checkValueObj.isSelected();
			}else if(name.equals(JAR)){
				config._checkJar = this._checkJar.isSelected();
			}else if(name.equals(JBOSS)){
				config._checkJBoss = this._checkJBoss.isSelected();
			}
		}

	}
}