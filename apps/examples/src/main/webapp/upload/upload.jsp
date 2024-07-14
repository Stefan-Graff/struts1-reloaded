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
<%@ page import="org.apache.struts.action.*,
                 java.util.Iterator,
                 org.apache.struts.webapp.upload.UploadForm,
                 org.apache.struts.Globals" %><%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %><%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %><%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<html>
  <head>
    <title>File Upload Example</title>
  </head>
  <body>
<logic:messagesPresent>
   <ul>
   <html:messages id="error">
      <li><bean:write name="error"/></li>
   </html:messages>
   </ul><hr />
</logic:messagesPresent>

    <!--
            The most important part is to declare your form's enctype to be "multipart/form-data",
            and to have a form:file element that maps to your ActionForm's FormFile property
    -->
    <html:form action="upload-submit.do?queryParam=Successful" enctype="multipart/form-data">
    <p>Please enter some text, just to demonstrate the handling of text elements as opposed to file elements: <br />
    <html:text property="theText" errorStyle="background-color: yellow"/></p>
    <p>Please select the file that you would like to upload: <br />
    <html:file property="theFile"  errorStyle="background-color: yellow" /></p>
    <p>If you would rather write this file to another file, please check here: <br />
    <html:checkbox property="writeFile" /></p>
    <p>If you checked the box to write to a file, please specify the file path here: <br />
    <html:text property="filePath"  errorStyle="background-color: yellow"/></p>
    <p>
    <html:submit />&nbsp;<html:cancel />
    </p>
    </html:form>

    <hr />
    <h3>Request Parameters</h3>

    <p>Display the request parameter values to show that the multipart request
       retains them in the event of a validation error.</p>

    <b>The Text:</b>&nbsp;<%= request.getParameter("theText") %><br>
    <b>Write File:</b>&nbsp;<%= request.getParameter("writeFile") %><br>
    <b>File Path:</b>&nbsp;<%= request.getParameter("filePath") %><br>

    <hr />

  </body>
</html>
