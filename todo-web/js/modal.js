import { loadTodayTasks } from "./today-tasks";
import { loadPastTasks } from "./past-tasks";

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
const modalTitle = employeeModal.querySelector(".modal-title");
const addTaskBtn = document.getElementById("addTaskBtn");

modalSubmitButton.addEventListener("click", () => {
   const action = modalTitle.textContent.includes("Add") ? "add" : "update";
   submitTaskForm(action);
});

function submitTaskForm(action) {
   const form = document.getElementById("taskForm");
   const formData = new FormData(form);
   const taskData = Object.fromEntries(formData.entries());
   const requestBody = {
      name: taskData.name,
      email: taskData.description,
      dueDate: taskData.dueDate,
      dueTime: taskData.dueTime,
   };
   const url =
      action === "add"
         ? "http://localhost:8080/api/task/add"
         : `http://localhost:8080/api/task/update/${taskEditId}`;
   const method = action === "add" ? "POST" : "PUT";
   fetch(url, {
      method: method,
      headers: {
         "Content-Type": "application/json",
      },
      body: JSON.stringify(requestBody),
   })
      .then((response) => response.json())
      .then((data) => {
         console.log("Task saved:", data);
         document.querySelector(".btn-close").click(); // Close the modal
         // Refresh Tasks
         loadTodayTasks();
         loadPastTasks();
      })
      .catch((error) => console.error("Error saving task:", error));
}
