// 유효성 검사 함수
function validateInput(inputElement, messageElement, regex, message){
	inputElement.addEventListener("input", e => {
	    messageElement.innerHTML = "";
	    if (!regex.test(e.target.value)) {
	      messageElement.innerHTML = message;
	    }
	 });
}