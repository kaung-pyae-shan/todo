document.addEventListener("DOMContentLoaded", () => {
   const modalBtn = document.getElementById("modalBtn");
   modalBtn.addEventListener("click", () => openTaskModal(null));
});

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

const taskModal = document.getElementById("taskModal");
const modalTitle = taskModal.querySelector(".modal-title");
const addTaskBtn = document.getElementById("addTaskBtn");

addTaskBtn.addEventListener("click", () => {
   const action = modalTitle.textContent.includes("Add") ? "add" : "update";
   console.log(action);
   submitTaskForm(action);
});

function submitTaskForm(action) {
   const form = document.getElementById("taskForm");
   const formData = new FormData(form);
   const taskData = Object.fromEntries(formData.entries());
   const requestBody = {
      name: taskData.name,
      description: taskData.description,
      dueDate: taskData.dueDate,
      dueTime: taskData.dueTime,
      status: "PENDING",
   };
   const url =
      action === "add"
         ? "http://localhost:8080/api/task/add"
         : `http://localhost:8080/api/task/update/${taskEditId}`;
   const method = action === "add" ? "POST" : "PUT";
   console.log(url, method);
   fetch(url, {
      method: method,
      credentials: "include",
      headers: {
         "Content-Type": "application/json",
      },
      body: JSON.stringify(requestBody),
   })
      .then((response) => response.json())
      .then((data) => {
         console.log("Task saved:", data);
         document.querySelector("#modalCloseBtn").click(); // Close the modal
         // Refresh Tasks
         document.querySelector('button[data-view="today-tasks.html"]').click();
      })
      .catch((error) => console.error("Error saving task:", error));
}
