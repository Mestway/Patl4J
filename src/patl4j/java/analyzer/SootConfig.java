package patl4j.java.analyzer;

import java.util.List;

import patl4j.handlers.PatlOption;
import soot.PackManager;
import soot.Scene;
import soot.SootClass;
import soot.options.Options;

/*
 * This class is used to set global configuration of soot. 
 * It should be created at the very beginning as a singleton.
 */

public class SootConfig {
	void setClassPath(PatlOption option) {
		String seperator = ":";
		if (option.getPlatform().equals("windows")) {
			seperator = ";";
		}
		
		List<String> classPath = option.getClassPath();
		String concatPath = "";
		for (String st: classPath) {
			concatPath += seperator + st;
		}
		
        String oldPath = Scene.v().getSootClassPath();
        Scene.v().setSootClassPath(oldPath + concatPath);

	}

	/*
	 * @param entryClass the main class of the whole project containing the main function
	 * @param option the patl option class
	 */
	public SootConfig(String entryClass, PatlOption option) {
		setClassPath(option);
		
		Options.v().set_whole_program(true);
        Options.v().setPhaseOption("cg.spark","on");
        Options.v().set_keep_line_number(true);
		
        SootClass s = Scene.v().loadClassAndSupport(entryClass);
        Scene.v().loadNecessaryClasses();
        Options.v().set_main_class(entryClass);
        
        PackManager.v().runPacks();
	}
}
