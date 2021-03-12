# Introduction

This demo was created to review some general features of JBoss Fuse 6.2.1 <br/>
It was created to help new fuse developers to understand how to create web services using different approaches. This services will execute basic mathematical operations (sum, add, multiply). We will create an additional web service that will standardize request to the other services and act as a proxy too.<br/><br/>

ENJOY!!!!

# Web Services Detail

We will create four web services which are: <br/><br/>
 * Sum Web Service:  It will be created using **Code first** approach. SOAP request will receive two numbers and return a sum operation. 
The wsdl definition will be available at `http://localhost:{CustomPort}/sum/?wsdl`.<br/>
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

 * Add Web Service:  It will be created using **Code first** approach. SOAP request will receive two numbers and return an add operation.  The wsdl definition will be available at `http://localhost:{CustomPort}/add/?wsdl`.<br/>
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
The wsdl definition will be available at `http://localhost:{CustomPort}/multiply/?wsdl`.<br/>
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
The wsdl definition will be available at `http://localhost:{CustomPort}/calculate/?wsdl`.<br/>
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
* Understand how to use Karaf console
* Understand how to Begin a Fabric configuration 
* Access Web console to manage Fuse and Fabrics
* Learn how to deploy projects profiles using fabric8 maven plugin
* Learn how to create web services using coding first approach
* Learn how to create web services using contract first approach
* Learn how to expose a proxy web service
* Learn how to route to different web services
* Learn how to use XSLT transformations
* Learn how to validate XML’s agains XSD schema.

## Pre-Requisites

1. JBoss Fuse 6.2.1 zip installation file 
2. Java JDK 7 installed
3. Apache Maven 3.1.1 version installed
4. Web Browser
5. Basic Linux commands understanding
6. SOAP UI or another web service client
7. Internet connection
8. GIT installed

# Setup JBoss Fuse

## Install JBoss Fuse

1. Open a command terminal

1. Unzip JBoss Fuse on any directory that you wish to use as $FUSE_HOME. In this example i will use directory `/opt/redhat/`. Copy JBoss Fuse installation zip file on the selected directory and be sure your user have read, write and execute privileges.

	- `cd /opt/redhat`
	- `unzip jboss-fuse-full-6.2.1.redhat-084.zip`<br/>
    ![Unzip Command](https://github.com/igl100/fuse621-wsdemo/blob/master/docs/image/Capture1.png)
    
	- `export FUSE_HOME=/opt/redhat/jboss-fuse-6.2.1.redhat-084`

	Thats it!!!, JBoss Fuse is already install!!!
 
## Configure JBoss Fuse 
 
Before running JBoss Fuse for the first time we need to configure user/password access.

1. Enable user/password for karaf console. On your opened terminal execute:
	- `cd $FUSE_HOME`
	- `vi ./etc/users.properties` (If you do not like vi, use any other text editor)
	- Uncomment the final line by removing # character from #admin=admin,admin line
	- Save the file (esc, :wq)

## Running JBoss Fuse

1. On opened terminal `$FUSE_HOME/bin/start`

2. Access karaf console:
	-  `$FUSE_HOME/bin/client -u admin -p admin` 
    <br/>If you get a message **"Failed to get the session"** wait a few seconds and try again. This message means that JBoss Fuse is starting.<br/>
	![Karaf Console](https://github.com/igl100/fuse621-wsdemo/blob/master/docs/image/Capture2.png)

3. Create a fabric so we can manage all the brokers from a single console:
	- `fabric:create --clean --wait-for-provisioning  --bind-address localhost --resolver manualip --global-resolver manualip --manual-ip localhost --zookeeper-password admin`<br/><br/>
    All this parameters are needed so that zookeeper and fuse fabric bind everything to **localhost** address. This is not what you need to do on production servers but since ipaddress might change on laptops or PC's fabric might not start correctly on different networks.
    
4. Validate that fabric created by running `container-list` on karaf console.
	<br/>
	![Container-list command](https://github.com/igl100/fuse621-wsdemo/blob/master/docs/image/Capture3.png)

5. Open URL http://localhost:8181 on a web browser and login with user admin and password admin<br/>
	![Fabric Login](https://github.com/igl100/fuse621-wsdemo/blob/master/docs/image/Capture4.png)
    <br/>
    ![Fabric Home](https://github.com/igl100/fuse621-wsdemo/blob/master/docs/image/Capture5.png)
    <br/>
    ![Fabric Containers](https://github.com/igl100/fuse621-wsdemo/blob/master/docs/image/Capture6.png)

# Cloning Proyects

Clone from GIT:
* Create or move to an empty dir: `cd <WorkshopProyectsDirToUse>`
* Run: `git clone https://github.com/igl100/fuse621-wsdemo.git`

# Deploy wscalculator profile

This project contains the definition of sum, add and multiply services. In order to deploy: 
 * On a terminal go to demo projects directory: `cd <FuseWADemoDir>/projects/wscalculator`
 * Run: `mvn clean install fabric8:deploy`

If settings.xml update is asked, press 'y' and use admin as username and admin for password.

Wait for success deployment. You can see the new created profile using web console at Runtime>Manage>Uncategorized>wscalculator.

![wscalculator profile](https://github.com/igl100/fuse621-wsdemo/blob/master/docs/image/Capture7.png)

# Create a camel broker for wscalculator

We need to create a broker that use the profile created in the last steps to actually expose the services.

* `fabric:container-create-child --profile wscalculator root ws-calculator1`

Notices how we define wscalculator profiles as parent.

* Go to web console at Runtime and wait for container to start.

![WSCalculator Start](https://github.com/igl100/fuse621-wsdemo/blob/master/docs/image/Captura8.png)

* Go to web console at Services>APIs and check that all three services are started. Notice the port was auto assigned.

![Console APIs](https://github.com/igl100/fuse621-wsdemo/blob/master/docs/image/Captura9.png)

# Edit wscalculate

The wscalculate project is a proxy so we need to edit the custom port where wscalculator services started.

* Edit file <FuseWADemoDir>/projects/wscalculate/src/main/fabric8/wsproxy.properties
* Change port and wsdlPort properties to the correct port assigned al APIs tab.
* Save changes.

# Deploy wscalculate profile

This project contains the definition of calculate services. In order to deploy: 

 * On a terminal go to demo projects directory: `cd <FuseWADemoDir>/projects/wscalculate`
 * Run: `mvn clean install fabric8:deploy`

Wait for success deployment. You can see the new created profile using web console at Runtime>Manage>Uncategorized>wscalculate.

![wscalculate profile](https://github.com/igl100/fuse621-wsdemo/blob/master/docs/image/Capture10.png)

# Create a camel broker

We need to create a broker that use both profiles created in the last steps to actually expose all the services.

* `fabric:container-create-child --profile wscalculate root ws-calculate1`

Notices how we define wscalculate profile as parent.

* Go to web console at Runtime and wait for container to start.

![WSCalculator Start](https://github.com/igl100/fuse621-wsdemo/blob/master/docs/image/Capture11.png)

* Go to web console at Services>APIs and check that all three services are started. Notice the port was auto assigned to service Calculate.

![Console APIs](https://github.com/igl100/fuse621-wsdemo/blob/master/docs/image/Capture12.png)

# Use Soap UI to invoke the services

At this point, the services should be exposed and ready to be invoked. Use Soap UI config files provided on this demo (Inside soapui directory) to import as projects, request and execute client.<br/><br/>

# Enable HA on web services

* On fabric console run: `fabric:container-create-child --profile gateway-http root ws-gateway`. This commando will create a new broker with gateway-http profile assigned. This profile enable load balancing for web, services and messaging request. By default it listen at port 9000 (this can be changed).

![WSGateway Broker](https://github.com/igl100/fuse621-wsdemo/blob/master/docs/image/Capture13.png)

* On web console edit wsproxy.properties inside wscalculate profile. Go to Runtime>Manage>Uncategorized and click on wscalculate profile. 

![wscalculate profile edit](https://github.com/igl100/fuse621-wsdemo/blob/master/docs/image/Capture14.png)

* Click on wsproxy.properties file on the left side.
* When properties are display, click on edit button on the top/right side.
* Change port value to 9000

![wscalculate port change](https://github.com/igl100/fuse621-wsdemo/blob/master/docs/image/Capture15.png)

* Click save button.

Note how changes to the profile will apply to brokers assigned to it.
After brokers refresh, note that services can be accessed at port 9000 too.

# Adding more brokers to cluster

Lets add more brokers to ensure HA. Create two more brokers to publish sum, add and multiply services.

* Run `fabric:container-create-child --profile wscalculator root ws-calculator2`
* Run `fabric:container-create-child --profile wscalculator root ws-calculator3`

* Go to web console at Runtime and wait for container to start.

![WSCalculator Start](https://github.com/igl100/fuse621-wsdemo/blob/master/docs/image/Capture16.png)

* Go to web console at Services>APIs and check that all three services are started. Notice the port was auto assigned to service Calculate.

![Console APIs](https://github.com/igl100/fuse621-wsdemo/blob/master/docs/image/Capture17.png)

* Run `fabric:container-create-child --profile wscalculate root ws-calculate2`
* Run `fabric:container-create-child --profile wscalculate root ws-calculate3`

* Go to web console at Runtime and wait for container to start.

![WSCalculator Start](https://github.com/igl100/fuse621-wsdemo/blob/master/docs/image/Capture18.png)

* Go to web console at Services>APIs and check that all three services are started. Notice the port was auto assigned to service Calculate.

![Console APIs](https://github.com/igl100/fuse621-wsdemo/blob/master/docs/image/Capture19.png)

As you can see, brokers can now be added and stopped at runtime and WSGateway will balance over active services.

Have fun!!
 
