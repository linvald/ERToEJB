package dk.itu.next.rea.transform.xslt;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import org.w3c.dom.Document;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import java.io.*;

public class XSLTransformer {

	private File _xmlFile, _xsltFile;

	public XSLTransformer(File xml, File xslt) {
		this._xmlFile = xml;
		this._xsltFile = xslt;
	}
		public void transform() {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

			try {
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document document = builder.parse(this._xmlFile);

				// Use a Transformer for output
				TransformerFactory tFactory = TransformerFactory.newInstance();
				StreamSource stylesource = new StreamSource(this._xsltFile);
				Transformer transformer = tFactory.newTransformer(stylesource);

				DOMSource source = new DOMSource(document);
				StreamResult result = new StreamResult(System.out);
				transformer.transform(source, result);

			} catch (TransformerConfigurationException tce) {
				System.out.println("TransformerConfigurationException:" + tce);

			} catch (TransformerException te) {
				System.out.println("TransformerException:" + te);

			} catch (SAXException sxe) {
				System.out.println("SAXException:" + sxe);

			} catch (ParserConfigurationException pce) {
				System.out.println("ParserConfigurationException:" + pce);

			} catch (IOException ioe) {
				System.out.println("IOException:" + ioe);
			}
		}
	public static void main(String[] args) {
		XSLTransformer transformer = new XSLTransformer(new File("resources//REASpec.xml"), new File("resources//REAToER.xsl"));
		transformer.transform();
	}
}
