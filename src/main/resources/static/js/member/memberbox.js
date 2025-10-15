document.addEventListener("DOMContentLoaded", function() {
	
	// 날짜 형식 2025/10/13으로 변경
	document.querySelectorAll(".col.regdate").forEach(inquiry => {
		inquiry.textContent
			= inquiry.textContent.substring(
				0, inquiry.textContent.indexOf("T")).replaceAll("-", "/");
	})
	
	fetchToBoardDetailInfomodal('.row.inquiry', '/momentlock/memberinquirylist/');
	fetchToBoardDetailInfomodal('.row.notice', '/momentlock/membernoticelist/');
	
})

 
