document.addEventListener("DOMContentLoaded", () => {
   const registerForm = document.getElementById("registerForm");

   registerForm.addEventListener("submit", async (e) => {
      e.preventDefault();

      const name = document.getElementById("register-name").value.trim();
      const email = document.getElementById("register-email").value.trim();
      const password = document.getElementById("register-password").value;

      const user = { name, email, password };

      try {
         const response = await fetch(
            "http://localhost:8080/api/auth/register",
            {
               method: "POST",
               headers: {
                  "Content-Type": "application/json",
               },
               body: JSON.stringify(user),
            }
         );

         if (response.status === 201) {
            const createdUser = await response.json();
            alert("Registered successfully!");
            console.log("User:", createdUser);
            // Redirect or reset form
            registerForm.reset();
            window.location.href = "/login.html";
         } else if (response.status === 409) {
            alert("User already exists with this email.");
         } else {
            alert("Registration failed. Please try again.");
         }
      } catch (error) {
         console.error("Error during registration:", error);
         alert("An unexpected error occurred.");
      }
   });
});

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

         if (response.status === 201) {
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

async function getCurrentUser() {
   try {
      const response = await fetch("/api/auth/me", {
         method: "GET",
         credentials: "include",
      });

      if (!response.ok) {
         const errorText = await response.text();
         console.warn("Not logged in:", errorText);
         return null;
      }

      const user = await response.json();
      console.log("Current user:", user);
      return user;
   } catch (error) {
      console.error("Failed to get current user:", error);
   }
}

async function logout() {
   try {
      const response = await fetch("/api/auth/logout", {
         method: "POST",
         credentials: "include",
      });

      const text = await response.text();
      alert(text);
   } catch (error) {
      console.error("Logout failed:", error);
   }
}
