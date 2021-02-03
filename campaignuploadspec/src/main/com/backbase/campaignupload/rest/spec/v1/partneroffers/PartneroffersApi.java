
package com.backbase.campaignupload.rest.spec.v1.partneroffers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import com.backbase.buildingblocks.presentation.errors.BadRequestException;
import com.backbase.buildingblocks.presentation.errors.InternalServerErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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

    @RequestMapping(method = RequestMethod.PUT, value = "", produces = {
        "application/json"
    })
    @ResponseStatus(HttpStatus.OK)
    public PartneroffersPutResponseBody putPartneroffers(
        @RequestBody
        @Valid
        PartneroffersPutRequestBody partneroffersPutRequestBody, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

    @RequestMapping(method = RequestMethod.POST, value = "/record/{id}", produces = {
        "application/json"
    })
    @ResponseStatus(HttpStatus.OK)
    public IdPostResponseBody postId(
        @PathVariable("id")
        String id,
        @RequestParam(value = "action", required = true)
        String action, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws BadRequestException, InternalServerErrorException
    ;

}
