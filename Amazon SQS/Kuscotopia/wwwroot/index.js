var mainUrl = "overseer";


function runPost() {
	fetch(mainUrl, {
		method: "POST",
		headers: {
			"Content-Type": "application/json" // mime-types
		},
		body: JSON.stringify({
			workCount: document.getElementById("count").value,
		})
	}).then(response => {
		
	}).then(responseJson => {
		
	});
}


function simpleSuccess(data) {
	document.getElementById("results").innerHTML = JSON.stringify(data);
}

function show(){
	console.log(document.getElementById("count").value)
}

window.onload = function() {
	
	document.getElementById("post").onclick = runPost;

}