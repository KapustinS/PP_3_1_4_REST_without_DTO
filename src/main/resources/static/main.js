$(async function() {
    await thisUser();
    await getAllUsers();
    await getRolesList();
    await newUser();
    editUser();
    deleteUser();
});

async function getAllUsers() {
  let response = await fetch("http://localhost:8080/api");
  let json = await response.json();
  let temp = "";

  json.forEach(function (user) {
    temp += `
            <tr>
            <td id="id${user.id}">${user.id}</td>
            <td id="username${user.id}">${user.firstName}</td> 
            <td id="lastName${user.id}">${user.lastName}</td> 
            <td id="age${user.id}">${user.age}</td>
            <td id="email${user.id}">${user.email}</td>
            <td id="roles${user.id}">${user.roles
      .map((r) => r.name.replace("ROLE_", ""))
      .join(", ")}</td>
        <td>
        <button class="btn btn-info btn-md" 
                type="button"
                data-toggle="modal" 
                data-target="#EditModal" 
                onclick="openModal(${user.id})"
            >Edit</button>
        </td>
        <td>
            <button type="button"
                    class="btn btn-danger" data-toggle="modal"
                    data-target="#DeleteModal"
                    data-row="${user}"
                    onclick="openModal(${user.id})"
                >Delete</button>
        </td>
        </tr>
            `;
  });

  document.getElementById("allUsersTable").innerHTML = temp;
}

async function openModal(id) {
  let response = await fetch("http://localhost:8080/api/" + id, {
    headers: {
      Accept: "application/json",
      "Content-Type": "application/json;charset=UTF-8",
    },
  });
  let user = await response.json();

  document.getElementById("idEdit").value = user.id;
  document.getElementById("firstNameEdit").value = user.firstName;
  document.getElementById("lastNameEdit").value = user.lastName;
  document.getElementById("ageEdit").value = user.age;
  document.getElementById("emailEdit").value = user.email;

  document.getElementById("idDelete").value = user.id;
  document.getElementById("firstNameDelete").value = user.firstName;
  document.getElementById("lastNameDelete").value = user.lastName;
  document.getElementById("ageDelete").value = user.age;
  document.getElementById("emailDelete").value = user.email;
}

async function getRolesList() {
  const rolesResponse = await fetch("http://localhost:8080/api/roles");
  const roles = await rolesResponse.json();

  let temp = "";
  roles.forEach((role) => {
    temp += `<option value="${role.id}">${role.name.replace(
      "ROLE_",
      ""
    )}</option>`;
  });

  document.getElementById("RoleEdit").innerHTML = temp;
  document.getElementById("RoleDelete").innerHTML = temp;
  document.getElementById("RoleNew").innerHTML = temp;
}


async function newUser() {
  const newUserForm = document.forms["newUserForm"];

  newUserForm.addEventListener("submit", async (ev) => {
    ev.preventDefault();
    let newUserRoles = [];

    for (let i = 0; i < newUserForm.RoleNew.options.length; i++) {
      if (newUserForm.RoleNew.options[i].selected) {
        newUserRoles.push({
          id: newUserForm.RoleNew.options[i].value,
          name: "ROLE_".concat(newUserForm.RoleNew.options[i].text),
        });
      }
    }

    const newUserRequest = await fetch("http://localhost:8080/api", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        firstName: newUserForm.firstName.value,
        lastName: newUserForm.lastName.value,
        age: newUserForm.age.value,
        email: newUserForm.email.value,
        password: newUserForm.password.value,
        roles: newUserRoles,
      }),
    });

    if (newUserRequest) {
      await newUserForm.reset();
      $("#users-table-tab").click();
      await getAllUsers();
    }
  });
}

async function editUser() {
  const editForm = document.forms["editForm"];
  editForm.addEventListener("submit", async (ev) => {
    ev.preventDefault();
    let editUserRoles = [];

    for (let i = 0; i < editForm.RoleEdit.options.length; i++) {
      if (editForm.RoleEdit.options[i].selected)
        editUserRoles.push({
          id: editForm.RoleEdit.options[i].value,
          name: "ROLE_".concat(editForm.RoleEdit.options[i].text),
        });
      console.log(editForm.RoleEdit.options[i].text);
    }

    const update = await fetch(
      "http://localhost:8080/api/" + editForm.id.value,
      {
        method: "PATCH",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          id: editForm.id.value,
          firstName: editForm.firstName.value,
          lastName: editForm.lastName.value,
          age: editForm.age.value,
          email: editForm.email.value,
          password: editForm.password.value,
          roles: editUserRoles,
        }),
      }
    );

    if (update) {
      await $("#EditModal .close").click();
      await getAllUsers();
    }
  });
}

async function deleteUser() {
  const deleteForm = document.forms["deleteForm"];
  deleteForm.addEventListener("submit", async (ev) => {
    ev.preventDefault();

    const del = await fetch(
      "http://localhost:8080/api/" + document.getElementById("idDelete").value,
      {
        method: "DELETE",
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json;charset=UTF-8",
        },
      }
    );

    if (del) {
      await $("#DeleteModal .close").click();
      await getAllUsers();
    }
  });
}

async function thisUser() {
  let response = await fetch("http://localhost:8080/api/user");
  let user = await response.json();

        // Добавляем информацию в шапку
        $('#headerUsername').append(user.email);
        let roles = user.roles.map(role => " " + role.name.substring(5));
        $('#headerRoles').append(roles);

        //Добавляем информацию в таблицу
        let userInfo = `$(
            <tr>
                <td>${user.id}</td>
                <td>${user.firstName}</td>
                <td>${user.lastName}</td>
                <td>${user.age}</td>
                <td>${user.email}</td>
                <td>${roles}</td>)`;
        $('#userPanelBody').append(userInfo);

}
