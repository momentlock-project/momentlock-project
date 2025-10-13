let isClicked = false; // 좋아요 버튼 누름 여부 전역 변수(기본값 false)


$(function() {
	const likeBtn = $('#likeBtn');
	const reportBtn = $('reportBtn');
	const capid = $('#capid').val();

	likeBtn.on('click', () => {
		likeActiveToggle(capid);
	})

	reportBtn.on('click', () => {
		if (confirm('신고 하시겠습니까?')) {
			
			alert('신고가 접수되었습니다.');
		} else {
			return;
		}
	})
})


async function likeActiveToggle(capid) {

	isClicked = !isClicked;
	console.log(isClicked);
	let currLikeCount = $('#likecount');

	if (isClicked) { // 좋아요를 눌렀을 때
		let response = await fetch(`/momentlock/capsulelikeaction?capid=${capid}&action=clicked`);
		let likeCount = await response.json();
		currLikeCount.text(likeCount); // html에 반영

	} else { // 좋아요 취소 (좋아요 누르고 다시 눌렀을 때)
		let response = await fetch(`/momentlock/capsulelikeaction?capid=${capid}&action=not_clicked`);
		let likeCount = await response.json();
		currLikeCount.text(likeCount);
	}

}

function movePage(list, indexMoveToPage) {

	console.log(list[indexMoveToPage]);
			
}

