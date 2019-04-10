package org.sdl.coreservice.services;


import com.sdltridion.contentmanager.coreservice.CoreService201701;
import com.sdltridion.contentmanager.coreservice.ICoreService;
import com.sdltridion.contentmanager.coreservice.ICoreServiceGetApiVersionCoreServiceFaultFaultFaultMessage;
import com.sdltridion.contentmanager.coreservice.ICoreServiceGetCurrentUserCoreServiceFaultFaultFaultMessage;
import org.sdl.coreservice.executor.Executor;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.io.IOException;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.Properties;

public class ConnectToTridionSites {

    private ConnectToTridionSites(){}

    private static final String CMS_WSDL_URL = getPropertyValue("wsdlUrl");
    private static final String Q_NAME_URI = getPropertyValue("qNameUri");
    private static final String Q_NAME_LOCAL_PART = getPropertyValue("qNameLocalPart");

    public static ICoreService connectToTridionSites(final String username, final String password) throws MalformedURLException {
        QName qName = new QName(Q_NAME_URI, Q_NAME_LOCAL_PART);
        Service service = CoreService201701.create(new URL(CMS_WSDL_URL), qName);
        ICoreService iCoreService = service.getPort(ICoreService.class);
        Authenticator myAuth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password.toCharArray());
            }
        };
        Authenticator.setDefault(myAuth);
        return iCoreService;

    }

    public static String getCmsVersion(ICoreService iCoreService) throws ICoreServiceGetApiVersionCoreServiceFaultFaultFaultMessage {
        return iCoreService.getApiVersion();
    }

    public static String getCurrentUser(ICoreService coreService) throws ICoreServiceGetCurrentUserCoreServiceFaultFaultFaultMessage {
        return coreService.getCurrentUser().getTitle().getValue();
    }

    public static String getPropertyValue(String property) {
        Properties properties = new Properties();
        InputStream inputStream = Executor.class.getClassLoader().getResourceAsStream("application.properties");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty(property);
    }
}
