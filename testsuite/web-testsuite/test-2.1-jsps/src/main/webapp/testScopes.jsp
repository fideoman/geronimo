<!--
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
-->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><title>test</title></head>
<body>
<c:set var="var1" value="value1" scope="page"/>
<c:set var="var2" value="value2" scope="request"/>
<c:set var="var3" value="value3" scope="session"/>
<c:set var="var4" value="value4" scope="application"/>
<c:out value='${pageScope.var1}' />
<c:out value='${requestScope.var2}' />
<c:out value='${sessionScope.var3}' />
<c:out value='${applicationScope.var4}' />
</body>
</html>
