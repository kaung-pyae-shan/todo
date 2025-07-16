document.addEventListener("DOMContentLoaded", () => {
   const loginForm = document.getElementById("loginForm");

   loginForm.addEventListener("submit", async (e) => {
      e.preventDefault();

      const email = document.getElementById("login-email").value.trim();
      const password = document.getElementById("login-password").value;

      const user = { email, password };
      try {
         const response = await fetch("http://localhost:8080/api/auth/login", {
            method: "POST",
            credentials: "include",
            headers: {
               "Content-Type": "application/json",
            },
            body: JSON.stringify(user),
         });

         if (response.status === 200) {
            const loggedInUser = await response.json();
            alert("Logged In successfully!");
            console.log("User:", loggedInUser);
            // Redirect or reset form
            loginForm.reset();
            window.location.href = "/index.html";
         } else if (response.status === 401) {
            alert("Invalid email or password.");
         } else {
            alert("Login failed. Please try again.");
         }
      } catch (error) {
         console.error("Error during registration:", error);
         alert("An unexpected error occurred.");
      }
   });
});
