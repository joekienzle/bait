package org.bait.rest;

import org.bait.db.BaiRepositoryInMemoryImpl;
import org.bait.model.BankAccountInformation;
import org.bait.service.BaiService;
import org.bait.service.BaiServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("bait")
public class BaitResource {

    private BaiService baiService;

    // TODO Set up implementations manually for now. This will change when the Spring Inversion of Control Container is taking care of this.
    public BaitResource() {
        baiService = new BaiServiceImpl();
        baiService.setBaiRepository(new BaiRepositoryInMemoryImpl());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBaiInfo(BankAccountInformation bankAccountInformation) {
        BankAccountInformation createdAccountInformation = baiService.createBankAccountInformation(bankAccountInformation);
        return Response.status(Response.Status.CREATED).entity(createdAccountInformation).build();
    }

    @Path("/{baiId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findBankAccountInformation(@PathParam("baiId") String baiId) {
        BankAccountInformation bankAccountInformation = baiService.findBankAccountInformation(baiId);
        return Response.ok().entity(bankAccountInformation).build();
    }

    public void setBaiService(BaiService baiService) {
        this.baiService = baiService;
    }
}
