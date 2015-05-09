package org.openfuxml.test.processor.latex.table;

import java.io.FileNotFoundException;
import java.util.List;

import net.sf.exlp.io.LoggerInit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.content.factory.ofx.table.ColumnFactory;
import org.openfuxml.content.ofx.layout.Alignment;
import org.openfuxml.content.ofx.table.Columns;
import org.openfuxml.renderer.data.exception.OfxConfigurationException;
import org.openfuxml.renderer.data.exception.OfxInternalProcessingException;
import org.openfuxml.renderer.processor.latex.content.table.util.LatexTabluar;

public class TstLatexTabular
{
	final static Logger logger = LoggerFactory.getLogger(TstLatexTabular.class);
	
	private LatexTabluar latexTabular;
	
	public TstLatexTabular()
	{
		Columns columns = new Columns();
		columns.getColumn().add(ColumnFactory.create(Alignment.LEFT,1));
		columns.getColumn().add(ColumnFactory.create(Alignment.LEFT,3));
		
		latexTabular = new LatexTabluar(columns);
	}
	
	public void calculations()
	{
		List<String> lCalculations = latexTabular.getLatexLengthCalculations();
		for(String s : lCalculations)
		{
			logger.debug(s);
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException, OfxConfigurationException, OfxInternalProcessingException
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		TstLatexTabular test = new TstLatexTabular();
		test.calculations();
    }
}