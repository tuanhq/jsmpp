/*
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package org.jsmpp.examples;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

import org.apache.log4j.Logger;
import org.jsmpp.InvalidResponseException;
import org.jsmpp.PDUException;
import org.jsmpp.bean.Alphabet;
import org.jsmpp.bean.BindType;
import org.jsmpp.bean.ESMClass;
import org.jsmpp.bean.GeneralDataCoding;
import org.jsmpp.bean.MessageClass;
import org.jsmpp.bean.NumberingPlanIndicator;
import org.jsmpp.bean.RegisteredDelivery;
import org.jsmpp.bean.SMSCDeliveryReceipt;
import org.jsmpp.bean.TypeOfNumber;
import org.jsmpp.examples.gateway.AutoReconnectGateway;
import org.jsmpp.examples.gateway.Gateway;
import org.jsmpp.extra.NegativeResponseException;
import org.jsmpp.extra.ResponseTimeoutException;
import org.jsmpp.session.BindParameter;
import org.jsmpp.session.SMPPSession;
import org.jsmpp.util.AbsoluteTimeFormatter;
import org.jsmpp.util.TimeFormatter;


/**
 * @author uudashr
 *
 */
public class SimpleSubmitExamplethq {
	private static final Logger logger = Logger.getLogger(SimpleSubmitExamplethq.class);
    private static TimeFormatter timeFormatter = new AbsoluteTimeFormatter();;
    
    public void sendMT(String file,String mt) throws IOException{
    	
    	System.out.println("start send mt");
    	
    	/* Gateway gateway = new AutoReconnectGateway("10.56.130.15", 6200, 
           new BindParameter(BindType.BIND_TRX, "cp_6x17", "20140242", "GE", 
                   TypeOfNumber.INTERNATIONAL, NumberingPlanIndicator.ISDN, "*"));*/
    	
		SMPPSession session = new SMPPSession();
    try {
        session.connectAndBind("10.56.130.15",2775, new BindParameter(BindType.BIND_TRX, "smppclient1", "password", "GE", TypeOfNumber.INTERNATIONAL, NumberingPlanIndicator.ISDN, "*"));
        System.out.println("Connection sucess");
    } catch (IOException e) {
        System.err.println("Failed connect and bind to host");
        e.printStackTrace();
    }
    	 
    	BufferedReader inputStream = null;
			try {
				inputStream = new BufferedReader(new FileReader(file));
				String subid;
				int i=0,j=0;
				
		//		while ((subid = inputStream.readLine()) != null) {
					try {
//						SMPPSession session = new SMPPSession();
//			      try {
//			          session.connectAndBind("10.56.130.15",6200, new BindParameter(BindType.BIND_TRX, "cp_6x17", "20140242", "GE", TypeOfNumber.INTERNATIONAL, NumberingPlanIndicator.ISDN, "*"));
//			          System.out.println("Connection sucess");
//			      } catch (IOException e) {
//			          System.err.println("Failed connect and bind to host");
//			          e.printStackTrace();
//			      }
//						try {
//							subid =subid.trim();
//							if("".equalsIgnoreCase(subid)){
//								continue;
//							}
//						} catch (Exception e) {
//							// TODO: handle exception
//							continue;
//						}
//											
						try {
							session.submitShortMessage("CMT",
		      			TypeOfNumber.NATIONAL,
		      			NumberingPlanIndicator.UNKNOWN,
		      			"6717",
		      			TypeOfNumber.NATIONAL,
		      			NumberingPlanIndicator.UNKNOWN,
		      			"84985633469",
		      			new ESMClass(),
		      			(byte) 0,
		      			(byte)0x3,
		      			// timeFormatter.format(new Date()),
		      			null,
		      			null,
		      			// new RegisteredDelivery(SMSCDeliveryReceipt.DEFAULT),
		      			new RegisteredDelivery((byte) 0),
		      			(byte) 0,
		      			new GeneralDataCoding(Alphabet.ALPHA_DEFAULT,
		          		MessageClass.CLASS1, false),
		      		//	new GeneralDataCoding((byte) 0),
		      			(byte) 0,
		      			mt.getBytes());      
		                    
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
						Thread.sleep(50);
						  j++;
	      		  i++;
//	      		  logger.error("stt:" + j + "-send sms to subid:" + subid);
//	      		  System.out.println("stt:" + j + "-send sms to subid:" + subid);
	       
	      }catch (Exception e) {
	        e.printStackTrace();
	        Thread.sleep(2000);
	    }
	//}
				
			} catch (Exception e) {
				logger.error("Loi upload :" + e.getMessage());
			} finally {				
				if (inputStream != null) {
					inputStream.close();
				}
			}	
    	
     
  
    }
    
    public static void main(String[] args) {
    	try {
    	 	new SimpleSubmitExamplethq().sendMT("/opt/fileUpload/filegoc.txt", "hello world");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
    	
   
    	/*
        SMPPSession session = new SMPPSession();
        try {
            session.connectAndBind("10.56.130.15",6200, new BindParameter(BindType.BIND_TRX, "cp_6x17", "20140242", "GE", TypeOfNumber.INTERNATIONAL, NumberingPlanIndicator.ISDN, "*"));
            System.out.println("Connection sucess");
        } catch (IOException e) {
            System.err.println("Failed connect and bind to host");
            e.printStackTrace();
        }
        
        try {
        	
            String messageId =session.submitShortMessage("CMT",
        			TypeOfNumber.NATIONAL,
        			NumberingPlanIndicator.UNKNOWN,
        			"6717",
        			TypeOfNumber.NATIONAL,
        			NumberingPlanIndicator.UNKNOWN,
        			"84985633469",
        			new ESMClass(),
        			(byte) 0,
        			(byte) 0x3,
        			// timeFormatter.format(new Date()),
        			null,
        			null,
        			// new RegisteredDelivery(SMSCDeliveryReceipt.DEFAULT),
        			new RegisteredDelivery((byte) 0),
        			(byte) 0,
        			new GeneralDataCoding(Alphabet.ALPHA_DEFAULT,
            		MessageClass.CLASS1, false),
        		//	new GeneralDataCoding((byte) 0),
        			(byte) 0,
        			"helloword".getBytes());
        			
        		  
       
        }catch (Exception e) {
          System.err.println("Kong hieu loi gi ");
          e.printStackTrace();
      }
        
        session.unbindAndClose();
    */
    	}
    
    
}
