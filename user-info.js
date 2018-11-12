const infoUser = "http://localhost:8080/HelloWorld/user/info";
const userIdField = document.querySelector("#user_id");
const firstnameField = document.querySelector("#firstname");
const lastnameField = document.querySelector("#lastname");

let split_URL = window.location.search.substring(1).split("=");
let user_ID = split_URL[1];

loadUser(user_ID);

function loadUser(user_ID) {
    let data;
    let res;
    fetch(infoUser + "/" + user_ID, {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    })
        .then(function (res) {
            return res.json();
        })
        .then(function (data) {
            console.log(data);
            userIdField.innerHTML = data.id;
            firstnameField.innerHTML = data.firstname;
            lastnameField.innerHTML = data.lastname;
        });
}