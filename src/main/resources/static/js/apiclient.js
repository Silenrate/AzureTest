var apiclient = (function () {

    /*
    LOCAL http://localhost:8080
    PRODUCTION https://foodapitacs.herokuapp.com
    */
    const urlAPI = "https://arep-foodapp.herokuapp.com";

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

    function postFood(food,username){
        var data = $.ajax({
            url: urlAPI+"/foods",
            type: "POST",
            data: JSON.stringify(food),
            contentType: "application/json",
            headers: {"x-userName" : username}
        });
        return data;
    }

    function getUser(email){
        var data = $.ajax({
            url: urlAPI+"/users/"+email,
            type: "GET"
        });
        return data;
    }

    function getFoods(email){
        var data = $.ajax({
            url: urlAPI+"/foods/"+email,
            type: "GET"
        });
        return data;
    }

    function deleteFood(foodId,username){
        var data = $.ajax({
            url: urlAPI+"/foods/"+foodId,
            type: "DELETE",
            contentType: "application/json",
            headers: {"x-userName" : username}
        });
        return data;
    }

    return {
        postLogin:postLogin,
        postUser:postUser,
        postFood:postFood,
        getUser:getUser,
        getFoods:getFoods,
        deleteFood:deleteFood
    };
});