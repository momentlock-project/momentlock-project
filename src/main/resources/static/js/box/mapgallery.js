// 한국 주요 도시 데이터
const cityData = {
	'서울': { lat: 37.5665, lng: 126.9780, capsules: [] },
	'부산': { lat: 35.1796, lng: 129.0756, capsules: [] },
	'대구': { lat: 35.8714, lng: 128.6014, capsules: [] },
	'인천': { lat: 37.4563, lng: 126.7052, capsules: [] },
	'광주': { lat: 35.1595, lng: 126.8526, capsules: [] },
	'대전': { lat: 36.3504, lng: 127.3845, capsules: [] },
	'울산': { lat: 35.5384, lng: 129.3114, capsules: [] },
	'세종': { lat: 36.4800, lng: 127.2890, capsules: [] },
	'경기': { lat: 37.4138, lng: 127.5183, capsules: [] },
	'강원': { lat: 37.8228, lng: 128.1555, capsules: [] },
	'충북': { lat: 36.8000, lng: 127.7000, capsules: [] },
	'충남': { lat: 36.5184, lng: 126.8000, capsules: [] },
	'전북': { lat: 35.7175, lng: 127.1530, capsules: [] },
	'전남': { lat: 34.8679, lng: 126.9910, capsules: [] },
	'경북': { lat: 36.4919, lng: 128.8889, capsules: [] },
	'경남': { lat: 35.4606, lng: 128.2132, capsules: [] },
	'제주': { lat: 33.4996, lng: 126.5312, capsules: [] }
};

// 샘플 타임캡슐 데이터 생성 (실제로는 서버에서 가져와야 함)
function generateSampleCapsules() {
	const cities = Object.keys(cityData);
	cities.forEach(city => {
		const capsuleCount = Math.floor(Math.random() * 5) + 3; // 최소 3개로 변경
		for (let i = 0; i < capsuleCount; i++) {
			cityData[city].capsules.push({
				id: `${city}_${i}`,
				title: `${city} 타임캡슐 ${i + 1}`,
				date: `2024-${String(Math.floor(Math.random() * 12) + 1).padStart(2, '0')}-${String(Math.floor(Math.random() * 28) + 1).padStart(2, '0')}`,
				image: '/img/basic_box.png' // 타임캡슐 이미지 경로
			});
		}
	});
}

// 지도 초기화
let map;
let markers = [];
let selectedCity = null;

function initMap() {
	const mapContainer = document.getElementById('map');
	const mapOption = {
		center: new kakao.maps.LatLng(36.5, 127.5), // 한국 중심
		level: 13 // 남한 전체가 보이는 레벨
	};

	map = new kakao.maps.Map(mapContainer, mapOption);

	// 도시별 마커 생성
	Object.keys(cityData).forEach(cityName => {
		const city = cityData[cityName];
		const markerPosition = new kakao.maps.LatLng(city.lat, city.lng);

		const marker = new kakao.maps.Marker({
			position: markerPosition,
			title: cityName
		});

		marker.setMap(map);

		// 마커 클릭 이벤트
		kakao.maps.event.addListener(marker, 'click', function() {
			selectCity(cityName, marker);
		});

		markers.push({ name: cityName, marker: marker });
	});

	// 지도 범위 제한 (남한만 보이도록)
	const bounds = new kakao.maps.LatLngBounds(
		new kakao.maps.LatLng(33.0, 124.5), // 남서쪽
		new kakao.maps.LatLng(38.9, 131.9)  // 북동쪽
	);
	map.setBounds(bounds);
}

// 도시 선택 함수
function selectCity(cityName, marker) {
	selectedCity = cityName;

	// 지도 위에 선택된 도시명 표시
	const cityLabel = document.getElementById('selectedCityLabel');
	const cityNameText = document.getElementById('cityNameText');
	cityNameText.textContent = cityName;
	cityLabel.style.display = 'block';

	// 해당 도시의 타임캡슐 표시
	displayCapsules(cityName);

	// 지도 중심을 선택된 마커로 이동
	const city = cityData[cityName];
	const moveLatLon = new kakao.maps.LatLng(city.lat, city.lng);
	map.setCenter(moveLatLon);
	map.setLevel(10); // 줌인
}

// 타임캡슐 표시 함수
function displayCapsules(cityName) {
	const capsuleContainer = document.getElementById('capsuleContainer');
	capsuleContainer.innerHTML = ''; // 기존 내용 삭제

	const capsules = cityData[cityName].capsules;

	if (capsules.length === 0) {
		capsuleContainer.innerHTML = '<p style="text-align:center; color: #8B4513; font-size: 18px;">이 지역에는 타임캡슐이 없습니다.</p>';
		return;
	}

	capsules.forEach(capsule => {
		const capsuleItem = document.createElement('div');
		capsuleItem.className = 'capsule-item';
		capsuleItem.innerHTML = `
            <img src="${capsule.image}" alt="${capsule.title}" onerror="this.src='/img/basic_box.png'">
            <div class="capsule-info">
                <div>${capsule.title}</div>
                <div style="font-size: 12px; color: #666; margin-top: 5px;">${capsule.date}</div>
            </div>
        `;

		capsuleItem.addEventListener('click', () => {
			alert(`${capsule.title} 상세 정보`);
			// 여기서 타임캡슐 상세 페이지로 이동하거나 모달 표시
		});

		capsuleContainer.appendChild(capsuleItem);
	});
}

// 페이지 로드 시 실행
window.addEventListener('load', () => {
	generateSampleCapsules(); // 샘플 데이터 생성
	initMap(); // 지도 초기화
});