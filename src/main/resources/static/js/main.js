const loginModalButton = document.getElementById("modalLoginBtn");
    

async function logout() {
	
	try {
		
		const response = await fetch("/logout", {
			
			mehtod: 'POST',
			headders: {
				
				'Content-Type': 'application/JSON'
				
			}
			
		});
		
		if (response.ok) {
			
			window.location.href = '/html/main';
			
		}else {
			
			alert("logout failed");
			
		}
		
	}catch (error) {
		
		alert("error occured during logout");
		
	}
	
};


document.addEventListener("DOMContentLoaded", () => {
  const startBtn = document.getElementById("startCapsuleBtn");
  if (startBtn) {
    startBtn.addEventListener("click", () => {
      location.href = "/momentlock/startCapsule";
    });
  }
});

function gotoLogin() {
	
	window.location.href='html/login';
	
};