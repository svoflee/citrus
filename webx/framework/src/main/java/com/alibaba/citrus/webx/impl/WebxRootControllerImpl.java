/*
 * Copyright 2010 Alibaba Group Holding Limited.
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.alibaba.citrus.webx.impl;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.citrus.service.requestcontext.RequestContext;
import com.alibaba.citrus.util.ServletUtil;
import com.alibaba.citrus.webx.WebxComponent;
import com.alibaba.citrus.webx.support.AbstractWebxRootController;
import com.alibaba.citrus.webx.util.WebxUtil;

/**
 * ��<code>WebxRootController</code>��Ĭ��ʵ�֡�
 * 
 * @author Michael Zhou
 */
public class WebxRootControllerImpl extends AbstractWebxRootController {
    @Override
    protected boolean handleRequest(RequestContext requestContext) throws Exception {
        HttpServletRequest request = requestContext.getRequest();

        // Servlet mapping������ƥ�䷽ʽ��ǰ׺ƥ��ͺ�׺ƥ�䡣
        // ����ǰ׺ƥ�䣬���磺/servlet/aaa/bbb��servlet pathΪ/servlet��path infoΪ/aaa/bbb
        // ����ǰ׺ƥ�䣬��mapping patternΪ/*ʱ��/aaa/bbb��servlet pathΪ""��path infoΪ/aaa/bbb
        // ���ں�׺ƥ�䣬���磺/aaa/bbb.html��servlet pathΪ/aaa/bbb.html��path infoΪnull
        //
        // ����ǰ׺ƥ�䣬ȡ��pathInfo�����ں�׺ƥ�䣬ȡ��servletPath��
        String path = ServletUtil.getResourcePath(request);

        // �ٸ���path����component
        WebxComponent component = getComponents().findMatchedComponent(path);
        boolean served = false;

        if (component != null) {
            try {
                WebxUtil.setCurrentComponent(request, component);
                served = component.getWebxController().service(requestContext);
            } finally {
                WebxUtil.setCurrentComponent(request, null);
            }
        }

        return served;
    }
}