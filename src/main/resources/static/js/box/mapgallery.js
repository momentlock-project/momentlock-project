// 한국 주요 도시 위도,경도 데이터 const cityData
// 해당 city 이름이 포함된 상자를 capsules 배열안에 저장, 서버에서 받아온 데이터를 담는다.
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

// 서버에서 Box 데이터 가져오기
async function getBoxData() {
	try {
		const response = await fetch('/momentlock/api/boxdata');
		const data = await response.json();
		console.log('받아온 데이터:', data);

		const now = new Date(); // 현재 시간 기준

		data.forEach(box => {
			const location = extractLocationFromAddress(box.boxlocation);

			if (cityData[location]) {
				// boxopendate가 존재하고 현재 시각이 그보다 이후인지 검사
				const isOpened = box.boxopendate && new Date(box.boxopendate) <= now;

				cityData[location].capsules.push({
					id: box.boxid,
					title: box.boxname,
					date: box.boxopendate ? box.boxopendate.split('T')[0] : '',
					image: isOpened ? '/img/myboxlistopen.png' : '/img/myboxlist.png',
					location: box.boxlocation,
					isOpened: isOpened // 개봉 여부 추가
				});
			}
		});

		console.log('지역별로 분류된 cityData:', cityData);

	} catch (error) {
		console.error('캡슐 데이터 로드 실패:', error);
	}
}

// 주소에서 지역명 추출하는 함수
function extractLocationFromAddress(address) {
	if (!address) {
		return '서울'; // 기본값
	}

	// 지역 목록
	const regions = ['서울', '부산', '대구', '인천', '광주', '대전',
		'울산', '세종', '경기', '강원', '충북', '충남',
		'전북', '전남', '경북', '경남', '제주'];

	// 주소에서 지역명 찾기
	for (const region of regions) {
		if (address.includes(region)) {
			return region;
		}
	}

	// 특별시, 광역시 등의 단어가 있으면 제거하고 다시 검색
	const cleanAddress = address.replace(/(특별시|광역시|특별자치시|특별자치도|도|시|군|구)/g, '');

	for (const region of regions) {
		if (cleanAddress.includes(region)) {
			return region;
		}
	}

	return '서울'; // 매칭 안되면 기본값
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

	const box = cityData[cityName].capsules;

	if (box.length === 0) {
		capsuleContainer.innerHTML = '<p style="text-align:center; color: #8B4513; font-size: 18px;">이 지역에는 타임캡슐이 없습니다.</p>';
		return;
	}

	// 날짜 기준 오름차순 정렬 (빠른 날짜 순)
	box.sort((a, b) => new Date(a.date) - new Date(b.date));

	box.forEach(box => {
		const capsuleItem = document.createElement('div');
		capsuleItem.className = 'capsule-item';

		// 열리지 않은 캡슐은 locked 클래스 추가
		if (!box.isOpened) {
			capsuleItem.classList.add('locked');
		}

		// D-Day 계산
		let dateDisplay = box.date;
		if (!box.isOpened && box.date) {
			const openDate = new Date(box.date);
			const today = new Date();
			const dDay = Math.ceil((openDate - today) / (1000 * 60 * 60 * 24));
			dateDisplay += ` (D-${dDay})`;
		}

		capsuleItem.innerHTML = `
        <img src="${box.image}" alt="${box.title}" onerror="this.src='/img/basic_box.png'">
        <div class="capsule-info">
            <div>${box.title}</div>
            <div style="font-size: 17px; color: #666; margin-top: 5px;">${dateDisplay}</div>
            ${!box.isOpened ? '<div class="locked-badge">🔒 미개봉</div>' : '<div class="locked-badge" style="color: #5cb85c;">✅ 개봉됨</div>'}
        </div>
    `;

		// ⭐ 개봉된 캡슐만 클릭 이벤트 추가
		if (box.isOpened) {
			capsuleItem.addEventListener('click', () => {
				window.location.href = `/momentlock/opencapsulelist?boxid=${box.id}`;
			});
		} else {
			// ⭐ 미개봉 캡슐 클릭 시 안내 메시지
			capsuleItem.addEventListener('click', () => {
				alert(`이 타임캡슐은 ${box.date}에 개봉됩니다.`);
			});
		}

		capsuleContainer.appendChild(capsuleItem);
	});
}

// 페이지 로드 시 실행
window.addEventListener('load', async () => {
	await getBoxData(); // 상자 data 불러오기 (비동기 처리)
	initMap(); // 지도 초기화
});