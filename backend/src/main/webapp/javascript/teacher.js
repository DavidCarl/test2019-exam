function teachersignup(){
    var messagediv = document.getElementById('messagediv');
    let emailField = document.getElementById('emailField').value;
    let nameField = document.getElementById('nameField').value;
    let eduField = document.getElementById('eduField').value;
    if(emailField !== '' && nameField !== '' && eduField !== ''){
        var list = {'email': emailField, 'name': nameField, 'edu': eduField};
        post_api('http://localhost:8080/2/api/teacher/register/' + nameField + '/' + emailField + '/' + eduField, list)
    }else if(emailField === ''){
        messagediv.textContent = 'Please enter an email!'
    }else if(nameField === ''){
        messagediv.textContent = 'Please enter a name!'
    }else if(eduField === ''){
        messagediv.textContent = 'Please select a education!'
    }else{
        messagediv.textContent = 'Something went wrong!'
    }
}

async function getInformation() {
    let emailField = document.getElementById('emailField').value;
    let eligibleDiv = document.getElementById('eligibleDiv');
    var nameDiv = document.getElementById('nameDiv');
    var messagediv = document.getElementById('messagediv');

    if(emailField !== ''){
        const resp_course = await get_api('http://localhost:8080/2/api/teacher/courses/' + emailField);
        const resp_eligible = await get_api('http://localhost:8080/2/api/teacher/status/' + emailField);
        const information = await get_api('http://localhost:8080/2/api/teacher/' + emailField);
        if(messagediv.innerText === 'SUCCESS'){
            const resp_json = JSON.parse(resp_course);
            const eligible = JSON.parse(resp_eligible);
            const info = JSON.parse(information);
            if(eligible["isEligible"] === true){
                eligibleDiv.textContent = 'You are eligible to vote!';
            }else{
                eligibleDiv.textContent = 'You are not eligible to vote!';
            }
            nameDiv.innerText = info['name'];
            buildTable(resp_json);
        }
    }else{
        alert('Please enter an email!')
    }
}

function buildTable(data){
    var courseTable = document.getElementById('courseTable');

    var tbl = document.createElement("table");
    var tblBody = document.createElement("tbody");
    var headerRow = document.createElement("tr");
    var headerName = document.createElement("th");
    var headerRoom = document.createElement("th");
    var headerPrice = document.createElement("th");

    headerName.textContent = 'Course name';
    headerRoom.textContent = 'Course room';
    headerPrice.textContent = 'Course price';

    headerRow.appendChild(headerName);
    headerRow.appendChild(headerRoom);
    headerRow.appendChild(headerPrice);

    tblBody.appendChild(headerRow);

    for (var i = 0; i < data.length; i++) {
        var row = document.createElement("tr");

        if(data[i] !== null ){
            var name = document.createElement("td");
            var room = document.createElement("td");
            var price = document.createElement("td");
            name.textContent = data[i]['_name'];
            room.textContent = data[i]['_roomNr'];
            price.textContent = data[i]['_price'];
            row.appendChild(name);
            row.appendChild(room);
            row.appendChild(price);
            tblBody.appendChild(row);
        }
    }

    tbl.setAttribute("border", "1");
    tbl.setAttribute("id", "table")

    tbl.appendChild(tblBody);
    courseTable.innerHTML = '';
    courseTable.appendChild(tbl);
}


async function get_api(endpoint){
    var messagediv = document.getElementById('messagediv')
    return fetch(endpoint)
        .then(function(response) {
            if(response.status === 200){
                messagediv.textContent = 'SUCCESS'
            }else{
                messagediv.textContent = 'ERROR'
            }
            return response.json();
        })
        .then(function(myJson) {
            return JSON.stringify(myJson)
        });
}

function post_api(endpoint, data){
    var messagediv = document.getElementById('messagediv');
    fetch(endpoint, {
        method: 'post',
        headers:{
            'Content-Type': 'application/json'
        }
    })
    .then(function (response) {
        if(response.status !== 201){
            messagediv.textContent = 'ERROR';
        }else{
            messagediv.textContent = 'SUCCESS';
        }
    })
    .catch(function (error) {
        messagediv.textContent = 'ERROR';
    });
}