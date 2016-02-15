package org.bait.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.bait.model.TransferInfo;
import org.bait.rest.model.TransferInfoJsonImpl;
import org.bait.service.api.TransferInfoService;
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
            response = TransferInfoJsonImpl.class)
    public Response createTransferInfo(final TransferInfoJsonImpl transferInfo) {
        TransferInfo createdTransferInfo = transferInfoService.createTransferInformation(transferInfo);
        return Response.status(Response.Status.CREATED).entity(convertToJson(createdTransferInfo)).build();
    }

    @Path("/{transferInfoId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Get the transfer information by ID",
            response = TransferInfoJsonImpl.class)
    public Response getTransferInfo(@PathParam("transferInfoId") final String transferInfoId) {
        TransferInfo transferInfo = transferInfoService.findTransferInfo(transferInfoId);
        if (transferInfo == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok().entity(convertToJson(transferInfo)).build();
    }

    @Path("/{transferInfoId}")
    @DELETE
    @ApiOperation(value = "Delete transfer information by ID")
    public Response deleteTransferInfo(@PathParam("transferInfoId") final String transferInfoId) {
        transferInfoService.deleteTransferInformation(transferInfoId);
        return Response.noContent().build();
    }

    public static TransferInfoJsonImpl convertToJson(final TransferInfo transferInfo) {
        TransferInfoJsonImpl transferInfoJson = new TransferInfoJsonImpl();
        transferInfoJson.setTransferId(transferInfo.getTransferId());
        transferInfoJson.setAmount(transferInfo.getAmount());
        transferInfoJson.setSubject(transferInfo.getSubject());
        transferInfoJson.setBaiId(transferInfo.getBaiId());
        return transferInfoJson;
    }

    @Required
    @Autowired
    public void setTransferInfoService(final TransferInfoService transferInfoService) {
        this.transferInfoService = transferInfoService;
    }
}
