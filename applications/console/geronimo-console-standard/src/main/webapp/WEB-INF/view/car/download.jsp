<%--
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet"%>
<portlet:defineObjects/>

<p>Processing ${configId}...</p>

<p>Found the following dependencies for this plugin.  Any missing dependencies
    will be installed for you automatically if you proceed.</p>
<ul>
<c:forEach var="jar" items="${dependencies}">
    <li>${jar}</li>
</c:forEach>
</ul>

<form name="<portlet:namespace/>PluginForm" action="<portlet:actionURL/>">
    <input type="hidden" name="file" value="${file}" />
    <input type="hidden" name="configId" value="${configId}" />
    <input type="hidden" name="mode" value="download-after" />
    <input type="hidden" name="repository" value="${repository}" />
    <input type="hidden" name="repo-user" value="${repouser}" />
    <input type="hidden" name="repo-pass" value="${repopass}" />
    <input type="hidden" name="proceed" value="true" />
    <input type="submit" value="Install Plugin" />
</form>

<p><a href="<portlet:actionURL portletMode="view"><portlet:param name="mode" value="index-before" /></portlet:actionURL>">Cancel</a></p>
