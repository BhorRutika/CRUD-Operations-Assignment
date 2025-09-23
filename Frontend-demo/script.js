const baseUrl = "http://localhost:8080/api/employees";

// ----- Fetch and display all employees -----
function fetchEmployees() {
    fetch(`${baseUrl}/all`)
        .then(res => res.json())
        .then(data => {
            const tbody = document.querySelector("#employeeTable tbody");
            tbody.innerHTML = "";
            data.forEach(emp => {
                tbody.innerHTML += `
                    <tr>
                        <td>${emp.id}</td>
                        <td><input type="text" value="${emp.name}" id="name-${emp.id}"></td>
                        <td><input type="email" value="${emp.email}" id="email-${emp.id}"></td>
                        <td>
                            <button onclick="updateEmployee(${emp.id})">Update</button>
                            <button onclick="deleteEmployee(${emp.id})">Delete</button>
                        </td>
                    </tr>
                `;
            });
        });
}

// ----- Add new employee -----
function addEmployee() {
    const name = document.getElementById("name").value;
    const email = document.getElementById("email").value;

    fetch(`${baseUrl}/add`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ name, email })
    })
    .then(res => res.json())
    .then(() => {
        document.getElementById("name").value = "";
        document.getElementById("email").value = "";
        fetchEmployees();
    })
    .catch(err => alert("Error adding employee: " + err));
}

// ----- Update employee -----
function updateEmployee(id) {
    const name = document.getElementById(`name-${id}`).value;
    const email = document.getElementById(`email-${id}`).value;

    fetch(`${baseUrl}/update/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ name, email })
    })
    .then(res => res.json())
    .then(() => fetchEmployees())
    .catch(err => alert("Error updating employee: " + err));
}

// ----- Delete employee -----
function deleteEmployee(id) {
    fetch(`${baseUrl}/delete/${id}`, { method: "DELETE" })
        .then(() => fetchEmployees())
        .catch(err => alert("Error deleting employee: " + err));
}

// ----- Load employees on page load -----
window.onload = fetchEmployees;
