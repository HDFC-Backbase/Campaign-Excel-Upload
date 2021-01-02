
package com.backbase.campaignupload.listener.client.v1.campaignupload;

import com.backbase.campaignupload.rest.spec.v1.campaignupload.CampaignuploadGetResponseBody;
import com.backbase.campaignupload.rest.spec.v1.campaignupload.CampaignuploadPostResponseBody;
import org.springframework.http.ResponseEntity;


/**
 * <p>Client common interface.</p>
 * 
 * 
 * 
 */
public interface CampaignuploadCampaignuploadClient {


    /**
     * <pre>POST /v1/campaignupload</pre>
     * 
     * 
     * 
     * 
     * @return
     *     {@link ResponseEntity }{@code <}{@link CampaignuploadPostResponseBody }{@code >}
     */
    public ResponseEntity<CampaignuploadPostResponseBody> postCampaignupload();

    /**
     * <pre>GET /v1/campaignupload</pre>
     * 
     * 
     * 
     * 
     * @return
     *     {@link ResponseEntity }{@code <}{@link CampaignuploadGetResponseBody }{@code >}
     */
    public ResponseEntity<CampaignuploadGetResponseBody> getCampaignupload();

}
