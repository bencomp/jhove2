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
package org.jhove2.persist.inmemory;

import java.util.ArrayList;
import java.util.List;

import org.jhove2.core.JHOVE2;
import org.jhove2.core.JHOVE2Exception;
import org.jhove2.module.Command;
import org.jhove2.persist.FrameworkAccessor;

import com.sleepycat.persist.model.Persistent;

/**
 * @author smorrissey
 *
 */
@Persistent
public class InMemoryFrameworkAccessor extends InMemoryBaseModuleAccessor
	implements FrameworkAccessor {

	protected List<Command> commands ;
	
	public InMemoryFrameworkAccessor(){
		super();
		this.commands = new ArrayList<Command>();
	}
	
	/* (non-Javadoc)
	 * @see org.jhove2.persist.FrameworkAccessor#getCommands(org.jhove2.core.JHOVE2)
	 */
	@Override
	public List<Command> getCommands(JHOVE2 jhove2) throws JHOVE2Exception {
		return this.commands;
	}

	/* (non-Javadoc)
	 * @see org.jhove2.persist.FrameworkAccessor#setCommands(org.jhove2.core.JHOVE2, java.util.List)
	 */
	@Override
	public List<Command> setCommands(JHOVE2 jhove2, List<Command> commands)
			throws JHOVE2Exception {
		if (commands != null){
			for (Command command:commands){
				command.setJhove2ModuleId(jhove2.getModuleId());
			}
		}
		this.commands = commands;
		return this.commands;
	}


}