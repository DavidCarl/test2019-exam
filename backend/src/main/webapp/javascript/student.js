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
        console.log('Request failure: ', error);
        messagediv.textContent = 'ERROR';
    });
}

function studentCourses(){
    var email = document.getElementById('emailField').value;
    get_api('http://localhost:8080/2/api/student/courses/' + email, function(data) {insertCourses(data)});
}

//TODO remove later
function testCouse() {
    var courses = ['1st','2nd', '3rd'];
    insertCourses(courses);
}

function insertCourses(data){
    var courses = document.getElementById('courses');

    // empty div for the new set of curses
    var child = courses.lastElementChild;
    while (child) {
        courses.removeChild(child);
        child = courses.lastElementChild;
    }

    for(var i in data){
        var tmp = document.createElement("p");
        tmp.innerText= data[i];
        courses.appendChild(tmp);
    }

}

function get_api(endpoint, next){
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
            console.log('Request failure: ', error);
            next('ERROR');
        });
}