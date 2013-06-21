package org.jboss.qa;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.omg.CORBA.SystemException;
import org.omg.CORBA.ORBPackage.InvalidName;

import com.arjuna.ats.arjuna.recovery.RecoveryManager;
import com.arjuna.ats.internal.jts.ORBManager;
import com.arjuna.ats.jts.OTSManager;
import com.arjuna.orbportability.ORB;
import com.arjuna.orbportability.RootOA;

public class Util {
	public static final String HOST = "127.0.0.1";
	private static ORB orb = null;
	
	public static void presetOrb() throws InvalidName, SystemException {
		 System.setProperty("org.omg.CORBA.ORBSingletonClass", "org.jacorb.orb.ORBSingleton");
		 System.setProperty("org.omg.CORBA.ORBClass", "org.jacorb.orb.ORB");
	    
		 // RecoveryManager.main(new String[] {"-test"});
		 
		 orb = com.arjuna.orbportability.ORB.getInstance("ClientSide");
	     RootOA oa = com.arjuna.orbportability.OA.getRootOA(orb);
	     orb.initORB(new String[] {}, null);
	     oa.initOA();
	     ORBManager.setORB(orb);
	     ORBManager.setPOA(oa);
	}
	
	public static void tearDownOrb() {
		orb.shutdown();
		RecoveryManager.manager().terminate();
	}
	
    public static void startCorbaTx() throws Throwable {            
        OTSManager.get_current().begin();
    }
    
    public static void commitCorbaTx() throws Throwable {
    	OTSManager.get_current().commit(true);
    }
    
    public static void rollbackCorbaTx() throws Throwable {
    	OTSManager.get_current().rollback();
    }
    
    public static InitialContext getContext() throws NamingException {
    	System.setProperty("com.sun.CORBA.ORBUseDynamicStub", "true");
    	
    	final Properties prope = new Properties();
    	
    	prope.put(Context.PROVIDER_URL, "corbaloc::" + HOST + ":3528/NameService");
    	// prope.put(Context.PROVIDER_URL, "corbaloc::" + HOST + ":3528/JBoss/Naming/root");
    	
    	prope.setProperty(Context.URL_PKG_PREFIXES, "org.jboss.iiop.naming:org.jboss.naming.client");
        prope.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.cosnaming.CNCtxFactory");
        prope.put(Context.OBJECT_FACTORIES, "org.jboss.tm.iiop.client.IIOPClientUserTransactionObjectFactory");
        
        
        return new InitialContext(prope);
    }
}
