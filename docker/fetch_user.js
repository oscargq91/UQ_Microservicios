var upstream_url = "http://localhost:8090/api/v1/users/";

function fetch_user(r) {
    var user_id = r.uri.split('/').pop();
    var url = upstream_url + user_id;

    r.subrequest(url, function(response) {
        if (response.status == 200) {
            r.return(response.body);
        } else {
            r.return(500, "Internal Server Error");
        }
    });
}

fetch_user(nginx);