import { openTaskModal } from "./modal.js";

export function loadTodayTasks() {
   const content = document.querySelector(".tasks-display-area");
   content.innerHTML = "";
   // fetch("tasks.json")
   fetch("http://localhost:8080/api/task/today", {
      method: "GET",
      credentials: "include",
   })
      .then((response) => {
         if (response.status === 204) {
               // If no tasks, show the empty state
               content.innerHTML = `
               <div class="d-flex align-items-center justify-content-center h-100">
                  <div class="text-center">
                     <img
                        src="https://storage.googleapis.com/workspace-0f70711f-8b4e-4d94-86f1-2a93ccde5887/image/650a9500-01ed-4501-b09b-1622b0c6568d.png"
                        alt="Illustration of an empty state with simple line drawing of a clipboard and pencil"
                        class="mx-auto d-block opacity-50"
                        style="height: 8rem; width: 8rem;"
                     >
                     <h3 class="mt-4 fs-5 fw-medium text-dark">There are no tasks yet!</h3>
                     <p class="mt-2 fs-6 text-muted">Add new to manage your daily taks.</p>
                  </div>
               </div>
            `;
               return; // Exit the function early
            }
            return response.json();
      })
      .then((tasks) => {
         tasks.forEach((task) => {
            const row = `
            <div class="d-flex justify-content-between align-items-start border-bottom pb-3 mb-3 task-row" data-id="${task.id}">
               <div class="d-flex">
                  <input class="form-check-input me-3 mt-1 task-status-checkbox" type="checkbox" data-id="${task.id}"  ${task.status === "COMPLETED" ? "checked" : ""}>
                  <div>
                     <h5 class="mb-1 fw-semibold ${task.status === "COMPLETED" ? "text-decoration-line-through text-muted" : ""}">${task.name}</h5>
                     <p class="mb-1 text-muted">${task.description}</p>
                     <small class="${task.status === "COMPLETED" ? "text-muted" : "text-primary"}">Due: ${task.dueDate} ${task.dueTime}</small>
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
         setListenerForCheckbox();
      })
      .catch((error) => {
         console.log("Error Fetching Today Tasks: ", error);
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
                  loadTodayTasks(); // Reload task list
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

function setListenerForCheckbox() {
   document.querySelectorAll(".task-status-checkbox").forEach((checkbox) => {
      checkbox.addEventListener("change", () => {
         const taskId = checkbox.getAttribute("data-id");
         const newStatus = checkbox.checked ? "COMPLETED" : "PENDING";

         fetch(`http://localhost:8080/api/task/status/${taskId}`, {
            method: "PUT",
            headers: {
               "Content-Type": "application/json",
            },
            credentials: "include",
            body: JSON.stringify({ status: newStatus }),
         })
            .then((res) => {
               if (!res.ok) throw new Error("Failed to update status");
               return res.json();
            })
            .then(() => {
               loadTodayTasks();
            })
            .catch((err) => {
               console.error("Status update failed:", err);
               alert("Could not update task status.");
            });
      });
   });
}