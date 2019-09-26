/**
 *  Copyright 2005-2016 Red Hat, Inc.
 *
 *  Red Hat licenses this file to you under the Apache License, version
 *  2.0 (the "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *  implied.  See the License for the specific language governing
 *  permissions and limitations under the License.
 */
package com.arcadia;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.util.Base64;

@Configuration
@ConfigurationProperties(prefix = "quickstart")
public class QuickstartConfiguration {

    /**
     * A comma-separated list of routes to use as recipients for messages.
     */
    private String recipients;

    /**
     * The username to use when connecting to the async queue (simulation)
     */
    private String queueUsername;

    /**
     * The password to use when connecting to the async queue (simulation)
     */
    private String queuePassword;

    private String rsaKey ;

    private String privateKeyPath;

    public String getPrivateKeyPath() {
        return privateKeyPath;
    }

    public void setPrivateKeyPath(String privateKeyPath) {
        this.privateKeyPath = privateKeyPath;
    }

    public String getRsaKey() { return rsaKey; }

    public byte[] getRsaKeyByte(){  return rsaKey.getBytes();               }


    public byte[] getPrivateKeyFromStatic() throws Exception{

            byte[] pkcs8EncodedBytes;

            // Read in the key into a String
            StringBuilder pkcs8Lines = new StringBuilder();
            BufferedReader rdr = new BufferedReader(new StringReader(rsaKey));
            String line;
            while ((line = rdr.readLine()) != null) {
                pkcs8Lines.append(line);
            }

            // Remove the "BEGIN" and "END" lines, as well as any whitespace

            String pkcs8Pem = pkcs8Lines.toString();
            pkcs8Pem = pkcs8Pem.replace("-----BEGIN RSA PRIVATE KEY-----", "");
            pkcs8Pem = pkcs8Pem.replace("-----END RSA PRIVATE KEY-----", "");
            pkcs8Pem = pkcs8Pem.replaceAll("\\s+","");

            // Base64 decode the result

            pkcs8EncodedBytes = Base64.getDecoder().decode(pkcs8Pem.getBytes());


            return pkcs8EncodedBytes;
    }





    public void setRsaKey(String rsaKey) {  this.rsaKey = rsaKey; }

    public String getRecipients() {
        return recipients;
    }

    public void setRecipients(String recipients) {
        this.recipients = recipients;
    }

    public String getQueueUsername() {
        return queueUsername;
    }

    public void setQueueUsername(String queueUsername) {
        this.queueUsername = queueUsername;
    }

    public String getQueuePassword() {
        return queuePassword;
    }

    public void setQueuePassword(String queuePassword) {
        this.queuePassword = queuePassword;
    }

}
