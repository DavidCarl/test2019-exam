function test(){
    getTopics();
    getTeachers();
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

async function getTopics(){
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

async function getTeachers(){
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