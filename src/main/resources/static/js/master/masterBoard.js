/*
	관리자 문의, 신고, 공지사항 모달창 js
*/

fetchToBoardDetailInfomodal('.row.inquiry', '/momentlock/master/masterinquirylist/');
fetchToBoardDetailInfomodal('.row.notice', '/momentlock/maseter/masternoticelist/');
fetchToBoardDetailInfomodal('.row.declar', '/momentlock/master/masterdeclarlist/');

/*
	문의사항 게시판 모달창 클릭 후 답변하기 js
*/
let inquiryBtn = document.querySelector(".member-btn.inquiry");
if(inquiryBtn != null){
	inquiryBtn.addEventListener("click", () => {
		inquiryBtn.style.display = 'none';
		document.querySelector("#inquiryForm").style.display = "block";
		document.querySelector("#inqid").value = document.querySelector(".modal-id").textContent;
	})
}
