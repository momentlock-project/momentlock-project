document.addEventListener("DOMContentLoaded", function() {
	
	// 날짜 형식 2025/10/13으로 변경
	document.querySelectorAll(".col.regdate").forEach(inquiry => {
		inquiry.textContent
			= inquiry.textContent.substring(
				0, inquiry.textContent.indexOf("T")).replaceAll("-", "/");
	})
	
	const overlay = document.querySelector(".modal-overlay");
	const modal = document.querySelector(".box-modal");
	const closebtn = document.querySelector(".close-btn");
	
	// row 하나 클릭 시 상세 정보 보여주기
	document.querySelectorAll(".row").forEach(row => {
		
		try {
			row.addEventListener("click", async () => {
				const response = 
					await fetch("/momentlock/memberinquirylist/" 
							+ row.children[0].textContent, {
						method: "GET"
					})
					
				if(!response.ok){
					throw new Error("상세정보를 찾을 수 없습니다");
				}
				
				const data = await response.json();
				
				document.querySelector(".modal-title").innerHTML = data.inqtitle;
				document.querySelector(".modal-content").innerHTML = data.inqcontent;
				
				if(modal && overlay){
					// 모달, 오버레이 display block으로 변경
					modal.style.display = 'block';
					overlay.style.display = 'block';
				}
					
			})
		} catch(err){
			console.log("error", err);
		}
	})
	
	// 모달창이 열려있을 경우
	if(closebtn != null){
		// x버튼 클릭 시 모달창 닫기
		closebtn.addEventListener("click", () => {
			
			// 모달, 오버레이 display none으로 변경
			modal.style.display = "none";
			overlay.style.display = 'none';
		})
	}
	
	
})
 
