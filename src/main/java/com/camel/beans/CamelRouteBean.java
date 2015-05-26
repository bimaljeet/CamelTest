package com.camel.beans;

import org.apache.camel.Exchange;
//import org.apache.camel.Handler;
//import com.protocolBufferTest.*;
//import com.protocolBufferTest.Hello.HelloWorld;
//import com.protocolBufferTest.Hello.*;

/**
 * Created with IntelliJ IDEA.
 * User: bnayar
 * Date: 11/22/13
 * Time: 3:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class CamelRouteBean {


    public void camelBeanMethod(Exchange exchange){
        try {
            System.out.println("2. Parameter is " + exchange);

            exchange.setOut(exchange.getIn());
            exchange.getOut().setBody("In a bean");

        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

}
