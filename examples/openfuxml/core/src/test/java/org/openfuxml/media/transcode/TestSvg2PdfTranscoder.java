package org.openfuxml.media.transcode;

import java.io.File;

import org.junit.Before;
import org.openfuxml.content.media.Media;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.test.AbstractOfxCoreTest;
import org.openfuxml.test.OfxCoreTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestSvg2PdfTranscoder extends AbstractOfxCoreTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestSvg2PdfTranscoder.class);

	private Media media;
	private Svg2PdfTranscoder transcoder;
	
	@Before
	public void init()
	{
		File fTarget = new File("target");
		transcoder = new Svg2PdfTranscoder(fTarget);
		
		media = new Media();
		media.setSrc("data/svg/under-construction.svg");
		media.setDst("test");
	}
	
	public void test() throws OfxAuthoringException
	{
		transcoder.transcode(media);
	}
	
	
	public static void main(String[] args) throws Exception
    {
    	OfxCoreTestBootstrap.init();
			
    	TestSvg2PdfTranscoder test = new TestSvg2PdfTranscoder();
    	test.init();
    	test.test();
    }
}