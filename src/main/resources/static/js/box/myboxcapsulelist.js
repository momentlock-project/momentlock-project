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

insertCapsule.addEventListener('click', () => {
	const confirmed = confirm('해당 박스에 캡슐을 추가하러 이동하시겠습니까?');

	if (confirmed) {
		window.location.href = `http://localhost:8888/momentlock/capsuleinsert?boxid=${boxId}`;
	}
});

boxJoinMemberBtn.addEventListener('click', () =>{
	boxJoinMemberModal.style.display = 'block';
});

memberCountConfirmBtn.addEventListener('click', ()=>{
	boxJoinMemberModal.style.display = 'none';
});
















