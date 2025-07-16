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
