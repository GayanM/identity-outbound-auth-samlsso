/*
 * Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.identity.authenticator.outbound.saml2sso.request;

import org.apache.commons.lang.StringUtils;
import org.wso2.carbon.identity.auth.saml2.common.SAML2AuthConstants;
import org.wso2.carbon.identity.gateway.api.exception.GatewayClientException;
import org.wso2.carbon.identity.gateway.api.request.GatewayRequestBuilderFactory;
import org.wso2.carbon.identity.gateway.util.GatewayUtil;
import org.wso2.msf4j.Request;

import javax.ws.rs.core.Response;

/**
 * The factory responsible of building the SAML2ACSRequest returned from the federated IdP.
 */
public class SAML2ACSRequestBuilderFactory
        extends GatewayRequestBuilderFactory<SAML2ACSRequest.SAML2ACSRequestBuilder> {

    @Override
    public boolean canHandle(Request request) throws GatewayClientException {

        String saml2SSOResponse = GatewayUtil.getParameter(request, SAML2AuthConstants.SAML_RESPONSE);
        if (StringUtils.isNotBlank(saml2SSOResponse)) {
            return true;
        }
        return false;
    }

    public SAML2ACSRequest.SAML2ACSRequestBuilder create(Request request) throws GatewayClientException {
        SAML2ACSRequest.SAML2ACSRequestBuilder builder = new SAML2ACSRequest.SAML2ACSRequestBuilder();
        this.create(builder, request);
        return builder;
    }

    public void create(SAML2ACSRequest.SAML2ACSRequestBuilder builder, Request request) throws GatewayClientException {
        super.create(builder, request);
        builder.setSAML2SSOResponse(GatewayUtil.getParameter(request, SAML2AuthConstants.SAML_RESPONSE));
        builder.setRequestDataKey(GatewayUtil.getParameter(request, SAML2AuthConstants.RELAY_STATE));
    }

    public Response.ResponseBuilder handleException(GatewayClientException exception) {
        return super.handleException(exception);
    }

    public int getPriority() {
        return 40;
    }
}
