
package com.backbase.campaignupload.listener.client.v1.corporateoffers;

import com.backbase.campaignupload.rest.spec.v1.corporateoffers.CorporateoffersGetResponseBody;
import com.backbase.campaignupload.rest.spec.v1.corporateoffers.CorporateoffersPostResponseBody;
import org.springframework.http.ResponseEntity;


/**
 * <p>Client common interface.</p>
 * 
 * 
 * 
 */
public interface CampaignuploadCorporateoffersClient {


    /**
     * <pre>POST /v1/corporate-offers</pre>
     * 
     * 
     * 
     * 
     * @return
     *     {@link ResponseEntity }{@code <}{@link CorporateoffersPostResponseBody }{@code >}
     */
    public ResponseEntity<CorporateoffersPostResponseBody> postCorporateoffers();

    /**
     * <pre>GET /v1/corporate-offers</pre>
     * 
     * 
     * 
     * 
     * @return
     *     {@link ResponseEntity }{@code <}{@link CorporateoffersGetResponseBody }{@code >}
     */
    public ResponseEntity<CorporateoffersGetResponseBody> getCorporateoffers();

}
