function teachersignup(){
    var emailField = document.getElementById('emailField').value;
    var nameField = document.getElementById('nameField').value;
    var eduField = document.getElementById('eduField').value;
    if(emailField != '' && nameField != '' && eduField != ''){
        var list = {'email': emailField, 'name': nameField, 'edu': eduField};
        //get_api('http://localhost:8080/2/api/test/hello/test')
        post_api('http://localhost:8080/2/api/teacher/register/' + nameField + '/' + emailField + '/' + eduField, list)
    }else{
        alert('Please fill out all fields!')
    }
}

function get_api(endpoint){
    fetch(endpoint)
        .then(function(response) {
            console.log(response)
            return response.json();
        })
        .then(function(myJson) {
            //console.log(myJson)
            console.log(JSON.stringify(myJson));
        });
}

function post_api(endpoint, data){
    fetch(endpoint, {
        method: 'post',
        headers:{
            'Content-Type': 'application/json'
        }
    })
    .then(function (response) {
        console.log('Request success: ', response);
    })
    .catch(function (error) {
        console.log('Request failure: ', error);
    });
}