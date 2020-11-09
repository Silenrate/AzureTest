var apiclient = (function () {

    /*
    LOCAL "http://localhost:8080
    PRODUCTION https://foodapitacs.herokuapp.com/
    */
    const urlAPI = "http://localhost:8080";

    function postLogin(loginRequest){
        var data = $.ajax({
                url: urlAPI+"/login",
                type: "POST",
                data: JSON.stringify(loginRequest),
                contentType: "application/json"
        });
        return data;
    }

    function postUser(user){
        var data = $.ajax({
            url: urlAPI+"/users",
            type: "POST",
            data: JSON.stringify(user),
            contentType: "application/json"
        });
        return data;
    }

    return {
        postLogin:postLogin,
        postUser:postUser
    };
});