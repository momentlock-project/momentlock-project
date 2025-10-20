// 날짜 입력 클릭 영역 확장
document.addEventListener('DOMContentLoaded', function() {
	// 내일 날짜부터 선택 가능하도록 설정
	const dateInput = document.getElementById('openDate');
	const tomorrow = new Date();
	tomorrow.setDate(tomorrow.getDate() + 1);
	const tomorrowStr = tomorrow.toISOString().split('T')[0];
	dateInput.setAttribute('min', tomorrowStr);

	const calendarIcon = document.getElementById('calendarIcon');

	// 아이콘 클릭 시 date input 열기
	if (calendarIcon) {
		calendarIcon.addEventListener('click', function() {
			dateInput.showPicker();
		});
	}
});

// form-box enter키로 폼 제출되는 이벤트 막기
document.getElementById("form-box").addEventListener("keypress", function(event) {
	if (event.key === "Enter") {
		event.preventDefault();
	}
});

// 위치 input 값이 없을시 alert
const form = document.querySelector('.box-form');
const locationInput = document.getElementById('locationInput');

form.addEventListener('submit', function(e) {
	if (!locationInput.value) {
		e.preventDefault();
		alert('위치를 선택해주세요!');
		document.getElementById('openMapBtn').focus();
	}
});

let map;
let marker;
let selectedAddress = '';
let selectedLat = '';
let selectedLng = '';

// Kakao Maps API 로드 확인
function waitForKakao(callback) {
	if (window.kakao && window.kakao.maps) {
		callback();
	} else {
		setTimeout(() => waitForKakao(callback), 100);
	}
}

// Kakao Maps API 초기화
function initMap() {
	const container = document.getElementById('map');
	const options = {
		center: new kakao.maps.LatLng(37.5665, 126.9780),
		level: 8
	};

	map = new kakao.maps.Map(container, options);
	map.setZoomable(true);

	// 지도 클릭 이벤트
	kakao.maps.event.addListener(map, 'click', function(mouseEvent) {
		const latlng = mouseEvent.latLng;

		// 위도, 경도 저장
		selectedLat = latlng.getLat();
		selectedLng = latlng.getLng();

		addMarker(latlng);
		getAddressFromCoords(latlng);
	});
}

// 마커 추가
function addMarker(position) {
	if (marker) {
		marker.setMap(null);
	}

	marker = new kakao.maps.Marker({
		position: position,
		map: map
	});
}

// 좌표로 주소 검색
function getAddressFromCoords(latlng) {
	const geocoder = new kakao.maps.services.Geocoder();
	geocoder.coord2Address(latlng.getLng(), latlng.getLat(), function(result, status) {
		if (status === kakao.maps.services.Status.OK) {
			const address = result[0].address.address_name;
			selectedAddress = address;
			document.getElementById('selectedLocation').textContent = address;
			document.getElementById('confirmLocationBtn').disabled = false;
		}
	});
}

// 장소 검색
function searchPlace(keyword) {
	if (!keyword.trim()) {
		alert('검색어를 입력해주세요.');
		return;
	}

	const ps = new kakao.maps.services.Places();

	ps.keywordSearch(keyword, function(data, status) {
		if (status === kakao.maps.services.Status.OK) {
			const place = data[0];
			const position = new kakao.maps.LatLng(place.y, place.x);

			// 위도, 경도 저장
			selectedLat = place.y;
			selectedLng = place.x;

			// 지도 이동
			map.setCenter(position);
			map.setLevel(3);

			// 마커 추가
			addMarker(position);

			// 주소 설정
			selectedAddress = place.address_name || place.road_address_name;
			document.getElementById('selectedLocation').textContent = place.place_name + ' - ' + selectedAddress;
			document.getElementById('confirmLocationBtn').disabled = false;

		} else if (status === kakao.maps.services.Status.ZERO_RESULT) {
			alert('검색 결과가 없습니다.');
		} else {
			alert('검색 중 오류가 발생했습니다.');
		}
	});
}

// 모달 열기/닫기
document.addEventListener('DOMContentLoaded', function() {
	const openMapBtn = document.getElementById('openMapBtn');
	const closeMapBtn = document.getElementById('closeMapBtn');
	const mapModal = document.getElementById('mapModal');
	const confirmBtn = document.getElementById('confirmLocationBtn');
	const locationInput = document.getElementById('locationInput');
	const latitudeInput = document.getElementById('latitudeInput');
	const longitudeInput = document.getElementById('longitudeInput');
	const mapSearchBtn = document.getElementById('mapSearchBtn');
	const mapSearchInput = document.getElementById('mapSearchInput');

	// 모달 열기
	openMapBtn.addEventListener('click', function() {
		mapModal.classList.add('active');

		waitForKakao(() => {
			setTimeout(() => {
				if (!map) {
					initMap();
				} else {
					map.relayout();
				}
			}, 100);
		});
	});

	// 검색 버튼
	mapSearchBtn.addEventListener('click', function() {
		searchPlace(mapSearchInput.value);
	});

	// 엔터키 검색
	mapSearchInput.addEventListener('keypress', function(e) {
		if (e.key === 'Enter') {
			searchPlace(mapSearchInput.value);
		}
	});

	// 모달 닫기
	closeMapBtn.addEventListener('click', function() {
		mapModal.classList.remove('active');
		resetModal();
	});

	// 외부 클릭
	mapModal.addEventListener('click', function(e) {
		if (e.target === mapModal) {
			mapModal.classList.remove('active');
			resetModal();
		}
	});

	// 위치 선택 완료
	confirmBtn.addEventListener('click', function() {
		if (selectedAddress && selectedLat && selectedLng) {
			// 주소, 위도, 경도 값을 input에 저장
			locationInput.value = selectedAddress;
			latitudeInput.value = selectedLat;
			longitudeInput.value = selectedLng;

			console.log('선택된 위치:', {
				address: selectedAddress,
				latitude: selectedLat,
				longitude: selectedLng
			});

			mapModal.classList.remove('active');
			resetModal();
		}
	});

	// 모달 초기화
	function resetModal() {
		selectedAddress = '';
		selectedLat = '';
		selectedLng = '';
		mapSearchInput.value = '';
		document.getElementById('selectedLocation').textContent = '지도를 클릭하여 위치를 선택하세요';
		document.getElementById('confirmLocationBtn').disabled = true;
		if (marker) {
			marker.setMap(null);
			marker = null;
		}
	}

});

// 폼 제출 시 확인 창
document.getElementById('form-box').addEventListener('submit', function(e) {
	e.preventDefault(); // 기본 제출 동작 막기

	// 버튼 텍스트로 수정/추가 판단
	const submitBtn = this.querySelector('.submit-btn');
	const action = submitBtn.textContent.includes('수정') ? '수정' : '추가';

	if (confirm(`상자를 ${action}하시겠습니까?`)) {
		this.submit(); // 확인 누르면 폼 제출
	}
});