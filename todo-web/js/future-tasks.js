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
               <div class="d-flex justify-content-between align-items-start border-bottom pb-3 mb-3">
                  <div class="d-flex">
                     <div>
                        <p class="d-none">${task.id}</p>
                        <h5 class="mb-1 fw-semibold">${task.name}</h5>
                        <p class="mb-1 text-muted">${task.description}</p>
                        <small class="text-muted">Due: ${task.dueDate} ${task.dueTime}</small>
                     </div>
                  </div>
                  <div>
                     <button class="btn btn-link text-primary">Edit</button>
                     <button class="btn btn-link text-danger">Delete</button>
                  </div>
               </div>
               `;
            content.innerHTML += row;
         });
      })
      .catch((error) => {
         console.log("Error Fetching Past Tasks: ", error);
      });
}
