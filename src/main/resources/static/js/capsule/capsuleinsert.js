document.addEventListener('DOMContentLoaded', function() {
	const form = document.querySelector('form');
	const submitBtn = document.getElementById('sumbit-btn');

	if (submitBtn && form) {
		submitBtn.addEventListener('click', function() {
			// 입력 검증
			const title = document.querySelector('.input-title').value.trim();
			const content = document.querySelector('.input-content').value.trim();

			if (!title) {
				alert('제목을 입력해 주세요.');
				document.querySelector('.input-title').focus();
				return;
			}

			if (!content) {
				alert('내용을 입력해 주세요.');
				document.querySelector('.input-content').focus();
				return;
			}

			// 확인 대화상자
			const buttonText = this.textContent.trim();
			const message = buttonText === '등록'
				? '캡슐을 등록하시겠습니까?'
				: '캡슐을 수정하시겠습니까?';

			const confirmed = confirm(message);

			if (confirmed) {
				form.submit();
			}
		});
	}
});

// 파일명 표시
function showFileName(input) {
	const fileBox = document.getElementById('fileName');
	if (input.files && input.files.length > 0) {
		const fileNames = Array.from(input.files).map(file => file.name).join(', ');
		fileBox.textContent = `선택된 파일: ${fileNames}`;
	} else {
		fileBox.textContent = '첨부파일은 600MB 까지 가능합니다.';
	}
}

// 기존 파일만 삭제
function deleteFile(button) {
	const afid = button.dataset.afid;
	const boxId = document.getElementById('boxid').value;
	const capId = document.getElementById('capid').value;

	if (confirm('이 파일을 삭제하시겠습니까?')) {
		window.location.href = `/momentlock/afiledelete?boxid=${boxId}&capsuleid=${capId}&afid=${afid}`;
	}
}






