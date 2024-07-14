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
<%@ page contentType="text/html; charset=utf-8" %><%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<html>
  <head>
    <title>utf-8 upload page</title>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
  </head>
  <body>
    <!--
            The most important part is to declare your form's enctype to be "multipart/form-data",
            and to have a form:file element that maps to your ActionForm's FormFile property
    -->
    <html:form action="upload-submit.do" enctype="multipart/form-data">Please enter some text, just to demonstrate the handling of text elements as opposed to file elements:
    <br />
    <html:text property="theText" />
    <br />
    <br />Please select the file that you would like to upload:
    <br />
    <html:file property="theFile" />
    <br />
    <br />If you would rather write this file to another file, please check here:
    <html:checkbox property="writeFile" />
    <br />
    <br />If you checked the box to write to a file, please specify the file path here:
    <br />
    <html:text property="filePath" />
    <br />
    <br />
    <html:submit />&nbsp;<html:cancel />
    </html:form>
  </body>
</html>
