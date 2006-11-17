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
<portlet:defineObjects/>
<p>This portlet allows the user to deploy new applications on Geronimo. Web applications require deployment plans, which are either packaged with the war file or kept in a separate file which is typically called geronimo-web.xml.<br>
</p>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="150" align="right" valign="top" class="MediumBackground" style="padding: 10px 10px 5px 10px"><strong>Archive</strong></td>
    <td class="LightBackground" style="padding: 10px 5px 10px 10px">Browse to select the war file in the Archive box. </td>
  </tr>
  <tr>
    <td width="150" align="right" valign="top" class="MediumBackground" style="padding: 10px 10px 5px 10px"><strong>Plan</strong></td>
    <td class="LightBackground" style="padding: 10px 5px 10px 10px">If the deployment plan is separate from the war file, then browse to select it.</td>
  </tr>
  <tr>
    <td width="150" align="right" valign="top" class="MediumBackground" style="padding: 10px 10px 10px 5px; color: #1E1E52;">Start app after install</td>
    <td class="LightBackground" style="padding: 10px 5px 10px 10px">Check this to automatically start the application when it is installed. Otherwise, the user can start the application by clicking on the associated &quot;Start&quot; toggle in the&quot;Installed Applications&quot; window. By default this checkbox is checked and newly installed applications will automatically be started.</td>
  </tr>
  <tr>
    <td width="150" align="right" valign="top" class="MediumBackground" style="padding: 10px 10px 5px 10px"><img src="/console/images/install.gif" /></td>
    <td class="LightBackground" style="padding: 10px 5px 10px 10px">Click on Install to install the application in the Geronimo server.</td>
  </tr>
</table>
