document.getElementById('closeBtn').addEventListener('click', function(e) {
	e.preventDefault(); // 기본 동작 방지
	history.back(); // 이전 페이지로 이동
});

document.querySelectorAll('.opencapsulelist-capsule-item').forEach(item => {
	item.addEventListener('click', function() {
		const capid = this.dataset.capid;

		window.location.href = `http://localhost:8888/momentlock/opencapsuledetail/${capid}`
	});
});
