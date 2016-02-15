package org.bait.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.bait.model.Bai;
import org.bait.service.BaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Controller
@Path("bai")
@Api("Bait Service")
public class BaitResource {

    private BaiService baiService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Creates a new reference to bank account information",
            response = Bai.class)
    public Response createBaiInfo(final Bai bai) {
        Bai createdBai = baiService.createBankAccountInformation(bai);
        return Response.status(Response.Status.CREATED).entity(createdBai).build();
    }

    @Path("/{baiId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Get the bank account information by ID",
            response = Bai.class)
    public Response findBankAccountInformation(@PathParam("baiId") final  String baiId) {
        Bai bai = baiService.findBankAccountInformation(baiId);
        if (bai == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok().entity(bai).build();
    }

    @Path("/{baiId}")
    @DELETE
    public Response deleteBaiInfo(@PathParam("baiId") final String baiId) {
        baiService.deleteBankAccountInformation(baiId);
        return Response.noContent().build();
    }

    @Required
    @Autowired
    public void setBaiService(final BaiService baiService) {
        this.baiService = baiService;
    }

}
