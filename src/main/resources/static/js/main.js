document.addEventListener("DOMContentLoaded", () => {
  const startBtn = document.getElementById("startCapsuleBtn");
  if (startBtn) {
    startBtn.addEventListener("click", () => {
      location.href = "/momentlock/startCapsule";
    });
  }
});