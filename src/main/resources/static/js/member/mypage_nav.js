const memberremoveBtn = document.querySelector(".main-btn.memberremove");

memberremoveBtn.addEventListener("click", async () => {
	if(window.confirm("정말 탈퇴하시겠습니까?")){
		await fetch("/momentlock/memberremove", {
			method: "POST"
		})
		
		alert("회원 탈퇴가 완료되었습니다.");
		window.location.href = `/logout`;
	} else {
		alert("취소되었습니다.")
	}
	
})