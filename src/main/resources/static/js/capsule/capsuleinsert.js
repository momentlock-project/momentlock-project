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