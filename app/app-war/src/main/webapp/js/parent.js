var Parent = {

    // cache and compile template
    taskTemplate: _.template($("#taskRow").html()),
    errorTemplate: $("#error"),

    // REST end point
    endpoint: '/api/task',

    init: function () {

        // set event listeners
        $(document).on('click', '.updateState', function (e) {
            e.preventDefault();

            var data = {
                taskId: $(this).attr('data-id'),
                status: $(this).attr('data-state')
            };
            Parent.updateTask(data);
        });

        $(document).on('click', '.delete', function (e) {
            e.preventDefault();
            var taskId = $(this).attr('data-id');
            Parent.deleteTask(taskId);
        });

        $(document).on('click', '#populateTasks', function (e) {
            e.preventDefault();
            Parent.populateTasks();
        });

        $('#taskFormTg').click(function (e) {
            $('#newTaskFormPanel').toggle();
        });

        $('#newTaskForm').submit(function (e) {
            e.preventDefault();
            Parent.createTask($(this).serialize());
            $('#newTaskFormPanel').hide();
        });

    },

    loadTasks: function loadTasks() {
        $.getJSON(Parent.endpoint, function (tasks) {
            Parent.clearTaskContainer();

            for (var i in tasks) {
                var task = tasks[i];
                var taskDom = Parent.taskTemplate(task);
                $("#tasksCollection").append(taskDom);
            }
        });
    },

    clearTaskContainer: function () {
        $('#tasksCollection').html("");
    },

    deleteTask: function (id) {
        $.ajax({
            url: Parent.endpoint + "?taskId=" + id,
            type: 'DELETE',
            success: Parent.loadTasks,
            error: Parent.reqError
        });
    },

    updateTask: function (data) {
        $.ajax({
            url: Parent.endpoint,
            data: data,
            type: 'POST',
            success: Parent.loadTasks,
            error: Parent.reqError
        });
    },

    createTask: function (data) {
        $.ajax({
            url: Parent.endpoint,
            data: data,
            type: 'PUT',
            success: Parent.loadTasks,
            error: Parent.reqError
        });
    },

    populateTasks: function () {
        $.get('/api/populate', function () {
            Parent.loadTasks();
        });

    },

    reqError: function (xhr, status, error) {
        Parent.errorTemplate.children('span').html(xhr.responseText);
        Parent.errorTemplate.show();
    }

}