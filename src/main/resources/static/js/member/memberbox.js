// 날짜 형식 2025/10/13으로 변경
document.querySelectorAll(".col.regdate").forEach(inquiry => {
	inquiry.textContent
		= inquiry.textContent.substring(
			0, inquiry.textContent.indexOf("T")).replaceAll("-", "/");
})

const overlay = document.querySelector(".modal-overlay");
const modal = document.querySelector(".box-modal");

// row 하나 클릭 시 상세 정보 보여주기
document.querySelectorAll(".row").forEach(row => {
	row.addEventListener("click", () => {
		modal.style.display = 'block';
		overlay.style.display = 'block';
	})
})



document.querySelector(".close-btn").addEventListener("click", () => {
	modal.style.display = "none";
	overlay.style.display = 'none';
})
