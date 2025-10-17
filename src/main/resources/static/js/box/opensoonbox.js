$(function() {
	$('.dday').each(function() {
		const opendate = $(this).data('target');
		renewOneSecond(opendate, $(this));
	});

});

// 백에서 보낸 오픈 예정일과 현재 날짜/시간의 차이를 기준으로
// 몇 일, 몇 시간, 몇 분, 몇 초 남았는지 1초마다 갱신하기 위한 함수 

// diff는 오픈 예정일까지 남은 전체적인 기간을 뜻합니다.
// diff를 24시간으로 나누면 몇 일 남았는지 나오고
// 24시간으로 나눈 것에 나머지가 있다면 60분으로 나눠서 몇 시간이 남았는지 구합니다.
// 60분으로 나눈 것에 나머지가 있다면 60초로 나눠서 몇 초가 남았는지
// => 총 얼마 만큼의 dd일, hh시간, mm분, ss초가 남았는지 구하고 html에 표시
function updatePeriod(targetLocalDate, element) {

	let today = new Date();
	let openday = new Date(targetLocalDate);
	const diff = openday - today; // 오픈 예정 날짜까지의 남은 기간

	const days = Math.floor(diff / (1000 * 60 * 60 * 24)); // 몇 일 남았는지
	const hours = Math.floor(diff % (1000 * 60 * 60 * 24) / (1000 * 60 * 60))// 몇 시간 남았는지
	const minutes = Math.floor(diff % (1000 * 60 * 60) / (1000 * 60)) // 몇 분 남았는지
	const second = Math.floor(diff % (1000 * 60) / 1000) // 몇 초 남았는지

	let restDays = '';
	if (days > 0) restDays += `${days}일`;
	if (hours>0 ) restDays += ` ${hours}시간`;
	if (minutes>0 ) restDays += ` ${minutes}분`;
	if (second > 0) restDays += ` ${second}초`;

	element.text(restDays);

}

function renewOneSecond(targetLocalDate, element) {
	updatePeriod(targetLocalDate, element) // 최초 실행
	setInterval(() => updatePeriod(targetLocalDate, element), 1000); // 1초마다 실행
}