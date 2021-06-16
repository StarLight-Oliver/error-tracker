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
		console.log(btn, btn.addEventListener, index)


		btn.addEventListener("click", function(ev) {
			ev.preventDefault();

			openModal("Add new Error", (bg, closeBtn) => {
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
						let b = await axios.post("/api/error/create", data)
						console.log(b.data); // if we got the data back
						makeCard(b.data);
						
						
						//makeCard(data);
						//closeBtn.click();
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
		// list devs at the top
		// add plus button
		// list short error

		console.log(data);
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

		// Loop through all developers


		let head = document.createElement("h4");
		head.classList.add("text-center", "text-lg");
		head.innerText = "Actions"

		bg.appendChild(head)


		
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
			console.log(info)
			let working = document.createElement("button")
			working.classList.add("btn-blue")
			working.innerText = info.name

			working.addEventListener("click", function(ev) {
				ev.preventDefault();
				info.div.appendChild(card);

				card.data.state = index

				let a = async ()=> {
					let resp = await axios.post("/api/error/update", card.data);
					console.log(resp);
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

		bg.appendChild(dropdowndiv)
		// <button id = "addTodo" class="btn-blue add-error">Add Error</button>

	};


	let realmColors = ["bg-blue-600", "bg-yellow-600", "bg-green-600"]
	let divs = [todoDiv, workingDiv, fixedDiv]


	makeCard = (data)=> {
		console.log("making cards")
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