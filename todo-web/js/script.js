import { loadPastTasks } from "./past-tasks.js";
import { loadTodayTasks } from "./today-tasks.js";

document.addEventListener("DOMContentLoaded", () => {
   const viewButtons = document.querySelectorAll("[data-view]");

   viewButtons.forEach((btn) => {
      btn.addEventListener("click", () => {
         // Reset all buttons
         viewButtons.forEach((b) => b.classList.remove("btn-primary"));
         viewButtons.forEach((b) => b.classList.add("btn-outline-secondary"));

         // Highlight selected button
         btn.classList.remove("btn-outline-secondary");
         btn.classList.add("btn-primary");

         // Show selected view
         const selectedView = btn.getAttribute("data-view");
         loadTasks(selectedView);
      });
   });
   loadTasks("today-tasks.html");
});



function refreshTasks() {
   const taskList = document.getElementById("taskList");
   exampleTasks.forEach((task) => {
      const taskItem = document.createElement("div");
      taskItem.className =
         "list-group-item d-flex justify-content-between align-items-start";
      taskItem.innerHTML = `
        <div>
          <h6 class="fw-semibold mb-1">${task.name}</h6>
          <p class="mb-1">${task.description}</p>
          <small class="text-muted">Due: ${task.dueDate} at ${
         task.dueTime
      }</small>
        </div>
        <div>
          <button class="btn btn-sm btn-link text-primary" onclick='openTaskModal(${JSON.stringify(
             task
          )})'>Edit</button>
        </div>
      `;
      taskList.appendChild(taskItem);
   });
}

function loadTasks(page) {
   fetch(page)
      .then((response) => response.text())
      .then((data) => {
         document.querySelector(".tasks-display-area").innerHTML = data;
         switch (page) {
            case "today-tasks.html":
               loadTodayTasks();
               break;
            case "past-tasks.html":
               loadPastTasks();
               break;
            case "future-tasks.html":
               break;
         }
      })
      .catch((error) => console.error("Error loading dashboard: ", error));
}
