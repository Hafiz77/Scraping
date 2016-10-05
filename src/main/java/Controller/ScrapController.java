package Controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@Path("/scraps")
public class ScrapController {

    @GET
    @Path("/{pageNumber}")
    @Produces("application/json")
    public Response getUrls(@PathParam("pageNumber") String pageNumber) throws IOException {
        Map<Object, Object> apiResponse = new HashMap<Object, Object>();
        ArrayList<String> subList = new ArrayList<String>();
        String siteUrl;
        try {
            if (pageNumber.length()==0 || Integer.parseInt(pageNumber)==1 || Integer.parseInt(pageNumber)==0) {
                 siteUrl = "http://www.marktplaats.nl/z/auto-s/particulier-bedrijf.html?categoryId=91&attributes=S%2C10898&attributes=S%2C10899&startDateFrom=today";
            }else {
                siteUrl="http://www.marktplaats.nl/z/auto-s/particulier-bedrijf.html?categoryId=91&attributes=S%2C10898&attributes=S%2C10899&startDateFrom=today"+"&currentPage="+pageNumber;
            }

            Document document = Jsoup.connect(siteUrl).get();
            Elements element = document.select("article > .cell-group.listing");
            for (Element el : element) {
                String linkText = el.select("span.mp-listing-priority-product").text();
                if (!linkText.equals("Dagtopper")) {
                    String url = el.select(".listing-title-description .heading>a").attr("href");
                    subList.add(url);
                }
            }
            apiResponse.put("urls", subList);
            return Response.ok(apiResponse).build();

        }catch (Exception ex){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }


    }

    @GET
    @Path("/get")
    @Produces("application/json")
    public Response getDetails(@QueryParam("url") String url) throws IOException {
        Map<Object, Object> resultData = new HashMap<Object, Object>();
        Document dc = Jsoup.connect(url).timeout(0).get();
        String viewCount = dc.select("#view-count").text();
        String title = dc.select("#title").text();
        String imageUrl = dc.select(".image>img").attr("src");
        String phone = dc.select(".phone-link.alternative").text();
        resultData.put("title", title);
        resultData.put("image", imageUrl);
        resultData.put("view_count", viewCount);
        resultData.put("phone", phone);

        return Response.ok(resultData).build();
    }
}
