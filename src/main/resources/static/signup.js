document.getElementById('signup-form').addEventListener('submit', function(event) {
    event.preventDefault();

    var userData = {
        username: document.getElementById('username').value,
        email: document.getElementById('email').value,
        password: document.getElementById('password').value
    };

    fetch('http://localhost:8080/api/users/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(userData)
    })
        .then(response => response.json())
        .then(data => {
            console.log('Success:', data);
            if (data.userId) {
                sessionStorage.setItem('userId', data.userId);
            }

            // Redirect to dashboard
            window.location.href = '/dashboard.html';
        })
        .catch((error) => {
            console.error('Error:', error);
            // TODO: Send an error message to the user
        });
});