var loginModule = (function () {

    /*
    LOCAL http://localhost:8080
    PRODUCTION https://foodapitacs.herokuapp.com
    */
    const url = "http://localhost:8080";

    function doLogin(){
        var email = $("#username").val();
        var passw = $("#password").val();
        var loginRequest = {username: email, password: passw};
        apiclient().postLogin(loginRequest)
            .then(function(data, textStatus, request) {
                localStorage.setItem("x-userName",email);
                location.href = url+"/index.html";
            }).catch( (e) => {
                Swal.fire({
                    icon: "error",
                    title: "Oops...",
                    text: "Usuario o clave incorrectos"
            });
        });
    }

    function showPassword() {
        var x = document.getElementById("password");
        if (x.type === "password") {
          x.type = "text";
        } else {
          x.type = "password";
        }
    }

    function doLogout(){
        localStorage.clear();
        window.location.href=url+"/login.html";
    }

    return {
        doLogin:doLogin,
        doLogout:doLogout,
        showPassword: showPassword
    };
});