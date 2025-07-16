async function registerUser () {
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    const response = await fetch('/api/auth/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ username, password })
    });
    if (response.ok) {
        // Registration successful
        const registeredUser  = await response.json();
        console.log('User  registered:', registeredUser );
        alert('Registration successful!');
    } else if (response.status === 409) {
        // Username already exists
        alert('Username already exists. Please choose a different username.');
    } else {
        // Handle other errors
        alert('An error occurred during registration. Please try again.');
    }
}