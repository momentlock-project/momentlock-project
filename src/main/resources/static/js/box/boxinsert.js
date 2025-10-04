// 날짜 입력 클릭 영역 확장
document.addEventListener('DOMContentLoaded', function() {
	const calendarIcon = document.getElementById('calendarIcon');
	const dateInput = document.getElementById('openDate');

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
		center: new kakao.maps.LatLng(37.5665, 126.9780), // 서울 중심
		level: 8
	};

	map = new kakao.maps.Map(container, options);
	map.setZoomable(true);

	// 지도 클릭 이벤트
	kakao.maps.event.addListener(map, 'click', function(mouseEvent) {
		const latlng = mouseEvent.latLng;
		addMarker(latlng);
		getAddressFromCoords(latlng);
	});
}

// 마커 추가 함수
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

// 장소 검색 함수
function searchPlace(keyword) {
	if (!keyword.trim()) {
		alert('검색어를 입력해주세요.');
		return;
	}

	const ps = new kakao.maps.services.Places();

	ps.keywordSearch(keyword, function(data, status) {
		if (status === kakao.maps.services.Status.OK) {
			// 첫 번째 검색 결과로 이동
			const place = data[0];
			const position = new kakao.maps.LatLng(place.y, place.x);

			// 지도 이동
			map.setCenter(position);
			map.setLevel(3); // 확대

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

	// 검색 버튼 클릭
	mapSearchBtn.addEventListener('click', function() {
		const keyword = mapSearchInput.value;
		searchPlace(keyword);
	});

	// 검색창 엔터키
	mapSearchInput.addEventListener('keypress', function(e) {
		if (e.key === 'Enter') {
			const keyword = mapSearchInput.value;
			searchPlace(keyword);
		}
	});

	// 모달 닫기
	closeMapBtn.addEventListener('click', function() {
		mapModal.classList.remove('active');
		resetModal();
	});

	// 모달 외부 클릭
	mapModal.addEventListener('click', function(e) {
		if (e.target === mapModal) {
			mapModal.classList.remove('active');
			resetModal();
		}
	});

	// 위치 선택 완료
	confirmBtn.addEventListener('click', function() {
		if (selectedAddress) {
			locationInput.value = selectedAddress;
			mapModal.classList.remove('active');
			resetModal();
		}
	});

	// 모달 초기화
	function resetModal() {
		selectedAddress = '';
		mapSearchInput.value = '';
		document.getElementById('selectedLocation').textContent = '지도를 클릭하여 위치를 선택하세요';
		document.getElementById('confirmLocationBtn').disabled = true;
		if (marker) {
			marker.setMap(null);
			marker = null;
		}
	}

});
