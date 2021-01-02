
package com.backbase.campaignupload.listener.client.v1.partneroffers;

import com.backbase.campaignupload.rest.spec.v1.partneroffers.PartneroffersGetResponseBody;
import com.backbase.campaignupload.rest.spec.v1.partneroffers.PartneroffersPostResponseBody;
import org.springframework.http.ResponseEntity;


/**
 * <p>Client common interface.</p>
 * 
 * 
 * 
 */
public interface CampaignuploadPartneroffersClient {


    /**
     * <pre>POST /v1/partner-offers</pre>
     * 
     * 
     * 
     * 
     * @return
     *     {@link ResponseEntity }{@code <}{@link PartneroffersPostResponseBody }{@code >}
     */
    public ResponseEntity<PartneroffersPostResponseBody> postPartneroffers();

    /**
     * <pre>GET /v1/partner-offers</pre>
     * 
     * 
     * 
     * 
     * @return
     *     {@link ResponseEntity }{@code <}{@link PartneroffersGetResponseBody }{@code >}
     */
    public ResponseEntity<PartneroffersGetResponseBody> getPartneroffers();

}
