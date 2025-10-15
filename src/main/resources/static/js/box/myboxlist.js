let boxId = null;

document.addEventListener("DOMContentLoaded", function() {
	const menuButtons = document.querySelectorAll(".menu-btn");

	menuButtons.forEach(btn => {
		btn.addEventListener("click", function(e) {
			e.stopPropagation(); // 클릭이 바깥으로 퍼지는 걸 방지
			const dropdown = btn.nextElementSibling;

			// 다른 열려있는 드롭다운 닫기
			document.querySelectorAll(".dropdown").forEach(d => {
				if (d !== dropdown) d.style.display = "none";
			});

			// 현재 버튼의 드롭다운 토글
			dropdown.style.display = (dropdown.style.display === "block") ? "none" : "block";
		});
	});

	// 바깥 클릭 시 모든 드롭다운 닫기
	document.addEventListener("click", function() {
		document.querySelectorAll(".dropdown").forEach(d => d.style.display = "none");
	});
});


// ✅ "상자 보내기" 클릭 시 폼 열기 (드롭다운의 3번째 a 태그 기준)
document.querySelectorAll(".dropdown a:nth-child(3)").forEach(sendBtn => {
	sendBtn.addEventListener("click", function(e) {
		e.preventDefault();
		const sendForm = document.getElementById("send-form");
		if (sendForm) sendForm.style.display = "flex";

		const boxCard = e.target.closest(".box_card");
		
		// 상대에게 보낼 상자의 아이디
		boxId = boxCard.querySelector(".boxid").value;
		console.log("boxid= " +boxId);
		
		transmit(boxId);
		
	});
});

// 박스 보내기
function transmit(boxId) {
	console.log(boxId);
	
	const transmitBtn = document.querySelector(".send-submit");
	
	transmitBtn.addEventListener("click", async() => {

		const inputNickname = document.querySelector('.send-nickname').value;
		
		const url = 
			`/momentlock/boxTransmit?boxid=${boxId}&inputNickname=${inputNickname}`;
			console.log('요청한 url=> ' + url);
		
		const response = await fetch(url);
		console.log(response);
		const status = response.status;
		
		console.log("status=> "+status);
		if(status!=200) {
			console.log(status);
		} 
		
		const userExists = await response.text();
		console.log('유저 존재 여부=> '+ userExists);
		
		if(!userExists) {
			alert('해당 유저가 존재하지 않습니다.');
		} else {
			alert('상자를 보냈습니다!');
		}
		
		
	});
	
}

// ✅ 닫기 버튼
const sendCloseBtn = document.querySelector(".send-close");
if (sendCloseBtn) {
	sendCloseBtn.addEventListener("click", function() {
		const sendForm = document.getElementById("send-form");
		if (sendForm) sendForm.style.display = "none";
	});
}


// 상자 오픈 이미지
const boxCards = document.querySelectorAll(".box_card");

function updateBoxes() {
	const now = new Date();

	boxCards.forEach(card => {
		const dateElem = card.querySelector(".box-open-date");
		const imgElem = card.querySelector(".box");

		if (dateElem && imgElem) {
			const openDateStr = dateElem.dataset.opendate;
			if (!openDateStr) return;

			const openDate = new Date(openDateStr);
			const remainingMs = openDate - now;

			if (remainingMs <= 0) {
				imgElem.src = "/img/myboxlistopen.png"; // 열림 이미지
				dateElem.textContent = "열림";
			} else {
				imgElem.src = "/img/myboxlist.png"; // 닫힘 이미지

				// 남은 시간 계산
				const days = Math.floor(remainingMs / (1000 * 60 * 60 * 24));
				const hours = Math.floor((remainingMs / (1000 * 60 * 60)) % 24);
				const minutes = Math.floor((remainingMs / (1000 * 60)) % 60);
				const seconds = Math.floor((remainingMs / 1000) % 60);

				dateElem.textContent = `${days}일 ${hours}시 ${minutes}분 ${seconds}초`;
			}
		}
	});
}

setInterval(updateBoxes, 1000);
updateBoxes();

// 삭제 버튼 클릭 시 확인창
document.querySelectorAll(".dropdown a:nth-child(2)").forEach(deleteBtn => {
	deleteBtn.addEventListener("click", function(e) {
		e.preventDefault();

		// 상자 카드 요소 찾기
		const boxCard = deleteBtn.closest(".box_card");
		const boxNameElem = boxCard.querySelector("h2");
		const boxName = boxNameElem ? boxNameElem.textContent : "이 상자";

		const confirmDelete = confirm(`정말 "${boxName}" 상자를 삭제하시겠습니까?`);
		if (confirmDelete) {
			// 확인하면 원래 링크로 이동
			window.location.href = deleteBtn.href;
		}
	});
});






