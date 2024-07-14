/*
 * $Id$
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.struts.webapp.upload;


import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.struts.upload.MultipartRequestHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



/**
 * This class takes the UploadForm and retrieves the text value
 * and file attributes and puts them in the request for the display.jsp
 * page to display them
 *
 * @author Mike Schachter
 * @version $Rev$ $Date$
 */


public class UploadAction extends Action
{
    private static final long serialVersionUID = 6492050366211798185L;

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
        throws Exception {

        if (form instanceof UploadForm) {
            UploadForm theForm = (UploadForm) form;

            final MultipartRequestHandler multipartHandler = form.getMultipartRequestHandler();

            // Was this transaction cancelled?
            if (isCancelled(request)) {
                multipartHandler.rollback();
                return mapping.findForward("home");
            }

            //this line is here for when the input page is upload-utf8.jsp,
            //it sets the correct character encoding for the response
            String encoding = request.getCharacterEncoding();
            if ((encoding != null) && (encoding.equalsIgnoreCase("utf-8")))
            {
                response.setContentType("text/html; charset=utf-8");
            }

            //retrieve the text data
            String text = theForm.getTheText();

            //retrieve the query string value
            String queryValue = theForm.getQueryParam();

            //retrieve the file representation
            FormFile file = theForm.getTheFile();

            final int fileCount;

            // Following is to test fix for STR-3173
            if (file == null) {
                final FormFile[] files = multipartHandler.getFileElements().get("otherFile");
                fileCount = files.length;
                file = fileCount == 0 ? null : files[0];
            } else {
                final FormFile[] files = multipartHandler.getFileElements().get("theFile");
                fileCount = files.length;
            }

            //retrieve the file name
            String fileName= file.getFileName();

            //retrieve the content type
            String contentType = file.getContentType();

            boolean writeFile = theForm.getWriteFile();

            //retrieve the file size
            String size = (file.getFileLength() + " bytes");

            String data = null;

            try (InputStream stream = file.getInputStream()) {
                //retrieve the file data
                if (!writeFile) {
                    //only write files out that are less than 4MB
                    if (file.getFileLength() < (4*1024000)) {

                        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                            byte[] buffer = new byte[8192];
                            int bytesRead = 0;
                            while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
                                baos.write(buffer, 0, bytesRead);
                            }
                            data = new String(baos.toByteArray());
                        }
                    } else {
                        data = new String("The file is greater than 4MB, " +
                                " and has not been written to stream." +
                                " File Size: " + file.getFileLength() + " bytes. This is a" +
                                " limitation of this particular web application, hard-coded" +
                                " in org.apache.struts.webapp.upload.UploadAction");
                    }
                } else {
                    //write the file to the file specified
                    try (OutputStream bos = new FileOutputStream(theForm.getFilePath())) {
                        int bytesRead = 0;
                        byte[] buffer = new byte[8192];
                        while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
                            bos.write(buffer, 0, bytesRead);
                        }
                    }
                    data = "The file has been written to \"" + theForm.getFilePath() + "\"";
                }
            } catch (IOException e) {
                return null;
            }

            //place the data into the request for retrieval from display.jsp
            request.setAttribute("text", text);
            request.setAttribute("queryValue", queryValue);
            request.setAttribute("fileCount", fileCount);
            request.setAttribute("fileName", fileName);
            request.setAttribute("contentType", contentType);
            request.setAttribute("size", size);
            request.setAttribute("data", data);

            //destroy temporary files
            multipartHandler.finish();

            //return a forward to display.jsp
            return mapping.findForward("display");
        }

        //this shouldn't happen in this example
        return null;
    }
}