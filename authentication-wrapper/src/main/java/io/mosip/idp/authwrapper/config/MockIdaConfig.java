/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package io.mosip.idp.authwrapper.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.mosip.idp.authwrapper.service.MockAuthenticationService;
import io.mosip.idp.core.spi.TokenService;
import io.mosip.kernel.signature.service.SignatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@ConditionalOnProperty(value = "mosip.idp.authn.wrapper.impl", havingValue = "MockAuthenticationService")
@Configuration
public class MockIdaConfig {

    @Value("${mosip.idp.authn.mock.impl.token-expire-sec:30}")
    private int tokenExpireInSeconds;

    @Value("${mosip.idp.authn.mock.impl.persona-repo:/mockida/personas/}")
    private String personaRepoDirPath;

    @Value("${mosip.idp.authn.mock.impl.policy-repo:/mockida/policies/}")
    private String policyRepoDirPath;

    @Value("${mosip.idp.authn.mock.impl.claims-mapping-file:claims_attributes_mapping.json}")
    private String claimsMappingFilePath;

    @Autowired
    private SignatureService signatureService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public MockAuthenticationService mockAuthenticationService() throws IOException {
        return new MockAuthenticationService(personaRepoDirPath, policyRepoDirPath, claimsMappingFilePath,
                tokenExpireInSeconds, signatureService, tokenService, objectMapper);
    }
}
