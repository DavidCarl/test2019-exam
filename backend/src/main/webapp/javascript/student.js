function studentsignup(){
    var emailField = document.getElementById('emailField').value;
    var firstNameField = document.getElementById('firstNameField').value;
    var lastNameField = document.getElementById('lastNameField').value;
    var birthdayField = document.getElementById('birthdayField').value;
    var messageDiv = document.getElementById('messagediv')
    if(emailField !== '' && firstNameField !== '' && lastNameField !== '' && birthdayField !== ''){
        birthArray = birthdayField.split('-');
        newBirthday = birthArray[2] + '-' + birthArray[1] + '-' + birthArray[0];
        var list = {'email': emailField, 'fname': firstNameField, 'lname': lastNameField, 'birthday': newBirthday};
        post_api('http://localhost:8080/2/api/student/register/' + firstNameField + '/' + lastNameField + '/' + newBirthday + '/' + emailField, list)
    }else{
        if(emailField === ''){
            messageDiv.textContent = 'MISSING email!'
        }else if(firstNameField === ''){
            messageDiv.textContent = 'MISSING first name!'
        }else if(lastNameField === ''){
            messageDiv.textContent = 'MISSING last name!'
        }else if(birthdayField === ''){
            messageDiv.textContent = 'MISSING birthday!'
        }
    }
}

function post_api(endpoint, data){
    var messagediv = document.getElementById('messagediv')
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

function studentCourses(){
    var email = document.getElementById('emailField').value;
    apiCall('http://localhost:8080/2/api/student/courses/' + email, function(data) {insertCourses(data)});
}


function insertCourses(data){
    var courses = document.getElementById('courses');
    // empty div for the new set of curses
    var child = courses.lastElementChild;
    while (child) {
        courses.removeChild(child);
        child = courses.lastElementChild;
    }
    var tableDiv = document.createElement('table');

    var tr = document.createElement('tr');
    var headers = ['Name', 'Room nr.', 'price']
    for(var i in headers){
        var th = document.createElement('th');
        th.innerText = headers[i];
        tr.appendChild(th);
    }
    tableDiv.appendChild(tr);

    for(var i in data){
        var tr = document.createElement('tr');
        tr.className = 'course';
        var headers = ['_name', '_roomNr', '_price']
        for(var j in headers){
            var td = document.createElement('td');
            td.className = headers[j];
            td.innerText = data[i][headers[j]];
            tr.appendChild(td);
        }
        tableDiv.appendChild(tr);
    }
    courses.appendChild(tableDiv);
}

function studentInfo() {
    var email = document.getElementById('emailField').value;
    apiCall('http://localhost:8080/2/api/student/info/' + email, function(data) {insertStudentInfo(data)});
}

function insertStudentInfo(data) {
    var studentInfo = document.getElementById('studentInfo');

    // empty div for the new set of curses
    var child = studentInfo.lastElementChild;
    while (child) {
        studentInfo.removeChild(child);
        child = studentInfo.lastElementChild;
    }

    if(data['_fName']){
        var tmp = document.createElement('p');
        tmp.id = 'fName';
        tmp.innerText= 'First name: ' + data['_fName'];
        studentInfo.appendChild(tmp);
    }
    if(data['_fName']){
        var tmp = document.createElement('p');
        tmp.id = 'lName';
        tmp.innerText= 'Last name: ' + data['_lName'];
        studentInfo.appendChild(tmp);
    }
    if(data['_fName']){
        var tmp = document.createElement('p');
        tmp.id = 'email';
        tmp.innerText= 'Email: ' + data['_email'];
        studentInfo.appendChild(tmp);
    }
    if(data['_fName']) {
        var tmp = document.createElement('p');
        tmp.id = 'birthday';
        tmp.innerText = 'Birthday: ' + data['_birthday']['day'] + '-' + data['_birthday']['month'] + '-' + data['_birthday']['year'];
        studentInfo.appendChild(tmp);
    }
    if(data['errorMessage']) {
        var tmp = document.createElement('p');
        tmp.id = 'error';
        tmp.innerText = data['errorMessage'];
        studentInfo.appendChild(tmp);
    }
}

function apiCall(endpoint, next){
    fetch(endpoint, {
        method: 'get',
        headers:{
            'Content-Type': 'application/json'
        }
    })
        .then(function (response) {
            return response.json();
        })
        .then(function (jsonResponse) {
            next(jsonResponse);
        })
        .catch(function (error) {
            next('ERROR');
        });
}
