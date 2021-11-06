package com.guestu.jbpm.util;

import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;

public class KieUtil {
    String URL="http://localhost:8085/kie-server/services/rest/server";
    String USERNAME="wbadmin";
    String PASSWORD="wbadmin";
    public KieServicesClient getKieServicesClient(){
        KieServicesConfiguration config=KieServicesFactory.newRestConfiguration(URL,USERNAME,PASSWORD);
        config.setMarshallingFormat(MarshallingFormat.JSON);
       return KieServicesFactory.newKieServicesClient(config);
       // KieServicesFactory.newKieServicesRestClient(URL,USERNAME,PASSWORD);
    }
}
