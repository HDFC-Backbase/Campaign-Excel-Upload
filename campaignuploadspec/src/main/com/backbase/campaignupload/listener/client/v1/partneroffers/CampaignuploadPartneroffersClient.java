
package com.backbase.campaignupload.listener.client.v1.partneroffers;

import com.backbase.campaignupload.rest.spec.v1.partneroffers.PartneroffersGetResponseBody;
import com.backbase.campaignupload.rest.spec.v1.partneroffers.PartneroffersPostResponseBody;
import com.backbase.campaignupload.rest.spec.v1.partneroffers.PartneroffersPutRequestBody;
import com.backbase.campaignupload.rest.spec.v1.partneroffers.PartneroffersPutResponseBody;
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

    /**
     * <pre>PUT /v1/partner-offers</pre>
     * 
     * 
     * 
     * 
     * @param partneroffersPutRequestBody
     *     {@link PartneroffersPutRequestBody }
     * @return
     *     {@link ResponseEntity }{@code <}{@link PartneroffersPutResponseBody }{@code >}
     */
    public ResponseEntity<PartneroffersPutResponseBody> putPartneroffers(PartneroffersPutRequestBody partneroffersPutRequestBody);

}
