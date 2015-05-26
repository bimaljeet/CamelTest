package com.cameltest;

import org.apache.camel.CamelContext;
import org.apache.camel.model.RouteDefinition;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bnayar
 * Date: 11/20/13
 * Time: 2:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class CamelBean {

    private CamelContext context;
    private List<String> routeFile;
    private JAXBContext jaxbContext;

    public CamelContext getContext() {
        return context;
    }

    public void setContext(CamelContext context) {
        this.context = context;
    }

    public List<String> getRouteFile() {
        return routeFile;
    }

    public void setRouteFile(List<String> routeFile) {
        this.routeFile = routeFile;
    }

    public JAXBContext getJaxbContext() {
        return jaxbContext;
    }

    public void setJaxbContext(JAXBContext jaxbContext) {
        this.jaxbContext = jaxbContext;
    }

    public void init() {
        try {
            loadRoutes(routeFile);
        } catch (Exception e) {
            System.out.println("!!!!!!!!!!!!!!!!!!! Exception init = " + e);
            e.printStackTrace();
        }
    }

    private void loadRoutes(List<String> routeFiles) {
        try {
            for (String routeFile : routeFiles) {
                String routeBody = getRouteBodyFromFile(routeFile);
                RouteDefinition routeDefinition = getRouteDefinition(routeBody.toString());
                routeDefinition.setId(routeFile);
                getContext().addRouteDefinition(routeDefinition);
            }
        } catch (Exception e) {
            System.out.println("!!!!!!!!!!!!!!!!!!! Exception loadRoutes = " + e);
            e.printStackTrace();
        }
    }

    private String getRouteBodyFromFile(String routeFile) {
        StringBuffer routeBody = new StringBuffer();
        try {
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(routeFile);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String line;
            while ((line = br.readLine()) != null) {
                routeBody.append(line);
            }
            br.close();

        } catch (Exception e) {
            System.out.println("Exception in getRouteBodyFromFile :" + e);
            e.printStackTrace();
        }
        return routeBody.toString();
    }

    private RouteDefinition getRouteDefinition(String routeBody) {
        RouteDefinition route = null;
        try {
            Unmarshaller unmarshaller = getJaxbContext().createUnmarshaller();
            Object value = unmarshaller.unmarshal(new StringReader(routeBody));
            if (value instanceof RouteDefinition) {
                route = (RouteDefinition) value;
                route.routeId("Sample");
            } else {
                System.out.println("Failed to unmarshall route definition!!!!");
            }

        } catch (Exception e) {
            System.out.println("Failed to unmarshall route definition!!!!" + e);
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return route;
    }


}
