package goofs.fs;

import java.nio.ByteBuffer;

import junit.framework.TestCase;

public class FileTestCase extends TestCase {

	protected File file;
	
	protected void setUp() throws Exception {
		super.setUp();

		file = new File(null, "hello", 0777, "hello world") {

			@Override
			public int save() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public int delete() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public int rename(Dir newParent, String name) {
				// TODO Auto-generated method stub
				return 0;
			}
			
		};

	}

	protected void tearDown() throws Exception {
		super.tearDown();

		file = null;
	}

	public void testRead() throws Exception {

		ByteBuffer buf = ByteBuffer.allocate(file.getContent().length);

		file.read(buf, 6);

		String bufStr = new String(buf.array());

		assert (bufStr.equals("world"));

	}

	public void testWrite() throws Exception {

		ByteBuffer buf = ByteBuffer.wrap("mello".getBytes());

		file.write(false, buf, 6);

		assert (file.getContent().equals("hello mellow"));

	}

}
