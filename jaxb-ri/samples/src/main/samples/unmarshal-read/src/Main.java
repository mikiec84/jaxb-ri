/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

// import java content classes generated by binding compiler
import primer.po.*;

/*
 * $Id: Main.java,v 1.1 2007-12-05 00:49:46 kohsuke Exp $
 */
 
public class Main {
    
    // This sample application demonstrates how to unmarshal an instance
    // document into a Java content tree and access data contained within it.
    
    public static void main( String[] args ) {
        try {
            // create a JAXBContext capable of handling classes generated into
            // the primer.po package
            JAXBContext jc = JAXBContext.newInstance( "primer.po" );
            
            // create an Unmarshaller
            Unmarshaller u = jc.createUnmarshaller();
            
            // unmarshal a po instance document into a tree of Java content
            // objects composed of classes from the primer.po package.
            JAXBElement<?> poElement = 
		(JAXBElement<?>)u.unmarshal( new FileInputStream( "po.xml" ) );
            PurchaseOrderType po = (PurchaseOrderType)poElement.getValue();
                
	    
            // examine some of the content in the PurchaseOrder
            System.out.println( "Ship the following items to: " );
            
            // display the shipping address
            USAddress address = po.getShipTo();
            displayAddress( address );
            
            // display the items
            Items items = po.getItems();
            displayItems( items );
            
        } catch( JAXBException je ) {
            je.printStackTrace();
        } catch( IOException ioe ) {
            ioe.printStackTrace();
        }
    }
    
    public static void displayAddress( USAddress address ) {
        // display the address
        System.out.println( "\t" + address.getName() );
        System.out.println( "\t" + address.getStreet() ); 
        System.out.println( "\t" + address.getCity() +
                            ", " + address.getState() + 
                            " "  + address.getZip() ); 
        System.out.println( "\t" + address.getCountry() + "\n"); 
    }
    
    public static void displayItems( Items items ) {
        // the items object contains a List of primer.po.ItemType objects
        List itemTypeList = items.getItem();

                
        // iterate over List
        for( Iterator iter = itemTypeList.iterator(); iter.hasNext(); ) {
            Items.Item item = (Items.Item)iter.next(); 
            System.out.println( "\t" + item.getQuantity() +
                                " copies of \"" + item.getProductName() +
                                "\"" ); 
        }
    }
}