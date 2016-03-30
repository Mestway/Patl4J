package org.openfuxml.addon.chart;

import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.openfuxml.xml.addon.chart.Chart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractOfxChart
{
	private final static Logger logger = LoggerFactory.getLogger(AbstractOfxChart.class);
	
	public static enum RenderTarget {web,pdf};
	
	protected Configuration config;
	protected Chart chart;
	protected List<Chart> charts;
	protected RenderTarget target;
	private String uuid;

	public AbstractOfxChart(Configuration config,RenderTarget target)
	{
		this.config=config;
		this.target=target;
		uuid = UUID.randomUUID().toString();
	}
		
	protected void loadXmlChartTemplate(String chartFile) throws FileNotFoundException
	{
		logger.trace("Loading: "+chartFile);
		chart = (Chart)JaxbUtil.loadJAXB(chartFile, Chart.class);
	}
	
/*	public void translate(AhtTranslationFacade fTrans,String lang)
	{
		if(chart!=null)
		{
			for(Axis axis : chart.getAxis())
			{
				if(axis.isSetLabel() && axis.getLabel().isSetKey())
				{
					axis.getLabel().setText(fTrans.t(axis.getLabel().getKey(),lang));
				}
			}
		}
	}
*/	
	public String getUuid() {return uuid;}
	
	public Chart getChart() {return chart;}
	public List<Chart> getCharts() {return charts;}
	
	@SuppressWarnings("unchecked")
	public void addToSession(FacesContext context)
	{
		HttpSession session = (HttpSession)context.getExternalContext().getSession(true);
		
		Map<String,Chart> mCharts = (Map<String,Chart>)session.getAttribute("charts");
		if(mCharts==null){mCharts = new Hashtable<String,Chart>();}
		mCharts.put(uuid, chart);
		session.removeAttribute("charts");
		session.setAttribute("charts", mCharts);
	}
}