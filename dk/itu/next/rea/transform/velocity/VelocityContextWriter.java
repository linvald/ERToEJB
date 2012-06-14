package dk.itu.next.rea.transform.velocity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * @author linvald
 * 
 * This class allows for chaning the file written to from within the template.
 * Put a VelocityContextWriter object into to the context and call changeOutput("fileName");
 * To start writing to another file
 */
public class VelocityContextWriter extends Writer {
	
	private Writer writer;
	private File directory;
	
	/**
	 * Velocity will call write emmidiately after Template.merge which is why
	 * we need a default filename - the file is deleted on writer.close(); 
	 */ 
	private String fileName="dummy.txt"; 
	private File emptyFile;

	public VelocityContextWriter() {
		super();
		try {
			emptyFile = File.createTempFile("dummy",null);
			writer = new FileWriter(emptyFile);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public VelocityContextWriter(File f) {
		this();
		this.directory = f;		
	}
// ---------- WRITER METHODS ----------------------
	/**
	 * @param lock
	 */
	/*public VelocityContextWriter(Object lock) {
		super(lock);
	}*/

	/* (non-Javadoc)
	 * @see java.io.Writer#write(char[], int, int)
	 */
	public void write(char[] cbuf, int off, int len) throws IOException {
		if(writer != null)
			writer.write(cbuf, off, len);
	}

	/* (non-Javadoc)
	 * @see java.io.Writer#flush()
	 */
	public void flush() throws IOException {
		writer.flush();
	}

	/* (non-Javadoc)
	 * @see java.io.Writer#close()
	 */
	public void close() throws IOException {
		writer.close();
		//delete the default file
		if(emptyFile.isFile()){
			emptyFile.delete();
		}
		
	}
//----------- NEW METHODS --------------------------

public void changeOutput(String fileName){
	this.fileName = fileName;
	try {
		flush();
		close();
		
		if(this.getDirectory() != null){
			File f = new File(this.getDirectory().getAbsolutePath()+"/"+this.fileName);
			writer = new FileWriter(f);
		}else{
			writer = new FileWriter(fileName);
		}	
	}
	catch (IOException e) {
		e.printStackTrace();
	}
}

	/**
	 * @return
	 */
	public File getDirectory() {
		return directory;
	}

	/**
	 * @param file
	 */
	public void setDirectory(File file) {
		System.out.println("Context writer will be writing ejbs to:" + file.getAbsolutePath());
		directory = file;
	}

}
