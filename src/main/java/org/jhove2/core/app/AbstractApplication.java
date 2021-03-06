/**
 * JHOVE2 - Next-generation architecture for format-aware characterization
 *
 * Copyright (c) 2009 by The Regents of the University of California,
 * Ithaka Harbors, Inc., and The Board of Trustees of the Leland Stanford
 * Junior University.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * o Redistributions of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 *
 * o Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * o Neither the name of the University of California/California Digital
 *   Library, Ithaka Harbors/Portico, or Stanford University, nor the names of
 *   its contributors may be used to endorse or promote products derived from
 *   this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package org.jhove2.core.app;

import java.util.Date;

import org.jhove2.core.Installation;
import org.jhove2.core.Invocation;
import org.jhove2.core.JHOVE2Exception;
import org.jhove2.module.AbstractModule;
import org.jhove2.module.display.Displayer;
import org.jhove2.persist.ApplicationModuleAccessor;

import com.sleepycat.persist.model.Persistent;

/**
 * Abstract application based on the JHOVE2 framework .
 * 
 * @author mstrong, slabrams, smorrissey
 */
@Persistent
public abstract class AbstractApplication
	extends AbstractModule
	implements Application
{
	/** Application command line. */
	protected String commandLine;

	/** Application invocation date/timestamp. */
	protected Date dateTime;
	
	/** Application installation properties.  */
	protected Installation installation;

	/** Application invocation properties.  */
	protected Invocation invocation;
	
	/**
	 * Instantiate a new <code>AbstractApplication</code>.
	 * 
	 * @param version
	 *            Application version identifier in three-part form: "M.N.P"
	 * @param release
	 *            Application release date in ISO 8601 format: "YYYY-MM-DD"
	 * @param rights
	 *            Application rights statement
	 * @param scope
	 *            Application scope: generic or specific
	 * @param applicationModuleAccessor persistence access manager
	 * @throws JHOVE2Exception if Displayer cannot be initialized
	 */
	public AbstractApplication(String version, String release, String rights,
			                   Scope scope, ApplicationModuleAccessor applicationModuleAccessor)
    throws JHOVE2Exception
	{
		super(version, release, rights, scope, applicationModuleAccessor);

		/* Default application installation and invocation properties. */
		this.setInstallation(Installation.getInstance());
		this.setInvocation  (new Invocation());	
		this.scope = Scope.Generic;
	}
	
	public AbstractApplication(ApplicationModuleAccessor applicationModuleAccessor)
	 throws JHOVE2Exception 
	{
		this(null, null, null, Scope.Generic, applicationModuleAccessor);
	}
	/**
     * Instantiate a new <code>AbstractApplication</code>.
	 */
	public AbstractApplication () 
	 throws JHOVE2Exception
	{
		this(null);
	}

	/** Get application {@link org.jhove2.core.Invocation} properties.
	 * @return Application invocation properties
	 */
	@Override
	public Invocation getInvocation() {
		return this.invocation;
	}

	/** Get application {@link org.jhove2.core.Installation} properties.
	 * @return Application installation properties
	 */
	@Override
	public Installation getInstallation() {
		return this.installation;
	}

	/**
	 * Get application command line.
	 * 
	 * @return Application command line
	 * @see org.jhove2.core.app.Application#getCommandLine()
	 */
	@Override
	public String getCommandLine() {
		return this.commandLine;
	}

	/**
	 * Get application invocation date/timestamp.
	 * 
	 * @return Application invocation date/timestamp
	 * @see org.jhove2.core.app.Application#getDateTime()
	 */
	@Override
	public Date getDateTime() {
		return this.dateTime;
	}

	/** Get application displayer.
	 * @return Application displayer
	 * @throws JHOVE2Exception 
	 */
	@Override
	public Displayer getDisplayer() 
	throws JHOVE2Exception {
		ApplicationModuleAccessor ama = 
			(ApplicationModuleAccessor)this.getModuleAccessor();
		return ama.getDisplayer(this);
	}

	/** Set application command line.
	 * @param commandLine Application command line
	 */
	public void setCommandLine(String commandLine) {
		this.commandLine = commandLine;
	}

	/** Set application invocation date/timestamp.
	 * @param dateTime Application invocation date/timestamp
	 */
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	/** Set application displayer.
	 * @param displayer Application displayer
	 * @throws JHOVE2Exception 
	 */
	@Override
	public Displayer setDisplayer(Displayer displayer) 
	throws JHOVE2Exception {
		ApplicationModuleAccessor ama = 
			(ApplicationModuleAccessor)this.getModuleAccessor();
		return ama.setDisplayer(this, displayer);
	}


	/** Set application {@link org.jhove2.core.Installation} properties.
	 * @param installation Application installation properties
	 */
	public void setInstallation(Installation installation) {
		this.installation = installation;
	}

	/** Set application {@link org.jhove2.core.Invocation} properties.
	 * @param invocation Application invocation properties
	 */
	public void setInvocation(Invocation invocation) {
		this.invocation = invocation;
	}


}
