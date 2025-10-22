// DOM 로드 완료 후 실행
document.addEventListener('DOMContentLoaded', function() {

	// URL 파라미터 확인 초대하기 보냈을때 성공/에러 메세지를 담아 리다이렉트 왔을때 처리
	const urlParams = new URLSearchParams(window.location.search);

	if (urlParams.has('error')) {
		const error = urlParams.get('error');
		let message = '';

		switch (error) {
			case 'nomember':
				message = '존재하지 않는 회원입니다.';
				break;
			case 'self':
				message = '자기 자신을 초대할 수 없습니다.';
				break;
			case 'failed':
				message = '초대에 실패했습니다.';
				break;
			case 'capsulezero':
				message = '상자에 캡슐을 1개 이상은 추가하고 묻어야 합니다.';
				break;
		}

		if (message) {
			alert(message);
		}
	}

	if (urlParams.has('success')) {
		const success = urlParams.get('success');
		if (success === 'invited') {
			alert('초대를 성공적으로 보냈습니다!');
		}
	}

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
// boxid 가져오기
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

// 오버 레이
const inviteOverlay = document.getElementById('inviteOverlay');
const memberCountOverlay = document.getElementById('memberCountOverlay');

insertCapsule.addEventListener('click', () => {

	window.location.href = `/momentlock/capsuleinsert?boxid=${boxId}`;
});

boxJoinMemberBtn.addEventListener('click', () => {
	boxJoinMemberModal.style.display = 'block';
	memberCountOverlay.style.display = 'flex';
});

memberCountConfirmBtn.addEventListener('click', () => {
	boxJoinMemberModal.style.display = 'none';
	memberCountOverlay.style.display = 'none';
});

// 기존 readyBtn 이벤트 리스너를 다음과 같이 수정

const buryAnimationOverlay = document.getElementById('buryAnimationOverlay');

readyBtn.addEventListener('click', () => {
	let confirmed;
	let isReady = false;

	if (memerboxReadyCode == 'MRN') {
		confirmed = confirm('상자 묻기 준비 완료 하시겠습니까?');
		isReady = true;
	} else {
		confirmed = confirm('상자 묻기 준비를 해제 하시겠습니까?');
		isReady = false;
	}

	if (confirmed) {
		// 준비 완료일 때만 애니메이션 표시
		if (isReady) {
			// 애니메이션 표시
			buryAnimationOverlay.classList.add('show');

			// 4초 후 페이지 이동
			setTimeout(() => {
				window.location.href = `/momentlock/boxready?boxid=${boxId}`;
			}, 4500);
		} else {
			// 준비 해제는 바로 이동
			window.location.href = `/momentlock/boxready?boxid=${boxId}`;
		}
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


// 상자를 만든 회원이 아닌 경우
// 초대하기 버튼이 없음
if (inviteBtn != null) {
	// ===== 초대하기 모달 열기 =====
	inviteBtn.addEventListener('click', function() {
		inviteModal.style.display = 'flex';
		inviteOverlay.style.display = 'flex';
		inviteInput.value = ''; // 입력창 초기화
		inviteInput.focus(); // 자동 포커스
	});
}

// ===== 초대하기 모달 닫기 (취소) =====
inviteCancel.addEventListener('click', function() {
	inviteOverlay.style.display = 'none';
	inviteModal.style.display = 'none';
});

// ===== 초대하기 확인 =====
inviteConfirm?.addEventListener('click', function() {
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
inviteInput?.addEventListener('keypress', function(e) {
	if (e.key === 'Enter') {
		inviteConfirm.click();
	}
});

// ===== 모달 외부 클릭 시 닫기 =====
inviteModal.addEventListener('click', function(e) {
	if (e.target === inviteModal) {
		inviteModal.style.display = 'none';
		inviteOverlay.style.display = 'none';
	}
});

// 개인 캡슐 수정, 삭제 하기
const modifyBtns = document.querySelectorAll('.modify-btn');
const deleteBtns = document.querySelectorAll('.delete-btn');

// 수정 버튼 선택
modifyBtns.forEach(modifyBtn => {
	modifyBtn.addEventListener('click', function() {
		// data-capid 값 가져오기
		const capsuleId = this.dataset.capid;

		window.location.href = `/momentlock/capsuleinsert?boxid=${boxId}&capsuleid=${capsuleId}`;
	});
});

deleteBtns.forEach(deleteBtn => {
	deleteBtn.addEventListener('click', function() {

		// data-capid 값 가져오기
		const capsuleId = this.dataset.capid;

		const confirmed = confirm("해당 캡슐을 삭제 하시겠습니까? 삭제된 데이터는 복구할 수 없습니다.");

		if (confirmed) {
			window.location.href = `/momentlock/capsuledelete?boxid=${boxId}&capsuleid=${capsuleId}`;
		}
	});
});
