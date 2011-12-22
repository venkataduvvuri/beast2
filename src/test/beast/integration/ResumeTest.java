package test.beast.integration;

import junit.framework.TestCase;

import org.junit.Test;

import beast.core.Logger;
import beast.core.MCMC;
import beast.util.Randomizer;
import beast.util.XMLParser;

import java.io.File;

/** check that a chain can be resumed after termination **/
public class ResumeTest  extends TestCase {
	
	final static String XML_FILE = "testHKY.xml";
	
	@Test
	public void test_ThatXmlExamplesRun() throws Exception {
		Randomizer.setSeed(127);
		Logger.FILE_MODE = Logger.FILE_OVERWRITE;
		String dir = System.getProperty("user.dir") + "/examples";
		String fileName = dir + "/" + XML_FILE;

		System.out.println("Processing " + fileName);
		XMLParser parser = new XMLParser();
		beast.core.Runnable runable = parser.parseFile(new File(fileName));
		runable.setStateFile("tmp.state", false);
		if (runable instanceof MCMC) {
			MCMC mcmc = (MCMC) runable;
			mcmc.setInputValue("preBurnin", 0);
			mcmc.setInputValue("chainLength", 1000);
			mcmc.run();
		}
		System.out.println("Done " + fileName);

		System.out.println("Resuming " + fileName);
		Logger.FILE_MODE = Logger.FILE_APPEND;
		parser = new XMLParser();
		runable = parser.parseFile(new File(fileName));
		runable.setStateFile("tmp.state", true);
		if (runable instanceof MCMC) {
			MCMC mcmc = (MCMC) runable;
			mcmc.setInputValue("preBurnin", 0);
			mcmc.setInputValue("chainLength", 1000);
			mcmc.run();
		}
		System.out.println("Done " + fileName);
	} // test_ThatXmlExamplesRun

} // class ResumeTest
