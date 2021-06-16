const realmNames = ["Server", "Client", "Shared"]

var openModal;
var makeCard;

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
		realm: "realm",
		name: "error-name"
	}

	for( let index = 0; index<tBtns.length; index ++) {
		const btn = tBtns[index]
		console.log(btn, btn.addEventListener, index)


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

				let nameL = document.createElement("label");
				nameL.for = args.name
				nameL.innerText = "Name of Error"
				fieldset.appendChild(nameL);

				let name = document.createElement("input")
				name.type = "text"
				name.id = args.name
				name.name = args.name
				fieldset.appendChild(name);

				fieldset.appendChild(document.createElement("br"));
				fieldset.appendChild(document.createElement("br"));

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
						hash : CRC32.bstr(shortErr.value + stack.value  + realm.value),
						shortErr: shortErr.value,
						stack: stack.value,
						realm: realm.value,
						state: index,
						name: name.value
					}

					let a = async ()=> {
						//let b = await axios.post("/api/error/create", data)
						//console.log(b.data); // if we got the data back
						makeCard(data);
					}

					a();
				})


				form.appendChild(submit)
				
				bg.appendChild(form);
			})
		})
	}
} )(document.getElementsByClassName("add-error"));

( (todoDiv, workingDiv, fixedDiv) => {

	let realmColors = ["bg-blue-600", "bg-yellow-600", "bg-green-600"]
	let divs = [todoDiv, workingDiv, fixedDiv]

	makeCard = (data)=> {
		console.log("making cards")
		let button = document.createElement("button");
		button.classList.add("error-card")
		let name = document.createElement("div");
		name.classList.add("inline-block")
		name.classList.add("flex-grow")
		name.innerText = `${data.hash}-${data.name}`
		
		
		let realm = document.createElement("div");
		realm.classList.add("realm-bar")
		realm.classList.add(realmColors[data.realm])
		
		button.appendChild(name);
		button.appendChild(realm);

		divs[data.state].appendChild(button)

	}
} )(document.getElementById("todo-div"), document.getElementById("working-div"), document.getElementById("fixed-div"))