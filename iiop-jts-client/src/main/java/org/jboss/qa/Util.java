package org.jboss.qa;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.omg.CORBA.SystemException;
import org.omg.CosTransactions.HeuristicHazard;
import org.omg.CosTransactions.HeuristicMixed;
import org.omg.CosTransactions.NoTransaction;

import com.arjuna.ats.internal.jts.ORBManager;
import com.arjuna.ats.jts.OTSManager;
import com.arjuna.orbportability.ORB;
import com.arjuna.orbportability.RootOA;

public class Util {
	// private static CurrentImple corbaTx;
	public static final String HOST = "127.0.0.1";
	
    public static void startCorbaTx() throws Throwable {
        ORB orb = com.arjuna.orbportability.ORB.getInstance("ClientSide");
        RootOA oa = com.arjuna.orbportability.OA.getRootOA(orb);
        orb.initORB(new String[] {}, null);
        oa.initOA();
        ORBManager.setORB(orb);
        ORBManager.setPOA(oa);
        OTSManager.get_current().begin();
    }
    
    public static void commitCorbaTx() throws NoTransaction, HeuristicHazard, HeuristicMixed, SystemException {
    	OTSManager.get_current().commit(true);
    }
    
    public static void rollbackCorbaTx() throws NoTransaction, HeuristicHazard, HeuristicMixed, SystemException {
    	OTSManager.get_current().rollback();
    }
    
    public static InitialContext getContext() throws NamingException {
    	System.setProperty("com.sun.CORBA.ORBUseDynamicStub", "true");
        final Properties prope = new Properties();
        prope.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.cosnaming.CNCtxFactory");
        prope.put(Context.OBJECT_FACTORIES, "org.jboss.tm.iiop.client.IIOPClientUserTransactionObjectFactory");
        // prope.put(Context.PROVIDER_URL, "corbaloc::" + HOST + ":3528/JBoss/Naming/root");
        prope.put(Context.PROVIDER_URL, "corbaloc::" + HOST + ":3528/NameService");
        return new InitialContext(prope);
    }
}
