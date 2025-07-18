import { openTaskModal } from "./modal.js";

export function loadFutureTasks() {
   const content = document.querySelector(".tasks-display-area");
   content.innerHTML = "";
   // fetch("tasks.json")
   fetch("http://localhost:8080/api/task/future", {
      method: "GET",
      credentials: "include",
   })
      .then((response) => response.json())
      .then((tasks) => {
         tasks.forEach((task) => {
            const row = `
            <div class="d-flex justify-content-between align-items-start border-bottom pb-3 mb-3 task-row" data-id="${task.id}">
               <div class="d-flex">
                  <div>
                     <h5 class="mb-1 fw-semibold">${task.name}</h5>
                     <p class="mb-1 text-muted">${task.description}</p>
                     <small class="text-muted">Due: ${task.dueDate} ${task.dueTime}</small>
                  </div>
               </div>
               <div>
                  <button class="btn btn-link text-primary btn-edit" data-id="${task.id}">Edit</button>
                  <button class="btn btn-link text-danger btn-delete" data-id="${task.id}">Delete</button>
               </div>
            </div>
            `;
            content.innerHTML += row;
         });
         setListenersForEditAndDelete();
      })
      .catch((error) => {
         console.log("Error Fetching Past Tasks: ", error);
      });
}

function setListenersForEditAndDelete() {
   document.querySelectorAll(".btn-edit").forEach((button) => {
      button.addEventListener("click", () => {
         const taskId = button.getAttribute("data-id");

         // Fetch that task and fill modal
         fetch(`http://localhost:8080/api/task/${taskId}`, {
            method: "GET",
            credentials: "include",
         })
            .then((res) => {
               if (!res.ok) throw new Error("Task not found");
               return res.json();
            })
            .then((task) => {
               openTaskModal(task);
            })
            .catch((err) => {
               console.error("Failed to load task", err);
               alert("Could not load task");
            });
      });
   });

   document.querySelectorAll(".btn-delete").forEach((button) => {
      button.addEventListener("click", () => {
         const taskId = button.getAttribute("data-id");

         // Confirm before deleting
         if (!confirm("Are you sure you want to delete this task?")) {
            return;
         }

         // Perform DELETE request
         fetch(`http://localhost:8080/api/task/delete/${taskId}`, {
            method: "DELETE",
            credentials: "include",
         })
            .then((res) => {
               if (res.status === 204) {
                  // Successfully deleted
                  loadFutureTasks(); // Reload task list
               } else {
                  alert("Failed to delete task");
               }
            })
            .catch((err) => {
               console.error("Delete error:", err);
               alert("Error deleting task");
            });
      });
   });
}