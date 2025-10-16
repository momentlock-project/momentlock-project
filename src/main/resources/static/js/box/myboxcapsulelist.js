// DOM 로드 완료 후 실행
document.addEventListener('DOMContentLoaded', function() {

	// 모든 dropdown-toggle 버튼 가져오기
	const dropdownToggles = document.querySelectorAll('.dropdown-toggle');

	// 각 버튼에 클릭 이벤트 추가
	dropdownToggles.forEach(toggle => {
		toggle.addEventListener('click', function(e) {
			e.stopPropagation(); // 이벤트 버블링 방지

			// 현재 버튼의 부모 dropdown 찾기
			const dropdown = this.closest('.dropdown');
			const menu = dropdown.querySelector('.dropdown-menu');

			// 다른 열려있는 메뉴 모두 닫기
			document.querySelectorAll('.dropdown-menu.show').forEach(openMenu => {
				if (openMenu !== menu) {
					openMenu.classList.remove('show');
				}
			});

			// 현재 메뉴 토글
			menu.classList.toggle('show');
		});
	});

	// 문서 전체 클릭 시 모든 드롭다운 닫기
	document.addEventListener('click', function() {
		document.querySelectorAll('.dropdown-menu.show').forEach(menu => {
			menu.classList.remove('show');
		});
	});

	// 드롭다운 메뉴 내부 클릭 시 닫히지 않도록
	document.querySelectorAll('.dropdown-menu').forEach(menu => {
		menu.addEventListener('click', function(e) {
			e.stopPropagation();
		});
	});
});

const insertCapsule = document.getElementById('insert-capsule')
const boxId = document.getElementById('boxId').value;
const boxJoinMemberBtn = document.getElementById('join-member-btn');
const boxJoinMemberModal = document.getElementById('memberCountModal');
const memberCountConfirmBtn = document.getElementById('memberCountConfirm');
const readyBtn = document.getElementById('ready-btn');
// 접속한 유저네임
const memberUsername = document.getElementById('memberusername').value;
const memerboxReadyCode = document.getElementById('memerboxReadyCode').value;
// 모든 kick-btn 버튼 선택
const kickBtns = document.querySelectorAll('.kick-btn');

// ===== 모달 요소들 =====
const inviteModal = document.getElementById('inviteModal');
const inviteBtn = document.getElementById('invite-btn');
const inviteCancel = document.getElementById('inviteCancel');
const inviteConfirm = document.getElementById('inviteConfirm');
const inviteInput = document.getElementById('inviteNickname');

insertCapsule.addEventListener('click', () => {
	const confirmed = confirm('해당 박스에 캡슐을 추가하러 이동하시겠습니까?');

	if (confirmed) {
		window.location.href = `http://localhost:8888/momentlock/capsuleinsert?boxid=${boxId}`;
	}
});

boxJoinMemberBtn.addEventListener('click', () => {
	boxJoinMemberModal.style.display = 'block';
});

memberCountConfirmBtn.addEventListener('click', () => {
	boxJoinMemberModal.style.display = 'none';
});

readyBtn.addEventListener('click', () => {
	let confirmed;
	if (memerboxReadyCode == 'MRN') {
		confirmed = confirm('상자 묻기 준비 완료 하시겠습니까?');
	} else {
		confirmed = confirm('상자 묻기 준비를 해제 하시겠습니까?');
	}

	if (confirmed) {
		window.location.href = `http://localhost:8888/momentlock/boxready?boxid=${boxId}&member=${memberUsername}`;
	}

});

// 각 버튼에 이벤트 리스너 추가
kickBtns.forEach(kickBtn => {
	kickBtn.addEventListener('click', function() {
		// data-username 값 가져오기
		const memberUsername = this.dataset.username;

		const confirmed = confirm(`${memberUsername}님을 상자에서 강퇴시키겠습니까?`);

		if (confirmed) {
			window.location.href = `/momentlock/kickmember?boxid=${boxId}&member=${memberUsername}`;
		}
	});
});

// 뒤로가기 버튼
document.getElementById('closeBtn').addEventListener('click', function(e) {
	e.preventDefault(); // 기본 동작 방지
	history.back(); // 이전 페이지로 이동
});


// ===== 초대하기 모달 열기 =====
inviteBtn.addEventListener('click', function() {
	inviteModal.style.display = 'flex';
	inviteInput.value = ''; // 입력창 초기화
	inviteInput.focus(); // 자동 포커스
});

// ===== 초대하기 모달 닫기 (취소) =====
inviteCancel.addEventListener('click', function() {
	inviteModal.style.display = 'none';
});

// ===== 초대하기 확인 =====
inviteConfirm.addEventListener('click', function() {
	const nickname = inviteInput.value.trim();
	if (!nickname) {
		alert('닉네임을 입력해주세요.');
		inviteInput.focus();
		return;
	}
	// 확인 대화상자
	const confirmed = confirm(`${nickname}님을 초대하시겠습니까?`);
	if (confirmed) {
		// 서버로 데이터 전송
		window.location.href = `/momentlock/invitemember?boxid=${boxId}&nickname=${encodeURIComponent(nickname)}
		&member=${memberUsername}`;
	}
});

// ===== Enter 키로 초대하기 =====
inviteInput.addEventListener('keypress', function(e) {
	if (e.key === 'Enter') {
		inviteConfirm.click();
	}
});

// ===== 모달 외부 클릭 시 닫기 =====
inviteModal.addEventListener('click', function(e) {
	if (e.target === inviteModal) {
		inviteModal.style.display = 'none';
	}
});



