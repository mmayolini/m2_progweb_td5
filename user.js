const addUserForm = document.querySelector("#addUser");
const modifyUserForm = document.querySelector("#modifyUser");
const contactList = document.querySelector("#contact-list");


const listUsers = "http://localhost:8080/HelloWorld/users";
const addUser = "http://localhost:8080/HelloWorld/user/add";
const deleteUser = "http://localhost:8080/HelloWorld/user/delete";
const modifyUser = "http://localhost:8080/HelloWorld/user/modify";
const infoUser = "user-info.html";

modifyUserForm.addEventListener('submit', handleSubmitModify);
addUserForm.addEventListener('submit', handleSubmitAdd);

$("#addUser").submit(function(e){
  e.preventDefault();
})

$("#modifyUser").submit(function(e){
  e.preventDefault();
})

async function handleSubmitAdd() {
  let firstname = $('#firstname').val();
  let lastname = $('#lastname').val();
  await fetch(addUser + "/" + firstname + "/" + lastname, {
    credentials: 'include',
    method: 'POST',
    mode: 'no-cors',
    headers: {
      'Content-Type': 'text/plain'
    }
  });
  window.location.href = window.location.href;
  /*$.ajax({
    url: addUser + "/" + firstname + "/" + lastname,
    type: 'POST',
    contentType: 'text/plain',
    data: firstname + "/" + lastname,
    success: function (data) {
      console.log("Insert success ; firstname = " + firstname +" ; lastname = " + lastname);
    },
    error: function (XMLHttpRequest, textStatus, errorThrown) {
      console.log("Insert error ; firstname = " + firstname +" ; lastname = " + lastname);
    }
  });*/
}

async function handleSubmitModify(){
  let firstname = $('#firstnameMod').val();
  let lastname = $('#lastnameMod').val();
  let id = $('#idMod').val();
  await fetch(modifyUser + "/" + id + "/" + firstname + "/" + lastname, {
    credentials: 'include',
    method: 'POST',
    mode: 'no-cors',
    headers: {
      'Content-Type': 'text/plain'
    }
  });
  window.location.href = window.location.href;
  //modifyUserForm.hidden = true;
}

let data;
let res;
loadContactList();

function loadContactList() {
  fetch(listUsers, {
    //credentials: 'include',
    //mode: 'no-cors',
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
      $.each(data['users'], function (lineNum, value) {
        let li = document.createElement("li");
        li.innerHTML = value.firstname + " " + value.lastname + "\t";

        let input_info = document.createElement("input");
        input_info.class = "info";
        input_info.type = "button";
        input_info.value = "Info";
        input_info.addEventListener('click', function () {
          handleInfo(value.id, value.firstname, value.lastname);
        });
        li.append(input_info);

        let input_modify = document.createElement("input");
        input_modify.class = "modify";
        input_modify.type = "button";
        input_modify.value = "Modify";
        input_modify.addEventListener('click', function () {
          handleModify(value.id, value.firstname, value.lastname);
        });
        li.append(input_modify);

        let input_delete = document.createElement("input");
        input_delete.class = "suppr";
        input_delete.type = "button";
        input_delete.value = "Delete";
        input_delete.addEventListener('click', function () {
          handleSuppr(value.id, value.firstname, value.lastname);
        });
        li.append(input_delete);
        contactList.append(li);
      });
    });
  /*$.getJSON(listUsers, function (result) {
    data = result["users"];
    $.each(data, function (lineNum, value) {
      let li = document.createElement("li");
      li.innerHTML = value.firstname + " " + value.lastname + "\t";

      let input = document.createElement("input");
      input.class = "suppr";
      input.type = "button";
      input.value = "Delete";
      input.addEventListener('click', function () {
        handleSuppr(value.id, value.firstname, value.lastname);
      });
      li.append(input);
      contactList.append(li);
    });
  });*/
}

async function handleInfo(id, firstname, lastname){
  window.location = infoUser+"?id="+id;
}

async function handleSuppr(id, firstname, lastname) {
  await fetch(deleteUser + "/" + id, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'text/plain'
    }
  });
  window.location.href = window.location.href;
  /*$.ajax({
    url: deleteUser + "/" + id,
    type: 'DELETE',
    contentType: 'text/plain',
    data: firstname + "/" + lastname + "/" + id,
    success: function (data) {
      console.log("Delete success ; id = " + id);
    },
    error: function (XMLHttpRequest, textStatus, errorThrown) {
      console.log("Delete error ; id = : " + id);
    }
  });*/
}

function handleModify(id, firstname, lastname){
  modifyUserForm.hidden = false;
  const firstnameField = document.querySelector("#firstnameMod");
  const lastnameField = document.querySelector("#lastnameMod");
  const idField = document.querySelector("#idMod");
  firstnameField.value = firstname;
  lastnameField.value = lastname;
  idField.value = id;
  
}
