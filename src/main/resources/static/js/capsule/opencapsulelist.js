document.getElementById('closeBtn').addEventListener('click', function(e) {
    e.preventDefault(); // 기본 동작 방지
    history.back(); // 이전 페이지로 이동
});

document.querySelectorAll('.opencapsulelist-capsule-item').forEach(item => {
    item.addEventListener('click', function() {
        const capid = this.dataset.capid;
		
		const confirmed = confirm('선택하신 캡슐의 상세 페이지로 이동하시겠습니까?')
		
		if(confirmed){
			window.location.href = `http://localhost:8888/momentlock/opencapsuledetail/${capid}`
		}
    });
});
