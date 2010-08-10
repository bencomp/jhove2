/**
 * 
 */
package org.jhove2.module.format.tiff;

import org.jhove2.annotation.ReportableProperty;
import org.jhove2.core.reportable.AbstractReportable;


/**
 * stores the Tiff SHORT type value
 * 
 * @author mstrong
 *
 */
public class Short
    extends AbstractReportable {
    private int value;


    public Short (int value) {
        this.value = value;
    }
    
    /**  no-arg constructor for Short object */
    public Short () {
    }
    
    /**
     * @return the value
     */
    @ReportableProperty(order = 1, value = "Tag SHORT  value")
    public int getValue() {
        return this.value;
    }


}