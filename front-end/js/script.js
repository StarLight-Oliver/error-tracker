const realmNames = ["Server", "Client", "Shared"]

var openModal;


( (modal,closeBtn, modalBg, titleBg)=>{

	let clearModal = () => {
		modalBg.innerHTML = "";
	}

	closeBtn.addEventListener("click", function(e) {
		modal.classList.add("hidden");
		e.preventDefault();

	}, false)


	openModal = (title, func, ...data) => {
		clearModal();

		modal.classList.remove("hidden");
		
		titleBg.innerText = title;

		func(modalBg, ...data);
	}

} )(document.getElementById("modal"), document.getElementById("modalBtn"), document.getElementById("modalBg"), document.getElementById("modalTitle"));

( (tBtns) => {
	const args = {
		shortErr: "shortErr",
		stack: "stack",
		realm: "realm"
	}

	for( let btn of tBtns) {
		btn.addEventListener("click", function(ev) {
			ev.preventDefault();

			openModal("Add new Error", (bg, data) => {
				let form = document.createElement("form");
				form.method = "post";
				form.action = "/"
				let fieldset = document.createElement("fieldset");

				let legend = document.createElement("legend");
				legend.innerText = "Input the error details";
				fieldset.appendChild(legend);

				let shortErrL = document.createElement("label");
				shortErrL.for = args.shortErr;
				shortErrL.innerText = "Short Error";
				fieldset.appendChild(shortErrL);

				let shortErr = document.createElement("input")
				shortErr.type = "text"
				shortErr.id = args.shortErr
				shortErr.name = args.shortErr
				fieldset.appendChild(shortErr);

				fieldset.appendChild(document.createElement("br"));
				fieldset.appendChild(document.createElement("br"));

				let stackL = document.createElement("label");
				stackL.for = args.stack;
				stackL.innerHTML = "Error Stack";

				fieldset.append(stackL);

				let stack = document.createElement("textarea");
				stack.name = args.stack;
				stack.id = args.stack;
				stack.cols = 30
				stack.rows = "5"

				fieldset.append(stack);

				fieldset.appendChild(document.createElement("br"));
				fieldset.appendChild(document.createElement("br"));

				let realmL = document.createElement("label");
				realmL.for = args.realm;
				realmL.innerHTML = "What Realm did the error happen in"
				
				fieldset.append(realmL);

				let realm = document.createElement("select");
				realm.name = args.realm
				realm.id = args.realm
				realm.classList.add("bg-gray-600")

				for (let i in realmNames) {
					let option = document.createElement("option")
					option.value = i
					option.innerText = realmNames[i]
					realm.append(option)
				}

				fieldset.append(realm);

				fieldset.appendChild(document.createElement("br"));
				fieldset.appendChild(document.createElement("br"));

				let submit = document.createElement("input")
				form.appendChild(fieldset);
				
				submit.type = "submit"
				submit.value= "Submit"
				submit.classList.add("btn-blue")
				
				submit.addEventListener("click", function(ev) {
					console.log("the click worked");
					console.log(shortErr.value);
					console.log(this.parentElement);
					ev.preventDefault();
				
					let data = {
						shortErr: shortErr.value,
						stack: stack.value,
						realm: realm.value,
					}

					let a = async ()=> {
						let b = await axois.post("/api/error/create", data)
						console.log(b.data);
					}

					a()
				})


				form.appendChild(submit)
				
				bg.appendChild(form);
			})
		})
	}
} )(document.getElementsByClassName("add-error"))

