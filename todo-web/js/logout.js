export function logout() {
   fetch("http://localhost:8080/api/auth/logout", {
      method: "POST",
      credentials: "include",
   })
      .then((res) => {
         if (res.ok) {
            window.location.href = "/login.html";
         } else {
            alert("Logout failed.");
         }
      })
      .catch((err) => {
         console.error("Logout error:", err);
      });
}
