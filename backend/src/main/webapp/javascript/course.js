function refreshCourseList(coursesJSON){
    var courseList = document.getElementById('listOfCourses');
    var coursesJSON = JSON.parse(coursesJSON);

    for(var course in coursesJSON) {
        var li = document.createElement("li");
        li.appendChild(document.createTextNode(coursesJSON[course]['_name'] + ' - ' + coursesJSON[course]['_price']));
        courseList.appendChild(li);
    }
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