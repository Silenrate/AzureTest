var validatorModule = (function () {

    /*
    LOCAL http://localhost:8080
    PRODUCTION https://foodapitacs.herokuapp.com
    */
    const url = "http://localhost:8080";

    function loadPage(){
        var isLoggedIn = localStorage.getItem("x-userName");
        if(isLoggedIn==null || isLoggedIn==""){
            window.location.href=url+"/login.html";
        }
    }

    return {
        loadPage:loadPage
    };
});