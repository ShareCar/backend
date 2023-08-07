package pt.sharecar.tenant;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;

@Path("/tenants")
public class TenantResource {

    @Inject
    TenantService service;

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(TenantDTO tenantDTO) {
        try {
            service.createSchema(tenantDTO.tenant);
            return Response.created(URI.create("/tenants/" + tenantDTO.tenant)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

}
