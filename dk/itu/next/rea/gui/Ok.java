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
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import dk.itu.next.ant.AntBrowser;
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
public class Ok extends JFrame {


	private Container _cp;
	private StatusPanelOutStreamed _status;
	private BrowserPanel _panel;
	private JComboBox _comboTargets = new JComboBox();
	private JButton bExecuteTargets = new JButton();
	PrintStream aPrintStream;
	private ArrayList _targets;
	private AntModel _antModel;
	private AntBrowser _antBrowser;
	
	public Ok(){
		super("ER to EJB transformer");
		intiGui();		
		_status = new StatusPanelOutStreamed();
		_antModel = new AntModel(new File("build.xml"),_status);	
		this._targets = this._antModel.getTargetsList();
		this.populateDropDown(this._comboTargets, this._targets);
	}
	public void intiGui(){
		 this._cp = this.getContentPane();
		 this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_cp.setLayout(new BorderLayout());
		
		_panel = new BrowserPanel(_status);
		_cp.add(_panel, BorderLayout.NORTH);
		_cp.add(_comboTargets, BorderLayout.CENTER);
		_cp.add(_status, BorderLayout.SOUTH);
		pack();
		this.setVisible(true);

	}

	public void populateDropDown(JComboBox combo, ArrayList items){
	  for(int i = 0 ; i<items.size(); i++){
		if(items.get(i).equals("xdoclet"))
		combo.addItem(items.get(i));
	  }
	}
	
	public void addAntTargets(Hashtable table){
	  this._comboTargets.removeAllItems();
	   for (Enumeration en = table.keys(); en.hasMoreElements(); ) {
		 String key = (String) en.nextElement();
		 this._comboTargets.addItem(key);
	   }
	}
	
	
	public Dimension getPreferredSize(){
		return new Dimension(800,500);
	}
	
	public static void main(String[] args) {
		new Ok();
	}


}
