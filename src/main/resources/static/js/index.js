var indexModule = (function () {

    /*
    LOCAL http://localhost:8080
    PRODUCTION https://foodapitacs.herokuapp.com/
    */
    const url = "http://localhost:8080";

    function loadData(data){
        if(data.length===0){
            document.getElementById("table_footer").innerHTML = "No se encontraron clases";
        } else {
            $("#table_class > tbody").empty();
                data.map(function(c){
                var onclick = "Delete";
                var stri="'"+onclick+"'";
                $("#table_class > tbody").append(
                    "<tr>" +
                    "<td>" + c.name+ "</td>"+
                    "<td>" + onclick + "</td>"+
                    "</tr>"
                );
            });
        }
        console.log(data);
    }

    function loadName(){
        apiclient().getUser(localStorage.getItem("x-userName"))
            .then(function(data, textStatus, request) {
                document.getElementById("welcome").innerHTML = "Welcome "+data.username;
            }).catch( (e) => {
                Swal.fire({
                    icon: "error",
                    title: "Oops...",
                    text: "Error de Autenticacion"
            });
        });
    }

    function loadFoods(){
        var username = localStorage.getItem("x-userName");
        apiclient().getFoods(username)
            .then(function(data, textStatus, request) {
                loadData(data);
            }).catch( (e) => {
                Swal.fire({
                    icon: "error",
                    title: "Oops...",
                    text: "Error al obtener los alimentos de "+ username
            });
        });
    }

    function postFood(){
        var name = document.getElementById("new_name").value;
        var food = {"name":name};
        apiclient().postFood(food,localStorage.getItem("x-userName"))
            .then(function(data, textStatus, request) {
                location.reload();
            }).catch( (e) => {
                Swal.fire({
                    icon: "error",
                    title: "Oops...",
                    text: "Error al insertar "+name
            });
        });
    }

    return {
        loadName:loadName,
        postFood:postFood,
        loadFoods:loadFoods
    };
});