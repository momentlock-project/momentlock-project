// 비밀번호 input
const password = document.querySelector("input[name='password']");
const passwordconfirm = document.querySelector("input[name='passwordconfirm']");

// msg p태그
const passwordP = document.querySelector(".msg.password");
const passwordconfirmP = document.querySelector(".msg.passwordconfirm");

// username(이메일) 유효성 검사
validateInput(
	document.querySelector("input[name='username']"), 
	document.querySelector(".msg.username"),
	/^[^\s@]+@[^\s@]+\.[^\s@]+$/, 
	"이메일 형식에 맞게 입력해주세요."
);

// 비밀번호 유효성 검사
// 영대소문자, 숫자, 특수문자 포함 8-20자 
validateInput(
	password, 
	passwordP, 
	/^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[!@#$%^&*()_\-+=\[\]{};:'",.<>/?\\|`~])[A-Za-z\d!@#$%^&*()_\-+=\[\]{};:'",.<>/?\\|`~]{8,20}$/, 
	"영대소문자, 숫자, 특수기호 포함 8-20자를 입력하세요."
);

// 전화번호 유효성 검사
// 하이픈없이 숫자 11자
validateInput(
	document.querySelector("input[name='phonenumber']"), 
	document.querySelector(".msg.phonenumber"), 
	/^\d{11}$/, 
	"하이픈(-)없이 전화번호 11자리를 입력하세요."
);

// 비밀번호 일치 확인
passwordconfirm.addEventListener("input", () => {
	passwordconfirmP.innerHTML = "";
	if(password.value != passwordconfirm.value){
		passwordconfirmP.innerHTML = "비밀번호가 일치하지 않습니다.";
	}
})


const form = document.querySelector("#memberjoin");

form.addEventListener("submit", e => {
	e.preventDefault(); // 기본 submit 막기
	alert("회원가입이 완료되었습니다!");
	form.submit();
})




















