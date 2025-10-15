document.addEventListener("DOMContentLoaded", () => {
  const capsuleList = document.querySelector(".capsule-list");

  // 캡슐 등록 완료 시 새 데이터 추가 (Ajax)
  function addCapsuleToList(capsule) {
    const item = document.createElement("div");
    item.classList.add("capsule-item");
    item.innerHTML = `
      <div class="capsule-info">
        <img src="/img/${capsule.capImage}" alt="캡슐 썸네일" />
        <div class="capsule-text">
			<h3 th:text="${capsule.username}"></h3>
        </div>
      </div>
      <div class="dropdown">
        <button class="dropdown-toggle">⋮</button>
        <div class="dropdown-menu">
          <a href="/momentlock/capsuleupdate?capid=${capsule.capid}">수정</a>
          <button class="delete-btn" data-capid="${capsule.capid}">삭제</button>
        </div>
      </div>
    `;
    capsuleList.appendChild(item);
  }

  // 폼에서 Ajax로 업로드
  const form = document.querySelector("#capsuleForm");
  if (form) {
    form.addEventListener("submit", async (e) => {
      e.preventDefault();
      const formData = new FormData(form);

      const res = await fetch("/momentlock/capsuleinsert-ajax", {
        method: "POST",
        body: formData
      });

      if (res.ok) {
        const capsule = await res.json();
        addCapsuleToList(capsule);
        alert("캡슐이 등록되었습니다!");
        form.reset();
      } else {
        alert("등록 실패 😢");
      }
    });
  }
});
