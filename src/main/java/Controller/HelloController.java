package Controller;

import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Abdullah on 10/5/2016.
 */


@Path("/hello")
public class HelloController {
    @GET
    @Path("/world")
    @Produces("application/json")
    public Response test() {
        Map<Object, Object> apiResponse = new HashMap<Object, Object>();
        String test="Hello World";
        apiResponse.put("test",test);
        return Response.status(200).entity(apiResponse).build();
    }
}
