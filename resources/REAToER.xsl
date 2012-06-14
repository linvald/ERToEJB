<?xml version="1.0" encoding="ISO-8859-1"?>

<xsl:transform version="1.0" 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>

<!-- Declare global variables -->
<xsl:variable name="project"><xsl:value-of select="reaspec/@project"/></xsl:variable>

<!-- Match entire document -->
<xsl:template match="/">
	<ermodel 
		xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
		xsi:noNamespaceSchemaLocation="ERSchema.xsd" 
		name="{$project}">

		<xsl:call-template name="resources"/>
		<xsl:call-template name="agents"/>
		<xsl:call-template name="events"/>
		
	</ermodel>
</xsl:template>

<!-- Template for resources -->
<xsl:template name="resources" match="resource"> 
	 <xsl:comment>These are the resources</xsl:comment> 
	 <xsl:for-each select="reaspec/resource">
		<entity name="{@name}">
		 	<xsl:for-each select="@*">
		 		<!-- The name attribute becomes the entity name -->
		 		<xsl:if test="not(name()='name')">
		 			<xsl:call-template name="attributes"/>
		 		</xsl:if>
		 	</xsl:for-each>
		</entity>	
	</xsl:for-each>
</xsl:template>

<!-- Template for agents -->
<xsl:template name="agents" match="agent"> 
	 <xsl:comment>These are the agents</xsl:comment> 
	 <xsl:for-each select="reaspec/agent/">
		<entity name="{@name}">
		 	<xsl:for-each select="@*">
		 		<xsl:if test="not(name()='name')">
		 		//<xsl:value-of select="@*"/>
		 			<xsl:call-template name="attributes"/>
		 		</xsl:if>	
		 	</xsl:for-each>
		 	<!-- custody is a relationship-->
		 	<xsl:for-each select="@*">
		 		<xsl:call-template name="attributes"/>
		 	</xsl:for-each>
		</entity>	
	</xsl:for-each>
</xsl:template>

<!-- Template for events -->
<xsl:template name="events" match="event"> 
	 <xsl:comment>These are the events</xsl:comment> 
	 <xsl:for-each select="reaspec/event">
		<entity name="{@name}">
		 	<xsl:for-each select="@*">
		 		<xsl:call-template name="attributes"/>
		 	</xsl:for-each>
		 		<!-- Check for any <attribute tags-->
		 		<xsl:for-each select="attribute">
		 			<xsl:for-each select="@*">
		 				<xsl:call-template name="attributes"/>
		 			</xsl:for-each>
		 		</xsl:for-each>
		</entity>	
	</xsl:for-each>
</xsl:template>


<!-- Template for all attributes in a node attributes-->
<xsl:template name="attributes" match="@*">
	<!-- The name attribute becomes the entity name -->
		<xsl:variable name="key"><xsl:value-of select="name()"/></xsl:variable>
		<xsl:variable name="val"><xsl:value-of select="."/></xsl:variable>
		<!-- This is written -->
		<attribute value="{$val}" name="{$key}"/>

</xsl:template>

</xsl:transform>

