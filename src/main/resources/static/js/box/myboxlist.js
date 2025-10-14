document.addEventListener("DOMContentLoaded", function() {
    const menuButtons = document.querySelectorAll(".menu-btn");

    menuButtons.forEach(btn => {
        btn.addEventListener("click", function(e) {
            e.stopPropagation(); // 클릭이 바깥으로 퍼지는 걸 방지
            const dropdown = btn.nextElementSibling;

            // 다른 열려있는 드롭다운 닫기
            document.querySelectorAll(".dropdown").forEach(d => {
                if(d !== dropdown) d.style.display = "none";
            }); 

            // 현재 버튼의 드롭다운 토글
            dropdown.style.display = (dropdown.style.display === "block") ? "none" : "block";
        });
    });

    // 바깥 클릭 시 모든 드롭다운 닫기
    document.addEventListener("click", function() {
        document.querySelectorAll(".dropdown").forEach(d => d.style.display = "none");
    });
});


// ✅ "상자 보내기" 클릭 시 폼 열기 (드롭다운의 3번째 a 태그 기준)
document.querySelectorAll(".dropdown a:nth-child(3)").forEach(sendBtn => {
    sendBtn.addEventListener("click", function(e) {
        e.preventDefault();
        const sendForm = document.getElementById("send-form");
        if (sendForm) sendForm.style.display = "flex";
    });
});

// ✅ 닫기 버튼
const sendCloseBtn = document.querySelector(".send-close");
if (sendCloseBtn) {
    sendCloseBtn.addEventListener("click", function() {
        const sendForm = document.getElementById("send-form");
        if (sendForm) sendForm.style.display = "none";
    });
}
