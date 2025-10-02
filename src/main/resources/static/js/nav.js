// 사이드바 관련 요소들
const hamburgerMenu = document.querySelector(".hamburger-menu");
const sidebar = document.getElementById("sidebar");
const sidebarOverlay = document.getElementById("sidebarOverlay");
const closeBtn = document.getElementById("sidebar-closeBtn");

// 사이드바 열기 함수
function openSidebar() {
  sidebar.classList.add("active");
  sidebarOverlay.classList.add("active");
  document.body.style.overflow = "hidden"; // 배경 스크롤 방지
}

// 사이드바 닫기 함수
function closeSidebar() {
  sidebar.classList.remove("active");
  sidebarOverlay.classList.remove("active");
  document.body.style.overflow = "auto"; // 배경 스크롤 복원
}

// 이벤트 리스너들
hamburgerMenu.addEventListener("click", openSidebar);
closeBtn.addEventListener("click", closeSidebar);
sidebarOverlay.addEventListener("click", closeSidebar);

// ESC 키로 사이드바 닫기
document.addEventListener("keydown", function (event) {
  if (event.key === "Escape") {
    closeSidebar();
  }
});

// 사이드바 메뉴 아이템 클릭 이벤트
const sidebarItems = document.querySelectorAll(".sidebar-item");
sidebarItems.forEach((item) => {
  item.addEventListener("click", function () {
    const itemText = this.textContent;
    console.log(`${itemText} 메뉴 클릭됨`);

    // 각 메뉴별 동작 추가 가능 (이 부분에 api 호출이나 페이지 이동 코드 작성)
    switch (itemText) {
      case "지도":
        window.location.href = "/momentlock/openboxmap";
        break;
      case "타임캡슐 갤러리":
        window.location.href = "/momentlock/popularbox";
        break;
      case "타임캡슐 상점":
        window.location.href = "/momentlock/store";
        break;
      case "마이페이지":
        window.location.href = "/momentlock/mypage";
        break;
	  case "문의게시판":
	    window.location.href = "/momentlock/memberinquirylist";
	    break;
    }

    // 메뉴 클릭 후 사이드바 닫기
    closeSidebar();
  });
});

// 햄버거 메뉴 애니메이션
hamburgerMenu.addEventListener("click", function () {
  this.classList.toggle("active");
});

// 사이드바가 닫힐 때 햄버거 메뉴 애니메이션 초기화
function resetHamburgerAnimation() {
  hamburgerMenu.classList.remove("active");
}

// 사이드바 닫기 함수 수정
function closeSidebar() {
  sidebar.classList.remove("active");
  sidebarOverlay.classList.remove("active");
  document.body.style.overflow = "auto";
  resetHamburgerAnimation();
}
