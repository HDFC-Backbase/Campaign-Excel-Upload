
package com.backbase.campaignupload.listener.client.v1.partneroffers;

import javax.validation.constraints.Pattern;
import com.backbase.buildingblocks.backend.internalrequest.InternalRequestContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


/**
 * Configuration class providing an HTTP/HTTPS implementation of the {@link CampaignuploadPartneroffersClient }.
 * 
 * <p>Conditional on:</p>
 * <ul>
 * <li><strong>There being no other implementation of the {@link CampaignuploadPartneroffersClient }
 *     interface in the application context</strong>.
 *     (Therefore, if the service configures its own {@link CampaignuploadPartneroffersClientHttpImpl } to use, the instance configured here
 *     will be ignored.)
 * <li><strong>The configuration property {@code backbase.communication.outbound} being set to
 *     {@code HTTP}</strong>.
 * <li><strong>There being a {@code RestTemplate} bean called 'interServiceRestTemplate'.</strong> The 'interServiceRestTemplate' bean may not be available if the communication module is not on the class path..
 * </ul>
 * 
 */
@AutoConfigureAfter(name = {
    "com.backbase.buildingblocks.backend.communication.http.HttpCommunicationConfiguration",
    "com.backbase.buildingblocks.backend.security.auth.config.SecurityContextUtilConfiguration"
})
@Configuration("com.backbase.campaignupload.listener.client.v1.partneroffers.CampaignuploadPartneroffersClientHttpImplAutoConfiguration")
@ConfigurationProperties("backbase.communication.services.campaignupload")
public class CampaignuploadPartneroffersClientHttpImplAutoConfiguration {

    /**
     * The host part of the URLs given to the RestTemplate.
     * 
     * <p>This is used by Spring Cloud LoadBalancer to look up the service instances in the registry.</p>
     * 
     */
    private String serviceId;
    /**
     * The baseUri value from the RAML.
     * 
     */
    private String baseUri = "/v1";
    /**
     * Request URI scheme. Can be http or https 
     * 
     * 
     * 
     */
    @Pattern(regexp = "https?")
    private String scheme;

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getBaseUri() {
        return baseUri;
    }

    public void setBaseUri(String baseUri) {
        this.baseUri = baseUri;
    }

    @ConditionalOnMissingBean
    @Bean("com.backbase.campaignupload.listener.client.v1.partneroffers.CampaignuploadPartneroffersClient")
    @ConditionalOnBean(name = "interServiceRestTemplate")
    public CampaignuploadPartneroffersClient createClient(
        @Value("${backbase.communication.http.default-scheme:http}")
        String defaultScheme,
        @Qualifier("interServiceRestTemplate")
        RestTemplate restTemplate, ObjectMapper objectMapper, InternalRequestContext internalRequestContext) {
        CampaignuploadPartneroffersClientHttpImpl clientInstance = new CampaignuploadPartneroffersClientHttpImpl(restTemplate, serviceId, ((scheme == null)?defaultScheme:scheme), objectMapper, internalRequestContext);
        clientInstance.setBaseUri(baseUri);
        return clientInstance;
    }

}
