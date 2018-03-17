package rmiRegister;

import rmiService.MonService;
import rmiService.MonServiceImpl;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author Antho
 */
public class InitRemoteService implements ServletContextListener {

    public static boolean isRegistered = false;
    public static MonService service;

    public void contextInitialized(ServletContextEvent sce) {
        if (!isRegistered) {
            try {
                service = new MonServiceImpl();
                MonService stub = (MonService) UnicastRemoteObject.exportObject(service, 0);
                Registry registry = LocateRegistry.createRegistry(9345);
                registry.rebind(MonService.serviceName, stub);
                System.out.println("++++++++++++++ Remote service bound");
                isRegistered = true;
            } catch (Exception e) {
                System.err.println("++++++++++++++ Remote service exception:");
                e.printStackTrace();
            }
        }
    }

    public void contextDestroyed(ServletContextEvent sce) {

    }

    static public MonService getService() {
        return service;
    }

    public InitRemoteService() {

    }
}
