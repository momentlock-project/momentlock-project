document.addEventListener("DOMContentLoaded", () => {
    const sendBtn = document.getElementById("sendCodeBtn");
    const verifyBtn = document.getElementById("verifyCodeBtn");

    sendBtn.addEventListener("click", async (e) => {
        e.preventDefault();
		const username = document.querySelector("input[name='username']").value;

        if (!username) {
            alert("이메일을 입력하세요.");
            return;
        }

        const response = await fetch("/momentlock/send-code", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded",
            },
            body: new URLSearchParams({ username })
        });

        const message = await response.text();
        alert(message);
    });

    verifyBtn.addEventListener("click", async (e) => {
        e.preventDefault();
		const username = document.querySelector("input[name='username']").value;
        const code = document.querySelector("input[name='code']").value;

        if (!username || !code) {
            alert("이메일과 인증번호를 모두 입력하세요.");
            return;
        }

        const response = await fetch("/momentlock/verify-code", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded",
            },
            body: new URLSearchParams({ username, code })
        });

		if (response.ok) {
            const message = await response.text();
            alert(message);

            //인증 성공하면 비밀번호 재설정 페이지로 이동
            window.location.href = `/momentlock/passwordreset?username=${encodeURIComponent(username)}`;
        } else {
            const errorMsg = await response.text();
            alert(errorMsg);
		        }
    });
});
