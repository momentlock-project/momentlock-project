// '#pre' 요소의 부모 중 'page-item' 클래스를 가진 가장 가까운 요소 찾기
const prePageItem = document.querySelector('#pre').closest('.page-item');
const nextPageItem = document.querySelector('#next').closest('.page-item');
const pageItems = document.querySelectorAll('.page-item');

if (prePageItem && prePageItem.classList.contains('disabled')) {
	// 이전 페이지 버튼이 비활성화 상태면 숨김
	document.querySelector('#pre').style.display = 'none';
} else if (nextPageItem && nextPageItem.classList.contains('disabled')) {
	// 다음 페이지 버튼이 비활성화 상태면 숨김
	document.querySelector('#next').style.display = 'none';
} else {
	// 그 외엔 모든 page-item 보이기
	pageItems.forEach(item => {
		item.style.display = '';
	});
}