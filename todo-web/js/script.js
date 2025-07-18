import { loadPastTasks } from "./past-tasks.js";
import { loadTodayTasks } from "./today-tasks.js";
import { loadFutureTasks } from "./future-tasks.js";
import { logout } from "./logout.js";

document.addEventListener("DOMContentLoaded", () => {
   fetch("http://localhost:8080/api/auth/me", {
      credentials: "include",
   })
      .then((res) => {
         if (!res.ok) {
            window.location.href = "/login.html";
         }
         return res.json();
      })
      .then((data) => {
         document.querySelector("#loggedInName").textContent = data.name;
         document.querySelector("#loggedInEmail ").textContent = data.email;
      })
      .catch((err) => {
         console.error("Error fetching user info:", err);
         // Optionally redirect to login page
      });
});

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

document.addEventListener("DOMContentLoaded", () => {
   const logoutBtn = document.querySelector("#logoutBtn");
   logoutBtn.addEventListener("click", (e) => {
      e.preventDefault();
      logout();
   });
});

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
               loadFutureTasks();
               break;
         }
      })
      .catch((error) => console.error("Error loading dashboard: ", error));
}
