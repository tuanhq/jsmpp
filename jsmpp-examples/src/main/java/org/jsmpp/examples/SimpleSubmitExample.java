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
import org.jsmpp.bean.TypeOfNumber;
import org.jsmpp.examples.gateway.AutoReconnectGateway;
import org.jsmpp.examples.gateway.Gateway;
import org.jsmpp.extra.NegativeResponseException;
import org.jsmpp.extra.ResponseTimeoutException;
import org.jsmpp.session.BindParameter;

/**
 * @author uudashr
 *
 */
public class SimpleSubmitExample {	
	private static final Logger logger = Logger.getLogger(SimpleSubmitExample.class);
	public static void main(String[] args) {
		Gateway gateway = null;
		BufferedReader inputStream1 = null;
		BufferedReader inputStream2 = null;
		try {
			gateway = new AutoReconnectGateway("10.56.130.15", 6200, new BindParameter(BindType.BIND_TRX, "cp_6x17", "20140242", "GE", TypeOfNumber.INTERNATIONAL,
					NumberingPlanIndicator.ISDN, "*"));
			inputStream1 = new BufferedReader(new FileReader("/opt/fileUpload/content.txt"));
			String l, mt = "";
			while ((l = inputStream1.readLine()) != null) {
				mt = mt + l + "\n";
			}
			try {
				inputStream2 = new BufferedReader(new FileReader("/opt/fileUpload/sendMt.txt"));
				String subid;
				int i = 0;
				while ((subid = inputStream2.readLine()) != null) {
					try {
						subid = subid.trim();
						if ("".equalsIgnoreCase(subid)) {
							continue;
						}
					} catch (Exception e) {
						// TODO: handle exception
						continue;
					}

					// send mt o day

					try {

						String messageId = gateway.submitShortMessage("CMT", TypeOfNumber.NATIONAL, NumberingPlanIndicator.UNKNOWN, "6717", TypeOfNumber.NATIONAL,
								NumberingPlanIndicator.UNKNOWN, subid, new ESMClass(), (byte) 0, (byte) 0x3, null, null, new RegisteredDelivery((byte) 0), (byte) 0,
								new GeneralDataCoding(Alphabet.ALPHA_DEFAULT, MessageClass.CLASS1, false), (byte) 0, mt.getBytes());
						logger.error(i + ":message_id is:" + messageId +" isdn:" +subid + " mt:" + mt );

					} catch (PDUException e) {
						// Invalid PDU parameter
						logger.error("Invalid PDU parameter");
						e.printStackTrace();
					} catch (ResponseTimeoutException e) {
						// Response timeout
						logger.error("Response timeout");
						e.printStackTrace();
					} catch (InvalidResponseException e) {
						// Invalid response
						logger.error("Receive invalid respose");
						e.printStackTrace();
					} catch (NegativeResponseException e) {
						// Receiving negative response (non-zero command_status)
						logger.error("Receive negative response");
						e.printStackTrace();
					} catch (Exception e) {
						logger.error("IO error occur");
						e.printStackTrace();
						
						
						
						
					}
					i++;
					Thread.sleep(100);

				}

			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if(inputStream2!=null){
					try {
						inputStream2.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(inputStream1!=null){
				try {
					inputStream1.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		System.exit(0);
	}
}