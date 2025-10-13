const subBtn = document.querySelector(".member-btn.sub");

if(subBtn != null){
	subBtn.addEventListener("click", async (e) => {
		e.preventDefault();
		
		if(window.confirm("구독을 취소하시겠습니까?")){
			await fetch("/momentlock/subdel", {
					method: "POST"
			})
			
			alert("구독이 취소되었습니다!");
			window.location = "/momentlock/memberinfo";
		}
	})
	
	// 2025-10-13T12:13:53에서 년도월일만 출력
	const enddate = document.querySelector("#enddate");
	enddate.textContent 
		= enddate.textContent.substring(0, enddate.textContent.indexOf("T"));
}

// 비밀번호 input
const password = document.querySelector("input[name='password']");
const passwordconfirm = document.querySelector("input[name='passwordconfirm']");

// msg p태그
const passwordP = document.querySelector(".msg.password");
const passwordconfirmP = document.querySelector(".msg.passwordconfirm");


// 전화번호 유효성 검사
// 하이픈없이 숫자 11자
validateInput(
	document.querySelector("input[name='phonenumber']"), 
	document.querySelector(".msg.phonenumber"), 
	/^\d{11}$/, 
	"하이픈(-)없이 전화번호 11자리를 입력하세요."
);

// 비밀번호 유효성 검사
// 영대소문자, 숫자, 특수문자 포함 8-20자 
validateInput(
	password, 
	passwordP, 
	/^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[!@#$%^&*()_\-+=\[\]{};:'",.<>/?\\|`~])[A-Za-z\d!@#$%^&*()_\-+=\[\]{};:'",.<>/?\\|`~]{8,20}$/, 
	"영대소문자, 숫자, 특수기호 포함 8-20자를 입력하세요."
);

// 비밀번호 일치 확인
passwordconfirm.addEventListener("input", () => {
	passwordconfirmP.innerHTML = "";
	if(password.value != passwordconfirm.value){
		passwordconfirmP.innerHTML = "비밀번호가 일치하지 않습니다.";
	}
})

document.querySelector(".member-btn.store").addEventListener("click", () => {
	window.location = "/momentlock/store";
})


