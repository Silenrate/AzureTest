var isLoggedIn = null;

var validatorModule = (function () {

    /*
    LOCAL "http://localhost:8080
    PRODUCCION https://foodapitacs.herokuapp.com/
    */
    const url = "http://localhost:8080";

    function loadPage(){
        if(isLoggedIn==null || isLoggedIn==false){
            window.location.href=url+"/login.html";
        }
    }

    return {
        loadPage:loadPage
    };
});