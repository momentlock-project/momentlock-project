document.addEventListener("DOMContentLoaded", () => {

	// URL 파라미터 확인 초대하기 보냈을때 성공/에러 메세지를 담아 리다이렉트 왔을때 처리
	const urlParams = new URLSearchParams(window.location.search);

	if (urlParams.has('error')) {
		const error = urlParams.get('error');
		if (error === 'boxmemcountfull') {
			alert('초대 받은 상자의 인원이 가득찼습니다.!');
		}
	}

	if (urlParams.has('success')) {
		const success = urlParams.get('success');
		if (success === 'invited') {
			alert('초대받은 상자에 가입되었습니다.!');
		}
	}

	const startBtn = document.getElementById("startCapsuleBtn");
	if (startBtn) {
		startBtn.addEventListener("click", () => {
			location.href = "/momentlock/startCapsule";
		});
	}


});