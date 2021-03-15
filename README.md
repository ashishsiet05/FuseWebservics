# Introduction

This demo was created to review some general features of JBoss Fuse 7.8 running on JBoss EAP 7.3<br/>
It was created to help new fuse developers to understand how to create web services using different approaches. This services will execute basic mathematical operations (sum, add, multiply). We will create an additional web service that will standardize request to the other services and act as a proxy too.<br/><br/>

ENJOY!!!!

# Web Services Detail

We will create four web services which are: <br/><br/>
 * Sum Web Service:  It will be created using **Code first** approach. SOAP request will receive two numbers and return a sum operation. 
The wsdl definition will be available at `http://localhost:8080/wscalculator/sum?wsdl`.<br/>
This is a SOAP Request and SOAP Response example:<br/>
SOAP Request: <br/>
```XML
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:wss="http://wssuma.ws.demos.fuse.redhat.com/">
   <soapenv:Header/>
   <soapenv:Body>
      <wss:sum>
         <arg0>
            <oper1>5</oper1>
            <oper2>1</oper2>
         </arg0>
      </wss:sum>
   </soapenv:Body>
</soapenv:Envelope>
```
SOAP Response: <br/>
```XML
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:wss="http://wssuma.ws.demos.fuse.redhat.com/">
   <soapenv:Header/>
   <soapenv:Body>
      <wss:sumResponse>
         <return>
            <result>6</result>
         </return>
      </wss:sumResponse>
   </soapenv:Body>
</soapenv:Envelope>
```

 * Add Web Service:  It will be created using **Code first** approach. SOAP request will receive two numbers and return an add operation.  The wsdl definition will be available at `http://localhost:8080/wscalculator/add?wsdl`.<br/>
This is a SOAP Request and SOAP Response example:<br/>
SOAP Request: <br/>
```XML
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:wss="http://wssuma.ws.demos.fuse.redhat.com/">
   <soapenv:Header/>
   <soapenv:Body>
      <wss:add>
         <arg0>
            <oper1>3</oper1>
            <oper2>2</oper2>
         </arg0>
      </wss:add>
   </soapenv:Body>
</soapenv:Envelope>
```
SOAP Response: <br/>
```XML
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:wss="http://wssuma.ws.demos.fuse.redhat.com/">
   <soapenv:Header/>
   <soapenv:Body>
      <wss:addResponse>
         <return>
            <result>1</result>
         </return>
      </wss:addResponse>
   </soapenv:Body>
</soapenv:Envelope>
```


 * Multiply Web Service:  It will be created using **Contract first** approach. SOAP request will receive two numbers and return a multiply operation.  
The wsdl definition will be available at `http://localhost:8080/wscalculator/multiply?wsdl`.<br/>
This is a SOAP Request and SOAP Response example:<br/>
SOAP Request: <br/>
```XML
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:wss="http://wssuma.ws.demos.fuse.redhat.com/">
   <soapenv:Header/>
   <soapenv:Body>
      <wss:multiply>
         <multiplyDTO>
            <oper1>2</oper1>
            <oper2>5</oper2>
         </multiplyDTO>
      </wss:multiply>
   </soapenv:Body>
</soapenv:Envelope>
```
SOAP Response: <br/>
```XML
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:wss="http://wssuma.ws.demos.fuse.redhat.com/">
   <soapenv:Header/>
   <soapenv:Body>
      <wss:multiplyResponse>
         <return>
            <result>10</result>
         </return>
      </wss:multiplyResponse>
   </soapenv:Body>
</soapenv:Envelope>
```
* Calculate Web Service: It will be created using **Contract first** approach. SOAP request will receive two numbers, a sender name and an operation type. The operation type can be **sum**, **add** or **multiply**. The service will return the resulting operation and an operation id constant **completed**.  <br/>
The wsdl definition will be available at `http://localhost:8080/wscalculate/calculate?wsdl`.<br/>
This is a SOAP Request and SOAP Response example:<br/>
SOAP Request: <br/>
```XML
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:wss="http://wssuma.ws.demos.fuse.redhat.com/">
   <soapenv:Header/>
   <soapenv:Body>
      <wss:calculate>
            <operType>sum</operType>
            <sender>isaac</sender>
            <cuerpo>
            	<oper1>10</oper1>
            	<oper2>6</oper2>
            </cuerpo>
      </wss:calculate>
   </soapenv:Body>
</soapenv:Envelope>
```
SOAP Response: <br/>
```XML
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:wss="http://wssuma.ws.demos.fuse.redhat.com/">
   <soapenv:Header/>
   <soapenv:Body>
      <wss:calculateResponse>
         <operationId>completed</operationId>
         <responseObject>16</responseObject>
      </wss:calculateResponse>
   </soapenv:Body>
</soapenv:Envelope>
```

Note how SOAP request and responses are different for sum, add and multiply services. If a client wants to consume them, it will need to decide which operation request to use and know how operation response will look like depending on the service call. To make things easy for the client, we will expose calculate web service in order to standardize request and response. The client will only change the operation type value an calculate web service will:<br/>
1. Process request and response as xml text. No use of java binding (The sum, add and multiply services use java binding so you can see the differences)
2. Get the standardize request.
3. Set a target namespace header
4. Decide based on **operType** tag, which web service to call.
5. Transform the original request into real web service request depending on **operType**. This transformation will execute using XSLT.
6. Validate the resulting transformation agains an XSD schema file.
6. Invoke the real operation service
7. Transform the operation service response into a standardize response using XSLT transformations
8. Return response to client.

Here is a visual camel route of calculate service:<br/>
![Camel Route](https://github.com/igl100/fuse621-wsdemo/blob/master/docs/image/Capture0.png)

## Objectives

This demo will include information about several topics wich include: 

* Create a JBoss Fuse installation and initial configuration.
* Understand how to use howtio console
* Learn how to deploy projects profiles using maven plugin
* Learn how to create web services using coding first approach
* Learn how to create web services using contract first approach
* Learn how to expose a proxy web service
* Learn how to route to different web services
* Learn how to use XSLT transformations
* Learn how to validate XML’s agains XSD schema.

## Pre-Requisites

1. JBoss Fuse 7.8 jar installation file 
2. Java JDK 11 or 15 installed
3. Apache Maven 3.1.1 version installed
4. Web Browser
5. Basic Linux commands understanding
6. SOAP UI or another web service client
7. Internet connection
8. GIT installed

# Setup JBoss Fuse

## Install JBoss EAP 7.3

1. Download jboss-eap-7.3.0.zip file from Red Hat Customer Portal or Developer Portal.

2. Download jboss-eap-7.3.5-patch.zip patch (or above) from Red Hat Customer Portal or Developer Portal.

3. Open a command Terminal

4. Unzip jboss-eap-7.3.0.zip:
   - `unzip jboss-eap-7.3.0.zip`<br/>

5. Copy jboss-eap-7.3.5-patch.zip to new unziped folder:
   - `cp /path/to/jboss-eap-7.3.5-patch.zip /path/to/jboss/jboss-eap-7.3/`

6. Apply the patch:
   - `<PathToJBossFolder>/bin/jboss-cli.sh "patch apply jboss-eap-7.3.x-patch.zip"`

7. Add an admin user to JBoss EAP:
   - `<PathToJBossFolder>/bin/add-user.sh`
   - Select Management User
   - Write a username
   - Write a password and confirm the password
   - Leave blank the groups question (just press enter)
   - Confirm the creation question
   - Write 'No' to "Is this new user going to be used for one AS process to connect to another AS process?" question.


## Install JBoss Fuse

1. Download fuse-eap-installer-7.8.0.jar file from Customer Portal of Developer Portal.

2. Open a command terminal

3. Copy fuse-eap-installer-7.8.0.jar to jboss folder installed on the preview step
   - `cp /path/to/fuse-eap-installer-7.8.0.jar /<PathToJBossFolder>/jboss/jboss-eap-7.3/`

4. Run the installer jar file:
   - `java -jar <PathToJBossFolder>/fuse-eap-installer-7.8.0.jar`

Thats it!!!, JBoss Fuse is already install!!!
 

## Running JBoss Fuse

1. On opened terminal and start JBoss EAP: `<PathToJBossFolder>/bin/standalone.sh`

2. Access Fuse console using a web browser (http://localhost:8080/hawtio):

3. Access the console using the user and password created when JBoss EAP was installed

# Cloning Proyects

Clone from GIT:
* Create or move to an empty dir: `cd <WorkshopProyectsDirToUse>`
* Run: `git clone https://github.com/igl100/fuse78-wsdemo.git`

# Deploy wscalculator profile

This project contains the definition of sum, add and multiply services. In order to deploy: 
 * On a terminal go to demo projects directory: `cd <FuseWADemoDir>/projects/wscalculator`
 * Run: `mvn clean generate-sources wildfly:deploy`

Wait for success deployment. You can see the new camel route using web console. A new CAMEL option will show on the left side menu.

# Deploy wscalculate profile

This project contains the definition of calculate services. In order to deploy: 

 * On a terminal go to demo projects directory: `cd <FuseWADemoDir>/projects/wscalculate`
 * Run: `mvn clean generate-sources wildfly:deploy`

Wait for success deployment. You can see the new created profile using web console.

# Use Soap UI to invoke the services

At this point, the services should be exposed and ready to be invoked. Use Soap UI config files provided on this demo (Inside soapui directory) to import as projects, request and execute client.<br/><br/>

Have fun!!
 
