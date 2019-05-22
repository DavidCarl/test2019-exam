function mainPage(){
    getTeachers();
    getStudents();
    getTopics();
    getCourses();
}

function topicLoad(){
    getTopics();
}

function frontPage(){
    getCourses();
    getStudents();
    getTeachers();
}

async function getTeachers(){
    let teachers = await get_api('http://localhost:8080/2/api/principal/teachers/');
    buildTeacherTable(JSON.parse(teachers))
}

async function getStudents() {
    let students = await get_api('http://localhost:8080/2/api/principal/students/')
    buildStudentTable(JSON.parse(students))
}

async function getTopics(){
    let topics = await get_api('http://localhost:8080/2/api/principal/topics');
    buildTopicTable(JSON.parse(topics))
}

async function getCourses(){
    let courses = await get_api('http://localhost:8080/2/api/principal/courses');
    buildCourseTable(JSON.parse(courses))
}

function addTopic(){
    let topicField = document.getElementById('topicField').value;
    post_api('http://localhost:8080/2/api/principal/register/addTopic/' + topicField)
    getTopics();
}

function buildTeacherTable(data){
    var courseTable = document.getElementById('teacherTable');

    var tbl = document.createElement("table");
    var tblBody = document.createElement("tbody");
    var headerRow = document.createElement("tr");
    var headerName = document.createElement("th");
    var headerRoom = document.createElement("th");
    var headerPrice = document.createElement("th");

    headerName.textContent = 'Teacher name';
    headerRoom.textContent = 'Teacher email';
    headerPrice.textContent = 'Teacher education';

    headerRow.appendChild(headerName);
    headerRow.appendChild(headerRoom);
    headerRow.appendChild(headerPrice);

    tblBody.appendChild(headerRow);

    for (var i = 0; i < data.length; i++) {
        var row = document.createElement("tr");
        var name = document.createElement("td");
        var room = document.createElement("td");
        var price = document.createElement("td");
        name.textContent = data[i]['name'];
        room.textContent = data[i]['email'];
        price.textContent = data[i]['eduBackground'];
        row.appendChild(name);
        row.appendChild(room);
        row.appendChild(price);
        tblBody.appendChild(row);
    }

    tbl.setAttribute("border", "1");
    tbl.setAttribute("id", "teacherTable")

    tbl.appendChild(tblBody);
    courseTable.innerHTML = '';
    courseTable.appendChild(tbl);
}

function buildStudentTable(data){
    var courseTable = document.getElementById('studentTable');

    var tbl = document.createElement("table");
    var tblBody = document.createElement("tbody");
    var headerRow = document.createElement("tr");
    var headerName = document.createElement("th");
    var headerRoom = document.createElement("th");
    var headerPrice = document.createElement("th");

    headerName.textContent = 'Student name';
    headerRoom.textContent = 'Student email';
    headerPrice.textContent = 'Birthday';

    headerRow.appendChild(headerName);
    headerRow.appendChild(headerRoom);
    headerRow.appendChild(headerPrice);

    tblBody.appendChild(headerRow);

    for (var i = 0; i < data.length; i++) {
        var row = document.createElement("tr");
        var name = document.createElement("td");
        var room = document.createElement("td");
        var price = document.createElement("td");
        name.textContent = data[i]['_fName'] + ' ' + data[i]['_lName'];
        room.textContent = data[i]['_email'];
        price.textContent = data[i]['_birthday']['day'] + '-' + data[i]['_birthday']['month'] + '-' + data[i]['_birthday']['year'];
        row.appendChild(name);
        row.appendChild(room);
        row.appendChild(price);
        tblBody.appendChild(row);
    }

    tbl.setAttribute("border", "1");
    tbl.setAttribute("id", "studentTable")

    tbl.appendChild(tblBody);
    courseTable.innerHTML = '';
    courseTable.appendChild(tbl);
}

function buildTopicTable(data){
    var courseTable = document.getElementById('topicTable');

    var tbl = document.createElement("table");
    var tblBody = document.createElement("tbody");
    var headerRow = document.createElement("tr");
    var headerName = document.createElement("th");
    var headerPrice = document.createElement("th");

    headerName.textContent = 'Topic';
    headerPrice.textContent = 'Courses';

    headerRow.appendChild(headerName);
    headerRow.appendChild(headerPrice);

    tblBody.appendChild(headerRow);

    for (var i = 0; i < data.length; i++) {
        var row = document.createElement("tr");
        var name = document.createElement("td");
        var room = document.createElement("td");
        name.textContent = data[i]['_name'];
        let courses = "";
        let numberLoop = 0;
        for (var key in data[i]['courses']) {
            if(numberLoop !== 0){
                courses += ', '
            }
            courses += key;
            numberLoop++;
        }
        room.textContent = courses;
        row.appendChild(name);
        row.appendChild(room);
        tblBody.appendChild(row);
    }

    tbl.setAttribute("border", "1");
    tbl.setAttribute("id", "topicTable");

    tbl.appendChild(tblBody);
    courseTable.innerHTML = '';
    courseTable.appendChild(tbl);
}

function buildCourseTable(data){
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
    tbl.setAttribute("id", "courseTable");

    tbl.appendChild(tblBody);
    courseTable.innerHTML = '';
    courseTable.appendChild(tbl);
}


async function get_api(endpoint){
    var messagediv = document.getElementById('messageDiv')
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

function post_api(endpoint){
    var messagediv = document.getElementById('messageDiv')
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


function test(){
    getTopicsCourse();
    getTeachersCourse();
    getCourses();
}

function registerCourse(){
    let topicSelect = document.getElementById('topicField').value;
    let courseField = document.getElementById('courseField').value;
    let roomField = document.getElementById('roomField').value;
    let teacherSelect = document.getElementById('teacherField').value;
    let priceField = document.getElementById('priceField').value;
    post_api('http://localhost:8080/2/api/principal/register/addCourse/' + courseField + '/' + topicSelect + '/' + roomField + '/' + teacherSelect + '/' + priceField)
    getCourses()
}

async function getCourses(){
    let courses = await get_api('http://localhost:8080/2/api/principal/courses');
    buildCourseTable(JSON.parse(courses))
}

async function getTopicsCourse(){
    let topics = await get_api('http://localhost:8080/2/api/principal/topics');
    topics = JSON.parse(topics);
    let sel = document.getElementById('topicField');
    for (let i = 0; i < topics.length; i++) {
        let opt = document.createElement('option');
        opt.appendChild( document.createTextNode(topics[i]['_name']));
        opt.value = topics[i]['_name'];
        sel.appendChild(opt);
    }
}

async function getTeachersCourse(){
    let teachers = await get_api('http://localhost:8080/2/api/principal/teachers');
    teachers = JSON.parse(teachers);
    let sel = document.getElementById('teacherField');
    for (let i = 0; i < teachers.length; i++) {
        let opt = document.createElement('option');
        opt.appendChild( document.createTextNode(teachers[i]['name']));
        opt.value = teachers[i]['email'];
        sel.appendChild(opt);
    }
}

function buildCourseTable(data){
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
    tbl.setAttribute("id", "courseTable");

    tbl.appendChild(tblBody);
    courseTable.innerHTML = '';
    courseTable.appendChild(tbl);
}