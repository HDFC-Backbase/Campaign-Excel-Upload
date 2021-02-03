
package com.backbase.campaignupload.listener.client.v1.corporateoffers;

import com.backbase.campaignupload.rest.spec.v1.corporateoffers.CorporateoffersGetResponseBody;
import com.backbase.campaignupload.rest.spec.v1.corporateoffers.CorporateoffersPostResponseBody;
import com.backbase.campaignupload.rest.spec.v1.corporateoffers.CorporateoffersPutRequestBody;
import com.backbase.campaignupload.rest.spec.v1.corporateoffers.CorporateoffersPutResponseBody;
import com.backbase.campaignupload.rest.spec.v1.corporateoffers.IdDeleteResponseBody;
import com.backbase.campaignupload.rest.spec.v1.corporateoffers.IdPostResponseBody;
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

    /**
     * <pre>PUT /v1/corporate-offers</pre>
     * 
     * 
     * 
     * 
     * @param corporateoffersPutRequestBody
     *     {@link CorporateoffersPutRequestBody }
     * @return
     *     {@link ResponseEntity }{@code <}{@link CorporateoffersPutResponseBody }{@code >}
     */
    public ResponseEntity<CorporateoffersPutResponseBody> putCorporateoffers(CorporateoffersPutRequestBody corporateoffersPutRequestBody);

    /**
     * <pre>DELETE /v1/corporate-offers/{id}</pre>
     * 
     * <p>Delete specific record</p>
     * 
     * 
     * 
     * @param id
     *     <p>string</p>
     *     
     * @return
     *     {@link ResponseEntity }{@code <}{@link IdDeleteResponseBody }{@code >}
     */
    public ResponseEntity<IdDeleteResponseBody> deleteId(String id);

    /**
     * <pre>POST /v1/corporate-offers/record/{id}</pre>
     * 
     * <p>Approve/Reject specific record</p>
     * 
     * 
     * 
     * @param postIdQueryParameters
     *     {@link PostIdQueryParameters }
     * @param id
     *     <p>string</p>
     *     
     * @return
     *     {@link ResponseEntity }{@code <}{@link IdPostResponseBody }{@code >}
     */
    public ResponseEntity<IdPostResponseBody> postId(String id, PostIdQueryParameters postIdQueryParameters);

}
