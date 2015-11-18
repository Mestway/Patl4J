package org.openfuxml.addon.chart.util;

import java.awt.Color;
import java.awt.Paint;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxCustomPaintColors
{
	final static Logger logger = LoggerFactory.getLogger(OfxCustomPaintColors.class);
	
	private Map<Integer,Integer> mapColorIndex;
	private List<Color> colors;
	
	public OfxCustomPaintColors()
	{
		mapColorIndex = new Hashtable<Integer,Integer>();
	}
	
	public Paint getSeriesPaint(int series)
	{
		Paint result;
		int index = series;
		if(mapColorIndex.size()>0)
		{
			index=mapColorIndex.get(series);
		}
//		logger.debug(index+" -> "+index%getColors().size());
		result = getColors().get(index%getColors().size());
		return result;
	}
	
	public void addColorMapping(int series, int indexColor)
	{
		mapColorIndex.put(series, indexColor);
	}
	
	private List<Color> getColors()
	{
		if(colors==null)
		{
			colors = new ArrayList<Color>();
			colors.add(Color.RED);
			colors.add(Color.BLUE);
			colors.add(Color.GREEN);
			colors.add(Color.ORANGE);
		}
		return colors;
	}
}
