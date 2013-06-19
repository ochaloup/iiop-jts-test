package org.jboss.qa;

import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import org.jboss.as.test.jbossts.crashrec.jms.JMSCrashBeanHome;
import org.jboss.as.test.jbossts.crashrec.jms.JMSCrashBeanRemote;

public class IIOPCrashTesting {
	private static final String BEAN_NAME= "JMSCrashBean";
	
	public static void main(String[] args) throws Throwable {
		String beanName = (args.length > 1) ? args[1] : BEAN_NAME; 
		
		InitialContext context = Util.getContext();
		
        final Object iiopObj = context.lookup(beanName);
        final JMSCrashBeanHome beanHome = (JMSCrashBeanHome) PortableRemoteObject.narrow(iiopObj, JMSCrashBeanHome.class);
        final JMSCrashBeanRemote result = beanHome.create();
        
        Util.startCorbaTx();
        
        result.testXATxMandatory("FakeJNDI", "Hi you man!");
        
        Util.commitCorbaTx();
	}
}
