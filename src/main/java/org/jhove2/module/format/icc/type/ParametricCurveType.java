/**
 * JHOVE2 - Next-generation architecture for format-aware characterization
 *
 * Copyright (c) 2009 by The Regents of the University of California
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

package org.jhove2.module.format.icc.type;

import java.io.EOFException;
import java.io.IOException;
import org.jhove2.annotation.ReportableProperty;
import org.jhove2.core.JHOVE2;
import org.jhove2.core.JHOVE2Exception;
import org.jhove2.core.Message;
import org.jhove2.core.Message.Context;
import org.jhove2.core.Message.Severity;
import org.jhove2.core.io.Input;
import org.jhove2.core.reportable.AbstractReportable;
import org.jhove2.module.format.Validator.Validity;
import org.jhove2.module.format.icc.field.FunctionType;

/** iCC parametric curve type element, as defined in
 * ICC.1:2004-10, \u00a7 10.15.
 * 
 * @author slabrams
 */
public class ParametricCurveType
        extends AbstractReportable
{
    /** A parameter. */
    protected S15Fixed16Number a;

    /** B parameter. */
    protected S15Fixed16Number b;
    
    /** C parameter. */
    protected S15Fixed16Number c;

    /** D parameter. */
    protected S15Fixed16Number d;

    /** E parameter. */
    protected S15Fixed16Number e;

    /** F parameter. */
    protected S15Fixed16Number f;
    
    /** Function type in raw form. */
    protected int functionType;
    
    /** Function type in descriptive form. */
    protected String functionType_d;
    
    /** Gamma parameter. */
    protected S15Fixed16Number gamma;
    
    /** Validation status. */
    protected Validity isValid;

    /** Signature (of tag type). */
    protected StringBuffer signature = new StringBuffer(4);   
 
    /** Invalid function type message. */
    protected Message invalidFunctionTypeMessage;
    
    /** Invalid tag type message. */
    protected Message invalidTagTypeMessage;
    
    /** Non-zero data in reserved field message. */
    protected Message nonZeroDataInReservedFieldMessage;
    
    /** Instantiate a new <code>ParametricCurveType</code>. */
    public ParametricCurveType() {
        super();
        
        this.isValid = Validity.Undetermined;
    }
    
    /** Parse an ICC signature tag type element.
     * @param jhove2 JHOVE2 framework
     * @param input  ICC input
     * @return Number of bytes consumed
     * @throws EOFException
     *             If End-of-File is reached reading the source unit
     * @throws IOException
     *             If an I/O exception is raised reading the source unit
     * @throws JHOVE2Exception
     */
    public long parse(JHOVE2 jhove2, Input input)
        throws EOFException, IOException, JHOVE2Exception
    {
        long consumed  = 0L;
        int  numErrors = 0;
        this.isValid   = Validity.True;
  
        /* Tag signature. */
        for (int i=0; i<4; i++) {
            short b = input.readUnsignedByte();
            this.signature.append((char) b);
        }
        if (!this.signature.toString().equals("XYZ ")) {
            numErrors++;
            this.isValid = Validity.False;
            Object [] args =
                new Object [] {input.getPosition()-4L, "XYZ ",
                               signature.toString()};
            this.invalidTagTypeMessage = new Message(Severity.ERROR,
                Context.OBJECT,
                "org.jhove2.module.format.icc.ICCTag.InvalidTagType",
                args, jhove2.getConfigInfo());
        }
        consumed += 4;
        
        /* Reserved. */
        int reserved = input.readSignedInt();
        if (reserved != 0) {
            numErrors++;
            this.isValid = Validity.False;
            Object [] args = new Object [] {input.getPosition()-4L};
            this.nonZeroDataInReservedFieldMessage = new Message(Severity.ERROR,
                    Context.OBJECT,
                    "org.jhove2.module.format.icc.ICCTag.NonZeroDataInReservedField",
                    args, jhove2.getConfigInfo());
        }
        consumed += 4;
        
        /* Function type. */
        this.functionType = input.readUnsignedShort();
        FunctionType type = FunctionType.getFunctionType(this.functionType,
                                                         jhove2);
        if (type != null) {
            this.functionType_d = type.getParameters();
        }
        else {
            numErrors++;
            this.isValid = Validity.False;
            Object [] args = new Object [] {input.getPosition()-4L, this.functionType};
            this.invalidFunctionTypeMessage = new Message(Severity.ERROR,
                    Context.OBJECT,
                    "org.jhove2.module.format.icc.type.ParametricCurveType.InvalidFunctionType",
                    args, jhove2.getConfigInfo());
        }
        consumed += 2;
        
        /* Reserved. */
        reserved = input.readSignedInt();
        if (reserved != 0) {
            numErrors++;
            this.isValid = Validity.False;
            Object [] args = new Object [] {input.getPosition()-4L};
            this.nonZeroDataInReservedFieldMessage = new Message(Severity.ERROR,
                    Context.OBJECT,
                    "org.jhove2.module.format.icc.ICCTag.NonZeroDataInReservedField",
                    args, jhove2.getConfigInfo());
        }
        consumed += 4;
        return consumed;
    }
    
    /** Get invalid function type message.
     * @return Invalid function type message
     */
    @ReportableProperty(order=23, value="Invalid function type.")
    public Message getInvalidFunctionTypeMessage() {
        return this.invalidFunctionTypeMessage;
    }

    /** Get invalid tag type message.
     * @return Invalid tag type message
     */
    @ReportableProperty(order=21, value="Invalid tag type.")
    public Message getInvalidTagTypeMessage() {
        return this.invalidTagTypeMessage;
    }

    /** Get non-zero data in reserved field message.
     * @return Non-zero data in reserved field message
     */
    @ReportableProperty(order=22, value="Non-zero data in reserved field.")
    public Message getNonZeroDataInReservedFieldMessage() {
        return this.nonZeroDataInReservedFieldMessage;
    }
    
    /** Get A parameter.
     * @return Number of XYZ values
     */
    @ReportableProperty(order=4, value="Function A parameter.",
            ref="ICC.1:2004-10, \u00a7 10.15")
    public S15Fixed16Number getA() {
        return this.a;
    }
    /** Get parametric curve signature.
     * @return Parametric curve signature
     */
    @ReportableProperty(order=1, value="Parametric curve  signature.",
            ref="ICC.1:2004-10, \u00a7 10.27")
    public String getSignature() {
        return this.signature.toString();
    }
 
    /** Get validation status.
     * @return Validation status
     */
    @ReportableProperty(order=11, value="Validation status.",
            ref="ICC.1:2004-10, \u00a7 10.19")
    public Validity isValid() {
        return this.isValid;
    }
}
