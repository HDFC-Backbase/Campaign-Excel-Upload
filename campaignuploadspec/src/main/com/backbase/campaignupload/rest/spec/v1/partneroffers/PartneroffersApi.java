
package com.backbase.campaignupload.rest.spec.v1.partneroffers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/v1/partner-offers")
@RestController
public interface PartneroffersApi {


    @RequestMapping(method = RequestMethod.POST, value = "", produces = {
        "application/json"
    })
    @ResponseStatus(HttpStatus.OK)
    public PartneroffersPostResponseBody postPartneroffers(
        @RequestParam("file")
        MultipartFile file, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

    @RequestMapping(method = RequestMethod.GET, value = "", produces = {
        "application/json"
    })
    @ResponseStatus(HttpStatus.OK)
    public PartneroffersGetResponseBody getPartneroffers(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

}
