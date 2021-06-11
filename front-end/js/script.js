

( (modal,closeBtn, modalBg)=>{
	closeBtn.addEventListener("click", function(e) {
		modal.classList.add("hidden")
		e.suppresDefault();

	}, false)

} )(document.getElementById("modal"), document.getElementById("modalBtn"), document.getElementById("modelBg"))
