
package com.backbase.campaignupload.rest.spec.v1.campaignupload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/v1/campaignupload")
@RestController
public interface CampaignuploadApi {


    @RequestMapping(method = RequestMethod.POST, value = "", produces = {
        "application/json"
    })
    @ResponseStatus(HttpStatus.OK)
    public CampaignuploadPostResponseBody postCampaignupload(
        @RequestParam("file")
        MultipartFile file,
        @RequestParam("uploadedBy")
        String uploadedBy, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

    @RequestMapping(method = RequestMethod.GET, value = "", produces = {
        "application/json"
    })
    @ResponseStatus(HttpStatus.OK)
    public CampaignuploadGetResponseBody getCampaignupload(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

}
