package org.openfuxml.addon.chart.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.exlp.util.xml.JaxbUtil;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.openfuxml.addon.chart.OfxChartRenderer;
import org.openfuxml.xml.addon.chart.Chart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxChart extends HttpServlet
{
	final static Logger logger = LoggerFactory.getLogger(OfxChart.class);
	
	private static final long serialVersionUID = 1;
	
	public void doGet(HttpServletRequest request , HttpServletResponse response) throws ServletException
	{
		chart(request,response);
	}
	public void doPost(HttpServletRequest request , HttpServletResponse response) throws ServletException
	{
		chart(request,response);
	}
	
	@SuppressWarnings("unchecked")
	private void chart(HttpServletRequest request , HttpServletResponse response)
	{
		String uuid = new String(request.getParameter("uuid"));
		try
		{
			OutputStream out = response.getOutputStream();
			HttpSession session = request.getSession();
			
			Map<String,Chart> mCharts = (Map<String,Chart>)session.getAttribute("charts");
			Chart chart = mCharts.get(uuid);
			
//			JaxbUtil.debug(chart, new MwiNsPrefixMapper());
			
			OfxChartRenderer ofxRenderer = new OfxChartRenderer();
			JFreeChart jfreeChart = ofxRenderer.render(JaxbUtil.toDocument(chart));

			response.setContentType("image/png");
			ChartUtilities.writeChartAsPNG(out,jfreeChart,chart.getDimension().getWidth(),chart.getDimension().getHeight());
		}
		catch (IOException e) {e.printStackTrace();}
	}
}