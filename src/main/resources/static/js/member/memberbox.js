// 날짜 형식 2025/10/13으로 변경
document.querySelectorAll(".col.regdate").forEach(inquiry => {
	inquiry.textContent
		= inquiry.textContent.substring(
			0, inquiry.textContent.indexOf("T")).replaceAll("-", "/");
})

// row 하나 클릭 시 상세 정보 보여주기