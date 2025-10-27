document.addEventListener("DOMContentLoaded", () => {
	// URL 파라미터 확인 초대하기 보냈을때 성공/에러 메세지를 담아 리다이렉트 왔을때 처리
	const urlParams = new URLSearchParams(window.location.search);

	if (urlParams.has('error')) {
		const error = urlParams.get('error');
		if (error === 'capcountzero') {
			alert('보유 캡슐 수가 부족합니다. 캡슐을 구매 후 이용해 주세요!');
		} else if (error === 'boxmemcountfull') {
			alert('초대 받은 상자의 인원이 가득찼습니다.!');
		}
	}

	if (urlParams.has('success')) {
		const success = urlParams.get('success');
		if (success === 'invited') {
			alert('초대받은 상자에 가입되었습니다.!');
		} else if (success === 'boxbury') {
			alert('상자가 성공적으로 묻혔습니다.! 오픈날을 기다려주세요');
		}
	}

});
