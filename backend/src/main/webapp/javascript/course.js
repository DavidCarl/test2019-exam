function refreshCourseList(coursesJSON){
    var courseList = document.getElementById('listOfCourses');
    var coursesJSON = JSON.parse(coursesJSON);

    for(var course in coursesJSON) {
        var li = document.createElement("li");
        li.appendChild(document.createTextNode(coursesJSON[course]['_name'] + ' - ' + coursesJSON[course]['_price']));
        li.addEventListener('click', showDetails);
        courseList.appendChild(li);
    }
}

function showDetails(event) {
    var clicked = event.target;
    var coursename = clicked.innerText.split(" - ")[0];
    apiCall("http://localhost:8080/2/api/principal/course/" + coursename, "GET", function (data) { insertData(data)});
}

function insertData(data) {
    var courseDetailsDiv = document.getElementById('courseDetails');

    //empty div
    var child = courseDetailsDiv.lastElementChild;
    while(child){
        courseDetailsDiv.removeChild(child);
        child = courseDetailsDiv.lastElementChild
    }
    addInformation(courseDetailsDiv, data);
    addEnrol(courseDetailsDiv, data);
}

function addEnrol(courseDetailsDiv, data) {
    var signupDiv = document.createElement('div');

    var tmpHeader = document.createElement('h4');
    tmpHeader.innerText = 'Enrol';
    signupDiv.appendChild(tmpHeader);


    var tmpParagraph = document.createElement('p');
    tmpParagraph.innerText = 'Email: ';
    signupDiv.appendChild(tmpParagraph);


    var tmpInput = document.createElement('input');
    tmpInput.id = 'enrolEmail';
    signupDiv.appendChild(tmpInput);
    
    var tmpButton = document.createElement('button');
    tmpButton.innerText = 'Signup';
    tmpButton.id = 'enrolBtn';
    tmpButton.addEventListener('click', enrol);
    signupDiv.appendChild(tmpButton);
    
    courseDetailsDiv.appendChild(signupDiv);
}

function enrol() {
    var email = document.getElementById('enrolEmail').value;
    var courseName = document.getElementById('courseName').innerText;

    apiCall('http://localhost:8080/2/api/student/enrol/'+email+'/'+courseName,'PUT', function (data) {})
}

function addInformation(courseDetailsDiv, data) {

    var tmpTeacherHeader = document.createElement('h4');
    tmpTeacherHeader.innerText = 'Teacher: ';
    courseDetailsDiv.appendChild(tmpTeacherHeader);

    var tmpTeacherParagraph = document.createElement('p');
    tmpTeacherParagraph.id = 'teacherName';
    tmpTeacherParagraph.innerText = data['teacher']['name'];
    courseDetailsDiv.appendChild(tmpTeacherParagraph);


    var tmpCourseHeader = document.createElement('h4');
    tmpCourseHeader.innerText = 'Course name: ';
    courseDetailsDiv.appendChild(tmpCourseHeader);

    var tmpCourseParagraph = document.createElement('p');
    tmpCourseParagraph.id = 'courseName';
    tmpCourseParagraph.innerText = data['name'];
    courseDetailsDiv.appendChild(tmpCourseParagraph);


    var tmpStudentHeader = document.createElement('h4');
    tmpStudentHeader.className = 'students';
    tmpStudentHeader.innerText = 'Students: ';
    courseDetailsDiv.appendChild(tmpStudentHeader);

    var ul = document.createElement('ul');
    Object.keys(data['students']).forEach(
        function (email) {
            var li = document.createElement('li');
            li.className = 'studentEmail';
            li.appendChild(document.createTextNode(email));
            ul.appendChild(li);
        });
    courseDetailsDiv.appendChild(ul);
}

function fill() {
    apiCall("http://localhost:8080/2/api/principal/register/addTopic/Arts", "POST", function (d) {});
    // register new teachers
    apiCall("http://localhost:8080/2/api/teacher/register/Lenard%20Lyndor/lylly@gmail.com/Teacher%20edu", "POST", function (d) {});
    apiCall("http://localhost:8080/2/api/teacher/register/Smith%20Stan/smt@gmail.com/Teacher%20edu", "POST", function (d) {});
    // register new courses
    apiCall("http://localhost:8080/2/api/principal/register/addCourse/Singing/Arts/342/lylly@gmail.com/300", "POST", function (d) {});
    apiCall("http://localhost:8080/2/api/principal/register/addCourse/Music/Arts/102/lylly@gmail.com/500", "POST", function (d) {});
    apiCall("http://localhost:8080/2/api/principal/register/addCourse/Drawing/Arts/204/lylly@gmail.com/200", "POST", function (d) {});
}

function apiCall(url, type, next) {
    fetch(url, {
        method: type,
        headers:{
            'Content-Type': 'application/json'
        }
    })
        .then(function (response) {
            return response.json();
        })
        .then(function (jsonObj) {
            next(jsonObj);
        })
        .catch(function (error) {
            console.log('Request failure: ', error);
        });
}

function get_api(endpoint){
    fetch(endpoint)
        .then(function(response) {
            return response.json();
        })
        .then(function(myJson) {
                refreshCourseList(JSON.stringify(myJson))
            });
}

function getNewestCourses() {
    var courseList = document.getElementById('listOfCourses');
    courseList.innerHTML = '';
    get_api('http://localhost:8080/2/api/principal/courses');
}

getNewestCourses();
