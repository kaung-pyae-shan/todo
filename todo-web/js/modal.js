function openTaskModal(task = null) {
   const modal = new bootstrap.Modal(document.getElementById("taskModal"));
   document.getElementById("taskForm").reset();
   if (task) {
      document.getElementById("task-id").value = task.id;
      document.getElementById("task-name").value = task.name;
      document.getElementById("task-desc").value = task.description;
      document.getElementById("due-date").value = task.dueDate;
      document.getElementById("due-time").value = task.dueTime;
      document.getElementById("taskModalLabel").textContent = "Edit Task";
   } else {
      document.getElementById("taskModalLabel").textContent = "Add Task";
   }
   modal.show();
}
