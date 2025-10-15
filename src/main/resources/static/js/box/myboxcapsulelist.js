// MemberCount 모달 관련 요소
const memberCountModal = document.getElementById("memberCountModal");
const memberCountBtn = document.querySelector(".memberCount .box-btn");
const memberCountConfirm = document.getElementById("memberCountConfirm"); // 확인 버튼

// MemberCount 버튼 클릭 → 모달 열기
memberCountBtn.addEventListener("click", () => {
  memberCountModal.style.display = "block";
});

// 확인 버튼 클릭 → 모달 닫기
memberCountConfirm.addEventListener("click", () => {
  memberCountModal.style.display = "none";
});

/*
// 모달 바깥 클릭 시 닫기 (원하면 다시 활성화)
window.addEventListener("click", (event) => {
  if (event.target === memberCountModal) {
    memberCountModal.style.display = "none";
  }
});
*/

// 준비하기 버튼
const readyBtn = document.querySelector(".footer-btn button");

// 모든 멤버의 준비 상태 이미지
const readyStatusImgs = document.querySelectorAll(".ready-status");

// 준비하기 버튼 클릭 시 상태 전환
readyBtn.addEventListener("click", () => {
  readyStatusImgs.forEach(img => {
    if (img.dataset.ready === "true") {
      // 준비 해제 → 기본 상태 이미지
      img.src = "/img/no_ready.png";
      img.dataset.ready = "false";
    } else {
      // 준비 완료 상태 이미지
      img.src = "/img/ready.png";
      img.dataset.ready = "true";
    }
  });
});


document.addEventListener("DOMContentLoaded", () => {
  const addCapsuleBtn = document.querySelector(".btn-addCapsule");

  if (addCapsuleBtn) {
    // boxId는 타임리프에서 주입받음 (예: <input type="hidden" id="boxId" th:value="${box.boxid}" />)
    const boxIdInput = document.getElementById("boxId");
    const boxId = boxIdInput ? boxIdInput.value : null;

    addCapsuleBtn.addEventListener("click", () => {
      if (!boxId) {
        alert("boxId를 불러올 수 없습니다.");
        return;
      }
      location.href = `/momentlock/capsuleinsert?boxid=${boxId}`;
    });
  }
});

fetch(`/momentlock/capsuledelete?capid=${capid}`, {
  method: "DELETE"
})
  .then(res => res.text())
  .then(result => {
    if (result === "success") {
      alert("캡슐이 삭제되었습니다.");
      btn.closest(".capsule-item").remove();
    } else {
      alert("삭제 중 오류가 발생했습니다.");
    }
  })
  .catch(err => console.error("삭제 요청 실패:", err));
