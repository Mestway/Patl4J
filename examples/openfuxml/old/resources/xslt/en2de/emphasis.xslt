<xsl:stylesheet version="1.0"
	xmlns:ofx="http://www.openfuxml.org"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	
	<xsl:template match="ofx:emphasis/@style"/>
	
	<xsl:template match="ofx:emphasis/@italic"/>
	<xsl:template match="ofx:emphasis/@bold"/>
	<xsl:template match="ofx:emphasis/@underline"/>
	
	<xsl:template match="ofx:emphasis[@style='typewriter']">
		<xsl:element name="schreibmaschine"><xsl:apply-templates select="@*|node()"/></xsl:element>
	</xsl:template>
	
	<xsl:template match="ofx:emphasis[@style='normal']">
		<xsl:call-template name="attr2elem">
			<xsl:with-param name="attr" select="@*"/>
			<xsl:with-param name="max" select="count(@*)"/>
			<xsl:with-param name="akt" select="1"/>
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template name="attr2elem">
		<xsl:param name="attr"/>
		<xsl:param name="max"/>
		<xsl:param name="akt"/>
		
		<xsl:if test="$akt &lt;= $max">
			<xsl:choose>
				<xsl:when test="$attr[$akt] = 'true'">
					<xsl:choose>
						<xsl:when test="local-name($attr[$akt]) = 'italic'">
							<xsl:element name="kursiv">
								<xsl:call-template name="attr2elem">
									<xsl:with-param name="attr" select="$attr"/>
									<xsl:with-param name="max" select="$max"/>
									<xsl:with-param name="akt" select="$akt + 1"/>
								</xsl:call-template>
								<xsl:if test="$akt = $max">
									<xsl:apply-templates select="@*|node()"/>
								</xsl:if>
							</xsl:element>
						</xsl:when>
						<xsl:when test="local-name($attr[$akt]) = 'bold'">
							<xsl:element name="fett">
								<xsl:call-template name="attr2elem">
									<xsl:with-param name="attr" select="$attr"/>
									<xsl:with-param name="max" select="$max"/>
									<xsl:with-param name="akt" select="$akt + 1"/>
								</xsl:call-template>
								<xsl:if test="$akt = $max">
									<xsl:apply-templates select="@*|node()"/>
								</xsl:if>
							</xsl:element>
						</xsl:when>
						<xsl:when test="local-name($attr[$akt]) = 'underline'">
							<xsl:element name="unterstrichen">
								<xsl:call-template name="attr2elem">
									<xsl:with-param name="attr" select="$attr"/>
									<xsl:with-param name="max" select="$max"/>
									<xsl:with-param name="akt" select="$akt + 1"/>
								</xsl:call-template>
								<xsl:if test="$akt = $max">
									<xsl:apply-templates select="@*|node()"/>
								</xsl:if>
							</xsl:element>
						</xsl:when>
						<xsl:otherwise>
							<xsl:element name="{local-name($attr[$akt])}">
								<xsl:call-template name="attr2elem">
									<xsl:with-param name="attr" select="$attr"/>
									<xsl:with-param name="max" select="$max"/>
									<xsl:with-param name="akt" select="$akt + 1"/>
								</xsl:call-template>
								<xsl:if test="$akt = $max">
									<xsl:apply-templates select="@*|node()"/>
								</xsl:if>
							</xsl:element>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:when>      
				<xsl:otherwise>
					<xsl:call-template name="attr2elem">
						<xsl:with-param name="attr" select="$attr"/>
						<xsl:with-param name="max" select="$max"/>
						<xsl:with-param name="akt" select="$akt + 1"/>
					</xsl:call-template>
					<xsl:if test="$akt = $max">
						<xsl:value-of select="."/>
					</xsl:if>     
				</xsl:otherwise>
			</xsl:choose> 
		</xsl:if>
	</xsl:template>
	
</xsl:stylesheet>