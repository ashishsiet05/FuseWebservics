<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:wss="http://wssuma.ws.demos.fuse.redhat.com/">

      <xsl:output method="xml" indent="yes" omit-xml-declaration="yes"/>

        <xsl:template match="/">
			<wss:calculateResponse>
         		<operationId>completed</operationId>
         		<responseObject>
					<xsl:copy-of select="child::node()/child::node()"/>
				</responseObject>
      		</wss:calculateResponse>
        </xsl:template>

</xsl:stylesheet>

