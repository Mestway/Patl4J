/*
 * Created on 05.11.2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.openfuxml.communication.cluster.sync.ssh;

/**
 * This exception signals a failure in the production system which does not 
 * result from a build error in the ANT build file. 
 * This exception should be thrown if proper communication with the production server
 * can not be established.
 */
public class SshCheckerException extends Exception
{
	static final long serialVersionUID=1;
	/**
	 * 
	 */
	public SshCheckerException() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public SshCheckerException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public SshCheckerException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public SshCheckerException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
