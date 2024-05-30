var upstream_url = "http://localhost:8040/api/v1/profile/";

function fetch_profile(r, username) {
    var url = upstream_url + username;

    r.subrequest(url, function(response) {
        if (response.status == 200) {
            r.return(response.body);
        } else {
            r.return(500, "Internal Server Error");
        }
    });
}

fetch_profile(nginx, "Lulu");