package org.openfuxml.addon.wiki.media.chart.factory;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.XPath;
import org.dom4j.DocumentHelper;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYIntervalSeries;
import org.jfree.data.xy.XYIntervalSeriesCollection;
import org.openfuxml.addon.wiki.data.jaxb.Ofxchart;
import org.openfuxml.addon.wiki.media.chart.ChartXmlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Deprecated
public class BarChartFactory extends AbstractChartFactory
{
	final static Logger logger = LoggerFactory.getLogger(BarChartFactory.class);
	
	public BarChartFactory()
	{

	}

	private IntervalXYDataset createDataset(Ofxchart ofxChart)
	{
		XYIntervalSeriesCollection dataset = new XYIntervalSeriesCollection();
		Document doc = ChartXmlUtil.loadChart(ofxChart); 
		try
		{
			StringBuffer sbXpDataSeries = new StringBuffer();
			sbXpDataSeries.append("/ofxchart/ofxchartcontainer[@type='dataseries']");
			XPath xPath = DocumentHelper.createXPath(sbXpDataSeries.toString()); 
			List<?> lDataSeries = xPath.selectNodes(doc); 
			logger.debug("xpath.DataSeries = "+lDataSeries.size());
			for(int i=1;i<=lDataSeries.size();i++)
			{
				StringBuffer sbXpDataSets = new StringBuffer();
				sbXpDataSets.append(sbXpDataSeries);
				sbXpDataSets.append("["+i+"]");
				sbXpDataSets.append("/ofxchartcontainer[@type='dataset']");
				xPath = DocumentHelper.createXPath(sbXpDataSets.toString()); 
				List<?> lDataSets = xPath.selectNodes(doc); 
				logger.debug("xpath.lDataSets = "+lDataSets.size());
				if(lDataSets!=null && lDataSets.size()>0)
				{
					XYIntervalSeries  series = new XYIntervalSeries(i);
					for(int j=1;j<=lDataSets.size();j++)
					{
						StringBuffer sbXpDataSet = new StringBuffer();
						sbXpDataSet.append(sbXpDataSets);
						sbXpDataSet.append("["+j+"]");
						sbXpDataSet.append("/ofxchartdata");
						
						double X = getChartValue(sbXpDataSet.toString(), "X", doc);
						double minY = getChartValue(sbXpDataSet.toString(), "minY", doc);
						double maxY = getChartValue(sbXpDataSet.toString(), "maxY", doc);
						
						series.add(X,X-3,X+3,minY,minY,maxY);
					}
					dataset.addSeries(series);
				}
			}
		}
		catch (JDOMException e) {logger.error("",e);}
        return dataset;
    }
	

	public JFreeChart createChart(Ofxchart ofxChart)
	{
		 JFreeChart chart = ChartFactory.createXYBarChart(
		            "XYBarChartDemo6",
		            "X",
		            false,
		            "Y",
		            createDataset(ofxChart),
		            PlotOrientation.VERTICAL,
		            false,
		            false,
		            false
		        );
		 
		        XYPlot plot = (XYPlot) chart.getPlot();
		        XYBarRenderer renderer = (XYBarRenderer) plot.getRenderer();
		        logger.debug("Dont know if this works ...");
		        renderer.setBarPainter(new StandardXYBarPainter());	
		        renderer.setUseYInterval(true);
		        plot.setRenderer(renderer);  // workaround to update axis range

		        return chart;
	}
}
