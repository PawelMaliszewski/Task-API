function getList() {
    let div = document.getElementById('task_list');
    div.innerHTML = '';
    let ulList = document.createElement('ul');
    ulList.setAttribute("id", 'json-list')
    div.appendChild(ulList);
    let data;
    let taskList;
    $.ajax({
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        type: 'GET',
        url: 'http://localhost:8080/task',
        data: data,
        success: function (data) {
            console.log(data)

            $(data).each(function (i, value) {
                const list = document.createElement('li');
                ulList.appendChild(list).innerHTML = 'Id: ' + value.id + ', tytuł: ' + value.title;
                let button = document.createElement('button');
                button.innerHTML = 'Usuń'
                button.onclick = function removeTask() {
                    console.log(value.id)
                    $.ajax({
                        url: 'http://localhost:8080/task/' + value.id,
                        type: 'DELETE',
                        success: function () {
                            getListAfterDelete();
                        }
                    })
                }
                list.appendChild(button);
            })
        }
    })

}

function addTask() {
    let title = document.getElementById('title').value;
    let description = document.getElementById('description').value;
    let deadLine = document.getElementById('deadLine').value;
    let task = {title: title, description: description, deadLine: deadLine}
    task = JSON.stringify(task);
    $.ajax({
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        type: 'POST',
        url: 'http://localhost:8080/task',
        data: task,
        success: function (data) {
            console.log(data)
            getListAfterDelete();

        }
    })
}

function updateTask() {
    let id = document.getElementById('task-id').value;
    let title = document.getElementById('title').value;
    let description = document.getElementById('description').value;
    let deadLine = document.getElementById('deadLine').value;
    let task = {title: title, description: description, deadLine: deadLine}
    task = JSON.stringify(task);
    console.log(task)
    $.ajax({
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        type: 'PUT',
        url: 'http://localhost:8080/task/' + id,
        data: task,
        success: function (data) {
            console.log(data)
            getListAfterDelete();

        }
    })
}

function getListAfterDelete() {
    getList();
}

