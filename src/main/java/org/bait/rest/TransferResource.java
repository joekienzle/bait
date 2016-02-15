package org.bait.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.bait.db.model.TransferInfoDbImpl;
import org.bait.service.TransferInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Controller
@Path("transfer")
@Api("Bait Service")
public class TransferResource {

    private TransferInfoService transferInfoService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Creates a new reference to transfer information",
            response = TransferInfoDbImpl.class)
    public Response createTransferInfo(final TransferInfoDbImpl transferInfoDbImpl) {
        TransferInfoDbImpl createdTransferInfoDbImpl = transferInfoService.createTransferInformation(transferInfoDbImpl);
        return Response.status(Response.Status.CREATED).entity(createdTransferInfoDbImpl).build();
    }

    @Path("/{transferInfoId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Get the bank account information by ID",
            response = TransferInfoDbImpl.class)
    public Response getTransferInfo(@PathParam("transferInfoId") final String transferInfoId) {
        TransferInfoDbImpl transferInfoDbImpl = transferInfoService.findTransferInfo(transferInfoId);
        if (transferInfoDbImpl == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok().entity(transferInfoDbImpl).build();
    }

    @Required
    @Autowired
    public void setTransferInfoService(final TransferInfoService transferInfoService) {
        this.transferInfoService = transferInfoService;
    }
}
