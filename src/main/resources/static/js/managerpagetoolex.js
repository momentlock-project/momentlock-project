
const tabs = document.querySelectorAll('.tabs');

tabs.forEach(tab => {
	tab.addEventListener('click', () => {
		// 모든 탭 active 제거
		tabs.forEach(t => t.classList.remove('active'));
		// 클릭한 탭에 active 추가
		tab.classList.add('active');
	});
});


