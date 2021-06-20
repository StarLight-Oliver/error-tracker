
const realmNames = ["Server", "Client", "Shared"]

var openModal;
var makeCard;
var getAllDevs;

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

		func(modalBg, closeBtn, ...data);
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

		btn.addEventListener("click", function(ev) {
			ev.preventDefault();

			openModal("Add new Error", (bg, closeBtn) => {
				let form = document.createElement("form");
				form.autocomplete = "off";
				form.method = "post";
				form.action = "/";
				let fieldset = document.createElement("fieldset");

				let legend = document.createElement("legend");
				legend.innerText = "Input the error details";
				fieldset.appendChild(legend);

				let nameL = document.createElement("label");
				nameL.for = args.name;
				nameL.innerText = "Name of Error";
				fieldset.appendChild(nameL);

				let name = document.createElement("input")
				name.type = "text";
				name.id = args.name;
				name.name = args.name;
				fieldset.appendChild(name);

				fieldset.appendChild(document.createElement("br"));
				fieldset.appendChild(document.createElement("br"));

				let shortErrL = document.createElement("label");
				shortErrL.for = args.shortErr;
				shortErrL.innerText = "Short Error";
				fieldset.appendChild(shortErrL);

				let shortErr = document.createElement("input")
				shortErr.type = "text"
				shortErr.id = args.shortErr;
				shortErr.name = args.shortErr;
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
				stack.cols = 30;
				stack.rows = "5";

				fieldset.append(stack);

				fieldset.appendChild(document.createElement("br"));
				fieldset.appendChild(document.createElement("br"));

				let realmL = document.createElement("label");
				realmL.for = args.realm;
				realmL.innerHTML = "What Realm did the error happen in";
				
				fieldset.append(realmL);

				let realm = document.createElement("select");
				realm.name = args.realm;
				realm.id = args.realm;
				realm.classList.add("bg-gray-600");

				for (let i in realmNames) {
					let option = document.createElement("option");
					option.value = i;
					option.innerText = realmNames[i];
					realm.append(option);
				}

				fieldset.append(realm);

				fieldset.appendChild(document.createElement("br"));
				fieldset.appendChild(document.createElement("br"));

				let submit = document.createElement("input");
				form.appendChild(fieldset);
				
				submit.type = "submit"
				submit.value= "Submit"
				submit.classList.add("btn-blue")
				
				submit.addEventListener("click", function(ev) {
					ev.preventDefault();
				
					let data = {
						hash : CRC32.bstr(shortErr.value + stack.value  + realm.value),
						shortErr: shortErr.value,
						stack: stack.value,
						realm: realm.value,
						state: index,
						name: name.value
					}

					let createError = async ()=> {
						closeBtn.click();
						let resp = await axios.post("/api/error/create", data)
						if (resp.data) {
							makeCard(resp.data);
						}
					}

					createError();
				})


				form.appendChild(submit)
				
				bg.appendChild(form);
			})
		})
	}
} )(document.getElementsByClassName("add-error"));

( (todoDiv, workingDiv, fixedDiv) => {

	let stateData= [
		{
			name: "Todo",
			div: todoDiv,
		},
		{
			name: "Working",
			div: workingDiv,
		},
		{
			name:"Fixed",
			div: fixedDiv
		}
	]

	let handleCardOpening = (bg, data, card)=> {

		let shortErr = document.createElement("p");
		shortErr.innerText = data.shortErr
		bg.appendChild(shortErr)

		let stack = document.createElement("p");
		stack.classList.add("pl-4")
		stack.innerText = data.stack

		bg.appendChild(stack)

		let developerHead = document.createElement("h4");
		developerHead.classList.add("text-center", "text-lg");
		developerHead.innerText = "Developers"

		bg.appendChild(developerHead);

		let developerDiv = document.createElement("div");

		let listDevs = async function() {
			for (let data of card.data.developers) {
				let devbutton = document.createElement("button")
				devbutton.innerText = data.name;
				devbutton.data = data;
				devbutton.classList.add("bg-blue-400")

				devbutton.addEventListener("click", function(ev) {
					ev.preventDefault();
					
					let btn = this;
					
					let resp = confirm(`Are you sure you want to remove ${this.innerText} from the ticket?`);
					if (resp) {
						let removeDeveloper = async function() {
							card.data.developers = card.data.developers.filter(dev => dev !== data);

							let resp2 = await axios.put("/api/error/update/" + card.data.hash, card.data);
							if (resp2.data) {
								devbutton.remove();
							}
						}
						removeDeveloper();
					}
				})

				developerDiv.appendChild(devbutton);
			}

			let selector = document.createElement("select");
			selector.innerText = "Add a Developer";
			
			let devs = getAllDevs();

			for (let devI in devs) {
				let option = document.createElement("option");
				option.value = devI;
				option.innerText = devs[devI].name;
				selector.appendChild(option);
			}

			developerDiv.appendChild(selector);

			let btn = document.createElement("button");
			btn.innerText = "Add Developer";

			btn.addEventListener("click", async function(ev){
				ev.preventDefault();
				card.data.developers.push(devs[selector.value]);

				let resp = await axios.put("/api/error/update/" + card.data.hash, card.data);				

				if (resp.data) {
					card.click();
				}
			})

			developerDiv.appendChild(btn);
		}

		bg.appendChild(developerDiv);

		listDevs();

		let head = document.createElement("h4");
		head.classList.add("text-center", "text-lg");
		head.innerText = "Actions";

		bg.appendChild(head);
		
		let dropdowndiv = document.createElement("div")
		dropdowndiv.classList.add("dropdown")
		
		let move = document.createElement("button")
		move.classList.add("btn-blue", "btn-dropdown")
		move.innerText = "Move Error"
		
		
		let listDiv = document.createElement("div")
		listDiv.classList.add("hidden", "dropdown-content")
		
		dropdowndiv.appendChild(move)
		
		for (let index in stateData) {
			let info = stateData[index];
			let working = document.createElement("button")
			working.classList.add("btn-blue")
			working.innerText = info.name

			working.addEventListener("click", function(ev) {
				ev.preventDefault();
				info.div.appendChild(card);

				card.data.state = index

				let a = async ()=> {
					let resp = await axios.put("/api/error/update/" + card.data.hash, card.data);
				}

				a();
			})
	
			listDiv.appendChild(working);
		}

		dropdowndiv.appendChild(listDiv)


		move.addEventListener("click", function(ev) {
			ev.preventDefault();
			listDiv.classList.toggle("hidden");
		})

		bg.appendChild(dropdowndiv);
		// DELETE BUTTON
		let deleteBtn = document.createElement("button")
		deleteBtn.classList.add("btn-blue")
		deleteBtn.innerText = "Delete Error"

		deleteBtn.addEventListener("click", function(ev){
			ev.preventDefault();

			let deleteError = async ()=> {
				let resp = await axios.delete("/api/error/delete/" + card.data.hash);
				if (resp.data) {
					card.remove();
					closeBtn.click();
				}
			}

			deleteError();

		})

		bg.appendChild(deleteBtn)
	};


	let realmColors = ["bg-blue-600", "bg-yellow-600", "bg-green-600"]
	let divs = [todoDiv, workingDiv, fixedDiv]


	makeCard = (data)=> {
		let button = document.createElement("button");
		button.classList.add("error-card")

		button.data = data;

		let name = document.createElement("div");
		name.classList.add("inline-block")
		name.classList.add("flex-grow")
		name.innerText = `${data.hash}-${data.name}`
		
		
		let realm = document.createElement("div");
		realm.classList.add("realm-bar")
		realm.classList.add(realmColors[data.realm])
		
		button.appendChild(realm);
		button.appendChild(name);

		divs[data.state].appendChild(button);

		button.addEventListener("click", function(ev) {
			ev.preventDefault();
			let btn = this;
			openModal(data.name, (bg) => {
				handleCardOpening(bg, data, btn);
			})
		})

	}

	setTimeout( async function() {
		let resp = await axios.get("/api/error/getAll")
		if (resp.data) {
			for (let error of resp.data) {
				makeCard(error);
			}
		}
	}, 500)

} )(document.getElementById("todo-div"), document.getElementById("working-div"), document.getElementById("fixed-div"))

// Clearing dropdown menus
window.onclick = function(event) {
	if (!event.target.matches('.btn-dropdown')) {
		var dropdowns = document.getElementsByClassName("dropdown-content");
		var i;
		for (i = 0; i < dropdowns.length; i++) {
			var openDropdown = dropdowns[i];
			if (!openDropdown.classList.contains('hidden')) {
				openDropdown.classList.add('hidden');
			}
		}
	}
}

let menus = document.getElementsByClassName("btn-dropdown")

for (let but of menus) {
	but.addEventListener("click", function(ev) {
		ev.preventDefault();
		but.nextElementSibling.classList.toggle("hidden");
	})
};

( (devBtn, devList) => {

	let devs = []

	getAllDevs = function() {
		return devs;
	}

	let addDeveloper = (data) => {
		devs.push(data);

		let devbutton = document.createElement("button")
		devbutton.innerText = data.name;
		devbutton.data = data;

		devbutton.addEventListener("click", function(ev) {
			ev.preventDefault();
			
			let btn = this;
			
			let resp = confirm(`Are you sure you want to delete ${this.innerText}?`);
			if (resp) {
				let deleteDeveloper = async function() {
					let resp2 = await axios.delete("/api/developer/delete/" + btn.data.id)
					if (resp2.data) {
						btn.remove();
						location.reload();
					}
				}
				deleteDeveloper();
			}
		})

		let li = document.createElement("li");
		li.appendChild(devbutton);
		devList.appendChild(li);

		devList.appendChild(devBtn);
	}

	devBtn.addEventListener("click", function(ev) {
		ev.preventDefault();

		let args = {
			name: "name",
		}

		openModal("Add Developer", (bg, closeBtn) => {
			let form = document.createElement("form");
			form.autocomplete = "off";
			let fieldset = document.createElement("fieldset")

			let legend = document.createElement("legend");
			legend.innerText = "Input the error details";
			fieldset.appendChild(legend);

			let nameL = document.createElement("label");
			nameL.for = args.name;
			nameL.innerText = "Name of Developer: ";
			fieldset.appendChild(nameL);

			let name = document.createElement("input");
			name.type = "text";
			name.id = args.name;
			name.name = args.name;
			name.classList.add("bg-gray-600", "px-2");
			fieldset.appendChild(name);

			form.appendChild(fieldset);

			let submit = document.createElement("input")
			submit.type = "submit";
			submit.value= "Submit";
			submit.classList.add("btn-blue");
			
			submit.addEventListener("click", function(ev) {
				ev.preventDefault();

				let data = {
					name: name.value,
				}

				let createDeveloper = async() => {
					let resp = await axios.post("/api/developer/create", data)
					if (resp.data) {
						addDeveloper(resp.data);
						closeBtn.click();
					}
				}

				createDeveloper();

			})

			form.appendChild(submit);
			bg.appendChild(form);
		})

	})

	setTimeout( async function() {
		let resp = await axios.get("/api/developer/getAll")
		if (resp.data) {
			for (let dev of resp.data) {
				addDeveloper(dev);
			}
		}
	}, 500)

} )(document.getElementById("dev-btn"), document.getElementById("dev-list"));
