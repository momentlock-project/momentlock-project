const memberremoveBtn = document.querySelector(".main-btn.memberremove");

memberremoveBtn.addEventListener("click", async () => {
	
	try{
		
		if(window.confirm("정말 탈퇴하시겠습니까?")){
			await fetch("/momentlock/memberremove", {
				method: "POST"
			})
			
			alert("회원 탈퇴가 완료되었습니다.");
			// 회원탈퇴하면서 로그아웃도 같이 진행
			window.location.href = `/logout`;
			
		} else {
			alert("취소되었습니다.");
		}
		
	} catch(error) {
		console.log("회원탈퇴 중 error 발생", error);
	}
	
})