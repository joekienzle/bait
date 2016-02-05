package org.bait.rest;

import org.bait.model.Bai;
import org.bait.service.BaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Controller
@Path("bait")
public class BaitResource {

    private BaiService baiService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBaiInfo(final Bai bai) {
        Bai createdBai = baiService.createBankAccountInformation(bai);
        return Response.status(Response.Status.CREATED).entity(createdBai).build();
    }

    @Path("/{baiId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findBankAccountInformation(@PathParam("baiId") final  String baiId) {
        Bai bai = baiService.findBankAccountInformation(baiId);
        return Response.ok().entity(bai).build();
    }

    @Required
    @Autowired
    public void setBaiService(final BaiService baiService) {
        this.baiService = baiService;
    }
}
