
package com.backbase.campaignupload.listener.client.v1.partneroffers;

import java.net.URI;
import java.util.Objects;
import com.backbase.buildingblocks.backend.communication.http.HttpCommunicationConstants;
import com.backbase.buildingblocks.backend.internalrequest.InternalRequestContext;
import com.backbase.buildingblocks.presentation.errors.InternalServerErrorException;
import com.backbase.campaignupload.rest.spec.v1.partneroffers.PartneroffersGetResponseBody;
import com.backbase.campaignupload.rest.spec.v1.partneroffers.PartneroffersPostResponseBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.util.UriComponentsBuilder;


/**
 * {@link RestTemplate } based HTTP client.
 * 
 * 
 * 
 */
public class CampaignuploadPartneroffersClientHttpImpl implements CampaignuploadPartneroffersClient
{

    protected RestTemplate restTemplate;
    protected String serviceId;
    protected String scheme;
    protected String baseUri = "/v1";
    protected ObjectMapper objectMapper;
    protected InternalRequestContext internalRequestContext;
    private final static Logger LOG = LoggerFactory.getLogger(CampaignuploadPartneroffersClientHttpImpl.class);

    /**
     * All constructor parameters are required.
     * 
     * @param objectMapper
     *     Jackson ObjectMapper.
     * @param scheme
     *     HTTP or HTTPS
     * @param restTemplate
     *     Ribbon configured RestTemplate.
     * @param internalRequestContext
     *     An InternalRequestContext request-scoped bean.
     * @param serviceId
     *     The application name in the registry.
     *     Typically the spring.application.name from the bootstrap properties of the service implementing this spec.
     *     A default can be configured with the plugin serviceId property.
     * @deprecated
     *     Use the constructor without ObjectMapper
     */
    @Deprecated
    public CampaignuploadPartneroffersClientHttpImpl(RestTemplate restTemplate, String serviceId, String scheme, ObjectMapper objectMapper, InternalRequestContext internalRequestContext) {
        Objects.requireNonNull(restTemplate, "RestTemplate is required.");
        Objects.requireNonNull(serviceId, "ServiceId is required.");
        Objects.requireNonNull(scheme, "scheme is required.");
        Objects.requireNonNull(baseUri, "BaseUri is required.");
        Objects.requireNonNull(internalRequestContext, "internalRequestContext is required.");
        this.restTemplate = restTemplate;
        this.serviceId = serviceId;
        this.scheme = scheme;
        this.objectMapper = objectMapper;
        this.internalRequestContext = internalRequestContext;
    }

    /**
     * All constructor parameters are required.
     * 
     * @param scheme
     *     HTTP or HTTPS
     * @param restTemplate
     *     Ribbon configured RestTemplate.
     * @param internalRequestContext
     *     An InternalRequestContext request-scoped bean.
     * @param serviceId
     *     The application name in the registry.
     *     Typically the spring.application.name from the bootstrap properties of the service implementing this spec.
     *     A default can be configured with the plugin serviceId property.
     */
    public CampaignuploadPartneroffersClientHttpImpl(RestTemplate restTemplate, String serviceId, String scheme, InternalRequestContext internalRequestContext) {
        this(restTemplate, serviceId, scheme, null, internalRequestContext);
    }

    public String getBaseUri() {
        return baseUri;
    }

    public void setBaseUri(String baseUri) {
        Objects.requireNonNull(baseUri, "baseUri is required.");
        this.baseUri = baseUri;
    }

    private void addHeaderIfNotEmpty(HttpHeaders headers, String name, String value) {
        if (!StringUtils.isEmpty(value)) {
            headers.add(name, value);
        }
    }

    public ResponseEntity<PartneroffersPostResponseBody> postPartneroffers() {
        try {
            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(((scheme +"://")+ serviceId)).path((baseUri +"/partner-offers"));
            // If uriString is passed to restTemplate.exchange below, it ends up double-encoding the query string
            // part.  So first, it is converted to a URI, which will get handled properly in RestTemplate:
            String uriString = uriBuilder.buildAndExpand().toUriString();
            URI uri = new URI(uriString);
            HttpHeaders httpHeaders = new HttpHeaders();
            addHeaderIfNotEmpty(httpHeaders, HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
            if (RequestContextHolder.getRequestAttributes()!= null) {
                InternalRequestContext internalRequestContext = this.internalRequestContext;
                String authToken = internalRequestContext.getUserToken();
                addHeaderIfNotEmpty(httpHeaders, HttpCommunicationConstants.X_CXT_USER_TOKEN, authToken);
                // Add information sent over HTTP in the internalRequestContext as request headers.
                addHeaderIfNotEmpty(httpHeaders, HttpCommunicationConstants.X_CXT_REMOTE_USER, internalRequestContext.getRemoteUser());
                addHeaderIfNotEmpty(httpHeaders, HttpCommunicationConstants.X_FORWARDED_FOR, internalRequestContext.getRemoteAddress());
                addHeaderIfNotEmpty(httpHeaders, HttpCommunicationConstants.X_CXT_REQUESTTIME, String.valueOf(internalRequestContext.getRequestTime()));
                addHeaderIfNotEmpty(httpHeaders, HttpCommunicationConstants.X_CXT_USERAGENT, internalRequestContext.getUserAgent());
                addHeaderIfNotEmpty(httpHeaders, HttpCommunicationConstants.X_CXT_CHANNELID, internalRequestContext.getChannelId());
                addHeaderIfNotEmpty(httpHeaders, HttpCommunicationConstants.X_CXT_REQUESTUUID, internalRequestContext.getRequestUuid());
                addHeaderIfNotEmpty(httpHeaders, HttpCommunicationConstants.X_CXT_AUTHSTATUS, String.valueOf(internalRequestContext.getAuthStatus()));
            }
            HttpEntity httpEntity = new HttpEntity(httpHeaders);
            ResponseEntity<PartneroffersPostResponseBody> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, PartneroffersPostResponseBody.class);
            return response;
        } catch (RestClientResponseException exception) {
            //Re-throw the exception if not part of the API
            throw exception;
        } catch (Exception exception) {
            LOG.debug("Unexpected error sending request.", exception);
            throw new InternalServerErrorException(exception.getMessage(), exception);
        }
    }

    public ResponseEntity<PartneroffersGetResponseBody> getPartneroffers() {
        try {
            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(((scheme +"://")+ serviceId)).path((baseUri +"/partner-offers"));
            // If uriString is passed to restTemplate.exchange below, it ends up double-encoding the query string
            // part.  So first, it is converted to a URI, which will get handled properly in RestTemplate:
            String uriString = uriBuilder.buildAndExpand().toUriString();
            URI uri = new URI(uriString);
            HttpHeaders httpHeaders = new HttpHeaders();
            addHeaderIfNotEmpty(httpHeaders, HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
            if (RequestContextHolder.getRequestAttributes()!= null) {
                InternalRequestContext internalRequestContext = this.internalRequestContext;
                String authToken = internalRequestContext.getUserToken();
                addHeaderIfNotEmpty(httpHeaders, HttpCommunicationConstants.X_CXT_USER_TOKEN, authToken);
                // Add information sent over HTTP in the internalRequestContext as request headers.
                addHeaderIfNotEmpty(httpHeaders, HttpCommunicationConstants.X_CXT_REMOTE_USER, internalRequestContext.getRemoteUser());
                addHeaderIfNotEmpty(httpHeaders, HttpCommunicationConstants.X_FORWARDED_FOR, internalRequestContext.getRemoteAddress());
                addHeaderIfNotEmpty(httpHeaders, HttpCommunicationConstants.X_CXT_REQUESTTIME, String.valueOf(internalRequestContext.getRequestTime()));
                addHeaderIfNotEmpty(httpHeaders, HttpCommunicationConstants.X_CXT_USERAGENT, internalRequestContext.getUserAgent());
                addHeaderIfNotEmpty(httpHeaders, HttpCommunicationConstants.X_CXT_CHANNELID, internalRequestContext.getChannelId());
                addHeaderIfNotEmpty(httpHeaders, HttpCommunicationConstants.X_CXT_REQUESTUUID, internalRequestContext.getRequestUuid());
                addHeaderIfNotEmpty(httpHeaders, HttpCommunicationConstants.X_CXT_AUTHSTATUS, String.valueOf(internalRequestContext.getAuthStatus()));
            }
            HttpEntity httpEntity = new HttpEntity(httpHeaders);
            ResponseEntity<PartneroffersGetResponseBody> response = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, PartneroffersGetResponseBody.class);
            return response;
        } catch (RestClientResponseException exception) {
            //Re-throw the exception if not part of the API
            throw exception;
        } catch (Exception exception) {
            LOG.debug("Unexpected error sending request.", exception);
            throw new InternalServerErrorException(exception.getMessage(), exception);
        }
    }

}
