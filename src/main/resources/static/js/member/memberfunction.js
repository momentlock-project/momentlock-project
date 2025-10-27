// 유효성 검사 함수
function validateInput(inputElement, messageElement, regex, message){
	inputElement.addEventListener("input", e => {
	    messageElement.innerHTML = "";
	    if (!regex.test(e.target.value)) {
	      messageElement.innerHTML = message;
	    }
	 });
}

// 게시판 상세 정보 모달창 함수
function fetchToBoardDetailInfomodal(classname, url){
	const overlay = document.querySelector(".modal-overlay");
	const modal = document.querySelector(".box-modal");
	const closebtn = document.querySelector(".close-btn");
	
	// row 하나 클릭 시 상세 정보 보여주기
	document.querySelectorAll(classname).forEach(row => {
		
		try {
			row.addEventListener("click", async () => {
				
				const response = 
					await fetch(url	+ row.children[0].textContent, {
						method: "GET",
						headers: {
							'Content-Type':'application/json'
						}
					})
					
				if(!response.ok){
					throw new Error("상세정보를 찾을 수 없습니다");
				}
				
				const data = await response.json();
				
				if(data.inqtitle != null){
					document.querySelector(".modal-title").innerHTML = data.inqtitle;
					document.querySelector(".modal-content").innerHTML = data.inqcontent;
					document.querySelector(".modal-id").innerHTML = data.inqid;
					document.querySelector(".modal-complete").innerHTML = data.inqcomplete;
					document.querySelector(".modal-answer").innerHTML = data.inqanswer;
				} else if(data.title != null){
					document.querySelector(".modal-title").innerHTML = data.title;
					document.querySelector(".modal-content").innerHTML = data.content;
				} else if(data.deccategory != null){
					document.querySelector(".modal-title").innerHTML = data.deccategory;
					document.querySelector(".modal-content").innerHTML = data.deccontent;
					document.querySelector(".modal-id").innerHTML = data.decid;
					document.querySelector(".modal-cap-title").innerHTML = data.capTitle;
					document.querySelector(".modal-cap-content").innerHTML = data.capContent;
				}
				
				// 관리자 문의사항 모달창에서 답변완료일 경우 답변하기 버튼 none으로 변경
				let inquiryBtn = document.querySelector(".member-btn.inquiry");
				if(inquiryBtn != null){
					if(data.inqcomplete == 'QNAY'){
						inquiryBtn.style.display = 'none';
					}
				}
				
				// 멤버 문의사항 모달창에서 문의답변이 있는 경우에만 아래 답변 나옴
				// 답변없으면 none으로 변경
				let answer = document.querySelector(".modal-answer");
				if(answer != null){
					if(answer.textContent != ''){
						answer.style.display = 'block';					
					} else{
						answer.style.display = 'none';
					}
				}
				
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
	
	// 모달창이 열려있을 경우 -> 닫기
	if(closebtn != null){
		// x버튼 클릭 시 모달창 닫기
		closebtn.addEventListener("click", () => {
			if(modal && overlay){
				// 모달, 오버레이, 답변하기 폼 display none으로 변경
				// 답변하기 버튼 display block으로 변경
				modal.style.display = "none";
				overlay.style.display = 'none';
				
				// 답변하기
				let inquiryBtn = document.querySelector(".member-btn.inquiry");
				if(inquiryBtn != null){
					document.querySelector(".member-btn.inquiry").style.display = 'block';
					document.querySelector("#inquiryForm").style.display = "none";
				}
			}
		})
	}
}


// 공지사항, QnA 페이지 카테고리로 이동하는 함수
function getNoticelistByType(name) {
	const type = document.querySelector(name);
	if(type != null){
		type.addEventListener("click", () => {
			try {
	
				window.location = `/momentlock/membernoticelist?type=`
					+ type.textContent;
				
			} catch(error){
				console.error(type.textContent + " type 호출 오류", error);
			}
		})
	}
}

// 나의 캡슐 리스트 문자열 10자 초과하면 ...으로 표시하는 함수
function truncateString(selectorName, maxLength){
	const capsulelist = document.querySelectorAll(selectorName);
	capsulelist.forEach(capsule => {
		for(let i=0; i<capsule.children.length; i++){
			if(capsule.children[i].children[1] != null){
				let title = capsule.children[i].children[1].textContent;
				if(title.length > maxLength && capsule.children[i].classList == 'capsule-title'){
					capsule.children[i].children[1].textContent 
						= title.slice(0, maxLength) + '...';
				} 
			} else if(capsule.children[1] != null){
				let title = capsule.children[1].textContent;
				if(title.length > maxLength && capsule.children[1].classList == 'col title'){
					capsule.children[1].textContent = title.slice(0, maxLength) + "..";
				}
			}
		}
	})
}



// 관리자 신고 게시판 상세모달창에서 신고처리하기 함수
function masterDeclarPlusCnt(){
	document.querySelector("#declarBtn").addEventListener("click", () => {
		window.location = `/momentlock/master/masterDeclarPlusCnt?decid=${document.querySelector(".modal-id").textContent}`;
	})
}	



