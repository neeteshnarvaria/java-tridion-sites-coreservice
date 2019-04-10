package org.sdl.coreservice.executor;

import com.sdltridion.contentmanager.coreservice.ICoreService;

import static org.sdl.coreservice.services.ConnectToTridionSites.connectToTridionSites;
import static org.sdl.coreservice.services.ConnectToTridionSites.getCmsVersion;
import static org.sdl.coreservice.services.ConnectToTridionSites.getCurrentUser;
import static org.sdl.coreservice.services.ConnectToTridionSites.getPropertyValue;

public class Executor {

    public static void main(String[] args) {
        try {
            String user = getPropertyValue("username");
            String password = getPropertyValue("password");
            ICoreService iCoreService = connectToTridionSites(user, password);
            System.out.println(getCmsVersion(iCoreService));
            System.out.println(getCurrentUser(iCoreService));
        } catch (Exception e) {
            System.err.println("Error Occurred: " + e.getLocalizedMessage());
        }
    }


}
