var urlList, rowNum = 0;
(function(){
    for (i= 0 ; i <= 35; i++){
        $.get("/api/scraps/"+i, function(data, status){
                urlList = data;
                scrapDataFromUrls();
         });
    }
})();

function scrapDataFromUrls() {
    var urls = urlList.urls;
    for (i in urls){
        $.get("/api/scraps/get?"+"url="+urls[i], function(data, status){
                console.log(data);
                addRow(data);
            });
    }
}

function addRow(data) {
    $("#counter").text(++rowNum);
    var row = "<tr>"+"<td>"+data.title+"</td>"+"<td>"+data.view_count+"</td>"+"<td>"+(data.phone.length > 0 ? data.phone : "-----" )+"</td>"+"</tr>";
    $('#myTable tbody').append(row);
}