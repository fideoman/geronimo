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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet" %>
<portlet:defineObjects/>
<script language="javascript">
<!--
	function doCheck(){
		var poolMax = <portlet:namespace/>.poolMaxSize.value;
        var block = <portlet:namespace/>.blocking.value;
        
        if(isNaN(parseFloat(poolMax))){
            alert("Please enter a numeric value for Pool Max Capacity.");
            return false;
        }
        if(isNaN(parseFloat(block))){
            alert("Please enter a numeric value for Blocking Timeout.");
            return false;
        }
        return true;
	}
//-->
</script>

<form name="<portlet:namespace/>" action="<portlet:actionURL/>" onSubmit="return doCheck();">
<input type="hidden" name="mode" value="addACF">
<table width="100%%"  border="0">
  <tr>
    <td width="16%"> <div align="right">Name: </div></td>
    <td width="84%"><input name="acfName" type="text" size="50"></td>
  </tr>
  <tr>
    <td><div align="right"></div></td>
    <td> <p>Name of this ActiveMQ Connection Factory.</td>
  </tr>
  <tr>
    <td> <div align="right">Server URL: </div></td>
    <td><input name="serverURL" type="text" size="50"></td>
  </tr>
  <tr>
    <td><div align="right"></div></td>
    <td> URL of the server to connect to.</td>
  </tr>
  <tr>
    <td> <div align="right">User Name: </div></td>
    <td><input name="userName" type="text" size="50"></td>
  </tr>
  <tr>
    <td><div align="right"></div></td>
    <td> User name to use.</td>
  </tr>
  <tr>
    <td> <div align="right">Password: </div></td>
    <td><input name="pword" type="password"></td>
  </tr>
  <tr>
    <td><div align="right"></div></td>
    <td> Password corresponding to user name used. </td>
  </tr>
  <tr>
    <td> <div align="right">Pool Max Capacity: </div></td>
    <td><input name="poolMaxSize" type="text" size="20" value="0"></td>
  </tr>
  <tr>
    <td><div align="right"></div></td>
    <td> Maximum connection pool size. </td>
  </tr>
  <tr>
    <td> <div align="right">Blocking Timeout (milliseconds): </div></td>
    <td><input name="blocking" type="text" size="20" value="0"></td>
  </tr>
  <tr>
    <td><div align="right"></div></td>
    <td> Blocking timeout in milliseconds. </td>
  </tr>
  <tr>
    <td><div align="right"></div></td>
    <td><input name="submit" type="submit" value="Create"></td>
  </tr>
</table>
</form>
<a href='<portlet:actionURL portletMode="view">
    <portlet:param name="mode" value="list" />
    </portlet:actionURL>'>List Connection Factories</a>
