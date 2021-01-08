
package com.backbase.campaignupload.rest.spec.v1.corporateoffers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/v1/corporate-offers")
@RestController
public interface CorporateoffersApi {


    @RequestMapping(method = RequestMethod.POST, value = "", produces = {
        "application/json"
    })
    @ResponseStatus(HttpStatus.OK)
    public CorporateoffersPostResponseBody postCorporateoffers(
        @RequestParam("file")
        MultipartFile file,
        @RequestParam("uploadedBy")
        String uploadedBy, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

    @RequestMapping(method = RequestMethod.GET, value = "", produces = {
        "application/json"
    })
    @ResponseStatus(HttpStatus.OK)
    public CorporateoffersGetResponseBody getCorporateoffers(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

}
