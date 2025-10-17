document.addEventListener("DOMContentLoaded", function() {
	
	// 날짜 형식 2025/10/13으로 변경
	document.querySelectorAll(".col.regdate").forEach(inquiry => {
		inquiry.textContent
			= inquiry.textContent.substring(0, inquiry.textContent.indexOf("T"))
				.replaceAll("-", "/");
	})
	
	// 상세정보 모달창 fetch 요청 함수
	fetchToBoardDetailInfomodal('.row.inquiry', '/momentlock/memberinquirylist/');
	fetchToBoardDetailInfomodal('.row.notice', '/momentlock/membernoticelist/');
	
})

// 상태 코드에 따른 프론트 complete 표시 변경
if(document.querySelector(".col.complete") != null) {
	document.querySelectorAll(".row").forEach(row => {
		let complete = row.children[3].textContent;
		
		if(complete == 'QNAN') {
			row.children[3].textContent = '답변 준비중';
		} else if(complete == 'QNAY'){
			row.children[3].textContent = '답변 완료';
		} else if(complete == 'DECN'){
			row.children[3].textContent = '처리 전';
		} else if(complete == 'DECY'){
			row.children[3].textContent = '처리 완료';
		}
		
	})
}

// notice, Q&A 카테고리 클릭 시 이동하는 함수
getNoticelistByType("#notice");
getNoticelistByType("#qna");

// 신고 제목 6자이상 넘어가면 ..으로 표시하는 함수
truncateString('.row.declar', 7);











