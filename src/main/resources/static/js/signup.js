var signupModule = (function () {

    /*
    LOCAL http://localhost:8080
    PRODUCTION https://foodapitacs.herokuapp.com
    */
    const url = "https://arep-foodapp.herokuapp.com";

    function alertError(message){
        Swal.fire({
            icon: "error",
            title: "Oops...",
            text: message
        });
    }

    function validate(firstName,password){
        var bool = true;
        if (firstName==="") {
            bool = false;
            alertError("The name cannot be empty");
        }else if (password===""){
            bool = false;
            alertError("The password cannot be empty");
        }
        bool = validatePassword(bool, password);
        return bool;
    }

    function validatePassword(bool, password) {
        var regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[$@$!%*?&])([A-Za-z\d$@$!%*?&]|[^ ]){8,40}$/;
        if (bool && !regex.test(password) ) {
            alertError("The password is weak. It must have a capital letter, a minimum length of 8, numbers and symbols like $@$!%*?&");
            bool = false;
        }
        return bool;        
    }

    function showPassword() {
        var x = document.getElementById("pass");
        if (x.type === "password") {
          x.type = "text";
        } else {
          x.type = "password";
        }
    }

    function createUser(){
        var firstName = document.getElementById("name_signup").value;
        var password = document.getElementById("pass").value;
        var bool=validate(firstName,password);
        if(bool){
            var user = {
                "username":firstName,
                "password": password
            };
            apiclient().postUser(user).then(function (){
                const Toast = Swal.mixin({
                    toast: true,
                    position: "top-end",
                    showConfirmButton: false,
                    timer: 3000,
                    width: 300,
                    timerProgressBar: true,
                    didOpen: (toast) => {
                        toast.addEventListener("mouseenter", Swal.stopTimer);
                        toast.addEventListener("mouseleave", Swal.resumeTimer);
                    }
                });
                Toast.fire({
                    icon: "success",
                    title: "Signed in successfully"
                });
            }).catch((e) => {
                Swal.fire({
                    icon: "error",
                    title: "Oops...",
                    text: "Ya existe un usuario con dicho correo"
                });
            });
        }
    }

    return {
        createUser:createUser,
        showPassword: showPassword
    };
});