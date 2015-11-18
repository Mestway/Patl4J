package org.openfuxml.addon.chart.renderer.gantt;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.exlp.util.DateUtil;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.SymbolAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.gantt.XYTaskDataset;
import org.jfree.data.time.SimpleTimePeriod;
import org.jfree.data.xy.IntervalXYDataset;
import org.openfuxml.addon.chart.interfaces.ChartRenderer;
import org.openfuxml.addon.chart.renderer.generic.XYPlotRenderer;
import org.openfuxml.addon.chart.util.ChartColorFactory;
import org.openfuxml.addon.chart.util.ChartLabelResolver;
import org.openfuxml.xml.addon.chart.Chart;
import org.openfuxml.xml.addon.chart.DataSet;
import org.openfuxml.xml.addon.chart.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GanttChartRenderer extends XYPlotRenderer implements ChartRenderer
{
	final static Logger logger = LoggerFactory.getLogger(GanttChartRenderer.class);
	
	public GanttChartRenderer()
	{
		
	}
	
	public JFreeChart render(Chart ofxChart)
	{
		this.ofxChart=ofxChart;
		setTimePeriod();
		
		IntervalXYDataset dataset;
		dataset = new XYTaskDataset(createTasks(ofxChart.getDataSet()));
//		dataset = new XYTaskDataset(createTasksDummy());
		
		chart = ChartFactory.createXYBarChart(
				ChartLabelResolver.getTitle(ofxChart),
				ChartLabelResolver.getAxisLabelY(ofxChart),
				false,
				ChartLabelResolver.getAxisLabelX(ofxChart),
				dataset,
                PlotOrientation.HORIZONTAL,
                ofxChart.isLegend(),
                false, false);

        chart.setBackgroundPaint(Color.white);
        
        setTaskNames();  
        
        Map<String,Color> colorMap = ChartColorFactory.getColorMap(ofxChart.getColors(), "task");
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setRenderer(new ColorTaskXYBarRenderer(colorMap));
        XYBarRenderer renderer = (XYBarRenderer) plot.getRenderer();
        renderer.setUseYInterval(true);
        renderer.setShadowVisible(false);
        renderer.setBarPainter(new ColorTaskGradientXYBarPainter());
//      renderer.setBaseItemLabelsVisible(true);
//      renderer.setBaseItemLabelGenerator(new StandardXYItemLabelGenerator());
        
        setAxis();
        
        setColors();
        setGrid();
        
        return chart;
	}
	
	private void setTaskNames()
	{
		 XYPlot plot = (XYPlot) chart.getPlot();
		 String[] taskNames = new String[ofxChart.getDataSet().size()];
		 
		 int i=0;
		 for(DataSet c : ofxChart.getDataSet())
		 {
			 taskNames[i] = c.getLabel();
			 i++;
		 } 
		 
		 SymbolAxis yAxis = new SymbolAxis(ChartLabelResolver.getAxisLabelY(ofxChart),taskNames);
	     yAxis.setGridBandsVisible(true);
	     plot.setDomainAxis(yAxis);
	     
	     plot.setDomainAxis(yAxis);
	}
	
	@SuppressWarnings("unused")
	private TaskSeriesCollection createTasksDummy()
	{
		Date from,to;
		SimpleTimePeriod stp;
        TaskSeriesCollection dataset = new TaskSeriesCollection();
  
        TaskSeries s1 = new TaskSeries("Team A");
        from = DateUtil.getDateFromInt(2010, 1, 1);
		to = DateUtil.getDateFromInt(2010, 1, 15);
		stp = new SimpleTimePeriod(from,to);
        s1.add(new Task("na", stp));
        dataset.add(s1);
/*   
               TaskSeries s2 = new TaskSeries("Team B");
        s2.add(new Task("T2a", new Hour(13, new Day())));
        s2.add(new Task("T2b", new Hour(19, new Day())));
        s2.add(new Task("T2c", new Hour(21, new Day())));
        dataset.add(s2);
        
        TaskSeries s3 = new TaskSeries("Team C");
        s3.add(new Task("T3a", new Hour(13, new Day())));
        s3.add(new Task("T2b", new Hour(19, new Day())));
        s3.add(new Task("T3c", new Hour(21, new Day())));
        dataset.add(s3);
*/
        TaskSeries s4 = new TaskSeries("Team D");
       
        from = DateUtil.getDateFromInt(2010, 1, 1);
		to = DateUtil.getDateFromInt(2010, 1, 15);
		stp = new SimpleTimePeriod(from,to);
        s4.add(new Task("na", stp));
        
        from = DateUtil.getDateFromInt(2010, 1, 17);
		to = DateUtil.getDateFromInt(2010, 1, 21);
		stp = new SimpleTimePeriod(from,to);
        s4.add(new Task("holiday", stp));
        
        dataset.add(s4);
               
        return dataset;
    }
	
	private TaskSeriesCollection createTasks(List<DataSet> container)
	{
		TaskSeriesCollection dataset = new TaskSeriesCollection();
		
		for(DataSet c: container)
		{	
			TaskSeries ts = new TaskSeries(c.getLabel());
			for(Data d : c.getData())
			{
				Date from = DateUtil.getDate4XmlGc(d.getFrom());
				Date to = DateUtil.getDate4XmlGc(d.getTo());
				SimpleTimePeriod stp = new SimpleTimePeriod(from,to);
				Task t = new Task(d.getCategory(),stp);
				ts.add(t);
			}
			dataset.add(ts);
		}
		return dataset;
	}
	
	@Override
	public Dimension getSuggestedSize()
	{
		Dimension d = new Dimension();
		d.setSize(0, 75+(ofxChart.getDataSet().size()*25));
		return d;
	}
}
