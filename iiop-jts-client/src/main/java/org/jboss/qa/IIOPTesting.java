package org.jboss.qa;

import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;


import org.jboss.as.test.manualmode.transaction.jts.propagation.IIOPTestBeanHome;
import org.jboss.as.test.manualmode.transaction.jts.propagation.IIOPTestRemote;

public class IIOPTesting {
	private static final String BEAN_NAME= "IIOPTestBean";
	
	public static void main(String[] args) throws Throwable {
		String beanName = (args.length > 1) ? args[1] : BEAN_NAME; 
		
		InitialContext context = Util.getContext();
		
        final Object iiopObj = context.lookup(beanName);
        final IIOPTestBeanHome beanHome = (IIOPTestBeanHome) PortableRemoteObject.narrow(iiopObj, IIOPTestBeanHome.class);
        final IIOPTestRemote result = beanHome.create();
        
        Util.startCorbaTx();
        try {
	        String a = result.helloMandatory();
	        // String a = result.hello();
	        
	        System.out.println(a);
        } catch(Throwable e) {
        	e.printStackTrace();
        	System.out.println("Exception got on call - rollback");
        	Util.rollbackCorbaTx();
        	return;
        }
        
        System.out.println("Well done - commit");
        Util.commitCorbaTx();
	}
}
