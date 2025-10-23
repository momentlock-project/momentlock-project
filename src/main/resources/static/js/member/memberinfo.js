const subBtn = document.querySelector(".member-btn.sub");

if(subBtn != null){
	subBtn.addEventListener("click", async (e) => {
		e.preventDefault();
		
		if(window.confirm("구독을 취소하시겠습니까?")){
			await fetch("/momentlock/subdel", {
					method: "POST"
			})
			
			alert("구독이 취소되었습니다!");
			window.location = "/momentlock/memberinfo";
		}
	})
	
	// 2025-10-13T12:13:53에서 년도월일만 출력
	const enddate = document.querySelector("#enddate");
	enddate.textContent 
		= enddate.textContent.substring(0, enddate.textContent.indexOf("T"));
}



document.querySelector(".member-btn.store").addEventListener("click", () => {
	window.location = "/momentlock/store";
})


